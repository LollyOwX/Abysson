package items;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ItemCheck {
    private static final Pattern TAIL = Pattern.compile("_(\\d+)_(\\d+)_([a-z_]+)$");

    public static void main(String[] args) {
        int bad = 0, total = 0;
        bad += check("armi", WeaponRegistry.ids(), WeaponRegistry::get);
        bad += check("scudi", ShieldRegistry.ids(), ShieldRegistry::get);
        bad += check("armature", ArmorRegistry.ids(), ArmorRegistry::get);
        total = WeaponRegistry.ids().length + ShieldRegistry.ids().length + ArmorRegistry.ids().length;
        System.out.println(bad == 0 ? "OK: " + total + " valid items" : "ERRORS: " + bad + " of " + total);
    }

    private static int check(String label, String[] ids, Function<String, Item> factory) {
        int bad = 0;
        for (String id : ids) {
            try {
                Item item = factory.apply(id);
                Matcher m = TAIL.matcher(id);
                if (!m.find()) throw new IllegalStateException("id without _level_level_rarity");
                if (!item.rarity.name().equalsIgnoreCase(m.group(3))) throw new IllegalStateException("rarity " + item.rarity + " differs from the id");
                if (LootTable.create(id) == null) throw new IllegalStateException("LootTable cannot create it (duplicate id across tables?)");
            } catch (RuntimeException e) {
                bad++;
                System.out.println("[" + label + "] " + id + ": " + e);
            }
        }
        return bad;
    }
}
