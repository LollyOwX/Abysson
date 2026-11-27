package combat;

import entity.Entity;
import entity.StatType;

import java.util.Random;

/**
 * Element system, status effects and reactions.
 *
 * Contains:
 *   - enum Element         → the 7 elements
 *   - enum StatusEffect    → all status effects
 *   - class ActiveEffect   → an effect active on an Entity (effect + duration + stacks)
 *   - static logic         → advantages, reactions, application, per-turn ticks
 *
 * Reaction.java is kept separate (data object with a Builder).
 *
 * ── How to add a new element ───────────────────────────────────────
 *   1. Add it to the Element enum
 *   2. Add its reactions in getReaction() (symmetric table)
 *   3. Add advantages/disadvantages in getMultiplier() if needed
 *
 * ── How to add a new status effect ─────────────────────────────────
 *   1. Add it to the StatusEffect enum
 *   2. Add the logic in processTurnEffects() if it ticks per turn
 *   3. Add it in isPositiveEffect() if it is positive
 *
 * <p> ── Reaction table (symmetric) ─────────────────────────────────
 * <p>          PHY    LGT    FIR    WAT    EAR    WND    ELE
 * <p>  PHY  [  ENH    EXS    INF    ERO    RUP    WHI    JOL ]
 * <p>  LGT  [  EXS    BLD    ABR    PUR    NAT    ILL    RAY ]
 * <p>  FIR  [  INF    ABR    EXP    VAP    CAR    FRN    OVL ]
 * <p>  WAT  [  ERO    PUR    VAP    FLD    MUD    STM    ELZ ]
 * <p>  EAR  [  RUP    NAT    CAR    MUD    RES    PUL    DFL ]
 * <p>  WND  [  WHI    ILL    FRN    STM    PUL    STN    BRN ]
 * <p>  ELE  [  JOL    RAY    OVL    ELZ    DFL    BRN    BLT ]
 *
 * ── Note on "X*ATK" in the reaction descriptions ──────────────────────
 * The game has one ATK/DEF pair PER ELEMENT (FireATK/FireDEF, WaterATK/WaterDEF,
 * ElectricATK/ElectricDEF, EarthATK/EarthDEF, WindATK/WindDEF, LightATK/LightDEF —
 * see StatType, Entity.getElementAttack()/getElementDefense()). Every formula such as
 * "10*FireATK" or "15*ElectricATK" therefore uses the stat OF THAT SPECIFIC ELEMENT
 * of the entity involved — which entity (the one suffering the effect vs the one that
 * caused it) is noted reaction by reaction below. attackStat()/defenseStat()
 * map an Element to the matching StatType pair (used by Player.recalculateStats()
 * to apply the equipment % bonuses stat by stat).
 *
 * ── Note on movement/flight/concealment ──────────────────────────────
 * Naturalization/Muddied (movement block) are hooked to CombatState.tryFlee()
 * (fleeing = moving, so it fails if they are present). Vaporization (flight) and
 * Illumination (concealment) remain declared STUBs: the game has no flight or
 * concealment system to hook them to, so the effect exists (it is applied, shown in
 * the HUD, expires) but blocks nothing until those systems exist.
 */
public class ElementSystem {

    private static final Random rng = new Random();

    public enum Element {
        NONE, PHYSICAL, LIGHT, FIRE, WATER, EARTH, WIND, ELECTRIC
    }

