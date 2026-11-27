package items;

import items.Weapon.WeaponSubtype;

public class WeaponRegistry {
    private static final ItemTable TABLE = new ItemTable("/items/weapons.tsv");

    public static String[] ids() { return TABLE.ids(); }

    public static Weapon get(String id) {
        ItemTable.Row r = TABLE.row(id);
        if (r == null) {
            System.err.println("Weapon not found: " + id);
            return null;
        }
        Weapon w = new Weapon(r.enumOf(WeaponSubtype.class, "subtype"));
        r.fillCommon(w);
        w.affilatezza = r.num("affilatezza");
        r.into(w.pomo, 0, "pomo");
        r.into(w.guardia, 0, "guardia");
        r.into(w.punta, 0, "punta");
        r.into(w.corda, 0, "corda");
        r.into(w.struttura, 0, "struttura");
        r.into(w.legamenti, 0, "legamenti");
        return w;
    }
}
