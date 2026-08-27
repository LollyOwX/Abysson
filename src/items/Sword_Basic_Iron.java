package items;

import entity.StatType;

public class Sword_Basic_Iron extends Item {
    public Sword_Basic_Iron() {
        name = "Basic Iron Sword";
        description = "A basic sword, used by local warriors and wanderers";
        slot = ItemSlot.MainHand;
        bonus(StatType.ATTACK, 3);
        bonus(StatType.DIFESA, -1);
    }
}
