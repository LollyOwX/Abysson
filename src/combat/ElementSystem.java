package combat;

import entity.Entity;

import java.util.Random;

/**
 * Sistema elementi, effetti di stato e reazioni.
 *
 * Contiene:
 *   - enum Element         → i 7 elementi
 *   - enum StatusEffect    → tutti gli effetti di stato
 *   - class ActiveEffect   → effetto attivo su un'Entity (effect + durata)
 *   - logica statica       → vantaggi, reazioni, applicazione, tick di turno
 *
 * Reaction.java è tenuta separata (oggetto dati con costruttore).
 *
 * ── Come aggiungere un nuovo elemento ───────────────────────────────
 *   1. Aggiungilo all'enum Element
 *   2. Aggiungi le reazioni in getReaction() (tabella simmetrica)
 *   3. Aggiungi vantaggi/svantaggi in getMultiplier() se necessario
 *
 * ── Come aggiungere un nuovo effetto di stato ────────────────────────
 *   1. Aggiungilo all'enum StatusEffect
 *   2. Aggiungi la logica in processTurnEffects() se ha tick per turno
 *   3. Aggiungi in isPositiveEffect() se è positivo
 *
 * ── Tabella reazioni (simmetrica) ───────────────────────────────────
 *           FIS    LUC    FUO    ACQ    TER    ARI    FUL
 *   FIS  [  POT    ESP    INF    ERO    ROT    TUR    SCO ]
 *   LUC  [  ESP    ACC    ABR    PUR    NAT    ILL    RAG ]
 *   FUO  [  INF    ABR    ESP    VAP    CAR    FIR    SOV ]
 *   ACQ  [  ERO    PUR    VAP    INO    INF    TEM    ELT ]
 *   TER  [  ROT    NAT    CAR    INF    RES    POL    DEV ]
 *   ARI  [  TUR    ILL    FIR    TEM    POL    STO    RAM ]
 *   FUL  [  SCO    RAG    SOV    ELT    DEV    RAM    FOL ]
 */
public class ElementSystem {

    private static final Random rng = new Random();

    public enum Element {
        NONE, FISICO, LUCE, FUOCO, ACQUA, TERRA, ARIA, FULMINE
    }

    public enum StatusEffect {
        NONE("Nessuno"),
        // ── Reazioni pure ──────────────────────────────────────────
        POTENZIAMENTO("Potenziamento"),       // +200% danni subiti questo turno
        ACCECAMENTO("Accecamento"),           // STUB - svantaggio precisione + no armi a distanza
        ESPLOSIONE("Esplosione"),             // Stordimento 3 turni + 150% danni
        INONDAZIONE("Inondazione"),           // Disarmo totale + Stordimento 1 turno
        RESISTENZA("Resistenza"),             // Impossibilità di ricevere nuovi effetti positivi
        STORDIMENTO("Stordimento"),           // Svantaggio tiro per colpire 5 turni
        FOLGORE("Folgore"),                   // Sul fallimento → 2d8 danni fulmine
        // ── Reazioni articolate ────────────────────────────────────
        ROTTURA("Rottura"),                   // +(10*liv)% danni subiti
        ESPOSIZIONE("Esposizione"),           // +1 durata effetti negativi subiti
        INFIAMMAZIONE("Infiammazione"),       // Sul fallimento → 1d6 danni fuoco
        EROSIONE("Erosione"),                 // Ogni turno rimuove RNG un effetto (pos o neg)
        TURBINIO("Turbinio"),                 // STUB - applica elemento a alleati vicini
        SCOSSA("Scossa"),                     // Chi attacca può farlo 2 volte
        ABRASIONE("Abrasione"),               // Abilità Fuoco hanno vantaggio
        PURIFICAZIONE("Purificazione"),       // Rimuove tutti gli effetti positivi
        NATURALIZZAZIONE("Naturalizzazione"), // Impossibilità di movimento/nuoto/volo
        ILLUMINAZIONE("Illuminazione"),       // Impossibilità di occultamento
        RAGGIO("Raggio"),                     // Ogni abilità causa 1d6 danni fulmine al bersaglio
        VAPORIZZAZIONE("Vaporizzazione"),     // Impossibilità di volo
        CARBONIZZAZIONE("Carbonizzazione"),   // Ogni turno causa 1d6 danni fuoco
        FIRENADO("Firenado"),                 // STUB - 2d6 fuoco + fuoco ad alleati vicini
        SOVRACCARICO("Sovraccarico"),         // Stordimento 1 turno
        INFANGATO("Infangato"),               // Impossibilità di movimento + svantaggio tiri salvezza
        TEMPESTA("Tempesta"),                 // 1d6 danni vento per 3 turni + applica Fulmine
        ELETTRIZZAZIONE("Elettrizzazione"),   // Entità con Acqua → 1d6 danni fulmine + riapplica Acqua
        POLVERIZZAZIONE("Polverizzazione"),   // Impedisce mira (no armi/magie a distanza)
        DEVIAZIONE("Deviazione"),             // Devia proiettili magici e fisici
        RAMIFICAZIONE("Ramificazione");       // STUB - catena fulmine esponenziale

