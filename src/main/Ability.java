package main;
import main.ElementSystem.*;
import entity.Entity;

/**
 * Raccoglie tutte le abilità del gioco.
 *
 * Per aggiungere una nuova abilità:
 *   1. Aggiungi metodo privato statico con la formula del danno
 *   2. Aggiungi case in use(), getName(), getElement()
 *   3. In Entity (o sottoclasse): unlockedAbilities.add("NomeAbility")
 *   4. Opzionale: abilityWeights.put("NomeAbility", peso)
 */
public class Ability {

    public static int use(String id, Entity user, Entity target) {
        switch (id) {
            case "NormalAttack": return normalAttack(user, target);
            case "PowerStrike": return powerStrike(user, target);
            case "Thunderbolt": return thunderbolt(user, target);
            case "AcquaJet": return acquajet(user, target);
            case "Earthshock": return earthschock(user, target);
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
            default: return Element.NONE;
        }
    }

    // ── Abilità ───────────────────────────────────

    private static int normalAttack(Entity user, Entity target) {
        return Math.max(1, user.attack - target.defense);
    }
    private static int powerStrike(Entity user, Entity target) {return Math.max(1, (user.attack * 2) - target.defense);}
    private static int thunderbolt(Entity user, Entity target) {return Math.max(1, user.attack * 2) - target.defense;}
    private static int acquajet(Entity user, Entity target) {return Math.max(1, user.attack * 2) -  target.defense;}
    private static int earthschock(Entity user, Entity target) {return Math.max(1, user.attack * 2) - target.defense;}
}