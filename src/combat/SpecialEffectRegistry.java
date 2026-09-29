package combat;

import entity.Entity;
import entity.Player;
import entity.StatType;

/**
 * Dispatcher statico globale per gli effetti speciali — stesso pattern di ComponentRegistry/
 * WeaponRegistry, ma UNICO punto per tutti gli id di SpecialEffect, sia quelli delle abilità
 * (Ability.getSpecialAction(), invocati da CombatState.dealDamage()) sia quelli
 * dell'equipaggiamento (Item.synergyBonusStub/fullSetOrStyleStub/mythicUniqueStub, risolti da
 * Item.resolveSynergyEffect()/resolveFullSetOrStyleEffect()/resolveMythicUniqueEffect()).
 *
 * I case sotto (dopo i 4 esempi gemma/incantesimo) sono le abilità dell'armatura de-stubbate —
 * confronto per PREFISSO (startsWith), non uguaglianza esatta: da questa sessione i testi
 * Masterwork/Mitico hanno un suffisso di livello in coda (es. "(livello Adepto)"), diverso per
 * fascia (vedi generate_equip.py) — la stessa abilità di base scatta a qualunque fascia.
 * "Presa Ferrea" (disarmo) non ha logica qui: è un controllo puntuale di CombatState.
 * isDisarmImmune() fatto direttamente sul testo dell'item, non un SpecialEffect a sé.
 * Vedi STATUS.md per l'elenco completo di cosa è usato/scartato e perché (mancano sistemi come
 * sanguinamento/maledizione/marchio/drenaggio/silenzio/critici/schivata/iniziativa/furtività/
 * spinta/danno da terreno).
 *
 * ── Come aggiungere un effetto ──────────────────────────────────
 *   1. Aggiungi un case con un id univoco (lo stesso testo che metterai in fullSetOrStyleStub/
 *      mythicUniqueStub/synergyBonusStub, o un nuovo id per un'abilità)
 *   2. new SpecialEffect() { ... } con timing() ed execute() — o una classe dedicata se
 *      l'effetto è complesso
 */
