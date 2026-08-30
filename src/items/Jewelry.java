package items;

import entity.StatType;

/**
 * Un gioiello. I campi grezzi vengono tradotti in bonus % reali da computeBonusPercent()
 * (l'"add components", vedi Item) quando l'oggetto viene equipaggiato.
 *
 * "solo rocce, no gemme" per i bracciali: gemma è usata da Corona/Collana/Anello, rocce SOLO
 * dai Bracciali — sono esclusivi tra loro, un oggetto valorizza uno dei due secondo jewelryType
 * (tenuti entrambi come campo per semplicità, invece di due sottoclassi separate: dato che
 * l'aggregazione vera è ancora da fare, non mi è sembrato il momento di irrigidire la gerarchia).
 *
 * Anelli e bracciali hanno 2 slot ciascuno (Ring1/Ring2, Bracelet1/Bracelet2) — quale dei due
 * usare non è deciso qui: il costruttore mette il primo (Ring1/Bracelet1) di default, sposta
 * manualmente .slot sul secondo se il primo è già occupato (non c'è ancora un
 * "equipaggia nel primo slot libero" automatico).
 */
public class Jewelry extends Item {

    public enum JewelryType { CORONA, COLLANA, ANELLO, BRACCIALE }

    public JewelryType jewelryType;
    public String legame;  // tipo di vantaggio (es. "tipo le corde dei bracciali") — non numerico, nessun bonus stat
    public int metalli;    // bonus stat — nessuna stat specifica indicata, vedi computeBonusPercent()

    public Component   gemma; // effetti speciali — usata da CORONA/COLLANA/ANELLO, null per BRACCIALE
    public final Component[] rocce = new Component[9]; // effetti speciali in combo — solo BRACCIALE, resta vuoto per gli altri tipi

    public Jewelry(JewelryType jewelryType) {
        this.category     = ItemCategory.GIOIELLO;
        this.jewelryType  = jewelryType;
        this.slot = switch (jewelryType) {
            case CORONA    -> ItemSlot.Head;
            case COLLANA   -> ItemSlot.Neck;
            case ANELLO    -> ItemSlot.Ring1;
            case BRACCIALE -> ItemSlot.Bracelet1;
        };
    }

    /**
     * "Add components": metalli -> EFFICIENZA. Scelta interpretativa — "bonus stat" nella tua
     * spec non indicava QUALE stat, ho preso EFFICIENZA come bonus generico "di potenza" dato
     * che non era già usata pesantemente altrove; cambiala se intendevi un'altra stat.
     */
    @Override
    public void computeBonusPercent() {
        statBonusPercent.clear();
        if (metalli != 0) statBonusPercent.merge(StatType.EFFICIENZA, metalli, Integer::sum);
        if (gemma != null) addComponents(new Component[]{gemma});
        addComponents(rocce);
    }
}
