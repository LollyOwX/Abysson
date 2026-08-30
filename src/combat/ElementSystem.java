package combat;

import entity.Entity;
import entity.StatType;

import java.util.Random;

/**
 * Sistema elementi, effetti di stato e reazioni.
 *
 * Contiene:
 *   - enum Element         → i 7 elementi
 *   - enum StatusEffect    → tutti gli effetti di stato
 *   - class ActiveEffect   → effetto attivo su un'Entity (effect + durata + stacks)
 *   - logica statica       → vantaggi, reazioni, applicazione, tick di turno
 *
 * Reaction.java è tenuta separata (oggetto dati con Builder).
 *
 * ── Come aggiungere un nuovo elemento ───────────────────────────────
 *   1. Aggiungilo all'enum Element
 *   2. Aggiungi le reazioni in getReaction() (tabella simmetrica)
 *   3. Aggiungi vantaggi/svantaggi in getMultiplier() se necessario
 *
 * ── Come aggiungere un nuovo effetto di stato ────────────────────────
 *   1. Aggiungilo all'enum StatusEffect
 *   2. Aggiungi la logica in processTurnEffects() se ha tick per turno
 *   3. Aggiungi in isPositiveEffect() se è positivo
 *
 * <p> ── Tabella reazioni (simmetrica) ───────────────────────────────────
 * <p>          FIS    LUC    FUO    ACQ    TER    ARI    FUL
 * <p>  FIS  [  POT    ESP    INF    ERO    ROT    TUR    SCO ]
 * <p>  LUC  [  ESP    ACC    ABR    PUR    NAT    ILL    RAG ]
 * <p>  FUO  [  INF    ABR    ESP    VAP    CAR    FIR    SOV ]
 * <p>  ACQ  [  ERO    PUR    VAP    INO    INF    TEM    ELT ]
 * <p>  TER  [  ROT    NAT    CAR    INF    RES    POL    DEV ]
 * <p>  ARI  [  TUR    ILL    FIR    TEM    POL    STO    RAM ]
 * <p>  FUL  [  SCO    RAG    SOV    ELT    DEV    RAM    FOL ]
 *
 * ── Nota su "X*ATK" nelle descrizioni delle reazioni ─────────────────
 * Il gioco ha una coppia ATK/DEF PER ELEMENTO (FireATK/FireDEF, WaterATK/WaterDEF,
 * ElectricATK/ElectricDEF, EarthATK/EarthDEF, WindATK/WindDEF, LightATK/LightDEF —
 * vedi StatType, Entity.getElementAttack()/getElementDefense()). Ogni formula tipo
 * "10*FireATK" o "15*ElectricATK" usa quindi lo stat DI QUELL'ELEMENTO SPECIFICO
 * dell'entità coinvolta — la scelta di quale entità (chi subisce l'effetto vs chi
 * l'ha causato) è annotata reazione per reazione qui sotto. attackStat()/defenseStat()
 * mappano un Element alla coppia StatType corrispondente (usata da Player.recalculateStats()
 * per applicare i bonus % dell'equipaggiamento stat per stat).
 *
 * ── Nota su movimento/volo/occultamento ──────────────────────────────
 * Naturalizzazione/Infangato (blocco movimento) sono agganciati a CombatState.tryFlee()
 * (fuggire = muoversi, quindi fallisce se presenti). Vaporizzazione (volo) e Illuminazione
 * (occultamento) restano STUB dichiarati: il gioco non ha alcun sistema di volo o
 * occultamento a cui agganciarli, quindi l'effetto esiste (si applica, si vede in HUD, scade)
 * ma non blocca nulla finché quei sistemi non esisteranno.
 */
public class ElementSystem {

    private static final Random rng = new Random();

    public enum Element {
        NONE, FISICO, LUCE, FUOCO, ACQUA, TERRA, ARIA, FULMINE
    }