    public enum StatusEffect {
        NONE("None"),
        // ── Pure reactions (same element) ──────────────────────────
        ENHANCEMENT("Enhancement"),         // +20% damage taken
        BLINDING("Blinding"),              // -10% precision, no ranged aiming
        EXPLOSION("Explosion"),              // marker (stun + burn are separate statuses)
        FLOODING("Flooding"),              // marker (the disarm is immediate, not a tick)
        RESISTANCE("Resistance"),             // blocks new POSITIVE effects
        STUN("Stun"),                  // -75% precision, 50% turn skip
        LIGHTNING_BOLT("Lightning Bolt"),            // on failure → 10*ElementATK lightning damage
        // ── Combined reactions ─────────────────────────────────────
        RUPTURE("Rupture"),                   // +(10*lvl)% damage taken
        EXPOSURE("Exposure"),              // +1 duration on negative effects received
        INFLAMMATION("Inflammation"),        // on failure → 10*ElementATK fire damage
        EROSION("Erosion"),                  // each turn removes a random effect
        WHIRLWIND("Whirlwind"),                // reapplies the non-Wind element of the pair
        JOLT("Jolt"),                       // can take two Normal Attacks in a row
        ABRASION("Abrasion"),                // own Fire actions are +50% effective
        PURIFICATION("Purification"),        // removes all positive effects
        NATURALIZATION("Naturalization"),   // movement block (hooked to tryFlee)
        ILLUMINATION("Illumination"),        // STUB — no concealment system
        RAY("Ray"),                        // every ability received causes 5*ElementATK lightning damage
        VAPORIZATION("Vaporization"),       // STUB — no flight system
        CARBONIZATION("Carbonization"),     // every turn 5*ElementATK fire damage
        FIRENADO("Firenado"),                 // immediate damage 5*ElementATK (spreading to allies: STUB, no party)
        OVERLOAD("Overload"),             // tick 15*ElementATK every active turn
        MUDDIED("Muddied"),                 // movement block (hooked to tryFlee)
        STORM("Storm"),                    // tick 15*ElementATK, reapplies the non-damaging element
        ELECTRIFICATION("Electrification"),   // immediate damage 20*ElementATK, reapplies Water
        PULVERIZATION("Pulverization"),     // blocks ranged aiming
        DEFLECTION("Deflection"),             // deflects ranged attacks, -50% aiming
        BRANCHING("Branching"),           // growing tick: stacks*15*ElementATK
        SEVERE_BURN("Severe burn");      // from Explosion: tick (duration/16)*maxLife, removal (duration/5)

        public final String displayName;
        StatusEffect(String displayName) { this.displayName = displayName; }
    }

    /**
     *   0  = this turn only
     *  -1  = permanent until the end of combat
     *  >0  = N turns remaining
     */
    public static class ActiveEffect {
        public StatusEffect effect;
        public int duration;
        public int stacks = 0; // used by Branching: grows by 1 each tick

        public ActiveEffect(StatusEffect effect, int duration) {
            this.effect = effect;
            this.duration = duration;
        }
    }

    public static double getMultiplier(Element attacker, Element defender) {
        if (attacker == Element.NONE || defender == Element.NONE) return 1.0;

        if (attacker == Element.EARTH && defender == Element.FIRE) return 1.25;
        if (attacker == Element.WIND && defender == Element.FIRE) return 1.25;
        if (attacker == Element.WATER && defender == Element.FIRE) return 2;
        if (attacker == Element.ELECTRIC && defender == Element.FIRE) return 0.75;
        if (attacker == Element.LIGHT && defender == Element.FIRE) return 0.75;

        if (attacker == Element.FIRE && defender == Element.WATER) return 2;
        if (attacker == Element.ELECTRIC && defender == Element.WATER) return 1.5;
        if (attacker == Element.LIGHT && defender == Element.WATER) return 0.5;

        if (attacker == Element.WIND && defender == Element.EARTH) return 1.25;
        if (attacker == Element.ELECTRIC && defender == Element.EARTH) return 0.5;

        if (attacker == Element.LIGHT && defender == Element.WIND) return 1.5;
        if (attacker == Element.ELECTRIC && defender == Element.WIND) return 1.5;
        if (attacker == Element.EARTH && defender == Element.WIND) return 0.75;

        if (attacker == Element.EARTH && defender == Element.ELECTRIC) return 2;
        if (attacker == Element.FIRE && defender == Element.ELECTRIC) return 1.5;
        if (attacker == Element.WIND && defender == Element.ELECTRIC) return 0.75;
        if (attacker == Element.WATER && defender == Element.ELECTRIC) return 0.75;

        if (attacker == Element.WATER && defender == Element.LIGHT) return 1.25;
        if (attacker == Element.EARTH && defender == Element.LIGHT) return 1.5;
        if (attacker == Element.FIRE && defender == Element.LIGHT) return 0.75;
        if (attacker == Element.ELECTRIC && defender == Element.LIGHT) return 0.75;

        return 1.0;
    }

    /**
     * Maps an element to its ATK/DEF StatType pair (used by Player.recalculateStats()
     * to apply the equipment % bonuses). PHYSICAL/NONE have no elemental pair: they
     * return null (physical abilities use ATTACK/DIFESA, not an elemental pair — see
     * Ability.isElemental()).
     */
    public static StatType attackStat(Element e) {
        switch (e) {
            case FIRE:   return StatType.FIRE_ATK;
            case WATER:   return StatType.WATER_ATK;
            case ELECTRIC: return StatType.ELECTRIC_ATK;
            case EARTH:   return StatType.EARTH_ATK;
            case WIND:    return StatType.WIND_ATK;
            case LIGHT:    return StatType.LIGHT_ATK;
            default:      return null;
        }
    }

