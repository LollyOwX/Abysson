package items;

import entity.StatType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Item {

    // ── Slot ──────────────────────────────────────────────────
    // MainHand/OffHand condivisi tra Weapon e Armatura Difensiva (Scudo/Broquel/Sai vanno in
    // OffHand di default — vedi Weapon). Head/Neck sono SOLO dei Gioielli (Corona/Collana);
    // Helmet/Gorget sono SOLO dell'Armatura — 4 slot distinti, non condivisi: Gioielli, Armi e
    // Armatura restano completamente separati tra loro, un elmo e una corona si possono
    // indossare insieme. Ring1/Ring2/Bracelet1/Bracelet2 per i Gioielli. Gli altri 10 sono i
    // pezzi dell'armatura (Chestplate rimosso, sostituito da questi — vedi Armor).
    public enum ItemSlot {
        MainHand, OffHand,
        Head, Neck, Ring1, Ring2, Bracelet1, Bracelet2,
        Helmet, Gorget, Pauldron, Rerebrace, Couter, Vanbrace, Gauntlet,
        Cuirasse, Cuisse, Poleyn, Greave, Sabaton
    }

    // ── Identità ──────────────────────────────────────────────
    // Ogni oggetto creato (new Weapon(...), new Armor(...), new Jewelry(...), o un item "a mano"
    // come Sword_Basic_Iron) è la SUA istanza, mai deduplicata per tipo — due Weapon dello stesso
    // WeaponSubtype (es. due Spada Corta) restano due oggetti indipendenti: modificare gemme/
    // incantesimi/campi grezzi sull'uno non tocca l'altro. Questo è già garantito dal fatto che
    // sono normali oggetti Java (equals() di default = identità, ogni new è a sé) — instanceId
    // qui sotto non serve a questo, serve solo ad avere un riferimento stabile e leggibile per
    // distinguere in un log/UI due oggetti altrimenti identici (es. due spade base senza
    // incantesimi, visivamente indistinguibili l'una dall'altra).
    //
    // NOTA per chi costruirà l'inventario vero: gli EQUIPAGGIABILI (Weapon/Armor/Jewelry) vanno
    // sempre in una lista di riferimenti a oggetti (List<Item>, come già in items/Inventory.java)
    // — mai in una mappa "tipo -> quantità" con un contatore, altrimenti si perde quale copia ha
    // quali componenti. Gli oggetti "puri" senza componenti (una Key) possono restare stackati
    // per nome, non hanno questo problema.
    private static long nextInstanceId = 1;
    public final long instanceId = nextInstanceId++;

    public String       name        = "Item";
    public String       description = "";
    public ItemSlot      slot        = ItemSlot.MainHand;
    public ItemCategory category; // impostata dal costruttore delle sottoclassi (Weapon/Armor/Jewelry)

    // Percorso del png — STUB, non si tocca: nessun asset esiste ancora, nessuna logica di
    // caricamento/rendering va costruita per questo campo. Condiviso da Weapon/Armor/Jewelry.
    // Weapon ha in più combatItemPath (percorso "esteso" per il minigioco di parata): lo
    // popolano davvero solo le Difensive (Scudo/Broquel/Sai), le altre armi lo lasciano null —
    // vedi Weapon.loadRigidZoneFromImage().
    public String itemPath;

    // ── Modificatori stat ──────────────────────────────────────
    // Percentuale di bonus/malus per statistica (es. taglio=8 -> ATTACK +8%, tradotto da
    // computeBonusPercent()). La parte "flat" di ogni stat resta sul personaggio (baseX in
    // Player); l'equipaggiamento modifica percentuale e moltiplicatore applicati sopra quel
    // valore base — vedi Player.recalculateStats() (stat = flat * % * moltiplicatore, in
    // quest'ordine).
    public Map<StatType, Integer> statBonusPercent = new EnumMap<>(StatType.class);

    // Stadio "moltiplicatore" per stat (1.0 = nessun effetto) — oggi nessun oggetto lo popola
    // (la qualità scala direttamente statBonusPercent, vedi applyQualityLevel()). Resta per
    // buff/debuff futuri: Player lo moltiplica nel suo statMultiplier — vedi
    // Player.applySlotBonus()/mult().
    public Map<StatType, Double> statMultiplier = new EnumMap<>(StatType.class);

    // Effetti risolti da gemme/incantesimi/rocce (vedi addComponents()) — SEMPRE effetti
    // speciali via combat.SpecialEffectRegistry, mai bonus %. Tre liste per timing:
    // PRE_TURN/POST_TURN (richiamati da CombatState.beforeTurn()/afterTurn() sul portatore ad
    // ogni turno, finché l'oggetto resta equipaggiato) vs ACTIVE (scatta solo se qualcuno lo
    // invoca esplicitamente — vedi SpecialEffect).
    public final List<combat.SpecialEffect> preTurnEffects  = new ArrayList<>();
    public final List<combat.SpecialEffect> postTurnEffects = new ArrayList<>();
    public final List<combat.SpecialEffect> activeEffects   = new ArrayList<>();

    /** Azzera tutto quello che computeBonusPercent() ricalcola da zero ad ogni chiamata —
     *  un solo posto invece di ripetere 4 clear() in ogni sottoclasse. */
    protected void clearComputedBonuses() {
        statBonusPercent.clear();
        statMultiplier.clear();
        preTurnEffects.clear();
        postTurnEffects.clear();
        activeEffects.clear();
    }

    /**
     * Qualità (affilatezza per Weapon, rifiniture per Armor): scala di poco TUTTI i bonus % che
     * l'oggetto ha appena messo in statBonusPercent — solo quelli dell'oggetto, non la stat
     * intera del Player. Il grosso del bonus resta quello dei component (ID -> bonus, vedi
     * ComponentRegistry). Arrotondato all'intero più vicino (statBonusPercent è intero).
     */
    protected static final double QUALITY_STEP = 0.25; // per livello
    protected void applyQualityLevel(int level) {
        if (level == 0) return;
        double factor = 1.0 + QUALITY_STEP * level;
        statBonusPercent.replaceAll((t, v) -> (int) Math.round(v * factor));
    }

    // ── Durabilità ────────────────────────────────────────────
    // 100 per tutti; le armi a distanza vere la leggono da struttura[0] (vedi Weapon). durability
    // parte da -1 = "non ancora inizializzata": si riempie al primo computeBonusPercent().
    public int maxDurability = 100;
    public int durability = -1;

    protected void initDurability() {
        if (durability < 0 || durability > maxDurability) durability = maxDurability;
    }

    // Tipologie di durabilità: l'oggetto perde durabilità SOLO per azioni di un tipo in lista
    // (vedi DamageTypes). Default impostato dalle sottoclassi.
    public String[] tipologie = {};

    /** Rotto = durabilità a 0 (non quella ancora da inizializzare, -1). */
    public boolean isBroken() { return durability == 0; }

    /** Toglie 'amount' di durabilità se 'type' è in tipologie. true se ha consumato qualcosa. */
    public boolean wear(String type, int amount) {
        if (durability <= 0 || amount <= 0) return false;
        for (String t : tipologie) {
            if (t.equals(type)) {
                durability = Math.max(0, durability - amount);
                return true;
            }
        }
        return false;
    }

    // Da chiamare in fondo a computeBonusPercent(). Rotto: nessun bonus assoluto (stat ed effetti
    // dell'oggetto; gli ID di set restano, vedi Player.activeFullID()). Danneggiato: se
    // scalesDefence, solo DIFESA cala in proporzione alla durabilità; gli altri bonus no.
    protected void applyDurabilityState(boolean scalesDefence) {
        if (isBroken()) {
            clearComputedBonuses();
            return;
        }
        if (scalesDefence && durability < maxDurability) {
            double ratio = (double) durability / maxDurability;
            statBonusPercent.computeIfPresent(StatType.DIFESA, (t, v) -> (int) Math.round(v * ratio));
        }
    }

    // Somma al statBonusPercent il bonus di ogni ID in ids[fromSlot..] per quella famiglia/component.
    protected void addComponentBonus(ComponentRegistry.Family f, ComponentRegistry.Part p, int[] ids, int fromSlot) {
        for (int i = fromSlot; i < ids.length; i++) {
            ComponentRegistry.Entry e = ComponentRegistry.get(f, p, ids[i]);
            if (e != null && e.stat != null) statBonusPercent.merge(e.stat, e.value, Integer::sum);
        }
    }

    // Somma i valori di un component senza StatType (guardia/manico: resistenza al disarmo).
    protected int componentValueSum(ComponentRegistry.Family f, ComponentRegistry.Part p, int[] ids) {
        int sum = 0;
        for (int id : ids) {
            ComponentRegistry.Entry e = ComponentRegistry.get(f, p, id);
            if (e != null) sum += e.value;
        }
        return sum;
    }

    // ── Rarità e bonus a scaglioni ───────────────────────────────
    // Rispecchia le colonne G/H/I della scheda Equip: cumulativi, non esclusivi — Masterwork ha
    // sia synergyBonusStub (G, sbloccato da High Quality) che fullSetOrStyleStub (H, sbloccato da
    // Masterwork); Mythic ha anche mythicUniqueStub (I). Solo testo STUB per ora, nessuna logica
    // — stesso pattern "contenitore vuoto" di Component: verranno tradotti in id/Registry reali
    // quando la meccanica sarà implementata. Chi crea l'oggetto decide quali riempire in base a
    // rarity; non c'è derivazione automatica qui.
    public Rarity rarity = Rarity.COMMON;
    public String synergyBonusStub;   // % che sinergizza con la build — da High Quality in su
    public String fullSetOrStyleStub; // full set (armatura/scudo) o stile di combattimento (arma) — da Masterwork in su
    public String mythicUniqueStub;   // effetto unico per pezzo, indipendente dal set — solo Mythic

    // Risolvono gli stub sopra nell'effetto reale via combat.SpecialEffectRegistry — lo stesso
    // registry usato da Ability per le abilità (vedi SpecialEffect). Tornano null finché lo
    // stub resta testo libero non registrato (es. "STUB"): comportamento corretto, non un
    // errore. Nessuno li richiama ancora da nessuna parte — sono il punto di aggancio pronto
    // per quando smetteranno di essere STUB, non logica già in funzione.
    public combat.SpecialEffect resolveSynergyEffect()       { return combat.SpecialEffectRegistry.get(synergyBonusStub); }
    public combat.SpecialEffect resolveFullSetOrStyleEffect() { return combat.SpecialEffectRegistry.get(fullSetOrStyleStub); }
    public combat.SpecialEffect resolveMythicUniqueEffect()   { return combat.SpecialEffectRegistry.get(mythicUniqueStub); }

    /**
     * L'"add components": ricalcola statBonusPercent/statMultiplier/passiveEffects/
     * activeEffects da zero leggendo i campi grezzi propri della sottoclasse (via
     * MaterialRegistry) + i componenti innestati (via SpecialEffectRegistry) — UN blocco letto
     * una sola volta qui, invece di controllare ogni componente uno per uno ad ogni accesso.
     * Richiamata da Player.equip() prima di applicare i bonus (così riflette anche componenti
     * cambiati dopo la creazione dell'oggetto, es. una gemma incastonata più tardi).
     *
     * No-op di default: un item "a mano" che non la sovrascrive resta con qualunque bonus sia
     * stato impostato altrove (nessuno lo fa più oggi — Weapon/Armor/Jewelry la sovrascrivono
     * tutte). Tenuta come no-op, non astratta, per non forzare un override su un futuro item
     * "puro" che non ne avesse bisogno.
     */
    public void computeBonusPercent() {
        // no-op di default
    }

    /**
     * Helper condiviso da Weapon/Armor/Jewelry: risolve ogni componente innestato (gemma/
     * incantesimo/roccia) in combat.SpecialEffectRegistry e lo smista in passiveEffects o
     * activeEffects secondo il suo timing() — gemme e incantesimi danno SEMPRE un effetto
     * speciale, mai un bonus % (quello lo danno solo i campi grezzi, via MaterialRegistry).
     * Slot null o id sconosciuto/non ancora implementato = nessun effetto, non un errore.
     */
    protected void addComponents(Component[] components) {
        if (components == null) return;
        for (Component c : components) {
            if (c == null) continue;
            combat.SpecialEffect eff = combat.SpecialEffectRegistry.get(c.id);
            if (eff == null) continue;
            switch (eff.timing()) {
                case PRE_TURN  -> preTurnEffects.add(eff);
                case POST_TURN -> postTurnEffects.add(eff);
                case ACTIVE    -> activeEffects.add(eff);
            }
        }
    }
}