    public enum StatusEffect {
        NONE("Nessuno"),
        // ── Reazioni pure (stesso elemento) ────────────────────────
        POTENZIAMENTO("Potenziamento"),       // +20% danni subiti
        ACCECAMENTO("Accecamento"),           // -10% precisione, no mira a distanza
        ESPLOSIONE("Esplosione"),             // marcatore (stordimento+bruciatura sono status separati)
        INONDAZIONE("Inondazione"),           // marcatore (il disarmo è immediato, non un tick)
        RESISTENZA("Resistenza"),             // blocca nuovi effetti POSITIVI
        STORDIMENTO("Stordimento"),           // -75% precisione, 50% skip turno
        FOLGORE("Folgore"),                   // su fallimento → 10*ElementoATK danni fulmine
        // ── Reazioni articolate ────────────────────────────────────
        ROTTURA("Rottura"),                   // +(10*liv)% danni subiti
        ESPOSIZIONE("Esposizione"),           // +1 durata effetti negativi subiti
        INFIAMMAZIONE("Infiammazione"),       // su fallimento → 10*ElementoATK danni fuoco
        EROSIONE("Erosione"),                 // ogni turno rimuove un effetto casuale
        TURBINIO("Turbinio"),                 // riapplica l'elemento non-Aria della coppia
        SCOSSA("Scossa"),                     // può subire due Attacchi Normali di fila
        ABRASIONE("Abrasione"),               // le proprie azioni Fuoco sono +50% efficaci
        PURIFICAZIONE("Purificazione"),       // rimuove tutti gli effetti positivi
        NATURALIZZAZIONE("Naturalizzazione"), // blocco movimento (agganciato a tryFlee)
        ILLUMINAZIONE("Illuminazione"),       // STUB — nessun sistema di occultamento
        RAGGIO("Raggio"),                     // ogni abilità subita causa 5*ElementoATK danni fulmine
        VAPORIZZAZIONE("Vaporizzazione"),     // STUB — nessun sistema di volo
        CARBONIZZAZIONE("Carbonizzazione"),   // ogni turno 5*ElementoATK danni fuoco
        FIRENADO("Firenado"),                 // danno immediato 5*ElementoATK (diffusione agli alleati: STUB, niente party)
        SOVRACCARICO("Sovraccarico"),         // tick 15*ElementoATK ogni turno attivo
        INFANGATO("Infangato"),               // blocco movimento (agganciato a tryFlee)
        TEMPESTA("Tempesta"),                 // tick 15*ElementoATK, riapplica l'elemento non danneggiante
        ELETTRIZZAZIONE("Elettrizzazione"),   // danno immediato 20*ElementoATK, riapplica Acqua
        POLVERIZZAZIONE("Polverizzazione"),   // impedisce mira a distanza
        DEVIAZIONE("Deviazione"),             // devia attacchi a distanza, -50% mira
        RAMIFICAZIONE("Ramificazione"),       // tick crescente: stacks*15*ElementoATK
        BRUCIATURA_GRAVE("Bruciatura grave"); // da Esplosione: tick (durata/16)*vitaMax, rimozione (durata/5)

        public final String displayName;
        StatusEffect(String displayName) { this.displayName = displayName; }
    }

    /**
     *   0  = solo questo turno
     *  -1  = permanente fino a fine combattimento
     *  >0  = N turni rimanenti
     */
    public static class ActiveEffect {
        public StatusEffect effect;
        public int duration;
        public int stacks = 0; // usato da Ramificazione: cresce di 1 ad ogni tick

        public ActiveEffect(StatusEffect effect, int duration) {
            this.effect = effect;
            this.duration = duration;
        }
    }

