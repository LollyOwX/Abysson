package items;

import items.Weapon.WeaponSubtype;
import items.Weapon.ZoneType;
import java.awt.geom.Rectangle2D;

public class ShieldRegistry {
    private static final ItemTable TABLE = new ItemTable("/items/shields.tsv");

    public static String[] ids() { return TABLE.ids(); }

    public static Weapon get(String id) {
        ItemTable.Row r = TABLE.row(id);
        if (r == null) {
            System.err.println("Weapon not found: " + id);
            return null;
        }
        WeaponSubtype subtype = r.enumOf(WeaponSubtype.class, "subtype");
        Weapon w = new Weapon(subtype);
        r.fillCommon(w);
        w.affilatezza = r.num("affilatezza");
        r.into(w.pomo, 0, "pomo");
        r.into(w.metalli, 0, "metalli");
        w.canDefend = true;
        w.restAngleDeg = 0;
        switch (subtype) {
            case SCUDO -> {
                w.staticGuard = true;
                zones(w, new Rectangle2D.Double(-30, -40, 60, 50), new Rectangle2D.Double(18, -38, 10, 14));
            }
            case BROQUEL -> zones(w, new Rectangle2D.Double(-15, -20, 30, 25), new Rectangle2D.Double(-15, -20, 8, 10));
            case SAI -> zones(w, new Rectangle2D.Double(-4, -35, 8, 35), new Rectangle2D.Double(-4, -35, 8, 6));
            default -> throw new IllegalArgumentException(id + ": " + subtype + " is not a defensive weapon");
        }
        return w;
    }

    private static void zones(Weapon w, Rectangle2D rigid, Rectangle2D weak) {
        w.defenseZones.add(new Weapon.DefenseZone(rigid, ZoneType.RIGID));
        w.defenseZones.add(new Weapon.DefenseZone(weak, ZoneType.WEAK));
    }
}
