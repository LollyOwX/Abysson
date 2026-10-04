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
 * Un'arma. I campi grezzi sono array di component (nomecomponent[slot] = ID) tradotti in bonus
 * da computeBonusPercent() (l'"add components", vedi Item) tramite ComponentRegistry, per
 * famiglia: Mischia (SPADE/MAZZE/LANCE + Coltello da Lancio), Distanza (ARCO_CORTO/ARCO_LUNGO/
 * BALESTRA), Scudo (SCUDO/BROQUEL/SAI). Le probabilità (disarmChance, stunChance,
 * counterattackChance) e penetratesUpTo non hanno un default "giusto": restano a 0/null finché
 * non li imposta chi crea l'istanza vera (vedi WeaponRegistry) — i numeri lì dentro sono di
 * esempio, non un bilanciamento reale.
 */
public class Weapon extends Item {

    public enum WeaponCategory { SPADE, MAZZE, LANCE, ARMI_A_DISTANZA, DIFENSIVE }

    public enum WeaponSubtype {
        SPADA_CORTA(WeaponCategory.SPADE, false),
        SPADA_LUNGA(WeaponCategory.SPADE, false),
        FRUSTA(WeaponCategory.SPADE, false),
        MAZZA_CHIODATA(WeaponCategory.MAZZE, false),
        ASCIA(WeaponCategory.MAZZE, false),
        SPADONE(WeaponCategory.MAZZE, true),          // Spadone/Claymore: entrambe le mani
        LANCIA_LUNGA(WeaponCategory.LANCE, true),     // lancia lunga: entrambe le mani
        PICCA(WeaponCategory.LANCE, true),            // picca: portata estrema, entrambe le mani
        FALCE(WeaponCategory.LANCE, true),             // falce: fendente ampio, entrambe le mani
        ARCO_CORTO(WeaponCategory.ARMI_A_DISTANZA, true),   // un arco si tende con entrambe le mani
        ARCO_LUNGO(WeaponCategory.ARMI_A_DISTANZA, true),
        BALESTRA(WeaponCategory.ARMI_A_DISTANZA, true),
        COLTELLO_DA_LANCIO(WeaponCategory.ARMI_A_DISTANZA, false), // leggero, lanciato con una mano sola
        SCUDO(WeaponCategory.DIFENSIVE, false),
        BROQUEL(WeaponCategory.DIFENSIVE, false),
        SAI(WeaponCategory.DIFENSIVE, false);

        public final WeaponCategory category;
        // true = richiede entrambe le mani: equipaggiarla libera forzatamente l'OffHand (e non
        // puoi equipaggiare un'OffHand finché la impugni) — scelta interpretativa per Type, non
        // dettata dalla scheda Equip (che non distingue 1/2 mani): correggimi i singoli casi se
        // non ci hai pensato allo stesso modo.
        public final boolean twoHanded;
        WeaponSubtype(WeaponCategory category, boolean twoHanded) {
            this.category = category;
            this.twoHanded = twoHanded;
        }
    }

    public final WeaponSubtype subtype;

    // affilatezza è un LIVELLO (non un ID): scala di poco tutti i bonus dell'oggetto — vedi
    // Item.applyQualityLevel(). Tutti gli altri campi sono component: nomecomponent[slot] = ID,
    // risolto da ComponentRegistry per la famiglia dell'arma (l'ID 4 di pomo non è l'ID 4 di
    // corda). Un campo senza tabella per la famiglia dell'arma non produce nulla.
    public int affilatezza;

    // ── Mischia + Coltello da Lancio ─────────────────────────────
    public final int[] pomo   = new int[1]; // -> VELOCITA
    public final int[] manico = new int[1]; // -> controllo: resistenza al disarmo (manicoBonus)
    public final int[] guardia = new int[1]; // -> resistenza al disarmo (guardiaBonus)
    public final int[] lama   = new int[1]; // nessuna tabella ancora
    // Punta: -> ATTACK. Nelle MAZZE (Mazza Chiodata, Ascia, Spadone) punta[0] NON è un ID: è il
    // peso dell'arma, un valore diretto (vedi baseDamage()).
    public final int[] punta  = new int[1];

    // ── Solo armi a distanza vere (non il Coltello, che usa i campi sopra) ──
    public final int[] corda = new int[1];     // -> PRECISIONE
    public final int[] struttura = new int[1]; // NON è un ID: durabilità massima, valore diretto (le altre armi hanno 100)
    // legamenti (sotto) nelle armi a distanza -> ATTACK

    // ── Scudi e armature (Difensive) ─────────────────────────────
    public final int[] metalli   = new int[3]; // -> DIFESA, fino a 3, sommati
    public final int[] legamenti = new int[1]; // distanza: -> ATTACK; scudi: nessuna tabella ancora

    // Valori risolti da computeBonusPercent() per guardia/manico (non sono un StatType): letti da
    // CombatState per disarmo e parata.
    public int guardiaBonus;
    public int manicoBonus;

    public int disarmChance;        // % probabilità di disarmare l'avversario — Frusta
    public int stunChance;          // % probabilità di stordire 1 turno — Falce (riusa StatusEffect.STORDIMENTO)
    public int counterattackChance; // % probabilità di contrattaccare — Coltello da lancio

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

    public ComponentRegistry.Family family() {
        if (subtype.category == WeaponCategory.DIFENSIVE) return ComponentRegistry.Family.SHIELD;
        if (subtype.category == WeaponCategory.ARMI_A_DISTANZA && subtype != WeaponSubtype.COLTELLO_DA_LANCIO)
            return ComponentRegistry.Family.RANGED;
        return ComponentRegistry.Family.MELEE;
    }

    /** MAZZE: punta[0] è il peso, danno diretto invece di un ID. */
    public boolean usesWeightDamage() {
        return subtype.category == WeaponCategory.MAZZE;
    }

    /**
     * Danno base di NormalAttack per chi impugna l'arma. Mazza Chiodata/Ascia: danno = peso
     * (l'ATK del Player non conta, solo gli ATTACK % dell'arma stessa, es. gemme/incantesimi).
     * Spadone: danno = ATK + peso. Tutte le altre armi: ATK.
     */
    public int baseDamage(int atk) {
        if (subtype == WeaponSubtype.SPADONE) return atk + punta[0];
        if (usesWeightDamage()) {
            return (int) Math.round(punta[0] * (1 + statBonusPercent.getOrDefault(StatType.ATTACK, 0) / 100.0));
        }
        return atk;
    }

    /**
     * "Add components": per la famiglia dell'arma somma ogni component al suo StatType (prima
     * della qualità), poi chiude con applyQualityLevel(affilatezza). Durabilità: 100 per tutte,
     * struttura[0] per le armi a distanza vere.
     */
    @Override
    public void computeBonusPercent() {
        clearComputedBonuses();
        ComponentRegistry.Family fam = family();
        switch (fam) {
            case SHIELD -> {
                addComponentBonus(fam, ComponentRegistry.Part.METALLI, metalli, 0);
                addComponentBonus(fam, ComponentRegistry.Part.LEGAMENTI, legamenti, 0);
            }
            case RANGED -> {
                addComponentBonus(fam, ComponentRegistry.Part.CORDA, corda, 0);
                addComponentBonus(fam, ComponentRegistry.Part.LEGAMENTI, legamenti, 0);
            }
            case MELEE -> {
                addComponentBonus(fam, ComponentRegistry.Part.PUNTA, punta, usesWeightDamage() ? 1 : 0);
                addComponentBonus(fam, ComponentRegistry.Part.POMO, pomo, 0);
                addComponentBonus(fam, ComponentRegistry.Part.LAMA, lama, 0);
            }
            default -> { }
        }
        guardiaBonus = componentValueSum(fam, ComponentRegistry.Part.GUARDIA, guardia);
        manicoBonus  = componentValueSum(fam, ComponentRegistry.Part.MANICO, manico);
        maxDurability = (fam == ComponentRegistry.Family.RANGED) ? struttura[0] : 100;
        initDurability();
        addComponents(gemme);
        addComponents(incantesimi);
        applyQualityLevel(affilatezza);
    }
}
