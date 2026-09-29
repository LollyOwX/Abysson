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
 * (disarmChance, stunChance, counterattackChance) e penetratesUpTo non hanno un default
 * "giusto": restano a 0/null finché non li imposta chi crea l'istanza vera (vedi
 * WeaponRegistry) — i numeri lì dentro sono di esempio, non un bilanciamento reale.
 *
 * Componenti per categoria (nomi tuoi, con quale StatType risolvono se non specificato
 * altrimenti nel commento sul campo):
 *   Mischia (SPADE/MAZZE/LANCE) e Coltello da Lancio ("stessa composizione della mischia"):
 *     Pomello(pomo)=peso->VELOCITA, Manico(manico)=controllo (senza tabella), Guardia(guardia)
 *     =riduce i danni alle mani se subiti (non uno statBonusPercent: vedi CombatState.
 *     effectiveDisarmChance()), Lama(lama)=durabilità (senza tabella), Punta(punta)=danni
 *     ->ATTACK — UNICA fonte di danno ora (prima taglio/contundente/peso, sostituiti).
 *   Armi a distanza vere (ARCO_CORTO/ARCO_LUNGO/BALESTRA, non il Coltello):
 *     Corda(corda)=danni->ATTACK, legamenti=durabilità (senza tabella, riusa il campo sotto),
 *     Struttura(struttura)=precisione->PRECISIONE.
 *   Difensive (SCUDO/BROQUEL/SAI — "stesso sistema di Armor, gestito come un'arma"):
 *     Metalli(metalli[0..2])=resistenza->DIFESA (fino a 3, sommati, come Armor.metalli),
 *     legamenti=durabilità (senza tabella, stesso campo di cui sopra, riusato).
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

    // affilatezza è un LIVELLO (non un ID): moltiplica il bonus totale, non lo genera — vedi
    // Item.applyQualityLevel(). Gli altri campi numerici sono ID: il numero non è più il bonus
    // stesso, è una chiave in MaterialRegistry che lo risolve (stesso id = stesso effetto su
    // qualunque arma) — ECCETTO guardia, che è un modificatore diretto (vedi sotto), non un ID.
    public int affilatezza; // livello: 0.25x di moltiplicatore per livello sul bonus totale dell'arma

    // ── Mischia + Coltello da Lancio ─────────────────────────────
    public int pomo;    // Pomello: peso — ID -> VELOCITA (MaterialRegistry.weaponPomo)
    // Manico: controllo — riduce la probabilità di essere disarmato ESATTAMENTE come Guardia
    // (10 punti = -10%, si sommano tra loro): vedi CombatState.effectiveDisarmChance(). Non è
    // un bonus %/StatType, non passa da MaterialRegistry — un numero diretto, non un ID.
    public int manico;
    // Guardia: riduce i danni alle mani SE SUBITI (disarmo) — non passa da MaterialRegistry,
    // è un numero di "resistenza" sottratto direttamente dalla probabilità di disarmo
    // dell'avversario in CombatState.effectiveDisarmChance() (10 punti = -10% di probabilità),
    // non un bonus %/StatType come le altre.
    public int guardia;
    // Lama: durabilità — quanto danno può assorbire l'arma prima di rompersi, PUNTI DIRETTI
    // (range fisso 0-100, non un ID: non passa da MaterialRegistry, il numero È i punti).
    // Nessun evento la consuma ancora (a differenza di Weapon.legamenti sulle Difensive, che
    // la parata già scala — vedi CombatState linee 755+): manca un evento di usura per le armi
    // da mischia equivalente alla parata, e nessuna logica di "cosa succede a durabilità 0"
    // (rottura? malus? niente?) — lasciata aperta, vedi STATUS.md.
    public int lama;
    public int punta; // Punta: danni — ID -> ATTACK (MaterialRegistry.blade) — unica fonte di danno per la mischia

    // ── Solo armi a distanza vere (non il Coltello, che usa i campi sopra) ──
    public int corda;     // Corda: danni — ID -> ATTACK (MaterialRegistry.blade)
    public int struttura; // Struttura: precisione — ID -> PRECISIONE (MaterialRegistry.weaponStruttura)

    // ── Solo Difensive (Scudo/Broquel/Sai) ───────────────────────
    public final int[] metalli = new int[3]; // Metalli: resistenza — ID -> DIFESA (MaterialRegistry.armorMetallo), fino a 3, sommati — come Armor.metalli

    // legamenti: durabilità — PUNTI DIRETTI (range fisso 0-100, non un ID: non passa da
    // MaterialRegistry), stessa semantica di Lama. Riusato da armi a distanza vere E Difensive
    // (queste ultime lo scalano già durante la parata — vedi CombatState linee 755+). Non
    // usato dalla mischia (quella ha Lama per lo stesso ruolo).
    public int legamenti;

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

    /**
     * "Add components", diverso per categoria (vedi il commento in testa alla classe):
     *   Difensive     -> somma metalli[0..2] su DIFESA (come Armor.metalli)
     *   Distanza vere -> corda su ATTACK, struttura su PRECISIONE
     *   Tutto il resto (mischia + Coltello, che usa la stessa composizione) -> punta su
     *                    ATTACK, pomo su VELOCITA
     * guardia NON entra qui: non è un bonus %, è letto direttamente da CombatState quando
     * serve (resistenza al disarmo) — vedi il commento sul campo. manico/lama/legamenti non
     * producono bonus (nessuna tabella ancora, vedi i commenti sui campi). affilatezza chiude
     * il calcolo moltiplicando quello appena messo in statBonusPercent (Item.
     * applyQualityLevel()). Scelte interpretative, non numeri o formule che mi hai dato tu:
     * correggile se non vanno bene.
     */
    @Override
    public void computeBonusPercent() {
        clearComputedBonuses();
        if (subtype.category == WeaponCategory.DIFENSIVE) {
            int difBonus = 0;
            for (int m : metalli) difBonus += MaterialRegistry.armorMetallo(m);
            if (difBonus != 0) statBonusPercent.merge(StatType.DIFESA, difBonus, Integer::sum);
        } else if (subtype.category == WeaponCategory.ARMI_A_DISTANZA && subtype != WeaponSubtype.COLTELLO_DA_LANCIO) {
            int atkBonus = MaterialRegistry.blade(corda);
            if (atkBonus != 0) statBonusPercent.merge(StatType.ATTACK, atkBonus, Integer::sum);
            int precBonus = MaterialRegistry.weaponStruttura(struttura);
            if (precBonus != 0) statBonusPercent.merge(StatType.PRECISIONE, precBonus, Integer::sum);
        } else {
            int atkBonus = MaterialRegistry.blade(punta);
            if (atkBonus != 0) statBonusPercent.merge(StatType.ATTACK, atkBonus, Integer::sum);
            int velBonus = MaterialRegistry.weaponPomo(pomo);
            if (velBonus != 0) statBonusPercent.merge(StatType.VELOCITA, velBonus, Integer::sum);
        }
        addComponents(gemme);
        addComponents(incantesimi);
        applyQualityLevel(affilatezza);
    }
}
