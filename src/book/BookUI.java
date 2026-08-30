package book;

import main.Button;
import main.GamePanel;
import main.UI;
import quest.Quest;
import quest.QuestState;
import quest.QuestStep;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Tutta la UI del libro (schermata gp.bookState): sfondo, bookmark, sottoargomenti (bookzone),
 * contenuto delle pagine, click del mouse. Estratta da UI.java per tenerla isolata dal resto
 * dell'interfaccia — stesso pattern già usato per il combattimento (UI.combat, un CombatState):
 * tenuta da UI (public UI.book) invece che da GamePanel direttamente, perché UI.draw() è quella
 * che decide quale schermata disegnare in base a gp.gameState.
 *
 * Usa `ui` solo per gli helper generici già esistenti e condivisi con altre schermate
 * (drawStyledText, il font MaruMonica) — quelli restano in UI, non duplicati qui.
 */
public class BookUI {
    private final GamePanel gp;
    private final UI ui;
    private Graphics2D g2;

    // Rettangoli dei 6 bookmark nello spazio immagine originale del libro (272x272, prima dello
    // scaling a schermo). Misurati confrontando i pixel non trasparenti di book.png con ognuna
    // delle 6 varianti book_X.png: sono la zona (linguetta) che cambia in ciascuna variante.
    // Indice = gp.bookindex - 1 (stesso ordine di GamePanel.ZONE_IMAGES: map, quests, skills,
    // calendar, bestiary, inventory — cioè bookindex_map=1 ... bookindex_inventory=6).
    private static final Rectangle[] BOOKMARK_IMAGE_RECTS = {
            new Rectangle(66,  71, 14, 13), // 0 Mappa      (bookindex 1)
            new Rectangle(159, 71, 14, 13), // 1 Quest      (bookindex 2)
            new Rectangle(219, 72, 14, 13), // 2 Abilità    (bookindex 3)
            new Rectangle(98,  71, 14, 13), // 3 Calendario (bookindex 4)
            new Rectangle(38,  72, 14, 13), // 4 Bestiario  (bookindex 5)
            new Rectangle(187, 70, 14, 13), // 5 Inventario (bookindex 6)
    };
    private final Button[] bookmarkButtons = new Button[BOOKMARK_IMAGE_RECTS.length];

    // Rettangoli dei pulsanti sottoargomento (bookzone), per area (indice esterno = bookindex-1,
    // stesso ordine di BOOKMARK_IMAGE_RECTS sopra). Posizioni PLACEHOLDER, da aggiustare a vista
    // come per i bookmark. Quanti Rectangle per area deve combaciare con GamePanel.BOOKZONE_COUNT
    // (mappa=3, quest=5, abilità=5, calendario=2, bestiario=3, inventario=5) — impilati
    // verticalmente accumulando y (altezza 20 + margine 5), a partire da y=95, sotto alla fascia
    // dei bookmark (y=70-85 circa).
    private static final Rectangle[][] SUBTOPIC_IMAGE_RECTS = {
            { new Rectangle(68, 86, 10, 18), new Rectangle(68, 106, 10, 18), new Rectangle(68, 126, 10, 18) }, // 0 Mappa (3)
            { new Rectangle(161, 86, 10, 10), new Rectangle(161, 98, 10, 10), new Rectangle(161,110,10,10), new Rectangle(161,122,10,10), new Rectangle(161,134,10,10) }, // 1 Quest (5)
            { new Rectangle(221, 87, 10, 10), new Rectangle(221, 99, 10, 10), new Rectangle(221, 111, 10, 10), new Rectangle(221, 123, 10, 10), new Rectangle(221, 135, 10, 10) }, // 2 Abilità (5)
            { new Rectangle(100, 86, 10, 28), new Rectangle(100, 116, 10, 28) }, // 3 Calendario (2)
            { new Rectangle(40, 87, 10, 18), new Rectangle(40, 107, 10, 18), new Rectangle(40, 127, 10, 18) }, // 4 Bestiario (3)
            { new Rectangle(189, 85, 10, 10), new Rectangle(189, 97, 10, 10), new Rectangle(189, 109, 10, 10), new Rectangle(189, 121, 10, 10), new Rectangle(189, 133, 10, 10) }, // 5 Inventario (5)
    };
    private final Button[][] subtopicButtons = new Button[SUBTOPIC_IMAGE_RECTS.length][];

