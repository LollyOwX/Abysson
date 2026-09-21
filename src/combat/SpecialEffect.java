package combat;

import entity.Entity;

/**
 * Effetto speciale — sostituisce la vecchia SpecialAction, stessa firma di execute() ma con in
 * più il timing, così lo stesso registry serve sia le abilità (Ability.getSpecialAction()) sia
 * i bonus di equipaggiamento (Item.synergyBonusStub/fullSetOrStyleStub/mythicUniqueStub, e le
 * gemme/incantesimi/rocce via Component).
 *
 *   ATTIVO    — scatta solo quando qualcuno lo invoca esplicitamente (es. CombatState.
 *               dealDamage() dopo il danno di un'abilità). execute() contiene la logica vera.
 *   PRE_TURN  — richiamato da CombatState.beforeTurn(actor) per OGNI entità in combattimento,
 *               prima che agisca (anche se poi salta il turno per Stordimento) — un pezzo
 *               equipaggiato con questo effetto lo attiva sul proprio portatore ad ogni round.
 *   POST_TURN — richiamato da CombatState.afterTurn(actor), dopo che ha agito (o dopo che gli è
 *               stato saltato) — stesso principio, a fine turno invece che a inizio.
 *
 * Per PRE_TURN/POST_TURN, execute(combat, source, target) riceve source = l'entità che possiede
 * l'oggetto equipaggiato, target = il suo avversario nel combattimento (mai source stesso) —
 * un effetto "su di sé" (una cura, un buff) ignora semplicemente target e agisce solo su
 * source; un effetto offensivo (es. "Eco": attacca due volte, il secondo colpo scatta come
 * POST_TURN) ha già il bersaglio giusto pronto, senza doverlo recuperare da solo.
 *
 * STUB: execute() è vuoto per i 4 esempi già registrati in SpecialEffectRegistry — nessuna
 * logica di gioco reale ancora, solo il punto di aggancio.
 */
public interface SpecialEffect {

    enum Timing { PRE_TURN, POST_TURN, ACTIVE }

    Timing timing();

    void execute(CombatState combat, Entity source, Entity target);
}
