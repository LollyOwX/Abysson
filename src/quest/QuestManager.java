package quest;

import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Tenuto da GamePanel (gp.questManager). Le quest ATTIVE e COMPLETATE sono liste separate.
 * Nel libro, l'area Quest (GamePanel.bookindex_quests) ha 5 bookzone (GamePanel.BOOKZONE_COUNT)
 * — una per quest CONOSCIUTA (attiva o completata, in quest ordine: vedi getKnownQuests()), non
 * una per stato: bookzone 0 = la prima quest conosciuta, bookzone 1 = la seconda, ecc. Uno slot
 * senza quest assegnata resta vuoto (vedi UI.drawQuestPageContent()). Il cap di 5 va alzato
 * (insieme al numero di Rectangle in UI.SUBTOPIC_IMAGE_RECTS per quell'area) se in
 * QuestRegistry finiscono per esistere più di 5 quest raggiungibili in una run.
 */
public class QuestManager {
    private final GamePanel gp;
    private final List<Quest> active    = new ArrayList<>();
    private final List<Quest> completed = new ArrayList<>();

    public QuestManager(GamePanel gp) {
        this.gp = gp;
    }

    public List<Quest> getActive()    { return active; }
    public List<Quest> getCompleted() { return completed; }

    /** Tutte le quest conosciute (attive + completate), in un ordine stabile — usata per
     *  indicizzare le bookzone del libro (vedi il commento di classe sopra). */
    public List<Quest> getKnownQuests() {
        List<Quest> known = new ArrayList<>(active);
        known.addAll(completed);
        return known;
    }

    /** Cerca una quest conosciuta per id — null se non ancora iniziata (o id inesistente).
     *  Utile per far dipendere qualcos'altro dallo stato di UNA quest specifica (es. un dialogo
     *  NPC diverso a seconda che l'abbia già data/il giocatore l'abbia già completata), senza
     *  scorrere a mano getActive()/getCompleted() ogni volta. */
    public Quest getQuestById(String id) {
        for (Quest q : active)    if (q.id.equals(id)) return q;
        for (Quest q : completed) if (q.id.equals(id)) return q;
        return null;
    }

    /**
     * Avvia una quest per id — idempotente: non fa nulla se è già attiva o completata, quindi
     * è sicuro richiamarla più volte (es. da un dialogo NPC ripetuto o da un trigger di mappa
     * che il giocatore attraversa di nuovo). Informa il giocatore con un messaggio a schermo.
     */
    public void startQuest(String id) {
        if (getQuestById(id) != null) return;
        Quest quest = QuestRegistry.get(id);
        if (quest == null) return;
        quest.state = QuestState.ACTIVE;
        active.add(quest);
        gp.ui.showMessage("Nuova missione: " + quest.title);
    }

    /**
     * Notifica un evento di gioco a TUTTE le quest attive, non solo una — più quest possono
     * avere uno step che ascolta lo stesso evento (es. due quest diverse che chiedono entrambe
     * di sconfiggere un Goblin). Chi avanza di step o completa l'ultimo lo comunica al
     * giocatore con un messaggio a schermo; chi completa l'ultimo passa da active a completed.
     */
    public void notify(QuestEventType type, String targetId) {
        for (int i = active.size() - 1; i >= 0; i--) {
            Quest quest = active.get(i);
            int stepBefore = quest.currentStepIndex;
            boolean changed = quest.notify(type, targetId);
            if (!changed) continue;

            if (quest.state == QuestState.COMPLETED) {
                active.remove(i);
                completed.add(quest);
                gp.ui.showMessage("Missione completata: " + quest.title);
            } else if (quest.currentStepIndex != stepBefore) {
                QuestStep next = quest.currentStep();
                if (next != null) gp.ui.showMessage("Nuovo obiettivo: " + next.description);
            }
        }
    }
}