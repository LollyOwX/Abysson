package items;

import java.util.ArrayList;
import java.util.List;

/**
 * STUB — solo le due liste, nessuna logica ancora (né aggiunta, né rimozione, né equip-da-qui,
 * né raccolta dal mondo). Tenuto da Player quando si passa a costruirlo davvero.
 *
 * Liste separate come deciso: gli oggetti EQUIPAGGIABILI (Weapon/Armor/Jewelry) e quelli PURI
 * (es. una Key raccolta dal mondo, senza equip) non stanno nella stessa lista.
 */
public class Inventory {
    public final List<Item> equippables = new ArrayList<>(); // Weapon/Armor/Jewelry posseduti ma non equipaggiati
    public final List<String> pureItems  = new ArrayList<>(); // oggetti "puri" per nome (es. "Key") — nessuna quantità/stacking ancora
}
