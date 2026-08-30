package items;

import entity.StatType;

/**
 * Un'armatura. I 12 tipi mappano 1:1 sui 12 slot armatura, tutti distinti dai Gioielli — Helmet
 * e Gorget NON condividono nulla con Corona/Collana (vedi Item.ItemSlot: Weapon/Armor/Jewelry
 * restano completamente separati tra loro). I campi grezzi vengono tradotti in bonus % reali
 * da computeBonusPercent() (l'"add components", vedi Item) quando l'oggetto viene equipaggiato.
 */
public class Armor extends Item {

    public enum ArmorType {
        HELMET, GORGET, PAULDRON, REREBRACE, COUTER, VANBRACE, GAUNTLET,
        CUIRASSE, CUISSE, POLEYN, GREAVE, SABATON
    }

    // Leggera/Media/Pesante — usata dalla penetrazione delle armi a distanza (vedi Weapon.
    // penetratesUpTo — struttura pronta, il meccanismo vero non è collegato, vedi STATUS.md).
    public enum WeightClass { LEGGERA, MEDIA, PESANTE }

    public final ArmorType armorType;
    public WeightClass weightClass;

    public int rifiniture; // livello — non tradotto in stat, vedi computeBonusPercent()
    public int metallo;    // stat principale: resistenza ai danni (difesa e altro)
    public int sostegno;   // stat principale: durabilità — non tradotto in stat, nessuna stat "durabilità" esiste ancora
    public int legamenti;  // stat principale: mobilità

    public final Component[] incantesimi = new Component[2]; // "[1] ovvero 2 incantesimi"

    public Armor(ArmorType armorType, WeightClass weightClass) {
        this.category    = ItemCategory.ARMATURA;
        this.armorType   = armorType;
        this.weightClass = weightClass;
        this.slot = switch (armorType) {
            case HELMET    -> ItemSlot.Helmet;
            case GORGET    -> ItemSlot.Gorget;
            case PAULDRON  -> ItemSlot.Pauldron;
            case REREBRACE -> ItemSlot.Rerebrace;
            case COUTER    -> ItemSlot.Couter;
            case VANBRACE  -> ItemSlot.Vanbrace;
            case GAUNTLET  -> ItemSlot.Gauntlet;
            case CUIRASSE  -> ItemSlot.Cuirasse;
            case CUISSE    -> ItemSlot.Cuisse;
            case POLEYN    -> ItemSlot.Poleyn;
            case GREAVE    -> ItemSlot.Greave;
            case SABATON   -> ItemSlot.Sabaton;
        };
    }

    /**
     * "Add components": metallo -> DIFESA ("resistenza ai danni"); legamenti -> VELOCITA
     * ("mobilità" — scelta interpretativa, poteva essere ELUSIONE altrettanto ragionevolmente).
     * rifiniture/sostegno non producono bonus (vedi commenti sui campi sopra).
     */
    @Override
    public void computeBonusPercent() {
        statBonusPercent.clear();
        if (metallo != 0)   statBonusPercent.merge(StatType.DIFESA, metallo, Integer::sum);
        if (legamenti != 0) statBonusPercent.merge(StatType.VELOCITA, legamenti, Integer::sum);
        addComponents(incantesimi);
    }
}