    public static StatType defenseStat(Element e) {
        switch (e) {
            case FIRE:   return StatType.FIRE_DEF;
            case WATER:   return StatType.WATER_DEF;
            case ELECTRIC: return StatType.ELECTRIC_DEF;
            case EARTH:   return StatType.EARTH_DEF;
            case WIND:    return StatType.WIND_DEF;
            case LIGHT:    return StatType.LIGHT_DEF;
            default:      return null;
        }
    }

    /**
     * The table is symmetric: order does not matter. entityLevel = level of the target
     * (used by Rupture). attacker = whoever caused the hit that triggers the reaction,
     * used by reactions whose bonus damage depends on its element ATK (Electrification,
     * Firenado).
     */
    public static Reaction getReaction(Element existing, Element incoming, int entityLevel, Entity attacker) {
        if (existing == Element.NONE || incoming == Element.NONE) return Reaction.NONE;

        int a = Math.min(existing.ordinal(), incoming.ordinal());
        int b = Math.max(existing.ordinal(), incoming.ordinal());
        Element ea = Element.values()[a];
        Element eb = Element.values()[b];

        // ── Diagonal (same element) — pure reactions ───────────
        if (a == b) {
            switch (ea) {
                case PHYSICAL:
                    return new Reaction.Builder("Enhancement", StatusEffect.ENHANCEMENT)
                            .duration(0).multiplier(1.2).build();
                case LIGHT:
                    return new Reaction.Builder("Blinding", StatusEffect.BLINDING)
                            .duration(3).build();
                case FIRE:
                    return new Reaction.Builder("Explosion", StatusEffect.STUN)
                            .duration(3)
                            .also(StatusEffect.SEVERE_BURN, 5) // initial burn duration: chosen value, to be balanced
                            .build();
                case WATER:
                    return new Reaction.Builder("Flooding", StatusEffect.NONE)
                            .disarm().build();
                case EARTH:
                    return new Reaction.Builder("Resistance", StatusEffect.RESISTANCE)
                            .duration(0).build();
                case WIND:
                    return new Reaction.Builder("Stun", StatusEffect.STUN)
                            .duration(5).build();
                case ELECTRIC:
                    return new Reaction.Builder("Lightning Bolt", StatusEffect.LIGHTNING_BOLT)
                            .duration(0).build();
                default:
                    return Reaction.NONE;
            }
        }

        // ── Combinations — combined reactions ────────────────────
        // PHYSICAL with...
        if (ea == Element.PHYSICAL && eb == Element.LIGHT)
            return new Reaction.Builder("Exposure", StatusEffect.EXPOSURE).duration(0).build();
        if (ea == Element.PHYSICAL && eb == Element.FIRE)
            return new Reaction.Builder("Inflammation", StatusEffect.INFLAMMATION).duration(0).build();
        if (ea == Element.PHYSICAL && eb == Element.WATER)
            return new Reaction.Builder("Erosion", StatusEffect.EROSION).duration(-1).build();
        if (ea == Element.PHYSICAL && eb == Element.EARTH)
            return new Reaction.Builder("Rupture", StatusEffect.RUPTURE)
                    .duration(0).multiplier(1.0 + (0.10 * entityLevel)).build();
        if (ea == Element.PHYSICAL && eb == Element.WIND)
            return new Reaction.Builder("Whirlwind", StatusEffect.WHIRLWIND)
                    .duration(0).reapply(Element.PHYSICAL).build();
        if (ea == Element.PHYSICAL && eb == Element.ELECTRIC)
            return new Reaction.Builder("Jolt", StatusEffect.JOLT).duration(0).build();

        // LIGHT with...
        if (ea == Element.LIGHT && eb == Element.FIRE)
            return new Reaction.Builder("Abrasion", StatusEffect.ABRASION).duration(-1).build();
        if (ea == Element.LIGHT && eb == Element.WATER)
            return new Reaction.Builder("Purification", StatusEffect.PURIFICATION).duration(0).build();
        if (ea == Element.LIGHT && eb == Element.EARTH)
            return new Reaction.Builder("Naturalization", StatusEffect.NATURALIZATION).duration(-1).build();
        if (ea == Element.LIGHT && eb == Element.WIND)
            return new Reaction.Builder("Illumination", StatusEffect.ILLUMINATION).duration(-1).build();
        if (ea == Element.LIGHT && eb == Element.ELECTRIC)
            return new Reaction.Builder("Ray", StatusEffect.RAY).duration(-1).build();

        // FIRE with...
        if (ea == Element.FIRE && eb == Element.WATER)
            return new Reaction.Builder("Vaporization", StatusEffect.VAPORIZATION).duration(-1).build();
        if (ea == Element.FIRE && eb == Element.EARTH)
            return new Reaction.Builder("Carbonization", StatusEffect.CARBONIZATION).duration(-1).build();
        if (ea == Element.FIRE && eb == Element.WIND)
            return new Reaction.Builder("Firenado", StatusEffect.FIRENADO)
                    .duration(0).bonus(5 * attacker.getElementAttack(Element.FIRE), Element.FIRE).build();
        if (ea == Element.FIRE && eb == Element.ELECTRIC)
            return new Reaction.Builder("Overload", StatusEffect.STUN)
                    .duration(1).also(StatusEffect.OVERLOAD, 1).build();

        // WATER with...
        if (ea == Element.WATER && eb == Element.EARTH)
            return new Reaction.Builder("Muddied", StatusEffect.MUDDIED).duration(-1).build();
        if (ea == Element.WATER && eb == Element.WIND)
            return new Reaction.Builder("Storm", StatusEffect.STORM).duration(3).build();
        if (ea == Element.WATER && eb == Element.ELECTRIC)
            return new Reaction.Builder("Electrification", StatusEffect.ELECTRIFICATION)
                    .duration(0).bonus(20 * attacker.getElementAttack(Element.ELECTRIC), Element.ELECTRIC).reapply(Element.WATER).build();

        // EARTH with...
        if (ea == Element.EARTH && eb == Element.WIND)
            return new Reaction.Builder("Pulverization", StatusEffect.PULVERIZATION).duration(-1).build();
        if (ea == Element.EARTH && eb == Element.ELECTRIC)
            return new Reaction.Builder("Deflection", StatusEffect.DEFLECTION).duration(-1).build();

        // WIND with...
        if (ea == Element.WIND && eb == Element.ELECTRIC)
            return new Reaction.Builder("Branching", StatusEffect.BRANCHING).duration(-1).build();

        return Reaction.NONE;
    }

