package items;

import items.Weapon.WeaponSubtype;
import items.Weapon.ZoneType;

import java.awt.geom.Rectangle2D;

/**
 * Dispatcher statico per le Difensive (Scudo/Broquel/Sai) — stesso pattern di WeaponRegistry/
 * ArmorRegistry, ma separato in un template a sé (terzo tipo oltre Armi/Armatura, come nella
 * scheda Equip). Restano oggetti Weapon (WeaponSubtype.DIFENSIVE): questo file è solo
 * organizzazione, non un nuovo tipo di Item.
 */
public class ShieldRegistry {
    public static Weapon get(String id) {
        switch (id) {
            case "shield_basic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo"; w.description = "Largo e solido, ma appesantisce.";
                w.affilatezza = 1; w.pomo = -4; w.manico = 3; w.guardia = 14;
                w.metallo = 50; w.legamenti = 45;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK)); // il fermaglio, in alto a destra
                return w;
            }
            case "buckler_basic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel"; w.description = "Piccolo e veloce, protegge meno di uno scudo.";
                w.affilatezza = 1; w.pomo = 4; w.manico = 5; w.guardia = 6;
                w.metallo = 30; w.legamenti = 30;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK)); // il bordo, piccolo e vicino al centro (facile da beccare)
                return w;
            }
            case "sai_basic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai"; w.description = "Una spada secondaria per la mano debole.";
                w.affilatezza = 1; w.pomo = 3; w.manico = 6; w.guardia = 3;
                w.taglio = 4; w.punta = 3; w.metallo = 25; w.legamenti = 25;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK)); // la punta, stretta
                return w;
            }

            // ── Template rarità: esempio Mythic ────────────────────
            // G+H+I tutti valorizzati (cumulativo). Testo dalla scheda Equip
            // (Type "Shield", Build "Defense").
            case "shield_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo (Mythic)"; w.description = "Largo e solido, ma appesantisce.";
                w.affilatezza = 1; w.pomo = -4; w.manico = 3; w.guardia = 14;
                w.metallo = 50; w.legamenti = 45;
                w.canDefend = true;
                w.staticGuard = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "STUB"; // G: "+% copertura; -% penalità di velocità dello scudo pesante"
                w.fullSetOrStyleStub = "STUB"; // H: [Full Set: Guardia] Torre
                w.mythicUniqueStub   = "STUB"; // I: [Mitico] Bastione
                return w;
            }

            default:
                System.err.println("Shield non trovato: " + id);
                return null;
        }
    }
}