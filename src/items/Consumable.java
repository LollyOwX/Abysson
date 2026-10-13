package items;

import java.util.ArrayList;
import java.util.List;

public class Consumable {
    public static class Effect {
        public final String iconPath, description;
        public Effect(String iconPath, String description) {
            this.iconPath = iconPath;
            this.description = description;
        }
    }

    public String name, itemPath, detailPath;
    public int goldValue, quantity = 1;
    public final List<StatLine> gains = new ArrayList<>();
    public final List<Effect> effects = new ArrayList<>();

    public Consumable(String name, int goldValue, int quantity) {
        this.name = name;
        this.goldValue = goldValue;
        this.quantity = quantity;
    }
}
