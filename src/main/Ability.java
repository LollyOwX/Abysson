package main;

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
            case "PowerStrike":  return powerStrike(user, target);
            case "Thunderbolt":  return thunderbolt(user, target);
            default: System.err.println("Ability non trovata: " + id); return 1;
        }
    }

    public static String getName(String id) {
        switch (id) {
            case "NormalAttack": return "Attacco Normale";
            case "PowerStrike":  return "Colpo Potente";
            case "Thunderbolt":  return "Fulmine";
            default:             return id;
        }
    }

    /** Elemento dell'abilità — usato da ElementSystem per calcolare reazioni */
    public static Element getElement(String id) {
        switch (id) {
            case "NormalAttack": return Element.FISICO;
            case "PowerStrike":  return Element.FISICO;
            case "Thunderbolt":  return Element.FULMINE;
            default:             return Element.NONE;
        }
    }

    // ── Abilità ───────────────────────────────────

    private static int normalAttack(Entity user, Entity target) {
        return Math.max(1, user.attack - target.defense);
    }

    private static int powerStrike(Entity user, Entity target) {
        return Math.max(1, (user.attack * 2) - target.defense);
    }

    private static int thunderbolt(Entity user, Entity target) {
        return Math.max(1, user.attack * 3);
    }

    /*
     * ── TEMPLATE ────────────────────────────────────────────────
     * private static int nomeAbility(Entity user, Entity target) {
     *     return Math.max(1, ... );
     * }
     * Aggiungi in use(), getName(), getElement().
     * ────────────────────────────────────────────────────────────
     */
}
