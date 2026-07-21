package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, escPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) { this.gp = gp; }
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // ── TITLE STATE ──────────────────────────────────────────
        if (gp.gameState == gp.titleState) {
            if (!gp.ui.isMenuConfirmPending()) {
                if (gp.ui.titleScreenState == 0) {
                    // Il main menu blocca la selezione ai bordi (niente wrap)
                    if (code == KeyEvent.VK_W) { gp.ui.commandNum--; if (gp.ui.commandNum <= -1) gp.ui.commandNum = 0; }
                    if (code == KeyEvent.VK_S) { gp.ui.commandNum++; if (gp.ui.commandNum >= 4)  gp.ui.commandNum = 3; }
                } else {
                    // Classe/difficoltà: risalendo da 0 si va in wrap a 3 (comportamento originale)
                    if (code == KeyEvent.VK_W) { gp.ui.commandNum--; if (gp.ui.commandNum <= -1) gp.ui.commandNum = 3; }
                    if (code == KeyEvent.VK_S) { gp.ui.commandNum++; if (gp.ui.commandNum >= 4)  gp.ui.commandNum = 3; }
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Stessa logica di conferma usata anche dal click del mouse, su tutti e 3 gli schermi
                gp.ui.confirmTitleMenu();
            }
        }

        // ── PLAY STATE ───────────────────────────────────────────
        if (gp.gameState == gp.playState) {
            // Neutral menu ha priorità sui tasti W/S/ENTER/ESC
            if (gp.ui.neutralMenuOpen) {
                if (code == KeyEvent.VK_W) { gp.ui.neutralMenuCommand--; if (gp.ui.neutralMenuCommand < 0) gp.ui.neutralMenuCommand = 1; }
                if (code == KeyEvent.VK_S) { gp.ui.neutralMenuCommand++; if (gp.ui.neutralMenuCommand > 1) gp.ui.neutralMenuCommand = 0; }
                if (code == KeyEvent.VK_ENTER)  gp.ui.confirmNeutralMenu();
                if (code == KeyEvent.VK_ESCAPE) gp.ui.closeNeutralMenu();
                return; // non propagare altri tasti mentre il menu è aperto
            }
            if (code == KeyEvent.VK_W) upPressed    = true;
            if (code == KeyEvent.VK_S) downPressed   = true;
            if (code == KeyEvent.VK_A) leftPressed   = true;
            if (code == KeyEvent.VK_D) rightPressed  = true;
            if (code == KeyEvent.VK_UP)   gp.zoomInOut(1);
            if (code == KeyEvent.VK_DOWN) gp.zoomInOut(-1);
            if (code == KeyEvent.VK_P) gp.gameState = gp.pauseState;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
                gp.gameState = gp.titleState;
                gp.ui.titleScreenState = 0;
                gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_I) {
                gp.playCinematic( "/cinematics/Open_book.gif", false);

            }
        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) gp.gameState = gp.playState;

        } else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) gp.gameState = gp.playState;

        } else if (gp.gameState == gp.combatState) {
            if (code == KeyEvent.VK_W)      gp.ui.combat.navigateUp();
            if (code == KeyEvent.VK_S)      gp.ui.combat.navigateDown();
            if (code == KeyEvent.VK_ENTER)  gp.ui.combat.confirmCommand();
            if (code == KeyEvent.VK_ESCAPE) gp.ui.combat.pressEsc();

        } else if (gp.gameState == gp.cinematicState) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) gp.skipCinematic();
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) upPressed   = false;
        if (code == KeyEvent.VK_S) downPressed  = false;
        if (code == KeyEvent.VK_A) leftPressed  = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
    }
}