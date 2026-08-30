package items;

import entity.StatType;

/**
 * Un'arma. I campi restano dati grezzi tradotti in bonus % reali da computeBonusPercent()
 * (l'"add components", vedi Item) quando l'oggetto viene equipaggiato. Le probabilità
 * (disarmChance, stunChance, counterattackChance) e comboTypes/penetratesUpTo non hanno un
 * default "giusto": restano a 0/null finché non li imposta chi crea l'istanza vera (vedi
 * WeaponRegistry) — i numeri lì dentro sono di esempio, non un bilanciamento reale.
 */
public class Weapon extends Item {

    public enum WeaponCategory { SPADE, MAZZE, LANCE, ARMI_A_DISTANZA, DIFENSIVE }

    public enum WeaponSubtype {
        SPADA_CORTA(WeaponCategory.SPADE),
        SPADA_LUNGA(WeaponCategory.SPADE),
        FRUSTA(WeaponCategory.SPADE),
        MAZZA_CHIODATA(WeaponCategory.MAZZE),
        ASCIA(WeaponCategory.MAZZE),
        SPADONE(WeaponCategory.MAZZE),
        LANCIA_LUNGA(WeaponCategory.LANCE),
        PICCA(WeaponCategory.LANCE),
        FALCE(WeaponCategory.LANCE),
        ARCO_CORTO(WeaponCategory.ARMI_A_DISTANZA),
        ARCO_LUNGO(WeaponCategory.ARMI_A_DISTANZA),
        BALESTRA(WeaponCategory.ARMI_A_DISTANZA),
        COLTELLO_DA_LANCIO(WeaponCategory.ARMI_A_DISTANZA),
        SCUDO(WeaponCategory.DIFENSIVE),
        BROQUEL(WeaponCategory.DIFENSIVE),
        SAI(WeaponCategory.DIFENSIVE);

        public final WeaponCategory category;
        WeaponSubtype(WeaponCategory category) { this.category = category; }
    }

    /** I 3 tipi di danno grezzo — usati da comboTypes sotto (Mazza chiodata combina
     *  PERFORANTE+CONTUNDENTE, Ascia TAGLIO+CONTUNDENTE). */
    public enum DamageType { TAGLIO, CONTUNDENTE, PERFORANTE }

    public final WeaponSubtype subtype;

    public int affilatezza; // livello
    public int pomo;        // "Butt" nella tua spec — tradotto come "pomo": velocità. Correggimi il nome se intendevi altro.
    public int manico;      // controllo: probabilità di non essere disarmato — NON tradotto in stat: è la difesa contro il disarmo altrui, non implementata (vedi STATUS.md)
    public int guardia;     // difesa della mano (- danni alle braccia — gestiremo dopo, per ora solo il numero)
    public int taglio;      // danni da taglio — prima insieme a contundente in un unico "lama", separati per il combo di Mazza chiodata/Ascia
    public int contundente; // danni contundenti — vedi sopra
    public int punta;       // danni perforanti
    public int metallo;     // durabilità lama — non tradotto in stat, nessuna stat "durabilità" esiste ancora
    public int legamenti;   // durabilità arma globale — vedi sopra
    public int peso;        // Spadone: il danno usa questo invece di taglio/contundente/punta — utile anche per Scudo/Broquel (largo,lento / piccolo,veloce)

    public int disarmChance;        // % probabilità di disarmare l'avversario — Frusta
    public int stunChance;          // % probabilità di stordire 1 turno — Falce (riusa StatusEffect.STORDIMENTO)
    public int counterattackChance; // % probabilità di contrattaccare — Coltello da lancio
    public DamageType[] comboTypes; // null = nessun combo; altrimenti i 2 tipi che questa arma combina (Mazza chiodata, Ascia)

    // "perforazione no armor" (Arco corto) / "perforazione heavy armor" (Arco lungo): null =
    // nessuna penetrazione speciale. STRUTTURA SOLO — il meccanismo vero (ignorare la DIFESA
    // data da pezzi d'armatura con questo weightClass o inferiore) non è collegato in
    // CombatState: la DIFESA oggi è un unico numero aggregato sul Player, non tracciata pezzo
    // per pezzo, quindi "ignora l'armatura leggera" non ha ancora un modo pulito di applicarsi
    // colpo per colpo. Vedi STATUS.md.
    public Armor.WeightClass penetratesUpTo;

    public final Component[] gemme       = new Component[3]; // bonus vari, effetto per gemma
    public final Component[] incantesimi = new Component[2]; // effetti speciali (come SpecialAction)

    public Weapon(WeaponSubtype subtype) {
        this.category = ItemCategory.ARMA;
        this.subtype  = subtype;
        // Difensive (Scudo/Broquel/Sai) di default in OffHand — "Sai" è letteralmente "spada
        // secondaria". Dual wield/mano diversa: sposta .slot manualmente dopo la creazione.
        this.slot = (subtype.category == WeaponCategory.DIFENSIVE) ? ItemSlot.OffHand : ItemSlot.MainHand;
    }

    public WeaponCategory weaponCategory() {
        return subtype.category;
    }

    /**
     * "Add components": taglio+contundente+punta+peso -> ATTACK (i 4 numeri "di danno" sommati,
     * scelta di semplicità: peso conta quanto un danno normale, non lo sostituisce, così lo
     * Spadone non ha bisogno di un ramo a parte); pomo -> VELOCITA; guardia -> DIFESA. manico/
     * metallo/legamenti non producono bonus (vedi i commenti sui campi sopra). Scelte
     * interpretative, non numeri o formule che mi hai dato tu: correggile se non vanno bene.
     */
    @Override
    public void computeBonusPercent() {
        statBonusPercent.clear();
        int atkBonus = taglio + contundente + punta + peso;
        if (atkBonus != 0) statBonusPercent.merge(StatType.ATTACK, atkBonus, Integer::sum);
        if (pomo != 0)     statBonusPercent.merge(StatType.VELOCITA, pomo, Integer::sum);
        if (guardia != 0)  statBonusPercent.merge(StatType.DIFESA, guardia, Integer::sum);
        addComponents(gemme);
        addComponents(incantesimi);
    }
}

