package items;

import items.Weapon.WeaponSubtype;
import items.Weapon.DamageType;
import items.Armor.WeightClass;

/**
 * Dispatcher statico per le definizioni delle armi — stesso stile di combat/Ability.java e
 * quest/QuestRegistry.java. Ogni chiamata a get() crea un'istanza NUOVA (Weapon ha stato
 * mutabile). UN oggetto per ciascuno dei 16 sottotipi, così c'è content da testare per tutti.
 *
 * ── Come aggiungere una nuova arma ──────────────────────────────
 *   1. Aggiungi un case con un id univoco (stringa)
 *   2. new Weapon(WeaponSubtype.X), poi imposta solo i campi che ti interessano
 *   3. I numeri qui sotto sono placeholder di esempio, non un bilanciamento reale
 */
public class WeaponRegistry {
    public static Weapon get(String id) {
        switch (id) {
            // ── Spade (bonus taglienti) ──────────────────────────
            case "short_sword_basic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_CORTA);
                w.name = "Spada Corta"; w.description = "Leggera e maneggevole.";
                w.affilatezza = 1; w.pomo = 5; w.manico = 10; w.guardia = 3;
                w.taglio = 8; w.punta = 2; w.metallo = 50; w.legamenti = 50;
                w.gemme[0] = new Component("ruby_shard"); // esempio: prova che i componenti funzionano
                return w;
            }
            case "long_sword_basic": {
                Weapon w = new Weapon(WeaponSubtype.SPADA_LUNGA);
                w.name = "Spada Lunga"; w.description = "Portata maggiore, più danno da taglio.";
                w.affilatezza = 1; w.pomo = 3; w.manico = 5; w.guardia = 4;
                w.taglio = 14; w.punta = 3; w.metallo = 55; w.legamenti = 50;
                return w;
            }
            case "whip_basic": {
                Weapon w = new Weapon(WeaponSubtype.FRUSTA);
                w.name = "Frusta"; w.description = "Colpisce da lontano, rischia di far cadere l'arma al nemico.";
                w.affilatezza = 1; w.pomo = 8; w.manico = 6;
                w.taglio = 4; w.metallo = 20; w.legamenti = 30;
                w.disarmChance = 20;
                return w;
            }

            // ── Mazze (bonus contundenti) ─────────────────────────
            case "spiked_mace_basic": {
                Weapon w = new Weapon(WeaponSubtype.MAZZA_CHIODATA);
                w.name = "Mazza Chiodata"; w.description = "Contundente e perforante insieme.";
                w.affilatezza = 1; w.pomo = 1; w.manico = 4; w.guardia = 2;
                w.contundente = 10; w.punta = 6; w.metallo = 40; w.legamenti = 45;
                w.comboTypes = new DamageType[]{DamageType.PERFORANTE, DamageType.CONTUNDENTE};
                return w;
            }
            case "axe_basic": {
                Weapon w = new Weapon(WeaponSubtype.ASCIA);
                w.name = "Ascia"; w.description = "Taglia e stordisce con il peso della lama.";
                w.affilatezza = 1; w.pomo = 1; w.manico = 3; w.guardia = 1;
                w.taglio = 9; w.contundente = 7; w.metallo = 45; w.legamenti = 40;
                w.comboTypes = new DamageType[]{DamageType.TAGLIO, DamageType.CONTUNDENTE};
                return w;
            }
            case "greatsword_basic": {
                Weapon w = new Weapon(WeaponSubtype.SPADONE);
                w.name = "Spadone"; w.description = "Il danno viene dal peso, non dal filo.";
                w.affilatezza = 1; w.pomo = -2; w.manico = 2; w.guardia = 5;
                w.taglio = 3; w.metallo = 60; w.legamenti = 55;
                w.peso = 18;
                return w;
            }

            // ── Lance (bonus perforanti) ──────────────────────────
            case "long_spear_basic": {
                Weapon w = new Weapon(WeaponSubtype.LANCIA_LUNGA);
                w.name = "Lancia Lunga"; w.description = "Perfora, con un filo secondario da taglio.";
                w.affilatezza = 1; w.pomo = 2; w.manico = 5; w.guardia = 1;
                w.punta = 10; w.taglio = 4; w.metallo = 40; w.legamenti = 45;
                return w;
            }
            case "pike_basic": {
                Weapon w = new Weapon(WeaponSubtype.PICCA);
                w.name = "Picca"; w.description = "Portata estrema, ottimo controllo.";
                w.affilatezza = 1; w.pomo = 1; w.manico = 12; w.guardia = 1;
                w.punta = 8; w.metallo = 35; w.legamenti = 50;
                return w;
            }
            case "scythe_basic": {
                Weapon w = new Weapon(WeaponSubtype.FALCE);
                w.name = "Falce"; w.description = "Un fendente ampio, rischia di stordire.";
                w.affilatezza = 1; w.pomo = 2; w.manico = 4; w.guardia = 1;
                w.punta = 7; w.taglio = 5; w.metallo = 35; w.legamenti = 35;
                w.stunChance = 15;
                return w;
            }

            // ── Armi a distanza (bonus vari) ──────────────────────
            case "short_bow_basic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_CORTO);
                w.name = "Arco Corto"; w.description = "Rapido, ignora le protezioni più leggere.";
                w.affilatezza = 1; w.pomo = 6; w.manico = 5;
                w.punta = 7; w.metallo = 20; w.legamenti = 30;
                w.penetratesUpTo = WeightClass.LEGGERA; // struttura pronta, meccanismo non collegato — vedi STATUS.md
                return w;
            }
            case "long_bow_basic": {
                Weapon w = new Weapon(WeaponSubtype.ARCO_LUNGO);
                w.name = "Arco Lungo"; w.description = "Lento da tendere, perfora anche l'armatura pesante.";
                w.affilatezza = 1; w.pomo = 2; w.manico = 4;
                w.punta = 12; w.metallo = 25; w.legamenti = 35;
                w.penetratesUpTo = WeightClass.PESANTE;
                return w;
            }
            case "crossbow_basic": {
                Weapon w = new Weapon(WeaponSubtype.BALESTRA);
                w.name = "Balestra"; w.description = "Molto danno, lentissima da ricaricare.";
                w.affilatezza = 1; w.pomo = -6; w.manico = 6;
                w.punta = 16; w.metallo = 30; w.legamenti = 30;
                return w;
            }
            case "throwing_knife_basic": {
                Weapon w = new Weapon(WeaponSubtype.COLTELLO_DA_LANCIO);
                w.name = "Coltello da Lancio"; w.description = "Leggero, può rispondere al colpo subito.";
                w.affilatezza = 1; w.pomo = 7; w.manico = 3;
                w.taglio = 3; w.punta = 3; w.metallo = 15; w.legamenti = 20;
                w.counterattackChance = 15;
                return w;
            }

