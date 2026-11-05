package quest;

public class QuestCondition {
    public final QuestEventType type;
    public final String targetId;
    public final int goalCount;
    public int currentCount = 0;

    public QuestCondition(QuestEventType type, String targetId, int goalCount) {
        this.type      = type;
        this.targetId  = targetId;
        this.goalCount = goalCount;
    }

    public boolean isMet() {
        return currentCount >= goalCount;
    }

    // true se l'evento combacia e la condizione non era ancora completa (quindi ha contato)
    public boolean notify(QuestEventType type, String targetId) {
        if (isMet() || this.type != type || !this.targetId.equals(targetId)) return false;
        currentCount++;
        return true;
    }

    public void reset() {
        currentCount = 0;
    }
}
