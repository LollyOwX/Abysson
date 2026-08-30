package items;

import items.Armor.ArmorType;
import items.Armor.WeightClass;

/**
 * Dispatcher statico per le armature — stesso pattern di WeaponRegistry. UN oggetto per
 * ciascuno dei 12 tipi, così c'è content da testare per tutti. Numeri placeholder di esempio.
 */
public class ArmorRegistry {
    public static Armor get(String id) {
        switch (id) {
            case "helmet_basic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo Semplice"; a.description = "Protezione base per la testa.";
                a.rifiniture = 1; a.metallo = 6; a.sostegno = 30; a.legamenti = 3;
                return a;
            }
            case "gorget_basic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera Semplice"; a.description = "Protezione base per la gola.";
                a.rifiniture = 1; a.metallo = 4; a.sostegno = 25; a.legamenti = 2;
                return a;
            }
            case "pauldron_basic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio Semplice"; a.description = "Protezione base per le spalle.";
                a.rifiniture = 1; a.metallo = 7; a.sostegno = 35; a.legamenti = 2;
                return a;
            }
            case "rerebrace_basic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore Semplice"; a.description = "Protezione base per il braccio superiore.";
                a.rifiniture = 1; a.metallo = 5; a.sostegno = 30; a.legamenti = 2;
                return a;
            }
            case "couter_basic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera Semplice"; a.description = "Protezione base per il gomito.";
                a.rifiniture = 1; a.metallo = 3; a.sostegno = 20; a.legamenti = 3;
                return a;
            }
            case "vanbrace_basic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio Semplice"; a.description = "Protezione base per l'avambraccio.";
                a.rifiniture = 1; a.metallo = 4; a.sostegno = 22; a.legamenti = 3;
                return a;
            }
            case "gauntlet_basic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto Semplice"; a.description = "Protezione base per la mano.";
                a.rifiniture = 1; a.metallo = 5; a.sostegno = 25; a.legamenti = 1;
                return a;
            }
            case "cuirasse_basic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza Semplice"; a.description = "Protezione base per il torace, il pezzo principale.";
                a.rifiniture = 1; a.metallo = 10; a.sostegno = 50; a.legamenti = 5;
                return a;
            }
            case "cuisse_basic": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale Semplice"; a.description = "Protezione base per la coscia.";
                a.rifiniture = 1; a.metallo = 7; a.sostegno = 35; a.legamenti = 3;
                return a;
            }
            case "poleyn_basic": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera Semplice"; a.description = "Protezione base per il ginocchio.";
                a.rifiniture = 1; a.metallo = 4; a.sostegno = 22; a.legamenti = 3;
                return a;
            }
            case "greave_basic": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera Semplice"; a.description = "Protezione base per lo stinco.";
                a.rifiniture = 1; a.metallo = 6; a.sostegno = 30; a.legamenti = 2;
                return a;
            }
            case "sabaton_basic": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa Semplice"; a.description = "Protezione base per il piede.";
                a.rifiniture = 1; a.metallo = 3; a.sostegno = 20; a.legamenti = 4;
                return a;
            }
            default:
                System.err.println("Armor non trovata: " + id);
                return null;
        }
    }
}
