package combat;

import combat.ElementSystem.*;
import entity.Entity;

public class Ability {

    public static int use(String id, Entity user, Entity target) {
        int atk = offense(id, user);
        int def = defense(id, target);
        switch (id) {
            case "NormalAttack": return CombatMath.mitigate(atk, def, user.level);
            case "PowerStrike":  return CombatMath.mitigate(atk * 2, def, user.level);
            case "Thunderbolt":  return CombatMath.mitigate(atk * 2, def, user.level);
            case "AcquaJet":     return CombatMath.mitigate(atk * 2, def, user.level);
            case "Earthshock":   return CombatMath.mitigate(atk * 2, def, user.level);
            case "Fireblade":    return CombatMath.mitigate(atk * 2, def, user.level);
            case "Lightray":     return CombatMath.mitigate(atk * 2, def, user.level);
            default: System.err.println("Ability not found: " + id); return 1;
        }
    }

    public static String getName(String id) {
        switch (id) {
            case "NormalAttack": return "Normal Attack";
            case "PowerStrike": return "Power Strike";
            case "Thunderbolt": return "Thunderbolt";
            case "AcquaJet": return "AcquaJet";
            case "Earthshock": return "Earthshock";
            case "Fireblade": return "Fireblade";
            case "Lightray" : return "Lightray";
            default: return id;
        }
    }

    public static Element getElement(String id) {
        switch (id) {
            case "NormalAttack": return Element.NONE;
            case "PowerStrike": return Element.PHYSICAL;
            case "Thunderbolt": return Element.ELECTRIC;
            case "AcquaJet": return Element.WATER;
            case "Earthshock": return Element.EARTH;
            case "Fireblade": return Element.FIRE;
            case "Lightray": return Element.LIGHT;
            default: return Element.NONE;
        }
    }

    /** true se l'abilità usa ElementoATK/ElementDEF invece di Attack/Difesa fisici. */
    public static boolean isElemental(String id) {
        Element e = getElement(id);
        return e != Element.NONE && e != Element.PHYSICAL;
    }

    // Tag "a distanza": usato da Accecamento/Polverizzazione (bloccano la mira a distanza) e da Deviazione (devia i proiettili).
    private static final java.util.Set<String> RANGED = java.util.Set.of("Thunderbolt", "AcquaJet", "Lightray");

    public static boolean isRanged(String id) {
        return RANGED.contains(id);
    }

    /**
     * Azione speciale della singola abilità, eseguita da CombatState.dealDamage() dopo il
     * danno (vedi SpecialEffect). Delega a SpecialEffectRegistry — STUB: nessun id di abilità
     * è ancora registrato lì, quindi ritorna sempre null finché non aggiungi un case.
     */
    public static SpecialEffect getSpecialAction(String id) {
        return SpecialEffectRegistry.get(id);
    }

    private static int offense(String id, Entity user) {
        Element e = getElement(id);
        if (isElemental(id)) return user.getElementAttack(e);
        if (id.equals("NormalAttack") && user instanceof entity.Player p
                && p.mainHandSlot.item instanceof items.Weapon w) {
            return w.baseDamage(user.attack);
        }
        return user.attack;
    }

    private static int defense(String id, Entity target) {
        Element e = getElement(id);
        return isElemental(id) ? target.getElementDefense(e) : target.defense;
    }
}