public class SpecialEffectRegistry {
    public static SpecialEffect get(String id) {
        if (id == null) return null;
        switch (id) {
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
        }

        // ── Armatura: Full Set / Mitico de-stubbati (confronto per prefisso) ─────
        if (id.startsWith("[Full Set: Guardia] Elmo Saldo:")) {
            return preTurn((combat, source, target) -> {
                if (ElementSystem.hasEffect(source, ElementSystem.StatusEffect.STORDIMENTO)
                        && combat.consumeOnce("elmo_saldo:" + System.identityHashCode(source))) {
                    source.activeEffects.removeIf(ae -> ae.effect == ElementSystem.StatusEffect.STORDIMENTO);
                }
            });
        }
        if (id.startsWith("[Full Set: Veggente] Occhio Limpido:")) {
            // "vedi le debolezze elementali del nemico" NON implementato: servirebbe un
            // sistema di rivelazione UI che non esiste ancora — solo l'immunità è reale.
            return preTurn((combat, source, target) ->
                source.activeEffects.removeIf(ae -> ae.effect == ElementSystem.StatusEffect.ACCECAMENTO));
        }
        if (id.startsWith("[Mitico] Corpo di Roccia:")) {
            // Solo PRE_TURN: se qualcuno te la applica durante il turno avversario, viene
            // comunque ripulita prima che inizi il TUO prossimo turno — non serve anche
            // POST_TURN (un SpecialEffect ha un solo timing(), non può risolvere a entrambi).
            return preTurn((combat, source, target) ->
                source.activeEffects.removeIf(ae -> ae.effect == ElementSystem.StatusEffect.ROTTURA));
        }
        if (id.startsWith("[Full Set: Veggente] Scaglia di Pietra:")) {
            // Approssimazione: nessun sistema di durata per i buff di stat esiste ancora
            // (vedi Player.applyTemporaryMultiplier) — "temporanea" qui vale "per il resto del
            // combattimento", non un numero di round preciso.
            return preTurn((combat, source, target) -> {
                if (ElementSystem.hasEffect(source, ElementSystem.StatusEffect.ROTTURA)
                        && source instanceof Player p) {
                    source.activeEffects.removeIf(ae -> ae.effect == ElementSystem.StatusEffect.ROTTURA);
                    p.applyTemporaryMultiplier(StatType.DIFESA, 1.10);
                }
            });
        }
        if (id.startsWith("[Full Set: Veggente] Parafulmine:")) {
            return preTurn((combat, source, target) -> {
                ElementSystem.ActiveEffect scossa = null;
                for (ElementSystem.ActiveEffect ae : source.activeEffects)
                    if (ae.effect == ElementSystem.StatusEffect.SCOSSA) { scossa = ae; break; }
                if (scossa != null) {
                    source.activeEffects.remove(scossa);
                    ElementSystem.addEffect(target, ElementSystem.StatusEffect.SCOSSA, scossa.duration);
                }
            });
        }
        if (id.startsWith("[Full Set: Veggente] Respiro di Brace:")) {
            // POST_TURN scatta PRIMA di ElementSystem.processTurnEffects() nell'ordine di
            // CombatState.afterTurn() — rimuovere qui Infiammazione previene davvero il tick,
            // non lo compensa dopo. Cura placeholder: 10% della vita massima.
            return postTurn((combat, source, target) -> {
                if (ElementSystem.hasEffect(source, ElementSystem.StatusEffect.INFIAMMAZIONE)
                        && combat.consumeOnce("respiro_di_brace:" + System.identityHashCode(source))) {
                    source.activeEffects.removeIf(ae -> ae.effect == ElementSystem.StatusEffect.INFIAMMAZIONE);
                    source.life = Math.min(source.maxLife, source.life + source.maxLife / 10);
                }
            });
        }
        if (id.startsWith("[Mitico] Sangue Puro:")) {
            return preTurn((combat, source, target) -> {
                for (ElementSystem.ActiveEffect ae : source.activeEffects)
                    if (ElementSystem.isNegativeEffect(ae.effect) && ae.duration != 0) ae.duration = 1;
            });
        }
        if (id.startsWith("[Full Set: Voto] Purga Lenta:")) {
            return postTurn((combat, source, target) -> {
                ElementSystem.ActiveEffect oldest = null;
                for (ElementSystem.ActiveEffect ae : source.activeEffects)
                    if (ElementSystem.isNegativeEffect(ae.effect)) { oldest = ae; break; }
                if (oldest != null) source.activeEffects.remove(oldest);
            });
        }
        if (id.startsWith("[Full Set: Passo] Suola Salda:")) {
            // "il terreno" non è modellato esplicitamente, ma Naturalizzazione/Infangato SONO
            // blocco movimento (agganciato a tryFlee) — l'interpretazione più vicina
            // disponibile con i sistemi che esistono oggi.
            return preTurn((combat, source, target) ->
                source.activeEffects.removeIf(ae ->
                    ae.effect == ElementSystem.StatusEffect.NATURALIZZAZIONE ||
                    ae.effect == ElementSystem.StatusEffect.INFANGATO));
        }

        return null; // id sconosciuto, null, o non ancora implementato: nessun errore
    }

    private static SpecialEffect turnStub(SpecialEffect.Timing timing) {
        return new SpecialEffect() {
            public Timing timing() { return timing; }
            public void execute(CombatState combat, entity.Entity source, entity.Entity target) {
                // STUB: nessuna logica reale ancora, solo la dimostrazione che l'aggancio funziona.
            }
        };
    }

    @FunctionalInterface
    private interface Logic { void run(CombatState combat, Entity source, Entity target); }

    private static SpecialEffect preTurn(Logic logic) {
        return new SpecialEffect() {
            public Timing timing() { return Timing.PRE_TURN; }
            public void execute(CombatState combat, Entity source, Entity target) { logic.run(combat, source, target); }
        };
    }

    private static SpecialEffect postTurn(Logic logic) {
        return new SpecialEffect() {
            public Timing timing() { return Timing.POST_TURN; }
            public void execute(CombatState combat, Entity source, Entity target) { logic.run(combat, source, target); }
        };
    }
}
