package quest;

/**
 * Tipi di evento che possono far avanzare uno QuestStep. Un nuovo tipo di obiettivo = un
 * nuovo valore qui + il punto del codice che lo notifica — vedi "Aggiungere una nuova quest"
 * in STATUS.md.
 */
public enum QuestEventType {
    KILL,           // notificato da CombatState.checkVictory()
    TALK,           // notificato da Entity.speak()
    REACH_LOCATION, // notificato da EventHandler — vedi locationEvent()
    COLLECT,        // notificato da Player.obtain(): targetId = id del registry dell'oggetto (es. short_sword_atk_0_10_common)
    CUSTOM          // per obiettivi che non rientrano negli altri tipi — notificare a mano dal punto giusto
}
