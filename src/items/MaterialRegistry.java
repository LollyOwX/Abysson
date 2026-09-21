package items;

/**
 * Risolve gli ID "materiale" di Weapon/Armor nel bonus % che generano — stesso concetto di
 * prima (ID globale: stesso id nello stesso campo risolve sempre allo stesso effetto), ma
 * implementato con array invece di switch: il catalogo generato dalla scheda Equip ha bisogno
 * di 14 "scaglioni di potere" (uno per blocco Level×Tier: 0-10 Common ... 40-50 Mythic), un
 * array è più compatto di 14+ case quasi identici.
 *
 * TIER/TIER_HALF sono le tabelle "scaglione -> bonus %", indicizzate direttamente dallo
 * scaglione (1 = 0-10 Common, ..., 14 = 40-50 Mythic; lo 0 esiste solo come "nessun bonus" per
 * i campi che restano a 0 in un Type che non usa quella stat):
 *   - blade()         (taglio/contundente/punta/peso, Weapon)      -> ATTACK
 *   - armorMetallo()  (metallo, Armor upper-body)                  -> DIFESA
 *   - offhandGuardia()(guardia, SOLO Shield/Broquel/Sai)            -> DIFESA
 *   - armorLegamenti()(legamenti, Armor gambe — scala DIMEZZATO rispetto agli altri: un bonus
 *                      di velocità pesa di più a parità di %, vedi Armor.computeBonusPercent)  -> VELOCITA
 *
 * pomo/guardia delle armi da MISCHIA restano invece un tratto FISSO della forma dell'arma (una
 * spada corta è sempre leggera, una balestra è sempre lenta), non scalano con rarità/livello —
 * weaponPomo()/weaponGuardia() sono quindi rimaste piccole tabelle a offset, INVARIATE dal giro
 * scorso.
 *
 * manico, il metallo/legamenti "durabilità" di Weapon, e il sostegno di Armor restano ID senza
 * tabella (vedi Weapon.java/Armor.java) — nessun metodo qui li risolve ancora.
 */
public class MaterialRegistry {

    // scaglione -> % — indice 0 = "nessun bonus" (Type che non usa questo campo), 1..14 = i 14
    // blocchi Level×Tier della scheda Equip, in ordine (0-10 Common ... 40-50 Mythic).
    private static final int[] TIER      = {0, 2, 4, 6, 8, 10, 13, 16, 19, 22, 26, 30, 34, 38, 42};
    private static final int[] TIER_HALF = {0, 1, 2, 3, 4,  5,  6,  8, 10, 11, 13, 15, 17, 19, 21};

    public static int blade(int id)          { return lookup(TIER, id, "blade"); }
    public static int armorMetallo(int id)   { return lookup(TIER, id, "armorMetallo"); }
    public static int offhandGuardia(int id) { return lookup(TIER, id, "offhandGuardia"); }
    public static int armorLegamenti(int id) { return lookup(TIER_HALF, id, "armorLegamenti"); }

    private static int lookup(int[] table, int id, String name) {
        if (id < 0 || id >= table.length) {
            System.err.println("MaterialRegistry." + name + ": id sconosciuto " + id);
            return 0;
        }
        return table[id];
    }

    // ── Tratti fissi delle armi da mischia (NON scalano con rarità/livello) ─────
    // Range invariato dal giro scorso: -3..4 per pomo, -2..7 per guardia, con offset per
    // indicizzare un array senza id negativi diretti.
    private static final int[] WEAPON_POMO    = {-6, -4, -2, 0, 2, 4, 6, 8};       // indice = id + 3
    private static final int[] WEAPON_GUARDIA = {-4, -2, 0, 2, 4, 6, 8, 10, 12, 14}; // indice = id + 2

    public static int weaponPomo(int id) {
        int idx = id + 3;
        if (idx < 0 || idx >= WEAPON_POMO.length) { System.err.println("MaterialRegistry.weaponPomo: id sconosciuto " + id); return 0; }
        return WEAPON_POMO[idx];
    }

    public static int weaponGuardia(int id) {
        int idx = id + 2;
        if (idx < 0 || idx >= WEAPON_GUARDIA.length) { System.err.println("MaterialRegistry.weaponGuardia: id sconosciuto " + id); return 0; }
        return WEAPON_GUARDIA[idx];
    }
}
