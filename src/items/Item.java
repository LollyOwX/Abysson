package items;

import entity.StatType;

import java.util.EnumMap;
import java.util.Map;

public abstract class Item {

    // ── Slot ──────────────────────────────────────────────────
    public enum ItemSlot {MainHand, OffHand, Chestplate}

    // ── Identità ──────────────────────────────────────────────
    public String   name        = "Item";
    public String   description = "";
    public ItemSlot slot        = ItemSlot.MainHand;

    // ── Modificatori stat ──────────────────────────────────────
    // Percentuale di bonus/malus per statistica (es. bonus(ATTACK, 3) = +3%).
    // La parte "flat" di ogni stat resta sul personaggio (baseX in Player);
    // l'equipaggiamento modifica solo la percentuale finale applicata sopra
    // quel valore base — vedi Player.recalculateStats().
    public Map<StatType, Integer> statBonusPercent = new EnumMap<>(StatType.class);

    protected void bonus(StatType type, int percent) {
        statBonusPercent.put(type, percent);
    }
}
