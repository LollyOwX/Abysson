package combat;

import combat.ElementSystem.*;
import entity.Entity;

public class Ability {

    public static int use(String id, Entity user, Entity target) {
        switch (id) {
            case "NormalAttack": return normalAttack(user, target);
            case "PowerStrike": return powerStrike(user, target);
            case "Thunderbolt": return thunderbolt(user, target);
            case "AcquaJet": return acquajet(user, target);
            case "Earthshock": return earthschock(user, target);
            case "Fireblade": return fireblade(user, target);
            case "Lightray": return lightray(user, target);
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

    // ── Abilità ───────────────────────────────────

    private static int normalAttack(Entity user, Entity target) {
        return Math.max(10, user.attack - target.defense);
    }
    private static int powerStrike(Entity user, Entity target) {return Math.max(10, (user.attack * 2) - target.defense);}
    private static int thunderbolt(Entity user, Entity target) {return Math.max(10, user.attack * 2) - target.defense;}
    private static int acquajet(Entity user, Entity target) {return Math.max(10, user.attack * 2) -  target.defense;}
    private static int earthschock(Entity user, Entity target) {return Math.max(10, user.attack * 2) - target.defense;}
    private static int fireblade(Entity user, Entity target) {return Math.max(10, user.attack * 2) - target.defense;}
    private static int lightray(Entity user, Entity target) {return Math.max(10, target.defense * 2) - user.attack;}
}