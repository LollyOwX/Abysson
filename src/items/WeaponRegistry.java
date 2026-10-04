package items;

import items.Weapon.WeaponSubtype;

/**
 * Catalogo COMPLETO delle armi (mischia + distanza), generato dalla scheda Equip — 546 case
 * (13 Type mischia/distanza x 3 Build x 14 blocchi Level x Tier). Ogni chiamata a get() crea
 * un'istanza NUOVA.
 *
 * Per Type, i campi grezzi che pesano su ATTACK (taglio/contundente/punta/peso) NON sono più 3
 * numeri arbitrari per riga come nei primi esempi a mano: un solo campo "primario" per Type
 * porta l'ID scalato per rarita'/livello (MaterialRegistry.blade()), lo stesso su tutti e 3 i
 * Build dello stesso Type (F/Bonus era gia' unito per Type nella scheda: stessa arma fisica,
 * cambia solo cosa ci hai imbuito) — solo rarity/G/H/I cambiano tra Atk/Elemental Atk/Special
 * Atk. pomo/guardia restano un tratto FISSO della forma dell'arma (vedi MaterialRegistry),
 * NON scalano con rarita'/livello. Generato da script a partire dalla scheda Equip — non
 * scritto a mano riga per riga.
 */
public class WeaponRegistry {
    public static Weapon get(String id) {
        switch (id) {
            // ══════ Short Sword ══════
            case "short_sword_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta semplice";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta semplice";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta semplice";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Breve da recluta";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Breve da recluta";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Breve da recluta";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Corta di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                return w;
            }
            case "short_sword_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Corta di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                return w;
            }
            case "short_sword_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Corta di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                return w;
            }
            case "short_sword_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta da apprendista";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta da apprendista";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta da apprendista";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Snella di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                return w;
            }
            case "short_sword_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Snella di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                return w;
            }
            case "short_sword_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Snella di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                return w;
            }
            case "short_sword_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Stocco del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppio Tempo: il secondo colpo dello stesso turno non può essere parato";
                return w;
            }
            case "short_sword_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Stocco del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Catena Breve: la Scossa al massimo salta a un secondo nemico";
                return w;
            }
            case "short_sword_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Stocco del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Filo Crescente: tre colpi consecutivi a segno garantiscono un critico";
                return w;
            }
            case "short_sword_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Breve da soldato";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Breve da soldato";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Breve da soldato";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Corta di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                return w;
            }
            case "short_sword_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Corta di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                return w;
            }
            case "short_sword_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama Corta di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                return w;
            }
            case "short_sword_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Filo Corto del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppio Tempo: il secondo colpo dello stesso turno non può essere parato";
                return w;
            }
            case "short_sword_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Filo Corto del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Catena Breve: la Scossa al massimo salta a un secondo nemico";
                return w;
            }
            case "short_sword_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Filo Corto del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Filo Crescente: tre colpi consecutivi a segno garantiscono un critico";
                return w;
            }
            case "short_sword_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama del Giuramento Infranto";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppio Tempo: il secondo colpo dello stesso turno non può essere parato";
                w.mythicUniqueStub   = "[Mitico] STUB — Terzo Tempo: hai prob. di agire una terza volta nello stesso round";
                return w;
            }
            case "short_sword_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama del Giuramento Infranto";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Catena Breve: la Scossa al massimo salta a un secondo nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Catena Infinita: la Scossa al massimo salta a tutti i nemici in campo";
                return w;
            }
            case "short_sword_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama del Giuramento Infranto";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Filo Crescente: tre colpi consecutivi a segno garantiscono un critico";
                w.mythicUniqueStub   = "[Mitico] STUB — Filo Perfetto: ogni colpo dopo il primo critico della serie è automaticamente critico";
                return w;
            }
            case "short_sword_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta da veterano";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta da veterano";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta da veterano";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_sword_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Snella di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                return w;
            }
            case "short_sword_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Snella di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                return w;
            }
            case "short_sword_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Snella di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                return w;
            }
            case "short_sword_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Stocco del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppio Tempo: il secondo colpo dello stesso turno non può essere parato";
                return w;
            }
            case "short_sword_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Stocco del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Catena Breve: la Scossa al massimo salta a un secondo nemico";
                return w;
            }
            case "short_sword_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Stocco del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Filo Crescente: tre colpi consecutivi a segno garantiscono un critico";
                return w;
            }
            case "short_sword_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama dell'Ultimo Giuramento";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% controllo; +% prob. di agire due volte nel round";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppio Tempo: il secondo colpo dello stesso turno non può essere parato";
                w.mythicUniqueStub   = "[Mitico] STUB — Terzo Tempo: hai prob. di agire una terza volta nello stesso round";
                return w;
            }
            case "short_sword_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama dell'Ultimo Giuramento";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fulmine; +% accumulo di Scossa per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Catena Breve: la Scossa al massimo salta a un secondo nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Catena Infinita: la Scossa al massimo salta a tutti i nemici in campo";
                return w;
            }
            case "short_sword_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Lama dell'Ultimo Giuramento";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 2;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di critico crescente a ogni colpo consecutivo a segno";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Filo Crescente: tre colpi consecutivi a segno garantiscono un critico";
                w.mythicUniqueStub   = "[Mitico] STUB — Filo Perfetto: ogni colpo dopo il primo critico della serie è automaticamente critico";
                return w;
            }

            // ══════ Long Sword ══════
            case "long_sword_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga semplice";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga semplice";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga semplice";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Distesa da recluta";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Distesa da recluta";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Distesa da recluta";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Lunga di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                return w;
            }
            case "long_sword_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Lunga di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                return w;
            }
            case "long_sword_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Lunga di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                return w;
            }
            case "long_sword_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga da apprendista";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga da apprendista";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga da apprendista";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Slanciata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                return w;
            }
            case "long_sword_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Slanciata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                return w;
            }
            case "long_sword_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Slanciata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                return w;
            }
            case "long_sword_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada da Guerra del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Fendente Pulito: i colpi da taglio applicano sempre sanguinamento";
                return w;
            }
            case "long_sword_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada da Guerra del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Lama Solare: Raggio acceca anche i nemici adiacenti al bersaglio";
                return w;
            }
            case "long_sword_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada da Guerra del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Risposta: una parata perfetta concede un attacco immediato gratuito";
                return w;
            }
            case "long_sword_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Distesa da soldato";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Distesa da soldato";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Distesa da soldato";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Lunga di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                return w;
            }
            case "long_sword_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Lunga di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                return w;
            }
            case "long_sword_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama Lunga di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                return w;
            }
            case "long_sword_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama del Duca del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Fendente Pulito: i colpi da taglio applicano sempre sanguinamento";
                return w;
            }
            case "long_sword_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama del Duca del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Lama Solare: Raggio acceca anche i nemici adiacenti al bersaglio";
                return w;
            }
            case "long_sword_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Lama del Duca del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Risposta: una parata perfetta concede un attacco immediato gratuito";
                return w;
            }
            case "long_sword_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada del Cavaliere Esiliato";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Fendente Pulito: i colpi da taglio applicano sempre sanguinamento";
                w.mythicUniqueStub   = "[Mitico] STUB — Taglio Netto: il sanguinamento applicato non può mai essere curato dal bersaglio";
                return w;
            }
            case "long_sword_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada del Cavaliere Esiliato";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Lama Solare: Raggio acceca anche i nemici adiacenti al bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Alba: Raggio si innesca automaticamente al primo colpo di ogni combattimento";
                return w;
            }
            case "long_sword_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada del Cavaliere Esiliato";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Risposta: una parata perfetta concede un attacco immediato gratuito";
                w.mythicUniqueStub   = "[Mitico] STUB — Contro Assoluto: ogni parata, perfetta o normale, concede un attacco immediato";
                return w;
            }
            case "long_sword_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga da veterano";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga da veterano";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga da veterano";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "long_sword_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Slanciata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                return w;
            }
            case "long_sword_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Slanciata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                return w;
            }
            case "long_sword_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Slanciata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                return w;
            }
            case "long_sword_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada da Guerra del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Fendente Pulito: i colpi da taglio applicano sempre sanguinamento";
                return w;
            }
            case "long_sword_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada da Guerra del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Lama Solare: Raggio acceca anche i nemici adiacenti al bersaglio";
                return w;
            }
            case "long_sword_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada da Guerra del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Risposta: una parata perfetta concede un attacco immediato gratuito";
                return w;
            }
            case "long_sword_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada del Re Sepolto";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da taglio; +% penetrazione contro armature leggere";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Fendente Pulito: i colpi da taglio applicano sempre sanguinamento";
                w.mythicUniqueStub   = "[Mitico] STUB — Taglio Netto: il sanguinamento applicato non può mai essere curato dal bersaglio";
                return w;
            }
            case "long_sword_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada del Re Sepolto";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Luce; +% prob. di innescare Raggio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Lama Solare: Raggio acceca anche i nemici adiacenti al bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Alba: Raggio si innesca automaticamente al primo colpo di ogni combattimento";
                return w;
            }
            case "long_sword_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada del Re Sepolto";
                w.affilatezza = 1; w.pomo[0] = 2; w.guardia[0] = 2;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni sul colpo successivo a una parata; +% finestra di parata";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Risposta: una parata perfetta concede un attacco immediato gratuito";
                w.mythicUniqueStub   = "[Mitico] STUB — Contro Assoluto: ogni parata, perfetta o normale, concede un attacco immediato";
                return w;
            }

            // ══════ Whip ══════
            case "whip_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta semplice";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta semplice";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta semplice";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Staffile da recluta";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Staffile da recluta";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Staffile da recluta";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                return w;
            }
            case "whip_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                return w;
            }
            case "whip_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                return w;
            }
            case "whip_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta da apprendista";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta da apprendista";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta da apprendista";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                return w;
            }
            case "whip_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                return w;
            }
            case "whip_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                return w;
            }
            case "whip_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Flagello del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Morsa: il nemico disarmato non può raccogliere l'arma per un round";
                return w;
            }
            case "whip_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Flagello del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Sferza di Vento: ogni bersaglio colpito propaga metà dell'effetto elementale";
                return w;
            }
            case "whip_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Flagello del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Redine: puoi trascinare un nemico a distanza e obbligarlo a perdere il turno";
                return w;
            }
            case "whip_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Staffile da soldato";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Staffile da soldato";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Staffile da soldato";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                return w;
            }
            case "whip_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                return w;
            }
            case "whip_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                return w;
            }
            case "whip_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza da Duello del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Morsa: il nemico disarmato non può raccogliere l'arma per un round";
                return w;
            }
            case "whip_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza da Duello del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Sferza di Vento: ogni bersaglio colpito propaga metà dell'effetto elementale";
                return w;
            }
            case "whip_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Sferza da Duello del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Redine: puoi trascinare un nemico a distanza e obbligarlo a perdere il turno";
                return w;
            }
            case "whip_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta del Boia Silente";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Morsa: il nemico disarmato non può raccogliere l'arma per un round";
                w.mythicUniqueStub   = "[Mitico] STUB — Catena Spezzata: il nemico disarmato non recupera mai più la sua arma in quel combattimento";
                return w;
            }
            case "whip_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta del Boia Silente";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Sferza di Vento: ogni bersaglio colpito propaga metà dell'effetto elementale";
                w.mythicUniqueStub   = "[Mitico] STUB — Uragano: ogni bersaglio colpito propaga l'effetto elementale per intero, non a metà";
                return w;
            }
            case "whip_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta del Boia Silente";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Redine: puoi trascinare un nemico a distanza e obbligarlo a perdere il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Marionetta: il nemico trascinato agisce al posto tuo per un turno, sotto il tuo controllo";
                return w;
            }
            case "whip_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta da veterano";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta da veterano";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta da veterano";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "whip_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                return w;
            }
            case "whip_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                return w;
            }
            case "whip_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                return w;
            }
            case "whip_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Flagello del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Morsa: il nemico disarmato non può raccogliere l'arma per un round";
                return w;
            }
            case "whip_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Flagello del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Sferza di Vento: ogni bersaglio colpito propaga metà dell'effetto elementale";
                return w;
            }
            case "whip_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Flagello del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Redine: puoi trascinare un nemico a distanza e obbligarlo a perdere il turno";
                return w;
            }
            case "whip_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta dell'Inquisitore Eterno";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di disarmare; +% gittata effettiva";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Morsa: il nemico disarmato non può raccogliere l'arma per un round";
                w.mythicUniqueStub   = "[Mitico] STUB — Catena Spezzata: il nemico disarmato non recupera mai più la sua arma in quel combattimento";
                return w;
            }
            case "whip_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta dell'Inquisitore Eterno";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Aria; +% numero di bersagli colpiti";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Sferza di Vento: ogni bersaglio colpito propaga metà dell'effetto elementale";
                w.mythicUniqueStub   = "[Mitico] STUB — Uragano: ogni bersaglio colpito propaga l'effetto elementale per intero, non a metà";
                return w;
            }
            case "whip_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta dell'Inquisitore Eterno";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di inciampo; +% durata dell'inciampo";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Redine: puoi trascinare un nemico a distanza e obbligarlo a perdere il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Marionetta: il nemico trascinato agisce al posto tuo per un turno, sotto il tuo controllo";
                return w;
            }

            // ══════ Mace ══════
            case "mace_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 41;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 41;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 41;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Randello da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Randello da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Randello da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Clava d'Acciaio di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                return w;
            }
            case "mace_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Clava d'Acciaio di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                return w;
            }
            case "mace_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Clava d'Acciaio di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                return w;
            }
            case "mace_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 43;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 43;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 43;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Ferrata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 44;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                return w;
            }
            case "mace_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Ferrata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 44;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                return w;
            }
            case "mace_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Ferrata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 44;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                return w;
            }
            case "mace_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello da Guerra del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 45;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Sfondamento: ignori completamente l'armatura pesante";
                return w;
            }
            case "mace_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello da Guerra del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 45;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Frantume: la Rottura al massimo distrugge un pezzo di armatura del nemico";
                return w;
            }
            case "mace_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello da Guerra del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 45;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Colpo Cieco: un colpo per combattimento ignora ogni difesa e resistenza";
                return w;
            }
            case "mace_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Randello da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 46;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Randello da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 46;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Randello da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 46;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Clava d'Acciaio di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 48;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                return w;
            }
            case "mace_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Clava d'Acciaio di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 48;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                return w;
            }
            case "mace_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Clava d'Acciaio di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 48;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                return w;
            }
            case "mace_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza del Campione del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 49;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Sfondamento: ignori completamente l'armatura pesante";
                return w;
            }
            case "mace_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza del Campione del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 49;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Frantume: la Rottura al massimo distrugge un pezzo di armatura del nemico";
                return w;
            }
            case "mace_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza del Campione del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 49;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Colpo Cieco: un colpo per combattimento ignora ogni difesa e resistenza";
                return w;
            }
            case "mace_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza del Colosso Caduto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 50;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Sfondamento: ignori completamente l'armatura pesante";
                w.mythicUniqueStub   = "[Mitico] STUB — Frantumazione: ignori completamente ogni tipo di armatura";
                return w;
            }
            case "mace_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza del Colosso Caduto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 50;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Frantume: la Rottura al massimo distrugge un pezzo di armatura del nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Terremoto: la Rottura al massimo distrugge tutta l'armatura del nemico in un colpo";
                return w;
            }
            case "mace_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza del Colosso Caduto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 50;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Colpo Cieco: un colpo per combattimento ignora ogni difesa e resistenza";
                w.mythicUniqueStub   = "[Mitico] STUB — Colpo del Giudizio: ogni colpo con questa arma ignora ogni difesa e resistenza";
                return w;
            }
            case "mace_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 52;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 52;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 52;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "mace_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Ferrata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 54;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                return w;
            }
            case "mace_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Ferrata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 54;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                return w;
            }
            case "mace_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Ferrata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 54;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                return w;
            }
            case "mace_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello da Guerra del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 55;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Sfondamento: ignori completamente l'armatura pesante";
                return w;
            }
            case "mace_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello da Guerra del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 55;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Frantume: la Rottura al massimo distrugge un pezzo di armatura del nemico";
                return w;
            }
            case "mace_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello da Guerra del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 55;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Colpo Cieco: un colpo per combattimento ignora ogni difesa e resistenza";
                return w;
            }
            case "mace_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello della Fine dei Giorni";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 57;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni contundenti e perforanti; +% danni contro armature pesanti";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Sfondamento: ignori completamente l'armatura pesante";
                w.mythicUniqueStub   = "[Mitico] STUB — Frantumazione: ignori completamente ogni tipo di armatura";
                return w;
            }
            case "mace_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello della Fine dei Giorni";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 57;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Terra; +% accumulo di Rottura per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Frantume: la Rottura al massimo distrugge un pezzo di armatura del nemico";
                w.mythicUniqueStub   = "[Mitico] STUB — Terremoto: la Rottura al massimo distrugge tutta l'armatura del nemico in un colpo";
                return w;
            }
            case "mace_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Martello della Fine dei Giorni";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 57;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% difesa ignorata; +% danni contro nemici già in Rottura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Colpo Cieco: un colpo per combattimento ignora ogni difesa e resistenza";
                w.mythicUniqueStub   = "[Mitico] STUB — Colpo del Giudizio: ogni colpo con questa arma ignora ogni difesa e resistenza";
                return w;
            }

            // ══════ Axe ══════
            case "axe_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 41;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 41;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 41;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Accetta da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Accetta da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Accetta da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                return w;
            }
            case "axe_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                return w;
            }
            case "axe_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 42;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                return w;
            }
            case "axe_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 43;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 43;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 43;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da Battaglia di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 44;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                return w;
            }
            case "axe_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da Battaglia di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 44;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                return w;
            }
            case "axe_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da Battaglia di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 44;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                return w;
            }
            case "axe_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Bipenne del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 45;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Spacca-Scudo: i tuoi colpi attraversano scudi e parate";
                return w;
            }
            case "axe_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Bipenne del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 45;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Brace Viva: l'Infiammazione non scade finché il bersaglio subisce colpi";
                return w;
            }
            case "axe_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Bipenne del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 45;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Accanimento: il bonus accumulato non si azzera se cambi bersaglio";
                return w;
            }
            case "axe_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Accetta da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 46;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Accetta da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 46;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Accetta da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 46;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 48;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                return w;
            }
            case "axe_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 48;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                return w;
            }
            case "axe_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 48;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                return w;
            }
            case "axe_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure del Boia del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 49;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Spacca-Scudo: i tuoi colpi attraversano scudi e parate";
                return w;
            }
            case "axe_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure del Boia del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 49;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Brace Viva: l'Infiammazione non scade finché il bersaglio subisce colpi";
                return w;
            }
            case "axe_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Scure del Boia del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 49;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Accanimento: il bonus accumulato non si azzera se cambi bersaglio";
                return w;
            }
            case "axe_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia del Boscaiolo Maledetto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 50;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Spacca-Scudo: i tuoi colpi attraversano scudi e parate";
                w.mythicUniqueStub   = "[Mitico] STUB — Spacca-Tutto: i tuoi colpi non possono mai essere bloccati o parati";
                return w;
            }
            case "axe_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia del Boscaiolo Maledetto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 50;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Brace Viva: l'Infiammazione non scade finché il bersaglio subisce colpi";
                w.mythicUniqueStub   = "[Mitico] STUB — Incendio Eterno: l'Infiammazione applicata non si esaurisce mai finché il bersaglio è in vita";
                return w;
            }
            case "axe_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia del Boscaiolo Maledetto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 50;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Accanimento: il bonus accumulato non si azzera se cambi bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Furia Instancabile: il bonus accumulato non ha limite massimo";
                return w;
            }
            case "axe_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 52;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 52;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 52;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "axe_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da Battaglia di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 54;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                return w;
            }
            case "axe_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da Battaglia di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 54;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                return w;
            }
            case "axe_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia da Battaglia di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 54;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                return w;
            }
            case "axe_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Bipenne del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 55;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Spacca-Scudo: i tuoi colpi attraversano scudi e parate";
                return w;
            }
            case "axe_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Bipenne del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 55;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Brace Viva: l'Infiammazione non scade finché il bersaglio subisce colpi";
                return w;
            }
            case "axe_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Bipenne del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 55;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Accanimento: il bonus accumulato non si azzera se cambi bersaglio";
                return w;
            }
            case "axe_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia del Titano Sopito";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 57;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni taglienti e contundenti; +% danni sui colpi caricati";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Spacca-Scudo: i tuoi colpi attraversano scudi e parate";
                w.mythicUniqueStub   = "[Mitico] STUB — Spacca-Tutto: i tuoi colpi non possono mai essere bloccati o parati";
                return w;
            }
            case "axe_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia del Titano Sopito";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 57;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fuoco; +% durata di Infiammazione";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Brace Viva: l'Infiammazione non scade finché il bersaglio subisce colpi";
                w.mythicUniqueStub   = "[Mitico] STUB — Incendio Eterno: l'Infiammazione applicata non si esaurisce mai finché il bersaglio è in vita";
                return w;
            }
            case "axe_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia del Titano Sopito";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 57;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni cumulativi a ogni colpo consecutivo sullo stesso bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Accanimento: il bonus accumulato non si azzera se cambi bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Furia Instancabile: il bonus accumulato non ha limite massimo";
                return w;
            }

            // ══════ Claymore ══════
            case "claymore_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone semplice";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone semplice";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone semplice";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Massiccia da recluta";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Massiccia da recluta";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Massiccia da recluta";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Grande Spada di buona fattura";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 2;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                return w;
            }
            case "claymore_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Grande Spada di buona fattura";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 2;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                return w;
            }
            case "claymore_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Grande Spada di buona fattura";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 2;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                return w;
            }
            case "claymore_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone da apprendista";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 3;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone da apprendista";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 3;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone da apprendista";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 3;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone Affilato di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 4;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                return w;
            }
            case "claymore_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone Affilato di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 4;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                return w;
            }
            case "claymore_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone Affilato di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 4;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                return w;
            }
            case "claymore_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Immane del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 5;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Peso della Lama: ogni colpo a segno respinge il bersaglio di una casella";
                return w;
            }
            case "claymore_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Immane del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 5;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Onda Tellurica: l'area colpita resta pericolosa per un round";
                return w;
            }
            case "claymore_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Immane del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 5;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mulinello: il colpo caricato colpisce tutti i nemici e non può essere interrotto";
                return w;
            }
            case "claymore_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Massiccia da soldato";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 6;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Massiccia da soldato";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 6;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Massiccia da soldato";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 6;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Grande Spada di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                return w;
            }
            case "claymore_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Grande Spada di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                return w;
            }
            case "claymore_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Grande Spada di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                return w;
            }
            case "claymore_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone del Titano del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Peso della Lama: ogni colpo a segno respinge il bersaglio di una casella";
                return w;
            }
            case "claymore_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone del Titano del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Onda Tellurica: l'area colpita resta pericolosa per un round";
                return w;
            }
            case "claymore_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone del Titano del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mulinello: il colpo caricato colpisce tutti i nemici e non può essere interrotto";
                return w;
            }
            case "claymore_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone dell'Eroe Caduto";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Peso della Lama: ogni colpo a segno respinge il bersaglio di una casella";
                w.mythicUniqueStub   = "[Mitico] STUB — Peso del Mondo: ogni colpo respinge e stordisce brevemente il bersaglio";
                return w;
            }
            case "claymore_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone dell'Eroe Caduto";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Onda Tellurica: l'area colpita resta pericolosa per un round";
                w.mythicUniqueStub   = "[Mitico] STUB — Faglia: l'area colpita resta pericolosa finché dura il combattimento";
                return w;
            }
            case "claymore_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone dell'Eroe Caduto";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mulinello: il colpo caricato colpisce tutti i nemici e non può essere interrotto";
                w.mythicUniqueStub   = "[Mitico] STUB — Apocalisse: il colpo caricato non richiede più alcun turno di carica";
                return w;
            }
            case "claymore_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone da veterano";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 12;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone da veterano";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 12;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone da veterano";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 12;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "claymore_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone Affilato di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 14;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                return w;
            }
            case "claymore_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone Affilato di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 14;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                return w;
            }
            case "claymore_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone Affilato di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 14;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                return w;
            }
            case "claymore_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Immane del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 15;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Peso della Lama: ogni colpo a segno respinge il bersaglio di una casella";
                return w;
            }
            case "claymore_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Immane del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 15;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Onda Tellurica: l'area colpita resta pericolosa per un round";
                return w;
            }
            case "claymore_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Lama Immane del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 15;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mulinello: il colpo caricato colpisce tutti i nemici e non può essere interrotto";
                return w;
            }
            case "claymore_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone del Sovrano Immortale";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 17;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% peso convertito in danno; +% danni sui nemici più leggeri di te";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Peso della Lama: ogni colpo a segno respinge il bersaglio di una casella";
                w.mythicUniqueStub   = "[Mitico] STUB — Peso del Mondo: ogni colpo respinge e stordisce brevemente il bersaglio";
                return w;
            }
            case "claymore_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone del Sovrano Immortale";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 17;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni elementali ad area; +% raggio dell'area colpita";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Onda Tellurica: l'area colpita resta pericolosa per un round";
                w.mythicUniqueStub   = "[Mitico] STUB — Faglia: l'area colpita resta pericolosa finché dura il combattimento";
                return w;
            }
            case "claymore_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone del Sovrano Immortale";
                w.affilatezza = 1; w.pomo[0] = -1; w.guardia[0] = 3;
                w.punta[0] = 17;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni del colpo caricato; -% turni di carica necessari";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mulinello: il colpo caricato colpisce tutti i nemici e non può essere interrotto";
                w.mythicUniqueStub   = "[Mitico] STUB — Apocalisse: il colpo caricato non richiede più alcun turno di carica";
                return w;
            }

            // ══════ Spear ══════
            case "spear_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta Appuntita da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta Appuntita da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta Appuntita da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta da Guerra di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                return w;
            }
            case "spear_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta da Guerra di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                return w;
            }
            case "spear_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta da Guerra di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                return w;
            }
            case "spear_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                return w;
            }
            case "spear_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                return w;
            }
            case "spear_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                return w;
            }
            case "spear_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia da Cavaliere del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppia Punta: puoi cambiare tipo di danno a metà attacco, dopo aver visto la difesa";
                return w;
            }
            case "spear_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia da Cavaliere del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Getto Continuo: l'Abrasione si propaga ai nemici in linea con il bersaglio";
                return w;
            }
            case "spear_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia da Cavaliere del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Zona di Minaccia: ogni nemico che entra a portata subisce un attacco automatico";
                return w;
            }
            case "spear_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta Appuntita da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta Appuntita da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta Appuntita da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta da Guerra di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                return w;
            }
            case "spear_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta da Guerra di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                return w;
            }
            case "spear_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta da Guerra di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                return w;
            }
            case "spear_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta del Campione del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppia Punta: puoi cambiare tipo di danno a metà attacco, dopo aver visto la difesa";
                return w;
            }
            case "spear_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta del Campione del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Getto Continuo: l'Abrasione si propaga ai nemici in linea con il bersaglio";
                return w;
            }
            case "spear_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Asta del Campione del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Zona di Minaccia: ogni nemico che entra a portata subisce un attacco automatico";
                return w;
            }
            case "spear_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia dell'Alba Spezzata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppia Punta: puoi cambiare tipo di danno a metà attacco, dopo aver visto la difesa";
                w.mythicUniqueStub   = "[Mitico] STUB — Punta Perfetta: scegli sempre il tipo di danno più efficace, automaticamente";
                return w;
            }
            case "spear_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia dell'Alba Spezzata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Getto Continuo: l'Abrasione si propaga ai nemici in linea con il bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Diluvio: l'Abrasione si propaga a tutti i nemici in campo, non solo in linea";
                return w;
            }
            case "spear_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia dell'Alba Spezzata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Zona di Minaccia: ogni nemico che entra a portata subisce un attacco automatico";
                w.mythicUniqueStub   = "[Mitico] STUB — Dominio Assoluto: la zona di minaccia copre l'intero campo di battaglia";
                return w;
            }
            case "spear_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "spear_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                return w;
            }
            case "spear_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                return w;
            }
            case "spear_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                return w;
            }
            case "spear_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia da Cavaliere del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppia Punta: puoi cambiare tipo di danno a metà attacco, dopo aver visto la difesa";
                return w;
            }
            case "spear_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia da Cavaliere del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Getto Continuo: l'Abrasione si propaga ai nemici in linea con il bersaglio";
                return w;
            }
            case "spear_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia da Cavaliere del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Zona di Minaccia: ogni nemico che entra a portata subisce un attacco automatico";
                return w;
            }
            case "spear_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia dell'Alba Eterna";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni con il tipo scelto (taglio o perforazione)";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Doppia Punta: puoi cambiare tipo di danno a metà attacco, dopo aver visto la difesa";
                w.mythicUniqueStub   = "[Mitico] STUB — Punta Perfetta: scegli sempre il tipo di danno più efficace, automaticamente";
                return w;
            }
            case "spear_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia dell'Alba Eterna";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo di Abrasione a distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Getto Continuo: l'Abrasione si propaga ai nemici in linea con il bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Diluvio: l'Abrasione si propaga a tutti i nemici in campo, non solo in linea";
                return w;
            }
            case "spear_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia dell'Alba Eterna";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni di contrattacco; +% prob. di contrattaccare";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Zona di Minaccia: ogni nemico che entra a portata subisce un attacco automatico";
                w.mythicUniqueStub   = "[Mitico] STUB — Dominio Assoluto: la zona di minaccia copre l'intero campo di battaglia";
                return w;
            }

            // ══════ Pique ══════
            case "pique_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Asta Lunga da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Asta Lunga da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Asta Lunga da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Lancia Pesante di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                return w;
            }
            case "pique_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Lancia Pesante di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                return w;
            }
            case "pique_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Lancia Pesante di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                return w;
            }
            case "pique_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                return w;
            }
            case "pique_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                return w;
            }
            case "pique_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca Rinforzata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                return w;
            }
            case "pique_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da Fanteria del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Muro di Punte: chi ti attacca in mischia subisce danni prima di colpire";
                return w;
            }
            case "pique_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da Fanteria del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Punta di Luce: ignori completamente la resistenza elementale del bersaglio";
                return w;
            }
            case "pique_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da Fanteria del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Linea Tenuta: i nemici non possono avvicinarsi a te finché reggi la posizione";
                return w;
            }
            case "pique_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Asta Lunga da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Asta Lunga da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Asta Lunga da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Lancia Pesante di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                return w;
            }
            case "pique_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Lancia Pesante di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                return w;
            }
            case "pique_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Lancia Pesante di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                return w;
            }
            case "pique_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Reggimento del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Muro di Punte: chi ti attacca in mischia subisce danni prima di colpire";
                return w;
            }
            case "pique_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Reggimento del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Punta di Luce: ignori completamente la resistenza elementale del bersaglio";
                return w;
            }
            case "pique_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Reggimento del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Linea Tenuta: i nemici non possono avvicinarsi a te finché reggi la posizione";
                return w;
            }
            case "pique_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Guardiano Dimenticato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Muro di Punte: chi ti attacca in mischia subisce danni prima di colpire";
                w.mythicUniqueStub   = "[Mitico] STUB — Bastione Vivente: chi ti attacca in mischia subisce danni pari al doppio, prima di colpire";
                return w;
            }
            case "pique_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Guardiano Dimenticato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Punta di Luce: ignori completamente la resistenza elementale del bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Lancia del Sole: ignori anche l'immunità elementale, non solo la resistenza";
                return w;
            }
            case "pique_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Guardiano Dimenticato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Linea Tenuta: i nemici non possono avvicinarsi a te finché reggi la posizione";
                w.mythicUniqueStub   = "[Mitico] STUB — Fronte Impenetrabile: nessun nemico può mai avvicinarsi a te, in nessun modo";
                return w;
            }
            case "pique_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "pique_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                return w;
            }
            case "pique_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                return w;
            }
            case "pique_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca Rinforzata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                return w;
            }
            case "pique_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da Fanteria del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Muro di Punte: chi ti attacca in mischia subisce danni prima di colpire";
                return w;
            }
            case "pique_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da Fanteria del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Punta di Luce: ignori completamente la resistenza elementale del bersaglio";
                return w;
            }
            case "pique_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca da Fanteria del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Linea Tenuta: i nemici non possono avvicinarsi a te finché reggi la posizione";
                return w;
            }
            case "pique_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Guardiano dell'Abisso";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% lunghezza convertita in danno; +% danni contro chi carica";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Muro di Punte: chi ti attacca in mischia subisce danni prima di colpire";
                w.mythicUniqueStub   = "[Mitico] STUB — Bastione Vivente: chi ti attacca in mischia subisce danni pari al doppio, prima di colpire";
                return w;
            }
            case "pique_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Guardiano dell'Abisso";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Luce; +% resistenze elementali ignorate";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Punta di Luce: ignori completamente la resistenza elementale del bersaglio";
                w.mythicUniqueStub   = "[Mitico] STUB — Lancia del Sole: ignori anche l'immunità elementale, non solo la resistenza";
                return w;
            }
            case "pique_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca del Guardiano dell'Abisso";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% distanza di respinta; +% prob. di annullare un avvicinamento";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Linea Tenuta: i nemici non possono avvicinarsi a te finché reggi la posizione";
                w.mythicUniqueStub   = "[Mitico] STUB — Fronte Impenetrabile: nessun nemico può mai avvicinarsi a te, in nessun modo";
                return w;
            }

            // ══════ Scythe ══════
            case "scythe_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce semplice";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da recluta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da Guerra di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                return w;
            }
            case "scythe_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da Guerra di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                return w;
            }
            case "scythe_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da Guerra di buona fattura";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                return w;
            }
            case "scythe_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce da apprendista";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Affilata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                return w;
            }
            case "scythe_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Affilata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                return w;
            }
            case "scythe_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Affilata di manifattura pregiata";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                return w;
            }
            case "scythe_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Ricurva del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Uncino: il bersaglio tirato verso di te perde il turno";
                return w;
            }
            case "scythe_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Ricurva del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Marea Nera: ciò che drena si trasferisce anche agli alleati";
                return w;
            }
            case "scythe_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Ricurva del Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mietitura: uccidere un nemico ripristina completamente la tua risorsa";
                return w;
            }
            case "scythe_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da soldato";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da Guerra di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                return w;
            }
            case "scythe_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da Guerra di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                return w;
            }
            case "scythe_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falcetto da Guerra di fattura magistrale";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                return w;
            }
            case "scythe_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce del Mietitore del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Uncino: il bersaglio tirato verso di te perde il turno";
                return w;
            }
            case "scythe_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce del Mietitore del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Marea Nera: ciò che drena si trasferisce anche agli alleati";
                return w;
            }
            case "scythe_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce del Mietitore del Gran Maestro d'Armi";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mietitura: uccidere un nemico ripristina completamente la tua risorsa";
                return w;
            }
            case "scythe_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce del Mietitore Silente";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Uncino: il bersaglio tirato verso di te perde il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Richiamo: puoi tirare a te qualunque nemico in campo, non solo quello più vicino";
                return w;
            }
            case "scythe_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce del Mietitore Silente";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Marea Nera: ciò che drena si trasferisce anche agli alleati";
                w.mythicUniqueStub   = "[Mitico] STUB — Abisso: drena il doppio e lo trasferisce a tutti gli alleati contemporaneamente";
                return w;
            }
            case "scythe_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce del Mietitore Silente";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mietitura: uccidere un nemico ripristina completamente la tua risorsa";
                w.mythicUniqueStub   = "[Mitico] STUB — Falce del Destino: uccidere un nemico ripristina anche la tua vita, non solo la risorsa";
                return w;
            }
            case "scythe_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce da veterano";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "scythe_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Affilata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                return w;
            }
            case "scythe_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Affilata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                return w;
            }
            case "scythe_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Affilata di manifattura superiore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                return w;
            }
            case "scythe_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Ricurva del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Uncino: il bersaglio tirato verso di te perde il turno";
                return w;
            }
            case "scythe_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Ricurva del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Marea Nera: ciò che drena si trasferisce anche agli alleati";
                return w;
            }
            case "scythe_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce Ricurva del Fabbro Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mietitura: uccidere un nemico ripristina completamente la tua risorsa";
                return w;
            }
            case "scythe_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce dell'Ultimo Raccolto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di avvicinare il bersaglio; +% danni sul bersaglio tirato";
                w.fullSetOrStyleStub = "[Stile: Assalto] STUB — Uncino: il bersaglio tirato verso di te perde il turno";
                w.mythicUniqueStub   = "[Mitico] STUB — Richiamo: puoi tirare a te qualunque nemico in campo, non solo quello più vicino";
                return w;
            }
            case "scythe_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce dell'Ultimo Raccolto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Acqua; +% quantità drenata per colpo";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Marea Nera: ciò che drena si trasferisce anche agli alleati";
                w.mythicUniqueStub   = "[Mitico] STUB — Abisso: drena il doppio e lo trasferisce a tutti gli alleati contemporaneamente";
                return w;
            }
            case "scythe_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce dell'Ultimo Raccolto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 1;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% cura per danno inflitto; +% cura extra sui nemici sotto metà vita";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mietitura: uccidere un nemico ripristina completamente la tua risorsa";
                w.mythicUniqueStub   = "[Mitico] STUB — Falce del Destino: uccidere un nemico ripristina anche la tua vita, non solo la risorsa";
                return w;
            }

            // ══════ Short Bow ══════
            case "short_bow_precision_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da caccia";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da caccia";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da caccia";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_precision_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Battuta da tiratore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Battuta da tiratore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Battuta da tiratore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_precision_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Caccia di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                return w;
            }
            case "short_bow_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Caccia di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                return w;
            }
            case "short_bow_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Caccia di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                return w;
            }
            case "short_bow_precision_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da battuta";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da battuta";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da battuta";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_precision_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Snello di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                return w;
            }
            case "short_bow_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Snello di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                return w;
            }
            case "short_bow_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Snello di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                return w;
            }
            case "short_bow_precision_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Ricurvo del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Rapido: due frecce per turno contro bersagli senza armatura";
                return w;
            }
            case "short_bow_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Ricurvo del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Freccia di Vento: il colpo non può essere deviato né bloccato dalla copertura";
                return w;
            }
            case "short_bow_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Ricurvo del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Raffica: ogni colpo a segno nella raffica aggiunge un colpo extra";
                return w;
            }
            case "short_bow_precision_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Battuta da frontiera";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Battuta da frontiera";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Battuta da frontiera";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_precision_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Caccia di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                return w;
            }
            case "short_bow_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Caccia di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                return w;
            }
            case "short_bow_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco da Caccia di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                return w;
            }
            case "short_bow_precision_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Rapido: due frecce per turno contro bersagli senza armatura";
                return w;
            }
            case "short_bow_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Freccia di Vento: il colpo non può essere deviato né bloccato dalla copertura";
                return w;
            }
            case "short_bow_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Raffica: ogni colpo a segno nella raffica aggiunge un colpo extra";
                return w;
            }
            case "short_bow_precision_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore Fantasma";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Rapido: due frecce per turno contro bersagli senza armatura";
                w.mythicUniqueStub   = "[Mitico] STUB — Pioggia di Frecce: tre frecce per turno contro qualunque bersaglio";
                return w;
            }
            case "short_bow_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore Fantasma";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Freccia di Vento: il colpo non può essere deviato né bloccato dalla copertura";
                w.mythicUniqueStub   = "[Mitico] STUB — Vento Assoluto: il colpo attraversa qualunque ostacolo e non manca mai il bersaglio";
                return w;
            }
            case "short_bow_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore Fantasma";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Raffica: ogni colpo a segno nella raffica aggiunge un colpo extra";
                w.mythicUniqueStub   = "[Mitico] STUB — Raffica Infinita: la raffica continua finché ogni colpo va a segno";
                return w;
            }
            case "short_bow_precision_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da esploratore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da esploratore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto da esploratore";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "short_bow_precision_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Snello di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                return w;
            }
            case "short_bow_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Snello di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                return w;
            }
            case "short_bow_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Snello di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                return w;
            }
            case "short_bow_precision_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Ricurvo dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Rapido: due frecce per turno contro bersagli senza armatura";
                return w;
            }
            case "short_bow_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Ricurvo dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Freccia di Vento: il colpo non può essere deviato né bloccato dalla copertura";
                return w;
            }
            case "short_bow_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Ricurvo dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Raffica: ogni colpo a segno nella raffica aggiunge un colpo extra";
                return w;
            }
            case "short_bow_precision_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore delle Anime";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni contro nemici senza armatura; +% cadenza di tiro";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Rapido: due frecce per turno contro bersagli senza armatura";
                w.mythicUniqueStub   = "[Mitico] STUB — Pioggia di Frecce: tre frecce per turno contro qualunque bersaglio";
                return w;
            }
            case "short_bow_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore delle Anime";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Aria; -% dispersione a lunga distanza";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Freccia di Vento: il colpo non può essere deviato né bloccato dalla copertura";
                w.mythicUniqueStub   = "[Mitico] STUB — Vento Assoluto: il colpo attraversa qualunque ostacolo e non manca mai il bersaglio";
                return w;
            }
            case "short_bow_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco del Cacciatore delle Anime";
                w.affilatezza = 1; w.pomo[0] = 3; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% numero di colpi per turno; -% penalità di danno sui colpi multipli";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Raffica: ogni colpo a segno nella raffica aggiunge un colpo extra";
                w.mythicUniqueStub   = "[Mitico] STUB — Raffica Infinita: la raffica continua finché ogni colpo va a segno";
                return w;
            }

            // ══════ Longbow ══════
            case "longbow_precision_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da caccia";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da caccia";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da caccia";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_precision_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Alto da tiratore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Alto da tiratore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Alto da tiratore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_precision_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco da Guerra di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                return w;
            }
            case "longbow_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco da Guerra di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                return w;
            }
            case "longbow_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco da Guerra di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                return w;
            }
            case "longbow_precision_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da battuta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da battuta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da battuta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_precision_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Teso di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                return w;
            }
            case "longbow_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Teso di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                return w;
            }
            case "longbow_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Teso di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                return w;
            }
            case "longbow_precision_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Composito del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Perforante: il colpo attraversa il bersaglio e colpisce chi sta dietro";
                return w;
            }
            case "longbow_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Composito del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Saetta: Folgore incatena fino a tre bersagli";
                return w;
            }
            case "longbow_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Composito del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Distanza di Sicurezza: alla massima gittata ogni colpo è critico";
                return w;
            }
            case "longbow_precision_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Alto da frontiera";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Alto da frontiera";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Alto da frontiera";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_precision_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco da Guerra di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                return w;
            }
            case "longbow_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco da Guerra di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                return w;
            }
            case "longbow_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco da Guerra di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                return w;
            }
            case "longbow_precision_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco del Guerriero del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Perforante: il colpo attraversa il bersaglio e colpisce chi sta dietro";
                return w;
            }
            case "longbow_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco del Guerriero del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Saetta: Folgore incatena fino a tre bersagli";
                return w;
            }
            case "longbow_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco del Guerriero del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Distanza di Sicurezza: alla massima gittata ogni colpo è critico";
                return w;
            }
            case "longbow_precision_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco della Freccia Perduta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Perforante: il colpo attraversa il bersaglio e colpisce chi sta dietro";
                w.mythicUniqueStub   = "[Mitico] STUB — Trapasso: il colpo attraversa tutti i nemici in linea, non solo il primo dietro";
                return w;
            }
            case "longbow_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco della Freccia Perduta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Saetta: Folgore incatena fino a tre bersagli";
                w.mythicUniqueStub   = "[Mitico] STUB — Tempesta: Folgore incatena tutti i nemici in campo";
                return w;
            }
            case "longbow_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco della Freccia Perduta";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Distanza di Sicurezza: alla massima gittata ogni colpo è critico";
                w.mythicUniqueStub   = "[Mitico] STUB — Occhio di Falco: ogni colpo a lunga distanza è automaticamente critico, senza soglia minima";
                return w;
            }
            case "longbow_precision_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da esploratore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da esploratore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo da esploratore";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "longbow_precision_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Teso di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                return w;
            }
            case "longbow_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Teso di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                return w;
            }
            case "longbow_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Teso di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                return w;
            }
            case "longbow_precision_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Composito dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Perforante: il colpo attraversa il bersaglio e colpisce chi sta dietro";
                return w;
            }
            case "longbow_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Composito dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Saetta: Folgore incatena fino a tre bersagli";
                return w;
            }
            case "longbow_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Composito dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Distanza di Sicurezza: alla massima gittata ogni colpo è critico";
                return w;
            }
            case "longbow_precision_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco della Stella Cadente";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% perforazione contro nemici corazzati; +% danni a lunga gittata";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Tiro Perforante: il colpo attraversa il bersaglio e colpisce chi sta dietro";
                w.mythicUniqueStub   = "[Mitico] STUB — Trapasso: il colpo attraversa tutti i nemici in linea, non solo il primo dietro";
                return w;
            }
            case "longbow_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco della Stella Cadente";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fulmine; +% prob. di innescare Folgore";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Saetta: Folgore incatena fino a tre bersagli";
                w.mythicUniqueStub   = "[Mitico] STUB — Tempesta: Folgore incatena tutti i nemici in campo";
                return w;
            }
            case "longbow_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco della Stella Cadente";
                w.affilatezza = 1; w.pomo[0] = 1; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni per unità di distanza; +% distanza massima utile";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Distanza di Sicurezza: alla massima gittata ogni colpo è critico";
                w.mythicUniqueStub   = "[Mitico] STUB — Occhio di Falco: ogni colpo a lunga distanza è automaticamente critico, senza soglia minima";
                return w;
            }

            // ══════ Crossbow ══════
            case "crossbow_precision_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da caccia";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da caccia";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da caccia";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 1;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_precision_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestrino da tiratore";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestrino da tiratore";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestrino da tiratore";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 2;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_precision_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da Assedio di ottima mira";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                return w;
            }
            case "crossbow_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da Assedio di ottima mira";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                return w;
            }
            case "crossbow_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da Assedio di ottima mira";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 3;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                return w;
            }
            case "crossbow_precision_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da battuta";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da battuta";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da battuta";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 4;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_precision_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra Rinforzata di precisione rara";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                return w;
            }
            case "crossbow_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra Rinforzata di precisione rara";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                return w;
            }
            case "crossbow_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra Rinforzata di precisione rara";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 5;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                return w;
            }
            case "crossbow_precision_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra a Ripetizione del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Colpo Pronto: il primo colpo di ogni combattimento è già carico";
                return w;
            }
            case "crossbow_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra a Ripetizione del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Verretta Incendiaria: il punto colpito resta in fiamme per un round";
                return w;
            }
            case "crossbow_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra a Ripetizione del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 6;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mira Assoluta: nessun bersaglio può essere coperto o nascosto per te";
                return w;
            }
            case "crossbow_precision_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestrino da frontiera";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestrino da frontiera";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestrino da frontiera";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 7;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_precision_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da Assedio di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                return w;
            }
            case "crossbow_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da Assedio di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                return w;
            }
            case "crossbow_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da Assedio di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 8;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                return w;
            }
            case "crossbow_precision_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Cecchino del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Colpo Pronto: il primo colpo di ogni combattimento è già carico";
                return w;
            }
            case "crossbow_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Cecchino del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Verretta Incendiaria: il punto colpito resta in fiamme per un round";
                return w;
            }
            case "crossbow_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Cecchino del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 9;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mira Assoluta: nessun bersaglio può essere coperto o nascosto per te";
                return w;
            }
            case "crossbow_precision_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Cecchino Sepolto";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Colpo Pronto: il primo colpo di ogni combattimento è già carico";
                w.mythicUniqueStub   = "[Mitico] STUB — Sempre Carica: ogni colpo è considerato precaricato, senza tempo di ricarica";
                return w;
            }
            case "crossbow_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Cecchino Sepolto";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Verretta Incendiaria: il punto colpito resta in fiamme per un round";
                w.mythicUniqueStub   = "[Mitico] STUB — Pioggia di Fuoco: il punto colpito resta in fiamme per l'intero combattimento";
                return w;
            }
            case "crossbow_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Cecchino Sepolto";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 10;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mira Assoluta: nessun bersaglio può essere coperto o nascosto per te";
                w.mythicUniqueStub   = "[Mitico] STUB — Occhio che Tutto Vede: nessun bersaglio può mai sottrarsi alla tua mira, nemmeno con effetti di invisibilità";
                return w;
            }
            case "crossbow_precision_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da esploratore";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da esploratore";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra da esploratore";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 11;
                w.struttura[0] = 100;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "crossbow_precision_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra Rinforzata di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                return w;
            }
            case "crossbow_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra Rinforzata di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                return w;
            }
            case "crossbow_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra Rinforzata di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 12;
                w.struttura[0] = 100;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                return w;
            }
            case "crossbow_precision_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra a Ripetizione dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Colpo Pronto: il primo colpo di ogni combattimento è già carico";
                return w;
            }
            case "crossbow_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra a Ripetizione dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Verretta Incendiaria: il punto colpito resta in fiamme per un round";
                return w;
            }
            case "crossbow_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra a Ripetizione dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 13;
                w.struttura[0] = 100;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mira Assoluta: nessun bersaglio può essere coperto o nascosto per te";
                return w;
            }
            case "crossbow_precision_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Giudice Silenzioso";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni del colpo precaricato; -% tempo di ricarica";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Colpo Pronto: il primo colpo di ogni combattimento è già carico";
                w.mythicUniqueStub   = "[Mitico] STUB — Sempre Carica: ogni colpo è considerato precaricato, senza tempo di ricarica";
                return w;
            }
            case "crossbow_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Giudice Silenzioso";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Fuoco; +% area dell'incendio generato";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Verretta Incendiaria: il punto colpito resta in fiamme per un round";
                w.mythicUniqueStub   = "[Mitico] STUB — Pioggia di Fuoco: il punto colpito resta in fiamme per l'intero combattimento";
                return w;
            }
            case "crossbow_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra del Giudice Silenzioso";
                w.affilatezza = 1; w.pomo[0] = -3; w.guardia[0] = 0;
                w.legamenti[0] = 14;
                w.struttura[0] = 100;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% copertura ignorata; +% danni contro bersagli in copertura";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Mira Assoluta: nessun bersaglio può essere coperto o nascosto per te";
                w.mythicUniqueStub   = "[Mitico] STUB — Occhio che Tutto Vede: nessun bersaglio può mai sottrarsi alla tua mira, nemmeno con effetti di invisibilità";
                return w;
            }

            // ══════ Throwing Knife ══════
            case "throwing_knife_precision_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da caccia";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_elemental_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da caccia";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_special_atk_0_10_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da caccia";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 1;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_precision_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnaletto da Lancio da tiratore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_elemental_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnaletto da Lancio da tiratore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_special_atk_10_20_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnaletto da Lancio da tiratore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 2;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_precision_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale da Lancio di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                return w;
            }
            case "throwing_knife_elemental_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale da Lancio di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                return w;
            }
            case "throwing_knife_special_atk_10_20_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale da Lancio di ottima mira";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 3;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                return w;
            }
            case "throwing_knife_precision_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da battuta";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_elemental_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da battuta";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_special_atk_20_30_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da battuta";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 4;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_precision_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello Bilanciato di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                return w;
            }
            case "throwing_knife_elemental_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello Bilanciato di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                return w;
            }
            case "throwing_knife_special_atk_20_30_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello Bilanciato di precisione rara";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 5;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                return w;
            }
            case "throwing_knife_precision_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Lama da Lancio del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Rimbalzo: il coltello lanciato torna in mano e può essere rilanciato";
                return w;
            }
            case "throwing_knife_elemental_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Lama da Lancio del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Goccia Costante: al terzo lancio l'effetto elementale si innesca automaticamente";
                return w;
            }
            case "throwing_knife_special_atk_20_30_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Lama da Lancio del Maestro Arciere";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 6;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Detonazione: puoi far esplodere tutte le lame piantate in un colpo solo";
                return w;
            }
            case "throwing_knife_precision_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnaletto da Lancio da frontiera";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_elemental_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnaletto da Lancio da frontiera";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_special_atk_30_40_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnaletto da Lancio da frontiera";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 7;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_precision_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale da Lancio di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                return w;
            }
            case "throwing_knife_elemental_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale da Lancio di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                return w;
            }
            case "throwing_knife_special_atk_30_40_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale da Lancio di manifattura d'arciere";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 8;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                return w;
            }
            case "throwing_knife_precision_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale dell'Ombra del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Rimbalzo: il coltello lanciato torna in mano e può essere rilanciato";
                return w;
            }
            case "throwing_knife_elemental_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale dell'Ombra del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Goccia Costante: al terzo lancio l'effetto elementale si innesca automaticamente";
                return w;
            }
            case "throwing_knife_special_atk_30_40_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale dell'Ombra del Gran Cecchino";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 9;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Detonazione: puoi far esplodere tutte le lame piantate in un colpo solo";
                return w;
            }
            case "throwing_knife_precision_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello dell'Ombra Fugace";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Rimbalzo: il coltello lanciato torna in mano e può essere rilanciato";
                w.mythicUniqueStub   = "[Mitico] STUB — Lame Infinite: il coltello torna in mano istantaneamente, senza consumare il turno";
                return w;
            }
            case "throwing_knife_elemental_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello dell'Ombra Fugace";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Goccia Costante: al terzo lancio l'effetto elementale si innesca automaticamente";
                w.mythicUniqueStub   = "[Mitico] STUB — Diluvio Costante: l'effetto elementale si innesca a ogni lancio, non solo al terzo";
                return w;
            }
            case "throwing_knife_special_atk_30_40_mythic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello dell'Ombra Fugace";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 10;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Detonazione: puoi far esplodere tutte le lame piantate in un colpo solo";
                w.mythicUniqueStub   = "[Mitico] STUB — Reazione a Catena: la detonazione di un bersaglio innesca anche quella dei bersagli vicini";
                return w;
            }
            case "throwing_knife_precision_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da esploratore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_elemental_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da esploratore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_special_atk_40_50_common": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio da esploratore";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 11;
                w.rarity = Rarity.COMMON;
                return w;
            }
            case "throwing_knife_precision_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello Bilanciato di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                return w;
            }
            case "throwing_knife_elemental_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello Bilanciato di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                return w;
            }
            case "throwing_knife_special_atk_40_50_high_quality": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello Bilanciato di tiro perfetto";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 12;
                w.rarity = Rarity.HIGH_QUALITY;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                return w;
            }
            case "throwing_knife_precision_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Lama da Lancio dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Rimbalzo: il coltello lanciato torna in mano e può essere rilanciato";
                return w;
            }
            case "throwing_knife_elemental_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Lama da Lancio dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Goccia Costante: al terzo lancio l'effetto elementale si innesca automaticamente";
                return w;
            }
            case "throwing_knife_special_atk_40_50_masterwork": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Lama da Lancio dell'Arciere Supremo";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 13;
                w.rarity = Rarity.MASTERWORK;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Detonazione: puoi far esplodere tutte le lame piantate in un colpo solo";
                return w;
            }
            case "throwing_knife_precision_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale dell'Ombra Eterna";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% prob. di contrattacco; -% penalità di danno sui lanci";
                w.fullSetOrStyleStub = "[Stile: Tiro Calibrato] STUB — Rimbalzo: il coltello lanciato torna in mano e può essere rilanciato";
                w.mythicUniqueStub   = "[Mitico] STUB — Lame Infinite: il coltello torna in mano istantaneamente, senza consumare il turno";
                return w;
            }
            case "throwing_knife_elemental_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale dell'Ombra Eterna";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni da Acqua; +% accumulo elementale per lancio";
                w.fullSetOrStyleStub = "[Stile: Canalizzazione] STUB — Goccia Costante: al terzo lancio l'effetto elementale si innesca automaticamente";
                w.mythicUniqueStub   = "[Mitico] STUB — Diluvio Costante: l'effetto elementale si innesca a ogni lancio, non solo al terzo";
                return w;
            }
            case "throwing_knife_special_atk_40_50_mythic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Pugnale dell'Ombra Eterna";
                w.affilatezza = 1; w.pomo[0] = 4; w.guardia[0] = 0;
                w.punta[0] = 14;
                w.rarity = Rarity.MYTHIC;
                w.synergyBonusStub   = "+% danni per ogni lama già piantata nel bersaglio";
                w.fullSetOrStyleStub = "[Stile: Astuzia] STUB — Detonazione: puoi far esplodere tutte le lame piantate in un colpo solo";
                w.mythicUniqueStub   = "[Mitico] STUB — Reazione a Catena: la detonazione di un bersaglio innesca anche quella dei bersagli vicini";
                return w;
            }

            default:
                System.err.println("Weapon non trovata: " + id);
                return null;
        }
    }
}
