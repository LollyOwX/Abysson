package main;	

import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
	
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
	
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler KeyH = new KeyHandler(this);
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this,KeyH);
	public SuperObject obj[] = new SuperObject[10];
	
	public GamePanel() {	
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		aSetter.setObject();
		
	}
	public void zoomInOut(int i) {
		int oldWorldWidth = tileSize * maxWorldCol; //2400 1200 = 0.5
		tileSize += i;
		int newWorldWidth = tileSize * maxWorldCol; //2350
		player.speed = newWorldWidth/600;
		double multiplier = newWorldWidth/oldWorldWidth;
		System.out.println("tileSize:"+tileSize);
		System.out.println("tileSize:"+tileSize);
		System.out.println("player World X:"+player.worldX);
		double newPlayerWorldX = player.worldX * multiplier;
		double newPlayerWorldY = player.worldY * multiplier;
		player.worldX = (int) (newPlayerWorldX);
		player.worldY =	(int) (newPlayerWorldY);
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
		int drawCount = 0;
		
		while(gameThread != null) {	
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2);
		g2.dispose();
	}
}









