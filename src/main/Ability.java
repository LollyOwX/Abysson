package main;

import entity.Entity;

/**
 * Classe statica che raccoglie tutte le abilità del gioco
 * Per aggiungere una nuova abilità:
 *   1. Aggiungi un metodo privato statico qui sotto con la formula del danno
 *   2. Aggiungi il case in use() e in getName()
 *   3. In Entity (o nella sottoclasse): unlockedAbilities.add("NomeAbility")
 *   4. Opzionale: abilityWeights.put("NomeAbility", peso) per l'AI del mostro
 */


public class Ability {
    public static int use(String id, Entity user, Entity target) {
        switch (id) {
            case "NormalAttack": return normalAttack(user, target);
            case "PowerStrike":  return powerStrike(user, target);
            case "Thunderbolt":  return thunderbolt(user, target);
            default:
                System.err.println("Ability non trovata: " + id);
                return 1;
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

    //  ABILITÀ

    /** Attacco base: attack - defense (min 1) */
    private static int normalAttack(Entity user, Entity target) {
        return Math.max(1, user.attack - target.defense);
    }
    /** Colpo potente: attack*2 - defense (min 1) */
    private static int powerStrike(Entity user, Entity target) {
        return Math.max(1, (user.attack * 2) - target.defense);
    }
    /** Fulmine: ignora completamente la difesa del target */
    private static int thunderbolt(Entity user, Entity target) {
        return Math.max(1, user.attack * 3);
    }
}