    public static double getMultiplier(Element attacker, Element defender) {
        if (attacker == Element.NONE || defender == Element.NONE) return 1.0;

        if (attacker == Element.TERRA && defender == Element.FUOCO) return 1.25;
        if (attacker == Element.ARIA && defender == Element.FUOCO) return 1.25;
        if (attacker == Element.ACQUA && defender == Element.FUOCO) return 2;
        if (attacker == Element.FULMINE && defender == Element.FUOCO) return 0.75;
        if (attacker == Element.LUCE && defender == Element.FUOCO) return 0.75;

        if (attacker == Element.FUOCO && defender == Element.ACQUA) return 2;
        if (attacker == Element.FULMINE && defender == Element.ACQUA) return 1.5;
        if (attacker == Element.LUCE && defender == Element.ACQUA) return 0.5;

        if (attacker == Element.ARIA && defender == Element.TERRA) return 1.25;
        if (attacker == Element.FULMINE && defender == Element.TERRA) return 0.5;

        if (attacker == Element.LUCE && defender == Element.ARIA) return 1.5;
        if (attacker == Element.FULMINE && defender == Element.ARIA) return 1.5;
        if (attacker == Element.TERRA && defender == Element.ARIA) return 0.75;

        if (attacker == Element.TERRA && defender == Element.FULMINE) return 2;
        if (attacker == Element.FUOCO && defender == Element.FULMINE) return 1.5;
        if (attacker == Element.ARIA && defender == Element.FULMINE) return 0.75;
        if (attacker == Element.ACQUA && defender == Element.FULMINE) return 0.75;

        if (attacker == Element.ACQUA && defender == Element.LUCE) return 1.25;
        if (attacker == Element.TERRA && defender == Element.LUCE) return 1.5;
        if (attacker == Element.FUOCO && defender == Element.LUCE) return 0.75;
        if (attacker == Element.FULMINE && defender == Element.LUCE) return 0.75;

        return 1.0;
    }

    /**
     * Mappa un elemento alla coppia StatType di ATK/DEF corrispondente (usata da
     * Player.recalculateStats() per applicare i bonus % dell'equipaggiamento).
     * FISICO/NONE non hanno una coppia elementale: ritornano null (le abilità fisiche
     * usano ATTACK/DIFESA, non una coppia elementale — vedi Ability.isElemental()).
     */
    public static StatType attackStat(Element e) {
        switch (e) {
            case FUOCO:   return StatType.FIRE_ATK;
            case ACQUA:   return StatType.WATER_ATK;
            case FULMINE: return StatType.ELECTRIC_ATK;
            case TERRA:   return StatType.EARTH_ATK;
            case ARIA:    return StatType.WIND_ATK;
            case LUCE:    return StatType.LIGHT_ATK;
            default:      return null;
        }
    }

    public static StatType defenseStat(Element e) {
        switch (e) {
            case FUOCO:   return StatType.FIRE_DEF;
            case ACQUA:   return StatType.WATER_DEF;
            case FULMINE: return StatType.ELECTRIC_DEF;
            case TERRA:   return StatType.EARTH_DEF;
            case ARIA:    return StatType.WIND_DEF;
            case LUCE:    return StatType.LIGHT_DEF;
            default:      return null;
        }
    }