    // Area di testo per il contenuto dinamico delle pagine (per ora solo l'area Quest) —
    // spazio immagine originale del libro (272x272, stesso sistema dei rettangoli sopra).
    // PLACEHOLDER, da aggiustare a vista: nessun'altra area disegna ancora testo, quindi non
    // c'era un riferimento da cui partire.
    private static final Rectangle PAGE_CONTENT_RECT = new Rectangle(95, 20, 165, 230);

    public BookUI(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;

        // Un Button per bookmark: array 0-based -> bookindex 1-based (i+1), il click chiama
        // sempre selectBookIndex() passandogli il bookindex giusto.
        for (int i = 0; i < bookmarkButtons.length; i++) {
            final int bookindex = i + 1;
            bookmarkButtons[i] = new Button(() -> gp.selectBookIndex(bookindex));
        }

        // Un Button per sottoargomento, per ogni area: il click chiama selectBookZone() con
        // l'indice giusto (0-based). Restano inattivi (bounds null) finché la loro area non è
        // quella aperta — vedi draw().
        for (int area = 0; area < subtopicButtons.length; area++) {
            subtopicButtons[area] = new Button[SUBTOPIC_IMAGE_RECTS[area].length];
            for (int z = 0; z < subtopicButtons[area].length; z++) {
                final int bookzone = z;
                subtopicButtons[area][z] = new Button(() -> gp.selectBookZone(bookzone));
            }
        }
    }

    // Disegna il libro sopra al mondo (già disegnato da GamePanel prima di chiamare UI.draw()),
    // esattamente come UI.drawPauseScreen() disegna "PAUSED" sopra al mondo.
    // Sfondo: book.png se nessuna area generale è selezionata, altrimenti book_X.png dell'area
    // attiva (bookmark evidenziato già dentro l'immagine — vedi GamePanel.getCurrentBookImage()).
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        BufferedImage bg = gp.getCurrentBookImage();
        if (bg == null) return;

        double scale = Math.min((double) gp.screenWidth / bg.getWidth(), (double) gp.screenHeight / bg.getHeight());
        int w = (int) (bg.getWidth() * scale);
        int h = (int) (bg.getHeight() * scale);
        int ox = (gp.screenWidth - w) / 2;
        int oy = (gp.screenHeight - h) / 2;
        g2.drawImage(bg, ox, oy, w, h, null);

        if (gp.bookindex == gp.bookindex_quests) drawQuestPageContent(ox, oy, scale);

        // Bookmark: sempre cliccabili (anche per cambiare area generale mentre se ne sta già
        // guardando una). I bounds si ricalcolano ogni frame perché seguono lo scaling del libro.
        // Niente overlay separato sopra: la linguetta evidenziata è già dentro book_X.png
        // (background sopra) — disegnarla due volte la stirava e sdoppiava a schermo.
        for (int i = 0; i < BOOKMARK_IMAGE_RECTS.length; i++) {
            Rectangle r = BOOKMARK_IMAGE_RECTS[i];
            bookmarkButtons[i].setBounds(
                    ox + (int) (r.x * scale), oy + (int) (r.y * scale),
                    (int) (r.width * scale), (int) (r.height * scale));
        }

