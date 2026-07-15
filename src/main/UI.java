package main;

import entity.Entity;
import object.OBJ_Left_Health;
import object.OBJ_Middle_Health;
import object.OBJ_Right_Health;

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
    }

    public void showMessage(String text) { message = text; messageOn = true; }

    // ═════════════════════════════════════════════
    //  DRAW PRINCIPALE
    // ═════════════════════════════════════════════

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(MaruMonica);
        g2.setColor(Color.white);
        textAnimTick++;

        if (gp.gameState == gp.titleState)    { drawTitleScreen(); }
        if (gp.gameState == gp.playState)     { drawPlayerLife(); drawNeutralMenu(); }
        if (gp.gameState == gp.pauseState)    { drawPauseScreen(); drawPlayerLife(); }
        if (gp.gameState == gp.dialogueState) { drawDialogueScreen(); }
        if (gp.gameState == gp.combatState)   { combat.draw(g2); }
    }

    // ═════════════════════════════════════════════
    //  HUD VITA
    // ═════════════════════════════════════════════

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
    //  TITLE SCREEN (invariato rispetto a GitHub)
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

            g2.setFont(MaruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48));
            String[] items = {"New Game", "Load Game", "Options", "Quit"};
            y += gp.tileSize * 3.5;
            for (int i = 0; i < items.length; i++) {
                text = items[i];
                x = getXforCenteredText(text);
                g2.setColor(Color.white);
                g2.drawString(text, x, y);
                if (commandNum == i) g2.drawString(">", x - gp.tileSize, y);
                y += gp.tileSize;
            }
        } else if (titleScreenState == 1) {
            g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 42F));
            g2.setColor(Color.white);
            String text = "Select your class";
            int x = getXforCenteredText(text), y = gp.tileSize * 3;
            g2.drawString(text, x, y);
            String[] classes = {"Warrior", "Mage", "Archer", "Back"};
            for (int i = 0; i < classes.length; i++) {
                text = classes[i];
                x = getXforCenteredText(text);
                y += (i == 3) ? gp.tileSize * 2 : gp.tileSize;
                g2.drawString(text, x, y);
                if (commandNum == i) g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 2) {
            g2.setFont(MaruMonica.deriveFont(Font.PLAIN, 42F));
            g2.setColor(Color.white);
            String text = "Select difficulty";
            int x = getXforCenteredText(text), y = gp.tileSize * 3;
            g2.drawString(text, x, y);
            String[] diffs = {"Easy", "Normal", "Hard", "Back"};
            for (int i = 0; i < diffs.length; i++) {
                text = diffs[i];
                x = getXforCenteredText(text);
                y += (i == 3) ? gp.tileSize * 2 : gp.tileSize;
                g2.drawString(text, x, y);
                if (commandNum == i) g2.drawString(">", x - gp.tileSize, y);
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
}
