package quest;

/**
 * Un singolo step di una quest (vedi Quest — step machine, uno step attivo alla volta).
 * matches()/currentCount/isComplete() sono l'UNICA logica di completamento: un nuovo tipo di
 * obiettivo si aggiunge estendendo QuestEventType e chi lo notifica, non aggiungendo campi qui.
 */
public class QuestStep {
    public final String description;      // testo mostrato al giocatore per questo step
    public final QuestEventType triggerType;
    public final String targetId;         // nome mostro/oggetto/NPC/locationId, a seconda di triggerType
    public final int goalCount;           // quante volte serve (default 1)
    public int currentCount = 0;

    public QuestStep(String description, QuestEventType triggerType, String targetId, int goalCount) {
        this.description  = description;
        this.triggerType  = triggerType;
        this.targetId     = targetId;
        this.goalCount    = goalCount;
    }

    public QuestStep(String description, QuestEventType triggerType, String targetId) {
        this(description, triggerType, targetId, 1);
    }

    public boolean matches(QuestEventType type, String targetId) {
        return this.triggerType == type && this.targetId.equals(targetId);
    }

    public boolean isComplete() {
        return currentCount >= goalCount;
    }
}
