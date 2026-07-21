package main;

import javax.swing.*;
import entity.Player;
import tile.TileManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Entity;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int OriginalTileSize = 16; //16x16 tile
	final int scale = 3;
	public int tileSize = OriginalTileSize * scale;
	public int maxScreenCol = 16;
	public int maxScreenRow = 12;
	public int screenWidth = tileSize * maxScreenCol; //768
	public int screenHeight = tileSize * maxScreenRow; //576

	//World
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int cinematicState = 4;
	public final int titleState = 5;
	public final int combatState = 6;

	//FPS
	final int FPS = 60;

	TileManager tileM = new TileManager(this);
	public KeyHandler KeyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);

	public Player player = new Player(this, KeyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public int difficulty = 1; // 1 = Easy, 2 = Normal, 3 = Hard

	ArrayList<Entity> entityList = new ArrayList<>();
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread;

	// ── Cinematic (GIF) ──
	GifPlayer cinematicPlayer = new GifPlayer();
	int cinematicReturnState = playState; // stato a cui tornare quando la cinematic finisce


	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);

		// ── Mouse: hover/click sul menu principale (schermata titolo) ──
		MouseAdapter titleMouseHandler = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				ui.updateMouseHover(e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ui.handleTitleClick(e.getX(), e.getY());
			}
		};
		this.addMouseMotionListener(titleMouseHandler);
		this.addMouseListener(titleMouseHandler);
	}
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNpc();
		playMusic(0);
		gameState = titleState;
	}
	public void zoomInOut(int i) {
		int newTileSize = tileSize + i;
		// Limiti per non rompere tutto
		if(newTileSize < 16 || newTileSize > 96) return;

		int oldWorldWidth = tileSize * maxWorldCol;
		tileSize = newTileSize;
		double newWorldWidth = tileSize * maxWorldCol;

		// Riscala la posizione del player proporzionalmente
		double multiplier = newWorldWidth / oldWorldWidth;
		player.worldX *= multiplier;
		player.worldY *= multiplier;
		player.speed = Math.max(1, (int)(newWorldWidth / 600));

		// Ricarica tutte le immagini con il nuovo tileSize
		tileM.getTileImage();
		player.getPlayerImage();
		for(int n = 0; n < npc.length; n++) {
			if(npc[n] instanceof entity.Npc_HumanRedWorker) {
				((entity.Npc_HumanRedWorker)npc[n]).getImage();
			}
		}
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;

		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}

			if(timer >= 1000000000) {
				timer = 0;
			}
		}
	}
	public void update() {
		if(gameState == playState) {
			player.update();
			for(int i = 0; i < obj.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive == false) {
						monster[i] = null;
					}
				}
			}
		}
		if(gameState == pauseState) {
		}
		if(gameState == combatState) {
			ui.combat.update();
		}
		if(gameState == cinematicState) {
			cinematicPlayer.update();
			if(cinematicPlayer.isFinished()) {
				gameState = cinematicReturnState;
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else if(gameState == cinematicState) {
			drawCinematic(g2);
		}
		else {
			tileM.draw(g2);
			player.draw(g2);
			//add entites to list
			entityList.add(player);
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}

			//sort
			Collections.sort(entityList, new Comparator<Entity>() {
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare((int)e1.worldY, (int)e2.worldY);
					return result;
				}

			});
			//draw
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			//reset list
			entityList.clear();

			ui.draw(g2);
		}
		g2.dispose();
	}
	public void playCinematic(String path) {
		playCinematic(path, false);
	}
	public void playCinematic(String path, boolean loop) {
		cinematicReturnState = gameState; // ricorda da dove siamo arrivati, per tornarci alla fine
		cinematicPlayer.load(path, loop);
		gameState = cinematicState;
	}
	public void skipCinematic() {
		if(gameState == cinematicState) gameState = cinematicReturnState;
	}
	// Disegna il frame corrente della cinematic a schermo intero, mantenendo le proporzioni
	// (letterbox: barre nere sopra/sotto o ai lati se l'aspect ratio non combacia con lo schermo)
	private void drawCinematic(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, screenWidth, screenHeight);

		java.awt.image.BufferedImage frame = cinematicPlayer.getCurrentFrame();
		if(frame != null) {
			double scale = Math.min((double) screenWidth / frame.getWidth(), (double) screenHeight / frame.getHeight());
			int w = (int) (frame.getWidth() * scale);
			int h = (int) (frame.getHeight() * scale);
			int x = (screenWidth - w) / 2;
			int y = (screenHeight - h) / 2;
			g2.drawImage(frame, x, y, w, h, null);
		}
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}