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

    // rifiniture è un LIVELLO (non un ID): moltiplica il bonus totale, non lo genera — vedi
    // Item.applyQualityLevel(). metallo/legamenti sono ID: il numero non è più il bonus stesso,
    // è una chiave in MaterialRegistry che lo risolve. sostegno resta un ID "pronto" ma senza
    // tabella ancora.
    public int rifiniture; // livello: 0.25x di moltiplicatore per livello sul bonus totale del pezzo
    public int metallo;    // ID -> DIFESA (MaterialRegistry.armorMetallo) — stat principale: resistenza ai danni
    public int sostegno;   // ID senza tabella — durabilità, nessuna stat "durabilità" esiste ancora
    public int legamenti;  // ID -> VELOCITA (MaterialRegistry.armorLegamenti) — stat principale: mobilità

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
     * "Add components": metallo -> DIFESA ("resistenza ai danni"), legamenti -> VELOCITA
     * ("mobilità" — scelta interpretativa, poteva essere ELUSIONE altrettanto ragionevolmente),
     * entrambi via il proprio metodo in MaterialRegistry. sostegno non produce bonus (vedi
     * commento sul campo sopra — nessuna tabella ancora). rifiniture chiude il calcolo
     * moltiplicando quello che è stato appena messo in statBonusPercent (vedi
     * Item.applyQualityLevel()).
     */
    @Override
    public void computeBonusPercent() {
        clearComputedBonuses();
        int difBonus = MaterialRegistry.armorMetallo(metallo);
        if (difBonus != 0) statBonusPercent.merge(StatType.DIFESA, difBonus, Integer::sum);
        int velBonus = MaterialRegistry.armorLegamenti(legamenti);
        if (velBonus != 0) statBonusPercent.merge(StatType.VELOCITA, velBonus, Integer::sum);
        addComponents(incantesimi);
        applyQualityLevel(rifiniture);
    }
}