    /**
     * La tabella è simmetrica: l'ordine non conta. entityLevel = livello del bersaglio
     * (usato da Rottura). attacker = chi ha causato il colpo che innesca la reazione,
     * usato dalle reazioni il cui danno bonus dipende dal suo ElementoATK (Elettrizzazione,
     * Firenado).
     */
    public static Reaction getReaction(Element existing, Element incoming, int entityLevel, Entity attacker) {
        if (existing == Element.NONE || incoming == Element.NONE) return Reaction.NONE;

        int a = Math.min(existing.ordinal(), incoming.ordinal());
        int b = Math.max(existing.ordinal(), incoming.ordinal());
        Element ea = Element.values()[a];
        Element eb = Element.values()[b];

        // ── Diagonale (stesso elemento) — reazioni pure ───────────
        if (a == b) {
            switch (ea) {
                case FISICO:
                    return new Reaction.Builder("Potenziamento", StatusEffect.POTENZIAMENTO)
                            .duration(0).multiplier(1.2).build();
                case LUCE:
                    return new Reaction.Builder("Accecamento", StatusEffect.ACCECAMENTO)
                            .duration(3).build();
                case FUOCO:
                    return new Reaction.Builder("Esplosione", StatusEffect.STORDIMENTO)
                            .duration(3)
                            .also(StatusEffect.BRUCIATURA_GRAVE, 5) // durata iniziale bruciatura: valore scelto, da bilanciare
                            .build();
                case ACQUA:
                    return new Reaction.Builder("Inondazione", StatusEffect.NONE)
                            .disarm().build();
                case TERRA:
                    return new Reaction.Builder("Resistenza", StatusEffect.RESISTENZA)
                            .duration(0).build();
                case ARIA:
                    return new Reaction.Builder("Stordimento", StatusEffect.STORDIMENTO)
                            .duration(5).build();
                case FULMINE:
                    return new Reaction.Builder("Folgore", StatusEffect.FOLGORE)
                            .duration(0).build();
                default:
                    return Reaction.NONE;
            }
        }

        // ── Combinazioni — reazioni articolate ────────────────────
        // FISICO con...
        if (ea == Element.FISICO && eb == Element.LUCE)
            return new Reaction.Builder("Esposizione", StatusEffect.ESPOSIZIONE).duration(0).build();
        if (ea == Element.FISICO && eb == Element.FUOCO)
            return new Reaction.Builder("Infiammazione", StatusEffect.INFIAMMAZIONE).duration(0).build();
        if (ea == Element.FISICO && eb == Element.ACQUA)
            return new Reaction.Builder("Erosione", StatusEffect.EROSIONE).duration(-1).build();
        if (ea == Element.FISICO && eb == Element.TERRA)
            return new Reaction.Builder("Rottura", StatusEffect.ROTTURA)
                    .duration(0).multiplier(1.0 + (0.10 * entityLevel)).build();
        if (ea == Element.FISICO && eb == Element.ARIA)
            return new Reaction.Builder("Turbinio", StatusEffect.TURBINIO)
                    .duration(0).reapply(Element.FISICO).build();
        if (ea == Element.FISICO && eb == Element.FULMINE)
            return new Reaction.Builder("Scossa", StatusEffect.SCOSSA).duration(0).build();

        // LUCE con...
        if (ea == Element.LUCE && eb == Element.FUOCO)
            return new Reaction.Builder("Abrasione", StatusEffect.ABRASIONE).duration(-1).build();
        if (ea == Element.LUCE && eb == Element.ACQUA)
            return new Reaction.Builder("Purificazione", StatusEffect.PURIFICAZIONE).duration(0).build();
        if (ea == Element.LUCE && eb == Element.TERRA)
            return new Reaction.Builder("Naturalizzazione", StatusEffect.NATURALIZZAZIONE).duration(-1).build();
        if (ea == Element.LUCE && eb == Element.ARIA)
            return new Reaction.Builder("Illuminazione", StatusEffect.ILLUMINAZIONE).duration(-1).build();
        if (ea == Element.LUCE && eb == Element.FULMINE)
            return new Reaction.Builder("Raggio", StatusEffect.RAGGIO).duration(-1).build();

        // FUOCO con...
        if (ea == Element.FUOCO && eb == Element.ACQUA)
            return new Reaction.Builder("Vaporizzazione", StatusEffect.VAPORIZZAZIONE).duration(-1).build();
        if (ea == Element.FUOCO && eb == Element.TERRA)
            return new Reaction.Builder("Carbonizzazione", StatusEffect.CARBONIZZAZIONE).duration(-1).build();
        if (ea == Element.FUOCO && eb == Element.ARIA)
            return new Reaction.Builder("Firenado", StatusEffect.FIRENADO)
                    .duration(0).bonus(5 * attacker.getElementAttack(Element.FUOCO), Element.FUOCO).build();
        if (ea == Element.FUOCO && eb == Element.FULMINE)
            return new Reaction.Builder("Sovraccarico", StatusEffect.STORDIMENTO)
                    .duration(1).also(StatusEffect.SOVRACCARICO, 1).build();

        // ACQUA con...
        if (ea == Element.ACQUA && eb == Element.TERRA)
            return new Reaction.Builder("Infangato", StatusEffect.INFANGATO).duration(-1).build();
        if (ea == Element.ACQUA && eb == Element.ARIA)
            return new Reaction.Builder("Tempesta", StatusEffect.TEMPESTA).duration(3).build();
        if (ea == Element.ACQUA && eb == Element.FULMINE)
            return new Reaction.Builder("Elettrizzazione", StatusEffect.ELETTRIZZAZIONE)
                    .duration(0).bonus(20 * attacker.getElementAttack(Element.FULMINE), Element.FULMINE).reapply(Element.ACQUA).build();

        // TERRA con...
        if (ea == Element.TERRA && eb == Element.ARIA)
            return new Reaction.Builder("Polverizzazione", StatusEffect.POLVERIZZAZIONE).duration(-1).build();
        if (ea == Element.TERRA && eb == Element.FULMINE)
            return new Reaction.Builder("Deviazione", StatusEffect.DEVIAZIONE).duration(-1).build();

        // ARIA con...
        if (ea == Element.ARIA && eb == Element.FULMINE)
            return new Reaction.Builder("Ramificazione", StatusEffect.RAMIFICAZIONE).duration(-1).build();

        return Reaction.NONE;
    }

