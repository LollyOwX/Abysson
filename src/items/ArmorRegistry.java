package items;

import items.Armor.ArmorType;
import items.Armor.WeightClass;

/**
 * Catalogo COMPLETO delle armature, generato dalla scheda Equip — 448 case. Ogni chiamata a
 * get() crea un'istanza NUOVA.
 *
 * Upper-body: metalli[0] (-> DIFESA, fino a 3 sommati) è il campo tiered, legamenti fisso.
 * Gambe: l'opposto — legamenti (-> VELOCITA) scala, metalli[0] fisso. armorSetID: 1=Guardia/
 * 2=Veggente/3=Voto (upper, per Build), 4=Passo/5=Ombra (gambe, per Build) — vedi
 * Player.activeArmorSetID(). Generato da script a partire dalla scheda Equip.
 */
public class ArmorRegistry {
    public static Armor get(String id) {
        switch (id) {
            // ══════ Helmet ══════
            case "helmet_defense_0_10_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio di cuoio";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio a punta del mago";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio del prete";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_defense_10_20_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio dell'apprendista";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio del chierico";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                return a;
            }
            case "helmet_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                return a;
            }
            case "helmet_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia del chierico devoto";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                return a;
            }
            case "helmet_defense_20_30_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio di pelle indurita";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio dello stregone";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio del monaco";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                return a;
            }
            case "helmet_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia dello stregone runico";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                return a;
            }
            case "helmet_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                return a;
            }
            case "helmet_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Conciapelli";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Elmo Saldo: il primo stordimento di ogni combattimento viene annullato (livello Iniziato)";
                return a;
            }
            case "helmet_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Occhio Limpido: immune all'accecamento, vedi le debolezze elementali del nemico (livello Iniziato)";
                return a;
            }
            case "helmet_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mente Intatta: gli effetti che ti fanno perdere il turno vengono riflessi sul lanciatore (livello Iniziato)";
                return a;
            }
            case "helmet_defense_30_40_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio del veggente";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio dell'eremita";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                return a;
            }
            case "helmet_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                return a;
            }
            case "helmet_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                return a;
            }
            case "helmet_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Elmo Saldo: il primo stordimento di ogni combattimento viene annullato (livello Adepto)";
                return a;
            }
            case "helmet_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Occhio Limpido: immune all'accecamento, vedi le debolezze elementali del nemico (livello Adepto)";
                return a;
            }
            case "helmet_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Patriarca";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mente Intatta: gli effetti che ti fanno perdere il turno vengono riflessi sul lanciatore (livello Adepto)";
                return a;
            }
            case "helmet_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Elmo Saldo: il primo stordimento di ogni combattimento viene annullato (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Vista del Custode: vedi sempre la barra vita esatta e le resistenze del nemico (livello Ascendente)";
                return a;
            }
            case "helmet_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Occhio Limpido: immune all'accecamento, vedi le debolezze elementali del nemico (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Terzo Occhio: prevedi la prossima mossa del nemico una volta per combattimento (livello Ascendente)";
                return a;
            }
            case "helmet_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mente Intatta: gli effetti che ti fanno perdere il turno vengono riflessi sul lanciatore (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Calma Assoluta: immune a paura e disperazione, qualunque sia la fonte (livello Ascendente)";
                return a;
            }
            case "helmet_defense_40_50_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio dell'oracolo";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cappuccio del pellegrino";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "helmet_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                return a;
            }
            case "helmet_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                return a;
            }
            case "helmet_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Cuffia del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                return a;
            }
            case "helmet_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Elmo Saldo: il primo stordimento di ogni combattimento viene annullato (livello Maestro)";
                return a;
            }
            case "helmet_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Occhio Limpido: immune all'accecamento, vedi le debolezze elementali del nemico (livello Maestro)";
                return a;
            }
            case "helmet_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmetto del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mente Intatta: gli effetti che ti fanno perdere il turno vengono riflessi sul lanciatore (livello Maestro)";
                return a;
            }
            case "helmet_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa da impatto; -% durata dello stordimento subito";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Elmo Saldo: il primo stordimento di ogni combattimento viene annullato (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Vista del Custode: vedi sempre la barra vita esatta e le resistenze del nemico (livello Trascendente)";
                return a;
            }
            case "helmet_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Luce; -% durata di Raggio";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Occhio Limpido: immune all'accecamento, vedi le debolezze elementali del nemico (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Terzo Occhio: prevedi la prossima mossa del nemico una volta per combattimento (livello Trascendente)";
                return a;
            }
            case "helmet_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.HELMET, WeightClass.LEGGERA);
                a.name = "Elmo della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al controllo; +% prob. di agire comunque se confuso";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mente Intatta: gli effetti che ti fanno perdere il turno vengono riflessi sul lanciatore (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Calma Assoluta: immune a paura e disperazione, qualunque sia la fonte (livello Trascendente)";
                return a;
            }

            // ══════ Gorget ══════
            case "gorget_defense_0_10_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare di cuoio";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare a punta del mago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare del prete";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_defense_10_20_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare dell'apprendista";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare del chierico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                return a;
            }
            case "gorget_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                return a;
            }
            case "gorget_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera del chierico devoto";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                return a;
            }
            case "gorget_defense_20_30_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare di pelle indurita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare dello stregone";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare del monaco";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                return a;
            }
            case "gorget_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera dello stregone runico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                return a;
            }
            case "gorget_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                return a;
            }
            case "gorget_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gola Serrata: il sanguinamento non può essere riapplicato finché è attivo (livello Iniziato)";
                return a;
            }
            case "gorget_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Respiro di Brace: curi invece di subire danni la prima volta che ti Infiammi (livello Iniziato)";
                return a;
            }
            case "gorget_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Voce Inflessibile: puoi usare una abilità anche durante il silenzio, una volta per combattimento (livello Iniziato)";
                return a;
            }
            case "gorget_defense_30_40_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare del veggente";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare dell'eremita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                return a;
            }
            case "gorget_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                return a;
            }
            case "gorget_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                return a;
            }
            case "gorget_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gola Serrata: il sanguinamento non può essere riapplicato finché è attivo (livello Adepto)";
                return a;
            }
            case "gorget_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Respiro di Brace: curi invece di subire danni la prima volta che ti Infiammi (livello Adepto)";
                return a;
            }
            case "gorget_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Patriarca";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Voce Inflessibile: puoi usare una abilità anche durante il silenzio, una volta per combattimento (livello Adepto)";
                return a;
            }
            case "gorget_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gola d'Acciaio del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gola Serrata: il sanguinamento non può essere riapplicato finché è attivo (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Respiro Eterno: immune al soffocamento e alle abilità che ignorano la difesa al collo (livello Ascendente)";
                return a;
            }
            case "gorget_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gola d'Acciaio dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Respiro di Brace: curi invece di subire danni la prima volta che ti Infiammi (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Cenere Viva: ogni volta che ti curi, infliggi danno da Fuoco a chi ti è vicino (livello Ascendente)";
                return a;
            }
            case "gorget_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gola d'Acciaio della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Voce Inflessibile: puoi usare una abilità anche durante il silenzio, una volta per combattimento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Giuramento Muto: le tue abilità non possono mai essere bloccate o interrotte (livello Ascendente)";
                return a;
            }
            case "gorget_defense_40_50_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare dell'oracolo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Collare del pellegrino";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gorget_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                return a;
            }
            case "gorget_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                return a;
            }
            case "gorget_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gorgiera del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                return a;
            }
            case "gorget_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gola Serrata: il sanguinamento non può essere riapplicato finché è attivo (livello Maestro)";
                return a;
            }
            case "gorget_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Respiro di Brace: curi invece di subire danni la prima volta che ti Infiammi (livello Maestro)";
                return a;
            }
            case "gorget_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Barbozza del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Voce Inflessibile: puoi usare una abilità anche durante il silenzio, una volta per combattimento (livello Maestro)";
                return a;
            }
            case "gorget_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gola d'Acciaio dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa al collo; -% danni da sanguinamento subiti";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gola Serrata: il sanguinamento non può essere riapplicato finché è attivo (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Respiro Eterno: immune al soffocamento e alle abilità che ignorano la difesa al collo (livello Trascendente)";
                return a;
            }
            case "gorget_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gola d'Acciaio del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Fuoco; -% durata di Infiammazione";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Respiro di Brace: curi invece di subire danni la prima volta che ti Infiammi (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Cenere Viva: ogni volta che ti curi, infliggi danno da Fuoco a chi ti è vicino (livello Trascendente)";
                return a;
            }
            case "gorget_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.GORGET, WeightClass.LEGGERA);
                a.name = "Gola d'Acciaio della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al silenzio; -% costo abilità mentre sei silenziato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Voce Inflessibile: puoi usare una abilità anche durante il silenzio, una volta per combattimento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Giuramento Muto: le tue abilità non possono mai essere bloccate o interrotte (livello Trascendente)";
                return a;
            }

            // ══════ Pauldron ══════
            case "pauldron_defense_0_10_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina di cuoio";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina a punta del mago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina del prete";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_defense_10_20_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina dell'apprendista";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina del chierico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                return a;
            }
            case "pauldron_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                return a;
            }
            case "pauldron_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera del chierico devoto";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                return a;
            }
            case "pauldron_defense_20_30_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina di pelle indurita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina dello stregone";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina del monaco";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                return a;
            }
            case "pauldron_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera dello stregone runico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                return a;
            }
            case "pauldron_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                return a;
            }
            case "pauldron_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Spalla d'Acciaio: chi tenta di disarmarti subisce contraccolpo (livello Iniziato)";
                return a;
            }
            case "pauldron_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Parafulmine: la Scossa subita si scarica sul nemico più vicino (livello Iniziato)";
                return a;
            }
            case "pauldron_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Spalla Consacrata: una maledizione a tua scelta viene dissolta a fine round (livello Iniziato)";
                return a;
            }
            case "pauldron_defense_30_40_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina del veggente";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina dell'eremita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                return a;
            }
            case "pauldron_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                return a;
            }
            case "pauldron_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                return a;
            }
            case "pauldron_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Spalla d'Acciaio: chi tenta di disarmarti subisce contraccolpo (livello Adepto)";
                return a;
            }
            case "pauldron_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Parafulmine: la Scossa subita si scarica sul nemico più vicino (livello Adepto)";
                return a;
            }
            case "pauldron_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Patriarca";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Spalla Consacrata: una maledizione a tua scelta viene dissolta a fine round (livello Adepto)";
                return a;
            }
            case "pauldron_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallare d'Arme del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Spalla d'Acciaio: chi tenta di disarmarti subisce contraccolpo (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Spalla del Titano: non puoi essere disarmato in nessuna circostanza (livello Ascendente)";
                return a;
            }
            case "pauldron_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallare d'Arme dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Parafulmine: la Scossa subita si scarica sul nemico più vicino (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Tempesta Latente: accumuli carica passivamente ogni round, da scaricare a piacere (livello Ascendente)";
                return a;
            }
            case "pauldron_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallare d'Arme della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Spalla Consacrata: una maledizione a tua scelta viene dissolta a fine round (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Manto Sacro: sei immune a maledizioni e marchi per l'intero combattimento (livello Ascendente)";
                return a;
            }
            case "pauldron_defense_40_50_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina dell'oracolo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallina del pellegrino";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "pauldron_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                return a;
            }
            case "pauldron_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                return a;
            }
            case "pauldron_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spalliera del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                return a;
            }
            case "pauldron_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Spalla d'Acciaio: chi tenta di disarmarti subisce contraccolpo (livello Maestro)";
                return a;
            }
            case "pauldron_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Parafulmine: la Scossa subita si scarica sul nemico più vicino (livello Maestro)";
                return a;
            }
            case "pauldron_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallaccio del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Spalla Consacrata: una maledizione a tua scelta viene dissolta a fine round (livello Maestro)";
                return a;
            }
            case "pauldron_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallare d'Arme dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa dalle prese; -% prob. di essere disarmato";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Spalla d'Acciaio: chi tenta di disarmarti subisce contraccolpo (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Spalla del Titano: non puoi essere disarmato in nessuna circostanza (livello Trascendente)";
                return a;
            }
            case "pauldron_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallare d'Arme del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Fulmine; -% durata di Scossa";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Parafulmine: la Scossa subita si scarica sul nemico più vicino (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Tempesta Latente: accumuli carica passivamente ogni round, da scaricare a piacere (livello Trascendente)";
                return a;
            }
            case "pauldron_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.PAULDRON, WeightClass.MEDIA);
                a.name = "Spallare d'Arme della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza alle maledizioni; -% durata dei marchi subiti";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Spalla Consacrata: una maledizione a tua scelta viene dissolta a fine round (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Manto Sacro: sei immune a maledizioni e marchi per l'intero combattimento (livello Trascendente)";
                return a;
            }

            // ══════ Rerebrace ══════
            case "rerebrace_defense_0_10_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale di cuoio";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale a punta del mago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale del prete";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_defense_10_20_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale dell'apprendista";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale del chierico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                return a;
            }
            case "rerebrace_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                return a;
            }
            case "rerebrace_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore del chierico devoto";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                return a;
            }
            case "rerebrace_defense_20_30_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale di pelle indurita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale dello stregone";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale del monaco";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                return a;
            }
            case "rerebrace_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore dello stregone runico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                return a;
            }
            case "rerebrace_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                return a;
            }
            case "rerebrace_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Braccio Instancabile: il peso dell'arma non riduce più il numero di attacchi per turno (livello Iniziato)";
                return a;
            }
            case "rerebrace_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ancora di Vento: non puoi essere spostato dalla tua posizione in combattimento (livello Iniziato)";
                return a;
            }
            case "rerebrace_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                a.fullSetOrStyleStub = "[Full Set: Voto] Purga Lenta: a fine round rimuovi automaticamente il debuff più vecchio (livello Iniziato)";
                return a;
            }
            case "rerebrace_defense_30_40_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale del veggente";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale dell'eremita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                return a;
            }
            case "rerebrace_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                return a;
            }
            case "rerebrace_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                return a;
            }
            case "rerebrace_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Braccio Instancabile: il peso dell'arma non riduce più il numero di attacchi per turno (livello Adepto)";
                return a;
            }
            case "rerebrace_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ancora di Vento: non puoi essere spostato dalla tua posizione in combattimento (livello Adepto)";
                return a;
            }
            case "rerebrace_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Patriarca";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                a.fullSetOrStyleStub = "[Full Set: Voto] Purga Lenta: a fine round rimuovi automaticamente il debuff più vecchio (livello Adepto)";
                return a;
            }
            case "rerebrace_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Braccio d'Arme del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Braccio Instancabile: il peso dell'arma non riduce più il numero di attacchi per turno (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Braccio Infaticabile: nessun equipaggiamento applica più penalità di velocità (livello Ascendente)";
                return a;
            }
            case "rerebrace_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Braccio d'Arme dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ancora di Vento: non puoi essere spostato dalla tua posizione in combattimento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Radice d'Aria: immune a ogni forma di spostamento forzato (livello Ascendente)";
                return a;
            }
            case "rerebrace_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Braccio d'Arme della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                a.fullSetOrStyleStub = "[Full Set: Voto] Purga Lenta: a fine round rimuovi automaticamente il debuff più vecchio (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] Sangue Puro: i debuff durano al massimo un round, qualunque sia la fonte (livello Ascendente)";
                return a;
            }
            case "rerebrace_defense_40_50_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale dell'oracolo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale del pellegrino";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "rerebrace_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                return a;
            }
            case "rerebrace_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                return a;
            }
            case "rerebrace_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Bracciale Superiore del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                return a;
            }
            case "rerebrace_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Braccio Instancabile: il peso dell'arma non riduce più il numero di attacchi per turno (livello Maestro)";
                return a;
            }
            case "rerebrace_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ancora di Vento: non puoi essere spostato dalla tua posizione in combattimento (livello Maestro)";
                return a;
            }
            case "rerebrace_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Manica di Piastra del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                a.fullSetOrStyleStub = "[Full Set: Voto] Purga Lenta: a fine round rimuovi automaticamente il debuff più vecchio (livello Maestro)";
                return a;
            }
            case "rerebrace_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Braccio d'Arme dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% controllo del peso; -% penalità di velocità da equipaggiamento pesante";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Braccio Instancabile: il peso dell'arma non riduce più il numero di attacchi per turno (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Braccio Infaticabile: nessun equipaggiamento applica più penalità di velocità (livello Trascendente)";
                return a;
            }
            case "rerebrace_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Braccio d'Arme del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza ad Aria; -% distanza di spinta subita";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Ancora di Vento: non puoi essere spostato dalla tua posizione in combattimento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Radice d'Aria: immune a ogni forma di spostamento forzato (livello Trascendente)";
                return a;
            }
            case "rerebrace_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.REREBRACE, WeightClass.MEDIA);
                a.name = "Braccio d'Arme della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% durata di TUTTI i debuff; +% prob. di scrollarne uno a inizio turno";
                a.fullSetOrStyleStub = "[Full Set: Voto] Purga Lenta: a fine round rimuovi automaticamente il debuff più vecchio (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] Sangue Puro: i debuff durano al massimo un round, qualunque sia la fonte (livello Trascendente)";
                return a;
            }

            // ══════ Couter ══════
            case "couter_defense_0_10_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera di cuoio";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera a punta del mago";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera del prete";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_defense_10_20_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera dell'apprendista";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera del chierico";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                return a;
            }
            case "couter_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                return a;
            }
            case "couter_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera del chierico devoto";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                return a;
            }
            case "couter_defense_20_30_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera di pelle indurita";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera dello stregone";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera del monaco";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                return a;
            }
            case "couter_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera dello stregone runico";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                return a;
            }
            case "couter_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                return a;
            }
            case "couter_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Conciapelli";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gomito Fermo: i colpi a distanza non possono essere critici contro di te (livello Iniziato)";
                return a;
            }
            case "couter_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Corrente Contraria: ogni rallentamento subito ti dà invece un turno di velocità (livello Iniziato)";
                return a;
            }
            case "couter_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Vena Chiusa: ciò che il nemico ti drena viene restituito a te a fine combattimento (livello Iniziato)";
                return a;
            }
            case "couter_defense_30_40_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera del veggente";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera dell'eremita";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                return a;
            }
            case "couter_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                return a;
            }
            case "couter_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                return a;
            }
            case "couter_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gomito Fermo: i colpi a distanza non possono essere critici contro di te (livello Adepto)";
                return a;
            }
            case "couter_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Corrente Contraria: ogni rallentamento subito ti dà invece un turno di velocità (livello Adepto)";
                return a;
            }
            case "couter_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Patriarca";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Vena Chiusa: ciò che il nemico ti drena viene restituito a te a fine combattimento (livello Adepto)";
                return a;
            }
            case "couter_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitale del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gomito Fermo: i colpi a distanza non possono essere critici contro di te (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Deviazione: puoi deviare un proiettile contro il suo stesso lanciatore, una volta per combattimento (livello Ascendente)";
                return a;
            }
            case "couter_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitale dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Corrente Contraria: ogni rallentamento subito ti dà invece un turno di velocità (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Marea Interiore: immune a ogni effetto di rallentamento (livello Ascendente)";
                return a;
            }
            case "couter_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitale della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Vena Chiusa: ciò che il nemico ti drena viene restituito a te a fine combattimento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Fonte Sigillata: non puoi mai essere drenato, in nessuna forma (livello Ascendente)";
                return a;
            }
            case "couter_defense_40_50_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera dell'oracolo";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Gomitiera del pellegrino";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "couter_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                return a;
            }
            case "couter_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                return a;
            }
            case "couter_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitiera del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                return a;
            }
            case "couter_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gomito Fermo: i colpi a distanza non possono essere critici contro di te (livello Maestro)";
                return a;
            }
            case "couter_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Corrente Contraria: ogni rallentamento subito ti dà invece un turno di velocità (livello Maestro)";
                return a;
            }
            case "couter_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubito d'Acciaio del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Vena Chiusa: ciò che il nemico ti drena viene restituito a te a fine combattimento (livello Maestro)";
                return a;
            }
            case "couter_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitale dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% controllo sulle armi a distanza; +% difesa dai proiettili";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Gomito Fermo: i colpi a distanza non possono essere critici contro di te (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Deviazione: puoi deviare un proiettile contro il suo stesso lanciatore, una volta per combattimento (livello Trascendente)";
                return a;
            }
            case "couter_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitale del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza ad Acqua; -% durata dei rallentamenti";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Corrente Contraria: ogni rallentamento subito ti dà invece un turno di velocità (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Marea Interiore: immune a ogni effetto di rallentamento (livello Trascendente)";
                return a;
            }
            case "couter_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.COUTER, WeightClass.LEGGERA);
                a.name = "Cubitale della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al drenaggio; +% recupero di ciò che ti viene drenato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Vena Chiusa: ciò che il nemico ti drena viene restituito a te a fine combattimento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Fonte Sigillata: non puoi mai essere drenato, in nessuna forma (livello Trascendente)";
                return a;
            }

            // ══════ Vanbrace ══════
            case "vanbrace_defense_0_10_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto di cuoio";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto a punta del mago";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto del prete";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_defense_10_20_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto dell'apprendista";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto del chierico";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                return a;
            }
            case "vanbrace_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                return a;
            }
            case "vanbrace_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio del chierico devoto";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                return a;
            }
            case "vanbrace_defense_20_30_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto di pelle indurita";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto dello stregone";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto del monaco";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                return a;
            }
            case "vanbrace_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio dello stregone runico";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                return a;
            }
            case "vanbrace_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                return a;
            }
            case "vanbrace_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Conciapelli";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Presa Ferrea: non perdi mai l'arma, nemmeno per effetti che ignorano le resistenze (livello Iniziato)";
                return a;
            }
            case "vanbrace_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Scaglia di Pietra: la Rottura subita si trasforma in armatura temporanea (livello Iniziato)";
                return a;
            }
            case "vanbrace_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Nome Nascosto: il primo nemico che ti marchia perde il proprio turno (livello Iniziato)";
                return a;
            }
            case "vanbrace_defense_30_40_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto del veggente";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto dell'eremita";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                return a;
            }
            case "vanbrace_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                return a;
            }
            case "vanbrace_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                return a;
            }
            case "vanbrace_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Presa Ferrea: non perdi mai l'arma, nemmeno per effetti che ignorano le resistenze (livello Adepto)";
                return a;
            }
            case "vanbrace_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Scaglia di Pietra: la Rottura subita si trasforma in armatura temporanea (livello Adepto)";
                return a;
            }
            case "vanbrace_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Patriarca";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Nome Nascosto: il primo nemico che ti marchia perde il proprio turno (livello Adepto)";
                return a;
            }
            case "vanbrace_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Vambrace del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Presa Ferrea: non perdi mai l'arma, nemmeno per effetti che ignorano le resistenze (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] Presa Eterna: la tua arma non può mai essere rimossa o sostituita contro la tua volontà (livello Ascendente)";
                return a;
            }
            case "vanbrace_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Vambrace dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Scaglia di Pietra: la Rottura subita si trasforma in armatura temporanea (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] Corpo di Roccia: la Rottura non riduce mai le tue statistiche (livello Ascendente)";
                return a;
            }
            case "vanbrace_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Vambrace della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Nome Nascosto: il primo nemico che ti marchia perde il proprio turno (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Senza Nome: non puoi mai essere marchiato o bersagliato in modo prioritario (livello Ascendente)";
                return a;
            }
            case "vanbrace_defense_40_50_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto dell'oracolo";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manichetto del pellegrino";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "vanbrace_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                return a;
            }
            case "vanbrace_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                return a;
            }
            case "vanbrace_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Avambraccio del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                return a;
            }
            case "vanbrace_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Presa Ferrea: non perdi mai l'arma, nemmeno per effetti che ignorano le resistenze (livello Maestro)";
                return a;
            }
            case "vanbrace_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Scaglia di Pietra: la Rottura subita si trasforma in armatura temporanea (livello Maestro)";
                return a;
            }
            case "vanbrace_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Manopola d'Avambraccio del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Nome Nascosto: il primo nemico che ti marchia perde il proprio turno (livello Maestro)";
                return a;
            }
            case "vanbrace_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Vambrace dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% controllo dell'arma; +% stabilità in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] Presa Ferrea: non perdi mai l'arma, nemmeno per effetti che ignorano le resistenze (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] Presa Eterna: la tua arma non può mai essere rimossa o sostituita contro la tua volontà (livello Trascendente)";
                return a;
            }
            case "vanbrace_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Vambrace del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a Terra; -% usura dell'equipaggiamento da Rottura";
                a.fullSetOrStyleStub = "[Full Set: Veggente] Scaglia di Pietra: la Rottura subita si trasforma in armatura temporanea (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] Corpo di Roccia: la Rottura non riduce mai le tue statistiche (livello Trascendente)";
                return a;
            }
            case "vanbrace_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.VANBRACE, WeightClass.LEGGERA);
                a.name = "Vambrace della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 2;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza ai marchi; -% danni extra subiti da bersaglio marchiato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Nome Nascosto: il primo nemico che ti marchia perde il proprio turno (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Senza Nome: non puoi mai essere marchiato o bersagliato in modo prioritario (livello Trascendente)";
                return a;
            }

            // ══════ Gauntlet ══════
            case "gauntlet_defense_0_10_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di cuoio";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto a punta del mago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto del prete";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_defense_10_20_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto dell'apprendista";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto del chierico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                return a;
            }
            case "gauntlet_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                return a;
            }
            case "gauntlet_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola del chierico devoto";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                return a;
            }
            case "gauntlet_defense_20_30_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di pelle indurita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto dello stregone";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto del monaco";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                return a;
            }
            case "gauntlet_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola dello stregone runico";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                return a;
            }
            case "gauntlet_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                return a;
            }
            case "gauntlet_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Pugno Chiuso: puoi parare a mani nude senza penalità (livello Iniziato)";
                return a;
            }
            case "gauntlet_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Mano che Spezza: puoi interrompere una reazione elementale prima che si inneschi, una volta per combattimento (livello Iniziato)";
                return a;
            }
            case "gauntlet_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mano Salda: chi ti deruba lascia cadere un proprio oggetto (livello Iniziato)";
                return a;
            }
            case "gauntlet_defense_30_40_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto del veggente";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto dell'eremita";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                return a;
            }
            case "gauntlet_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                return a;
            }
            case "gauntlet_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                return a;
            }
            case "gauntlet_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Pugno Chiuso: puoi parare a mani nude senza penalità (livello Adepto)";
                return a;
            }
            case "gauntlet_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Mano che Spezza: puoi interrompere una reazione elementale prima che si inneschi, una volta per combattimento (livello Adepto)";
                return a;
            }
            case "gauntlet_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Patriarca";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mano Salda: chi ti deruba lascia cadere un proprio oggetto (livello Adepto)";
                return a;
            }
            case "gauntlet_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola d'Arme del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Pugno Chiuso: puoi parare a mani nude senza penalità (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] Mano di Ferro: non puoi mai essere disarmato (livello Ascendente)";
                return a;
            }
            case "gauntlet_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola d'Arme dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Mano che Spezza: puoi interrompere una reazione elementale prima che si inneschi, una volta per combattimento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Mano Neutra: puoi interrompere una reazione elementale ogni volta che si innesca (livello Ascendente)";
                return a;
            }
            case "gauntlet_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola d'Arme della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mano Salda: chi ti deruba lascia cadere un proprio oggetto (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Mano Vuota: non puoi mai essere derubato di oggetti o componenti (livello Ascendente)";
                return a;
            }
            case "gauntlet_defense_40_50_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto dell'oracolo";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto del pellegrino";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "gauntlet_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                return a;
            }
            case "gauntlet_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                return a;
            }
            case "gauntlet_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                return a;
            }
            case "gauntlet_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Pugno Chiuso: puoi parare a mani nude senza penalità (livello Maestro)";
                return a;
            }
            case "gauntlet_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Mano che Spezza: puoi interrompere una reazione elementale prima che si inneschi, una volta per combattimento (livello Maestro)";
                return a;
            }
            case "gauntlet_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Guanto di Piastra del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mano Salda: chi ti deruba lascia cadere un proprio oggetto (livello Maestro)";
                return a;
            }
            case "gauntlet_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola d'Arme dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% presa sull'arma; -% prob. di essere disarmato in parata";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Pugno Chiuso: puoi parare a mani nude senza penalità (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] Mano di Ferro: non puoi mai essere disarmato (livello Trascendente)";
                return a;
            }
            case "gauntlet_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola d'Arme del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% durata di TUTTE le reazioni elementali; +% prob. di annullarne l'innesco";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Mano che Spezza: puoi interrompere una reazione elementale prima che si inneschi, una volta per combattimento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Mano Neutra: puoi interrompere una reazione elementale ogni volta che si innesca (livello Trascendente)";
                return a;
            }
            case "gauntlet_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.GAUNTLET, WeightClass.MEDIA);
                a.name = "Manopola d'Arme della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 1;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza al furto; +% prob. di recuperare subito ciò che ti è stato rubato";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Mano Salda: chi ti deruba lascia cadere un proprio oggetto (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Mano Vuota: non puoi mai essere derubato di oggetti o componenti (livello Trascendente)";
                return a;
            }

            // ══════ Cuirasse ══════
            case "cuirasse_defense_0_10_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto di cuoio";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 1;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_elemental_def_0_10_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto a punta del mago";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 1;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_special_def_0_10_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto del prete";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 1;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_defense_10_20_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto di cuoio grezzo";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 2;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_elemental_def_10_20_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto dell'apprendista";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 2;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_special_def_10_20_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto del chierico";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 2;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_defense_10_20_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto di cuoio rinforzato";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 3;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                return a;
            }
            case "cuirasse_elemental_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto dell'apprendista incantato";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 3;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                return a;
            }
            case "cuirasse_special_def_10_20_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto del chierico devoto";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 3;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                return a;
            }
            case "cuirasse_defense_20_30_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto di pelle indurita";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 4;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_elemental_def_20_30_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto dello stregone";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 4;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_special_def_20_30_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto del monaco";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 4;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_defense_20_30_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto di pelle borchiata";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 5;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                return a;
            }
            case "cuirasse_elemental_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto dello stregone runico";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 5;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                return a;
            }
            case "cuirasse_special_def_20_30_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto del monaco consacrato";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 5;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                return a;
            }
            case "cuirasse_defense_20_30_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Conciapelli";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 6;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Corazza Intera: i colpi critici contro di te diventano colpi normali (livello Iniziato)";
                return a;
            }
            case "cuirasse_elemental_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza dell'Arcano Maestro";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 6;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Guscio Prismatico: assorbi una volta per combattimento un attacco elementale e ne guadagni l'elemento (livello Iniziato)";
                return a;
            }
            case "cuirasse_special_def_20_30_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Sommo Sacerdote";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 6;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Fede Inamovibile: nessun attacco può ignorare la tua armatura (livello Iniziato)";
                return a;
            }
            case "cuirasse_defense_30_40_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto di cuoio bollito";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 7;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_elemental_def_30_40_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto del veggente";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 7;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_special_def_30_40_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto dell'eremita";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 7;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_defense_30_40_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto di cuoio bollito rinforzato";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 8;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                return a;
            }
            case "cuirasse_elemental_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto del veggente sigillato";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 8;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                return a;
            }
            case "cuirasse_special_def_30_40_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto dell'eremita venerato";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 8;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                return a;
            }
            case "cuirasse_defense_30_40_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Maestro Conciapelli";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 9;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Corazza Intera: i colpi critici contro di te diventano colpi normali (livello Adepto)";
                return a;
            }
            case "cuirasse_elemental_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza dell'Arcimago";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 9;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Guscio Prismatico: assorbi una volta per combattimento un attacco elementale e ne guadagni l'elemento (livello Adepto)";
                return a;
            }
            case "cuirasse_special_def_30_40_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Patriarca";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 9;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Fede Inamovibile: nessun attacco può ignorare la tua armatura (livello Adepto)";
                return a;
            }
            case "cuirasse_defense_30_40_mythic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Petto d'Acciaio del Lupo Ombra";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 10;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Corazza Intera: i colpi critici contro di te diventano colpi normali (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Corazza Assoluta: non puoi mai subire un colpo critico (livello Ascendente)";
                return a;
            }
            case "cuirasse_elemental_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Petto d'Acciaio dell'Occhio Arcano";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 10;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Guscio Prismatico: assorbi una volta per combattimento un attacco elementale e ne guadagni l'elemento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Guscio Infinito: assorbi un attacco elementale a round, non solo una volta a combattimento (livello Ascendente)";
                return a;
            }
            case "cuirasse_special_def_30_40_mythic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Petto d'Acciaio della Luce Eterna";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 10;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Fede Inamovibile: nessun attacco può ignorare la tua armatura (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Corazza della Fede: la tua armatura non può mai essere ignorata (livello Ascendente)";
                return a;
            }
            case "cuirasse_defense_40_50_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto di pelle di bestia";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 11;
                a.armorSetID = 1;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_elemental_def_40_50_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto dell'oracolo";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 11;
                a.armorSetID = 2;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_special_def_40_50_common": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corpetto del pellegrino";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 11;
                a.armorSetID = 3;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuirasse_defense_40_50_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto di pelle di bestia temprata";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 12;
                a.armorSetID = 1;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                return a;
            }
            case "cuirasse_elemental_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto dell'oracolo custodito";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 12;
                a.armorSetID = 2;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                return a;
            }
            case "cuirasse_special_def_40_50_high_quality": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corsaletto del pellegrino redento";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 12;
                a.armorSetID = 3;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                return a;
            }
            case "cuirasse_defense_40_50_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Cacciatore Leggendario";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 13;
                a.armorSetID = 1;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Corazza Intera: i colpi critici contro di te diventano colpi normali (livello Maestro)";
                return a;
            }
            case "cuirasse_elemental_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Gran Tessitore di Magia";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 13;
                a.armorSetID = 2;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Guscio Prismatico: assorbi una volta per combattimento un attacco elementale e ne guadagni l'elemento (livello Maestro)";
                return a;
            }
            case "cuirasse_special_def_40_50_masterwork": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Corazza del Profeta Sacro";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 13;
                a.armorSetID = 3;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Fede Inamovibile: nessun attacco può ignorare la tua armatura (livello Maestro)";
                return a;
            }
            case "cuirasse_defense_40_50_mythic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Petto d'Acciaio dell'Ultimo Custode";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 14;
                a.armorSetID = 1;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa da perforazione e taglio sul busto";
                a.fullSetOrStyleStub = "[Full Set: Guardia] STUB — Corazza Intera: i colpi critici contro di te diventano colpi normali (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Corazza Assoluta: non puoi mai subire un colpo critico (livello Trascendente)";
                return a;
            }
            case "cuirasse_elemental_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Petto d'Acciaio del Velo Infinito";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 14;
                a.armorSetID = 2;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% resistenza a TUTTI gli elementi (valore ridotto); -% danni ad area";
                a.fullSetOrStyleStub = "[Full Set: Veggente] STUB — Guscio Prismatico: assorbi una volta per combattimento un attacco elementale e ne guadagni l'elemento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Guscio Infinito: assorbi un attacco elementale a round, non solo una volta a combattimento (livello Trascendente)";
                return a;
            }
            case "cuirasse_special_def_40_50_mythic": {
                Armor a = new Armor(ArmorType.CUIRASSE, WeightClass.MEDIA);
                a.name = "Petto d'Acciaio della Grazia Divina";
                a.rifiniture = 1; a.legamenti = 3;
                a.metalli[0] = 14;
                a.armorSetID = 3;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% difesa contro i danni che ignorano l'armatura";
                a.fullSetOrStyleStub = "[Full Set: Voto] STUB — Fede Inamovibile: nessun attacco può ignorare la tua armatura (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Corazza della Fede: la tua armatura non può mai essere ignorata (livello Trascendente)";
                return a;
            }

            // ══════ Cuisse ══════
            case "cuisse_speed_0_10_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto leggera";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 1;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_elusivity_0_10_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto del ladro";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 1;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_speed_10_20_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto agile";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 2;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_elusivity_10_20_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto del borsaiolo";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 2;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_speed_10_20_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale agile rinforzata";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 3;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                return a;
            }
            case "cuisse_elusivity_10_20_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale del borsaiolo esperto";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 3;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                return a;
            }
            case "cuisse_speed_20_30_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto snella";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 4;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_elusivity_20_30_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto del furfante";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 4;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_speed_20_30_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale snella temprata";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 5;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                return a;
            }
            case "cuisse_elusivity_20_30_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale del furfante scaltro";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 5;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                return a;
            }
            case "cuisse_speed_20_30_masterwork": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Difesa di Coscia del Corridore";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 6;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Passo Continuo: muoverti non consuma mai il turno (livello Iniziato)";
                return a;
            }
            case "cuisse_elusivity_20_30_masterwork": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Difesa di Coscia del Maestro Ladro";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 6;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Vuoto: una schivata riuscita ti rende intangibile fino al tuo turno (livello Iniziato)";
                return a;
            }
            case "cuisse_speed_30_40_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto veloce";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 7;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_elusivity_30_40_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto dell'assassino";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 7;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_speed_30_40_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale veloce impareggiabile";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 8;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                return a;
            }
            case "cuisse_elusivity_30_40_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale dell'assassino ombra";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 8;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                return a;
            }
            case "cuisse_speed_30_40_masterwork": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Difesa di Coscia del Maestro Fuggitivo";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 9;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Passo Continuo: muoverti non consuma mai il turno (livello Adepto)";
                return a;
            }
            case "cuisse_elusivity_30_40_masterwork": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Difesa di Coscia del Signore dei Ladri";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 9;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Vuoto: una schivata riuscita ti rende intangibile fino al tuo turno (livello Adepto)";
                return a;
            }
            case "cuisse_speed_30_40_mythic": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Gamba di Piastra del Vento del Nord";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 10;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Passo Continuo: muoverti non consuma mai il turno (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Passo Impossibile: immune a ogni forma di incapacitazione (livello Ascendente)";
                return a;
            }
            case "cuisse_elusivity_30_40_mythic": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Gamba di Piastra dell'Ombra Silenziosa";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 10;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Vuoto: una schivata riuscita ti rende intangibile fino al tuo turno (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Fantasma: una schivata riuscita annulla anche l'effetto di stato dell'attacco schivato (livello Ascendente)";
                return a;
            }
            case "cuisse_speed_40_50_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto scattante";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 11;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_elusivity_40_50_common": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Coscialetto del contrabbandiere";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 11;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "cuisse_speed_40_50_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale scattante ineguagliabile";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 12;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                return a;
            }
            case "cuisse_elusivity_40_50_high_quality": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Cosciale del contrabbandiere leggendario";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 12;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                return a;
            }
            case "cuisse_speed_40_50_masterwork": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Difesa di Coscia del Fulmine Silente";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 13;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Passo Continuo: muoverti non consuma mai il turno (livello Maestro)";
                return a;
            }
            case "cuisse_elusivity_40_50_masterwork": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Difesa di Coscia del Re dei Bassifondi";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 13;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Vuoto: una schivata riuscita ti rende intangibile fino al tuo turno (livello Maestro)";
                return a;
            }
            case "cuisse_speed_40_50_mythic": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Gamba di Piastra dell'Ultimo Respiro";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 14;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% velocità di movimento; -% prob. di essere incapacitato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Passo Continuo: muoverti non consuma mai il turno (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Passo Impossibile: immune a ogni forma di incapacitazione (livello Trascendente)";
                return a;
            }
            case "cuisse_elusivity_40_50_mythic": {
                Armor a = new Armor(ArmorType.CUISSE, WeightClass.MEDIA);
                a.name = "Gamba di Piastra dell'Ultimo Fantasma";
                a.rifiniture = 1; a.metalli[0] = 4;
                a.legamenti = 14;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% prob. di schivata; +% danni sul colpo dopo una schivata riuscita";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Vuoto: una schivata riuscita ti rende intangibile fino al tuo turno (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Fantasma: una schivata riuscita annulla anche l'effetto di stato dell'attacco schivato (livello Trascendente)";
                return a;
            }

            // ══════ Poleyn ══════
            case "poleyn_speed_0_10_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello leggera";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 1;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_elusivity_0_10_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello del ladro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 1;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_speed_10_20_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello agile";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 2;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_elusivity_10_20_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello del borsaiolo";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 2;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_speed_10_20_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera agile rinforzata";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 3;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                return a;
            }
            case "poleyn_elusivity_10_20_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera del borsaiolo esperto";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 3;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                return a;
            }
            case "poleyn_speed_20_30_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello snella";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 4;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_elusivity_20_30_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello del furfante";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 4;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_speed_20_30_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera snella temprata";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 5;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                return a;
            }
            case "poleyn_elusivity_20_30_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera del furfante scaltro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 5;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                return a;
            }
            case "poleyn_speed_20_30_masterwork": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Protezione del Ginocchio del Corridore";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 6;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Ginocchio Leggero: l'armatura pesante non applica più penalità di velocità (livello Iniziato)";
                return a;
            }
            case "poleyn_elusivity_20_30_masterwork": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Protezione del Ginocchio del Maestro Ladro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 6;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Anticipo: agisci sempre per primo nel primo round di ogni combattimento (livello Iniziato)";
                return a;
            }
            case "poleyn_speed_30_40_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello veloce";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 7;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_elusivity_30_40_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello dell'assassino";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 7;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_speed_30_40_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera veloce impareggiabile";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 8;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                return a;
            }
            case "poleyn_elusivity_30_40_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera dell'assassino ombra";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 8;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                return a;
            }
            case "poleyn_speed_30_40_masterwork": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Protezione del Ginocchio del Maestro Fuggitivo";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 9;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Ginocchio Leggero: l'armatura pesante non applica più penalità di velocità (livello Adepto)";
                return a;
            }
            case "poleyn_elusivity_30_40_masterwork": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Protezione del Ginocchio del Signore dei Ladri";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 9;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Anticipo: agisci sempre per primo nel primo round di ogni combattimento (livello Adepto)";
                return a;
            }
            case "poleyn_speed_30_40_mythic": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera di Piastra del Vento del Nord";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 10;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Ginocchio Leggero: l'armatura pesante non applica più penalità di velocità (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Peso Nullo: l'armatura non ha mai peso percepito, qualunque sia il tipo (livello Ascendente)";
                return a;
            }
            case "poleyn_elusivity_30_40_mythic": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera di Piastra dell'Ombra Silenziosa";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 10;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Anticipo: agisci sempre per primo nel primo round di ogni combattimento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Sempre Pronto: agisci sempre per primo, in ogni round, non solo il primo (livello Ascendente)";
                return a;
            }
            case "poleyn_speed_40_50_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello scattante";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 11;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_elusivity_40_50_common": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiello del contrabbandiere";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 11;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "poleyn_speed_40_50_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera scattante ineguagliabile";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 12;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                return a;
            }
            case "poleyn_elusivity_40_50_high_quality": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera del contrabbandiere leggendario";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 12;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                return a;
            }
            case "poleyn_speed_40_50_masterwork": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Protezione del Ginocchio del Fulmine Silente";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 13;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Ginocchio Leggero: l'armatura pesante non applica più penalità di velocità (livello Maestro)";
                return a;
            }
            case "poleyn_elusivity_40_50_masterwork": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Protezione del Ginocchio del Re dei Bassifondi";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 13;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Anticipo: agisci sempre per primo nel primo round di ogni combattimento (livello Maestro)";
                return a;
            }
            case "poleyn_speed_40_50_mythic": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera di Piastra dell'Ultimo Respiro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 14;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% peso percepito dell'armatura; +% velocità con armatura pesante";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Ginocchio Leggero: l'armatura pesante non applica più penalità di velocità (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Peso Nullo: l'armatura non ha mai peso percepito, qualunque sia il tipo (livello Trascendente)";
                return a;
            }
            case "poleyn_elusivity_40_50_mythic": {
                Armor a = new Armor(ArmorType.POLEYN, WeightClass.LEGGERA);
                a.name = "Ginocchiera di Piastra dell'Ultimo Fantasma";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 14;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% velocità di riposizionamento; +% prob. di agire per primo nel round";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Anticipo: agisci sempre per primo nel primo round di ogni combattimento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Sempre Pronto: agisci sempre per primo, in ogni round, non solo il primo (livello Trascendente)";
                return a;
            }

            // ══════ Greave ══════
            case "greave_speed_0_10_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera leggera";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 1;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_elusivity_0_10_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera del ladro";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 1;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_speed_10_20_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera agile";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 2;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_elusivity_10_20_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera del borsaiolo";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 2;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_speed_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera agile rinforzata";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 3;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                return a;
            }
            case "greave_elusivity_10_20_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera del borsaiolo esperto";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 3;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                return a;
            }
            case "greave_speed_20_30_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera snella";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 4;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_elusivity_20_30_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera del furfante";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 4;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_speed_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera snella temprata";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 5;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                return a;
            }
            case "greave_elusivity_20_30_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera del furfante scaltro";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 5;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                return a;
            }
            case "greave_speed_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Stinchiera del Corridore";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 6;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Corsa Libera: immune a tutti gli effetti di rallentamento (livello Iniziato)";
                return a;
            }
            case "greave_elusivity_20_30_masterwork": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Stinchiera del Maestro Ladro";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 6;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Passo Muto: i nemici non ti individuano finché non attacchi (livello Iniziato)";
                return a;
            }
            case "greave_speed_30_40_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera veloce";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 7;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_elusivity_30_40_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera dell'assassino";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 7;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_speed_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera veloce impareggiabile";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 8;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                return a;
            }
            case "greave_elusivity_30_40_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera dell'assassino ombra";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 8;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                return a;
            }
            case "greave_speed_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Stinchiera del Maestro Fuggitivo";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 9;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Corsa Libera: immune a tutti gli effetti di rallentamento (livello Adepto)";
                return a;
            }
            case "greave_elusivity_30_40_masterwork": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Stinchiera del Signore dei Ladri";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 9;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Passo Muto: i nemici non ti individuano finché non attacchi (livello Adepto)";
                return a;
            }
            case "greave_speed_30_40_mythic": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera di Piastra del Vento del Nord";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 10;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Corsa Libera: immune a tutti gli effetti di rallentamento (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Vento tra le Gambe: essere rallentato ti dà invece un bonus di velocità permanente per il resto del combattimento (livello Ascendente)";
                return a;
            }
            case "greave_elusivity_30_40_mythic": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera di Piastra dell'Ombra Silenziosa";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 10;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Passo Muto: i nemici non ti individuano finché non attacchi (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Silenzio Totale: non puoi mai essere individuato prima di attaccare, nemmeno da abilità dedicate (livello Ascendente)";
                return a;
            }
            case "greave_speed_40_50_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera scattante";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 11;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_elusivity_40_50_common": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Gambiera del contrabbandiere";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 11;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "greave_speed_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera scattante ineguagliabile";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 12;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                return a;
            }
            case "greave_elusivity_40_50_high_quality": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera del contrabbandiere leggendario";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 12;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                return a;
            }
            case "greave_speed_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Stinchiera del Fulmine Silente";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 13;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Corsa Libera: immune a tutti gli effetti di rallentamento (livello Maestro)";
                return a;
            }
            case "greave_elusivity_40_50_masterwork": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Stinchiera del Re dei Bassifondi";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 13;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Passo Muto: i nemici non ti individuano finché non attacchi (livello Maestro)";
                return a;
            }
            case "greave_speed_40_50_mythic": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera di Piastra dell'Ultimo Respiro";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 14;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% durata dei rallentamenti; +% velocità dopo essere stato rallentato";
                a.fullSetOrStyleStub = "[Full Set: Passo] STUB — Corsa Libera: immune a tutti gli effetti di rallentamento (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Vento tra le Gambe: essere rallentato ti dà invece un bonus di velocità permanente per il resto del combattimento (livello Trascendente)";
                return a;
            }
            case "greave_elusivity_40_50_mythic": {
                Armor a = new Armor(ArmorType.GREAVE, WeightClass.MEDIA);
                a.name = "Schiniera di Piastra dell'Ultimo Fantasma";
                a.rifiniture = 1; a.metalli[0] = 3;
                a.legamenti = 14;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% furtività; +% danni sul primo colpo se non sei stato individuato";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Passo Muto: i nemici non ti individuano finché non attacchi (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Silenzio Totale: non puoi mai essere individuato prima di attaccare, nemmeno da abilità dedicate (livello Trascendente)";
                return a;
            }

            // ══════ Sabaton ══════
            case "sabaton_speed_0_10_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare leggera";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 1;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_elusivity_0_10_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare del ladro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 1;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_speed_10_20_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare agile";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 2;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_elusivity_10_20_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare del borsaiolo";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 2;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_speed_10_20_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa agile rinforzata";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 3;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                return a;
            }
            case "sabaton_elusivity_10_20_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa del borsaiolo esperto";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 3;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                return a;
            }
            case "sabaton_speed_20_30_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare snella";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 4;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_elusivity_20_30_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare del furfante";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 4;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_speed_20_30_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa snella temprata";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 5;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                return a;
            }
            case "sabaton_elusivity_20_30_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa del furfante scaltro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 5;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                return a;
            }
            case "sabaton_speed_20_30_masterwork": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa di Ferro del Corridore";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 6;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                a.fullSetOrStyleStub = "[Full Set: Passo] Suola Salda: il terreno ostile non ti rallenta né ti danneggia (livello Iniziato)";
                return a;
            }
            case "sabaton_elusivity_20_30_masterwork": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa di Ferro del Maestro Ladro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 6;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Dissolvenza: una fuga riuscita non fa perdere il loot né il progresso della zona (livello Iniziato)";
                return a;
            }
            case "sabaton_speed_30_40_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare veloce";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 7;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_elusivity_30_40_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare dell'assassino";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 7;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_speed_30_40_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa veloce impareggiabile";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 8;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                return a;
            }
            case "sabaton_elusivity_30_40_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa dell'assassino ombra";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 8;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                return a;
            }
            case "sabaton_speed_30_40_masterwork": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa di Ferro del Maestro Fuggitivo";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 9;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                a.fullSetOrStyleStub = "[Full Set: Passo] Suola Salda: il terreno ostile non ti rallenta né ti danneggia (livello Adepto)";
                return a;
            }
            case "sabaton_elusivity_30_40_masterwork": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa di Ferro del Signore dei Ladri";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 9;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Dissolvenza: una fuga riuscita non fa perdere il loot né il progresso della zona (livello Adepto)";
                return a;
            }
            case "sabaton_speed_30_40_mythic": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Solerette d'Arme del Vento del Nord";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 10;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                a.fullSetOrStyleStub = "[Full Set: Passo] Suola Salda: il terreno ostile non ti rallenta né ti danneggia (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Piede Immune: nessun terreno o trappola ambientale può mai danneggiarti (livello Ascendente)";
                return a;
            }
            case "sabaton_elusivity_30_40_mythic": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Solerette d'Arme dell'Ombra Silenziosa";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 10;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Dissolvenza: una fuga riuscita non fa perdere il loot né il progresso della zona (livello Adepto)";
                a.mythicUniqueStub   = "[Mitico] STUB — Passo Perduto: la fuga riesce sempre, indipendentemente da livello o abilità del nemico (livello Ascendente)";
                return a;
            }
            case "sabaton_speed_40_50_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare scattante";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 11;
                a.armorSetID = 4;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_elusivity_40_50_common": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Calzare del contrabbandiere";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 11;
                a.armorSetID = 5;
                a.rarity = Rarity.COMMON;
                return a;
            }
            case "sabaton_speed_40_50_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa scattante ineguagliabile";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 12;
                a.armorSetID = 4;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                return a;
            }
            case "sabaton_elusivity_40_50_high_quality": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa del contrabbandiere leggendario";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 12;
                a.armorSetID = 5;
                a.rarity = Rarity.HIGH_QUALITY;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                return a;
            }
            case "sabaton_speed_40_50_masterwork": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa di Ferro del Fulmine Silente";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 13;
                a.armorSetID = 4;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                a.fullSetOrStyleStub = "[Full Set: Passo] Suola Salda: il terreno ostile non ti rallenta né ti danneggia (livello Maestro)";
                return a;
            }
            case "sabaton_elusivity_40_50_masterwork": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Scarpa di Ferro del Re dei Bassifondi";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 13;
                a.armorSetID = 5;
                a.rarity = Rarity.MASTERWORK;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Dissolvenza: una fuga riuscita non fa perdere il loot né il progresso della zona (livello Maestro)";
                return a;
            }
            case "sabaton_speed_40_50_mythic": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Solerette d'Arme dell'Ultimo Respiro";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 14;
                a.armorSetID = 4;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "-% danni ambientali; +% stabilità su terreno pericoloso";
                a.fullSetOrStyleStub = "[Full Set: Passo] Suola Salda: il terreno ostile non ti rallenta né ti danneggia (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Piede Immune: nessun terreno o trappola ambientale può mai danneggiarti (livello Trascendente)";
                return a;
            }
            case "sabaton_elusivity_40_50_mythic": {
                Armor a = new Armor(ArmorType.SABATON, WeightClass.LEGGERA);
                a.name = "Solerette d'Arme dell'Ultimo Fantasma";
                a.rifiniture = 1; a.metalli[0] = 2;
                a.legamenti = 14;
                a.armorSetID = 5;
                a.rarity = Rarity.MYTHIC;
                a.synergyBonusStub   = "+% prob. di fuga; -% prob. di essere inseguito dopo la fuga";
                a.fullSetOrStyleStub = "[Full Set: Ombra] STUB — Dissolvenza: una fuga riuscita non fa perdere il loot né il progresso della zona (livello Maestro)";
                a.mythicUniqueStub   = "[Mitico] STUB — Passo Perduto: la fuga riesce sempre, indipendentemente da livello o abilità del nemico (livello Trascendente)";
                return a;
            }

            default:
                System.err.println("Armor non trovata: " + id);
                return null;
        }
    }
}