        public final String displayName;
        StatusEffect(String displayName) { this.displayName = displayName; }
    }

    /**
     *   0  = solo questo turno
     *  -1  = permanente fino a fine combattimento
     *  >0  = N turni rimanenti
     */
    public static class ActiveEffect {
        public StatusEffect effect;
        public int duration;

        public ActiveEffect(StatusEffect effect, int duration) {
            this.effect = effect;
            this.duration = duration;
        }
    }

    public static double getMultiplier(Element attacker, Element defender) {
        if (attacker == Element.NONE || defender == Element.NONE) return 1.0;

        if (attacker == Element.TERRA && defender == Element.FUOCO) return 1.25;
        if (attacker == Element.ARIA && defender == Element.FUOCO) return 1.25;
        if (attacker == Element.ACQUA && defender == Element.FUOCO) return 2;
        if (attacker == Element.FULMINE && defender == Element.FUOCO) return 0.75;
        if (attacker == Element.LUCE && defender == Element.FUOCO) return 0.75;

        if (attacker == Element.FUOCO && defender == Element.ACQUA) return 2;
        if (attacker == Element.FULMINE && defender == Element.ACQUA) return 1.5;
        if (attacker == Element.LUCE && defender == Element.ACQUA) return 0.5;

        if (attacker == Element.ARIA && defender == Element.TERRA) return 1.25;
        if (attacker == Element.FULMINE && defender == Element.TERRA) return 0.5;

        if (attacker == Element.LUCE && defender == Element.ARIA) return 1.5;
        if (attacker == Element.FULMINE && defender == Element.ARIA) return 1.5;
        if (attacker == Element.TERRA && defender == Element.ARIA) return 0.75;

        if (attacker == Element.TERRA && defender == Element.FULMINE) return 2;
        if (attacker == Element.FUOCO && defender == Element.FULMINE) return 1.5;
        if (attacker == Element.ARIA && defender == Element.FULMINE) return 0.75;
        if (attacker == Element.ACQUA && defender == Element.FULMINE) return 0.75;

        if (attacker == Element.ACQUA && defender == Element.LUCE) return 1.25;
        if (attacker == Element.TERRA && defender == Element.LUCE) return 1.5;
        if (attacker == Element.FUOCO && defender == Element.LUCE) return 0.75;
        if (attacker == Element.FULMINE && defender == Element.LUCE) return 0.75;

        return 1.0;
    }

