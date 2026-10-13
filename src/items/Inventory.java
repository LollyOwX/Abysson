package items;

import java.util.ArrayList;
import java.util.List;

/**
 * Tutto ciò che il Player possiede, diviso per zona dell'Inventario nel libro: equipaggiamento
 * (posseduto ma non indossato — quello indossato lo aggiunge la UI), consumabili, preziosi,
 * oggetti di quest e risorse.
 */
public class Inventory {
    public final List<Item> gear = new ArrayList<>();
    public final List<Consumable> consumables = new ArrayList<>();
    public final List<Precious> precious = new ArrayList<>();
    public final List<QuestItem> questItems = new ArrayList<>();
    public final List<Resource> resources = new ArrayList<>();

    public void addGear(Item item) {
        if (item == null) return;
        item.computeBonusPercent();
        gear.add(item);
    }
}
