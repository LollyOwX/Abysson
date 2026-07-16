package items;

public abstract class Item {

    // ── Slot ──────────────────────────────────────────────────
    public enum ItemSlot {MainHand, OffHand, Chestplate}

    // ── Identità ──────────────────────────────────────────────
    public String   name        = "Item";
    public String   description = "";
    public ItemSlot slot        = ItemSlot.MainHand;


    // ── Modificatori stat (tutti 0 = nessun effetto) ──────────
    public int attack    = 0;
    public int defense   = 0;
    public int maxLife   = 0;
    public int speed     = 0;
    public int precision = 0;
    public int evasion   = 0;
}
