package items;

import combat.WeaponHitboxBuilder;
import entity.StatType;

import javax.imageio.ImageIO;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    // affilatezza è un LIVELLO (non un ID): moltiplica il bonus totale, non lo genera — vedi
    // Item.applyQualityLevel(). Tutti gli altri campi qui sotto sono ID: il numero non è più il
    // bonus stesso, è una chiave in MaterialRegistry che lo risolve (stesso id = stesso effetto
    // su qualunque arma). manico/metallo/legamenti restano ID "pronti" ma senza tabella ancora.
    public int affilatezza; // livello: 0.25x di moltiplicatore per livello sul bonus totale dell'arma
    public int pomo;        // ID -> VELOCITA (MaterialRegistry.weaponPomo) — "Butt" nella tua spec, tradotto come "pomo". Correggimi il nome se intendevi altro.
    public int manico;      // ID senza tabella — controllo/probabilità di non essere disarmato, non implementata (vedi STATUS.md)
    public int guardia;     // ID -> DIFESA (MaterialRegistry.weaponGuardia)
    public int taglio;      // ID -> ATTACK (MaterialRegistry.blade) — prima insieme a contundente in un unico "lama", separati per il combo di Mazza chiodata/Ascia
    public int contundente; // ID -> ATTACK (MaterialRegistry.blade) — vedi sopra
    public int punta;       // ID -> ATTACK (MaterialRegistry.blade)
    public int metallo;     // ID senza tabella — durabilità lama, nessuna stat "durabilità" esiste ancora
    public int legamenti;   // ID senza tabella — durabilità arma globale, vedi sopra
    public int peso;        // ID -> ATTACK (MaterialRegistry.blade) — Spadone: il danno usa questo invece di taglio/contundente/punta; utile anche per Scudo/Broquel (largo,lento / piccolo,veloce)

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

    // ── Geometria di parata (minigioco angolare in CombatState) ─────────────────────
    // Forme in coordinate LOCALI, pivot = impugnatura = origine (0,0), asse verticale = lama a
    // riposo — CombatState le ruota/traduce sullo schermo al momento della parata. Due modi per
    // popolarle: a mano (vedi WeaponRegistry: Scudo/Broquel/Sai, rettangoli placeholder) oppure
    // da un'immagine dettagliata vera via loadRigidZoneFromImage() (solo zone RIGIDE — le zone
    // DEBOLI restano sempre da aggiungere a parte, manualmente).
    public enum ZoneType { RIGID, WEAK }

    public static class DefenseZone {
        public final Shape shape; // coordinate locali, pivot = impugnatura
        public final ZoneType type;
        public DefenseZone(Shape shape, ZoneType type) { this.shape = shape; this.type = type; }
    }

    public final List<DefenseZone> defenseZones = new ArrayList<>();
    public double restAngleDeg = 0;      // orientamento di riposo ("verticale"), specifico per arma
    public boolean canDefend  = false;   // può essere l'arma difensiva nella parata — vero di norma per le Difensive
    public boolean staticGuard = false;  // true SOLO per lo scudo vero: non ruota mai, non può essere eluso

    // itemPath (icona/inventario) è ereditato da Item, condiviso con Armor/Jewelry. Questo è il
    // secondo percorso, specifico di Weapon: la versione "estesa" da cui estrarre la geometria
    // di parata (vedi loadRigidZoneFromImage() sotto) — lo popolano davvero solo le Difensive.
    public String combatItemPath;

    public final Component[] gemme       = new Component[3]; // bonus vari, effetto per gemma
    public final Component[] incantesimi = new Component[2]; // effetti speciali (come SpecialEffect)

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
     * Carica combatItemPath e aggiunge a defenseZones UNA zona RIGIDA costruita da ogni pixel
     * non vuoto dell'immagine (vedi combat.WeaponHitboxBuilder — stesso approccio "shader" di
     * PaletteSwap). Le zone deboli NON sono generate qui: vanno aggiunte a parte. Non fa nulla
     * se combatItemPath è null o la risorsa non si trova (stampa un errore, non blocca).
     * Da richiamare esplicitamente (non automatico nel costruttore: i campi vengono popolati
     * dopo, dai vari Registry).
     */
    public void loadRigidZoneFromImage() {
        if (combatItemPath == null) return;
        try (InputStream is = getClass().getResourceAsStream(combatItemPath)) {
            if (is == null) {
                System.err.println("ERROR: resource not found: " + combatItemPath);
                return;
            }
            BufferedImage image = ImageIO.read(is);
            Area rigid = WeaponHitboxBuilder.getOrBuild(combatItemPath, image);
            defenseZones.add(new DefenseZone(rigid, ZoneType.RIGID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * "Add components": taglio/contundente/punta/peso sono ID che MaterialRegistry.blade()
     * risolve e somma su ATTACK (scelta di semplicità: peso conta quanto un danno normale, non
     * lo sostituisce, così lo Spadone non ha bisogno di un ramo a parte); pomo -> VELOCITA;
     * guardia -> DIFESA, entrambi via il proprio metodo in MaterialRegistry. manico/metallo/
     * legamenti non producono bonus (vedi i commenti sui campi sopra — nessuna tabella ancora).
     * affilatezza chiude il calcolo moltiplicando quello che è stato appena messo in
     * statBonusPercent (vedi Item.applyQualityLevel()). Scelte interpretative, non numeri o
     * formule che mi hai dato tu: correggile se non vanno bene.
     */
    @Override
    public void computeBonusPercent() {
        clearComputedBonuses();
        int atkBonus = MaterialRegistry.blade(taglio) + MaterialRegistry.blade(contundente)
                     + MaterialRegistry.blade(punta)  + MaterialRegistry.blade(peso);
        if (atkBonus != 0) statBonusPercent.merge(StatType.ATTACK, atkBonus, Integer::sum);
        int velBonus = MaterialRegistry.weaponPomo(pomo);
        if (velBonus != 0) statBonusPercent.merge(StatType.VELOCITA, velBonus, Integer::sum);
        // Difensive (Scudo/Broquel/Sai): guardia È la stat che scala con rarità/livello (è il
        // loro "Build" Defense/Elemental Def/Special Def). Mischia/distanza: guardia resta un
        // tratto fisso della forma dell'arma, invariata rispetto a prima — vedi MaterialRegistry.
        int difBonus = (subtype.category == WeaponCategory.DIFENSIVE)
            ? MaterialRegistry.offhandGuardia(guardia)
            : MaterialRegistry.weaponGuardia(guardia);
        if (difBonus != 0) statBonusPercent.merge(StatType.DIFESA, difBonus, Integer::sum);
        addComponents(gemme);
        addComponents(incantesimi);
        applyQualityLevel(affilatezza);
    }
}

