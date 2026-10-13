package items;

import java.util.Arrays;

/**
 * Dati di prova per vedere l'Inventario nel libro (nessun oggetto reale ha ancora icone o dati
 * per queste zone). Impostare ENABLED = false per partire con l'inventario vuoto.
 */
public final class InventoryDemo {
    private InventoryDemo() {}

    public static final boolean ENABLED = true;

    public static void seed(Inventory inv) {
        if (!ENABLED) return;

        for (String key : new String[]{"mace_atk_20_30_masterwork", "claymore_atk_40_50_mythic", "short_bow_precision_10_20",
                "shield_defense_20_30_masterwork", "helmet_defense_0_10_common"}) {
            String id = find(LootIndex.WEAPONS, key);
            if (id == null) id = find(LootIndex.SHIELDS, key);
            if (id == null) id = find(LootIndex.ARMORS, key);
            if (id != null) inv.addGear(LootTable.create(id));
        }

        Consumable potion = new Consumable("Pozione di vita", 40, 5);
        potion.gains.add(new StatLine("VIT", StatLine.Kind.FLAT, 150));
        potion.gains.add(new StatLine("DEF", StatLine.Kind.PERCENT, 10));
        potion.effects.add(new Consumable.Effect(null, "Rigenera 5 vita al turno"));
        potion.effects.add(new Consumable.Effect(null, "Rimuove il veleno"));
        inv.consumables.add(potion);
        Consumable elixir = new Consumable("Elisir del fulmine", 220, 2);
        elixir.gains.add(new StatLine("ELECTRIC-ATK", StatLine.Kind.MULTIPLIER, 1.25));
        inv.consumables.add(elixir);

        Precious gem = new Precious("Rubino grezzo", 450, 3, 0.15, 0.10);
        gem.cities.add(new Precious.City("Valmora", 520, 0xFFC03030));
        gem.cities.add(new Precious.City("Brinhaven", 380, 0xFF3060C0));
        gem.cities.add(new Precious.City("Ostia Alta", 610, 0xFF30A050));
        inv.precious.add(gem);
        inv.precious.add(new Precious("Statuetta d'oro", 1800, 1, 0.20, 0.05));

        inv.questItems.add(new QuestItem("Lettera sigillata", "Una lettera per il capovilla di Valmora, scritta in fretta e sigillata con cera rossa. Non va aperta.", QuestItem.Kind.ARCH).due(17, true));
        inv.questItems.add(new QuestItem("Erbe medicinali", "Un fascio di erbe raccolte per la guaritrice: servono entro la fine del mese.", QuestItem.Kind.NICHE).due(24, false));
        inv.questItems.add(new QuestItem("Chiave di ferro", "Apre una porta che nessuno ricorda più. Non va consegnata a nessuno.", QuestItem.Kind.SPECIAL));

        Resource wood = new Resource("Legno grezzo", 4, 36, "Tronchi tagliati nei boschi a nord. Si lavora alla sega.");
        wood.usages.addAll(Arrays.asList(new String[24]));
        inv.resources.add(wood);
        for (String n : new String[]{"Ferro", "Pietra", "Cuoio", "Lino", "Argilla", "Carbone", "Sale", "Corda", "Resina", "Lana", "Rame", "Vetro"}) {
            inv.resources.add(new Resource(n, 6, 12, "Materiale comune."));
        }
    }

    private static String find(String[] ids, String key) {
        for (String id : ids) if (id.contains(key)) return id;
        return null;
    }
}
