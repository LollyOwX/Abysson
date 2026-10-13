package items;

public class QuestItem {
    /** File: res/ui/icon_<tipo>quest.png */
    public enum Kind { ARCH, NICHE, SPECIAL, TODO, WORLD;
        public String iconPath() { return "/ui/icon_" + name().toLowerCase() + "quest.png"; }
    }

    public String name, itemPath, detailPath, description;
    public Kind kind = Kind.TODO;
    public boolean mustDeliver;   // false: nessuna consegna da fare, calendario tutto nero
    public int deliverDay;        // giorno del mese (1..); 0 = nessuno
    public boolean exactDay;      // true: giorno preciso (rosso); false: entro il giorno (giallo)

    public QuestItem(String name, String description, Kind kind) {
        this.name = name;
        this.description = description;
        this.kind = kind;
    }

    public QuestItem due(int day, boolean exact) {
        this.mustDeliver = true;
        this.deliverDay = day;
        this.exactDay = exact;
        return this;
    }
}