        // Sottoargomenti (bookzone): attivi SOLO per l'area effettivamente aperta (gp.bookindex - 1 == area) — per tutte le altre azzero i bounds, così non restano cliccabili con posizioni "vecchie" da quando erano loro l'area attiva (altrimenti si sovrapporrebbero al contenuto sbagliato).
        for (int area = 0; area < subtopicButtons.length; area++) {
            boolean isActiveArea = (gp.bookindex - 1 == area) && !gp.pageTurnActive;
            for (int z = 0; z < subtopicButtons[area].length; z++) {
                if (isActiveArea) {
                        Rectangle r = SUBTOPIC_IMAGE_RECTS[area][z];
                    subtopicButtons[area][z].setBounds(
                            ox + (int) (r.x * scale), oy + (int) (r.y * scale),
                            (int) (r.width * scale), (int) (r.height * scale));
                } else {
                    subtopicButtons[area][z].bounds = null;
                }
            }
        }

        if (gp.pageTurnActive) {
            BufferedImage frame = gp.pageTurnPlayer.getCurrentFrame();
            if (frame != null) {
                double frameScale = Math.min((double) gp.screenWidth / frame.getWidth(),
                        (double) gp.screenHeight / frame.getHeight());
                int fw = (int) (frame.getWidth() * frameScale);
                int fh = (int) (frame.getHeight() * frameScale);
                int fx = (gp.screenWidth - fw) / 2;
                int fy = (gp.screenHeight - fh) / 2;
                g2.drawImage(frame, fx, fy, fw, fh, null);
            }
        }
    }

    // ═════════════════════════════════════════════
    //  Contenuto pagine Quest (bookzone = una quest CONOSCIUTA — vedi QuestManager,
    //  bookpage 0 = riepilogo, bookpage 1 = lista obiettivi)
    // ═════════════════════════════════════════════

    private void drawQuestPageContent(int ox, int oy, double scale) {
        if (gp.bookzone < 0) return; // nessuna voce selezionata: solo lo sfondo dell'area

        List<Quest> known = gp.questManager.getKnownQuests();
        if (gp.bookzone >= known.size()) return; // slot senza quest assegnata: pagina vuota
        Quest q = known.get(gp.bookzone);

        Rectangle r = PAGE_CONTENT_RECT;
        int x = ox + (int) (r.x * scale);
        int y = oy + (int) (r.y * scale);
        int w = (int) (r.width * scale);

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 18f));
        g2.setColor(Color.black); // testo scuro sopra la pagina chiara del libro
        g2.drawString(q.title, x, y);

        if (gp.bookpage == 0) {
            ui.drawStyledText(q.description, x, y + 28, w);
            String stateLabel = (q.state == QuestState.COMPLETED) ? "Completata" : "In corso";
            g2.setColor(Color.black);
            g2.drawString("Stato: " + stateLabel, x, y + 28 + 80);
        } else {
            int textY = y + 28;
            for (int i = 0; i < q.steps.size(); i++) {
                QuestStep step = q.steps.get(i);
                boolean done = i < q.currentStepIndex || q.state == QuestState.COMPLETED;
                boolean current = i == q.currentStepIndex && q.state == QuestState.ACTIVE;
                String marker = done ? "[x] " : (current ? "[ ] " : "    ");
                String progress = (current && step.goalCount > 1)
                        ? " (" + step.currentCount + "/" + step.goalCount + ")" : "";
                g2.setColor(Color.black);
                ui.drawStyledText(marker + step.description + progress, x, textY, w);
                textY += 32;
            }
        }
    }

    // ═════════════════════════════════════════════
    //  Input mouse (chiamato da GamePanel, stesso schema di UI.updateMouseHover/handleTitleClick)
    // ═════════════════════════════════════════════

    public void handleClick(int x, int y) {
        if (gp.gameState != gp.bookState) return;
        for (Button b : bookmarkButtons) {
            if (b.click(x, y)) return;
        }
        for (Button[] areaButtons : subtopicButtons) {
            for (Button b : areaButtons) {
                if (b.click(x, y)) return;
            }
        }
    }
}
