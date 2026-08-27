package combat;

import entity.Entity;

/**
 * Azione speciale associata a un'abilità (Ability.getSpecialAction()), eseguita da
 * CombatState.dealDamage() dopo il calcolo del danno — se non null.
 *
 * Non produce testo generico tipo "succede X!": qualunque messaggio va accodato
 * dentro execute() con combat.queueAction(...), così resta deciso da chi implementa
 * l'azione (dipende da cosa fa davvero), non generato genericamente qui.
 *
 * STUB: nessuna abilità la usa ancora — Ability.getSpecialAction() ritorna sempre null.
 * Esempi futuri: cambio dell'ordine dei turni, un evento nel mondo di gioco, ecc.
 */
public interface SpecialAction {
    void execute(CombatState combat, Entity user, Entity target);
}
