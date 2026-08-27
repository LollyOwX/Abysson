package combat;

import combat.ElementSystem.*;
import entity.Entity;

public class Ability {

    public static int use(String id, Entity user, Entity target) {
        int atk = offense(id, user);
        int def = defense(id, target);
        switch (id) {
            case "NormalAttack": return Math.max(10, atk - def);
            case "PowerStrike":  return Math.max(10, (atk * 2) - def);
            case "Thunderbolt":  return Math.max(10, (atk * 2) - def);
            case "AcquaJet":     return Math.max(10, (atk * 2) - def);
            case "Earthshock":   return Math.max(10, (atk * 2) - def);
            case "Fireblade":    return Math.max(10, (atk * 2) - def);
            case "Lightray":     return Math.max(10, (atk * 2) - def);
            default: System.err.println("Ability non trovata: " + id); return 1;
        }
    }

    public static String getName(String id) {
        switch (id) {
            case "NormalAttack": return "Attacco Normale";
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
            case "PowerStrike": return Element.FISICO;
            case "Thunderbolt": return Element.FULMINE;
            case "AcquaJet": return Element.ACQUA;
            case "Earthshock": return Element.TERRA;
            case "Fireblade": return Element.FUOCO;
            case "Lightray": return Element.LUCE;
            default: return Element.NONE;
        }
    }

    /** true se l'abilità usa ElementoATK/ElementDEF invece di Attack/Difesa fisici. */
    public static boolean isElemental(String id) {
        Element e = getElement(id);
        return e != Element.NONE && e != Element.FISICO;
    }

    /**
     * true se l'abilità colpisce "a distanza" — usato da Accecamento/Polverizzazione
     * (bloccano la mira a distanza) e da Deviazione (devia i proiettili).
     * Scelta di design esplicita, non presente nel codice originale: il gioco non aveva
     * finora una nozione di mischia/distanza per abilità, quindi la classificazione qui
     * sotto è un'assunzione ragionevole (Thunderbolt/AcquaJet/Lightray = a distanza,
     * il resto = da mischia) — verifica che rispecchi il design che avevi in mente.
     */
    public static boolean isRanged(String id) {
        switch (id) {
            case "Thunderbolt":
            case "AcquaJet":
            case "Lightray":
                return true;
            default:
                return false;
        }
    }

    /**
     * Azione speciale della singola abilità, eseguita da CombatState.dealDamage() dopo il
     * danno (vedi SpecialAction). STUB: nessuna abilità ne ha ancora una.
     */
    public static SpecialAction getSpecialAction(String id) {
        return null;
    }

    private static int offense(String id, Entity user) {
        Element e = getElement(id);
        return isElemental(id) ? user.getElementAttack(e) : user.attack;
    }

    private static int defense(String id, Entity target) {
        Element e = getElement(id);
        return isElemental(id) ? target.getElementDefense(e) : target.defense;
    }
}
