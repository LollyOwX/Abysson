package main;

import entity.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, escPressed;
    GamePanel gp;


    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.titleState) {
            if(gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum <= -1) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum >= 4) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                        gp.ui.commandNum = 0;
                        gp.stopMusic();
                    }
                    if (gp.ui.commandNum == 1) {
                        //ADD LATER
                    }
                    if (gp.ui.commandNum == 3) {
                        System.exit(0);
                    }
                }
            }
            else if(gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum <= -1) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum >= 4) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.player.playerClass = "Warrior";
                        gp.player.getPlayerImage();
                        gp.ui.titleScreenState = 2;
                        gp.ui.commandNum = 1;
                    }
                    if (gp.ui.commandNum == 1) {
                        gp.player.playerClass = "Mage";
                        gp.player.getPlayerImage();
                        gp.ui.titleScreenState = 2;
                        gp.ui.commandNum = 1;
                    }
                    if (gp.ui.commandNum == 2) {
                        gp.player.playerClass = "Archer";
                        gp.player.getPlayerImage();
                        gp.ui.titleScreenState = 2;
                        gp.ui.commandNum = 1;
                    }
                    if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                    }
                }
            }
            else if(gp.ui.titleScreenState == 2) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum <= -1) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum >= 4) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.difficulty = 1; // Easy
                        gp.aSetter.setMonster();
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 1) {
                        gp.difficulty = 2; // Normal
                        gp.aSetter.setMonster();
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 2) {
                        gp.difficulty = 3; // Hard
                        gp.aSetter.setMonster();
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 1;
                        gp.ui.commandNum = 0;
                    }
                }
            }
        }
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            //ZOOM+
            if (code == KeyEvent.VK_UP) {
                gp.zoomInOut(1);
            }
            //ZOOM-
            if (code == KeyEvent.VK_DOWN) {
                gp.zoomInOut(-1);
            }
            //Pause
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            //Dialogue
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            //ESC
            if (code == KeyEvent.VK_ESCAPE) {
                escPressed = true;
                gp.gameState = gp.titleState;
                gp.ui.titleScreenState = 0;
                gp.ui.commandNum = 0;
            }
        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        } else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        } else if (gp.gameState == gp.combatState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.combat.commandNum--;
                if (gp.ui.combat.commandNum < 0) gp.ui.combat.commandNum = 3;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.combat.commandNum++;
                if (gp.ui.combat.commandNum > 3) gp.ui.combat.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.ui.combat.confirmCommand();
            }
        }
    }
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}