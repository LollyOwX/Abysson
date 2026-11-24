package items;

import items.Armor.ArmorType;
import items.Armor.WeightClass;

public class ArmorRegistry {
    private static final ItemTable TABLE = new ItemTable("/items/armors.tsv");

    public static String[] ids() { return TABLE.ids(); }

    public static Armor get(String id) {
        ItemTable.Row r = TABLE.row(id);
        if (r == null) {
            System.err.println("Armor non trovata: " + id);
            return null;
        }
        Armor a = new Armor(r.enumOf(ArmorType.class, "type"), r.enumOf(WeightClass.class, "weight"));
        r.fillCommon(a);
        a.rifiniture = r.num("rifiniture");
        r.into(a.legamenti, 0, "legamenti");
        r.into(a.metalli, 0, "metalli");
        if (r.str("upperID") != null) a.upperID = r.num("upperID");
        if (r.str("lowerID") != null) a.lowerID = r.num("lowerID");
        r.into(a.acceptedUpperIDs, 0, "accepted0");
        r.into(a.acceptedUpperIDs, 1, "accepted1");
        return a;
    }
}