    // ═════════════════════════════════════════════
    //  APPLY REACTION
    // ═════════════════════════════════════════════

    /**
     * Applies a reaction's effects to the target. Returns the reaction's immediate bonus
     * damage. Updates the target's lastElementHit (normally with the incoming element,
     * unless the reaction specifies a different reapplyElement — Whirlwind/Storm/Electrification).
     */
    public static int applyReaction(Reaction reaction, Entity target, Element incoming) {
        if (reaction == Reaction.NONE) {
            target.lastElementHit = incoming;
            return 0;
        }

        // Purification: removes positive effects before applying the new one
        if (reaction.effect == StatusEffect.PURIFICATION) {
            removePositiveEffects(target);
        }

        // Resistance blocks ONLY new positive effects, not negative ones
        boolean resisted = hasEffect(target, StatusEffect.RESISTANCE);

        if (!(resisted && isPositiveEffect(reaction.effect))) {
            addEffect(target, reaction.effect, reaction.effectDuration);
        }
        if (reaction.secondaryEffect != StatusEffect.NONE
                && !(resisted && isPositiveEffect(reaction.secondaryEffect))) {
            addEffect(target, reaction.secondaryEffect, reaction.secondaryDuration);
        }

        target.lastElementHit = (reaction.reapplyElement != Element.NONE) ? reaction.reapplyElement : incoming;

        return reaction.bonusDamage;
    }

    // ═════════════════════════════════════════════
    //  TURN TICK
    // ═════════════════════════════════════════════

