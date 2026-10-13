package items;

import java.util.ArrayList;
import java.util.List;

public class Precious {
    public static class City {
        public final String name;
        public final int value;
        public final int flagColor; // 0xAARRGGBB — colore della bandiera, applicato con PaletteSwap
        public City(String name, int value, int flagColor) {
            this.name = name;
            this.value = value;
            this.flagColor = flagColor;
        }
        public String initials() {
            return name.length() >= 2 ? name.substring(0, 2).toUpperCase() : name.toUpperCase();
        }
    }

    public String name, itemPath, detailPath;
    public int currentValue, quantity = 1;
    public double reputationUp, reputationDown; // ratio: 0.15 = +15% di valore per ogni salita / -15% per ogni discesa
    public final List<City> cities = new ArrayList<>();

    public Precious(String name, int currentValue, int quantity, double reputationUp, double reputationDown) {
        this.name = name;
        this.currentValue = currentValue;
        this.quantity = quantity;
        this.reputationUp = reputationUp;
        this.reputationDown = reputationDown;
    }
}
