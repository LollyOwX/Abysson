package main;

import combat.CombatState;
import entity.Entity;
import object.OBJ_Left_Health;
import object.OBJ_Middle_Health;
import object.OBJ_Right_Health;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font MaruMonica, PurisaB, BrothericR, GraenMetalR, WonderWorldR, HoperushD;
    BufferedImage health_left_full, health_left_half, health_left_low, health_left_empty,
            health_center_full, health_center_half, health_center_low, health_center_empty,
            health_right_full, health_right_half, health_right_low, health_right_empty;
    public boolean messageOn = false;
    public String message = " ";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public CombatState combat;

    // ── Behavior neutral: mini-menu Parla/Attacca ────────────────
    public boolean neutralMenuOpen = false;
    public int neutralMenuCommand = 0; // 0 = Parla, 1 = Attacca
    public Entity neutralTarget = null;
    public int neutralTargetIndex = -1;
    public boolean neutralTargetIsNpc = true;

    // ── Contatore globale per animazioni testo ───────────────────
    public int textAnimTick = 0;

    // ═════════════════════════════════════════════
    //  MENU PRINCIPALE: stato per hover/animazioni
    // ═════════════════════════════════════════════

    // Path dell'immagine "glow" che cresce sotto la voce in hover.
    // Sottolineatura larga e sottile (l'asset attuale è 81x9). Deve stare nel classpath (cartella res/),
    // stesso meccanismo usato per i font sopra.
    private static final String MENU_HOVER_IMAGE_PATH = "/ui/menu_hover_glow.png";
    private BufferedImage menuHoverImage;

    private int mouseX = -1, mouseY = -1;
    private int hoveredIndex = -1; // voce sotto il mouse, -1 se nessuna (solo mouse)
    private final Rectangle[] menuItemBounds = new Rectangle[4];
    private final float[] menuHoverProgress = new float[4]; // 0 = riposo, 1 = "attivo" pieno

    // Animazione "punch" al click/conferma di una voce (tastiera ENTER o click mouse)
    private final int[] punchStartTick = {-100000, -100000, -100000, -100000};
    private static final int   PUNCH_DURATION  = 14;    // frame di durata del punch
    private static final float PUNCH_MAGNITUDE = 0.28f; // scala extra al picco del punch

    // Ritardo tra la conferma di una voce e l'esecuzione vera e propria del comando,
    // così il punch (e in generale il feedback visivo) fa in tempo a vedersi anche
    // per voci come "New Game" o "Quit" che altrimenti cambierebbero schermata/uscirebbero
    // dal programma nello stesso istante del click.
    private static final int MENU_CONFIRM_DELAY = 30; // ~1s a 60 FPS
    private int pendingCommand = -1;     // voce confermata in attesa di esecuzione, -1 = nessuna
    private int pendingExecuteTick = -1; // tick in cui va eseguito il comando in attesa
    private int pendingScreen = -1;      // titleScreenState in cui è stato dato il comando in attesa

    private int titleMenuEnterTick = -1; // tick in cui lo schermo di titolo corrente è apparso
    private int lastMenuScreen = -2;     // ultimo titleScreenState visto (-1 = fuori dal titleState);
    // -2 iniziale forza il primo ingresso a contare come "nuovo schermo"

    private static final int   MENU_SLIDE_DURATION = 35;    // frame per lo slide-in di ogni voce
    private static final int   MENU_STAGGER_DELAY  = 10;    // ritardo extra per indice (stagger)
    private static final int   MENU_SLIDE_OFFSET   = 220;   // px di partenza (da destra verso sinistra)
    private static final float HOVER_ANIM_SPEED    = 0.15f; // velocità di transizione hover/selezione (lerp/frame)
    private static final float HOVER_SCALE         = 1.15f; // scala testo quando in hover
    private static final int   HOVER_OFFSET_X      = 18;    // offset orizzontale quando in hover
    private static final float DIM_ALPHA           = 0.45f; // opacità voci non in hover

    // ═════════════════════════════════════════════
    //  LIBRO: bookmark (aree generali) — hit-test mouse
    // ═════════════════════════════════════════════

    // Rettangoli dei 6 bookmark nello spazio immagine originale del libro (272x272, prima dello
    // scaling a schermo). Misurati confrontando i pixel non trasparenti di book.png con ognuna
    // delle 6 varianti book_X.png: sono la zona (linguetta) che cambia in ciascuna variante.
    // Indice = gp.bookindex - 1 (stesso ordine di GamePanel.ZONE_IMAGES: map, quests, skills,
    // calendar, bestiary, inventory — cioè bookindex_map=1 ... bookindex_inventory=6).
    private static final Rectangle[] BOOKMARK_IMAGE_RECTS = {
            new Rectangle(64,  82, 17, 73), // 0 Mappa      (bookindex 1)
            new Rectangle(157, 73, 17, 82), // 1 Quest      (bookindex 2)
            new Rectangle(217, 83, 17, 73), // 2 Abilità    (bookindex 3)
            new Rectangle(96,  75, 17, 80), // 3 Calendario (bookindex 4)
            new Rectangle(36,  83, 17, 73), // 4 Bestiario  (bookindex 5)
            new Rectangle(185, 82, 17, 72), // 5 Inventario (bookindex 6)
    };
    private final Rectangle[] bookmarkScreenBounds = new Rectangle[BOOKMARK_IMAGE_RECTS.length];
    private int hoveredBookmark = -1;

    // Voci (bookzone) dell'area attiva: nessun asset dedicato ancora (vedi TODO in STATUS.md),
    // quindi bottoni testuali disegnati e dimensionati a schermo in base al testo stesso
    // (font metrics + un po' di padding) — non c'è un layout fisso da rispettare come per i
    // bookmark. Lunghezza variabile: quante sono le voci effettivamente sbloccate in quell'area.
    private final List<Rectangle> bookZoneButtonBounds = new ArrayList<>();
    private int hoveredBookZoneButton = -1;
    private static final int ZONE_BUTTON_PAD_X = 14;
    private static final int ZONE_BUTTON_PAD_Y = 8;
    private static final int ZONE_BUTTON_GAP   = 6; // spazio verticale tra un bottone e il successivo

    public UI(GamePanel gp) {
        this.gp = gp;
        combat = new CombatState(gp, this);
        try {
            InputStream is;
            is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            PurisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Brotheric-Regular-Demo.otf");
            BrothericR = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/GraenMetal-Regular.otf");
            GraenMetalR = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/HoperushDemo-G35pG.otf");
            HoperushD = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/WonderworldPersonalUseRegular-gxdo3.otf");
            WonderWorldR = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        Entity lefthealth   = new OBJ_Left_Health(gp);
        Entity middlehealth = new OBJ_Middle_Health(gp);
        Entity righthealth  = new OBJ_Right_Health(gp);
        health_left_full    = lefthealth.image;
        health_left_half    = lefthealth.image2;
        health_left_low     = lefthealth.image3;
        health_left_empty   = lefthealth.image4;
        health_center_full  = middlehealth.image;
        health_center_half  = middlehealth.image2;
        health_center_low   = middlehealth.image3;
        health_center_empty = middlehealth.image4;
        health_right_full   = righthealth.image;
        health_right_half   = righthealth.image2;
        health_right_low    = righthealth.image3;
        health_right_empty  = righthealth.image4;

        try {
            InputStream hoverIs = getClass().getResourceAsStream(MENU_HOVER_IMAGE_PATH);
            if (hoverIs != null) {
                menuHoverImage = ImageIO.read(hoverIs);
            } else {
                System.err.println("ERROR: resource not found: " + MENU_HOVER_IMAGE_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) { message = text; messageOn = true; }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(MaruMonica);
        g2.setColor(Color.white);
        textAnimTick++;

        if (gp.gameState == gp.titleState) updatePendingMainMenuCommand();

        // Rileva l'ingresso in un nuovo schermo del titolo (0/1/2) per far ripartire
        // slide-in/stagger, e per evitare che hover/punch di uno schermo restino "sporchi"
        // passando al successivo (es. Options -> Select class).
        boolean inTitleMenu = (gp.gameState == gp.titleState);
        int screenKey = inTitleMenu ? titleScreenState : -1;
        if (inTitleMenu && screenKey != lastMenuScreen) {
            titleMenuEnterTick = textAnimTick;
            hoveredIndex = -1;
            java.util.Arrays.fill(menuHoverProgress, 0f);
            java.util.Arrays.fill(punchStartTick, -100000);
        }
        lastMenuScreen = screenKey;

        if (gp.gameState == gp.titleState)    { drawTitleScreen(); }
        if (gp.gameState == gp.playState)     { drawPlayerLife(); drawNeutralMenu(); }
        if (gp.gameState == gp.pauseState)    { drawPauseScreen(); drawPlayerLife(); }
        if (gp.gameState == gp.dialogueState) { drawDialogueScreen(); }
        if (gp.gameState == gp.combatState)   { combat.draw(g2); }
        if (gp.gameState == gp.bookState)     { drawBookScreen(); }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        BufferedImage left, center, right;
        double hp = (double) gp.player.life / gp.player.maxLife * 100;
        if      (hp > 75) { left = health_left_full;  center = health_center_full;  right = health_right_full;  }
        else if (hp > 50) { left = health_left_half;  center = health_center_half;  right = health_right_half;  }
        else if (hp > 25) { left = health_left_low;   center = health_center_low;   right = health_right_low;   }
        else              { left = health_left_empty;  center = health_center_empty; right = health_right_empty; }
        g2.drawImage(left,   x, y, null); x += gp.tileSize;
        g2.drawImage(center, x, y, null); x += gp.tileSize;
        g2.drawImage(right,  x, y, null);
    }

    // ═════════════════════════════════════════════
    //  TITLE SCREEN
    // ═════════════════════════════════════════════

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setFont(BrothericR);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 72));
            String text = "Abysson";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.setColor(new Color(0, 0, 255));   g2.drawString(text, x + 4, y + 4);
            g2.setColor(new Color(0, 127, 255)); g2.drawString(text, x + 2, y + 2);
            g2.setColor(new Color(0, 255, 200)); g2.drawString(text, x, y);

            String[] items = {"New Game", "Load Game", "Options", "Quit"};
            int baseY = (int) (y + gp.tileSize * 3.5);
            int[] itemYs = { baseY, baseY + gp.tileSize, baseY + gp.tileSize * 2, baseY + gp.tileSize * 3 };
            drawMenuItems(items, itemYs, 48f);

        } else if (titleScreenState == 1) {
            g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 42F));
            g2.setColor(Color.white);
            String text = "Select your class";
            int y0 = gp.tileSize * 3;
            g2.drawString(text, getXforCenteredText(text), y0);

            String[] items = {"Warrior", "Wizard", "Archer", "Back"};
            int T = gp.tileSize;
            int[] itemYs = { y0 + T, y0 + 2 * T, y0 + 3 * T, y0 + 5 * T };
            drawMenuItems(items, itemYs, 42F);

        } else if (titleScreenState == 2) {
            g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 42F));
            g2.setColor(Color.white);
            String text = "Select difficulty";
            int y0 = gp.tileSize * 3;
            g2.drawString(text, getXforCenteredText(text), y0);

            String[] items = {"Easy", "Normal", "Hard", "Back"};
            int T = gp.tileSize;
            int[] itemYs = { y0 + T, y0 + 2 * T, y0 + 3 * T, y0 + 5 * T };
            drawMenuItems(items, itemYs, 42F);
        }
    }

    // Renderizza le 4 voci di un qualunque schermo del titolo (main menu, classe, difficoltà)
    // con tutti gli effetti: slide-in + stagger, float, scale/offset/dimming/glow in hover
    // (mouse o tastiera), punch alla conferma. Popola anche menuItemBounds per l'hit-test mouse.
    private void drawMenuItems(String[] items, int[] itemBaseYs, float fontSize) {
        g2.setFont(MaruMonica.deriveFont(Font.PLAIN, fontSize));
        FontMetrics fm = g2.getFontMetrics();
        int elapsed = (titleMenuEnterTick < 0) ? 999999 : textAnimTick - titleMenuEnterTick;

        for (int i = 0; i < items.length; i++) {
            String text = items[i];
            int itemBaseX = getXforCenteredText(text);
            int itemBaseY = itemBaseYs[i];

            // 1) Sliding-in + 2) stagger per indice: ogni voce parte in ritardo in base al suo indice
            int itemElapsed = elapsed - i * MENU_STAGGER_DELAY;
            float slideT = easeOutCubic(clamp01(itemElapsed / (float) MENU_SLIDE_DURATION));
            int slideOffsetX = (int) ((1 - slideT) * MENU_SLIDE_OFFSET);

            // 3) Float continuo (leggero ondeggiamento verticale, sfasato per indice)
            float floatPhase = (textAnimTick * 0.05f) + i * 1.3f;
            int floatOffsetY = (int) (Math.sin(floatPhase) * 3 * slideT);

            // Voce "attiva": il mouse ha la priorità se è sopra una voce, altrimenti conta
            // la selezione da tastiera (commandNum) — così scale/offset/glow/dimming
            // funzionano identici sia con mouse che con W/S, su qualunque schermo.
            int activeIndex = (hoveredIndex != -1) ? hoveredIndex : commandNum;
            boolean isActive = (activeIndex == i);
            menuHoverProgress[i] += ((isActive ? 1f : 0f) - menuHoverProgress[i]) * HOVER_ANIM_SPEED;
            float hp = menuHoverProgress[i];

            // 5) Offsetting della voce attiva
            int hoverOffsetX = (int) (HOVER_OFFSET_X * hp);

            // Punch: piccolo "pop" di scala quando la voce viene confermata (ENTER o click)
            int punchElapsed = textAnimTick - punchStartTick[i];
            float punchScale = 0f;
            if (punchElapsed >= 0 && punchElapsed < PUNCH_DURATION) {
                float punchT = punchElapsed / (float) PUNCH_DURATION;
                punchScale = (float) Math.sin(punchT * Math.PI) * PUNCH_MAGNITUDE;
            }

            int drawX = itemBaseX + slideOffsetX + hoverOffsetX;
            int drawY = itemBaseY + floatOffsetY;

            // Bounding box per l'hit-test del mouse (posizione "a riposo", senza l'offset di hover,
            // altrimenti il rettangolo si sposterebbe insieme al testo mentre è già in hover)
            int boxW = fm.stringWidth(text) + 40;
            int boxH = fm.getHeight();
            menuItemBounds[i] = new Rectangle(itemBaseX + slideOffsetX - 20, itemBaseY - boxH + 6, boxW, boxH + 10);

            // 6) Immagine (sottolineatura) che cresce in larghezza sotto al testo in hover,
            // centrata, da 0 alla larghezza piena — l'asset reale è largo e sottile (~81x9)
            if (menuHoverImage != null && hp > 0.01f) {
                int fullW = menuHoverImage.getWidth();
                int imgH  = menuHoverImage.getHeight();
                int curW  = Math.max(1, (int) (fullW * hp));
                int imgX  = drawX + fm.stringWidth(text) / 2 - curW / 2;
                int imgY  = drawY + 6;
                g2.drawImage(menuHoverImage, imgX, imgY, curW, imgH, null);
            }

            // 7) Dimming delle voci non attive, solo quando ce n'è una attiva
            float dimAlpha = 1f;
            if (!isActive) {
                dimAlpha = 1f - (1f - DIM_ALPHA) * menuHoverProgress[activeIndex];
            }
            Composite origComposite = g2.getComposite();
            if (dimAlpha < 1f) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dimAlpha));
            }

            // 4) Scaling della voce attiva + punch al momento della conferma
            Font baseFont = g2.getFont();
            float scale = 1f + (HOVER_SCALE - 1f) * hp + punchScale;
            if (scale != 1f) {
                g2.setFont(baseFont.deriveFont(baseFont.getSize2D() * scale));
            }
            FontMetrics scaledFm = g2.getFontMetrics();
            int centeredDrawX = drawX + (fm.stringWidth(text) - scaledFm.stringWidth(text)) / 2;

            g2.setColor(Color.white);
            g2.drawString(text, centeredDrawX, drawY);
            if (commandNum == i) g2.drawString(">", centeredDrawX - gp.tileSize, drawY);

            g2.setFont(baseFont);
            g2.setComposite(origComposite);
        }
    }

    // ═════════════════════════════════════════════
    //  MENU PRINCIPALE: input mouse (chiamato da GamePanel)
    // ═════════════════════════════════════════════

    public void updateMouseHover(int x, int y) {
        mouseX = x; mouseY = y;
        if (gp.gameState != gp.titleState) { hoveredIndex = -1; return; }
        int idx = -1;
        for (int i = 0; i < menuItemBounds.length; i++) {
            if (menuItemBounds[i] != null && menuItemBounds[i].contains(x, y)) { idx = i; break; }
        }
        hoveredIndex = idx;
    }

    public void handleTitleClick(int x, int y) {
        if (gp.gameState != gp.titleState) return;
        if (pendingCommand != -1) return; // già in attesa che un comando venga eseguito
        for (int i = 0; i < menuItemBounds.length; i++) {
            if (menuItemBounds[i] != null && menuItemBounds[i].contains(x, y)) {
                commandNum = i;
                confirmTitleMenu();
                return;
            }
        }
    }

    public boolean isMenuConfirmPending() {
        return pendingCommand != -1;
    }

    // Logica di conferma della voce selezionata nella schermata di titolo attiva
    // (main menu, selezione classe o selezione difficoltà). Richiamata sia da tastiera
    // (KeyHandler, tasto ENTER) sia da mouse (click su una voce).
    // Non esegue il comando subito: fa partire il punch e programma l'esecuzione vera
    // e propria dopo MENU_CONFIRM_DELAY frame, così l'effetto si vede sempre, su ogni schermo.
    public void confirmTitleMenu() {
        if (pendingCommand != -1) return; // ignora conferme ripetute mentre si è già in attesa
        punchStartTick[commandNum] = textAnimTick; // "pop" di conferma, identico per tastiera e mouse
        pendingCommand = commandNum;
        pendingScreen  = titleScreenState;
        pendingExecuteTick = textAnimTick + MENU_CONFIRM_DELAY;
    }

    // Esegue il comando in attesa una volta trascorso il ritardo, sullo schermo in cui era
    // stato dato (main menu / classe / difficoltà). Chiamato ad ogni frame in titleState.
    private void updatePendingMainMenuCommand() {
        if (pendingCommand == -1 || textAnimTick < pendingExecuteTick) return;
        int cmd    = pendingCommand;
        int screen = pendingScreen;
        pendingCommand = -1;

        if (screen == 0) {
            if (cmd == 0) { titleScreenState = 1; commandNum = 0; gp.stopMusic(); }
            if (cmd == 1) { /* ADD LATER */ }
            if (cmd == 3) { System.exit(0); }

        } else if (screen == 1) {
            if (cmd == 0) {
                gp.player.playerClass = "Warrior"; gp.player.getPlayerImage();
                titleScreenState = 2; commandNum = 1;
            } else if (cmd == 1) {
                gp.player.playerClass = "Mage"; gp.player.getPlayerImage();
                titleScreenState = 2; commandNum = 1;
            } else if (cmd == 2) {
                gp.player.playerClass = "Archer"; gp.player.getPlayerImage();
                titleScreenState = 2; commandNum = 1;
            } else if (cmd == 3) {
                titleScreenState = 0; commandNum = 0;
            }

        } else if (screen == 2) {
            if (cmd == 0) {
                gp.difficulty = 1; gp.aSetter.setMonster(); gp.gameState = gp.playState; gp.playMusic(0);
            } else if (cmd == 1) {
                gp.difficulty = 2; gp.aSetter.setMonster(); gp.gameState = gp.playState; gp.playMusic(0);
            } else if (cmd == 2) {
                gp.difficulty = 3; gp.aSetter.setMonster(); gp.gameState = gp.playState; gp.playMusic(0);
            } else if (cmd == 3) {
                titleScreenState = 1; commandNum = 0;
            }
        }
    }

    // ═════════════════════════════════════════════
    //  PAUSE
    // ═════════════════════════════════════════════

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 83));
        String text = "PAUSED";
        g2.drawString(text, getXforCenteredText(text), gp.screenHeight / 2);
    }

    // Disegna il libro sopra al mondo (già disegnato da GamePanel prima di chiamare draw()),
    // esattamente come drawPauseScreen() disegna "PAUSED" sopra al mondo.
    // Sfondo: book.png se nessuna area generale è selezionata, altrimenti book_X.png dell'area
    // attiva (bookmark evidenziato già dentro l'immagine — vedi GamePanel.getCurrentBookImage()).
    // TODO: il testo placeholder (vedi getBookContentText()) va sostituito dai contenuti veri di
    // ciascuna area generale quando pronti.
    public void drawBookScreen() {
        BufferedImage bg = gp.getCurrentBookImage();
        if (bg == null) return;

        double scale = Math.min((double) gp.screenWidth / bg.getWidth(), (double) gp.screenHeight / bg.getHeight());
        int w = (int) (bg.getWidth() * scale);
        int h = (int) (bg.getHeight() * scale);
        int ox = (gp.screenWidth - w) / 2;
        int oy = (gp.screenHeight - h) / 2;
        g2.drawImage(bg, ox, oy, w, h, null);

        // Bookmark: sempre cliccabili (anche per cambiare area generale mentre se ne sta già
        // guardando una). Le bounding box a schermo si ricalcolano ogni frame perché seguono lo
        // scaling del libro. Niente overlay separato sopra: la linguetta evidenziata è già dentro
        // book_X.png (background sopra) — disegnarla due volte la stirava e sdoppiava a schermo.
        for (int i = 0; i < BOOKMARK_IMAGE_RECTS.length; i++) {
            Rectangle r = BOOKMARK_IMAGE_RECTS[i];
            bookmarkScreenBounds[i] = new Rectangle(
                    ox + (int) (r.x * scale), oy + (int) (r.y * scale),
                    (int) (r.width * scale), (int) (r.height * scale));
        }

        // Voci (bookzone) dell'area attiva: lista verticale di bottoni testuali nella fascia alta
        // della pagina, libera perché i bookmark occupano quella centrale (vedi BOOKMARK_IMAGE_RECTS,
        // partono da y=73 in spazio immagine). Nessun asset dedicato ancora (vedi STATUS.md), quindi
        // ogni bottone è dimensionato sul proprio testo (font metrics + padding), non su un layout fisso.
        bookZoneButtonBounds.clear();
        if (gp.bookindex != 0 && !gp.pageTurnActive) {
            List<BookEntry> zones = gp.unlockedBookZones.get(gp.bookindex - 1);
            g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 18f));
            FontMetrics fm = g2.getFontMetrics();
            int buttonHeight = fm.getHeight() + ZONE_BUTTON_PAD_Y * 2;
            int buttonY = oy + 20;
            for (int i = 0; i < zones.size(); i++) {
                String label = zones.get(i).name;
                int buttonWidth = fm.stringWidth(label) + ZONE_BUTTON_PAD_X * 2;
                Rectangle bounds = new Rectangle(ox + 20, buttonY, buttonWidth, buttonHeight);
                bookZoneButtonBounds.add(bounds);

                boolean active = (i == gp.bookzone);
                boolean hovered = (i == hoveredBookZoneButton);
                g2.setColor(active ? Color.yellow : (hovered ? Color.lightGray : Color.white));
                g2.drawString(label, bounds.x + ZONE_BUTTON_PAD_X, bounds.y + fm.getAscent() + ZONE_BUTTON_PAD_Y);

                buttonY += buttonHeight + ZONE_BUTTON_GAP;
            }
        }

        if (gp.bookindex != 0 && !gp.pageTurnActive) {
            String content = getBookContentText();
            g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 22f));
            g2.setColor(Color.white);
            g2.drawString(content, ox + w / 2 - g2.getFontMetrics().stringWidth(content) / 2, oy + h - 30);
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

    private String getBookContentText() {
        BookEntry entry = gp.getCurrentBookEntry();
        if (entry == null) return "Niente sbloccato ancora in quest'area";
        return entry.name + " — Pagina " + (gp.bookpage + 1) + "/" + entry.pages.length + ": " + entry.pages[gp.bookpage];
    }


    public void updateBookMouseHover(int x, int y) {
        if (gp.gameState != gp.bookState) { hoveredBookmark = -1; hoveredBookZoneButton = -1; return; }
        hoveredBookmark = -1;
        for (int i = 0; i < bookmarkScreenBounds.length; i++) {
            if (bookmarkScreenBounds[i] != null && bookmarkScreenBounds[i].contains(x, y)) { hoveredBookmark = i; break; }
        }
        hoveredBookZoneButton = -1;
        for (int i = 0; i < bookZoneButtonBounds.size(); i++) {
            if (bookZoneButtonBounds.get(i).contains(x, y)) { hoveredBookZoneButton = i; break; }
        }
    }

    public void handleBookClick(int x, int y) {
        if (gp.gameState != gp.bookState) return;
        for (int i = 0; i < bookmarkScreenBounds.length; i++) {
            if (bookmarkScreenBounds[i] != null && bookmarkScreenBounds[i].contains(x, y)) {
                gp.selectBookIndex(i + 1); // array 0-based -> bookindex 1-based
                return;
            }
        }
        for (int i = 0; i < bookZoneButtonBounds.size(); i++) {
            if (bookZoneButtonBounds.get(i).contains(x, y)) {
                gp.selectBookZone(i);
                return;
            }
        }
    }

    // ═════════════════════════════════════════════
    //  DIALOGO con testo stilizzato tramite tag
    // ═════════════════════════════════════════════

    public void drawDialogueScreen() {
        g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 32));
        int x      = gp.tileSize * 2;
        int y      = gp.tileSize / 2;
        int width  = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindwow(x, y, width, height);

        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;

        // Disegna testo con tag di stile
        drawStyledText(currentDialogue, textX, textY, width - gp.tileSize * 2);

        // Triangolo "vai avanti" in basso a destra della box
        drawNextIndicator(x + width, y + height);
    }

    // ═════════════════════════════════════════════
    //  BEHAVIOR NEUTRAL: mini-menu Parla/Attacca
    // ═════════════════════════════════════════════

    public void drawNeutralMenu() {
        if (!neutralMenuOpen || neutralTarget == null) return;

        g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 28));
        int x      = gp.tileSize * 2;
        int y      = gp.screenHeight - gp.tileSize * 4;
        int width  = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 3;
        drawSubWindwow(x, y, width, height);

        g2.setColor(Color.white);
        g2.drawString(neutralTarget.name != null ? neutralTarget.name : "???", x + 20, y + 36);

        String[] options = {"Parla", "Attacca"};
        int optX = x + 40, optY = y + 80;
        for (int i = 0; i < options.length; i++) {
            g2.drawString(options[i], optX, optY);
            if (neutralMenuCommand == i) g2.drawString(">", optX - 24, optY);
            optY += 40;
        }
        drawNextIndicator(x + width, y + height);
    }

    public void openNeutralMenu(Entity target, int index, boolean isNpc) {
        neutralMenuOpen    = true;
        neutralMenuCommand = 0;
        neutralTarget      = target;
        neutralTargetIndex = index;
        neutralTargetIsNpc = isNpc;
    }

    public void closeNeutralMenu() {
        neutralMenuOpen    = false;
        neutralTarget      = null;
        neutralTargetIndex = -1;
        neutralMenuCommand = 0;
    }

    public void confirmNeutralMenu() {
        if (!neutralMenuOpen || neutralTarget == null) return;
        if (neutralMenuCommand == 0) {
            gp.gameState = gp.dialogueState;
            neutralTarget.speak();
            closeNeutralMenu();
        } else {
            int idx = neutralTargetIndex;
            boolean isNpc = neutralTargetIsNpc;
            closeNeutralMenu();
            Entity target = isNpc ? gp.npc[idx] : gp.monster[idx];
            gp.gameState = gp.combatState;
            combat.startCombat(target, idx);
        }
    }

    // ═════════════════════════════════════════════
    //  TRIANGOLO "NEXT" INDICATOR
    // ═════════════════════════════════════════════

    public void drawNextIndicator(int boxRight, int boxBottom) {
        // Lampeggio: visibile 30 frame, invisibile 30
        if ((textAnimTick / 30) % 2 == 1) return;
        int size = 12, margin = 14;
        int cx = boxRight - margin, cy = boxBottom - margin;
        int[] xs = {cx - size, cx + size, cx};
        int[] ys = {cy - size, cy - size, cy + size / 2};
        g2.setColor(Color.white);
        g2.fillPolygon(xs, ys, 3);
    }

    // ═════════════════════════════════════════════
    //  TESTO STILIZZATO CON TAG
    //
    //  Tag supportati:
    //    <gold>testo</gold>       → colore dorato
    //    <red>testo</red>         → colore rosso
    //    <blue>testo</blue>       → colore blu
    //    <green>testo</green>     → colore verde
    //    <shake>testo</shake>     → testo che trema
    //    <wave>testo</wave>       → testo ondulato
    //    <rainbow>testo</rainbow> → colori arcobaleno
    //
    //  Esempio: "La <gold>spada</gold> è <shake>leggendaria</shake>!"
    //
    //  Per aggiungere un nuovo tag colore: aggiungi il case in applyTagStyle()
    //  Per aggiungere un nuovo tag animato: aggiungi il case in drawSegmentWord()
    // ═════════════════════════════════════════════

    public void drawStyledText(String text, int startX, int startY, int maxWidth) {
        if (text == null || text.isEmpty()) return;
        List<TextSegment> segments = parseStyledText(text);
        FontMetrics fm = g2.getFontMetrics();
        int x = startX, y = startY;
        int lineHeight = fm.getHeight() + 8;

        for (TextSegment seg : segments) {
            String[] words = seg.content.split("(?<= )");
            for (String word : words) {
                int wordW = fm.stringWidth(word);
                if (x + wordW > startX + maxWidth && x > startX) { x = startX; y += lineHeight; }
                drawSegmentWord(word, seg.tag, x, y, fm);
                x += wordW;
            }
        }
    }

    private void drawSegmentWord(String word, String tag, int startX, int startY, FontMetrics fm) {
        Color orig = g2.getColor();
        if (tag.equals("shake") || tag.equals("wave") || tag.equals("rainbow")) {
            int cx = startX;
            for (int i = 0; i < word.length(); i++) {
                String ch = String.valueOf(word.charAt(i));
                int offX = 0, offY = 0;
                switch (tag) {
                    case "shake":
                        offX = (int)(Math.sin(textAnimTick * 0.5 + i * 1.7) * 2);
                        offY = (int)(Math.cos(textAnimTick * 0.5 + i * 2.3) * 2);
                        g2.setColor(orig);
                        break;
                    case "wave":
                        offY = (int)(Math.sin((textAnimTick * 0.1) + i * 0.6) * 4);
                        g2.setColor(orig);
                        break;
                    case "rainbow":
                        float hue = ((textAnimTick * 0.02f) + (i * 0.1f)) % 1.0f;
                        g2.setColor(Color.getHSBColor(hue, 1f, 1f));
                        break;
                }
                g2.drawString(ch, cx + offX, startY + offY);
                cx += fm.stringWidth(ch);
            }
        } else {
            applyTagStyle(tag);
            g2.drawString(word, startX, startY);
        }
        g2.setColor(orig);
    }

    private void applyTagStyle(String tag) {
        switch (tag) {
            case "gold":  g2.setColor(new Color(255, 200, 0));   break;
            case "red":   g2.setColor(new Color(220, 50,  50));  break;
            case "blue":  g2.setColor(new Color(80,  150, 255)); break;
            case "green": g2.setColor(new Color(80,  220, 80));  break;
            default:      g2.setColor(Color.white);               break;
        }
    }

    // ── Parser tag ───────────────────────────────────────────────

    private static class TextSegment {
        String content, tag;
        TextSegment(String content, String tag) { this.content = content; this.tag = tag; }
    }

    private List<TextSegment> parseStyledText(String text) {
        List<TextSegment> segments = new ArrayList<>();
        int i = 0;
        StringBuilder plain = new StringBuilder();
        while (i < text.length()) {
            if (text.charAt(i) == '<') {
                int close = text.indexOf('>', i);
                if (close == -1) { plain.append(text.charAt(i++)); continue; }
                String tagName = text.substring(i + 1, close);
                if (tagName.startsWith("/")) { i = close + 1; continue; }
                if (plain.length() > 0) { segments.add(new TextSegment(plain.toString(), "")); plain.setLength(0); }
                String closeTag = "</" + tagName + ">";
                int contentStart = close + 1;
                int contentEnd   = text.indexOf(closeTag, contentStart);
                if (contentEnd == -1) { i = close + 1; continue; }
                segments.add(new TextSegment(text.substring(contentStart, contentEnd), tagName));
                i = contentEnd + closeTag.length();
            } else {
                plain.append(text.charAt(i++));
            }
        }
        if (plain.length() > 0) segments.add(new TextSegment(plain.toString(), ""));
        return segments;
    }

    // ═════════════════════════════════════════════
    //  UTILITY
    // ═════════════════════════════════════════════

    public void drawSubWindwow(int x, int y, int width, int height) {
        g2.setColor(new Color(255, 255, 255));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(new Color(0, 0, 0));
        g2.setStroke(new BasicStroke(5));
        g2.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawStatsBars() {}

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getYforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenHeight / 2 - length / 2;
    }

    private static float clamp01(float v) { return v < 0 ? 0 : (v > 1 ? 1 : v); }

    private static float easeOutCubic(float t) { return 1 - (float) Math.pow(1 - t, 3); }
}