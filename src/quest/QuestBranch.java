package quest;

import java.util.ArrayList;
import java.util.List;

public class QuestBranch {
    public final String id;
    public final QuestBranchType type;
    public final boolean requireAll; // AND (true) / OR (false) tra i requisiti
    public final int targetStep;     // X di arrivo quando il branch prende priorità
    public final int targetMicro;    // Y di arrivo

    public final List<QuestCondition> requirements = new ArrayList<>();
    public QuestCondition continuation; // FAST e COINCIDENT: serve dopo il requisito per prendere priorità
    public boolean armed = false;       // requisito soddisfatto, in attesa della continuazione

    public QuestBranch(String id, QuestBranchType type, boolean requireAll, int targetStep, int targetMicro) {
        this.id          = id;
        this.type        = type;
        this.requireAll  = requireAll;
        this.targetStep  = targetStep;
        this.targetMicro = targetMicro;
    }

    public boolean requirementMet() {
        if (requirements.isEmpty()) return false;
        for (QuestCondition c : requirements) {
            if (requireAll && !c.isMet()) return false;
            if (!requireAll && c.isMet()) return true;
        }
        return requireAll;
    }

    public void reset() {
        for (QuestCondition c : requirements) c.reset();
        if (continuation != null) continuation.reset();
        armed = false;
    }
}
