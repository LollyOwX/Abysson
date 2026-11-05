package quest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Quest {
    public final String id; // = nome del file in res/quests/
    public final String title;
    public final String description;
    public final QuestTier tier;
    public final String giverNpc; // nome dell'NPC che la assegna, null se solo da trigger di mappa
    public final Map<String, Step> steps = new LinkedHashMap<>(); // chiave "X.Y", in ordine di file
    public final List<Step> history = new ArrayList<>();           // situazioni attraversate, l'ultima è l'attuale

    public QuestState state = QuestState.NOT_STARTED;
    public int step  = 0; // X
    public int micro = 0; // Y

    public static class Step {
        public final int x;
        public final int y;
        public final String text;
        public final List<QuestBranch> branches = new ArrayList<>();

        public Step(int x, int y, String text) {
            this.x    = x;
            this.y    = y;
            this.text = text;
        }
    }

    public Quest(String id, String title, String description, QuestTier tier, String giverNpc) {
        this.id          = id;
        this.title       = title;
        this.description = description;
        this.tier        = tier;
        this.giverNpc    = giverNpc;
    }

    public static String key(int x, int y) {
        return x + "." + y;
    }

    // ── Stato ────────────────────────────────────────────────────

    public Step currentStep() {
        return steps.get(key(step, micro));
    }

    public String saveKey() {
        return id + "." + step + "." + micro; // STUB: il salvataggio vero non esiste ancora
    }

    // " (2/3)" se la situazione attuale ha un solo obiettivo contato, altrimenti ""
    public String progressText() {
        Step s = currentStep();
        if (state != QuestState.ACTIVE || s == null || s.branches.size() != 1) return "";
        QuestBranch b = s.branches.get(0);
        QuestCondition c = b.armed ? b.continuation : (b.requirements.size() == 1 ? b.requirements.get(0) : null);
        return (c != null && c.goalCount > 1) ? " (" + c.currentCount + "/" + c.goalCount + ")" : "";
    }

    public void start() {
        if (steps.isEmpty()) return;
        state = QuestState.ACTIVE;
        history.clear();
        Step first = steps.values().iterator().next();
        enter(first.x, first.y);
    }

    private void enter(int x, int y) {
        step  = x;
        micro = y;
        Step s = steps.get(key(x, y));
        if (s != null) history.add(s);
        if (s == null || s.branches.isEmpty()) {
            state = QuestState.COMPLETED; // situazione senza branch = finale
            return;
        }
        for (QuestBranch b : s.branches) b.reset();
    }

    // ── Eventi ───────────────────────────────────────────────────

    public boolean notify(QuestEventType type, String targetId) {
        Step s = currentStep();
        if (state != QuestState.ACTIVE || s == null) return false;

        boolean changed = false;
        QuestBranch winnerImportant = null;
        QuestBranch winnerOther = null;

        for (QuestBranch b : s.branches) {
            boolean fires = false;
            if (b.type == QuestBranchType.IMPORTANT) {
                for (QuestCondition c : b.requirements) {
                    if (c.notify(type, targetId)) changed = true;
                }
                fires = b.requirementMet();
            } else if (b.armed) {
                if (b.continuation.notify(type, targetId)) {
                    changed = true;
                    fires = b.continuation.isMet();
                }
            } else {
                for (QuestCondition c : b.requirements) {
                    if (c.notify(type, targetId)) changed = true;
                }
                if (b.requirementMet()) b.armed = true; // Y resta invariata: serve la continuazione
            }
            if (!fires) continue;
            if (b.type == QuestBranchType.IMPORTANT) { if (winnerImportant == null) winnerImportant = b; }
            else if (winnerOther == null) winnerOther = b;
        }

        QuestBranch winner = (winnerImportant != null) ? winnerImportant : winnerOther;
        if (winner != null) {
            enter(winner.targetStep, winner.targetMicro);
            changed = true;
        }
        return changed;
    }
}