    // ═════════════════════════════════════════════
    //  APPLICA REAZIONE
    // ═════════════════════════════════════════════

    /**
     * Applica gli effetti di una reazione al target. Ritorna il danno immediato bonus della
     * reazione. Aggiorna lastElementHit del target (di norma con l'elemento in arrivo, salvo
     * che la reazione specifichi un reapplyElement diverso — Turbinio/Tempesta/Elettrizzazione).
     */
    public static int applyReaction(Reaction reaction, Entity target, Element incoming) {
        if (reaction == Reaction.NONE) {
            target.lastElementHit = incoming;
            return 0;
        }

        // Purificazione: rimuove effetti positivi prima di applicare il nuovo
        if (reaction.effect == StatusEffect.PURIFICAZIONE) {
            removePositiveEffects(target);
        }

        // Resistenza blocca SOLO i nuovi effetti positivi, non quelli negativi
        boolean resisted = hasEffect(target, StatusEffect.RESISTENZA);

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
    //  TICK DI TURNO
    // ═════════════════════════════════════════════

    /**
     * Processa gli effetti persistenti del portatore SUL PROPRIO turno (chiamato da
     * CombatState.afterTurn() dopo che l'entità ha agito — o dopo aver saltato il turno).
     * Prima ticchettavano solo sul BERSAGLIO quando veniva colpito, dentro dealDamage: ora
     * ogni entità subisce i propri effetti una volta a round, sul proprio turno (vedi §5
     * TODO in STATUS.md — risolto qui). Ritorna il danno totale da effetti questo tick.
     */
    public static int processTurnEffects(Entity target) {
        int damage = 0;
        for (int i = target.activeEffects.size() - 1; i >= 0; i--) {
            ActiveEffect ae = target.activeEffects.get(i);
            switch (ae.effect) {
                case CARBONIZZAZIONE:
                    damage += 5 * target.getElementAttack(Element.FUOCO);
                    break;
                case TEMPESTA: {
                    boolean windBranch = rng.nextBoolean();
                    // 50/50 Wind o Fulmine: ognuno usa il proprio ATK elementale ora
                    damage += 15 * target.getElementAttack(windBranch ? Element.ARIA : Element.FULMINE);
                    // riapplica l'elemento NON danneggiante di questo tick
                    target.lastElementHit = windBranch ? Element.FULMINE : Element.ARIA;
                    break;
                }
                case SOVRACCARICO: {
                    boolean fireBranch = rng.nextBoolean();
                    // Fuoco o Fulmine, 50/50: ognuno usa il proprio ATK elementale ora
                    damage += 15 * target.getElementAttack(fireBranch ? Element.FUOCO : Element.FULMINE);
                    break;
                }
                case RAMIFICAZIONE:
                    ae.stacks++;
                    damage += ae.stacks * 15 * target.getElementAttack(Element.FULMINE);
                    break;
                case BRUCIATURA_GRAVE: {
                    int burnDmg = (int) Math.max(1, (ae.duration / 16.0) * target.maxLife);
                    damage += burnDmg;
                    double removeChance = Math.min(1.0, ae.duration / 5.0);
                    if (rng.nextDouble() < removeChance) {
                        target.activeEffects.remove(i);
                        continue; // già rimosso da qui, non scalare durata sotto
                    }
                    break;
                }
                case EROSIONE:
                    if (!target.activeEffects.isEmpty()) {
                        int removeIdx = rng.nextInt(target.activeEffects.size());
                        target.activeEffects.remove(removeIdx);
                        i = Math.min(i, target.activeEffects.size() - 1);
                        continue; // già rimosso, non scalare durata
                    }
                    break;
                default:
                    break;
            }
            // Scala durata
            if (ae.duration > 0) {
                ae.duration--;
                if (ae.duration == 0) {
                    target.activeEffects.remove(i);
                }
            } else if (ae.duration == 0) {
                target.activeEffects.remove(i);
            }
            // duration == -1 → permanente, non rimuovere
        }
        return damage;
    }

    // ═════════════════════════════════════════════
    //  MODIFICATORI DA STATUS (precisione / mira)
    // ═════════════════════════════════════════════

    /** Combina Stordimento/Accecamento/Deviazione sulla precisione di chi agisce. */
    public static double precisionMultiplier(Entity e) {
        double mult = 1.0;
        if (hasEffect(e, StatusEffect.STORDIMENTO)) mult *= 0.25; // -75%
        if (hasEffect(e, StatusEffect.ACCECAMENTO)) mult *= 0.90; // -10%
        if (hasEffect(e, StatusEffect.DEVIAZIONE))  mult *= 0.50; // -50%
        return mult;
    }

    /** Accecamento/Polverizzazione: chi le porta non può mirare con abilità a distanza. */
    public static boolean isRangedBlocked(Entity attacker, String abilityId) {
        boolean blocked = hasEffect(attacker, StatusEffect.ACCECAMENTO)
                || hasEffect(attacker, StatusEffect.POLVERIZZAZIONE);
        return blocked && Ability.isRanged(abilityId);
    }

    /** Deviazione: il bersaglio devia gli attacchi a distanza in arrivo (miss automatico). */
    public static boolean isDeflected(Entity defender, String abilityId) {
        return hasEffect(defender, StatusEffect.DEVIAZIONE) && Ability.isRanged(abilityId);
    }

    // ═════════════════════════════════════════════
    //  QUERY EFFETTI
    // ═════════════════════════════════════════════

    public static boolean hasEffect(Entity e, StatusEffect effect) {
        for (ActiveEffect ae : e.activeEffects) {
            if (ae.effect == effect) return true;
        }
        return false;
    }

    /**
     * Aggiunge un effetto — se già presente sul bersaglio, ne rinfresca solo la durata invece
     * di duplicarlo (importante per Ramificazione: una singola istanza i cui stacks crescono
     * nel tempo, non tante istanze separate).
     */
    public static void addEffect(Entity e, StatusEffect effect, int duration) {
        if (effect == StatusEffect.NONE) return;
        // Esposizione: +1 durata a nuovi effetti negativi
        if (hasEffect(e, StatusEffect.ESPOSIZIONE) && isNegativeEffect(effect)) {
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
            case RESISTENZA:
            case DEVIAZIONE:
            case ABRASIONE:
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
