package items;

import entity.StatType;

/**
 * Dispatcher statico per gli effetti dei componenti — stesso pattern di WeaponRegistry. Un
 * componente contribuisce SOLO un bonus % su UNA StatType per ora: niente ancora di più
 * articolato tipo Lifesteal (l'esempio che avevi fatto tu) — quello richiederebbe un hook nel
 * danno/cura che oggi non esiste per un "effetto" invece che un bonus stat puro. Struttura
 * pronta per estenderlo quando servirà (basta cambiare cosa ritorna Effect).
 *
 * ── Come aggiungere un nuovo componente ──────────────────────────
 *   1. Aggiungi un case con un id univoco
 *   2. new Effect(StatType.X, percentuale)
 *   3. Riferiscilo da un Component(id) in un gemme[]/incantesimi[]/rocce[] di un item
 */
public class ComponentRegistry {
    public static class Effect {
        public final StatType stat;
        public final int percent;
        public Effect(StatType stat, int percent) { this.stat = stat; this.percent = percent; }
    }

    public static Effect get(String id) {
        switch (id) {
            case "ruby_shard":     return new Effect(StatType.FIRE_ATK, 5);   // gemma
            case "haste_rune":     return new Effect(StatType.VELOCITA, 5);   // incantesimo
            case "iron_vein_stone":return new Effect(StatType.DIFESA, 2);     // roccia
            case "swift_stone":    return new Effect(StatType.VELOCITA, 2);   // roccia
            default:
                return null; // id sconosciuto: nessun bonus, non un errore bloccante
        }
    }
}
