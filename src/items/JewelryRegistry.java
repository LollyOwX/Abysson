package items;

import items.Jewelry.JewelryType;

/**
 * Dispatcher statico per i gioielli — stesso pattern di WeaponRegistry. UN oggetto per
 * ciascuno dei 4 tipi. Il bracciale usa rocce (non gemma) come da regola "solo rocce, no gemme".
 */
public class JewelryRegistry {
    public static Jewelry get(String id) {
        switch (id) {
            case "crown_basic": {
                Jewelry j = new Jewelry(JewelryType.CORONA);
                j.name = "Corona Semplice"; j.description = "Un cerchio d'oro, niente di che.";
                j.legame = "nessuno"; j.metalli = 4;
                j.gemma = new Component("ruby_shard");
                return j;
            }
            case "necklace_basic": {
                Jewelry j = new Jewelry(JewelryType.COLLANA);
                j.name = "Collana Semplice"; j.description = "Una catenina con un ciondolo.";
                j.legame = "nessuno"; j.metalli = 3;
                return j;
            }
            case "iron_ring_basic": {
                Jewelry j = new Jewelry(JewelryType.ANELLO);
                j.name = "Anello di Ferro"; j.description = "Un semplice anello di ferro, niente di speciale.";
                j.legame = "nessuno"; j.metalli = 2;
                return j;
            }
            case "bracelet_basic": {
                Jewelry j = new Jewelry(JewelryType.BRACCIALE);
                j.name = "Bracciale di Corda"; j.description = "Corde annodate con delle pietre incastonate.";
                j.legame = "corde intrecciate"; j.metalli = 1;
                j.rocce[0] = new Component("swift_stone");
                j.rocce[1] = new Component("iron_vein_stone");
                return j;
            }
            default:
                System.err.println("Jewelry non trovata: " + id);
                return null;
        }
    }
}
