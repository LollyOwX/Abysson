package combat;

/**
 * Dispatcher statico globale per gli effetti speciali — stesso pattern di ComponentRegistry/
 * WeaponRegistry, ma UNICO punto per tutti gli id di SpecialEffect, sia quelli delle abilità
 * (Ability.getSpecialAction(), invocati da CombatState.dealDamage()) sia quelli
 * dell'equipaggiamento (Item.synergyBonusStub/fullSetOrStyleStub/mythicUniqueStub, risolti da
 * Item.resolveSynergyEffect()/resolveFullSetOrStyleEffect()/resolveMythicUniqueEffect()).
 *
 * STUB: nessun id registrato ancora — get() ritorna sempre null. Questo include "STUB", il
 * placeholder letterale che oggi popola gli stub di Item: passandolo qui non serve nessun caso
 * speciale, cade nel default come qualunque id sconosciuto = nessun effetto, comportamento
 * corretto per un placeholder.
 *
 * ── Come aggiungere un effetto ──────────────────────────────────
 *   1. Aggiungi un case con un id univoco (lo stesso testo che metterai in fullSetOrStyleStub/
 *      mythicUniqueStub/synergyBonusStub, o un nuovo id per un'abilità)
 *   2. new SpecialEffect() { ... } con timing() ed execute() — o una classe dedicata se
 *      l'effetto è complesso
 */
public class SpecialEffectRegistry {
    public static SpecialEffect get(String id) {
        switch (id == null ? "" : id) {
            // Migrati da ComponentRegistry (davano un bonus % diretto): ora sono SpecialEffect
            // POST_TURN — scelta placeholder (potevano essere PRE_TURN altrettanto
            // ragionevolmente, un rune/gemma non ha una fase "naturale" finché non sai cosa fa
            // davvero). execute() resta vuoto: questi 4 servono solo a dimostrare che una
            // gemma/incantesimo/roccia arriva davvero fino al ciclo di turno.
            case "ruby_shard": // gemma
                return turnStub(SpecialEffect.Timing.POST_TURN);
            case "haste_rune": // incantesimo
                return turnStub(SpecialEffect.Timing.POST_TURN);
            case "iron_vein_stone": // roccia
                return turnStub(SpecialEffect.Timing.POST_TURN);
            case "swift_stone": // roccia
                return turnStub(SpecialEffect.Timing.POST_TURN);

            default:
                return null; // id sconosciuto, null, o non ancora implementato: nessun errore
        }
    }

    private static SpecialEffect turnStub(SpecialEffect.Timing timing) {
        return new SpecialEffect() {
            public Timing timing() { return timing; }
            public void execute(CombatState combat, entity.Entity source, entity.Entity target) {
                // STUB: nessuna logica reale ancora, solo la dimostrazione che l'aggancio funziona.
            }
        };
    }
}