    /**
     * La tabella è simmetrica: l'ordine non conta.
     */
    public static Reaction getReaction(Element existing, Element incoming, int entityLevel) {
        if (existing == Element.NONE || incoming == Element.NONE) return Reaction.NONE;

        // Ordiniamo per simmetria (a <= b sempre)
        int a = Math.min(existing.ordinal(), incoming.ordinal());
        int b = Math.max(existing.ordinal(), incoming.ordinal());
        Element ea = Element.values()[a];
        Element eb = Element.values()[b];

        // ── Diagonale (stesso elemento) ───────────────────────────
        if (a == b) {
            switch (ea) {
                case FISICO:  return mk("Potenziamento",   0, Element.NONE,    StatusEffect.POTENZIAMENTO,   0, 3.0);
                case LUCE:    return mk("Accecamento",     0, Element.NONE,    StatusEffect.ACCECAMENTO,     0, 1.0);
                case FUOCO:   return mk("Esplosione",      0, Element.NONE,    StatusEffect.ESPLOSIONE,      3, 1.5);
                case ACQUA:   return mk("Inondazione",     0, Element.NONE,    StatusEffect.INONDAZIONE,     1, 1.0);
                case TERRA:   return mk("Resistenza",      0, Element.NONE,    StatusEffect.RESISTENZA,      0, 1.0);
                case ARIA:    return mk("Stordimento",     0, Element.NONE,    StatusEffect.STORDIMENTO,     5, 1.0);
                case FULMINE: return mk("Folgore",         0, Element.NONE,    StatusEffect.FOLGORE,         0, 1.0);
                default: return Reaction.NONE;
            }
        }

        // ── Combinazioni ──────────────────────────────────────────
        // FISICO con...
        if (ea == Element.FISICO && eb == Element.LUCE)    return mk("Esposizione",      0, Element.NONE,    StatusEffect.ESPOSIZIONE,     0, 1.0);
        if (ea == Element.FISICO && eb == Element.FUOCO)   return mk("Infiammazione",    0, Element.FUOCO,   StatusEffect.INFIAMMAZIONE,   0, 1.0);
        if (ea == Element.FISICO && eb == Element.ACQUA)   return mk("Erosione",         0, Element.NONE,    StatusEffect.EROSIONE,        -1, 1.0);
        if (ea == Element.FISICO && eb == Element.TERRA)   return mk("Rottura",          0, Element.NONE,    StatusEffect.ROTTURA,         0, 1.0 + (0.10 * entityLevel));
        if (ea == Element.FISICO && eb == Element.ARIA)    return mk("Turbinio",         0, Element.NONE,    StatusEffect.TURBINIO,        0, 1.0); // STUB
        if (ea == Element.FISICO && eb == Element.FULMINE) return mk("Scossa",           0, Element.NONE,    StatusEffect.SCOSSA,          0, 1.0);

        // LUCE con...
        if (ea == Element.LUCE && eb == Element.FUOCO)   return mk("Abrasione",          0, Element.NONE,    StatusEffect.ABRASIONE,       0, 1.0);
        if (ea == Element.LUCE && eb == Element.ACQUA)   return mk("Purificazione",      0, Element.NONE,    StatusEffect.PURIFICAZIONE,   0, 1.0);
        if (ea == Element.LUCE && eb == Element.TERRA)   return mk("Naturalizzazione",   0, Element.NONE,    StatusEffect.NATURALIZZAZIONE,0, 1.0);
        if (ea == Element.LUCE && eb == Element.ARIA)    return mk("Illuminazione",      0, Element.NONE,    StatusEffect.ILLUMINAZIONE,   0, 1.0);
        if (ea == Element.LUCE && eb == Element.FULMINE) return mk("Raggio",             0, Element.FULMINE, StatusEffect.RAGGIO,          0, 1.0);

        // FUOCO con...
        if (ea == Element.FUOCO && eb == Element.ACQUA)   return mk("Vaporizzazione",   0, Element.NONE,    StatusEffect.VAPORIZZAZIONE,  0, 1.0);
        if (ea == Element.FUOCO && eb == Element.TERRA)   return mk("Carbonizzazione",  0, Element.FUOCO,   StatusEffect.CARBONIZZAZIONE, -1, 1.0);
        if (ea == Element.FUOCO && eb == Element.ARIA)    return mk("Firenado",     rollD6(2), Element.FUOCO,   StatusEffect.FIRENADO,        0, 1.0); // STUB parziale
        if (ea == Element.FUOCO && eb == Element.FULMINE) return mk("Sovraccarico",     0, Element.NONE,    StatusEffect.SOVRACCARICO,    1, 1.0);

        // ACQUA con...
        if (ea == Element.ACQUA && eb == Element.TERRA)   return mk("Infangato",        0, Element.NONE,    StatusEffect.INFANGATO,       0, 1.0);
        if (ea == Element.ACQUA && eb == Element.ARIA)    return mk("Tempesta",        rollD6(1), Element.FULMINE, StatusEffect.TEMPESTA,        3, 1.0);
        if (ea == Element.ACQUA && eb == Element.FULMINE) return mk("Elettrizzazione", rollD6(1), Element.FULMINE, StatusEffect.ELETTRIZZAZIONE, 0, 1.0);

        // TERRA con...
        if (ea == Element.TERRA && eb == Element.ARIA)    return mk("Polverizzazione",  0, Element.NONE,    StatusEffect.POLVERIZZAZIONE, 0, 1.0);
        if (ea == Element.TERRA && eb == Element.FULMINE) return mk("Deviazione",       0, Element.NONE,    StatusEffect.DEVIAZIONE,      0, 1.0);

        // ARIA con...
        if (ea == Element.ARIA && eb == Element.FULMINE)  return mk("Ramificazione",    0, Element.FULMINE, StatusEffect.RAMIFICAZIONE,   0, 1.0); // STUB

        return Reaction.NONE;
    }

