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
import java.util.List;

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
    public final int bookState = 7;

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

    // ── Libro: 3 livelli di navigazione, stessa convenzione di gameState (costanti nominate + variabile) ──
    // bookindex (area generale) = bookmark cliccato sul dorso del libro (l'intera categoria: Quest, Mappa, ecc.)
    // bookzone  (voce)          = singola voce dentro l'area generale (es. "Quest 1", "Quest 2"...)
    // bookpage  (dettaglio)     = pagina di dettaglio dentro alla voce (requisiti, ecc. — non sempre presente)
    public int bookindex; // 0 = nessuna area generale selezionata (si vede solo book.png con i bookmark)
    public final int bookindex_map       = 1;
    public final int bookindex_quests    = 2;
    public final int bookindex_skills    = 3;
    public final int bookindex_calendar  = 4;
    public final int bookindex_bestiary  = 5;
    public final int bookindex_inventory = 6;

    public int bookzone; // indice della voce sbloccata dentro all'area generale (dentro unlockedBookZones)
    public int bookpage; // pagina di dettaglio dentro alla voce (dentro BookEntry.pages)

    static final int ZONE_COUNT = 6;
    // Nomi file immagine: indice = bookindex-1 (stesso ordine delle costanti bookindex_* sopra).
    static final String[] ZONE_IMAGES = {"book_map.png", "book_quests.png", "book_skills.png",
            "book_calendar.png", "book_bestiary.png", "book_inventory.png"};
    // Voci sbloccate per area (indice = bookindex-1, stesso ordine di ZONE_IMAGES sopra). Stesso
    // pattern di Entity.unlockedAbilities: una voce esiste per il giocatore solo se è in questa
    // lista — niente booleano "locked" da controllare altrove. Vuota finché non sbloccata.
    List<List<BookEntry>> unlockedBookZones = new ArrayList<>();

    java.awt.image.BufferedImage bookImage; // pagina di base, nessuna area generale selezionata
    java.awt.image.BufferedImage[] bookZoneImages = new java.awt.image.BufferedImage[ZONE_COUNT]; // book_map.png, ecc.
    GifPlayer pageTurnPlayer = new GifPlayer();
    boolean pageTurnActive = false;

    // Valori a cui passare (bookindex/bookzone/bookpage) non appena l'animazione turning_pages finisce
    int pendingBookindex, pendingBookzone, pendingBookpage;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);

        // ── Mouse: hover/click sul menu principale (titolo) e sul libro (bookmark) ──
        MouseAdapter titleMouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ui.updateMouseHover(e.getX(), e.getY());
                ui.updateBookMouseHover(e.getX(), e.getY());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.handleTitleClick(e.getX(), e.getY());
                ui.handleBookClick(e.getX(), e.getY());
            }
        };
        this.addMouseMotionListener(titleMouseHandler);
        this.addMouseListener(titleMouseHandler);

        loadBookImage();
        seedBookDebugEntries();
    }
    private void loadBookImage() {
        bookImage = loadImageResource("/ui/book.png");
        for(int i = 0; i < ZONE_COUNT; i++) {
            bookZoneImages[i] = loadImageResource("/ui/" + ZONE_IMAGES[i]);
        }
    }
    // Voci di debug per provare la navigazione del libro finché i veri punti di sblocco (quest
    // accettata, mostro incontrato, area scoperta...) non sono collegati. Da rimuovere quando
    // arrivano i contenuti veri — vedi STATUS.md.
    private void seedBookDebugEntries() {
        for(int i = 0; i < ZONE_COUNT; i++) unlockedBookZones.add(new ArrayList<>());
        unlockedBookZones.get(bookindex_map - 1).add(new BookEntry("Zona di partenza", new String[]{"Stub pagina 1", "Stub pagina 2"}));
        unlockedBookZones.get(bookindex_map - 1).add(new BookEntry("Villaggio", new String[]{"Stub pagina 1"}));
        unlockedBookZones.get(bookindex_quests - 1).add(new BookEntry("Quest di prova 1", new String[]{"Stub pagina 1", "Stub requisiti"}));
        unlockedBookZones.get(bookindex_quests - 1).add(new BookEntry("Quest di prova 2", new String[]{"Stub pagina 1"}));
        unlockedBookZones.get(bookindex_skills - 1).add(new BookEntry("Colpo base", new String[]{"Stub pagina 1", "Stub pagina 2"}));
        unlockedBookZones.get(bookindex_skills - 1).add(new BookEntry("Parata", new String[]{"Stub pagina 1"}));
        unlockedBookZones.get(bookindex_calendar - 1).add(new BookEntry("Giorno 1", new String[]{"Stub pagina 1", "Stub pagina 2"}));
        unlockedBookZones.get(bookindex_calendar - 1).add(new BookEntry("Giorno 2", new String[]{"Stub pagina 1"}));
        unlockedBookZones.get(bookindex_bestiary - 1).add(new BookEntry("Goblin", new String[]{"Stub pagina 1", "Stub debolezze"}));
        unlockedBookZones.get(bookindex_bestiary - 1).add(new BookEntry("Slime", new String[]{"Stub pagina 1"}));
        unlockedBookZones.get(bookindex_inventory - 1).add(new BookEntry("Pozione", new String[]{"Stub pagina 1", "Stub pagina 2"}));
        unlockedBookZones.get(bookindex_inventory - 1).add(new BookEntry("Antidoto", new String[]{"Stub pagina 1"}));
    }
    private java.awt.image.BufferedImage loadImageResource(String path) {
        java.io.InputStream is = getClass().getResourceAsStream(path);
        if(is == null) {
            System.err.println("ERROR: resource not found: " + path);
            return null;
        }
        try {
            return javax.imageio.ImageIO.read(is);
        } catch(java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Immagine di sfondo del libro da disegnare in questo momento: book.png se nessuna area generale
    // è selezionata, altrimenti la variante book_X.png dell'area attiva (bookmark evidenziato).
    public java.awt.image.BufferedImage getCurrentBookImage() {
        if(bookindex == 0) return bookImage;
        return bookZoneImages[bookindex - 1] != null ? bookZoneImages[bookindex - 1] : bookImage;
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
        if(gameState == bookState && pageTurnActive) {
            pageTurnPlayer.update();
            if(pageTurnPlayer.isFinished()) {
                pageTurnActive = false;
                bookindex = pendingBookindex;
                bookzone = pendingBookzone;
                bookpage = pendingBookpage;
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Lo sfondo da disegnare "sotto" è lo stato a cui la cinematic tornerà (non cinematicState
        // stesso, che non è un vero schermo) — così i pixel trasparenti del GIF rivelano il mondo
        // di gioco (o il titolo) invece di mostrare solo il nero di base del pannello.
        int backdropState = (gameState == cinematicState) ? cinematicReturnState : gameState;

        if(backdropState == titleState) {
            ui.draw(g2);
        }
        else {
            drawWorld(g2);
        }

        if(gameState == cinematicState) {
            drawCinematic(g2);
        }

        g2.dispose();
    }
    private void drawWorld(Graphics2D g2) {
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
    public void playCinematic(String path) {
        playCinematic(path, false, gameState);
    }
    public void playCinematic(String path, boolean loop) {
        playCinematic(path, loop, gameState);
    }
    public void playCinematic(String path, boolean loop, int nextState) {
        cinematicReturnState = nextState; // stato a cui andare quando la cinematic finisce
        cinematicPlayer.load(path, loop);
        gameState = cinematicState;
    }
    public void skipCinematic() {
        if(gameState == cinematicState) gameState = cinematicReturnState;
    }
    // Avvia l'animazione turning_pages verso una nuova combinazione bookindex/bookzone/bookpage
    // (direction: -1 = verso sinistra, +1 = verso destra). Ogni cambio di contenuto del libro
    // passa sempre da qui, mouse o tastiera che sia.
    private void startBookTransition(int newBookindex, int newBookzone, int newBookpage, int direction) {
        if(pageTurnActive) return; // non sovrapporre due turn insieme
        String path = direction < 0 ? "/cinematics/Turning_pages_right.gif" : "/cinematics/Turning_pages_left.gif";
        pageTurnPlayer.load(path, false);
        pageTurnActive = true;
        pendingBookindex = newBookindex;
        pendingBookzone = newBookzone;
        pendingBookpage = newBookpage;
    }
    // Voce (BookEntry) attualmente aperta, o null se l'area non ha ancora nessuna voce sbloccata
    // (lista vuota) o se bookzone punta fuori dai limiti. La UI mostra "niente sbloccato" in quel caso.
    public BookEntry getCurrentBookEntry() {
        if(bookindex == 0) return null;
        List<BookEntry> zones = unlockedBookZones.get(bookindex - 1);
        if(bookzone < 0 || bookzone >= zones.size()) return null;
        return zones.get(bookzone);
    }
    // Frecce LEFT/RIGHT: cambia pagina di dettaglio dentro alla voce corrente.
    // Ai bordi (prima/ultima pagina) non fa nulla, come un libro vero. Il numero di pagine è
    // quello della voce stessa (BookEntry.pages), non più una costante fissa uguale per tutte.
    public void turnBookPage(int direction) {
        BookEntry entry = getCurrentBookEntry();
        if(entry == null) return; // nessuna area aperta, o nessuna voce sbloccata: niente pagine da girare
        int newPage = bookpage + direction;
        if(newPage < 0 || newPage >= entry.pages.length) return;
        startBookTransition(bookindex, bookzone, newPage, direction);
    }
    // Click su un bookmark: salta all'area generale corrispondente (bookindex), resettando voce e pagina.
    public void selectBookIndex(int newBookindex) {
        if(newBookindex < 1 || newBookindex > ZONE_COUNT || newBookindex == bookindex) return;
        int direction = (bookindex == 0 || newBookindex > bookindex) ? 1 : -1;
        startBookTransition(newBookindex, 0, 0, direction);
    }
    // Click su una voce (bookzone) dentro all'area generale attiva: resta nella stessa area generale.
    // Il numero di voci è quello effettivamente sbloccato finora (unlockedBookZones), non una
    // costante fissa: una voce non sbloccata semplicemente non è nella lista, niente da selezionare.
    public void selectBookZone(int newBookzone) {
        if(bookindex == 0) return;
        int zoneCount = unlockedBookZones.get(bookindex - 1).size();
        if(newBookzone < 0 || newBookzone >= zoneCount || newBookzone == bookzone) return;
        int direction = (newBookzone > bookzone) ? 1 : -1;
        startBookTransition(bookindex, newBookzone, 0, direction);
    }
    // Tasti A/D in bookState: area generale precedente/successiva (clamp ai bordi, niente wrap,
    // come le pagine). Se nessuna area generale è ancora selezionata, D apre la prima e A l'ultima.
    public void cycleBookIndex(int direction) {
        int target = (bookindex == 0) ? (direction > 0 ? 1 : ZONE_COUNT) : bookindex + direction;
        if(target < 1 || target > ZONE_COUNT) return;
        selectBookIndex(target);
    }
    // Tasti W/S in bookState: voce precedente/successiva dentro all'area generale corrente.
    // Senza effetto se nessuna area generale è aperta (niente voci da cambiare).
    public void cycleBookZone(int direction) {
        if(bookindex == 0) return;
        int target = bookzone + direction;
        if(target < 0 || target >= unlockedBookZones.get(bookindex - 1).size()) return;
        selectBookZone(target);
    }
    // Chiude il libro e resetta la navigazione, così la prossima apertura riparte da zero.
    public void closeBook() {
        gameState = playState;
        bookindex = 0;
        bookzone = 0;
        bookpage = 0;
        pageTurnActive = false;
    }
    private void drawCinematic(Graphics2D g2) {
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