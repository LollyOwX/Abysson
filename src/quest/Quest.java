package quest;

import java.util.List;

/**
 * Una quest: sequenza di QuestStep, UNO attivo alla volta (step machine). Il giocatore vede
 * solo lo step corrente (currentStepIndex) — gli step futuri non sono nemmeno controllati
 * finché non tocca a loro, quindi un evento che combacerebbe con uno step 3 non fa nulla se
 * la quest è ancora allo step 1.
 */
public class Quest {
    public final String id;
    public final String title;
    public final String description;
    public final QuestTier tier;
    public final String giverNpc; // nome dell'NPC che la assegna, null se solo da trigger di mappa
    public final List<QuestStep> steps;

    public QuestState state = QuestState.NOT_STARTED;
    public int currentStepIndex = 0;

    public Quest(String id, String title, String description, QuestTier tier, String giverNpc, List<QuestStep> steps) {
        this.id          = id;
        this.title       = title;
        this.description = description;
        this.tier        = tier;
        this.giverNpc    = giverNpc;
        this.steps       = steps;
    }

    public QuestStep currentStep() {
        return (currentStepIndex >= 0 && currentStepIndex < steps.size()) ? steps.get(currentStepIndex) : null;
    }

    /**
     * Notifica un evento di gioco a questa quest. Fa qualcosa SOLO se combacia con lo step
     * attivo (non con step già superati o futuri). Se lo step si completa, passa al successivo
     * o, se era l'ultimo, completa la quest. Ritorna true se qualcosa è cambiato — utile al
     * chiamante, es. per un futuro messaggio "Obiettivo completato!".
     */
    public boolean notify(QuestEventType type, String targetId) {
        QuestStep step = currentStep();
        if (state != QuestState.ACTIVE || step == null) return false;
        if (!step.matches(type, targetId)) return false;

        step.currentCount++;
        if (!step.isComplete()) return true;

        currentStepIndex++;
        if (currentStepIndex >= steps.size()) {
            state = QuestState.COMPLETED;
        }
        return true;
    }
}
