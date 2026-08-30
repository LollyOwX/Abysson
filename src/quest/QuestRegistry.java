package quest;

import java.util.List;

/**
 * Dispatcher statico per le definizioni delle quest — stesso stile di combat/Ability.java
 * (facile da estendere: una quest nuova = un nuovo case, non tocchi QuestManager). Ogni
 * chiamata a get() crea un'istanza NUOVA: Quest ha stato mutabile (currentStepIndex, state),
 * quindi non si può condividere la stessa istanza tra partite o riusi diversi.
 *
 * ── Come aggiungere una nuova quest ──────────────────────────────
 *   1. Aggiungi un case con un id univoco (stringa, es. "goblin_bounty")
 *   2. Scrivi i suoi QuestStep in ordine — ognuno con un QuestEventType e il targetId giusto
 *      (il nome esatto di mostro/NPC/locationId che deve combaciare — vedi QuestEventType)
 *   3. Se lo step usa REACH_LOCATION, aggiungi il trigger di mappa in EventHandler
 *      (vedi EventHandler.locationEvent()); se usa KILL/TALK non serve altro, sono già
 *      agganciati (CombatState.checkVictory() / Entity.speak())
 *   4. Per farla assegnare da un NPC via dialogo: metti il suo id in
 *      quell'NPC.givesQuestId; per farla partire da un trigger di mappa: aggiungi una
 *      chiamata a EventHandler.questStartEvent() in checkEvent()
 */
public class QuestRegistry {
    public static Quest get(String id) {
        switch (id) {
            case "goblin_bounty":
                return new Quest(
                        "goblin_bounty",
                        "Taglia sul Goblin",
                        "Un goblin infesta i dintorni del villaggio.",
                        QuestTier.FLAVOR,
                        "Villager", // placeholder: da allineare a un NPC vero quando ce n'è uno con questo nome
                        List.of(
                                new QuestStep("Sconfiggi il Goblin", QuestEventType.KILL, "Goblin")
                        )
                );
            default:
                System.err.println("Quest non trovata: " + id);
                return null;
        }
    }
}