    /**
     * Processes the bearer's persistent effects ON ITS OWN turn (called by
     * CombatState.afterTurn() after the entity has acted — or after it skipped its turn).
     * They used to tick only on the TARGET when hit, inside dealDamage: now each entity
     * takes its own effects once per round, on its own turn (see §5 to do in STATUS.md —
     * solved here). Returns the total damage from effects this tick.
     */
    public static int processTurnEffects(Entity target) {
        int damage = 0;
        for (int i = target.activeEffects.size() - 1; i >= 0; i--) {
            ActiveEffect ae = target.activeEffects.get(i);
            switch (ae.effect) {
                case CARBONIZATION:
                    damage += 5 * target.getElementAttack(Element.FIRE);
                    break;
                case STORM: {
                    boolean windBranch = rng.nextBoolean();
                    // 50/50 Wind or Electric: each uses its own elemental ATK now
                    damage += 15 * target.getElementAttack(windBranch ? Element.WIND : Element.ELECTRIC);
                    // reapply the NON-damaging element of this tick
                    target.lastElementHit = windBranch ? Element.ELECTRIC : Element.WIND;
                    break;
                }
                case OVERLOAD: {
                    boolean fireBranch = rng.nextBoolean();
                    // Fire or Electric, 50/50: each uses its own elemental ATK now
                    damage += 15 * target.getElementAttack(fireBranch ? Element.FIRE : Element.ELECTRIC);
                    break;
                }
                case BRANCHING:
                    ae.stacks++;
                    damage += ae.stacks * 15 * target.getElementAttack(Element.ELECTRIC);
                    break;
                case SEVERE_BURN: {
                    int burnDmg = (int) Math.max(1, (ae.duration / 16.0) * target.maxLife);
                    damage += burnDmg;
                    double removeChance = Math.min(1.0, ae.duration / 5.0);
                    if (rng.nextDouble() < removeChance) {
                        target.activeEffects.remove(i);
                        continue; // already removed here, do not decrement duration below
                    }
                    break;
                }
                case EROSION:
                    if (!target.activeEffects.isEmpty()) {
                        int removeIdx = rng.nextInt(target.activeEffects.size());
                        target.activeEffects.remove(removeIdx);
                        i = Math.min(i, target.activeEffects.size() - 1);
                        continue; // already removed, do not decrement duration
                    }
                    break;
                default:
                    break;
            }
            // Decrement duration
            if (ae.duration > 0) {
                ae.duration--;
                if (ae.duration == 0) {
                    target.activeEffects.remove(i);
                }
            } else if (ae.duration == 0) {
                target.activeEffects.remove(i);
            }
            // duration == -1 → permanent, do not remove
        }
        return damage;
    }

    // ═════════════════════════════════════════════
    //  STATUS MODIFIERS (precision / aiming)
    // ═════════════════════════════════════════════

    /** Combines Stun/Blinding/Deflection on the acting entity's precision. */
    public static double precisionMultiplier(Entity e) {
        double mult = 1.0;
        if (hasEffect(e, StatusEffect.STUN)) mult *= 0.25; // -75%
        if (hasEffect(e, StatusEffect.BLINDING)) mult *= 0.90; // -10%
        if (hasEffect(e, StatusEffect.DEFLECTION))  mult *= 0.50; // -50%
        return mult;
    }

    /** Blinding/Pulverization: whoever bears them cannot aim with ranged abilities. */
    public static boolean isRangedBlocked(Entity attacker, String abilityId) {
        boolean blocked = hasEffect(attacker, StatusEffect.BLINDING)
                || hasEffect(attacker, StatusEffect.PULVERIZATION);
        return blocked && Ability.isRanged(abilityId);
    }

    /** Deflection: the target deflects incoming ranged attacks (automatic miss). */
    public static boolean isDeflected(Entity defender, String abilityId) {
        return hasEffect(defender, StatusEffect.DEFLECTION) && Ability.isRanged(abilityId);
    }

    // ═════════════════════════════════════════════
    //  EFFECT QUERIES
    // ═════════════════════════════════════════════

    public static boolean hasEffect(Entity e, StatusEffect effect) {
        for (ActiveEffect ae : e.activeEffects) {
            if (ae.effect == effect) return true;
        }
        return false;
    }

    /**
     * Adds an effect — if already present on the target, only refreshes its duration instead
     * of duplicating it (important for Branching: a single instance whose stacks grow over
     * time, not many separate instances).
     */
    public static void addEffect(Entity e, StatusEffect effect, int duration) {
        if (effect == StatusEffect.NONE) return;
        // Exposure: +1 duration on new negative effects
        if (hasEffect(e, StatusEffect.EXPOSURE) && isNegativeEffect(effect)) {
            duration = duration < 0 ? duration : duration + 1;
        }
        for (ActiveEffect ae : e.activeEffects) {
            if (ae.effect == effect) { ae.duration = duration; return; }
        }
        e.activeEffects.add(new ActiveEffect(effect, duration));
    }

    public static void removePositiveEffects(Entity e) {
        e.activeEffects.removeIf(ae -> isPositiveEffect(ae.effect));
    }

    public static void removeAllEffects(Entity e) {
        e.activeEffects.clear();
    }

    public static boolean isPositiveEffect(StatusEffect e) {
        switch (e) {
            case RESISTANCE:
            case DEFLECTION:
            case ABRASION:
                return true;
            default:
                return false;
        }
    }

    public static boolean isNegativeEffect(StatusEffect e) {
        return !isPositiveEffect(e) && e != StatusEffect.NONE;
    }

    public static int rollD6(int n) {
        int t = 0; for (int i = 0; i < n; i++) t += rng.nextInt(6) + 1; return t;
    }

    public static int rollD8(int n) {
        int t = 0; for (int i = 0; i < n; i++) t += rng.nextInt(8) + 1; return t;
    }
}
