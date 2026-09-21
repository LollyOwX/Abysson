package items;

import items.Weapon.WeaponSubtype;
import items.Weapon.ZoneType;
import java.awt.geom.Rectangle2D;

/**
 * Catalogo COMPLETO delle Difensive (Scudo/Broquel/Sai), generato dalla scheda Equip — 126
 * case (3 Type x 3 Build x 14 blocchi Level x Tier). Restano oggetti Weapon (WeaponSubtype
 * DIFENSIVE): questo file e' solo organizzazione, non un nuovo tipo di Item.
 *
 * guardia (-> DIFESA) e' il campo scalato per rarita'/livello — e' il loro Build (Defense/
 * Elemental Def/Special Def), stessa logica di Armor.metallo. pomo resta fisso. Generato da
 * script a partire dalla scheda Equip — non scritto a mano riga per riga.
 */
public class ShieldRegistry {
    public static Weapon get(String id) {
        switch (id) {
            // ══════ Shield ══════
            case "shield_defense_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da scudiero";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 1;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_elemental_def_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da scudiero";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 1;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_special_def_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da scudiero";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 1;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_defense_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudetto da guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 2;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_elemental_def_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudetto da guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 2;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_special_def_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudetto da guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 2;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_defense_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Targa di solida fattura";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 3;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                return w;
            }
            case "shield_elemental_def_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Targa di solida fattura";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 3;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                return w;
            }
            case "shield_special_def_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Targa di solida fattura";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 3;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                return w;
            }
            case "shield_defense_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da sentinella";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 4;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_elemental_def_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da sentinella";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 4;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_special_def_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da sentinella";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 4;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_defense_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Rinforzato di buona guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 5;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                return w;
            }
            case "shield_elemental_def_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Rinforzato di buona guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 5;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                return w;
            }
            case "shield_special_def_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Rinforzato di buona guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 5;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                return w;
            }
            case "shield_defense_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Torre del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 6;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Torre: puoi coprire anche un alleato senza perdere il turno";
                return w;
            }
            case "shield_elemental_def_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Torre del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 6;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Specchio Elementale: l'elemento assorbito diventa il tuo prossimo attacco";
                return w;
            }
            case "shield_special_def_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Torre del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 6;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Egida: il primo effetto di stato di ogni combattimento viene sempre annullato";
                return w;
            }
            case "shield_defense_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudetto da presidio";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 7;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_elemental_def_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudetto da presidio";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 7;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_special_def_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudetto da presidio";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 7;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_defense_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Targa di manifattura da assedio";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 8;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                return w;
            }
            case "shield_elemental_def_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Targa di manifattura da assedio";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 8;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                return w;
            }
            case "shield_special_def_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Targa di manifattura da assedio";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 8;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                return w;
            }
            case "shield_defense_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo del Gran Difensore";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 9;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Torre: puoi coprire anche un alleato senza perdere il turno";
                return w;
            }
            case "shield_elemental_def_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo del Gran Difensore";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 9;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Specchio Elementale: l'elemento assorbito diventa il tuo prossimo attacco";
                return w;
            }
            case "shield_special_def_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo del Gran Difensore";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 9;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Egida: il primo effetto di stato di ogni combattimento viene sempre annullato";
                return w;
            }
            case "shield_defense_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo Caduto";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 10;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Torre: puoi coprire anche un alleato senza perdere il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Bastione: puoi coprire tutti gli alleati contemporaneamente senza perdere il turno";
                return w;
            }
            case "shield_elemental_def_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo Caduto";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 10;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Specchio Elementale: l'elemento assorbito diventa il tuo prossimo attacco";
                w.mythicUniqueStub   = "[Mitico] STUB — Specchio Infinito: ogni attacco elementale assorbito diventa un attacco, non solo il prossimo";
                return w;
            }
            case "shield_special_def_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo Caduto";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 10;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Egida: il primo effetto di stato di ogni combattimento viene sempre annullato";
                w.mythicUniqueStub   = "[Mitico] STUB — Egida Suprema: ogni effetto di stato negativo viene annullato automaticamente";
                return w;
            }
            case "shield_defense_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da milizia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 11;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_elemental_def_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da milizia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 11;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_special_def_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo da milizia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 11;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "shield_defense_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Rinforzato di ferrea guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 12;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                return w;
            }
            case "shield_elemental_def_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Rinforzato di ferrea guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 12;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                return w;
            }
            case "shield_special_def_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Rinforzato di ferrea guardia";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 12;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                return w;
            }
            case "shield_defense_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Torre del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 13;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Torre: puoi coprire anche un alleato senza perdere il turno";
                return w;
            }
            case "shield_elemental_def_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Torre del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 13;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Specchio Elementale: l'elemento assorbito diventa il tuo prossimo attacco";
                return w;
            }
            case "shield_special_def_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo Torre del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 13;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Egida: il primo effetto di stato di ogni combattimento viene sempre annullato";
                return w;
            }
            case "shield_defense_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo Eterno";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 14;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% copertura; -% penalità di velocità dello scudo pesante";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Torre: puoi coprire anche un alleato senza perdere il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Bastione: puoi coprire tutti gli alleati contemporaneamente senza perdere il turno";
                return w;
            }
            case "shield_elemental_def_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo Eterno";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 14;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danno elementale assorbito; +% quantità convertita in risorsa";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Specchio Elementale: l'elemento assorbito diventa il tuo prossimo attacco";
                w.mythicUniqueStub   = "[Mitico] STUB — Specchio Infinito: ogni attacco elementale assorbito diventa un attacco, non solo il prossimo";
                return w;
            }
            case "shield_special_def_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo del Baluardo Eterno";
                w.affilatezza = 1; w.pomo = -2; w.guardia = 14;
                w.canDefend = true;
                w.staticGuard = true; // non ruota mai, non può essere eluso
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-30, -40, 60, 50), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(18, -38, 10, 14), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di annullamento; +% effetti annullabili per combattimento";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Egida: il primo effetto di stato di ogni combattimento viene sempre annullato";
                w.mythicUniqueStub   = "[Mitico] STUB — Egida Suprema: ogni effetto di stato negativo viene annullato automaticamente";
                return w;
            }

            // ══════ Broquel ══════
            case "broquel_defense_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 1;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_elemental_def_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 1;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_special_def_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 1;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_defense_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotellino da guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 2;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_elemental_def_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotellino da guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 2;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_special_def_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotellino da guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 2;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_defense_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotella di solida fattura";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 3;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                return w;
            }
            case "broquel_elemental_def_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotella di solida fattura";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 3;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                return w;
            }
            case "broquel_special_def_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotella di solida fattura";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 3;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                return w;
            }
            case "broquel_defense_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da sentinella";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 4;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_elemental_def_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da sentinella";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 4;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_special_def_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da sentinella";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 4;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_defense_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel Rinforzato di buona guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 5;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                return w;
            }
            case "broquel_elemental_def_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel Rinforzato di buona guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 5;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                return w;
            }
            case "broquel_special_def_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel Rinforzato di buona guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 5;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                return w;
            }
            case "broquel_defense_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Brocchiero del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 6;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Parata Leggera: parare non consuma il turno";
                return w;
            }
            case "broquel_elemental_def_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Brocchiero del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 6;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Scarico Rapido: puoi trasferire un effetto elementale attivo al nemico";
                return w;
            }
            case "broquel_special_def_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Brocchiero del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 6;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Tempo Esatto: una parata perfetta annulla completamente il round del nemico";
                return w;
            }
            case "broquel_defense_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotellino da presidio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 7;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_elemental_def_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotellino da presidio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 7;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_special_def_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotellino da presidio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 7;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_defense_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotella di manifattura da assedio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 8;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                return w;
            }
            case "broquel_elemental_def_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotella di manifattura da assedio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 8;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                return w;
            }
            case "broquel_special_def_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Rotella di manifattura da assedio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 8;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                return w;
            }
            case "broquel_defense_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante del Gran Difensore";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 9;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Parata Leggera: parare non consuma il turno";
                return w;
            }
            case "broquel_elemental_def_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante del Gran Difensore";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 9;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Scarico Rapido: puoi trasferire un effetto elementale attivo al nemico";
                return w;
            }
            case "broquel_special_def_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante del Gran Difensore";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 9;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Tempo Esatto: una parata perfetta annulla completamente il round del nemico";
                return w;
            }
            case "broquel_defense_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante Esiliato";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 10;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Parata Leggera: parare non consuma il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Parata Perpetua: puoi parare un numero illimitato di volte per round";
                return w;
            }
            case "broquel_elemental_def_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante Esiliato";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 10;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Scarico Rapido: puoi trasferire un effetto elementale attivo al nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Scarico Totale: puoi trasferire ogni effetto elementale attivo, non solo uno";
                return w;
            }
            case "broquel_special_def_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante Esiliato";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 10;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Tempo Esatto: una parata perfetta annulla completamente il round del nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Tempo Fermo: ogni parata è considerata perfetta";
                return w;
            }
            case "broquel_defense_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da milizia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 11;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_elemental_def_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da milizia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 11;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_special_def_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel da milizia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 11;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "broquel_defense_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel Rinforzato di ferrea guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 12;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                return w;
            }
            case "broquel_elemental_def_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel Rinforzato di ferrea guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 12;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                return w;
            }
            case "broquel_special_def_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel Rinforzato di ferrea guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 12;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                return w;
            }
            case "broquel_defense_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Brocchiero del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 13;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Parata Leggera: parare non consuma il turno";
                return w;
            }
            case "broquel_elemental_def_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Brocchiero del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 13;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Scarico Rapido: puoi trasferire un effetto elementale attivo al nemico";
                return w;
            }
            case "broquel_special_def_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Brocchiero del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 13;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Tempo Esatto: una parata perfetta annulla completamente il round del nemico";
                return w;
            }
            case "broquel_defense_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante Immortale";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 14;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% agilità in parata; -% finestra di recupero dopo la parata";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Parata Leggera: parare non consuma il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Parata Perpetua: puoi parare un numero illimitato di volte per round";
                return w;
            }
            case "broquel_elemental_def_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante Immortale";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 14;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "-% durata degli effetti elementali; +% prob. di scrollarli anticipatamente";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Scarico Rapido: puoi trasferire un effetto elementale attivo al nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Scarico Totale: puoi trasferire ogni effetto elementale attivo, non solo uno";
                return w;
            }
            case "broquel_special_def_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel del Duellante Immortale";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 14;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 30, 25), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-15, -20, 8, 10), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% finestra di parata perfetta; +% risorsa recuperata su parata perfetta";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Tempo Esatto: una parata perfetta annulla completamente il round del nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Tempo Fermo: ogni parata è considerata perfetta";
                return w;
            }

            // ══════ Sai (sword) ══════
            case "sai_defense_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 1;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_elemental_def_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 1;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_special_def_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 1;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_defense_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Uncino da guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 2;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_elemental_def_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Uncino da guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 2;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_special_def_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Uncino da guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 2;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_defense_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Forcella di solida fattura";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 3;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                return w;
            }
            case "sai_elemental_def_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Forcella di solida fattura";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 3;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                return w;
            }
            case "sai_special_def_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Forcella di solida fattura";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 3;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                return w;
            }
            case "sai_defense_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da sentinella";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 4;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_elemental_def_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da sentinella";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 4;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_special_def_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da sentinella";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 4;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_defense_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai Ricurvo di buona guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 5;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                return w;
            }
            case "sai_elemental_def_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai Ricurvo di buona guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 5;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                return w;
            }
            case "sai_special_def_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai Ricurvo di buona guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 5;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                return w;
            }
            case "sai_defense_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da Duello del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 6;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Blocco Istantaneo: puoi parare anche fuori dal tuo turno";
                return w;
            }
            case "sai_elemental_def_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da Duello del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 6;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ritorno: l'elemento riflesso innesca la reazione sul nemico, non su di te";
                return w;
            }
            case "sai_special_def_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da Duello del Maestro Scudiero";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 6;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Presa Rubata: disarmando il nemico puoi impugnare tu la sua arma";
                return w;
            }
            case "sai_defense_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Uncino da presidio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 7;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_elemental_def_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Uncino da presidio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 7;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_special_def_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Uncino da presidio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 7;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_defense_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Forcella di manifattura da assedio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 8;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                return w;
            }
            case "sai_elemental_def_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Forcella di manifattura da assedio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 8;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                return w;
            }
            case "sai_special_def_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Pugnale a Forcella di manifattura da assedio";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 8;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                return w;
            }
            case "sai_defense_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Maestro del Gran Difensore";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 9;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Blocco Istantaneo: puoi parare anche fuori dal tuo turno";
                return w;
            }
            case "sai_elemental_def_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Maestro del Gran Difensore";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 9;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ritorno: l'elemento riflesso innesca la reazione sul nemico, non su di te";
                return w;
            }
            case "sai_special_def_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Maestro del Gran Difensore";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 9;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Presa Rubata: disarmando il nemico puoi impugnare tu la sua arma";
                return w;
            }
            case "sai_defense_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Serpente Silenzioso";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 10;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Blocco Istantaneo: puoi parare anche fuori dal tuo turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Blocco Costante: puoi parare più volte fuori dal tuo turno, non solo una";
                return w;
            }
            case "sai_elemental_def_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Serpente Silenzioso";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 10;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ritorno: l'elemento riflesso innesca la reazione sul nemico, non su di te";
                w.mythicUniqueStub   = "[Mitico] STUB — Ritorno Totale: rifletti sempre sia il danno che l'effetto elementale, senza probabilità";
                return w;
            }
            case "sai_special_def_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Serpente Silenzioso";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 10;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Presa Rubata: disarmando il nemico puoi impugnare tu la sua arma";
                w.mythicUniqueStub   = "[Mitico] STUB — Presa Definitiva: disarmando il nemico, la sua arma sparisce dal combattimento";
                return w;
            }
            case "sai_defense_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da milizia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 11;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_elemental_def_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da milizia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 11;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_special_def_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da milizia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 11;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "sai_defense_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai Ricurvo di ferrea guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 12;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                return w;
            }
            case "sai_elemental_def_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai Ricurvo di ferrea guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 12;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                return w;
            }
            case "sai_special_def_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai Ricurvo di ferrea guardia";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 12;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                return w;
            }
            case "sai_defense_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da Duello del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 13;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Blocco Istantaneo: puoi parare anche fuori dal tuo turno";
                return w;
            }
            case "sai_elemental_def_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da Duello del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 13;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ritorno: l'elemento riflesso innesca la reazione sul nemico, non su di te";
                return w;
            }
            case "sai_special_def_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai da Duello del Guardiano Supremo";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 13;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Presa Rubata: disarmando il nemico puoi impugnare tu la sua arma";
                return w;
            }
            case "sai_defense_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Serpente Ancestrale";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 14;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% protezione istantanea dai danni taglienti";
                w.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Blocco Istantaneo: puoi parare anche fuori dal tuo turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Blocco Costante: puoi parare più volte fuori dal tuo turno, non solo una";
                return w;
            }
            case "sai_elemental_def_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Serpente Ancestrale";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 14;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danno elementale riflesso; +% prob. di riflettere anche l'effetto";
                w.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ritorno: l'elemento riflesso innesca la reazione sul nemico, non su di te";
                w.mythicUniqueStub   = "[Mitico] STUB — Ritorno Totale: rifletti sempre sia il danno che l'effetto elementale, senza probabilità";
                return w;
            }
            case "sai_special_def_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai del Serpente Ancestrale";
                w.affilatezza = 1; w.pomo = 2; w.guardia = 14;
                w.canDefend = true;
                w.restAngleDeg = 0;
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 35), ZoneType.RIGID));
                w.defenseZones.add(new Weapon.DefenseZone(new Rectangle2D.Double(-4, -35, 8, 6), ZoneType.WEAK));
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di disarmo su parata; +% durata del disarmo";
                w.fullSetOrStyleStub = "[Full Set: Voto] STUB — Presa Rubata: disarmando il nemico puoi impugnare tu la sua arma";
                w.mythicUniqueStub   = "[Mitico] STUB — Presa Definitiva: disarmando il nemico, la sua arma sparisce dal combattimento";
                return w;
            }

            default:
                System.err.println("Shield non trovato: " + id);
                return null;
        }
    }
}
