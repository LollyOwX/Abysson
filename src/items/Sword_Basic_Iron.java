package items;

public class Sword_Basic_Iron extends Item{
    public Sword_Basic_Iron() {
        name = "Basic Iron Sword";
        description = "A basic sword, used by local warriors and wanderers";
        slot = ItemSlot.MainHand;
        attack = 3;
        defense = -1;
    }
}