    // ═════════════════════════════════════════════
    //  APPLICA REAZIONE
    // ═════════════════════════════════════════════

    /**
     * Applica gli effetti di una reazione al target.
     * Ritorna il danno immediato bonus della reazione.
     * Aggiorna lastElementHit del target.
     */
    public static int applyReaction(Reaction reaction, Entity target, Element incoming) {
        if (reaction == Reaction.NONE) {
            target.lastElementHit = incoming;
            return 0;
        }

        // Purificazione: rimuove effetti positivi prima di applicare il nuovo
        if (reaction.effect == StatusEffect.PURIFICAZIONE) {
            removePositiveEffects(target);
        }

        // Applica effetto di stato (solo se non ha Resistenza)
        if (!hasEffect(target, StatusEffect.RESISTENZA)) {
            addEffect(target, reaction.effect, reaction.effectDuration);
        }

        // Elettrizzazione: riapplica Acqua come elemento
        if (reaction.effect == StatusEffect.ELETTRIZZAZIONE) {
            target.lastElementHit = Element.ACQUA;
        } else {
            target.lastElementHit = incoming;
        }

        return reaction.bonusDamage;
    }

    // ═════════════════════════════════════════════
    //  TICK DI TURNO
    // ═════════════════════════════════════════════

    /**
     * Processa gli effetti persistenti del target a fine turno.
     * Ritorna il danno da effetti persistenti subito questo turno.
     * Chiamare in CombatState a fine turno di ogni entità.
     */
    public static int processTurnEffects(Entity target) {
        int damage = 0;
        for (int i = target.activeEffects.size() - 1; i >= 0; i--) {
            ActiveEffect ae = target.activeEffects.get(i);
            switch (ae.effect) {
                case CARBONIZZAZIONE:
                    damage += rollD6(1);
                    break;
                case TEMPESTA:
                    damage += rollD6(1);
                    break;
                case EROSIONE:
                    if (!target.activeEffects.isEmpty()) {
                        int removeIdx = rng.nextInt(target.activeEffects.size());
                        target.activeEffects.remove(removeIdx);
                        i = Math.min(i, target.activeEffects.size() - 1);
                        continue; // già rimosso, non scalare durata
                    }
                    break;
                default:
                    break;
            }
            // Scala durata
            if (ae.duration > 0) {
                ae.duration--;
                if (ae.duration == 0) {
                    target.activeEffects.remove(i);
                }
            } else if (ae.duration == 0) {
                target.activeEffects.remove(i);
            }
            // duration == -1 → permanente, non rimuovere
        }
        return damage;
    }

    // ═════════════════════════════════════════════
    //  QUERY EFFETTI
    // ═════════════════════════════════════════════

    public static boolean hasEffect(Entity e, StatusEffect effect) {
        for (ActiveEffect ae : e.activeEffects) {
            if (ae.effect == effect) return true;
        }
        return false;
    }

    public static void addEffect(Entity e, StatusEffect effect, int duration) {
        if (effect == StatusEffect.NONE) return;
        // Esposizione: +1 durata a nuovi effetti negativi
        if (hasEffect(e, StatusEffect.ESPOSIZIONE) && isNegativeEffect(effect)) {
            duration = duration < 0 ? duration : duration + 1;
        }
        e.activeEffects.add(new ActiveEffect(effect, duration));
    }

    public static void removePositiveEffects(Entity e) {
        e.activeEffects.removeIf(ae -> isPositiveEffect(ae.effect));
    }

    public static void removeAllEffects(Entity e) {
        e.activeEffects.clear();
    }

    public static boolean isPositiveEffect(StatusEffect e) {
        switch (e) {
            case RESISTENZA:
            case DEVIAZIONE:
            case ABRASIONE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isNegativeEffect(StatusEffect e) {
        return !isPositiveEffect(e) && e != StatusEffect.NONE;
    }

    public static int rollD6(int n) {
        int t = 0; for (int i = 0; i < n; i++) t += rng.nextInt(6) + 1; return t;
    }

    public static int rollD8(int n) {
        int t = 0; for (int i = 0; i < n; i++) t += rng.nextInt(8) + 1; return t;
    }

    /** Factory shortcut per creare Reaction inline */
    private static Reaction mk(String name, int bonus, Element bonusEl,
                                StatusEffect effect, int duration, double mult) {
        return new Reaction(name, bonus, bonusEl, effect, duration, mult);
    }
}