            // ── Difensive (bonus difensivi) ────────────────────────
            case "shield_basic": {
                Weapon w = new Weapon(WeaponSubtype.SCUDO);
                w.name = "Scudo"; w.description = "Largo e solido, ma appesantisce.";
                w.affilatezza = 1; w.pomo = -4; w.manico = 3; w.guardia = 14;
                w.metallo = 50; w.legamenti = 45;
                return w;
            }
            case "buckler_basic": {
                Weapon w = new Weapon(WeaponSubtype.BROQUEL);
                w.name = "Broquel"; w.description = "Piccolo e veloce, protegge meno di uno scudo.";
                w.affilatezza = 1; w.pomo = 4; w.manico = 5; w.guardia = 6;
                w.metallo = 30; w.legamenti = 30;
                return w;
            }
            case "sai_basic": {
                Weapon w = new Weapon(WeaponSubtype.SAI);
                w.name = "Sai"; w.description = "Una spada secondaria per la mano debole.";
                w.affilatezza = 1; w.pomo = 3; w.manico = 6; w.guardia = 3;
                w.taglio = 4; w.punta = 3; w.metallo = 25; w.legamenti = 25;
                return w;
            }

            default:
                System.err.println("Weapon non trovata: " + id);
                return null;
        }
    }
}
