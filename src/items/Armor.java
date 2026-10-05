package items;

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

    // rifiniture è un LIVELLO (non un ID): scala di poco tutti i bonus del pezzo — vedi
    // Item.applyQualityLevel(). Gli altri campi sono component: nomecomponent[slot] = ID,
    // risolto da ComponentRegistry (famiglia ARMOR).
    public int rifiniture;
    public final int[] metalli   = new int[3]; // -> DIFESA, fino a 3, sommati
    public final int[] legamenti = new int[1]; // -> VELOCITA
    public final int[] sostegno  = new int[1]; // nessuna tabella ancora

    // ── Set ───────────────────────────────────────────────────────
    // Pezzi upper (Helmet..Cuirasse): upperID 1=Guardia 2=Veggente 3=Voto, lowerID 0.
    // Gambe (Cuisse..Sabaton): lowerID 1=Passo 2=Ombra, upperID 0, e 2 acceptedUpperIDs (su 3):
    // il bonus set si attiva solo se gli upper indossati hanno uno di quei due upperID.
    // Il fullSetOrStyleStub (Item) di una gamba vale per acceptedUpperIDs[0], fullSetStubAlt
    // per acceptedUpperIDs[1].
    public int upperID;
    public int lowerID;
    public final int[] acceptedUpperIDs = new int[2];
    public String fullSetStubAlt;

    /** fullID = upperID e lowerID uno dopo l'altro (Guardia+Passo = 11, Voto+Ombra = 32). */
    public static int fullID(int upperID, int lowerID) { return upperID * 10 + lowerID; }

    public boolean acceptsUpperID(int upperID) {
        return upperID == acceptedUpperIDs[0] || upperID == acceptedUpperIDs[1];
    }

    /** Testo full set di questo pezzo quando gli upper indossati hanno 'upperID' (null se non c'è). */
    public String fullSetStubFor(int upperID) {
        if (lowerID == 0 || upperID == acceptedUpperIDs[0]) return fullSetOrStyleStub;
        return upperID == acceptedUpperIDs[1] ? fullSetStubAlt : null;
    }

    public final Component[] incantesimi = new Component[2]; // "[1] ovvero 2 incantesimi"

    public Armor(ArmorType armorType, WeightClass weightClass) {
        this.category    = ItemCategory.ARMATURA;
        this.armorType   = armorType;
        this.weightClass = weightClass;
        this.tipologie   = DamageTypes.defensiveDefault();
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
     * "Add components": metalli -> DIFESA, legamenti -> VELOCITA via ComponentRegistry (famiglia
     * ARMOR), poi applyQualityLevel(rifiniture). Durabilità 100; a 0 nessun bonus, danneggiata
     * perde DIFESA in proporzione.
     */
    @Override
    public void computeBonusPercent() {
        clearComputedBonuses();
        addComponentBonus(ComponentRegistry.Family.ARMOR, ComponentRegistry.Part.METALLI, metalli, 0);
        addComponentBonus(ComponentRegistry.Family.ARMOR, ComponentRegistry.Part.LEGAMENTI, legamenti, 0);
        addComponentBonus(ComponentRegistry.Family.ARMOR, ComponentRegistry.Part.SOSTEGNO, sostegno, 0);
        initDurability();
        addComponents(incantesimi);
        applyQualityLevel(rifiniture);
        applyDurabilityState(true);
    }
}
