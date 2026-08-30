package items;

import entity.StatType;

import java.util.EnumMap;
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
    public String       name        = "Item";
    public String       description = "";
    public ItemSlot      slot        = ItemSlot.MainHand;
    public ItemCategory category; // impostata dal costruttore delle sottoclassi (Weapon/Armor/Jewelry)

    // ── Modificatori stat ──────────────────────────────────────
    // Percentuale di bonus/malus per statistica (es. bonus(ATTACK, 3) = +3%).
    // La parte "flat" di ogni stat resta sul personaggio (baseX in Player);
    // l'equipaggiamento modifica solo la percentuale finale applicata sopra
    // quel valore base — vedi Player.recalculateStats().
    //
    // Weapon/Armor/Jewelry NON la popolano più a mano (bonus(...) nel costruttore, come faceva
    // Sword_Basic_Iron): la traducono dai loro campi grezzi tramite computeBonusPercent() —
    // l'"add components". Sword_Basic_Iron resta com'era, invariata.
    public Map<StatType, Integer> statBonusPercent = new EnumMap<>(StatType.class);

    protected void bonus(StatType type, int percent) {
        statBonusPercent.put(type, percent);
    }

    /**
     * L'"add components": ricalcola statBonusPercent da zero leggendo i campi grezzi propri
     * della sottoclasse + i componenti innestati (gemme/incantesimi/rocce) — UN blocco letto
     * una sola volta qui, invece di controllare ogni componente uno per uno ad ogni accesso.
     * Richiamata da Player.equip() prima di applicare i bonus (così riflette anche componenti
     * cambiati dopo la creazione dell'oggetto, es. una gemma incastonata più tardi).
     *
     * No-op di default: oggetti "a mano" come Sword_Basic_Iron impostano statBonusPercent
     * direttamente nel costruttore e NON vanno ricalcolati — chiamare questo metodo su di loro
     * non fa nulla, i loro bonus restano quelli impostati. Weapon/Armor/Jewelry la sovrascrivono.
     */
    public void computeBonusPercent() {
        // no-op di default
    }

    /** Helper condiviso da Weapon/Armor/Jewelry: somma il bonus di ogni componente innestato
     *  (via ComponentRegistry) a statBonusPercent. Slot null o id sconosciuto = nessun bonus,
     *  non un errore. */
    protected void addComponents(Component[] components) {
        if (components == null) return;
        for (Component c : components) {
            if (c == null) continue;
            ComponentRegistry.Effect eff = ComponentRegistry.get(c.id);
            if (eff != null) statBonusPercent.merge(eff.stat, eff.percent, Integer::sum);
        }
    }
}

