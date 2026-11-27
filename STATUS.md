# Abysson — Status del codice (2026-11-26, dopo 0.22.3 + giro 0.22.4)

Questo file va tenuto in `docs/STATUS.md` (o dove preferisci nel repo). Non è generato automaticamente: lo aggiorno io a mano ogni tanto, su richiesta, quando facciamo un blocco di modifiche significativo.

---

## 8. Dettagli per Claude (punto d'ingresso: leggere per primo)

*Scritto per una chat Claude che parte da zero, senza la cronologia. (Il vecchio §8 "Quest nel libro + notifiche" ora è §8b.) Il resto del file è il dettaglio per sistema: §0 workflow, §28 riferimento equipaggiamento/combattimento, §30-§36 lavoro recente.*

### 8.1 Il progetto e l'utente
- **Abysson**: RPG 2D in **Java Swing**, mondo a tile, combattimento a turni con sistema elementale; partito dal tutorial YouTube di RyiSnow ma con sistemi molto custom. Repo **github.com/LollyOwX/Abysson** (branch `main`). Sviluppatore indipendente, lavora da solo; versioni `0.19.x` (ultima pushata: **0.19.11**), commit tipo "0.19.11 Equip - UI".
- Ambiente dell'utente: **Windows + PowerShell**, **IntelliJ IDEA** (cartella locale `C:\Users\logan\Desktop\DD2D`), JDK 25 (`ms-25.0.3`), Python 3.14. Parla **italiano** (e inglese): rispondi in italiano salvo diversa indicazione. Testi di gioco: combattimento in inglese; libro, quest e UI in italiano.
- Moduli IntelliJ: `useless/DD2D.iml` (`res` = cartella Resources, `src` = sorgenti, output `out/production/DD2D`). `build.bat` compila soltanto (non copia le risorse).

### 8.2 Come vuole lavorare (preferenze esplicite dell'utente)
- **Spiega ogni modifica passo-passo**, non solo il risultato. Modifiche **circoscritte**: stessa struttura del codice, cambia il minimo.
- **File completi** quando le modifiche sono interdipendenti; solo i file davvero cambiati. **Diff riga per riga** per modifiche minime. Le modifiche **meccaniche che l'IDE fa da solo** (Move Class, rename) si spiegano a voce.
- **Commenti nel codice minimi**: solo di sezionamento (cosa fa quella sezione).
- **Chiedi conferma se hai dubbi, poi risolvi.** Una domanda alla volta, con i default che proporresti. **Non inventare valori di gameplay**: se manca un dato, proponi un default dichiarato e scrivilo come scelta tua.
- **Dichiara cosa hai verificato** (compilato, test numerici, render headless) e cosa no ("non provato in gioco").
- **Aggiorna questo STATUS.md** a ogni blocco significativo e **appunta sempre un ragionamento dopo che viene fatto** (regola esplicita dell'utente, vedi riga in §0).
- Alle domande di design/approccio ("come potrei fare?") rispondi con **snippet mirati, non codice completo**, e metti il ragionamento qui (§0, esempio §38).
- Formato risposte: prosa chiara, tabelle solo per dati confrontabili; niente liste quando rifiuti qualcosa.

### 8.3 Come lavorare tecnicamente (sandbox di Claude)
- **Clona il repo** ogni volta: `git clone https://github.com/LollyOwX/Abysson.git` (github.com è raggiungibile). La sandbox **si azzera tra un messaggio e l'altro**: riclona e reinstalla il JDK (`apt-get update; apt-get install -y openjdk-21-jdk-headless`; il progetto è Java 25 ma compila con 21).
- Compilare: `find src -name "*.java" > sources.txt; javac -d /tmp/out @sources.txt`. Il repo **contiene `out` e `bin/`** (output di build, stantii: ignorali, non fidarti di quello che c'è dentro).
- **`pp` è nel `.gitignore`**: gli script locali dell'utente (`Fix_Weapon_registry.py`, `convert_components.py`, `convert_sets_corda.py`, `generate_loot_index.py`, `apply_*.py`) **non sono su GitHub**: non li vedi. `generate_equip.py` (generatore dei registry dalla scheda Equip dell'xlsx, solo il primo foglio) **non è nel progetto**.
- **Test headless**: `java -Djava.awt.headless=true -cp out:res Classe` (la cartella `res` deve essere nel classpath, altrimenti `TileManager` lancia "Resource not found"). `new GamePanel()` funziona headless; per il libro: `gp.gameState = gp.bookState`, disegna su un `BufferedImage` con `gp.ui.book.draw(g2)`, simula i click con `gp.ui.book.handleClick(x, y)` (coordinate schermo), poi **guarda il PNG** con lo strumento di visualizzazione. PIL/numpy/scipy servono per misurare coordinate dalle immagini (template matching esatto sui pixel).
- **Come consegnare modifiche a file esistenti: script Python, non `.patch`.** I `.patch` sono falliti due volte con "corrupt patch": le righe di contesto fatte di un solo spazio vengono ripulite dagli editor. Si usano script `apply_<cosa>.py` con sostituzioni di testo **esatte**: ogni sostituzione verifica che il testo compaia **una volta sola**, riconosce "già applicato", **non scrive nulla se qualcosa non torna**, gestisce gli a-capo CRLF. L'utente li mette in `pp` e li lancia dalla root del progetto: `python pp/apply_x.py src` (poi *File > Reload All from Disk*, Ctrl+Alt+Y, e ricompila). Prima di consegnare: provali su una copia pulita del repo (LF e CRLF), rilancia per verificare l'idempotenza, compila. File **nuovi**: consegnali completi (anche in uno zip con la struttura `src/...`).
- Regola di sicurezza già costata un errore (§0): dopo ogni patch mirata rileggi il risultato.

### 8.4 Run, build e risorse (trappola già vissuta)
- In IntelliJ la run configuration deve avere **"Build project" in *Before launch***. Se è disattivato, la cartella di output (`out/production/DD2D`) non riceve le risorse nuove: il libro non disegnava **niente** (nemmeno la pagina iniziale) perché le immagini erano state spostate in `res/ui/book_general/` e `res/ui/book_inventory/` ma nel classpath c'erano solo i vecchi file piatti. In console compare `ERROR: resource not found: /ui/book_general/book.png`. Non era un bug del codice.
- `main/ResourceStreams` (ripiego opzionale su `res` con un AVVISO in console, applicato con `apply_resources.py`) esiste ma **non è necessario** se la build è corretta.

### 8.5 Mappa del codice
- `main`: `GamePanel` (loop, stati, libro: `bookindex/bookzone/bookpage`), `UI`, `KeyHandler`, `Button`, `PaletteSwap`, `GameCalendar`, `AssetSetter`. `entity`: `Entity`, `Player` (slot equip, `inventory`, `obtain()/receiveItem()`), NPC e mostri, `StatType`. `combat`: `CombatState`, `ElementSystem`, `Ability`, `CombatMath`, `SpecialEffect*`. `items`: `Item`, `Weapon`, `Armor`, `Jewelry`, registry generati (`WeaponRegistry`, `ShieldRegistry`, `ArmorRegistry`), `ComponentRegistry`, `DamageTypes`, `LootTable`/`LootIndex`, `Inventory` + `Consumable/Precious/QuestItem/Resource`, `ItemInfo`, `StatLine`. `book`: `BookUI`, `InventoryPage`. `quest`: `Quest*`. Risorse in `res` (`ui/`, `cinematics/`, ...).

### 8.6 Sintesi dei sistemi (dettagli nei § indicati)
- **Equipaggiamento** (§25-30): 1120 oggetti generati dalla scheda Equip (672 armi/scudi + 448 armature), rarità COMMON/HIGH_QUALITY/MASTERWORK/MYTHIC a scaglioni (ID 1..14 = fasce di livello 0-10 … 40-50). Ogni oggetto = **component** `nome[slot] = ID` risolti da `ComponentRegistry` per famiglia (MELEE, RANGED, SHIELD, ARMOR) → bonus percentuali su `Item.statBonusPercent`; la qualità (`affilatezza`/`rifiniture`, +25% per livello) scala **solo i bonus dell'oggetto**. `punta[0]` delle MAZZE = peso = danno diretto (Spadone: ATK + peso); `struttura[0]` degli archi = durabilità. `armorSetID` non esiste più: set con `upperID` (1 Guardia, 2 Veggente, 3 Voto), `lowerID` (1 Passo, 2 Ombra) + 2 `acceptedUpperIDs`; set valido se `Player.activeFullID() != 0` (combinazioni 11, 31, 22, 32).
- **Durabilità** (§30.3): 100 per tutti (archi: `struttura[0]`); si consuma per **tipologia** (`Item.tipologie`, `DamageTypes`); sotto il massimo armature/scudi perdono **solo la DIFESA** in proporzione; a 0 armi e Sai sono rotti e inutilizzabili, scudi e armature perdono i bonus ma **tengono gli ID** (i set restano attivi). Usura da parata: `Item.lose()`.
- **Loot** (§30.5): `LootTable.roll(livelloMostro)` — 50% stessa fascia, 35% inferiore, 15% superiore (mai oltre +10 livelli), poi più rara = meno probabile, pezzo equiprobabile. `LootIndex` è **generato** (rilanciare lo script quando cambiano i registry).
- **Formule** (§31, `CombatMath`, scala stat 0..1000): mitigazione `0,90 × DIF/(DIF + 20 + 8×livello attaccante)`; probabilità di colpire `precisione / (100 + elusione)`, poi i mancati ×1,25 (relativo).
- **Libro / Inventario** (§33-35, `InventoryPage`): 5 zone (bookzone 0 gear, 1 consumabili, 2 quest, 3 risorse, 4 preziosi), righe a sinistra (10 per pagina, click = selezione), dettagli a destra, tag di rarità + riquadro effetto per Masterwork/Mythic, calendario nella pagina Quest. `InventoryDemo.ENABLED = true` riempie l'inventario di dati di prova (metterlo a `false` per partire vuoti).
- **Oggetti ricevuti** (§35.2): `Player.obtain(item)` / `receiveItem(item, equipNow)` → inventario + evento `COLLECT` con `targetId` = **id del registry** (`Item.id`, impostato da `LootTable.create(id)`). `equip()/unequip()` spostano i pezzi tra slot e `inventory.gear`.

### 8.7 Trappole e cose facili da sbagliare
- `legamenti` ha significati diversi per famiglia (ARMOR → VELOCITA, RANGED → ATTACK, SHIELD → nessuna tabella ancora). `lama`, `sostegno` e `legamenti` degli scudi non hanno ancora bonus.
- `Item.durability` vale -1 finché non passa da `computeBonusPercent()` (equip/`addGear`); per l'usura di oggetti mai equipaggiati (es. l'arma di un mostro) usa `lose()`.
- La parata di oggi è del **mostro** che para l'attacco del Player; il sistema mostro → player non esiste ancora (spec in §5).
- Il Player ha `level` = 0: nelle formule conta come 1. Le stat base (DIF 20, ATK 40, PRE 100) sono molto sotto la scala 1000 e non crescono col livello.
- `statMultiplier` non è più popolato da nessun oggetto (resta per buff futuri).
- `BookUI.PAGE_CONTENT_RECT` (95,20,165,230) è un placeholder che cade **sopra** il libro: la pagina Quest vecchia ne risente; le coordinate vere sono in §34.3. Il "tronco" nelle immagini del libro è un'icona jolly provvisoria.
- Una quest `COLLECT` avanza solo se è `ACTIVE`. `out` e `bin/` non vanno modificati né letti come fonte di verità.
- Dopo ogni rigenerazione dei registry con `generate_equip.py` (scrive ancora il formato vecchio) vanno rilanciati, in ordine, gli script di conversione (§30.6) o va aggiornato il generatore.

### 8.8 Decisioni ancora aperte (da chiedere all'utente, non da assumere)
- **Calendario**: minuti reali per giorno, mesi per stagione (3 o 1), se combattimento/menu/dialoghi consumano tempo, salto del tempo (§32, §35.5).
- **RNG dei tiri di caratteristica**: Marble-sack o Equilibratore (o nessuno dei due), nulla deciso né implementato (§36).
- Bonus di `lama`, `sostegno`, `legamenti` di armi/scudi; uno Scudo/Broquel a durabilità 0 deve ancora parare? (oggi sì; il Sai no).
- `PRECISION_BASE_MAX` (oggi 100), crescita delle stat per livello, `dodgeChance` del mostro (usa ancora la vecchia sottrazione), caso "precisione/2" del sistema mostro → player.
- Peso del gear (esiste solo per le MAZZE), icone/immagini dettagliate degli oggetti (`itemPath`, `combatItemPath`: nessuno le imposta), equipaggiare/usare dalla lista dell'Inventario, rimuovere `out` e `bin/` da git.

### 8.9 Come iniziare una sessione
1. Chiedi/leggi la versione pushata, **clona** e compila. 2. Leggi questo §8, poi §0 e i § recenti (§30-§36). 3. Per ogni richiesta: riproduci o verifica, modifica nello stile esistente, **testa** (compilazione, test numerici o render headless), consegna file/script con istruzioni passo-passo, aggiorna lo STATUS. 4. Quando cambi qualcosa che questo punto descrive, aggiorna anche qui.

---

## 0. Come lavoriamo (workflow)

- **Spiego sempre ogni modifica passo-passo**, non solo il risultato.
- **Modifiche circoscritte**: tocco solo quello che serve, mantengo la struttura esistente il più possibile.
- **File completi vs. spiegazione a voce** — differenziare in base a cosa serve davvero:
    - Se la modifica è **meccanica e un IDE la fa da solo** (es. IntelliJ *Refactor → Move Class* per uno spostamento di package, un rename, un'estrazione automatica) → **spiego a voce cosa fare**, non rigenero/incollo file interi.
    - Se la modifica è **logica** (nuovo comportamento, bugfix, refactor che tocca più punti non meccanici) → do i **file completi**, perché lì serve vedere il codice risultante.
    - *Esempio concreto (19/07)*: spostamento di `CombatState`/`ElementSystem`/`Ability`/`Reaction` in un package `combat` — bastava dire "usa Refactor → Move Class in IntelliJ, aggiorna da solo gli import", invece ho rigenerato e incollato 5 file interi consumando risorse inutilmente. Da evitare.
- Commenti nel codice: minimi, solo di **sezionamento** (cosa fa quella sezione), niente spiegazioni riga-per-riga — quelle restano in chat.
- **Verificare sempre il file dopo un patch mirato** (sostituzione di poche righe, non il file intero): il 19/07, durante l'aggiunta di `drawBook()`, una sostituzione ha cancellato per errore la riga della firma di `playMusic()` (il testo da sostituire matchava anche quella riga adiacente). Individuato e corretto subito controllando il file dopo l'edit — ma è un promemoria a **ricontrollare il risultato di ogni patch puntuale**, non solo il bilanciamento delle graffe, prima di consegnare.
- **Domande di design ("come potrei fare…?")** (regola dell'utente, 06/11): rispondo con **spiegazione + snippet mirati, mai il codice completo**, e riporto il ragionamento in questo STATUS.md. Il codice completo arriva solo quando l'utente chiede di implementare. Esempio: §38.
- IMPORTANTE: ricordare SEMPRE di appuntare qui un ragionamento dopo che viene effettuato. Esempio calendario

## 1. Architettura generale

- **Java Swing**, game loop standard (thread + `repaint()`), mondo a tile.
- Classi core: `GamePanel` (loop, stato di gioco, array entità), `TileManager`, `Entity` (classe base), `Player`, NPC/monster che estendono `Entity`.
- Stati di gioco (`gp.gameState`): `titleState`, `playState`, `pauseState`, `dialogueState`, `combatState`, `cinematicState` (cinematic GIF, implementato il 19/07 — vedi §2).
- Segui il tutorial YouTube di RyiSnow come base, ma con sistemi custom molto oltre lo scope del tutorial (combattimento a turni, sistema elementale, palette swap, menu animati).
- **Package `combat`** (nuovo, 19/07): `CombatState`, `ElementSystem`, `Ability`, `Reaction` vivono ora in `src/combat`, non più in `src/main/`.

## 2. Sistemi principali

| Sistema | File | Note |
|---|---|---|
| Combattimento a turni | `combat/CombatState.java` | Innescato da collisione, menu Attack/Ability/Inventory/Minimap/Flee, navigabile W/S/ENTER/ESC. Testi tutti in inglese (tradotti da IT il 18/07). **04/08, cambio importante**: `dealDamage(attacker, target, abilityId)` accentra la logica di danno prima duplicata quasi identica in `playerUseAbility()`/`monsterTurn()` (precisione/schivata, moltiplicatore elementale, reazione, effetti attivi, contraccolpo Folgore/Infiammazione su un fallimento). **Cambio di comportamento voluto**: prima solo il mostro poteva mancare un colpo (il giocatore colpiva sempre) — unificando la formula, ora si applica a entrambi allo stesso modo. **Ordine dei turni a velocità**: `decideTurnOrder()` confronta `player.speed`/`monster.speed` a inizio di OGNI round (non solo all'inizio del combattimento — `startCombat()` e `advanceRound()` la richiamano entrambi), quindi un cambio di velocità a metà scontro (es. un debuff) si riflette dal round successivo; a parità va il giocatore, come nel comportamento precedente. **Stordimento (`STUN`) ha finalmente un effetto**: prima esisteva come `StatusEffect` ma non veniva controllato da nessuna parte — ora chi è stordito salta il turno da solo (`isStunned()`, controllato in `update()` per il giocatore e in testa a `monsterTurn()` per il mostro), e tickano i suoi effetti attivi in quel momento (altrimenti la durata dello stordimento non scenderebbe mai). **Coda azioni** (`queueAction()`/`actionQueue`): i messaggi di combattimento non finiscono più subito in `combatMessage`, vengono accodati e mostrati uno alla volta da `update()` quando `messageTimer` torna a 0 — `onVictory()`/`onDefeat()` sono stati convertiti alla coda per lo stesso motivo (altrimenti il messaggio di vittoria/sconfitta comparirebbe PRIMA di quello del colpo che l'ha causata). `tryFlee()` non è stato toccato (nessun messaggio in coda prima del suo, nessun problema di ordine). |
| Elementi & status | `combat/ElementSystem.java` | Enum `Element` (PHYSICAL/LIGHT/FIRE/WATER/EARTH/WIND/ELECTRIC), `StatusEffect` (28 effetti), `ActiveEffect`, tabella reazioni 7×7. **Nomi/displayName ancora in italiano** (Raggio, Folgore, Infiammazione, Rottura, Potenziamento, Scossa, Abrasione) — non tradotti, vedi TODO. |
| Abilità | `combat/Ability.java` | Dispatcher statico, facile da estendere (vedi §4). |
| Palette swap | `main/PaletteSwap.java` | Remapping colore-esatto via `getRGB`/`setRGB`, cachato per (chiave, immagine). Formato compatto stringa: `"RRGGBB>RRGGBB,..."`, parsato da `PaletteSwap.parsePalette()`. |
| Menu titolo | `main/UI.java` (`drawMenuItems`) | Un solo metodo condiviso usato da tutti e 3 gli schermi (main menu, classe, difficoltà): slide-in+stagger, float, hover scale/offset/dimming/glow (mouse **o** tastiera, unificati), punch al confirm, delay di 1s prima di eseguire il comando. Glow = sottolineatura `res/ui/menu_hover_glow.png` (81×9, larga e sottile) che cresce in **larghezza** da 0 al pieno, centrata sotto la voce — sistemato il 19/07 (cresceva in altezza per errore, quasi invisibile). |
| Setup entità | `main/AssetSetter.java` | Helper `place(array, index, factory, col, row, paletteDef)` — una riga per entità invece di 3-4 righe separate. |
| Debug colori sprite | `main/ColorDump.java` | Utility standalone (`main()` con path PNG come argomenti) per stampare i colori ARGB unici di uno sprite — serve per scrivere palette corrette. |
| Cinematic (GIF) | `main/GifPlayer.java`, `GamePanel.playCinematic()` | Decodifica un GIF con compositing corretto (gestisce i "disposal method" per frame, non solo il caso banale). `playCinematic(path)` carica da classpath (`res/cinematics/...`), ricorda lo stato da cui arrivi e ci torna da solo a fine riproduzione; overload `(path, loop)` per il loop, `(path, loop, nextState)` per atterrare su uno stato diverso da quello di partenza (usato per il libro, vedi sotto). ENTER/ESC durante la cinematic la saltano (`GamePanel.skipCinematic()`). `paintComponent()` disegna prima lo sfondo di destinazione (`cinematicReturnState`: mondo/titolo/libro) e la cinematic sopra — così la vera trasparenza del GIF rivela quello sfondo invece del nero di base del pannello (fix 19/07). |
| Libro: apertura/pagine | `GamePanel.bookState`, `UI.drawBookScreen()` | Tasto **I** in gioco → cinematic `Open_book.gif` → atterra su `bookState`. Sfondo disegnato con **aspect ratio corretto** (scala uniforme `Math.min(screenW/imgW, screenH/imgH)` + centratura, stessa formula già usata per le cinematic) — non stirato a `screenWidth×screenHeight` (bug fixato in una sessione precedente). **ESC/I** → `GamePanel.closeBook()` (richiude e resetta `bookindex/bookzone/bookpage` a 0). |
| Libro: navigazione a 3 livelli | `GamePanel` (`bookindex`/`bookzone`/`bookpage`, costanti `bookindex_map=1`…`bookindex_inventory=6`, `selectBookIndex()`, `selectBookZone()`, `turnBookPage()`, `cycleBookIndex()`, `cycleBookZone()`, `startBookTransition()`) | Progettata il 28/07 nello stesso stile di `gameState` (costanti nominate + variabile), **scelta esplicita al posto di enum+stack**: la profondità di navigazione è fissa a 3 livelli (mai variabile), quindi uno stack non aggiungerebbe nulla, solo cerimonia. **`bookindex`** = area generale (bookmark: 6 costanti nominate `bookindex_map=1` … `bookindex_inventory=6`, valore `0` = nessuna selezionata, array indicizzati con `bookindex - 1`). **`bookzone`** = sottoargomento dentro l'area (0-based come `bookpage`, ma **`-1` = nessuno ancora selezionato** — vedi bug fixato in §3, 0 è un valore valido quindi non può fare anche da sentinella), **`bookpage`** = pagina di dettaglio dentro al sottoargomento (0-based, resettato a `0` — qui non serve una sentinella diversa perché non c'è ancora nessun pulsante "salta a pagina X" con lo stesso problema di `selectBookZone`). **02/08: tolto il modello dati `BookEntry`/`unlockedBookZones`** (era arrivato al 29/07, vedi versioni precedenti di questo file) insieme a tutti gli stub. **`MICRO_PAGE_COUNT[]` e `BOOKZONE_COUNT[]` sono entrambi array per area** (indice `bookindex-1`) — **03/08: numeri diversi per area** finalmente decisi: `BOOKZONE_COUNT = {3, 2, 5, 2, 3, 5}` (map, quests, skills, calendar, bestiary, inventory — quest non specificata esplicitamente, lasciata a 2). Attenzione a tenerli sincronizzati con quanti `Rectangle` ci sono in `UI.SUBTOPIC_IMAGE_RECTS` per la stessa area. (Nota: nella stessa giornata `BOOKZONE_COUNT` era stato temporaneamente reso 1-based/flat, poi la scelta è stata annullata — vedi commit precedenti se serve la cronologia esatta.) Il file `BookEntry.java` resta nel progetto ma non è più referenziato da nessuna parte — per quando si riprenderà il contenuto vero. Ogni cambio passa da `startBookTransition()`, che innesca **sempre** l'animazione `Turning_pages_left/right.gif`. Sfondo: `book.png` se `bookindex == 0`, altrimenti `book_X.png` dell'area attiva (bookmark evidenziato già dentro l'immagine). **Pagina completamente vuota di contenuto vero** — nessun testo, nessun rettangolo di debug: i pulsanti sono invisibili come i bookmark (il feedback di un cambio riuscito è solo l'animazione voltapagina). |
| Libro: pulsanti (bookmark + sottoargomenti) | `main/Button.java`, `UI.java` (`BOOKMARK_IMAGE_RECTS`, `bookmarkButtons[]`, `SUBTOPIC_IMAGE_RECTS`, `subtopicButtons[][]`) | `Button.java` (aggiunto il 01/08): area (`Rectangle bounds`) + azione (`Runnable onClick`), riusabile ovunque serva un bottone — `setBounds()` va richiamato ogni frame (dipende dallo scaling del libro), `click(x, y)` esegue l'azione se il punto è dentro ai bounds. **Bookmark**: 6 `Button`, sempre attivi, posizioni in `BOOKMARK_IMAGE_RECTS` **corrette a mano dall'utente il 01/08** dopo averle misurate a occhio in un editor di immagini. **Sottoargomenti (bookzone), aggiunti il 02/08**: `SUBTOPIC_IMAGE_RECTS`/`subtopicButtons` sono array **per area** (indice esterno = `bookindex - 1`, indice interno `z` = `bookzone`, 0-based) — un numero diverso di pulsanti placeholder per area (vedi riga sopra per i conteggi), impilati verticalmente da y=95 (sotto alla fascia dei bookmark, y≈70-85, per non sovrapporli). Attivi **solo quando quell'area è quella effettivamente aperta** (`gp.bookindex - 1 == area`): per le aree non attive i bounds vengono azzerati (`null`) ogni frame in `drawBookScreen()`, così `Button.click()` li ignora automaticamente. **03/08: nessun rettangolo disegnato** — un tentativo di feedback visivo di debug (contorni colorati) è stato provato e rimosso lo stesso giorno: **il problema reale non erano i pulsanti** (funzionavano, cambiavano `bookzone` correttamente) **ma l'animazione voltapagina che non si vedeva partire** — quella resta la cosa da verificare se "non sembra succedere niente" cliccando. Posizioni ancora placeholder, da aggiustare a vista come già fatto per i bookmark. |
| Libro: input — mouse + tastiera | `GamePanel` (`cycleBookIndex()`, `cycleBookZone()`), `KeyHandler` (ramo `bookState`), `UI` (`handleBookClick()`) | Click su un bookmark o su un pulsante sottoargomento (quando visibile) = `selectBookIndex()`/`selectBookZone()` tramite `Button.click()`. Da tastiera: **A/D** = area generale precedente/successiva (`cycleBookIndex`, clamp ai bordi come le pagine, apre la prima/ultima se nessuna è ancora selezionata), **W/S** = sottoargomento precedente/successivo dentro all'area corrente (`cycleBookZone`, limite `BOOKZONE_COUNT[bookindex-1]`, diverso per area), **LEFT/RIGHT** = pagina (`turnBookPage`, limite `MICRO_PAGE_COUNT[bookindex-1]`, diverso per area). Tutti passano da `startBookTransition()`, quindi qualunque cambio (mouse o tastiera) fa sempre l'animazione voltapagina — **verificare che quell'animazione parta davvero a schermo**, era la causa reale di "sembra non funzionare" (vedi riga sopra). **ESC chiude tutto il libro in un colpo solo** (non risale di un livello alla volta) — nota, non ancora richiesto esplicitamente. |
| Tile mappa: terreno vs speciali | `tile/SpecialTile.java`, `tile/TileManager.java` (`SPECIAL_TILE_BASE`, `loadSpecialTilesUsedInMap()`) | Progettata il 30/07 (discussa con `/llm-council`), applicata al codice il 01/08 dopo il fix del repository — verificata identica al design originale, zero conflitti con la 0.13.8 (`getTileImage()`/`draw()`/`setup()` invariati nel frattempo). **Terreno** (ID 0-25): invariato, eager-load in `getTileImage()` come sempre. **Speciali** (ID ≥ `SPECIAL_TILE_BASE=100`, arredi one-off tipo vasi/decorazioni): enum `SpecialTile` con nome leggibile + `collision`, ID = `SPECIAL_TILE_BASE` + ordinale (l'ordine in cui la voce compare nell'enum) — aggiungere un tile nuovo è una riga sola, mai un numero da tenere a mente. Sezionato a commenti per luogo (`// Casa 1`, `// Casa 2`...) invece di una lista piatta, per restare leggibile quando cresce. Caricamento **lazy**: `loadSpecialTilesUsedInMap()` scansiona `mapTileNum` subito dopo averlo letto in `loadMap()` e chiama `setup()` solo per gli ID usati davvero in quella mappa (mai dentro `draw()` — un `ImageIO.read` lì dentro bloccherebbe il frame, stutter visibile proprio quando il player si avvicina a qualcosa di nuovo). Guard: un ID ≥ `SPECIAL_TILE_BASE` senza voce `SpecialTile` corrispondente stampa un errore chiaro invece di andare in crash o disegnare silenziosamente niente. `tile[]` alzato da 100 a 300 slot (temporaneo, da alzare ancora se `SpecialTile` cresce molto). `mapTileNum`/i `.txt` restano `int[][]` invariati, nessuna migrazione dati richiesta. |

## 3. Cose da ricordare (bug pattern ricorrenti + decisioni prese)

- **Direzione di default per entità statiche**: `Entity.draw()` usa `"down"` come fallback (non più `"idle_down"`) quando `direction`/`idleDirection` sono `null` — perché gli oggetti statici (Door, Key, Chest, Boots) settano solo `down1`, mai `downIdle1/2`. Se aggiungi un nuovo oggetto statico, **niente panico**: eredita questo fix automaticamente.
- **Palette swap**: va applicato ad ogni punto che disegna un'immagine, non solo in `Entity.draw()`. Punti attualmente coperti: `Entity.draw()` (ramo normale), `Entity.dyingAnimation()` (blink/morte), `CombatState.drawMonster()`. Se aggiungi un nuovo punto di disegno custom per un'entità con palette, ricorda di applicare `PaletteSwap.getOrCreate(...)` anche lì.
- **Key/Door segnalati come "non funzionanti"** nonostante il fix della direzione di default — da verificare a fondo (vedi TODO), non richiudere la questione finché non è confermato visivamente in gioco.
- **I colori della palette devono combaciare esattamente (bit a bit)** con i pixel reali dello sprite — usa `ColorDump.java` per leggerli, non inventarli.
- **Asset del glow del menu**: è largo e sottile (81×9), non stretto e alto — se lo rifai/sostituisci, ricorda che il codice cresce in **larghezza**, non altezza.
- **Stat init order**: livello va inizializzato prima delle stat derivate (bug ricorrente in passato).
- **Zoom** (`GamePanel.zoomInOut`): richiede il ricaricamento completo delle immagini (tile + player + NPC con `getImage()`), non un semplice scale a runtime. Dalla 0.22.4 lo zoom aggiorna anche posizione e area di collisione di NPC/mostri/oggetti, lo sprite del player e i rettangoli degli eventi (§44.2).
- **Wrap navigazione menu**: main menu (stato 0) blocca ai bordi (0..3, niente wrap); classe/difficoltà (stati 1/2) fanno wrap risalendo da 0 a 3 ma non ridiscendendo da 3 a 0 — asimmetria del codice originale, mantenuta di proposito per non cambiare comportamento esistente senza che fosse richiesto.
- **Sentinella "nessuno selezionato" che collide con un valore valido** (bug del 04/08, libro): `bookzone` partiva a `0` sia come default/reset sia come primo sottoargomento valido — cliccare il primo pulsante di un'area chiamava `selectBookZone(0)`, che veniva scartato dal controllo "è diverso da quello attuale?" perché `0 == 0`. Sistemato usando `-1` come sentinella (bookzone è 0-based, quindi 0 è un valore reale — a differenza di `bookindex`, che parte da 1 e può usare `0` come "nessuno" senza conflitti). **Regola generale**: quando un campo ha sia un range di valori validi che parte da 0 SIA bisogno di un valore "non ancora impostato", il sentinella deve stare FUORI dal range valido (es. `-1`), non dentro.

## 4. Come si fa per… (guide rapide)

### Aggiungere un nuovo mostro
1. Crea `monster/MON_NomeMostro.java` che estende `Entity` (vedi `MON_Goblin.java` come riferimento) — costruttore prende solo `GamePanel gp`.
2. In `AssetSetter.setMonster()`, aggiungi una riga:
   ```java
   place(gp.monster, INDEX, () -> new MON_NomeMostro(gp), colonna, riga, null);
   ```
3. Se vuoi una variante di colore, sostituisci `null` con una stringa palette (usa `ColorDump` sullo sprite per i colori esatti).

### Aggiungere un nuovo effetto testuale (tipo `<shake>`, `<rainbow>`)
In `UI.java`, dentro `drawSegmentWord()`:
1. Aggiungi un nuovo `case "nometag":` nello `switch(tag)` che calcola `offX`/`offY` (o il colore) in funzione di `textAnimTick`.
2. Se il tag è puramente di colore (niente animazione posizionale), va invece in `applyTagStyle()`.
3. Aggiorna il commento della lista tag supportati sopra `drawStyledText()`.

### Aggiungere un nuovo menu (con gli stessi effetti di quelli esistenti)
In `UI.java`:
1. Aggiungi un nuovo `titleScreenState` (es. `3`) e il relativo ramo in `drawTitleScreen()`.
2. Calcola `String[] items` e `int[] itemYs`, poi chiama `drawMenuItems(items, itemYs, fontSize)` — ottieni automaticamente slide-in/stagger/float/hover/dimming/glow/punch.
3. Aggiungi la logica di esecuzione comando nel `switch(screen)` dentro `updatePendingMainMenuCommand()`.
4. In `KeyHandler.java`, il blocco W/S/ENTER per `titleState` è già generico — non serve toccarlo, a meno che il nuovo schermo non abbia un numero diverso di voci (allora serve una `commandNum` clamp/wrap dedicata).

### Aggiungere un palette swap a un'entità
1. `ColorDump.java` sullo sprite reale → leggi i colori esatti.
2. In `AssetSetter`, passa la stringa palette a `place(...)`: `"vecchio1>nuovo1,vecchio2>nuovo2"`.
3. Verifica che il punto di disegno dell'entità applichi `PaletteSwap.getOrCreate(...)` (vedi §3 sopra per i punti già coperti).

### Spostare classi in un nuovo package (come `combat/`)
Se è **solo** uno spostamento (nessuna modifica di logica): usa IntelliJ **Refactor → Move Class...** sulle classi interessate — aggiorna da solo tutti gli import nel progetto. Non serve chiedere file interi per questo, basta chiedere conferma che non ci siano effetti collaterali (vedi §0).

### Riprodurre una cinematic (GIF)
1. Metti il file in `res/cinematics/nome.gif`.
2. Da qualunque punto del codice: `gp.playCinematic("/cinematics/nome.gif");` (one-shot) oppure `gp.playCinematic("/cinematics/nome.gif", true)` per farla ripetere in loop.
3. Torna automaticamente allo stato di gioco da cui era partita quando finisce (o quando il giocatore preme ENTER/ESC per saltarla).

### Aggiungere un nuovo stile di testo
Nuovo colore (es. <purple>)
Solo una riga in applyTagStyle() in UI.java:
javacase "purple": g2.setColor(new Color(180, 80, 255)); break;
Poi usi <purple>testo</purple> nei dialoghi.

Nuova animazione (es. <flash> — testo che lampeggia)
Un nuovo case in drawSegmentWord(), dentro il blocco switch (tag):
javacase "flash":
// visibile e invisibile ogni 15 frame
if ((textAnimTick / 15) % 2 == 0) {
g2.setColor(orig);
} else {
g2.setColor(new Color(0, 0, 0, 0)); // trasparente
}
break;
E poi aggiungi "flash" alla condizione dell'if che decide se processare carattere per carattere:
javaif (tag.equals("shake") || tag.equals("wave") || tag.equals("rainbow") || tag.equals("flash")) {

Regola generale

Colore statico → solo applyTagStyle()
Animazione o effetto per carattere → drawSegmentWord() + aggiungi il tag nell'if
Non serve toccare il parser — riconosce automaticamente qualsiasi tag scritto nel formato <nome>testo</nome>

### Calendar System
1. Critico (blocca progressione, esposizione di lore/zone essenziali per capire il gioco)
   → Mai davvero perdibile. O non è gated dal calendario affatto, o — se narrativamente ha senso che sia legato a un momento — ha un fallback diegetico: se il giocatore non si presenta nella finestra, un NPC lo raggiunge dopo, o l'evento si "riprogramma" al ciclo successivo con una spiegazione in-fiction (es. "il rituale è stato rimandato per il maltempo"). Il giocatore non deve mai sapere che ha "fallito una finestra" — il gioco si adatta silenziosamente.
2. Maggiore (quest sostanziose con lore/estetica/aree importanti ma non bloccanti)
   → Finestre generose e contenuto ciclico dove possibile. Se è legato a una stagione, fallo accadere ogni volta che quella stagione ricorre (con variazioni minori per non farlo sembrare uguale), non una tantum. Questo trasforma "l'ho perso per sempre" in "lo rivedrò tra una stagione" — elimina l'ansia per costruzione, non per avviso.
3. Flavor (colore, atmosfera, easter egg, ricompensa per l'esplorazione attenta)
   → Genuinamente perdibile, senza compromessi. Qui la perdibilità è la caratteristica, non il bug — è quello che rende il mondo vivo e premia chi esplora con attenzione. Nessun rimpianto strutturale perché non è mai stato presentato come "importante".
   Per il "non rompere la quarta parete": sostituisci ogni avviso UI con segnali diegetici in-mondo — un cantastorie/banditore in piazza che annuncia eventi imminenti, un tabellone degli annunci nel villaggio, dialoghi NPC che cambiano progressivamente man mano che una finestra si avvicina ("si dice che la festa sia vicina..." → "è domani!"), o un diario/taccuino del personaggio che si aggiorna da solo con le voci/rumor che il giocatore ha sentito in giro. Chi esplora e parla con la gente ottiene il promemoria nel mondo; chi non lo fa, semplicemente non lo sa — che è esattamente la meccanica che vuoi (l'esplorazione viene premiata, non è un tutorial forzato).

---

## 5. TODO aperti

- [x] ~~"effetti attivi" post-turno ticchettano sul BERSAGLIO quando viene colpito~~ — risolto il 27/08: `CombatState.beforeTurn()`/`afterTurn()` ticchettano ogni entità sul PROPRIO turno (vedi §6).
- [x] ~~Aggiungere tutti gli effetti delle reazioni~~ — fatto il 27/08, tutte e 26 implementate (vedi §6). Restano STUB dichiarati solo Illuminazione/Vaporizzazione (nessun sistema di occultamento/volo nel gioco).
- [ ] `queueAction()`/`actionQueue` è una coda semplice (`ArrayDeque<String>`, un messaggio testuale alla volta) — se in futuro serve accodare azioni più ricche (non solo testo: es. un'animazione per messaggio, o più colpi con dati diversi ciascuno), andrà esteso oltre la semplice stringa.
- [x] Tradurre in inglese `ElementSystem.java` (nomi `Element`/`StatusEffect`/`displayName`) e `Reaction.java` — al momento restano in italiano nonostante `CombatState.java` sia stato tradotto, quindi si vedono nomi come "Raggio"/"Folgore" mescolati a testo inglese. — **fatto 0.22.4 (§44.6)**
- [ ] `commandNum == 1` ("Load Game") nel main menu è ancora uno stub (`/* ADD LATER */`) — nessun salvataggio implementato.
- [ ] `Options` (main menu) è uno stub — nessuna schermata opzioni.
- [ ] `Inventory` e `Minimap` in combattimento sono placeholder (mostrano solo un messaggio).
- [ ] Il glow PNG per l'hover del menu va creato/importato — fatto ma non funzionante
- [ ] Verificare che tutti gli altri oggetti statici (Chest, Boots) siano effettivamente istanziati in `AssetSetter` — solo Key e Door sono attivi al momento, **ma segnalati come non funzionanti**: da investigare a fondo (il fix della direzione di default dovrebbe averli sbloccati, ma va confermato in gioco).
- [ ] Contenuto vero delle pagine del libro: tolto tutto (modello dati `BookEntry`/`unlockedBookZones`, testo placeholder) il 02/08 — da riprogettare da zero quando si è pronti, probabilmente ripartendo da `BookEntry.java` (rimasto nel progetto, non referenziato) o da un approccio diverso.
- [x] **Da confermare/bilanciare (27/08)**: `Ability.isRanged()` classifica Thunderbolt/AcquaJet/Lightray come "a distanza" e il resto come mischia — assunzione mia, il gioco non aveva finora questa nozione; verifica che rispecchi il design voluto (usata da Accecamento/Polverizzazione/Deviazione). — **fatto 0.22.4: ora è un tag, §44.5**
- [ ] **Da bilanciare (27/08)**: durata iniziale della Bruciatura Grave da Esplosione fissata a 5 (valore arbitrario, la formula del danno/rimozione dipende da questo numero — vedi §6). Item.percentBonus dei pezzi esistenti (es. `Sword_Basic_Iron`: +3/-1) ora significano percentuali, non più flat — stesso numero, effetto diverso, da ritarare quando ci sono più item con cui confrontare.
- [ ] **Correzione stessa sessione (27/08)**: ElementoATK/ElementDEF era stato inizialmente accorpato in una sola coppia di stat per tutti gli elementi — sbagliato, era imprecisione mia. Sistemato subito dopo in una coppia ATK/DEF PER ELEMENTO (FireATK/FireDEF...LightATK/LightDEF, vedi §6.5). Le basi del player partono tutte uguali (40/20 su ognuna) solo perché non c'è ancora differenziazione da equip/level design — da rivedere quando ci sarà.
- [ ] Posizioni placeholder dei pulsanti sottoargomento (`SUBTOPIC_IMAGE_RECTS` in `UI.java`) da aggiustare a vista, stesso procedimento già fatto per i bookmark (aprire `book_X.png` in un editor di immagini, leggere le coordinate pixel).
- [x] `res/maps/world2.txt` esiste ma non è caricato da nessun codice (nessun `loadMap("/maps/world2.txt")` in giro) — usa anche ID tile 26-39, oltre il terreno definito (0-25) e sotto `SPECIAL_TILE_BASE=100`. Se in futuro verrà caricata andrà sistemata (o quegli ID vanno rimappati, o vanno definiti come terreno aggiuntivo in `getTileImage()`). — **fatto 26/11: file cancellato**
- [ ] ESC nel libro chiude tutto in un colpo solo, non risale di un livello alla volta (pagina→voce→area→chiuso) — possibile miglioramento futuro, non richiesto esplicitamente.
- [ ] Effetti passivi come invisibilità fuori combat — `SpecialEffect`/`CombatState.firePreTurnEffects()`/`firePostTurnEffects()` (vedi §24) coprono solo il ciclo di turno DENTRO un combattimento. Alcuni effetti PASSIVI dovranno valere anche fuori (es. un'invisibilità che ti nasconde dai mostri sulla mappa) — nessun aggancio esiste ancora per quel caso, da decidere quando serve davvero (dipende da come funziona il rilevamento fuori combattimento, non ancora implementato).

---

*Fine status — prossimo aggiornamento quando accumuliamo un altro blocco di modifiche.*
## 6. Sessione 27/08 — Sistema stat/equip, azioni pre/post turno, reazioni complete

Blocco di 4 modifiche correlate, spiegate a fondo in chat lo stesso giorno.

### 6.1 Azioni tra i turni (pre/post-azione)
Nuovi hook generici in `CombatState`: `beforeTurn(actor, isPlayer)` (richiamato prima che l'attore agisca — ritorna `true` se il turno va saltato, es. Stordimento) e `afterTurn(actor, isPlayer)` (richiamato dopo — ticka gli effetti attivi del portatore, `ElementSystem.processTurnEffects()`). Prima lo stordimento era un caso speciale duplicato in `update()` e in testa a `monsterTurn()`; ora è solo il primo utilizzatore di un meccanismo generico — un futuro "controllo prima di agire" o "tick dopo aver agito" va aggiunto qui, non duplicato nei punti di ingresso del turno. `preTurnChecked` (bool) evita che il 50% di skip di Stordimento venga rivalutato ad ogni frame mentre si aspetta l'input del giocatore.

### 6.2 Stat: calcStat + equip percentuale
9 statistiche (`entity/StatType.java`): Vita, Attack, ElementoATK, Difesa, ElementDEF, Velocità, Elusione, Precisione, Efficienza. Aggiunte alle esistenti: `elementAttack`/`elementDefense`/`efficiency` su `Entity`. Formula: **stat finale = base flat (in `Player`, `baseX`) × (1 + bonus%/100)**. `Player.calcStat(stat, bonus, statChanged)` — statico, decide se sommare o sottrarre in base allo stato dello SLOT: `Player.EquipSlot` (uno per `MainHand`/`OffHand`/`Chestplate`) porta il proprio `statChanged` privato. **Nota sulla pseudocode originale**: il toggle di `statChanged` va fatto UNA volta per slot dopo aver richiamato `calcStat` per ogni sua statistica non-zero, non ad ogni singola chiamata — altrimenti un item con più stat alternerebbe somma e sottrazione invece di applicarle tutte insieme (bug nella bozza fornita, corretto qui). `Item.statBonusPercent` (Map<StatType,Integer>) sostituisce i vecchi campi flat: stessi numeri di prima (`Sword_Basic_Iron`: +3/-1) ma ora significano percentuali. Corretto anche un bug preesistente: `equip()` non gestiva davvero `OffHand` (finiva sempre nel ramo Chestplate).

### 6.3 DealDmg esteso + SpecialAction (stub)
`Ability.use()` ora sceglie da solo Attack/Difesa fisici o ElementoATK/ElementDEF in base all'elemento dell'abilità (`Ability.isElemental()`) — prima tutte le abilità, incluse quelle elementali, usavano sempre Attack/Difesa fisici. Corretto di riflesso un bug di `Lightray` (formula invertita: usava `target.defense*2 - user.attack`). Nuovo `combat/SpecialAction.java` (interfaccia, eseguita da `dealDamage()` dopo il danno se non null) + `Ability.getSpecialAction(id)` — **STUB**, nessuna abilità ne ha ancora una. Qualunque nuova abilità, anche senza danno diretto, deve comunque passare da `dealDamage()` per far scattare reazioni/disarmo/SpecialAction.

### 6.4 Tutte le 26 reazioni implementate
`Reaction.java` riscritta con un `Builder` (supporta secondo effetto simultaneo, riapplicazione elemento, disarmo — impossibile con i 6 campi posizionali di prima). Tabella completa in `ElementSystem.getReaction()`. Novità/fix degni di nota:
- **Esplosione**: ora applica DUE effetti (Stordimento 3 + Bruciatura Grave, nuovo `StatusEffect`) invece di un solo marcatore.
- **Resistenza**: bloccava TUTTI i nuovi effetti; ora blocca solo quelli positivi (bug fix, coerente con la descrizione "impossibilità di ricevere nuovi effetti positivi").
- **Abrasione**: bonificava (per errore) il danno FIRE subito dal portatore; ora bonifica le SUE azioni FIRE in uscita (coerente con l'essere classificata come effetto positivo).
- **Folgore/Infiammazione**: da danno fisso (1d8/1d6) a `10 * ElementoATK` di chi fallisce l'azione.
- **Raggio**: da danno fisso a `5 * ElementoATK` dell'attaccante.
- **Inondazione**: ora disarma davvero (`Player.unequip(MainHand)`) invece di limitarsi a un marcatore.
- **Stordimento/Accecamento/Deviazione**: penalità di mira centralizzate in `ElementSystem.precisionMultiplier()`; Accecamento/Polverizzazione bloccano la mira a distanza (`isRangedBlocked`), Deviazione la devia (`isDeflected`) — entrambe usano la nuova classificazione mischia/distanza di `Ability.isRanged()` (assunzione da confermare, vedi TODO).
- **Ramificazione**: nuovo campo `ActiveEffect.stacks`, cresce di 1 ad ogni tick proprio, danno = `stacks * 15 * ElementoATK`.
- **Naturalizzazione/Infangato**: agganciate a `CombatState.tryFlee()` (bloccano la fuga, unico "movimento" che il combattimento ha).
- **Illuminazione/Vaporizzazione**: STUB dichiarati — nessun sistema di occultamento/volo a cui agganciarle.
- **Firenado**: diffusione dell'elemento agli alleati vicini è STUB — il combattimento è 1v1, nessun sistema party.

### 6.5 Correzione: ATK/DEF elementale spaccato per elemento (stessa sessione, dopo il primo giro)
Il primo giro di §6.2/6.4 usava una singola coppia `elementAttack`/`elementDefense` per tutti gli elementi — non era quello che si voleva, era una mia semplificazione eccessiva. Corretto:
- `entity/StatType.java`: da `ELEMENTO_ATK`/`ELEMENT_DEF` unici a 6 coppie, una per elemento — `FIRE_ATK`/`FIRE_DEF`, `WATER_ATK`/`WATER_DEF`, `ELECTRIC_ATK`/`ELECTRIC_DEF`, `EARTH_ATK`/`EARTH_DEF`, `WIND_ATK`/`WIND_DEF`, `LIGHT_ATK`/`LIGHT_DEF`. PHYSICAL/NONE restano su `ATTACK`/`DIFESA` (non hanno una coppia elementale).
- `Entity.elementAttack`/`elementDefense`: da singolo `int` a `Map<Element,Integer>` (+ getter `getElementAttack(Element)`/`getElementDefense(Element)`, default 0 per elementi non impostati).
- `Player`: basi `baseElementAttack`/`baseElementDefense` sono ora anch'esse mappe per elemento (private); `recalculateStats()` cicla su tutti gli elementi applicando il bonus % dello `StatType` giusto a ciascuno. Partono tutte uguali (40 ATK / 20 DEF su ognuna) — stesso valore del vecchio stat unico, semplicemente non ancora differenziate.
- `ElementSystem.attackStat(Element)`/`defenseStat(Element)`: nuova mappa Element → StatType, usata da `recalculateStats()`. Ritornano `null` per PHYSICAL/NONE.
- Tutte le formule "X\*ATK" delle reazioni (Folgore, Infiammazione, Raggio, Carbonizzazione, Sovraccarico, Tempesta, Ramificazione, Elettrizzazione, Firenado) ora usano lo stat ATK dell'elemento giusto invece del generico — es. Folgore (Fulmine) usa `ElectricATK`, Infiammazione (Fuoco) usa `FireATK`. Sovraccarico/Tempesta (50/50 tra due elementi) usano l'ATK dell'elemento estratto quel tick, quindi il 50/50 ora cambia davvero il numero, non solo l'etichetta come nel primo giro.
- `MON_Goblin` (usa Thunderbolt/Fulmine): imposta `elementAttack.put(ELECTRIC, ...)` invece del vecchio campo singolo.

## 7. Sessione 27/08 (continua) — Struttura Quest

> **Superato dal §37**: la struttura quest (`QuestStep`/`QuestRegistry`/step machine lineare) è stata sostituita da file in `res/quests/` con situazioni `X.Y` e branch. Questo paragrafo resta come storico.

Nuovo package `quest/` (7 file): `QuestEventType` (enum: KILL/TALK/REACH_LOCATION/COLLECT/CUSTOM), `QuestTier` (CRITICO/MAGGIORE/FLAVOR, rispecchia i tier del Calendar System §1), `QuestState` (NOT_STARTED/ACTIVE/COMPLETED/FAILED — FAILED è uno stub non ancora agganciato), `QuestStep` (uno step = descrizione + trigger + target + goal, con `matches()`/`isComplete()`), `Quest` (**step machine**: sequenza di `QuestStep`, uno attivo alla volta — `notify()` avanza solo se l'evento combacia con lo step corrente, non con step futuri o già superati), `QuestRegistry` (dispatcher statico delle definizioni, stesso stile di `combat/Ability.java` — vedi il suo header per "come aggiungere una nuova quest"), `QuestManager` (liste `active`/`completed` separate; correzione fatta nella sessione successiva, vedi §8b — non collegate 1:1 a bookzone come scritto qui inizialmente).

**Quest ottenibili sia da dialogo NPC che da trigger di mappa, dal primo giro**:
- Dialogo: nuovo campo `Entity.givesQuestId` (null di default) — se impostato, `speak()` chiama `QuestManager.startQuest()` la prima volta che ci si parla; ad ogni `speak()` notifica anche un evento `TALK` col nome dell'NPC (per step tipo "parla con X").
- Trigger di mappa: due nuovi metodi in `EventHandler` (`questStartEvent()`/`locationEvent()`), stesso pattern di `damagePit()`/`healingPool()` esistenti. **Capacità pronta ma non richiamata da `checkEvent()`** — nessuna mappa ha ancora un punto reale a cui agganciarle; per usarle basta una riga in `checkEvent()` come per gli eventi esistenti.
- Combattimento: `CombatState.onVictory()` notifica `KILL` col nome del mostro sconfitto — già collegato, funziona.

**Un solo esempio concreto** in `QuestRegistry` (`"goblin_bounty"`, un solo step: sconfiggi il Goblin) per provare che l'impianto giri end-to-end con quello che già esiste (l'unico mostro nel gioco). Il campo `giverNpc` è un placeholder ("Villager") — nessun NPC con quel nome esiste ancora, da allineare quando c'è.

**Deliberatamente non collegato**:
- `QuestEventType.COLLECT` — nessun sistema di inventario reale esiste (`Player.pickUpObject()` è uno stub vuoto), quindi non c'è nulla da notificare. Il valore enum esiste già per quando ci sarà.
- Rendering delle quest nel libro (bookzone Quests) — solo la struttura dati è pronta, la UI resta da fare.
- Persistenza: nessun sistema di salvataggio esiste ancora (§5) — lo stato delle quest si perde alla chiusura del gioco, come tutto il resto.

## 8b. Sessione 27/08 (continua) — Quest nel libro + notifiche a schermo

> **Aggiornato dal §37**: la pagina 1 del libro ora elenca `Quest.history` (le situazioni attraversate), non più `steps`.

**Correzione rispetto a §7**: avevo scritto lì che `BOOKZONE_COUNT` per l'area Quest fosse 2 (Attive/Completate) — falso, il valore vero nel codice è **5** (il commento in `GamePanel`/`UI` diceva "quest=2" ma sia l'array `BOOKZONE_COUNT` sia i `Rectangle` in `SUBTOPIC_IMAGE_RECTS` per quell'area erano già 5: commento sbagliato, corretto anche quello. Il progetto usa la convenzione "bookzone = singola voce, es. Quest 1/Quest 2" (vedi il commento originale sui 3 livelli in `GamePanel.java`) — quindi **una bookzone = una quest**, non uno stato.

- `QuestManager.getKnownQuests()`: lista concatenata active+completed (ordine stabile) — bookzone *N* mostra la N-esima quest conosciuta dal giocatore (non tutte quelle esistenti in `QuestRegistry`: una quest mai iniziata non compare, niente spoiler). Cap a 5 slot per ora (come `BOOKZONE_COUNT`); da alzare (lì + i `Rectangle` corrispondenti in `UI.SUBTOPIC_IMAGE_RECTS`) se `QuestRegistry` supera le 5 quest raggiungibili in una run.
- `UI.drawQuestPageContent()`: disegna testo VERO per la prima volta in una pagina del libro (finora erano solo immagini pre-renderizzate, `book_X.png`, senza overlay di testo). bookpage 0 = titolo + descrizione + stato; bookpage 1 = lista obiettivi con `[x]`/`[ ]` e progresso (`3/5`) sullo step attivo. Area di testo (`PAGE_CONTENT_RECT`) è un **placeholder** (95,20,165,230 nello spazio immagine 272×272) — da aggiustare a vista come i rettangoli di bookmark/subtopic, nessun riferimento precedente da cui partire visto che nessun'altra area disegna ancora testo.
- `QuestManager.getQuestById(id)`: lookup diretto per id (cercando in active poi completed) — utile per far dipendere qualcos'altro dallo stato di UNA quest specifica (es. un dialogo NPC diverso se il giocatore l'ha già completata) senza scorrere a mano le due liste ogni volta.

**Come il giocatore viene informato**: prima di questa sessione, **in nessun modo** — `UI.showMessage()`/`messageOn` esistevano già (usati da `Npc_HumanRedWorker` per "Hai ottenuto: Spada!") ma non erano MAI disegnati da nessuna parte: un aggancio morto, bug preesistente non mio. Aggiunto `UI.drawMessage()` (banner in alto, ~2 secondi, richiamato da `draw()` sempre tranne che nel titolo) e ora `QuestManager` lo usa per tre eventi: avvio quest ("Nuova missione: ..."), avanzamento di step ("Nuovo obiettivo: ..."), completamento ("Missione completata: ..."). Di riflesso, questo ha sistemato anche il messaggio della spada, che ora funziona davvero.
- `QuestManager` tiene ora un riferimento a `GamePanel` (passato nel costruttore, `new QuestManager(this)` in `GamePanel`) per poter chiamare `gp.ui.showMessage()` — stesso pattern di `CombatState`/`EventHandler`/`UI` stesse, che tengono già tutte `gp`.

## 9. Guida pratica — Aggiungere e usare le Quest (sistema v2: file in `res/quests/`)

Riferimento autonomo: tutto quello che serve per lavorare sulle quest. Se qui e nel codice (`Quest`, `QuestLoader`, `QuestManager`, `EventHandler`) c'è divergenza, fidati del codice.

### 9.1 Modello
- Una quest è un insieme di **situazioni** `X.Y`: **X** = step principale (la storia avanza cambiando X), **Y** = situazione attuale dentro lo step (il progresso, non "con chi hai parlato"). Il salvataggio (STUB: solo `Quest.saveKey()`) è `nomequest.X.Y`, dove `nomequest` = nome del file.
- Ogni situazione ha dei **branch** (uscite). Quando un branch **prende priorità** la quest passa alla sua situazione di arrivo e gli altri branch della situazione di partenza smettono di contare: non si retrocede. Una situazione **senza branch è un finale**: al suo ingresso la quest si completa (la chiave dice quale finale).
- **IMPORTANT**: completato il `require`, prende subito priorità.
- **FAST**: completato il `require` non cambia nulla (nemmeno Y); solo quando poi si completa la `continue` prende priorità. Una `continue` fatta prima del requisito non conta.
- **COINCIDENT**: come FAST ma con più `require` combinati in AND oppure OR. Per una scelta dopo un gate: due branch COINCIDENT con gli stessi `require` e `continue` diverse.
- Se nello stesso evento scattano più branch vince un IMPORTANT su un FAST/COINCIDENT, a parità l'ordine del file.

### 9.2 Formato del file (`res/quests/<id>.txt`, UTF-8)
```
title: Scopri il mistero
description: Qualcosa non va nel villaggio.
tier: MAGGIORE                      # CRITICO | MAGGIORE | FLAVOR (default FLAVOR)
giver: Villager                     # opzionale, solo informativo

step 1.0: Arrivi al villaggio
  branch arrivo IMPORTANT -> 1.1
    require REACH_LOCATION villaggio
step 1.1: Indaga sul villaggio
  branch casa COINCIDENT OR -> 2.0
    require TALK npc_1
    require TALK npc_2
    continue REACH_LOCATION casa
step 2.0: Esplora la casa
```
- `branch <id> <IMPORTANT|FAST|COINCIDENT> [AND|OR] -> X.Y` (`-> X` = `X.0`); `require|continue <EVENTO> <target> [xN]` (`xN` = N volte). Righe con `#` e vuote ignorate; l'indentazione è solo estetica.
- La prima situazione del file è quella di partenza. Id e target senza spazi. `targetId` combacia esattamente (case-sensitive) con ciò che passa chi notifica (§9.5).
- Errori di formato: messaggio in console con numero di riga, la quest non si carica. Avvisi (non bloccanti): più `require` fuori da COINCIDENT, `continue` su IMPORTANT, arrivo non definito o non successivo.
- `res/quests/esempio_mistero.txt` contiene la legenda e un esempio con tutti e tre i tipi (id segnaposto, nessuna zona/NPC reale lo avvia). `goblin_bounty.txt` è la quest reale assegnata dall'NPC.
- Ogni `QuestLoader.load(id)` crea un'istanza NUOVA (stato mutabile).

### 9.3 Come farla partire
- **Dialogo NPC**: invariato (§10-§11): `Entity.givesQuestId` porta l'id (= nome file), la sottoclasse NPC chiama `gp.questManager.startQuest(givesQuestId)` al `dialoguesIndex` giusto. Idempotente.
- **Zona di mappa**: riga con `start idQuest` in `res/maps/locations.txt` (9.4).

### 9.4 Zone di mappa (`res/maps/locations.txt`, caricato da `main/LocationZone`)
```
libreria  = world01,col30,row10 .. col35,row14
casa      = world01,col40,row40
villaggio = world01,col20,row20 .. col40,row35 start esempio_mistero
```
- Una riga per zona: tile singola o rettangolo (angoli opposti inclusi). `idZona` è il target dei `REACH_LOCATION` nei file quest.
- `EventHandler.checkLocations()` (chiamato all'inizio di `checkEvent()`, quindi solo mentre il player si muove) usa la tile al centro della `solidArea` del player. L'evento scatta all'**ingresso** nella zona; uscendo e rientrando scatta di nuovo (così una visita fatta prima che la quest ascolti non si perde). Cambiando `currentWorld` lo stato "dentro" si azzera; le zone valgono solo per il loro mondo.
- Obiettivi tipo "ha esplorato X" si fanno con una zona interna (es. `libreria_fondo`) e `require REACH_LOCATION libreria_fondo`, senza eventi `CUSTOM`. Se si entra in più zone nello stesso istante scattano nell'ordine del file: metti prima la più generale.
- Con `start`, la quest viene avviata **prima** della notifica: il suo primo step può già ascoltare la zona che l'ha avviata.
- Il file è fornito **vuoto** (solo legenda ed esempio commentato): nessuna mappa ha ancora coordinate reali per libreria/villaggio/ecc.
- Sostituisce `EventHandler.locationEvent()`/`questStartEvent()` (rimossi: non ridefinivano lo scatto per ingresso e notificavano a ogni frame).

### 9.5 Tipi di obiettivo (QuestEventType)
| Tipo | Chi lo notifica | targetId | Stato |
|---|---|---|---|
| `KILL` | `CombatState` (vittoria) | `monster.name` | Collegato |
| `TALK` | `Entity.speak()` | `entity.name` | Collegato |
| `REACH_LOCATION` | `EventHandler.checkLocations()` | `idZona` di `locations.txt` | Collegato (nessuna zona reale ancora) |
| `COLLECT` | `Player.obtain()` | `Item.id` del registry | Collegato (§35.2) |
| `CUSTOM` | a mano: `gp.questManager.notify(QuestEventType.CUSTOM, "un_id")` | id a piacere | Per obiettivi senza un tipo loro |

### 9.6 Leggere lo stato da altrove
```java
Quest q = gp.questManager.getQuestById("goblin_bounty"); // null se mai iniziata
if (q != null && q.state == QuestState.COMPLETED) { /* ... */ }
// q.step (X), q.micro (Y), q.currentStep().text, q.history, q.saveKey()
```
`getActive()` / `getCompleted()` / `getKnownQuests()` come prima.

### 9.7 Messaggi a schermo
`QuestManager` mostra da solo "Nuova missione", "Nuovo obiettivo: <testo della nuova situazione>" (solo quando cambia X o Y: l'armarsi di un FAST/COINCIDENT non cambia Y e non mostra nulla) e "Missione completata".

### 9.8 Nel libro
Ogni quest conosciuta compare da sola come bookzone (limite 5, vedi `GamePanel.BOOKZONE_COUNT[1]` e `UI.SUBTOPIC_IMAGE_RECTS[1]`). Pagina 0: titolo, descrizione, stato. Pagina 1: `Quest.history` con `[x]` per le situazioni passate, `[ ]` per l'attuale (con `(n/N)` se ha un solo obiettivo contato, `Quest.progressText()`), tutto `[x]` a quest completata (finale compreso).

### 9.9 Limiti noti
- Nessuna persistenza: `saveKey()` produce solo la stringa `nome.X.Y`; per ripristinare davvero servirebbero anche contatori e condizioni già soddisfatte (non inclusi in X.Y).
- `QuestState.FAILED` non è agganciato a nulla.
- Condizioni solo piatte (AND oppure OR per branch): niente annidamenti tipo `A AND (B OR C)`.
- Nessun evento di fine cinematica o simili: va notificato come `CUSTOM` dal punto giusto.
- Solo `goblin_bounty` è una quest reale; il resto è dimostrato con `esempio_mistero` e con test headless, non in gioco con contenuti veri.

## 10. Sessione 27/08 (continua) — Quest da AssetSetter + messaggi impilati

**`AssetSetter.place()`**: nuovo overload a 7 argomenti (`..., paletteDef, questId`) — se `questId` è `null` non assegna nessuna quest (comportamento invariato per obj/monster, che continuano a usare la versione a 6 argomenti senza toccarla). `setNpc()` ora collega davvero `Npc_HumanRedWorker` alla quest `"goblin_bounty"` (l'unica reale in `QuestRegistry`) — primo esempio end-to-end funzionante: dialogo che assegna la quest, uccisione del Goblin che la completa, libro che la mostra.
- **Nota sulla scelta del punto**: l'alternativa (metterlo nel costruttore dell'NPC stesso, `Npc_HumanRedWorker.java`) resta più coerente con come il resto del progetto è scritto (quell'NPC si autoconfigura interamente lì — dialoghi, hitbox...) — qui è stato messo in `AssetSetter` su richiesta esplicita, perché in questo punto si vede a colpo d'occhio insieme a tutti gli altri piazzamenti. Da tenere a mente se in futuro si aggiungono più NPC della stessa classe con quest diverse: quel caso torna a favorire il costruttore.

**`UI`: da singolo messaggio a coda impilata**: prima un secondo `showMessage()` sovrascriveva silenziosamente il primo se ancora a schermo (helper interno, non esposto). Ora `messages` è una lista di box, ognuno con il proprio timer indipendente (~2 secondi) — più messaggi contemporanei si impilano uno sotto l'altro invece di accavallarsi. Con l'NPC attuale la sovrapposizione non capita mai davvero in pratica (`givesQuestId` scatta al primo dialogo, la spada al terzo — momenti diversi), ma resta la protezione generale corretta per quando due notifiche arriveranno sullo stesso frame.

## 11. Sessione 27/08 (continua) — Assegnazione quest spostata dentro l'NPC

`Entity.speak()` non chiama più `startQuest(givesQuestId)` automaticamente al primo dialogo — restava troppo rigido (un solo momento possibile, sempre il primo click, uguale per ogni NPC). `givesQuestId` ora è solo il dato ("quale quest dare"); OGNI sottoclasse NPC decide da sé A QUALE `dialoguesIndex` chiamare `gp.questManager.startQuest(givesQuestId)`, esattamente come già faceva `Npc_HumanRedWorker` per la spada. `Entity.speak()` continua a notificare `TALK` ad ogni dialogo (invariato — resta generico, serve agli step quest tipo "parla con X").

`Npc_HumanRedWorker`: la quest `"goblin_bounty"` ora parte allo stesso `dialoguesIndex==2` della spada — scelta deliberata (non obbligata) per far vedere, nello stesso identico dialogo, i due box impilati di §10 invece che uno alla volta. Per darla in un punto diverso basta cambiare quel numero nella sua `speak()`.

## 12. Sessione 27/08 (continua) — Struttura Weapon/Armor/Jewelry (struttura decisa dall'utente)

Struttura dati per i 3 tipi di oggetto, così come descritta esplicitamente (non una mia proposta): `items/ItemCategory.java` (ARMA/ARMATURA/GIOIELLO), `items/Component.java` (slot generico gemma/incantesimo/roccia — porta solo un `id` stringa, nessuna logica), `items/Weapon.java`, `items/Armor.java`, `items/Jewelry.java`.

**Deliberatamente non implementato in questa sessione — "add components"**: la funzione che legge tutti i componenti equipaggiati su un oggetto (gemme/incantesimi/rocce) e li riduce in UN blocco di bonus/effetti unico (invece di controllarli uno per uno) è stata esplicitamente rimandata al futuro da chi ha dato la spec. Di conseguenza: `Weapon`/`Armor`/`Jewelry` portano solo i campi grezzi descritti (Affilatezza, Metallo, Rocce...), `statBonusPercent` (il ponte verso `Player.recalculateStats()`, esistente da prima) resta vuota per questi 3 tipi — equipaggiarli non rompe nulla ma non dà ancora bonus reali. `Sword_Basic_Iron` (esempio "a mano", da prima di questa struttura) resta com'era, non migrato a `Weapon`.

**Scelte interpretative fatte per completare campi non specificati esplicitamente — da confermare**:
- `Weapon.pomo` — la spec diceva "Butt = velocità" in inglese in mezzo a una lista italiana; tradotto come "pomo" (l'estremità opposta alla lama di un'arma). Se si intendeva altro, va solo rinominato il campo.
- `Armor.armorType` — lasciato `String` libero (non un enum chiuso): la spec dice "tipo di armatura e di conseguenza dove andrà" ma non elenca i tipi reali (elmo/corazza/gambali/...). Oggi ESISTE solo lo slot `Chestplate` per l'armatura — ogni `Armor` ci va sempre, qualunque `armorType`. Quando i tipi reali saranno decisi, andranno aggiunti gli `ItemSlot` mancanti (`Legs`/`Hands`/`Feet`...) e `armorType` può diventare un enum chiuso.
- `Jewelry`: `gemma` (singola) usata da Corona/Collana/Anello, `rocce[9]` usata SOLO da Bracciale ("solo rocce, no gemme") — tenuti entrambi come campo sulla stessa classe invece di sottoclassi separate, per non irrigidire la gerarchia prima che l'aggregazione vera esista.

**Nuovi slot su `Item.ItemSlot`/`Player`**: `Head`, `Neck`, `Ring1`, `Ring2`, `Bracelet1`, `Bracelet2` (prima esistevano solo `MainHand`/`OffHand`/`Chestplate`) — necessari per corona/collana/i 2 anelli/i 2 bracciali dei Gioielli. `Player` ha un `EquipSlot` in più per ciascuno (`headSlot`, `neckSlot`, `ring1Slot`, `ring2Slot`, `bracelet1Slot`, `bracelet2Slot`), `slotFor()` aggiornato di conseguenza. Un elmo (`Armor`) potrà in futuro condividere lo slot `Head` con una corona (`Jewelry`) — stesso slot, categorie diverse, corretto: un solo oggetto per zona del corpo, non per categoria.
- **Limite noto**: anelli e bracciali hanno 2 slot ciascuno ma nessuna logica "equipaggia nel primo slot libero" — `Jewelry` di tipo `ANELLO`/`BRACCIALE` parte sempre su `Ring1`/`Bracelet1`, va spostata a mano su `Ring2`/`Bracelet2` (`item.slot = ItemSlot.Ring2;` prima di chiamare `equip()`) se il primo è già occupato.

## 13. Sessione 27/08 (continua) — UI del libro estratta in package/classe separati

Nuovo package `book/` (1 file, `BookUI.java`) — tutto quello che riguardava SOLO il libro spostato da `UI.java`: i rettangoli di bookmark/sottoargomenti (`BOOKMARK_IMAGE_RECTS`, `SUBTOPIC_IMAGE_RECTS`, `PAGE_CONTENT_RECT`), i `Button` corrispondenti, `draw()` (ex `drawBookScreen()`), `drawQuestPageContent()`, `handleClick()` (ex `handleBookClick()`).

**Stesso pattern già esistente per il combattimento**, non inventato apposta: `UI` teneva già `public CombatState combat` come sotto-schermata separata — `public book.BookUI book` fa lo stesso, istanziato allo stesso modo (`book = new book.BookUI(gp, this);`, subito dopo `combat = new CombatState(gp, this);`). `UI.draw()` ora chiama `book.draw(g2)` invece di `drawBookScreen()`; `GamePanel`'s mouse listener chiama `ui.book.handleClick(...)` invece di `ui.handleBookClick(...)`.

**Cosa è rimasto in `UI` apposta** (condiviso con altre schermate, non solo il libro): il font `MaruMonica`, `drawStyledText()` — `BookUI` li usa tramite il riferimento `ui` che tiene (`ui.MaruMonica`, `ui.drawStyledText(...)`), niente di duplicato. `Button` (già un file a sé in `main/Button.java` da prima) non si è dovuto toccare.

Due commenti in `GamePanel.java` che citavano `UI.SUBTOPIC_IMAGE_RECTS` corretti in `book.BookUI.SUBTOPIC_IMAGE_RECTS`.

## 14. Sessione 27/08 (continua) — Armature reali (12 tipi) + armi con categoria/sottotipo/combo

**Armor**: `ArmorType` non è più una stringa libera — enum chiuso con i 12 tipi reali (HELMET, GORGET, PAULDRON, REREBRACE, COUTER, VANBRACE, GAUNTLET, CUIRASSE, CUISSE, POLEYN, GREAVE, SABATON), mappati 1:1 sui nuovi `ItemSlot`. **`Chestplate` rimosso** (sostituito, come deciso) — nessun riferimento rimasto in tutto il repo. `Item.ItemSlot`/`Player` hanno ora i 10 slot armatura nuovi (`pauldronSlot`...`sabatonSlot`); Helmet/Gorget condividono `Head`/`Neck` con Corona/Collana (Jewelry) — stessa logica già decisa in §12: un solo oggetto per zona del corpo, non per categoria (assunzione mia, non riconfermata esplicitamente in questa sessione, ma coerente con quanto già scritto). Nuovo `Armor.WeightClass` (LEGGERA/MEDIA/PESANTE), usato dalla penetrazione degli archi.

**Weapon**: `WeaponType` (il mio placeholder a 3 valori) sostituito da `WeaponCategory` (SPADE/MAZZE/LANCE/ARMI_A_DISTANZA/DIFENSIVE, 5) + `WeaponSubtype` (16 valori reali, ognuno con la propria `category`). `lama` (che accorpava taglio+contundente) **spezzato** in `taglio`+`contundente` distinti (per il combo di Mazza chiodata/Ascia). Nuovi campi: `disarmChance` (Frusta), `stunChance` (Falce — riusa `StatusEffect.STUN` già esistente), `counterattackChance` (Coltello da lancio), `peso` (Spadone: il danno usa questo invece di taglio/contundente/punta), `comboTypes` (`DamageType[]`, null di default — Mazza chiodata: {PERFORANTE,CONTUNDENTE}, Ascia: {TAGLIO,CONTUNDENTE}). Le armi Difensive (Scudo/Broquel/Sai) di default vanno in `OffHand` invece di `MainHand`.

**Deliberatamente non fatto — resta dentro il perimetro "add components" già rimandato al futuro (§12)**:
- Nessun numero è popolato per `disarmChance`/`stunChance`/`counterattackChance`/`comboTypes` — non ho valori reali per "quanto" disarma una Frusta o stordisce una Falce, restano a 0/null finché non li imposta chi crea l'istanza vera (un futuro `WeaponRegistry`, stesso pattern di `Ability`/`QuestRegistry`).
- Nessuna logica in `CombatState` legge ancora questi campi (disarmo su hit, stordimento su hit, contrattacco, combo taglio+contundente, penetrazione per `WeightClass`) — solo la struttura dati, come per tutto il resto della categoria "add components".

## 15. Sessione 27/08 (continua) — Correzione slot + Registry + meccaniche armi in combattimento

**Correzione**: Helmet/Gorget erano finiti condivisi con Head/Neck dei Gioielli in §14 nonostante l'intento fosse tenerli separati — in realtà il codice era già corretto (slot dedicati `helmetSlot`/`gorgetSlot`), solo il commento sopra `Armor.java` diceva ancora "condivisi": corretto anche quello. Gioielli/Armi/Armatura restano completamente distaccati, nessuno slot in comune.

**`WeaponRegistry`/`ArmorRegistry`/`JewelryRegistry`** (nuovi, in `items/`) — stesso pattern statico di `Ability`. Un solo esempio reale per categoria (`"short_sword_basic"`, `"cuirasse_basic"`, `"iron_ring_basic"`) con numeri placeholder — nessun bilanciamento reale dato, solo per provare che l'impianto giri.

**Meccaniche armi ora vive in `CombatState.dealDamage()`**, automatiche su ogni colpo andato a segno (come deciso):
- **Combo** (`Weapon.comboTypes`): il bonus si somma al danno già inflitto (es. Mazza chiodata: `punta + contundente`).
- **Disarmo** (`disarmChance`): tira, se riesce disequipaggia il MainHand del bersaglio (solo se è il Player — i mostri non hanno equip).
- **Stordimento** (`stunChance`): tira, se riesce applica `StatusEffect.STUN` per 1 turno (riusa il sistema già esistente).
- **Contrattacco** (`counterattackChance`): dipende dall'arma di chi SUBISCE il colpo, non di chi attacca — tira sull'arma del bersaglio, se riesce infligge danno di rientro all'attaccante.

Tutte e 4 leggono l'arma equipaggiata in `MainHand` (helper `weaponOf()`, `null` se l'entità non è un Player o non ha nulla equipaggiato — i mostri restano esclusi da queste meccaniche per ora). Nessun numero reale è ancora nel registry per queste probabilità (restano 0 nell'unico esempio `short_sword_basic`), quindi oggi non scattano mai finché non le imposti su un'arma vera.

**Ancora da fare (ordine dato: 2 → 3 → 1, fatti 2 e 3, resta 1)**: l'inventario vero — un contenitore che tenga gli oggetti posseduti-ma-non-equipaggiati, popolato dalla raccolta nel mondo (`Player.pickUpObject()` è ancora uno stub vuoto) e da un menu per equipaggiare da lì. Liste separate confermate: equipaggiabili (Weapon/Armor/Jewelry) e oggetti "puri" (es. una Key) non nella stessa lista.

## 16. Sessione 27/08 (continua) — Disarmo/Stordimento confermati, Contrattacco ristrutturato, stub Inventory

**Disarmo e Stordimento**: confermato che il design già in campo (base 0, solo l'arma che imposta un valore >0 ha una chance — es. Frusta per disarmo, Falce per stordimento) corrisponde a "0 con bonus flat se l'equip lo permette". Nessuna modifica di codice necessaria.

**Contrattacco ristrutturato**: prima era danno di rientro immediato e inline; ora scatena un **turno EXTRA per il player, ristretto a soli Attack/Ability** (niente Inventory/Minimap/Flee), che **non sostituisce** il suo turno normale del round.
- `CombatState`: nuovi campi `counterattackPending`/`counterattackOrigin`/`insideCounterattack` (quest'ultimo anti-ricorsione, per quando i mostri potranno equipaggiare armi in futuro — oggi `weaponOf()` ritorna sempre `null` per loro, quindi solo il player può subire un contrattacco).
- `monsterTurn()`: se dopo l'attacco del mostro `counterattackPending` è vero, invece di `advanceRound()` passa `turnPhase` a `PLAYER_TURN` con `preTurnChecked=true` (salta `beforeTurn()` — è un'azione bonus, non il turno vero, niente ri-check dello stordimento).
- `confirmCommand()`/`navigateUp()`/`navigateDown()`/`drawCommandMenu()`: durante `counterattackPending` il menu mostra SOLO Attack/Ability ("Counterattack! Choose:").
- `executeCounterattack()`: risolve il colpo (via `dealDamage()`, niente `afterTurn()` — bonus, non tick doppio degli effetti) e poi riprende il round esattamente da dove `monsterTurn()` l'aveva lasciato (`advanceRound(false)`).

**`items/Inventory.java`** (nuovo) — STUB richiesto: solo le due liste separate (`equippables: List<Item>`, `pureItems: List<String>`), nessuna logica (niente add/remove/equip-da-qui/raccolta). **Non ancora agganciato a `Player`** — nessun campo `Player.inventory` esiste ancora, la prossima sessione su questo tema dovrà collegarlo (più `pickUpObject()`, più un menu vero al posto dello stub "Inventory is empty.").

## 17. Sessione 27/08 (continua) — Add components implementato + un oggetto per tipologia

**"Add components" implementato**: `Item.computeBonusPercent()` (no-op di default, così `Sword_Basic_Iron` resta invariata) sovrascritta da `Weapon`/`Armor`/`Jewelry` — ognuna traduce i propri campi grezzi in `statBonusPercent` (letta poi dal sistema di equip già esistente da prima). Richiamata da `Player.equip()` subito prima di applicare i bonus dello slot. Nuovo `Item.addComponents()` (helper condiviso) somma il bonus di ogni componente innestato tramite il nuovo `ComponentRegistry` (dispatcher statico, stesso pattern di Ability/QuestRegistry — 4 componenti di esempio: `ruby_shard`, `haste_rune`, `iron_vein_stone`, `swift_stone`).

**Scelte interpretative fatte per la traduzione campi→stat (nessuna formula data, da confermare/correggere)**:
- `Weapon`: `taglio+contundente+punta+peso` → `ATTACK` (sommati insieme, non per-tipo); `pomo` → `VELOCITA`; `guardia` → `DIFESA`. `manico`/`metallo`/`legamenti` NON producono bonus (manico è la difesa contro il disarmo altrui — non implementata; metallo/legamenti sono durabilità, nessuna stat "durabilità" esiste).
- `Armor`: `metallo` → `DIFESA`; `legamenti` → `VELOCITA` ("mobilità" — poteva essere `ELUSIONE` altrettanto ragionevolmente). `rifiniture`/`sostegno` non producono bonus.
- `Jewelry`: `metalli` → `EFFICIENZA` (nessuna stat specifica indicata per "bonus stat" generico).

**Correzione/completamento da §14-15**: `Weapon.penetratesUpTo` (Armor.WeightClass) — il campo per "Arco corto ignora armatura leggera / Arco lungo penetra pesante" non era mai stato aggiunto nella sessione che ha introdotto `Armor.WeightClass`, solo menzionato nei commenti. Aggiunto ora come struttura dati — **il meccanismo vero resta non collegato**: la DIFESA del Player è un aggregato unico, non tracciata pezzo per pezzo, quindi "ignora la difesa dei pezzi leggeri" non ha ancora un modo pulito di applicarsi colpo per colpo in `CombatState`.

**Content per testare tutto**: `WeaponRegistry` (16 armi, una per sottotipo), `ArmorRegistry` (12 armature, una per tipo), `JewelryRegistry` (4 gioielli, uno per tipo) — tutti con numeri placeholder di esempio, non un bilanciamento reale. Combo (Mazza chiodata, Ascia), disarmo (Frusta), stordimento (Falce), contrattacco (Coltello da lancio) e penetrazione (i 2 archi) sono popolati sui rispettivi esempi, pronti per essere provati in combattimento (tranne la penetrazione, non collegata — vedi sopra).

## 18. Sessione 27/08 (continua, dopo il push 0.17.1) — Notifiche a forma di segnalibro + stadio moltiplicatore stat

**Notifiche ridisegnate** (`UI.drawMessage()`): via dal box pieno di `drawSubWindwow` — ora usa i nuovi asset `res/ui/notify_bookmark_head.png` (14×17, punta decorativa) e `res/ui/notify_bookmark_body.png` (1×17, ripetuto). Analisi pixel dei due sprite: il bordo DESTRO dell'head combacia esattamente con la colonna del body (stessi colori riga per riga) — quindi l'head va a sinistra con la punta rivolta fuori, il body si ripete verso destra fino a coprire il testo. Larghezza = solo quella necessaria (head + N tile di body, N calcolato dalla larghezza del testo via `FontMetrics`), non un rettangolo pieno.
- Ancorata in alto a destra (`rightEdge = screenWidth - tileSize/2`, fisso); scorre in entrata da fuori schermo (x = screenWidth) fino alla posizione di riposo in `NOTIFY_SLIDE_FRAMES` (15) frame, con un ease-out cubico. Box e testo si muovono insieme (stesso `offsetX`).
- Più notifiche contemporanee si impilano verso il basso, stessa meccanica di prima (indice × altezza+gap).
- Non ferma il gioco: resta un overlay disegnato normalmente dentro `draw()` ad ogni frame, nessun blocco/pausa.
- `NOTIFY_SCALE` (3) e `NOTIFY_SLIDE_FRAMES` (15) sono costanti facili da ritoccare. Fallback al vecchio `drawSubWindwow` se gli sprite non si caricano (non blocca il gioco per un asset mancante).

**Piccola modifica alle stat** (`Player.recalculateStats()`): aggiunto un terzo stadio di calcolo dopo flat e percentuale — un moltiplicatore per stat (`statMultiplier`, default 1.0 = nessun effetto). Ordine ora: **stat flat → percentuale → moltiplicatore**. Non ancora popolato da nessuno (nessun buff/debuff temporaneo lo usa ancora) — struttura pronta, comportamento numerico invariato finché qualcosa non ci scrive dentro.

## 19. Sessione 27/08 (continua) — Asset notifica aggiornati a 20x20/1x20 + fix allineamento 1px

Aggiornato il commento di riferimento alle dimensioni reali dei due sprite (head 20x20, body 1x20) — il calcolo stesso (`headW`/`boxH`) non doveva cambiare, legge già `notifyHead.getWidth()`/`getHeight()` dall'immagine, non valori scritti a mano.

Aggiunto `NOTIFY_HEAD_Y_OFFSET` (-1): nell'asset l'head risulta 1px nativo più in basso del body, disallineandoli quando disegnati alla stessa y. L'head viene ora disegnato con `y + NOTIFY_HEAD_Y_OFFSET * NOTIFY_SCALE`. Se il verso risultasse sbagliato (l'head finisce più in alto invece che allineato), basta cambiare il segno della costante (-1 → 1).

## 20. Sessione 27/08 (continua) — Salto, Rampino, Cornice, Collinetta

Nuovo movimento oltre gli ostacoli, come discusso: salto sempre disponibile, rampino dietro `Player.hookUnlocked` (di default `false`).

**`tile/SpecialTile.java`** esteso con `Kind` (DECORATION/HOOK/CORNICE/COLLINETTA) e `Direction` (per le cornici, quale verso è attraversabile). Nuove tile: `appiglio` (HOOK), `cornice_giu/su/sinistra/destra` (CORNICE, una per direzione), `collinetta` (COLLINETTA). `TileManager.getSpecialTileAt(col,row)` nuovo, usato da `CollisionChecker`.

**`tile/TileLink.java` + `tile/TileLinkRegistry.java`** (nuovi): il formato di collegamento è esattamente quello dato — `world01,col16,row17 link world01,col15,row20`, un file per riga (`res/maps/tile_links.txt`, oggi vuoto/commentato: nessuna tile `appiglio` è ancora piazzata su una mappa vera). Ogni riga vale nei DUE sensi, registrata automaticamente al contrario senza doverla scrivere due volte. La virgola tra `col` e `row` è obbligatoria su entrambi i lati — una riga malformata stampa un errore chiaro in console e viene scartata, non ignorata silenziosamente.

**`main/CollisionChecker.java`**: 3 nuovi metodi — `tileAhead(entity, N)` (colonna/riga a N tile di distanza nella direzione in cui l'entità guarda, gestisce sia `"up"` che `"idle_up"`), `specialTileAt(col,row)`, `isTileCollisionAt(col,row)`.

**`entity/Player.java`**: nuovo `hookUnlocked` (bool, default false — tocca SOLO le meccaniche del rampino, il salto non ha un flag equivalente). Nuovo stato di movimento scriptato condiviso (`scripting`/`startScriptedMove()`/`updateScriptedMove()` — interpolazione lineare posizione, input normale disattivo finché non finisce): usato da tutte e 3 le meccaniche.
- `tryCornice()`: automatico camminando contro una CORNICE dal verso giusto (niente tasto) — dal verso sbagliato resta un muro normale (gestito dalla collisione esistente, invariata).
- `tryJump()`: tasto SPAZIO, solo su COLLINETTA, verifica che l'atterraggio (2 tile avanti) non sia bloccato prima di partire.
- `tryHook()`: tasto SPAZIO (stesso di jump — mutuamente esclusivi per tipo di tile, si prova prima jump poi hook), solo su HOOK e solo se `hookUnlocked`. Cerca un `TileLink` per (mondo corrente, colonna, riga) della tile davanti — nessun link registrato = l'appiglio è muto, non succede nulla. Stesso mondo = riposizionamento diretto; mondo diverso = stessa animazione ma a fine corsa cambia mappa (`GamePanel.loadWorld()`).

**`main/GamePanel.java`**: nuovo `currentWorld` (String, default `"world01"`) e `loadWorld(worldId, col, row)` — ricarica la griglia di tile e riposiziona il player. **Limite esplicito**: `npc[]`/`obj[]`/`monster[]` NON vengono ricaricati/ripuliti al cambio mondo — `AssetSetter` oggi piazza sempre lo stesso contenuto fisso pensato per `world01`, non è ancora data-driven per mappa. Finché non lo diventa, cambiare mondo sposta il terreno sotto i piedi ma lascia gli oggetti/npc del mondo precedente dove stavano.

**`main/KeyHandler.java`**: nuovo tasto SPAZIO (`spacePressed`), consumato/resettato come `enterPressed`.

**Semplificazione dichiarata sulla Collinetta**: la tua descrizione parlava di "rettangoli di collisione più piccoli" — il sistema di collisione qui lavora per cella di griglia intera, non per rettangoli dentro la cella, quindi ho implementato la collinetta come qualunque altro ostacolo a tile piena, scavalcato dal salto esattamente come una cornice/staccionata (2 tile di spostamento). Il risultato di gioco (cammini, non passi, salti, passi) dovrebbe essere lo stesso; se invece ti serve DAVVERO poter "sfiorare" il bordo della tile camminando e bloccarti solo sulla parte centrale, è un lavoro via più grosso sul collision system, da fare a parte.

**Non testabile oggi**: nessuna mappa ha ancora tile `appiglio`/`cornice_*`/`collinetta` piazzate (serve editarle a mano nei file `.txt` delle mappe), e non esistono `world02`/`world03`/`world04` — i link cross-mondo restano solo infrastruttura pronta finché non ci sono altre mappe da collegare.

## 21. Sessione 27/08 (continua) — Identità item confermata + instanceId

Confermato (non è un cambio di comportamento, è già così): `Weapon`/`Armor`/`Jewelry` sono normali oggetti Java, mai deduplicati per tipo. Due spade base create separatamente sono già due istanze indipendenti — modificare `incantesimi[]`/`gemme[]` sull'una non tocca l'altra. Nessun `Registry` mette in cache o riusa un'istanza condivisa, ogni `get()` crea un oggetto nuovo.

Aggiunto solo `Item.instanceId` (long, incrementale, univoco per oggetto creato) — non serve a garantire l'indipendenza (già garantita dal linguaggio), serve solo ad avere un riferimento stabile per distinguere in log/UI due oggetti altrimenti identici (es. due spade base senza incantesimi, visivamente indistinguibili).

Nota lasciata nel codice per chi costruirà l'inventario vero: gli equipaggiabili vanno sempre in una `List<Item>` di riferimenti (come già `items/Inventory.java`), mai in una mappa "tipo→quantità" — altrimenti si perde quale copia porta quali componenti. Gli oggetti "puri" senza componenti (una Key) possono restare stackati per nome, non hanno questo problema.

## 22. Sessione 27/08 (continua) — Minigioco angolare parata/schivata (Attacco Normale)

Implementato per davvero, non solo progettato. Scope confermato: **solo** "Attacca" (NormalAttack) del player passa da qui — le abilità restano su `playerUseAbility()`/`dealDamage()` a formula come prima, `enemy→player` resta un sistema futuro separato (non toccato).

**Semplificazione dichiarata — geometria a forme, non a pixel**: la spec parlava di assegnare un'area ad ogni pixel del PNG dell'arma. Il sistema di collisione qui usa `java.awt.geom.Area`/`Shape`/`AffineTransform` su forme (di norma rettangoli) in coordinate locali (pivot = impugnatura), non maschere pixel — stesso risultato di gioco (angolo conta, punto debole esiste, si elude mirando dove l'arma non c'è), sostituibile con maschere pixel vere in futuro senza toccare il resto del meccanismo. Nessuna arte reale creata: le zone sono rettangoli placeholder.

**`items/Weapon.java`**: nuovi `ZoneType` (RIGID/WEAK), `DefenseZone` (forma + tipo), `defenseZones` (lista), `restAngleDeg` (orientamento di riposo), `canDefend` (tag "arma difensiva"), `staticGuard` (vero solo per lo scudo: non ruota, non può essere eluso). `WeaponRegistry`: Scudo/Broquel/Sai ora hanno `canDefend=true` + geometria di zone reali (rettangoli placeholder) — lo Scudo ha anche `staticGuard=true`.

**`entity/Entity.java`**: nuovo campo `defenseWeapon` (arma usata per parare) + metodo virtuale `resolveDefenseWeapon()` (di default ritorna quel campo — pensato per i mostri, impostato direttamente da chi li costruisce). **`entity/Player.java`** lo sovrascrive: deriva l'arma difensiva dall'equip vero (OffHand se `canDefend`, altrimenti MainHand se `canDefend`, altrimenti null) invece di un campo statico — sempre sincronizzato con l'equip attuale. **`monster/MON_Goblin.java`**: gli ho dato un Broquel (`WeaponRegistry.get("buckler_basic")`) come `defenseWeapon`, così il minigioco è testabile subito contro un nemico vero.

**`combat/CombatState.java`** — il grosso del lavoro:
- Selezionare "Attacca" ora apre una fase di **mira** (`aiming=true`) invece di risolvere subito il colpo: frecce muovono la posizione del colpo (dentro un riquadro fisso attorno al mostro), A/D ruotano l'angolazione, Invio conferma, Esc annulla senza consumare il turno.
- Alla conferma (`confirmAim()`): **placeholder dichiarato** su come il difensore sceglie tra parare e schivare — nessuna regola data, ho messo "70% para se ha un'arma difensiva, altrimenti schiva sempre". Da rivedere quando deciderai l'IA vera.
- **Schivata** (`resolveDodge()`): esattamente la formula data — Elusività difensiva - Velocità attaccante (mischia) o - Precisione attaccante (a distanza), clampata 0-100, poi un tiro. Fallita = colpo pieno garantito.
- **Parata** (`resolveParry()`): l'arma difensiva ruota a un angolo — **formula placeholder**: parte da `restAngleDeg`, con una variazione casuale che si restringe all'aumentare di `manico` (più controllo = meno tremore, arma più precisa); gli scudi (`staticGuard`) non ruotano mai. Si controlla la sovrapposizione geometrica tra la "lama" dell'attaccante e le zone del difensore: punto debole → **danno extra a `metallo`/`legamenti` dell'arma difensiva** (finalmente usati per qualcosa, `metallo`/`legamenti` erano segnalati inutilizzati da mesi), punto rigido → usura normale, nessuna sovrapposizione → elusa, colpo pieno garantito (tranne con lo scudo, dove nessuna sovrapposizione forza comunque un blocco rigido: "non può essere eluso").
- Nuovo overload `dealDamage(attacker, target, abilityId, forceHit)` — quando `forceHit=true` salta il tiro di mira interno (il colpo va sempre a segno): usato da schivata fallita e parata elusa, che hanno già deciso l'esito con un calcolo diverso e non vanno ri-arbitrati con un secondo tiro casuale indipendente. Il vecchio overload a 3 argomenti resta invariato per tutte le altre chiamate esistenti (abilità, mostro che attacca...).
- `drawAimingOverlay()`: disegna il riquadro di mira, le zone del difensore alla loro posizione di RIPOSO (non l'angolo vero — quello si scopre solo alla parata, mirare "alla cieca" ha senso), la lama dell'attaccante alla posizione/angolo correnti.

**`main/KeyHandler.java`**: nuove frecce (posizione) + A/D (rotazione) nello stato di combattimento, no-op quando non si sta mirando.

**Non fatto, deliberatamente**: nessuna vera arte/hitbox pixel (placeholder rettangoli), nessuna IA vera per la scelta parata/schivata del difensore (70/30 arbitrario), nessun collegamento a `enemy→player` (resta un sistema futuro).

## 23. Sessione 27/08 (continua) — defenseWeapon → equippedWeapon, geometria da immagine vera

**Rimosso `Entity.defenseWeapon`** (campo dedicato alla sola parata) — sostituito con `Entity.equippedWeapon`, un campo generico ("cosa ha in mano ora", non solo per parare). `resolveDefenseWeapon()` ora controlla `equippedWeapon.canDefend` invece di ritornare un campo separato — un mostro che cambia arma a runtime (riassegnando `equippedWeapon`) aggiorna automaticamente anche cosa può usare per parare, senza bisogno di sincronizzare due campi. `MON_Goblin` aggiornato di conseguenza (`equippedWeapon = ...` invece di `defenseWeapon = ...`). `Player.resolveDefenseWeapon()` resta invariata (deriva da OffHand/MainHand veri, ignora `equippedWeapon`). `CombatState.java` non ha richiesto NESSUNA modifica — chiamava già solo `resolveDefenseWeapon()`, mai il campo direttamente.

**`Weapon.itemPath`/`combatItemPath`** (nuovi campi String) — percorsi per l'icona e per l'immagine dettagliata da cui estrarre la geometria di parata. Nessun asset vero esiste ancora per nessuna arma in `WeaponRegistry`: sono stringhe pronte, non popolate/testate.

**`combat/WeaponHitboxBuilder.java`** (nuovo) — stesso approccio "software shader" di `main.PaletteSwap` (scansione pixel-per-pixel via `getRGB()`, risultato cachato per chiave): ogni pixel non vuoto (alpha≠0) di `combatItemPath` diventa parte di UN'area RIGIDA. **Ottimizzazione**: non un rettangolo per pixel (troppo lento su un'immagine dettagliata) ma un rettangolo per ogni striscia orizzontale di pixel contigui non vuoti, riga per riga — stessa area risultante, molto meno lavoro. **Zone deboli non generate qui** — restano da aggiungere a parte, come richiesto ("poi penso io alle zone deboli"). Convenzione di pivot: il centro-basso dell'immagine è l'impugnatura (0,0 locale), coerente con le zone già definite a mano in `WeaponRegistry`.

`Weapon.loadRigidZoneFromImage()` (nuovo metodo) — carica `combatItemPath`, costruisce/cachа l'area rigida tramite `WeaponHitboxBuilder`, la aggiunge a `defenseZones`. Da richiamare esplicitamente (non automatico nel costruttore, dato che i Registry popolano i campi dopo la creazione) — nessun Registry lo richiama ancora, in attesa di asset veri da caricare.

## 24. Item: Rarità a scaglioni, ID materiale, SpecialEffect globale (PRE_TURN/POST_TURN/ACTIVE)

Sessione lunga, tocca quasi tutto il pacchetto `items` + il ciclo di turno in `combat/CombatState.java`. Riassunto in ordine di dipendenza.

**Rarità (scheda Equip)**: nuovo `items/Rarity.java` (`COMMON/HIGH_QUALITY/MASTERWORK/MYTHIC`) + su `Item`: `rarity`, `synergyBonusStub`/`fullSetOrStyleStub`/`mythicUniqueStub` (String, cumulativi per tier — Masterwork ha sia G che H, Mythic ha anche I) + 3 resolver (`resolveSynergyEffect()` ecc.) che li girano a `SpecialEffectRegistry.get(...)`. Nessuno li richiama ancora per applicarli davvero — sono il punto di aggancio, non logica in funzione.

**`ShieldRegistry.java`** (nuovo) — Scudo/Broquel/Sai spostati fuori da `WeaponRegistry` in un terzo template a sé (restano `Weapon`/`WeaponSubtype` DIFENSIVE, è solo organizzazione), per allinearsi ai 3 raggruppamenti Armi/Armatura/Scudi della scheda Equip.

**`itemPath`** spostato da `Weapon` a `Item` (condiviso con Armor/Jewelry) — STUB dichiarato "da non toccare": nessun asset, nessuna logica di caricamento. `combatItemPath` resta specifico di `Weapon` (percorso esteso per il minigioco di parata, popolato davvero solo dalle Difensive).

**`Sword_Basic_Iron.java` rimossa** (non piaceva) — sostituita da `WeaponRegistry.get("short_sword_iron")`, unico chiamante aggiornato (`Npc_HumanRedWorker.java`). Di conseguenza `Item.bonus()` (il vecchio modo di scrivere `statBonusPercent` a mano) è morto e rimosso: non ha più nessun chiamante.

**Conversione a ID dei campi grezzi di Weapon/Armor** (non di Jewelry, restata fuori scope): `taglio/contundente/punta/peso/pomo/guardia` (Weapon) e `metallo/legamenti` (Armor) non sono più quantità dirette — sono ID globali risolti da `items/MaterialRegistry.java` (nuovo) nel bonus % che generano (stesso id = stesso effetto su qualunque arma/armatura). `affilatezza`/`rifiniture` restano un LIVELLO, non un ID: moltiplicano il totale (0.25× per livello) invece di generarlo — vedi sotto. `manico`, il `metallo`/`legamenti` "durabilità" di `Weapon`, e il `sostegno` di `Armor` restano ID senza tabella: nessuna stat durabilità/controllo esiste ancora, per ora sono numeri inerti (nessun metodo li passa da `MaterialRegistry`). **Tutti i ~30 oggetti già scritti nei 3 registry sono stati rimappati sui nuovi ID** (numeri diversi da prima, ordine relativo preservato dove possibile) — verificato con un harness che equipaggia ogni entry: 0 id sconosciuti.

**`Item.statMultiplier`** (nuovo, `Map<StatType,Double>`, mirror di `statBonusPercent`) + `Item.applyQualityLevel(level)` (helper condiviso): applica `1 + 0.25*level` a TUTTE le stat che l'oggetto ha appena messo in `statBonusPercent` — scelta interpretativa esplicita (non solo alla "stat principale"), da correggere se non era l'intento. `Player.applySlotBonus()` ora aggrega anche questo (moltiplicativo, stesso toggle `slot.statChanged` di `percentBonus`) dentro `Player.statMultiplier` — che esisteva già, pronto per buff/debuff futuri, ma finora non lo popolava nessuno.

**`ComponentRegistry.java` rimossa** — gemme/incantesimi/rocce (`Component`) NON danno più un bonus % diretto: risolvono SEMPRE in un `combat.SpecialEffect` (vedi sotto). `Item.addComponents()` riscritta di conseguenza.

**`combat/SpecialEffect.java`** (nuovo, sostituisce `SpecialAction`) + **`combat/SpecialEffectRegistry.java`** (nuovo, dispatcher unico) — un solo registry per TUTTO ciò che è "effetto speciale": le abilità (`Ability.getSpecialAction()` ora delega qui), gli stub di rarità sopra, e i componenti. `Timing` a 3 valori: `PRE_TURN`/`POST_TURN` (richiamati sul portatore ad ogni suo turno, finché resta equipaggiato) / `ACTIVE` (scatta solo se invocato, es. dalle abilità). 4 id di esempio migrati da `ComponentRegistry` (`ruby_shard`/`haste_rune`/`iron_vein_stone`/`swift_stone`) → `POST_TURN`, `execute()` vuoto (dimostrano solo che l'aggancio funziona, nessuna logica reale).

**Aggancio al ciclo di turno** (tua spec: preturno-P1/turno-P1/postturno-P1/preturno-P2/...): `CombatState.beforeTurn(actor)` e `afterTurn(actor)` esistevano già (stordimento/tick elementale) e sono i punti giusti — ci ho aggiunto `firePreTurnEffects(actor)`/`firePostTurnEffects(actor)`, che per un `Player` scorrono `equippedItems()` (nuovo helper su `Player`, 20 slot) e richiamano `execute(this, actor, opponentOf(actor))` su ogni effetto della lista giusta. `target` è il vero avversario (`opponentOf()`, nuovo helper: `gp.player`/`monster`, essendo 1v1) — non `actor` due volte come nella prima versione (corretto dopo la tua nota): un effetto "su di sé" ignora semplicemente `target`, uno offensivo come "Eco" (attacca due volte, il secondo colpo come POST_TURN) ha già il bersaglio giusto pronto. I mostri non hanno equip oggi (`weaponOf()` lo nota già) quindi per loro questi due metodi non trovano nulla — non è un caso speciale gestito a mano, semplicemente `equippedItems()` non esisterebbe su un `Monster`.

**Aperto per il prossimo giro**: i 3 resolver di rarità (`resolveFullSetOrStyleEffect()` ecc.) non sono ancora richiamati da nessuna parte — manca la logica "conta i pezzi dello stesso set equipaggiati" che decide QUANDO un bonus di set è attivo. Quando la costruirai, il pattern naturale è: quel check produce un `SpecialEffect` (PRE_TURN o POST_TURN a seconda di cosa fa) che poi passa dagli stessi due hook già pronti in `CombatState` — non serve un terzo punto di aggancio.

## 25. Catalogo equipaggiamento completo generato dalla scheda Equip (1120 oggetti)

`WeaponRegistry`/`ArmorRegistry`/`ShieldRegistry` sono stati **rigenerati da zero** con TUTTO il contenuto della scheda Equip — non più ~30 esempi a mano, ma i 1120 oggetti reali (546 armi, 448 armature, 126 Difensive), uno per ogni combinazione Level×Tier×Type×Build. Generato da uno script Python (`generate_equip.py`, non incluso nel progetto — è uno strumento di build una tantum, non codice di gioco) che legge l'xlsx e scrive i 3 file `.java`: **switch scelto sopra un file dati esterno** (discusso con l'utente) per restare coerente con lo stile del resto del progetto — `get(id)` è identico da fuori, quindi migrare a un formato dati in futuro non toccherebbe i chiamanti.

**Id**: `{type}_{build}_{levelband}_{tier}`, es. `helmet_defense_0_10_common`, `claymore_atk_30_40_masterwork`. Slug derivati meccanicamente dai nomi della scheda.

**Come scalano i numeri** (placeholder, non bilanciamento reale — vedi sempre le note nei file):
- Ogni Type ha UN campo grezzo "primario" che scala con lo scaglione di potere (1..14, uno per blocco Level×Tier): `taglio`/`contundente`/`punta`/`peso` per le armi (quale dei 4 dipende dal Type — es. Claymore usa `peso`, Mace usa `contundente`), `metallo` per l'armatura upper-body, `legamenti` per le gambe, `guardia` per le Difensive. Tutti risolti da `MaterialRegistry`, che ora usa array a 14+ scaglioni invece delle vecchie tabelle piccole.
- Gli altri campi (`pomo`/`guardia` sulle armi da mischia, `legamenti` sull'armatura upper-body, `metallo` sulle gambe) restano un **tratto fisso** della forma del pezzo, uguale su tutte le 14 varianti dello stesso Type — non scalano con rarità/livello.
- `affilatezza`/`rifiniture` sempre 1 su tutto il catalogo generato (il moltiplicatore di qualità è quindi costante 1.25× ovunque) — semplificazione esplicita, potrebbe scalare anch'esso in futuro se vuoi più profondità.
- I 3 Build di uno stesso Type (es. Atk/Elemental Atk/Special Atk della stessa spada) condividono **le stesse stat grezze** — cambia solo `rarity`/G/H/I, coerente con F/Bonus che nella scheda era già unito per Type.

**Bug preso e corretto prima di consegnare**: le Difensive generate inizialmente avevano `canDefend = true` ma **nessuna `defenseZones`** — la parata sarebbe sempre fallita silenziosamente (nessun crash, il for-each su lista vuota semplicemente non trova mai un overlap). Corretto riusando la geometria hardcoded dei 3 esempi originali (Scudo/Broquel/Sai), identica su tutte le 14 varianti dello stesso Type.

**Verificato con un harness** (non incluso nel progetto): tutti i 1120 id risolvono (`get()` non ritorna mai `null`), nessun "id sconosciuto" da `MaterialRegistry`, ogni Difensiva ha `defenseZones` popolato. `short_sword_iron` (lo starter dato da `Npc_HumanRedWorker`) e `buckler_basic` (l'arma del `MON_Goblin`) sono stati sostituiti con gli id reali del nuovo catalogo (`short_sword_atk_0_10_common`, `broquel_defense_0_10_common`) — erano gli unici due riferimenti nel codice ai vecchi id a mano, ora rimossi.

**Aperto per il prossimo giro**: nessun oggetto ha ancora una gemma/incantesimo di serie (tranne il nuovo starter, che ne ha una a scopo dimostrativo) — il catalogo genera solo le stat grezze + gli stub G/H/I, non riempie `gemme[]`/`incantesimi[]`. `affilatezza`/`rifiniture` piatti a 1 ovunque, quindi il moltiplicatore di qualità non differenzia ancora nulla all'interno dello stesso pezzo — se vuoi varianti "logora/pregiata" dello stesso oggetto in futuro, è lì che andrebbe.

## 26. Full Set reali + de-stub di 10 effetti armatura (10/64)

**Meccanismo di rilevamento set** — esattamente come richiesto, senza controllare "1000 case":
- `Armor.armorSetID` (nuovo campo): 1=Guardia/2=Veggente/3=Voto (upper-body, per Build Defense/Elemental Def/Special Def), 4=Passo/5=Ombra (gambe, Speed/Elusivity). Assegnato dal generatore in base a Type+Build.
- `Player.activeArmorSetID()`: confronta i 12 `armorSetID` dei 12 slot armatura (Helmet...Sabaton) — se anche UNO slot è vuoto o ha un id diverso, ritorna `-1` (nessun set completo). Confronto diretto tra interi, non la concatenazione-stringa descritta a parole ("111111111111"): stesso risultato, senza costruire ed esaminare una stringa — se preferisci letteralmente la stringa per motivi di debug/UI in futuro, è una riga da cambiare.
- `CombatState.fireRaritySpecialEffects()` (richiamato da `firePreTurnEffects`/`firePostTurnEffects`, quindi ogni turno): per ogni pezzo equipaggiato, se **Mitico** risolve ed esegue sempre `mythicUniqueStub` (nessuna condizione, come richiesto — "esclusi dalla logica del set"); se il set da 12 è completo, risolve ed esegue **anche** `fullSetOrStyleStub` di OGNI pezzo d'armatura (non un bonus condiviso: ogni pezzo ha il suo testo diverso, il set è solo il cancello che li sblocca tutti insieme).

**10 testi de-stubbati** (tolto "STUB — ", registrati in `SpecialEffectRegistry` con logica vera): Elmo Saldo (stordimento annullato 1×/combat), Presa Ferrea/Presa Eterna/Mano di Ferro (immunità disarmo — implementata **diversamente**: non è un `SpecialEffect` a sé, è un controllo diretto `CombatState.isDisarmImmune()` sui 2 punti reali dove il disarmo scatta nel codice, perché un turn-boundary non è il punto giusto per un evento che succede "quando qualcuno tenta di disarmarti"), Occhio Limpido (immune Accecamento — non implementata la parte "vedi debolezze nemico", serve un sistema di rivelazione UI), Corpo di Roccia (immune Rottura), Scaglia di Pietra (Rottura → +10% DIFESA temporaneo, `Player.applyTemporaryMultiplier()` nuovo, nessun sistema di durata quindi "temporaneo" = per il resto del combattimento), Parafulmine (Scossa trasferita al nemico, stessa durata), Respiro di Brace (previene il tick di Infiammazione 1×/combat e cura il 10% invece), Sangue Puro (ogni debuff dura al massimo 1 round).

**54 testi restano STUB** — non per pigrizia, mancano i sistemi sottostanti:
- **Sanguinamento** (Gola Serrata e derivati) — nessuno stato "bleed" esiste
- **Maledizione/Marchio/Drenaggio/Silenzio/Confusione** — l'INTERO set Voto (8 Full Set + 8 Mitici = 16 testi) li usa, NESSUNO di questi sistemi esiste in `ElementSystem`/`CombatState`
- **Critici** — nessun sistema di colpi critici esiste (Gomito Fermo, Corazza Intera e i loro mitici)
- **Rallentamento/spinta/schivata/iniziativa/furtività/danno da terreno** — nessuno di questi esiste (Ancora di Vento, Corrente Contraria, l'intero set Ombra, l'intero set Passo)
- **Rivelazione UI** ("vedi la barra vita esatta", "prevedi la prossima mossa") — richiede un sistema di UI/informazioni non ancora costruito
- **Riflessione proiettili, resource/carica accumulabile, hook "on-heal" centralizzato** — richiedono agganci che non esistono ancora nel codice di combattimento

Vedi `combat/SpecialEffectRegistry.java` per l'elenco esatto dei 10 implementati — il commento in testa al file rimanda qui per il resto.

**Non testato in-game** (il sandbox non ha gli asset grafici reali, `GamePanel` fallisce al costruttore per le tile mancanti) — verificato invece: compilazione pulita, pipeline item→stat (harness precedente), e la logica di `activeArmorSetID()`/`fireRaritySpecialEffects()` per ispezione diretta del codice. Da confermare sulla tua macchina: equipaggia 12 pezzi con lo stesso `armorSetID` e verifica che gli effetti Full Set scattino.

## 27. Un bonus solo per set + ristrutturazione completa dei "components"

**Full Set semplificato**: un solo bonus scelto per ciascuno dei 5 set (non più uno a pezzo) — `CombatState.SET_BONUS_TEXT[armorSetID]`. Guardia=Elmo Saldo, Veggente=Occhio Limpido, Voto=Purga Lenta (nuovo: rimuove il debuff più vecchio a fine round), Passo=Suola Salda (nuovo: immune a Naturalizzazione/Infangato), Ombra=Vuoto (resta STUB, nessun sistema di schivata esiste). "Presa Ferrea" (era la scelta iniziale per Guardia) scartata, ripristinato "STUB —" sul suo testo — restano solo i 2 Mitici (Presa Eterna/Mano di Ferro) per l'immunità al disarmo, invariati dal set.

**Ristrutturazione completa dei campi grezzi** (Weapon/Armor/Jewelry) — terza e ultima rigenerazione del catalogo:

- **Armatura**: `metallo`(singolo) → `metalli[3]` (fino a 3 metalli, sommati, stesso ruolo → DIFESA). `sostegno`/`legamenti` invariati.
- **Gioielli**: `legame` da `String` (solo flavor) a `int` (ID) → **VITA**, sommato IN PIÙ al bonus di `metalli` (non lo sostituisce). Nuovo `MaterialRegistry.jewelryLegame()`.
- **Armi da mischia + Coltello da Lancio** ("stessa composizione"): **Pomello**(`pomo`, fisso, invariato) — **Manico**(`manico`, senza tabella, invariato) — **Guardia**(`guardia`, **significato cambiato**: non più DIFESA, ora riduce direttamente la probabilità di essere disarmato, "come IRL" — vedi sotto) — **Lama**(`lama`, nuovo nome, durabilità, senza tabella) — **Punta**(`punta`, **unica fonte di danno ora**, sostituisce taglio/contundente/peso). `taglio`/`contundente`/`peso` **rimossi** dalla classe.
- **Armi a distanza vere** (Arco Corto/Lungo, Balestra — non il Coltello): **Corda**(`corda`, nuovo campo, danni → ATTACK) — legamenti (durabilità, invariato, riusato) — **Struttura**(`struttura`, nuovo campo, fisso → **PRECISIONE**, StatType che esisteva già e non avevo ancora usato).
- **Scudi**: stesso sistema di Armor ma gestiti come Weapon — `metalli[3]`(nuovo, → DIFESA) + `legamenti` (durabilità, **non** mobilità come in Armor — per le Difensive resta senza tabella).

**Conseguenza segnalata**: `comboTypes`/`comboDamage()` (bonus Mazza Chiodata/Ascia) **rimossi** — dipendevano da `taglio`+`contundente`, non hanno più senso con Punta come unica fonte di danno uniforme su tutte le armi da mischia.

**Guardia → resistenza al disarmo**, implementata sui 2 punti reali dove il disarmo scatta (Inondazione + `disarmChance` dell'attaccante): `CombatState.effectiveDisarmChance(baseChance, target)` sottrae `target.weaponOf().guardia * 10` punti percentuali dalla chance base (10 punti di Guardia = -10%), clampato a 0. Non passa da MaterialRegistry: è un modificatore diretto, non un bonus %/StatType.

**Rigenerato un'ultima volta**: `WeaponRegistry.java` (546), `ArmorRegistry.java` (448), `ShieldRegistry.java` (126) — verificato con harness dedicato: 1120/1120 risolvono, 0 errori `MaterialRegistry`. Compilazione pulita, 60 file.

**Ancora aperto**: `manico` (controllo) e `lama` (durabilità) restano ID senza tabella — nessuna stat "controllo"/"durabilità" esiste ancora nel gioco, stesso discorso di `sostegno`(Armor)/`legamenti`(Weapon, distanza+scudi). L'usura durante la parata (`CombatState`, colpo su punto debole/rigido) ora tocca solo `legamenti`, non più anche `metallo` (che è diventato la stat DIFESA, non va più consumato come durabilità).

## 28. STATO ATTUALE COMPLETO — sistema equipaggiamento/combattimento (riferimento)

Sezione di riferimento, non un'altra voce di log — consolida come funziona TUTTO oggi dopo le sessioni §24-27+correzioni. Le sezioni precedenti restano come storico di come ci si è arrivati; questa è la fotografia attuale.

### Catalogo (1120 oggetti)
`WeaponRegistry`/`ArmorRegistry`/`ShieldRegistry`, generati da script (`generate_equip.py`, non nel progetto) a partire dalla scheda Equip. Id: `{type}_{build}_{levelband}_{tier}` (es. `claymore_atk_30_40_masterwork`). 546 armi (mischia+distanza), 448 armature, 126 Difensive. Ogni `get(id)` crea un'istanza nuova. I 3 Build dello stesso Type condividono le stesse stat grezze — cambia solo `rarity`/G/H/I.

### Struttura di un Item
- `rarity` (COMMON/HIGH_QUALITY/MASTERWORK/MYTHIC) + 3 stub testuali cumulativi per tier: `synergyBonusStub`(G, da High Quality), `fullSetOrStyleStub`(H, da Masterwork), `mythicUniqueStub`(I, solo Mythic) — testo preso 1:1 dalla scheda, **non letto da nessuna logica di gioco per il Full Set** (vedi sotto), lo è invece `mythicUniqueStub` via `resolveMythicUniqueEffect()`.
- `itemPath`/`combatItemPath`: STUB dichiarati "da non toccare", nessun asset.
- `statBonusPercent` (Map, %) → `Player.applySlotBonus()`. La qualità (`affilatezza`/`rifiniture`, +25% per livello, `Item.QUALITY_STEP`) scala **solo i bonus dell'oggetto** dentro `statBonusPercent`, arrotondati. `statMultiplier` (moltiplicatore sull'intera stat del Player) non è più popolato da nessun oggetto: resta per buff/debuff futuri. Dettagli in §30.
- `preTurnEffects`/`postTurnEffects`/`activeEffects`: liste di `SpecialEffect` risolte da `gemme[]`/`incantesimi[]`/`rocce[]` via `SpecialEffectRegistry` (mai più un bonus % diretto, sempre un effetto speciale).

### Componenti per categoria (schema finale)
- **Armatura** (upper 8 Type + gambe 4 Type): `metalli[3]`(ID somma, → DIFESA) — `legamenti[1]`(ID → VELOCITA) — `sostegno[1]`(ID, **nessuna tabella ancora**). `rifiniture` = livello qualità. Durabilità 100 per tutti, vedi sotto.
- **Gioielli**: `metalli`(bonus diretto, non ID, → EFFICIENZA) + `legame`(ID → **VITA**, si somma a metalli, non lo sostituisce) + `gemma`/`rocce` (→ SpecialEffect).
- **Armi da mischia + Coltello da Lancio** (famiglia MELEE): `pomo[1]`(→ VELOCITA) — `manico[1]`(ID → resistenza al disarmo, `manicoBonus`) — `guardia[1]`(ID → resistenza al disarmo, `guardiaBonus`) — `lama[1]`(ID, **nessuna tabella ancora**) — `punta[1]`(→ ATTACK; nelle **MAZZE** `punta[0]` è il **peso diretto**: Mazza Chiodata/Ascia danno = peso, Spadone danno = ATK + peso, vedi `Weapon.baseDamage()`). `taglio`/`contundente`/`peso`/`comboTypes` rimossi.
- **Armi a distanza vere** (famiglia RANGED: Arco Corto/Lungo, Balestra): `corda[1]`(ID → **PRECISIONE**) — `legamenti[1]`(ID → **ATTACK**) — `struttura[1]`(**non un ID**: durabilità massima diretta, 100 oggi).
- **Difensive** (famiglia SHIELD: Scudo/Broquel/Sai — gestite come Weapon): `metalli[3]`(→ DIFESA) — `legamenti[1]`(ID, **nessuna tabella ancora**). Gli altri campi di Weapon esistono ma non hanno tabella per questa famiglia: ignorati.
- **Come si risolvono**: `nomecomponent[slot] = ID`, indici da 0. `ComponentRegistry` ha una tabella per (famiglia, component): ID → nome in gioco, StatType, valore. L'ID 4 di `pomo` non è l'ID 4 di `corda`. Un component senza tabella (o con ID 0) non produce nulla. Slot: `metalli`=3, tutti gli altri=1. Vedi §30.

### Formula stat
`finale = flat × pct(type) × moltiplicatore(type)`, i 3 fattori calcolati separatamente poi moltiplicati in quest'ordine — `Player.recalculateStats()`.

### Resistenza al disarmo
`CombatState.effectiveDisarmChance(baseChance, target)`: `chance effettiva = baseChance - (guardiaBonus + manicoBonus dell'arma del bersaglio) × 10` (valori risolti da `ComponentRegistry`; ID e valore coincidono, 0..14), clampato a 0. Applicata ai 2 punti reali dove il disarmo scatta (Inondazione, base 100%; `disarmChance` dell'arma attaccante). In più: **immunità totale** (bypassa il calcolo) per 2 Mitici (`Presa Eterna`/Vanbrace, `Mano di Ferro`/Gauntlet) via `CombatState.isDisarmImmune()` — controllo diretto sul testo dell'item, non un `SpecialEffect`.

### Durabilità — stato reale
`Item.durability`/`maxDurability`: 100 per tutti gli oggetti, tranne archi e balestre (`struttura[0]`, 100 oggi). Parte da -1 (non inizializzata), si riempie al primo `computeBonusPercent()` (cioè all'`equip()`). Si consuma per **tipologia** (`Item.tipologie`, `DamageTypes`) e nella parata; a 0 l'oggetto è rotto. Regole complete in §30.

### Sistema SpecialEffect (`combat/SpecialEffect.java` + `SpecialEffectRegistry.java`)
`Timing{PRE_TURN, POST_TURN, ACTIVE}`. `PRE_TURN`/`POST_TURN` richiamati da `CombatState.beforeTurn()`/`afterTurn()` per il Player (i mostri non hanno equip). `target` nell'`execute()` è sempre il vero avversario (non l'entità stessa) — un effetto "su di sé" ignora `target`, uno offensivo (es. "Eco") lo usa. `ACTIVE` per le abilità (`Ability.getSpecialAction()` delega qui).

### Sistema Set — upper e gambe SEPARATI (corretto dopo un errore)
**Superato da §30** (`armorSetID`/`activeArmorMatchGroup()` non esistono più): ora `upperID`/`lowerID`/`acceptedUpperIDs`/`fullID`, controllati da `Player.activeFullID()`. I 5 bonus scelti nella lista qui sotto restano come descrizione dei set:
- Guardia → Elmo Saldo (reale: primo stordimento annullato 1×/combat)
- Veggente → Occhio Limpido (reale: immune Accecamento)
- Voto → Purga Lenta (reale: rimuove il debuff più vecchio a fine round)
- Passo → Suola Salda (reale: immune Naturalizzazione/Infangato)
- Ombra → Vuoto (**ancora STUB**, nessun sistema di schivata esiste)

Gli altri 27 testi "Full Set" (32 totali − 5 scelti) restano SOLO flavor text sui pezzi — mai letti a runtime. Mitico invariato: ogni pezzo Mitico spara SEMPRE il proprio `mythicUniqueStub`, indipendente dal set.

### STUB aperti — elenco completo
- **54 testi Armor Full Set/Mitico non scelti/non implementabili** — mancano sanguinamento, maledizione/marchio/drenaggio/silenzio/confusione (l'intero set Voto originario), critici, rallentamento/spinta/schivata/iniziativa/furtività/danno da terreno, rivelazione UI, riflessione proiettili, resource/carica accumulabile, hook "on-heal" centralizzato
- **`synergyBonusStub`** (colonna G) — non risolto da nessuna logica, mai richiamato
- **`itemPath`/`combatItemPath`** — nessun asset, dichiarati "da non toccare"
- **`lama`, `sostegno`, `legamenti` di armi/scudi**: slot presenti ma senza tabella in `ComponentRegistry` — non danno nessun bonus (non sono più durabilità). `manico` ha un effetto (disarmo) ma nessun oggetto lo imposta.
- **Testi Full Set mancanti per le nuove combinazioni delle gambe**: Passo+Voto e Ombra+Voto (`Armor.fullSetStubAlt` è null → nessun effetto).
- **Nessun oggetto del catalogo ha più di un metallo/gemma** (`metalli[1]`/`[2]` sempre 0, `gemme[]`/`incantesimi[]`/`rocce[]` vuoti tranne lo starter del player) — crafting/socketing non esiste ancora
- **`Inventory.java`** resta 2 liste vuote, non agganciato a `Player`
- **Loot non agganciato a un inventario**: `LootTable` genera il drop (§30) ma `Inventory` non è collegato al Player: resta in `CombatState.loot` + messaggio a schermo.

### TODO aperti (oltre a quelli in §5)
- [ ] Decidere i bonus di `lama`, `sostegno`, `legamenti` (armi/scudi) e riempire le tabelle in `ComponentRegistry`
- [ ] Scrivere i testi Full Set per Passo+Voto e Ombra+Voto (`fullSetStubAlt`)
- [x] Calcolo PRECISIONE e mitigazione danni % — fatto, vedi §31
- [x] `dodgeChance` del mostro (`CombatState`, schivata nel minigioco: `clamp(evasion - precisione|velocità, 0, 100)`) usa ancora la vecchia sottrazione, non `CombatMath.hitChance()` — **fatto 0.22.4: ora 1 − hitChance(precisione del player, evasione del mostro), §44.5**
- [ ] Sistema mostro → player: la spec dice "100 - precisione" (o "100 - precisione/2") di mancare di base; oggi tutti i colpi usano `CombatMath.hitChance()`. Il caso "precisione/2" non è implementato (quando vale?)
- [ ] Scala 0..1000: le stat base del Player (DIF 20, ATK 40, PRE 100) sono molto sotto il tetto e non crescono col livello (il Player non ha un livello: `level` = 0, trattato come 1). Serve decidere la crescita per livello
- [x] Inventario nel libro: sistema e UI fatti (§34); icone e immagini dettagliate degli oggetti non esistono ancora
- [ ] UI del libro: definita solo l'Inventario; il resto delle aree verrà dopo
- [ ] Calendario: fissare giorni per mese/stagione/anno e come scorre il tempo (§32)
- [ ] Equipaggiare/usare gli oggetti dall'Inventario (oggi il libro li mostra soltanto), consumare i consumabili, raccolta nel mondo (`Player.pickUpObject()` ora mette l'oggetto tra gli oggetti quest e lo toglie dalla mappa — fatto 0.22.4)
- [ ] Icone (`itemPath`) e immagini dettagliate (`combatItemPath`/`detailPath`) per gli oggetti: nessun registry le imposta, quindi le celle icona e il riquadro dettagli restano vuoti
- [ ] Orologio di gioco: `GameCalendar` ha solo giorno e mese correnti fissi (nessun avanzamento); i mesi sono da 30 giorni come la griglia del calendario nel libro. Proposta di funzionamento in discussione (§35.4) **Decisi il 26/11**: mese 10 giorni, stagione 30, anno 120, giorno di gioco = 10 minuti reali (§44.4); l'orologio che li fa avanzare resta da fare.
- [ ] Decidere se uno Scudo/Broquel a durabilità 0 deve ancora poter parare (oggi sì; il Sai no)
- [ ] Se si rigenerano i registry con `generate_equip.py` (non nel progetto, scrive ancora il formato vecchio): rilanciare in ordine `Fix_Weapon_registry.py`, `convert_components.py`, `convert_sets_corda.py`, `generate_loot_index.py` (cartella `pp`), oppure aggiornare `generate_equip.py` al formato nuovo
- [x] Rimuovere `MaterialRegistry` (non lo usa più nessuno) — **fatto dall'utente (0.22.3)**
- [ ] Usura per danni da status/veleno/sanguinamento (oggi solo i colpi che passano da `dealDamage()`)
- [ ] Full Set: valutare se gli altri 27 testi scartati vadano recuperati per qualcos'altro (bonus secondari? scelte alternative per il player?) o restano solo lore
- [ ] Sistema di crafting/socketing per riempire `metalli[1]`/`[2]` e le gemme sugli oggetti generati

## 29. Correzione set (gruppo, non ID esatto) + varianti per livello + 1-mano/2-mani

> Il meccanismo dei set descritto qui (`armorSetID`, `MATCH_GROUP`, `activeArmorMatchGroup()`) è stato **sostituito in §30**. Restano validi varianti per livello e vincolo 1-mano/2-mani.

**Bug del giro scorso corretto**: avevo sbagliato a capire la richiesta iniziale — il modello "ogni pezzo il proprio bonus, gate su tutti e 12 uguali" **era quello giusto**, il problema è che 3 archetipi upper (Guardia/Veggente/Voto) e 2 gambe (Passo/Ombra) su ID numerici diversi non potevano MAI dare "12 uguali" per nessuno dei 5 (una gamba non avrà mai lo stesso numero di un pezzo Veggente). Scelta l'opzione 2 tra le due proposte (non scartare Passo/Ombra, considerarli "stesso set" per il confronto pur restando effetti diversi):

- `Armor.armorSetID` resta 1-5 (Guardia/Veggente/Voto/Passo/Ombra), invariato — ogni pezzo mantiene la propria identità e il proprio testo.
- **Nuovo**: `Player.activeArmorMatchGroup()` — confronta tutti e 12 gli slot per **GRUPPO** (`MATCH_GROUP[armorSetID]`), non per id esatto: Guardia(1) e Passo(4) sono gruppo 1, Veggente(2) e Ombra(5) sono gruppo 2. **Voto(3) resta da solo** — 3 upper non si dividono in pari con 2 gambe, quindi un Voto da 12 pezzi non può esistere matematicamente: resta al massimo un set da 8 (solo upper-body). Segnalato, non nascosto.
- `CombatState.fireRaritySpecialEffects()` torna al modello per-pezzo: ogni Armor equipaggiata spara il proprio `fullSetOrStyleStub` SE `activeArmorMatchGroup() >= 0`. `SET_BONUS_TEXT` (un bonus solo per set, §27) **rimosso** — era la simplificazione sbagliata.
- `isDisarmImmune()`/`hasActivePassive()`: **Presa Ferrea** (Full Set Guardia, disarmo) rimessa in gioco — ora raggiungibile per davvero col gruppo Guardia+Passo. `hasActivePassive` confronta per prefisso (`startsWith`), non più uguaglianza esatta (vedi sotto, il testo ora varia per fascia).

**Elenco dei 32 testi Full Set** (con che gruppo può/non può completare un 12) generato in `full_set_texts.txt` (non nel progetto, consegnato a parte) — utile come riferimento anche se non è la lista "sostituiti" (quella serviva solo per l'opzione 1, non scelta).

**Varianti per fascia di livello** (Masterwork/Mitico, solo Armor — High Quality invariato come richiesto): stesso testo base + un suffisso diverso per fascia, generato in `generate_equip.py` — `" (livello Iniziato)"`/`" (livello Adepto)"`/`" (livello Maestro)"` per Masterwork (20-30/30-40/40-50), `" (livello Ascendente)"`/`" (livello Trascendente)"` per Mitico (30-40/40-50). `SpecialEffectRegistry` confronta ora per **prefisso** (`id.startsWith(...)`), non più uguaglianza esatta, così le 9 abilità già implementate (Elmo Saldo, Occhio Limpido, Corpo di Roccia, Scaglia di Pietra, Parafulmine, Respiro di Brace, Sangue Puro, Purga Lenta, Suola Salda) continuano a risolvere su tutte le fasce senza dover registrare 3 (o 2) case per ognuna. Verificato: stessa abilità, testo diverso, `get()` la trova su tutte le fasce.

**Armi: vincolo 1-mano/2-mani** (nuovo, `Weapon.WeaponSubtype.twoHanded`) — decisione mia per Type, non dettata dalla scheda (che non distingue 1/2 mani):
- **1 mano** (compatibili con un'OffHand): Short Sword, Long Sword, Whip, Mace, Axe, Coltello da Lancio
- **2 mani** (nessuna OffHand possibile): Claymore, Spear, Pique, Scythe, Short Bow, Longbow, Crossbow

`Player.equip()`: equipaggiare un'arma a 2 mani in MainHand libera forzatamente l'OffHand (se occupata); equipaggiare un'OffHand mentre impugni un'arma a 2 mani libera forzatamente la MainHand. Nessuno stato incoerente possibile (mai arma a 2 mani + scudo insieme).

**Non testato in-game** (stesso limite del sandbox, nessun asset grafico) — verificato: compilazione pulita, 1120/1120 id, testo che varia per fascia e si risolve comunque, `full_set_texts.txt` generato correttamente. Il vincolo 1-mano/2-mani è verificato solo per lettura del codice (richiede un `Player` vero per un test end-to-end).

## 30. Sessione 0.19.5-0.19.7 — Component per famiglia, durabilità per tipologia, set upper/lower, loot

### 30.1 Component: `nome[slot] = ID`, tabelle per famiglia (`items/ComponentRegistry.java`)
- Quattro famiglie: **MELEE** (SPADE/MAZZE/LANCE + Coltello da Lancio), **RANGED** (Arco Corto/Lungo, Balestra), **SHIELD** (Scudo/Broquel/Sai), **ARMOR**. `Weapon.family()` la ricava dal sottotipo.
- Ogni tabella è (famiglia, component) → ID → `Entry(nome, StatType, valore)`. I valori di partenza sono quelli delle vecchie tabelle di `MaterialRegistry` (che oggi non usa più nessuno: si può togliere). I nomi sono segnaposto ("Punta 3"); solo `manico[0]=3` ha un nome vero ("Manico rifinito").
- Tabelle oggi: MELEE `punta`→ATTACK (ID 1..14), `pomo`→VELOCITA (ID -3..4), `guardia`/`manico`→resistenza al disarmo (0..14, valore=ID). RANGED `corda`→PRECISIONE (1..14: +1…+21), `legamenti`→ATTACK (1..14: +2…+42). SHIELD `metalli`→DIFESA. ARMOR `metalli`→DIFESA, `legamenti`→VELOCITA.
- Slot (lunghezza dell'array, indici da 0): `metalli`=3, tutti gli altri=1.
- **Due component NON sono ID ma valori diretti**: `punta[0]` delle MAZZE (peso) e `struttura[0]` delle armi a distanza (durabilità). Il peso nei registry è ricavato dal vecchio bonus (Mazza/Ascia = 40 + 40×bonus%, Spadone = 40×bonus%, 40 = `baseAttack` del Player): valori da bilanciare.
- Nei registry gli ID 1..14 sono lo **scaglione** (0-10 Common … 40-50 Mythic).
- Gli scudi usavano `guardia` come scala della DIFESA ma il codice leggeva `metalli`: ora `metalli[0]` (prima la DIFESA degli scudi era 0).

### 30.2 Calcolo e qualità
Ordine: ID dei component → `statBonusPercent` dell'oggetto → qualità (`affilatezza`/`rifiniture`, ×(1 + 0,25×livello) **solo sui bonus dell'oggetto**) → stato di durabilità → `Player.applySlotBonus()` → `recalculateStats()` (flat × % × moltiplicatore). Danno diretto: `Weapon.baseDamage(atk)` usato da `Ability.offense()` per `NormalAttack` del Player; le altre abilità restano a formula.

### 30.3 Durabilità per tipologia
- `Item.tipologie` (String[]): l'oggetto perde durabilità solo per azioni di un tipo in lista. Tipi in `items/DamageTypes.java`: 7 elementi (PHYSICAL, LIGHT, FIRE, WATER, EARTH, WIND, ELECTRIC) + EROSION, POISON, PSYCHIC, BLEEDING, CURSE. Default armature e scudi: tutti gli elementi + EROSION (non POISON/PSYCHIC/BLEEDING/CURSE). Default armi: solo PHYSICAL.
- Aggancio: `CombatState.wearEquipment()`, chiamata nel ramo "colpo andato a segno" di `dealDamage()`: -1 ai pezzi del bersaglio (armature e scudo) e all'arma di chi attacca (solo il Player) se il tipo (`DamageTypes.of(abilityElement)`, NONE = PHYSICAL) è nella loro lista; poi `Player.refreshEquipment()`. La parata scala `durability` e ricalcola.
- **Durabilità scesa ma > 0**: armature e scudi perdono **solo la DIFESA**, in proporzione (`durabilità/massimo`); gli altri bonus restano. Armi: nessun calo.
- **A 0 (rotto)**: armi e Sai → nessun bonus, nessun effetto (preTurn/postTurn/mitico), inutilizzabili (`Weapon.isUnusable()`: `weaponOf()` le ignora, un mostro con un Sai rotto non para più). Scudo/Broquel a 0 → come armi e Sai: `isUnusable()` è ora `isBroken()` per tutti, quindi non parano più (**deciso 26/11**; prima continuavano a parare). Armature → nessun bonus assoluto, nessun effetto, nessun mitico, ma **tengono gli ID**, quindi i bonus set restano attivi. Riparazione (forgia): non esiste ancora.
- **Usura da parata**: `Item.lose(amount)` (inizializza la durabilità se l'oggetto non è mai passato da `equip`, come lo scudo del Goblin) e annuncia "broke!" (arma/Sai) o "is worn out!" (scudo) **una sola volta**, quando tocca 0. Bug corretto in 0.19.9: lo scudo del Goblin partiva da durabilità -1 e risultava rotto alla prima parata, con il messaggio ripetuto a ogni parata.
- La parata di oggi è del **mostro** che para l'attacco del Player (`monster.resolveDefenseWeapon()`): la durabilità consumata lì è quella dell'arma del mostro. La parata del Player (mostro → player) non esiste ancora.
- Non consumano durabilità: danni da status (veleno, sanguinamento…) e i danni che non passano da `dealDamage()`. Quantità fissa: 1 per colpo.

### 30.4 Set: upper e gambe separati (`Armor`, `Player.activeFullID()`)
- Upper (8 pezzi): `upperID` 1=Guardia, 2=Veggente, 3=Voto. Gambe (4 pezzi): `lowerID` 1=Passo, 2=Ombra + `acceptedUpperIDs[2]` (2 su 3). Registry: Passo accetta Guardia e Voto, Ombra accetta Veggente e Voto (**scelta mia**, da confermare).
- `fullID(upperID, lowerID)` = upperID×10 + lowerID (Guardia+Passo=11, Voto+Ombra=32).
- `Player.activeFullID()` ≠ 0 se: 12 slot pieni, 8 upper con lo stesso upperID, 4 gambe con lo stesso lowerID, e le gambe accettano quell'upperID. Pezzi rotti contano. Altrimenti 0. Un set valido sblocca i bonus come prima (`fireRaritySpecialEffects()`/`hasActivePassive()`).
- Una gamba ha due testi full set: `fullSetOrStyleStub` per `acceptedUpperIDs[0]`, `fullSetStubAlt` per `[1]` (oggi null per le combinazioni con Voto: nessun effetto finché non vengono scritti). `Armor.fullSetStubFor(upperID)` sceglie il testo.
- Combinazioni valide oggi: 11, 31, 22, 32. Non più valide: Guardia+Ombra e Veggente+Passo.

### 30.5 Loot (`items/LootTable.java`, `items/LootIndex.java`)
- `CombatState.onVictory()` → `LootTable.roll(monster.level, rng)` → un pezzo (armi, scudi e armature insieme), messaggio "Loot: …" e `CombatState.loot`.
- Gruppo: 50% stessa fascia di livello del mostro, 35% inferiore, 15% superiore; mai una fascia che parte oltre 10 livelli sopra il mostro; gruppo vuoto → salta e gli altri si rinormalizzano (es. mostro lvl 5: 77% stesso, 23% superiore). Scaglione nel gruppo: peso 0,5^(ordine rarità) (più è rara, meno è probabile). Pezzo nello scaglione: equiprobabile. Livello oltre 50 conta come ultima fascia.
- `LootIndex.java` è **generato** da `generate_loot_index.py` (elenco degli ID dei 3 registry): rilanciarlo quando i registry cambiano.

### 30.6 Script e come si usano
- Nella cartella `pp`: `Fix_Weapon_registry.py`, `convert_components.py`, `convert_sets_corda.py`, `generate_loot_index.py`. Ordine, da root progetto: `python pp/<script>.py src/items` (l'ultimo non prende l'argomento). Si possono rilanciare senza danni. Verificato: partendo dai registry della 0.19.4 la catena riproduce esattamente i registry attuali.

### 30.7 Come si fa per…
- **Dare un bonus a un component**: una riga `table(Family.X, Part.Y, StatType.Z, primoID, valori…)` in `ComponentRegistry`; poi assegnare l'ID agli oggetti (`w.lama[0] = 2;`).
- **Rinominare un ID**: `rename(Family.MELEE, Part.MANICO, 3, "Manico rifinito");` in fondo al blocco `static`.
- **Nuovo tipo di danno**: una costante in `DamageTypes` (+ aggiungerla al default se armature/scudi devono consumarsi con quel tipo); poi far restituire il tipo dal punto che infligge il danno.
- **Nuovo pezzo/gamba**: nel registry impostare `upperID` (upper) oppure `lowerID` + `acceptedUpperIDs` (gambe); poi rilanciare `generate_loot_index.py`.

### 30.8 Verifica e limiti
Compilazione pulita su tutto il progetto; test con oggetti veri: durabilità per tipologia (veleno non consuma, fuoco sì), DIFESA proporzionale e a 0, rottura arma, `activeFullID()` con 12 pezzi reali (11, 22, 31, 32 validi; 12 e 21 no; elmo diverso, gamba mancante e pezzo rotto verificati), distribuzione del loot su 200.000 estrazioni. **Controlli dopo 0.19.8: tutti gli 1120 oggetti risolvono senza ID sconosciuti, equip completo → 20 refresh → tutto tolto riporta le stat al valore da nudo (nessuna deriva). **Non testato in-game** (nessun asset grafico nel sandbox).

## 31. Mitigazione danni % e precisione (0.19.10) — `combat/CombatMath.java`

Tutte le formule e le costanti da tarare sono in `CombatMath`. Le stat sono considerate nella scala **0..1000** (`STAT_MAX`): nelle formule un valore sopra 1000 conta come 1000; le stat stesse non vengono tagliate (`maxLife` base è già 1000).

### 31.1 Mitigazione danni
- `mitigazione = MAX_MITIGATION × DIF / (DIF + K)`, con `K = K_BASE + K_PER_LEVEL × livello di chi ATTACCA` (livello 1..100; il Player ha level 0 → conta 1). Costanti: `MAX_MITIGATION` 0,90 — `K_BASE` 20 — `K_PER_LEVEL` 8.
- Più alta è la DIFESA più mitiga (mai oltre 90%); a parità di DIFESA, un attaccante di livello più alto mitiga meno. Esempi: DIF 20 vs lv 2 → 32%; DIF 100 vs lv 10 → 45%; DIF 500 vs lv 50 → 49%; DIF 1000 vs lv 1 → 87,5%, vs lv 100 → 49,5%.
- Sostituisce il vecchio `max(10, ATK - DIF)`: ora `danno = max(1, round(ATK × (1 - mitigazione)))`, per tutte le abilità (`Ability.use()`; le abilità da `2×ATK` mitigano il doppio grezzo). Per le abilità elementali la DIFESA è `getElementDefense(e)`.
- Il calo proporzionale di DIFESA per durabilità (§30.3) agisce a monte: abbassa la DIF, quindi la mitigazione scende con la curva.

### 31.2 Precisione ed elusione
- `probabilità di colpire = precisione / precisione massima`, con `precisione massima = PRECISION_BASE_MAX + EVASION_WEIGHT × elusione del bersaglio` (100 e 1,0). L'elusione **non toglie precisione: alza il tetto**.
- Poi, come ultimo passaggio: `mancare = min(1, (1 - colpire) × MISS_MULTIPLIER)` con `MISS_MULTIPLIER` 1,25 (+25% **relativo**: 33% di base → 41,7%).
- Conseguenza: se la probabilità di mancare di base è 0 (precisione ≥ 100 + elusione) resta 0 — il +25% relativo non crea mancati dal nulla.
- La precisione passa prima dai moltiplicatori di stato (`ElementSystem.precisionMultiplier()`: Stordimento, Accecamento, Deviazione). `forceHit` e deviazione/blocco a distanza restano come prima.
- Esempi: prec 100 vs el 0 → 100%; prec 100 vs el 50 → 58,3%; prec 200 vs el 250 → 46,4%.

### 31.3 Da sapere
- `PRECISION_BASE_MAX` = 100 mantiene "precisione 100 = nessun mancato a elusione 0" come oggi. Se il tetto base deve essere 1000, va cambiata la costante **e** alzata la precisione base dei personaggi.
- Verificato con test numerici (monotonia, limiti, Monte Carlo 1.000.000 di tiri: 50,01% contro 50,00% atteso); non provato in-game.

## 32. Calendario — ragionamento (da decidere)

Nel codice non c'è ancora nessun calendario: nessun giorno/mese/stagione/anno è definito (esiste solo il segnalibro Calendario nel libro, con i sottoargomenti vuoti). I principi di design (quest Critiche mai perdibili, Maggiori che ricorrono a ogni ritorno della stagione, Flavor perdibili, avvisi diegetici) sono nella sezione "Calendar System" più sopra.

### 32.1 Requisiti
- Eventi **ogni stagione**, "mancabili", ma il player **non deve aspettare troppo**.
- Ipotesi dell'utente: **20 minuti reali per giorno di gioco**, stagioni da **30 giorni**.

### 32.2 Calcolo (20 minuti reali per giorno di gioco)
| Giorni per stagione | Durata stagione | Durata anno (4 stagioni) |
|---|---|---|
| 30 | 10 ore | 40 ore |
| 15 | 5 ore | 20 ore |
| 12 | 4 ore | 16 ore |
| 10 | 3 ore 20 | 13 ore 20 |
| 7 | 2 ore 20 | 9 ore 20 |

- Un evento legato a una stagione, se perso, torna alla ricorrenza successiva della stessa stagione: con 30 giorni sono **40 ore** di attesa. Una finestra di 3 giorni vale circa 1 ora reale.
- Valutazione: 30 giorni sono troppi se il player non può far avanzare il tempo (dormire, aspettare); in quel caso l'attesa diventa una sua scelta e vanno bene. Senza salto del tempo: **10-12 giorni per stagione** (evento perso → 13-16 ore).

### 32.3 Ancora aperto
- Il tempo scorre anche in combattimento, nei menu e nei dialoghi? Il player può farlo avanzare (dormire/aspettare)?
- Decisi questi punti: fissare **giorni per mese, mesi per stagione, stagioni per anno** come costanti in un unico punto del codice (come `CombatMath` per le formule) e riportarli qui.
- Il calendario serve alla pagina Quest dell'Inventario (§33.5): giorno attuale, giorno o scadenza di consegna.

## 33. UI del libro — Inventario (struttura decisa dall'utente, 0.19.9)

Per ora la UI definita comprende **solo l'Inventario**; il resto del libro verrà dopo. Nelle immagini il **tronco** è un'icona "jolly" provvisoria: quando verranno segnate le coordinate di dove vanno le icone, verrà tolto e lasciato vuoto.

### 33.1 Regole generali
- Cinque zone: **gear, consumabili, precious, quest, risorse**. Sfondo: `res/ui/book_inventory/book_inventory_<zona>.png` (272x272).
- **Pagina sinistra = lista**: ogni oggetto dell'inventario crea una nuova riga (immagine-riga `list_inventory_<zona>.png`), riempita con le info generali.
- **Pagina destra**: per tutte le zone **tranne risorse**, accanto al bookmark va l'**immagine dettagliata** dell'oggetto (quella usata in combattimento). Premendo una riga (deve esistere/essere attiva) i dettagli dell'oggetto appaiono nella facciata destra del libro.
- **Paginazione**: una pagina contiene un numero fisso di righe; se gli oggetti sono di più si disegna una pagina successiva con quelli in eccesso (es. 10 righe per pagina, 11 oggetti → la pagina 2 ha l'undicesimo).

### 33.2 Gear (equipaggiamento)
- **Sinistra**: icona dell'item, nome, peso, durabilità (in %). Una riga per oggetto (`list_inventory_gear`).
- **Destra**: lista di stat (`list_inventory_gear_stats`): nome abbreviato del valore (es. `FIRE-ATK`) e valore. **Moltiplicatori, valori percentuali e valori flat sono considerati separatamente**: una riga diversa per ciascuno.

### 33.3 Consumabili
- **Sinistra**: icona, nome, valore in oro, quantità (`list_inventory_consumable`).
- **Destra**: nome del valore e valore che si guadagna (nuova riga per ogni valore, `list_inventory_consumable_stats`). Se ha **effetti speciali**, sotto va una riga con a sinistra l'icona dell'effetto speciale e a destra la descrizione breve (`list_inventory_consumable_effects`; la prima riga esiste già, sostituirla o aggiungerla è equivalente: non è un punto critico).

### 33.4 Precious
- **Sinistra**: icona, nome, valore, quantità (`list_inventory_precious`).
- **Destra** (righe fisse, non se ne creano): valore attuale **nella zona geografica attuale** (icona monete); di quanto **aumenta** il valore se sale la reputazione (ratio, icona faccia verde); di quanto **si abbassa** (ratio, icona faccia rossa).
- **In basso**: le **città scoperte**, con nome della città e valore in quella città. La bandiera a sinistra viene sostituita tramite shader con i colori della bandiera della città, con dentro le iniziali della città. Una nuova riga per città (`list_inventory_precious_reputation`).

### 33.5 Quest
- **Sinistra**: icona dell'item, nome, quanto tempo rimane per portarlo (vuoto se non ci sono giorni), icona del tipo di quest dove l'item va portato (`res/ui/icon_<tipo>quest.png`: arch, niche, special, todo, world; 10x10).
- **Destra**: il **calendario**, con il giorno ATTUALE in **verde**; il giorno di consegna in **ROSSO** se è un giorno preciso, **GIALLO** se si può portare entro il giorno X; tutti i giorni **neri** se l'oggetto non va portato. Sotto, la descrizione dell'item nella storia. Dipende dal Calendar System (§32).

### 33.6 Risorse
- **Sinistra**: icona della risorsa, nome, valore (sempre fisso, senza storia delle reputazioni), quantità (`list_inventory_resources`).
- **Destra**: nessuna immagine dettagliata. A **sinistra del bookmark**: breve descrizione dell'item e come si ottiene. A **destra del bookmark**: come usarlo e come ottenerlo (materiale primario → strumento di lavorazione → materiale rifinito, `list_inventory_resources_process`). **Sotto**: una tabella di tutti gli usi; nelle celle vanno i **materiali finiti** (se per ottenere la staccionata serve legno grezzo → legno rifinito → staccionata, nella tabella va comunque "staccionata").

### 33.7 Immagini (in `res/ui/book_inventory`, dimensioni in pixel)
- Sfondi: `book_inventory_gear/consumable/precious/quests/resources.png` — 272x272.
- Righe: `list_inventory_gear` 94x8, `list_inventory_gear_stats` 39x8, `list_inventory_consumable` 95x8, `list_inventory_consumable_stats` 39x8, `list_inventory_consumable_effects` 98x7, `list_inventory_precious` 94x8, `list_inventory_precious_reputation` 98x8, `list_inventory_quests` 94x8, `list_inventory_resources` 94x8, `list_inventory_resources_process` 41x8.
- Altro: `tag_masterwork.png`, `tag_mythic.png` (40x11, presenti nella cartella, uso da definire); icone quest in `res/ui`.

### 33.8 Cose che il codice oggi non ha (da tenere presenti)
- **Peso** nel gear: nessun oggetto ha un peso (c'è solo `punta[0]` delle MAZZE, che è un danno, e `Armor.weightClass`): serve decidere quale valore mostrare.
- **Righe stat**: gli oggetti hanno solo bonus percentuali (`statBonusPercent`); `statMultiplier` non è più popolato da nessun oggetto (§30.2) e non ci sono bonus flat (a parte il peso-danno delle MAZZE). I nomi abbreviati si ricavano dagli `StatType` (`FIRE_ATK` → "FIRE-ATK").
- **Precious**: servono zone geografiche, valori per città, reputazione e città scoperte: nessuno di questi sistemi esiste ancora.
- **Quest**: il calendario della pagina destra e i "giorni rimasti" richiedono il Calendar System (§32); gli oggetti quest oggi non hanno una scadenza.
- **Inventario**: collegato al Player in §34 (il loot ci finisce); resta da fare equipaggiare dalla lista.
- **Geometria** (le coordinate dell'Inventario sono ora misurate sulle immagini, §34.3): in `BookUI`, `PAGE_CONTENT_RECT` (95,20,165,230) è un placeholder che cade **sopra** al libro. Sul vecchio `book_inventory.png` le pagine di pergamena misuravano x 22..133 (sinistra) e 137..249 (destra), y 86..247, con il bookmark dell'Inventario in alto a destra (x ~186..202, y ~86..152): da rimisurare con le immagini nuove prima di segnare le coordinate. La pagina Quest già esistente usa lo stesso rettangolo, quindi il suo testo è fuori posto.

## 34. Inventario nel libro: sistema e UI (0.19.10)

Implementa §33 sulle immagini in `res/ui/book_inventory`. File: `book/InventoryPage.java` (UI), `items/Inventory.java`, `items/Consumable|Precious|QuestItem|Resource|StatLine|ItemInfo.java`, `main/GameCalendar.java`, `items/InventoryDemo.java`. Applicato con `apply_inventory.py` (modifica `Item`, `Player`, `CombatState`, `GamePanel`, `BookUI`).

### 34.1 Dati (`items/Inventory`, `Player.inventory`)
- `gear` (Item posseduti ma non indossati), `consumables`, `precious`, `questItems`, `resources`. `addGear(item)` calcola i bonus dell'oggetto (`computeBonusPercent()`), quindi stat e durabilità sono pronte.
- **Gear**: la lista del libro mostra prima gli oggetti **indossati** (nome preceduto da `*`) e poi quelli dell'inventario.
- `Consumable`: nome, oro, quantità, `gains` (StatLine), `effects` (icona + descrizione breve). `Precious`: valore attuale, quantità, `reputationUp`/`reputationDown` (ratio: 0,15 = 15%), `cities` (nome, valore, colore bandiera ARGB). `QuestItem`: nome, descrizione, `Kind` (ARCH, NICHE, SPECIAL, TODO, WORLD → `res/ui/icon_<tipo>quest.png`), `mustDeliver`, `deliverDay`, `exactDay` (rosso se true, giallo se false), `.due(giorno, esatto)`. `Resource`: nome, valore fisso, quantità, descrizione, icone della catena (`processPrimary` → `processTool` → `processRefined`, `processExtra` in alto) e `usages` (icone dei materiali **finiti**, una per cella).
- `StatLine`: nome abbreviato (da `StatType`: VIT, ATK, DEF, SPD, EVA, PRE, EFF; gli elementali `FIRE_ATK` → "FIRE-ATK") + `Kind` FLAT / PERCENT / MULTIPLIER = **una riga per ciascuno**. `ItemInfo.statLines(item)`: flat (solo il peso-danno delle MAZZE, "DMG") poi percentuali poi moltiplicatori.
- `Item.weight` (nuovo, 0 = non impostato). `ItemInfo.weightText`: nelle MAZZE il peso è `punta[0]`, altrimenti `weight` o "-". Durabilità in lista: numero 0..100 senza il segno %, per stare nella cella.
- `main/GameCalendar`: `day`, `month`, `DAYS_PER_MONTH` = 30.
- **Loot**: `CombatState.onVictory()` aggiunge il drop a `player.inventory` (`addGear`).

### 34.2 UI (`book/InventoryPage`)
- Sottoargomenti (bookzone): **0 gear, 1 consumabili, 2 quest, 3 risorse, 4 preziosi** (ordine delle icone nel bookmark, verificato sulle immagini). Sfondo per zona: `GamePanel.getCurrentBookImage()` usa `book_inventory_<zona>.png`.
- Pagina sinistra: una riga (`list_inventory_<zona>.png`) per oggetto, **10 righe per pagina** (`ROWS_PER_PAGE`), passo 13 px (`ROW_PITCH`); le pagine sono dinamiche (`GamePanel.bookPageCount()`, ← → per girarle). Click su una riga = selezione (bordo rosso); i dettagli appaiono a destra. Il cambio di zona azzera la selezione.
- Pagina destra: **gear/consumabili** — righe stat (`list_inventory_*_stats`, una per StatLine), riquadro dettagli (immagine usata in combattimento: `combatItemPath`, altrimenti `itemPath`); i consumabili hanno anche le righe effetto. **Preziosi** — valore attuale, ratio su (faccia verde) e giù (faccia rossa), città scoperte con bandiera ricolorata via `PaletteSwap` (bianco → colore) e iniziali. **Quest** — data `gg/mm`, calendario 6x5 (giorno attuale verde; consegna rossa/gialla; tutto nero se non va consegnato), nome e descrizione. **Risorse** — nome e descrizione a sinistra del bookmark, catena di lavorazione a destra, tabella usi 10x8 sotto (nessun riquadro dettagli).
- Il testo segnaposto delle celle nome (quest, risorse) e della data viene coperto prima di scrivere.

### 34.3 Geometria (pixel dell'immagine 272x272, misurati sulle tue immagini)
- Riga 1 a sinistra: x 31, y 106 (icona 31..39, nome da x 42, celle A e B diverse per zona: costanti `NAME_W`, `A_X/A_W`, `B_X/B_W`). Righe stat: x 146, y 106, passo 10 (cella nome 24, valore 13 a x 172). Riquadro dettagli: (204,104) 38x38. Effetti consumabile: x 144, y 162, passo 9. Preziosi: celle valore (161,97), (161,107), (161,116) 23 px; città da y 169 passo 10 (nome x 157, valore x 228). Quest: etichetta data (156,98) 27x9; calendario: 30 segni 3x2, colonne passo 5, righe y 114 + 5r con x di partenza 154,154,153,152,151; nome (153,156) 37x8; descrizione (145,166) 98x75. Risorse: nome (154,98) 29x6, descrizione (146,107) 37x36, icone catena (202,110), (219,110), (236,110) e (218,93) in alto; tabella: celle 8x8 da (149,169), passo 9, 10 colonne x 8 righe.
- **Bug trovato**: `GamePanel` caricava ancora `/ui/book.png` e `/ui/book_X.png`, ma le immagini erano state spostate in `res/ui/book_general`: il libro non si disegnava. Percorsi corretti.

### 34.4 Dati di prova
`items/InventoryDemo.ENABLED = true` riempie l'inventario all'avvio (`GamePanel.setupGame()`) con oggetti di esempio; **metterlo a false** per partire vuoti.

### 34.5 Come si fa per…
- **Aggiungere un oggetto a una zona**: `player.inventory.consumables.add(new Consumable(...))` (idem per `precious`, `questItems`, `resources`; gear con `addGear`).
- **Cambiare righe per pagina o passo**: `ROWS_PER_PAGE`, `ROW_PITCH` in `InventoryPage`. **Spostare un elemento**: costanti in cima alla classe e nei metodi `draw*` (coordinate in pixel dell'immagine originale).
- **Icone**: impostare `itemPath` (e `detailPath`/`combatItemPath`) con un percorso risorsa, es. `/items/spada.png`; le immagini mancanti restano vuote (un solo errore in console per percorso).

### 34.6 Non fatto / da sapere
- Equipaggiare o usare dalla lista, navigazione da tastiera delle righe (solo mouse). I tag di rarità sono in §35.
- Il calendario non avanza: `GameCalendar.day`/`month` sono fissi finché non c'è un orologio di gioco (§32).
- Il peso esiste solo per le MAZZE e per gli Item con `weight` impostato.
- Verificato con render headless (768x576) di tutte e 5 le zone con dati di prova, selezione, seconda pagina, bandiere ricolorate e calendario; test di regressione su 1120 oggetti, set e loot → inventario. Non provato in-game (mouse e animazione di pagina).

## 35. Tag di rarità, oggetti ricevuti e proposta calendario (0.19.11)

### 35.1 Tag di rarità nel dettaglio gear (`InventoryPage.drawRarityEffect`)
- Solo oggetti **Masterwork** e **Mythic** (gli altri non hanno tag). Pagina destra: il **tag** (`tag_masterwork.png` / `tag_mythic.png`, 40x11) sotto le righe stat (massimo 8 righe stat quando c'è un tag); la placca arancio del tag porta il nome dello stile/set (es. "Assalto", "Guardia") o "Mitico"; in basso, a tutta larghezza (x 145..243, da y 186), il **riquadro con la descrizione dell'effetto**.
- Testi: Masterwork → `fullSetOrStyleStub`; Mythic → `fullSetOrStyleStub` + `mythicUniqueStub` come due paragrafi. Dal testo si tolgono `[Tag]` e `STUB — `; il nome sulla placca è la parte dopo i due punti dell'etichetta ("[Full Set: Guardia]" → "Guardia").
- Per le gambe si mostra il testo principale (`fullSetOrStyleStub`); il testo alternativo col Voto (`fullSetStubAlt`) oggi è vuoto.

### 35.2 Oggetti ricevuti (hook)
- `Player.obtain(item)`: l'oggetto entra in `inventory.gear` e notifica `QuestEventType.COLLECT` con `targetId` = **id del registry** (nuovo campo `Item.id`, impostato da `LootTable.create(id)`, es. `short_sword_atk_0_10_common`). `Player.receiveItem(item, equipNow)`: come `obtain()` + messaggio "Hai ottenuto: <nome>!" + equip opzionale.
- Prima l'NPC (`Npc_HumanRedWorker`) equipaggiava la spada direttamente: non risultava posseduta né veniva notificata (COLLECT non era collegato). Ora usa `receiveItem(LootTable.create(...), true)`; il messaggio mostra il nome vero dell'oggetto.
- Loot di fine combattimento: `CombatState.onVictory()` passa da `obtain()`.
- `Player.equip()` toglie l'oggetto dallo zaino e rimette nello zaino il pezzo sostituito (anche quello liberato dalla regola 1-mano/2-mani); `unequip()` manda il pezzo nello zaino. La durabilità resta quella dell'istanza.
- Per una quest "ottieni X": condizione `require COLLECT <id registry>` in un branch del file quest (vedi §9).

### 35.3 Come si fa per…
- **Far ricevere un oggetto da un NPC o come ricompensa**: `gp.player.receiveItem(items.LootTable.create("<id>"), false)` (true per indossarlo subito).

### 35.4 Libro che non disegnava niente: causa e ripiego opzionale
- Causa reale: la run configuration di IntelliJ aveva **"Build project" disattivato in *Before launch***, quindi `out/production/DD2D/ui` conteneva ancora solo i vecchi file piatti e mancavano `ui/book_general/` e `ui/book_inventory/`. `GamePanel` stampava `ERROR: resource not found: /ui/book_general/book.png` e `BookUI.draw()` non disegnava nulla. Riattivato "Build project": risolto.
- Ripiego **opzionale** (`main/ResourceStreams`, `apply_resources.py`): prima il classpath, poi `res` (se il gioco parte dalla root), con un `AVVISO` una volta per file. Non serve se la build è corretta.

### 35.5 Calendario — proposta (da confermare con l'utente)
Contesto: la griglia del libro ha **30 giorni per mese** (6 x 5) e l'etichetta `gg/mm`; l'utente vuole **eventi ogni stagione, "mancabili", ma senza far aspettare troppo** il player; aveva ipotizzato 20 minuti reali per giorno e stagioni da 30 giorni (= 10 ore a stagione, 40 ore l'anno: troppo). Inconsistenza da chiarire: se la stagione è 1 mese l'anno ha 4 mesi e l'etichetta `17/05` non esiste.
- **Struttura proposta**: 12 mesi x 30 giorni = 360 giorni; 4 stagioni da 3 mesi (90 giorni).
- **Orologio**: 1 giorno = **3 minuti** di gioco attivo (mese 1h30, stagione 4h30, anno 18h). Alternative: 2 min → stagione 3h, anno 12h; 5 min → 7h30 e 30h. Gira **solo in esplorazione**; fermo in libro, menu, dialoghi, combattimento.
- **Salto del tempo**: letto/locanda → mattino dopo; "aspetta N giorni" in punti precisi (es. bacheche). Rende l'attesa una scelta.
- **Eventi** (`CalendarEvent`, registry stile `Ability`): finestra (inizio + durata, 5-10 giorni = 15-30 minuti reali) + `repeatEveryDays`. **Critiche**: non scadono mai (la finestra resta aperta finché non le compi). **Maggiori**: ripetizione ogni 45 giorni (2 volte a stagione, ritorno dopo ~2h15 se perse). **Flavor**: perdibili, ritorno annuale. Avvisi **diegetici** (bacheche, NPC, cantastorie); nel libro compare solo ciò che hai scoperto.
- **Quest**: `QuestItem.deliverDay` diventa una data assoluta (giorni totali); i "giorni rimasti" sono la differenza di giorni assoluti; la pagina Quest mostra il mese corrente e, se la scadenza è in un altro mese, i giorni rimasti nella cella.
- **Codice (dopo conferma)**: `GameCalendar` con `totalDays` + contatore di tick, `update()` chiamato dal ciclo in esplorazione, `dateLabel()` `gg/mm`, listener `onNewDay`/`onNewSeason`; `CalendarEvent` + registry. Un solo numero basta anche per il salvataggio.
- **Da decidere**: minuti per giorno, mesi per stagione (3 o 1), se combattimento e menu consumano tempo, se esiste il salto del tempo. L'utente applicherà modifiche e le comunicherà.

## 36. RNG dei tiri di caratteristica — ragionamento (da decidere)

Nessuna delle due meccaniche è nel codice: questo § raccoglie il confronto fatto con il consiglio (5 pareri indipendenti, revisione incrociata, sintesi). Non è una decisione.

### 36.1 Le due meccaniche (come descritte dall'utente)
- **Marble-sack**: il numero scelto dal random viene reso una frazione e non può essere riscelto, dando ogni volta risultati diversi. Finché la probabilità desiderata (in frazione) non avviene, il sacco non viene riempito.
  - Lettura da confermare: estrazione senza reinserimento, es. 25% = 1 biglia su 4, sacco rimesso a nuovo ogni 4 estrazioni (come il 7-bag di Tetris). Cosa faccia scattare il riempimento va fissato.
- **Equilibratore**: a ogni tiro di caratteristica si ottiene un punteggio P, con O = risultato ottenuto e N = minimo necessario per passare: `P += O - N`.
  - P < 0: si tira un random tra 0 e -100; se il risultato è maggiore di P si aggiunge P ponderato (M) con `M = O × P / 100`, arrotondato per eccesso.
  - P > 0: si tira un random tra 0 e 100; se il risultato è minore di P si sottrae P ponderato (M) con la stessa formula, arrotondato per difetto.
  - Da chiarire: a cosa si aggiunge/sottrae M (presumibilmente a O).

### 36.2 Esito del confronto
- **Dove concordano i pareri**: il Marble-sack è molto più semplice da specificare, implementare e testare (deterministico nella convergenza, adatto a test unitari). L'Equilibratore ha più complessità: stato persistente P, due rami con arrotondamenti diversi, comportamento probabilistico difficile da riprodurre in debug. Entrambi sono ancora sotto-specificati.
- **Dove si scontrano**: (1) Marble-sack: rischio di "contare le biglie" e prevedere gli esiti a fine sacco, trascurabile finché lo stato resta interno e invisibile al giocatore. (2) Equilibratore: potenziale come "pacing dinamico" della difficoltà contro il rischio anti-skill (chi ottimizza il build viene penalizzato da P positivo, e la percezione di equità cala).
- **Punti ciechi emersi**: (1) i due sistemi risolvono problemi diversi: il Marble-sack riduce la varianza a breve termine (serie fortunate/sfortunate), l'Equilibratore corregge lo scarto cumulativo lungo tutta la partita (rubber-banding). (2) Entrambi aggiungono stato nascosto che andrà salvato (oggi non esiste salvataggio, vedi §5: "Load Game" è uno stub). (3) Manca lo scope di P: globale, per personaggio o per singola caratteristica; mancano anche reset e limiti.
- **Raccomandazione del consiglio** (non decisa dall'utente): usare il Marble-sack come utility generica e riusabile (tiri di caratteristica, ma anche loot e spawn). Mettere in pausa l'Equilibratore; eventualmente riprenderlo come assist ristretto (es. solo boss) se il playtest mostra un problema reale.
- **Primo passo suggerito**: fissare per iscritto un esempio numerico concreto del Marble-sack (granularità, trigger di riempimento) prima di scrivere codice.

### 36.3 Ancora aperto
- Qual è il problema da risolvere: serie di tiri sfortunati (varianza) o deriva cumulativa lungo la partita?
- Marble-sack: granularità della frazione, quando il sacco si riempie, se il sacco è uno per caratteristica, per soglia N o globale.
- Equilibratore: scope di P, reset, limiti (clamp) su P, a cosa si applica M, arrotondamenti.
- Salvataggio dello stato nascosto (sacco o P) quando verrà implementato il salvataggio.
- Se si implementa: costanti e formule in un unico punto, come `CombatMath` (§31).

---
## 37. Quest v2: situazioni X.Y, branch, zone di mappa (0.20.1)

### 37.1 Cosa è cambiato
- Il package `quest/` ora ha `Quest` (nuovo: situazioni `X.Y`, branch, `history`), `QuestBranch`, `QuestBranchType` (FAST/IMPORTANT/COINCIDENT), `QuestCondition`, `QuestLoader` (legge `res/quests/<id>.txt`). **Da eliminare a mano**: `QuestStep.java` e `QuestRegistry.java` (le quest sono file, non più `case`); se restano, la build fallisce con "constructor Quest cannot be applied" in `QuestRegistry.java`. `QuestEventType`/`QuestTier`/`QuestState` restano (solo commenti aggiornati).
- `QuestManager`: usa `QuestLoader.load()` + `Quest.start()`; avanzamento rilevato confrontando `step`/`micro` prima e dopo `notify()`.
- `BookUI.drawQuestPageContent()`: la pagina 1 scorre `q.history` invece di `q.steps`.
- Nuovo `main/LocationZone` + `res/maps/locations.txt`; `EventHandler.checkLocations()` (da `checkEvent()`) notifica `REACH_LOCATION` all'ingresso nella zona e può avviare una quest. `locationEvent()`/`questStartEvent()` rimossi.
- `goblin_bounty` migrata in `res/quests/goblin_bounty.txt` (stesso comportamento: la uccisione del Goblin la completa; il libro mostra in più la riga finale "Goblin sconfitto").

### 37.2 Decisioni di design (dall'utente)
- Y = situazione ATTUALE, non chi hai incontrato; la storia avanza solo cambiando X. Nessuna Y assegnata a mano sulle condizioni.
- IMPORTANT: priorità appena completato il requisito (nell'esempio "inizia a esplorare la libreria", cioè l'ingresso). FAST/COINCIDENT: il requisito non cambia Y, serve la continuazione.
- Gli eventi di luogo, compresi "ha esplorato X", passano dall'event manager (`EventHandler`), non da `CUSTOM` a mano.

### 37.3 Verifica
Compilazione dell'intero albero (come `build.bat`) dopo l'eliminazione di `QuestStep`/`QuestRegistry`; 23 scenari sul motore (`esempio_mistero` + quest di prova con gate AND a due continuazioni e FAST + file malformati) e un test d'integrazione headless con `GamePanel` reale: ingresso in zona avvia e avanza la quest, nessuna doppia notifica restando nella zona, rientro che non riavvia, zone di un altro mondo ignorate, zone sovrapposte in ordine di file, `goblin_bounty` da file. Non provato in una partita vera (nessuna zona reale sulla mappa).

### 37.4 TODO / da decidere
- Definire le zone reali (`locations.txt`) quando ci sono libreria/villaggio/ecc. sulla mappa.
- Salvataggio vero (contatori + condizioni soddisfatte, oltre a `nome.X.Y`).
- Commento stantio in `Entity.givesQuestId` ("id di QuestRegistry"): ora è il nome del file in `res/quests/`.

---
## 38. Mondo generato da seed + Perlin noise — progetto (da implementare)

> **Approccio superato dal §39 (14/11)**: l'utente ha chiesto di cambiare approccio (noise per cella -> varianti grafiche low/medium/high, senza cambiare gli id delle tile). Restano validi di questo § la classe `PerlinNoise` in `tile/`, il `SeedCreator` (38.3), gli accorgimenti sul reticolo di Perlin (`scale` non tonda, offset) e le misure. Il resto (numero casuale per cella, controllo vicini, tabella `tile_variants.txt`) non serve più.

Nessuna riga di codice è nel progetto: questo § raccoglie la spiegazione data in chat (06-12/11) con gli snippet, per la prossima sessione. Gli snippet e le misure sono stati provati in test a parte (value noise sulla vera `world01.txt`, poi il `PerlinNoise` dell'utente su 50x50).

### 38.1 Decisioni dell'utente
- **Stesso seed = stesso mondo**: nessun `Math.random()`; il numero casuale per cella è un hash di `(seed, col, row)`.
- **"Seed creator" ricavato dai numeri della matrice**: nessun header e nessuna modifica alla struttura del file né della matrice, così funziona anche per mappe generate in futuro (dungeon casuali: stesso `mix` sulla loro matrice).
- **Noisemap = il generatore Perlin dell'utente** (`PerlinNoise`: permutazione di 256 valori mescolata con `new Random(seed)`, rumore 2D "improved", somma di ottave). Il suo `main` che scrive un PNG diventa uno strumento di debug.
- **Scopo della soglia 0,80**: evitare che una cella al centro di un terreno orientato verso +1 diventi ≈ -1 per colpa del random. Si risolve con un **controllo sui vicini** (§38.5), che sostituisce la soglia 0,80 (può restare come extra).
- A ogni caricamento la tile cambia in base a `numero casuale × peso`; i pesi vanno da -1 a +1.
- Le spiegazioni indicano **dove va ogni pezzo e perché**, non solo "X si fa così".
- **Seed dai numeri della matrice: confermato (13/11)**; l'utente accetta che modificare una tile rimescoli le varianti.
- **Nessuna normalizzazione** dei pesi (decisione del 12/11, vedi 38.4). **Parametri del Perlin**: l'utente si fida dei valori di partenza (38.4, spiegati in parole in 38.10).

### 38.2 Dove va ogni pezzo (flusso di `TileManager.loadMap()`)
```
loadMap(path)                                         [TileManager, esiste già]
 ├─ legge le righe -> mapTileNum, e per ogni numero    [TileManager: 1 riga in più nel ciclo]
 │     h = SeedCreator.mix(h, valore)
 ├─ seed = SeedCreator.finish(h, colonneLette, righeLette)   [SeedCreator, nuova]
 ├─ WorldGenerator.apply(mapTileNum, seed, colonneLette, righeLette)   [WorldGenerator, nuova]
 │     per ogni cella con varianti: peso Perlin -> casuale (hash) -> controllo vicini -> scrive la variante
 └─ loadSpecialTilesUsedInMap()                        [TileManager, invariato]
```
- **`TileManager.loadMap()`**: unico punto che conosce file e matrice; accumula l'hash mentre legge e chiama il generatore. Nessuna logica di rumore.
- **`tile/SeedCreator`** (nuova, piccola): `mix` e chiusura dell'hash; riusabile dai dungeon generati (`fromMatrix`).
- **`tile/PerlinNoise`** (la classe dell'utente, spostata in un file a sé nel package `tile`): `noise(x, y)` deterministico per seed.
- **`tile/WorldGenerator`** (nuova): campionamento delle ottave (`weight`), tendenza dei vicini, applicazione. Non sa nulla di file né di `GamePanel`.
- **`res/maps/tile_variants.txt`** (dato): una riga per tile base con le varianti. Nuove tile (id 26-99) registrate in `getTileImage()`; l'erba è animata, quindi le sue varianti usano `setupAnimated`.
- **Prima di `loadSpecialTilesUsedInMap()`**: il generatore tocca solo id < 100; le varianti sono tile normali già caricate da `getTileImage()`.

### 38.3 Seed creator (snippet)
```java
static long mix(long h, int v) {                 // in loadMap(), per ogni numero letto
    h ^= v + 0x9E3779B97F4A7C15L + (h << 6) + (h >>> 2);
    h *= 0xFF51AFD7ED558CCDL;
    return h ^ (h >>> 32);
}
long h = 1469598103934665603L;                   // prima del ciclo di lettura
h = mix(h, value);                               // nel ciclo, dopo parseInt
long seed = mix(mix(h, colsRead), rowsRead);     // finish()
```
- L'hash si calcola sulla matrice **originale** (prima delle varianti) e **mentre si legge**, non sull'array intero: `mapTileNum` non viene azzerata tra un load e l'altro e `world2.txt` ha 30 righe con 44-46 colonne, quindi le celle non lette contengono il mondo precedente e farebbero dipendere il seed dall'ordine di caricamento.
- Provato: stessa matrice -> stesso seed.
- **Conseguenza**: modificare **una sola tile** cambia il seed e quindi la distribuzione delle varianti (misurato: ~33% delle celle cambia esito). Se non è accettabile, nell'hash entrano solo larghezza/altezza (o un id del mondo).

### 38.4 Il Perlin dell'utente: cosa va bene e cosa cambia per il gioco (misurato su 50x50)
- **Va bene**: deterministico (stesso seed -> stessi valori, seed diverso -> valori diversi); `new Random(seed)` accetta qualsiasi `long`, quindi l'hash del seed creator va bene.
- **`scale` e dimensioni**: nel generatore `scale = 100` è pensato per 800 pixel. Su 50 tile equivale a una sola chiazza quasi piatta (misurato: valori da -1,21 a 0,00). Per i tile `scale` è la dimensione delle chiazze **in tile**: circa 10-15 (provato 12,7).
- **Zeri del reticolo**: il Perlin vale esattamente 0 sui punti interi del reticolo. Campionando a coordinate intere con `scale = 8`, le ottave 4 e 5 (frequenze 8 e 16) valgono 0 in **tutte** le 2500 celle (la 3 in 1405); anche a `col + 0.5` l'ottava 5 resta a 0. Rimedio: `scale` non tonda (non potenza di 2) e un offset costante non "bello" (es. 0,37): con `scale = 12,7` e offset 0,37 gli zeri esatti scendono a circa 0-5 celle per ottava.
- **Normalizzazione: decisione dell'utente = nessuna.** Sommando le ottave senza dividere per le ampiezze (3 ottave, `persistence 0,5`) il generatore dà già valori tra circa -1,1 e +0,9, cioè la scala voluta per i pesi: il min-max sulla mappa cambierebbe quasi nulla. Basta un `clamp` a [-1,+1] (i valori sforano di poco). Cosa **non** fare: dividere per la somma delle ampiezze, che schiaccia i pesi (la soglia 0,8 non scatterebbe quasi mai). Misurato su 30 seed, 50x50: `|w| > 0,80` in 0-3,7% delle celle e zona polarizzata (`|t| >= 0,5`) in 9-22%; su 200x200 i valori si stabilizzano (1,3-2,0% e 12-15%). Su mappe piccole la variabilità tra seed è alta.
- **Conseguenza**: i pesi restano una **funzione pura** di `(seed, col, row)`: niente primo passo, niente array con bordo, niente min/max. La tendenza dei vicini si calcola chiamando `weight()` 9 volte (misurato: ~6 ms per una mappa 50x50, ~28 ms per 200x200).
- **Il numero casuale per cella non è Perlin**: il Perlin è liscio (celle vicine simili); il casuale deve essere indipendente cella per cella: resta `hash(seed ^ sale, col, row)`.
- Parametri di partenza (da tarare guardando il PNG): `scale 12,7`, 3 ottave, `persistence 0,5`, `lacunarity 2`, offset 0,37.
```java
double sx = (col + OFFSET) / scale, sy = (row + OFFSET) / scale;   // OFFSET = 0.37
double amp = 1, freq = 1, h = 0;
for (int i = 0; i < octaves; i++) {
    h += noise.noise(sx * freq, sy * freq) * amp;                  // come nel generatore
    amp *= persistence; freq *= lacunarity;
}
double w = Math.max(-1, Math.min(1, h));                           // peso in [-1,+1], nessuna normalizzazione
```

### 38.5 Controllo vicini (snippet)
La "tendenza" del terreno è la media dei pesi della cella e degli 8 vicini, calcolata al volo con `weight()` (funzione pura, vale anche fuori mappa: nessun controllo sui limiti):
```java
double t = 0;
for (int dx = -1; dx <= 1; dx++)
    for (int dy = -1; dy <= 1; dy++) t += weight(col + dx, row + dy);
t /= 9;
// dopo p = r * w[c][r]:
if (Math.abs(t) >= 0.50 && Math.signum(p) != Math.signum(t)) continue;   // incoerente col terreno: resta uguale
```
- **Perché la sola soglia 0,80 non basta**: `r` ha segno, quindi in zona polarizzata (`|t| >= 0,5`) il segno di `p` è opposto al terreno in circa il **51%** dei casi, qualunque sia `|w|` (value noise su `world01`: 275 celle su 535; Perlin senza normalizzazione, 50x50, 30 seed: 45-53% delle celle polarizzate, che sono il 9-22% del totale). La soglia 0,80 tocca solo le celle con `|w|` alto e lascia passare per esempio `w = 0,79`, `r = -1`.
- Con il controllo vicini le celle incoerenti restano com'erano; in zona polarizzata la variante può avere solo il segno del terreno.
- Alternative: ripiegare il segno invece di scartare (`p = Math.abs(r) * w`), oppure `r` non negativo (nessun ribaltamento per costruzione, ma `|p| <= |w|`).

### 38.6 Regola completa e dati
```java
double r = hash(seed ^ 0x5DEECE66DL, col, row);           // -1..+1, ripetibile, indipendente dall'ordine
double p = r * w;
int[] v = VARIANTS[id];                                   // es. erba -> {0, 26, 27}; null = non toccare
if (v == null) continue;
// controllo vicini (38.5)
if (p >  0.30) id = v[1]; else if (p < -0.30) id = v[2];  // soglie da tarare
mapTileNum[col][row] = id;
```
- Soglie sul prodotto: `|r × w| > 0,30` scatta nel 10-19% delle celle secondo il seed (Perlin senza normalizzazione, 3 ottave, 50x50); `r × w` si concentra vicino a 0, quindi la soglia controlla direttamente quante tile cambiano.

### 38.7 Cosa non toccare
Tile speciali (id >= 100), bordi hand-placed di acqua/sabbia (id 6-25: spezzerebbero le coste), celle di `tile_links`, zone di `res/maps/locations.txt`, punti di spawn di NPC/mostri/oggetti. Prima iterazione sicura: solo varianti con lo **stesso `collision`** della tile base (nessun sentiero bloccato).

### 38.8 Stato lavori (file completi consegnati il 14/11, da applicare al progetto)
| Passo | Dove | Stato |
|---|---|---|
| Seed creator (`mix`, `finish`) | `tile/SeedCreator` + 1 riga in `loadMap()` | Da fare |
| `PerlinNoise` in `tile/` | file a sé, dalla classe dell'utente | Da fare (classe già scritta dall'utente) |
| `weight(col,row)` + casuale per cella + controllo vicini + variante | `tile/WorldGenerator` | Da fare |
| Tabella varianti | `res/maps/tile_variants.txt` + asset nuovi 26-99 | Da fare (servono le immagini) |
| Chiamata nel caricamento | `TileManager.loadMap()` | Da fare |
| Strumento di debug: PNG della noisemap sul mondo vero | `NoiseMapGenerator` adattato | Facoltativo |

### 38.9 Aperto
- Quali varianti per l'erba (id 26, 27...) e se le immagini esistono già: senza asset non si può partire dal gioco, solo dal prototipo con tile segnaposto.
- Famiglie di tile e varianti (quali asset nuovi servono), soglie su `p` e `t`, `scale`/ottave.
- Scartare o ripiegare il segno nelle celle incoerenti (38.5).

### 38.10 Spiegazione in parole (richiesta dell'utente, 12/11)
- **Perlin, i parametri**: immagina tre fogli trasparenti di colline sovrapposti. `scale` = larghezza in tile delle colline più grandi (12,7: chiazze di terreno larghe una dozzina di tile; più alto = chiazze più larghe). `octaves` = quanti fogli (3: grandi, medie, piccole). `lacunarity` = di quanto si restringe ogni foglio rispetto al precedente (2 = metà larghezza). `persistence` = quanto è alto ogni foglio rispetto al precedente (0,5 = metà altezza, quindi i dettagli fini sono discreti). `OFFSET` = piccolo spostamento del punto di lettura per non finire sui punti del reticolo dove il Perlin vale esattamente 0.
- **Seed dai numeri della matrice**: l'hash prende tutti i numeri del file mappa e ne ricava un solo `long`. Stesso file -> stesso seed -> stesso mondo. Un buon hash cambia completamente se cambia anche un solo numero (effetto valanga): se modifichi **una** tile nel file, il seed è un altro, la noisemap è un'altra e le varianti si ridistribuiscono (misurato: ~33% delle celle cambia esito). Quindi mentre disegni la mappa, ogni modifica rimescola dove compaiono le varianti; a mappa finita il mondo è stabile. Alternative se disturba: hash di sola larghezza e altezza (stabile alle modifiche, ma due mappe della stessa taglia avrebbero lo stesso disegno) o un seed scritto a mano nel file (che l'utente non vuole per non cambiare la struttura).

### 38.11 Guida all'implementazione passo per passo (13/11)
Prototipo provato in una **copia di lavoro** del repo (non nel progetto): compila e, su `world01`, funziona come descritto. Risultati: due caricamenti danno la stessa matrice; cambia solo l'erba (id 0) e solo in id di variante; con le soglie di partenza varia il 10,5% delle celle d'erba (5,2% `plus`, 5,3% `minus`); nessuna cella incoerente col terreno; la `collision` di ogni cella resta invariata; cambiare una tile cambia il seed.

**Passo 0 — asset e id.** Servono le immagini delle varianti, registrate in `TileManager.getTileImage()` con id 26-99 **prima** della `loadMap()` (il costruttore chiama già `getTileImage()` e poi `loadMap()`). `draw()` fa `tile[tileNum]` senza controlli: un id usato ma non registrato dà `NullPointerException`. Le varianti d'erba usano `setupAnimated(26, new String[]{...}, false, 45)` (stessa velocità dell'erba, così restano sincronizzate perché il frame dipende dal tick globale).

**Passo 1 — `src/tile/PerlinNoise.java`.** La classe `PerlinNoise` del generatore dell'utente, con `package tile;` in testa e senza il `main`/PNG. Resta identica: costruttore con seed, `noise(x, y)`, `fade`, `lerp`, `grad`.

**Passo 2 — `src/tile/SeedCreator.java`.** `START` (valore iniziale), `mix(h, v)` e `finish(h, cellsRead)` (§38.3). Separata da `WorldGenerator` perché i futuri dungeon generati la riusano con la loro matrice.

**Passo 3 — `TileManager.loadMap()`: tre modifiche minime** (l'hash va fatto qui perché solo qui si sa cosa è stato letto davvero):
```diff
     public void loadMap(String filePath) {
+        long h = SeedCreator.START;
+        int cellsRead = 0;
         try {
 ...
                     mapTileNum[col][row] = Integer.parseInt(numbers[col]);
+                    h = SeedCreator.mix(h, mapTileNum[col][row]);
+                    cellsRead++;
                     col++;
 ...
         }
+        WorldGenerator.apply(mapTileNum, tile, SeedCreator.finish(h, cellsRead), cellsRead, gp.maxWorldCol);
         loadSpecialTilesUsedInMap();
     }
```
- `h` e `cellsRead` si dichiarano **prima del `try`**: se la lettura va in eccezione (come oggi con `world2.txt`, che ha righe con meno colonne o righe mancanti) restano visibili dopo il `catch` e il generatore lavora solo sulle celle lette. Il caricamento che si interrompe è un comportamento già esistente, indipendente da questo lavoro.
- L'hash è calcolato sui valori **originali**, prima che `apply()` li modifichi. `loadMap()` gira all'avvio e a ogni `changeWorld()`: il risultato è lo stesso per lo stesso mondo.

**Passo 4 — `res/maps/tile_variants.txt`** (dato, nello stile di `tile_links.txt`/`locations.txt`):
```
# base plus=<id> minus=<id>      (plus se p > soglia, minus se p < -soglia; ne basta una)
0 plus=26 minus=27
```
Caricato una volta (pigro) da `WorldGenerator`. Alla lettura si controlla che le tile esistano, che nessun id sia >= 100 e che la variante abbia lo **stesso `collision`** della base: se no, errore chiaro in console e riga ignorata. Così nessun sentiero si blocca e non servono liste di celle protette: si toccano solo gli id elencati qui (le tile di acqua/sabbia 2-25, i muri e gli speciali non sono in tabella).

**Passo 5 — `src/tile/WorldGenerator.java`.** Costanti (`SCALE 12,7`, `OCTAVES 3`, `PERSISTENCE 0,5`, `LACUNARITY 2`, `OFFSET 0,37`, `POLARIZED 0,50`, `THRESHOLD 0,30`, `SALT`) e quattro funzioni:
- `weight(noise, col, row)`: il campionamento delle ottave di 38.4, con clamp a [-1,+1].
- `tendency(noise, col, row)`: media di `weight` sulla cella e sugli 8 vicini (38.5).
- `hash(seed, x, y)`: il numero casuale per cella.
- `apply(map, tile, seed, cellsRead, maxCol)`:
```java
PerlinNoise noise = new PerlinNoise(seed);
for (int idx = 0; idx < cellsRead; idx++) {                 // stesso ordine di lettura: righe, poi colonne
    int col = idx % maxCol, row = idx / maxCol;
    int[] v = variants.get(map[col][row]);                  // null = tile non variabile: salta
    if (v == null) continue;
    double p = hash(seed ^ SALT, col, row) * weight(noise, col, row);
    double t = tendency(noise, col, row);
    if (Math.abs(t) >= POLARIZED && Math.signum(p) != Math.signum(t)) continue;   // incoerente: resta uguale
    if (p > THRESHOLD && v[0] >= 0) map[col][row] = v[0];
    else if (p < -THRESHOLD && v[1] >= 0) map[col][row] = v[1];
}
```
- Si può scrivere **sul posto** nella matrice senza copia perché la regola non guarda gli id dei vicini: usa solo i pesi (funzione pura di seed e coordinate), e ogni cella viene scritta una volta sola. Quindi l'ordine di scansione non cambia il risultato.
- `cellsRead` limita il ciclo alle celle lette; `idx % maxCol` e `idx / maxCol` ricostruiscono colonna e riga perché la lettura è per righe.

**Passo 6 — verifica.** (a) Caricare due volte lo stesso mondo e confrontare `mapTileNum` con `Arrays.deepEquals`; (b) controllare che le uniche celle cambiate abbiano id base in tabella; (c) contare le celle variate (con le soglie di partenza ~10% dell'erba su `world01`) e tarare `THRESHOLD`/`SCALE` a occhio; (d) facoltativo: adattare `NoiseMapGenerator` per scrivere un PNG dei pesi sul mondo vero e confrontarlo col gioco.

**Passo 7 — limiti da ricordare.** Modificare una tile della mappa rimescola le varianti (accettato). `world2.txt` oggi non si carica per intero (righe corte): il generatore agisce sulle celle lette. Il generatore varia solo la tile base elencata in tabella; per variare anche altre tile basta una riga in più, senza toccare il codice.

### 38.12 File completi consegnati (14/11)
Dopo la guida a snippet l'utente ha scritto "non sto capendo, dammi i file completi così li posso studiare": consegnati i file interi (la regola "solo snippet" vale finché non viene chiesto il codice).
- Nuovi: `src/tile/PerlinNoise.java` (la classe dell'utente, identica, con `package tile;` e `public`), `src/tile/SeedCreator.java`, `src/tile/WorldGenerator.java`, `res/maps/tile_variants.txt`.
- Modificato: `src/tile/TileManager.java` — **7 righe aggiunte**, nessuna tolta: 2 righe in `getTileImage()` (tile segnaposto 26 = `earth`, 27 = `sand`, da sostituire con le immagini vere), 2 dichiarazioni prima del `try` in `loadMap()`, 2 righe nel ciclo di lettura (hash e contatore), 1 chiamata a `WorldGenerator.apply(...)` prima di `loadSpecialTilesUsedInMap()`.
- **Con i segnaposto attivi** (`0 plus=26 minus=27`) nel gioco l'erba mostra chiazze di terra e di sabbia: serve a vedere la noisemap in azione. Per disattivare basta commentare la riga in `tile_variants.txt`.
- Anteprima (`anteprima_noisemap.png`): mappa originale, pesi del Perlin e risultato affiancati, sul seed ricavato da `world01`.
- Verifica sui file finali (recompilazione dell'intero repo + test headless): stessa matrice a ogni caricamento; cambia solo l'erba; 126 celle `plus` e 129 `minus` su 2442 d'erba; nessuna cella incoerente col terreno; `collision` invariata; cambiare una tile cambia il seed.
- Se l'utente ha modifiche locali a `TileManager.java`, applicare solo le 7 righe sopra invece di sostituire il file.

---
## 39. Noise per cella e varianti grafiche low/medium/high — approccio attuale (14/11)

### 39.1 Idea dell'utente (cambio di approccio)
- Il Perlin produce **valori numerici** per una mappa di dimensione "raggiungibile" (invece di stampare un PNG).
- I numeri si **assegnano alla variabile `noise` di ogni tile** (cella).
- In base al valore la cella ha una variante **low / medium / high**.
- La variante decide quale immagine si disegna: stesso path della tile + `"_" + variante`, cercata in `res/tiles/` (es. `grass_an1_low.png`).

### 39.2 Differenze rispetto al §38
| | §38 (superato) | §39 (attuale) |
|---|---|---|
| Cosa cambia | l'**id** della tile in `mapTileNum` | solo l'**immagine** disegnata |
| Numero casuale per cella | sì (hash) | no: il Perlin è liscio, niente sbalzi |
| Controllo vicini / soglia 0,80 | sì | non servono |
| Tabella varianti, id 26-99 | sì | no: nomi file `_low/_medium/_high` |
| Collision, spawn, `tile_links`, zone, speciali | da proteggere | **non toccati** (stesso id, stesso `Tile`) |
| Limite | — | la variante è solo grafica (non può cambiare cosa è la tile) |

### 39.3 Dove va ogni pezzo
```
TileManager.loadMap(path)
 ├─ legge la matrice -> mapTileNum                                  (invariato)
 ├─ mapNoise = NoiseField.generate(seed, maxWorldCol, maxWorldRow)   [NoiseField, nuova: il generatore senza PNG]
 ├─ mapVariant[c][r] = NoiseField.band(mapNoise[c][r])               [0 low, 1 medium, 2 high]
 └─ loadSpecialTilesUsedInMap()                                     (invariato)
TileManager.setup()/setupAnimated(): oltre all'immagine base cercano path+"_low/_medium/_high"   [+ campi in Tile]
TileManager.draw(): v = mapVariant[col][row]; img = tile.imageFor(v) / tile.frameFor(v, frame)
```
- **Perché matrici parallele e non dentro `Tile`**: `tile[id]` è la tabella dei *tipi* (un solo oggetto per id, condiviso da tutte le celle); il noise appartiene alla **cella** `(col,row)`. Quindi `mapNoise[col][row]` (valore) e `mapVariant[col][row]` (0/1/2, calcolato una volta al caricamento, non a ogni frame).
- `tile/PerlinNoise.java`: la classe dell'utente con `package tile;` e senza il `main`.

### 39.4 Dal generatore dell'utente a `NoiseField` (cosa cambia)
- `width/height` diventano `cols/rows` (`gp.maxWorldCol/Row`); spariscono `BufferedImage`, `ImageIO`, `Color`, `main`, il ciclo di normalizzazione.
- `scale`: non 100 (pensato per 800 pixel) ma circa **lato/4** (50 tile -> 12,7). Non una potenza di 2 e con `OFFSET = 0,37` nel campionamento, per evitare gli zeri esatti del reticolo di Perlin (§38.4).
- 3 ottave (con 5 su 50 tile l'ottava più fine è larga meno di una tile e dà puntini).
- **Come si sceglie `scale`** (domanda dell'utente, 15/11): `scale` è la larghezza **in tile** delle chiazze più grandi (distanza tra i punti del reticolo di Perlin alla prima ottava). **Non dipende dalla dimensione della mappa**, che è solo la finestra su cui si campiona: il numero di chiazze lungo il lato è circa `lato / scale`. La regola "lato/4" serviva solo a ottenere ~4 chiazze per lato; su 100 tile darebbe 25,7. Criterio pratico: una schermata mostra **16x12 tile** (768x576 px con `tileSize 48`), quindi `scale 8` = 2 chiazze per schermata (molto movimentato), `15` = circa una per schermata (equilibrato), `25,7` = chiazze più grandi della schermata (transizioni lente), `50` o più = quasi non si nota camminando. Tenere un valore non tondo (e `OFFSET` 0,37, §38.4). Confronto visivo su 100x100: `scale_confronto.png` (scale 8, 15, 25,7, 50, 100).
```java
double sampleX = ((x + OFFSET) / scale) * frequency;     // + OFFSET: unica differenza nel ciclo
double sampleY = ((y + OFFSET) / scale) * frequency;
// ... stesso ciclo delle ottave; alla fine: noiseValues[x][y] = noiseHeight;  e  return noiseValues;
static int band(double n) { return n < -0.20 ? Tile.LOW : (n > 0.20 ? Tile.HIGH : Tile.MEDIUM); }
```
- **Soglie** (misurate su 30 seed, 50x50, 3 ottave, valore grezzo senza normalizzare; il valore sta tra circa -1,15 e +0,9): `±0,20` -> low 17-36%, medium 37-52%, high 21-40%; `±0,25` -> 14-31 / 45-61 / 16-34. Con il min-max [0,1] del generatore e soglie 1/3 e 2/3 le quote sono meno stabili (low 7-35%, medium 52-66%). Per quote **garantite** a ogni seed: soglie per percentile (ordinare i valori e prendere il 30° e il 70°).

### 39.5 Immagini delle varianti
- Naming: `grass_an1` -> `grass_an1_low.png`, `grass_an1_medium.png`, `grass_an1_high.png` (per le animate, per **ogni frame**).
- Le varianti sono **opzionali**: se un file non esiste la tile usa l'immagine base (nessuna eccezione). Una tile senza nessuna variante (muro, albero...) si disegna sempre uguale.
- In `Tile` (campi e due metodi di fallback) e in `setup()`/`setupAnimated()` (dopo le immagini base, tre tentativi di caricamento con `loadOptional(path + Tile.VARIANT_SUFFIX[v])`, scalate con `scaleImage` come le altre):
```java
public BufferedImage[] variantImage;  public BufferedImage[][] variantFrames;   // [variante][frame]
public BufferedImage imageFor(int v)       { return variantImage != null && variantImage[v] != null ? variantImage[v] : image; }
public BufferedImage frameFor(int v, int f) { return variantFrames != null && variantFrames[v] != null && variantFrames[v][f] != null ? variantFrames[v][f] : frames[f]; }
```
- In `draw()` si cambiano solo le due righe che scelgono l'immagine (`img = t.frames[frameIndex]` -> `img = t.frameFor(v, frameIndex)` e `img = t.image` -> `img = t.imageFor(v)`), con `int v = mapVariant[worldCol][worldRow];` prima.

### 39.6 `loadMap()` e seed
```java
mapNoise = NoiseField.generate(seed, gp.maxWorldCol, gp.maxWorldRow);
for (int c = 0; c < gp.maxWorldCol; c++)
    for (int r = 0; r < gp.maxWorldRow; r++) mapVariant[c][r] = (byte) NoiseField.band(mapNoise[c][r]);
```
- Va prima di `loadSpecialTilesUsedInMap()`; `mapNoise`/`mapVariant` si allocano nel costruttore accanto a `mapTileNum`. Gira all'avvio e a ogni `changeWorld()`.
- **Seed**: per iniziare una costante (123456L, come nel generatore); poi `SeedCreator` come deciso in 38.3 (hash dei numeri della matrice, accumulato in `loadMap()` mentre si legge; modificare una tile rimescola le chiazze).

### 39.7 Dimensione della mappa ("numero raggiungibile")
- Generare i valori è economico (misurato, 3 ottave / 5 ottave): 200x200 ~1 / 6 ms; 400x400 ~5 / 10 ms; 800x800 ~22 / 40 ms.
- I limiti reali sono altri: il **file della matrice** deve contenere `cols x rows` numeri (oggi `world01` ne ha 2500) e `TileManager.draw()` scorre **tutte** le celle a ogni frame (con il culling dentro il ciclo): a 800x800 sono 640.000 iterazioni per frame. Comodo: 100x100 - 200x200. `maxWorldCol/Row` sono costanti in `GamePanel` (oggi 50).

### 39.8 Verifica (prototipo in copia di lavoro, non nel progetto)
Compila; `world01` (50x50): low 25%, medium 47%, high 28%, valori da -1,15 a 0,88; due caricamenti danno gli stessi valori; con varianti di prova (erba più scura/più chiara, medium assente) la variante high/low differisce dal frame base e `medium` ripiega sul base; una tile senza varianti (muro) resta uguale; `draw()` su un'immagine funziona e mostra chiazze lisce; la collision non cambia.

### 39.9 Stato lavori (nulla ancora nel progetto)
| Passo | Dove | Stato |
|---|---|---|
| `PerlinNoise` in `tile/` | file a sé, dalla classe dell'utente | Da fare |
| `NoiseField` (`generate`, `band`) | `src/tile/NoiseField.java` | Da fare |
| `mapNoise`, `mapVariant` + riempimento | `TileManager` (costruttore, `loadMap`) | Da fare |
| Varianti in `Tile` + caricamento opzionale | `Tile`, `TileManager.setup/setupAnimated` | Da fare |
| Scelta dell'immagine nel disegno | `TileManager.draw()` | Da fare |
| Immagini `_low/_medium/_high` | `res/tiles/` | Da fare (servono gli asset) |
| Seed dalla matrice | `SeedCreator` + 3 righe in `loadMap` | Dopo (all'inizio costante) |

### 39.10 Aperto
- Dimensione del mondo (oggi 50x50): quale "numero raggiungibile" si vuole (39.7).
- Quali tile avranno varianti (solo l'erba all'inizio?) e se le immagini esistono già.
- Soglie fisse o per percentile; `scale` (lato/4) e ottave da tarare a occhio.

---
## 40. Mappa di prova 100x100 con tutte le tile (15/11)

Mappa generata con uno script a parte (non fa parte del progetto) a partire dalle tile esistenti (id 0-25; nessuna tile speciale), file `res/maps/world100.txt` (100 righe, 100 numeri per riga, stesso formato di `world01.txt`).

### 40.1 Regole degli autotile dedotte dalle immagini (valgono per ogni mappa)
Nomi e lati delle tile di bordo, verificati leggendo i pixel e confrontandoli con lo stagno 3x3 di `world01`:
- **Acqua (id 2 = interno).** Il bordo è una riva d'erba disegnata **sui lati rivolti verso la terra**. Per una cella d'acqua si guardano i 4 vicini ortogonali (N, E, S, O): terra a N+E = 6 (`rightUp`), S+E = 7 (`rightDown`), N+O = 8 (`leftUp`), S+O = 9 (`leftDown`); terra solo a E = 10 (`centerRight`), solo O = 11 (`centerLeft`), solo N = 12 (`centerUp`), solo S = 13 (`centerDown`).
- **Angoli interni dell'acqua** (id 14-17, "island"): nessun vicino ortogonale è terra, ma lo è **una diagonale**: terra a SO = 14, a NO = 15, a SE = 16, a NE = 17. Hanno `collision = true` come tutte le tile d'acqua (6-17).
- **Sabbia (id 5 = interno).** I bordi (18-25) hanno l'**erba sui lati esterni**: lati scoperti N+E = 18, S+E = 19, N+O = 20, S+O = 21; solo E = 22, solo O = 23, solo N = 24, solo S = 25. Non esistono angoli interni per la sabbia: vanno evitati (nel generatore una cella di sabbia con 3 lati scoperti o con due lati opposti scoperti viene tolta).
- **Cosa non si può disegnare con queste 13 tile d'acqua**: canali larghi 1 tile, penisole di 1 tile, due diagonali di terra sulla stessa cella. Il generatore toglie le celle d'acqua che cadono in questi casi.
- La "terra" per i bordi dell'acqua è **qualsiasi tile non acqua** (erba, sabbia, muro, albero...). Per la sabbia, i vicini acqua non richiedono bordo.

### 40.2 Contenuto della mappa
- **Corte di partenza** (colonne 17-29, righe 10-23): muri in pietra con varco a (23,10) per `OBJ_Door`, stagno 3x3 a (18-20, 11-13) come in `world01`, vialetto di terra e piazzale dove stanno player (23,21), NPC (21,21), goblin (22-24,22) e chiave (28,22). Le coordinate fisse di `AssetSetter` e `Player.setDefaultValues()` cadono su tile camminabili.
- **Sentieri di terra** larghi 2 tile con curve dal varco: verso il villaggio, il lago ovest-sud, le rovine e una radura con altare (muro 2x2) nel bosco ovest.
- **Villaggio** al centro: piazza con fontana (stagno 3x3) e 4 case di muro con porta e pavimento di terra.
- **Lago grande** a nord-est con isola (alberi e sabbia), **lago** a sud-ovest, **laghetto** a est, spiagge, **rovine** a sud-est (recinto spezzato, colonne, piazzale), dune, foreste dense con cornice di alberi lungo il bordo e boschetti sparsi.
- Conteggi (id: celle): erba 4193, muro 185, acqua 757 + bordi, terra 814, albero 3104, sabbia 323 + bordi. Celle camminabili 5610, raggiungibili dal player a porta aperta 5545 (le 65 non raggiungibili sono l'isola, voluta).

### 40.3 Come provarla (3 modifiche) e limite
1. Copiare `world100.txt` in `res/maps/`.
2. `GamePanel`: `maxWorldCol` e `maxWorldRow` da 50 a 100 (sono `final`).
3. `TileManager` (costruttore): `loadMap("/maps/world01.txt")` -> `loadMap("/maps/world100.txt")`.
- **Limite**: `loadMap()` legge `maxWorldCol` numeri per riga, quindi con le costanti a 100 `world01.txt` e `world2.txt` (50 colonne) **non si caricano più** (eccezione). Per tenere mondi di dimensioni diverse servirebbe una dimensione per mondo (larghezza/altezza lette dal file o da una tabella) e rimuovere `final`/costanti globali: modifica più grande, da decidere. (`world2.txt` è stato cancellato il 26/11.)
- Verifica (copia di lavoro con costanti a 100): `loadMap` carica la matrice identica al file, tutti gli id usati sono registrati, gli spawn sono su tile senza collisione, `TileManager.draw()` funziona.
---
## 41. Controllo 0.20.1 / 0.21.x, fix SeedCreator e riscrittura del package `tile/` (21/11)

### 41.1 Cosa mancava nei commit
- **0.20.1** (3 commit con lo stesso messaggio: `a829177` codice+risorse, `c1f1484` STATUS.md, `52a27d5` pulizia). Contenuto completo rispetto al §37, **tranne** la cancellazione di `QuestStep.java` e `QuestRegistry.java`: i tre commit non compilano (`QuestRegistry.java:26`). Risolto dalla 0.21.0, che li elimina. `52a27d5` cancella anche `build.bat`, `build.log`, `full_set_texts.txt` (da confermare che fosse voluto).
- **0.21.2**: non compila. Mancano `src/tile/SeedCreator.java` (mai aggiunto a git), la dichiarazione di `cellsRead` in `loadMap()` e le tre variabili locali di `NoiseField.value()` (`amplitude`, `frequency`, `noiseHeight`). La 0.21.1 non esiste come commit.
- `world100.txt` non serve più: il contenuto è in `res/maps/world01.txt` (100x100) e `GamePanel.maxWorldCol/Row` sono già 100. Il §40.3 è superato.

### 41.2 Cosa cambia nel package `tile/` (file consegnati)
| File | Cosa |
|---|---|
| `SeedCreator.java` (nuovo) | `START`, `mix(h, v)`, `finish(h, cellsRead)` come da §38.3; riusabile dai dungeon generati |
| `PerlinNoise.java` (nuovo) | la classe già esistente, spostata in un file suo e resa `public` (era dentro `NoiseMapGenerator.java`) |
| `NoiseField.java` | `value()` ritrova le sue variabili; `height()` usa i campi `lowLimit/highLimit` invece di ±0,30 scritti a mano |
| `TileManager.java` | riscritto sullo stesso scheletro (stessi campi e metodi pubblici) |
| `NoiseMapGenerator.java` | **da cancellare a mano**: mai chiamata, `extends GamePanel`, `Double.MIN_VALUE` sbagliato |

`TileManager`, punto per punto:
- `loadImage()`: unico punto che apre e chiude il PNG; `setup()` e `setupAnimated()` lo usano (prima il file veniva aperto due volte e il primo stream non si chiudeva).
- `getTileImage()`: in fondo azzera le tile speciali (id >= 100) e le ricarica, così lo zoom le ridimensiona.
- `loadMap()`: azzera `mapTileNum` prima di leggere, tollera righe/colonne mancanti (celle = 0 + WARNING con il numero di celle lette), segnala riga e colonna di un valore non numerico, calcola il seed con `SeedCreator` solo sulle celle realmente lette.
- `loadSpecialTilesUsedInMap()`: un id senza tile (26-99, >= 300, negativo, speciale fuori enum) viene sostituito con 0 e segnalato, quindi `tile[id]` non è mai null per `draw()` e `CollisionChecker`.
- `draw()`: calcola prima il rettangolo di colonne/righe visibili e scorre solo quelle (verificato uguale al vecchio culling su 200 milioni di controlli).
- Soglie altezza: `NoiseField` ora legge i valori passati in `loadMap()`. Ho messo **-0,30 / 0,30** (quelli che avevi scritto a mano in `height()`); per cambiare la distribuzione low/medium/high si cambiano solo questi due numeri.

### 41.3 Come si fa per...
- **Nuova tile di terreno**: una riga `setup(id, "nome", collisione)` in `getTileImage()` con id 26-99 + il PNG in `res/tiles/`.
- **Nuova tile speciale**: una voce in `SpecialTile` (l'id è 100 + posizione nell'enum, quindi aggiungere sempre in fondo) + `res/tiles/<nome>.png`. Nessun PNG oggi esiste per le 10 voci attuali.
- **Nuova mappa**: file in `res/maps/` con al più 100x100 numeri; le celle mancanti restano erba.

### 41.4 TODO emersi dall'audit (non applicati)
- `CollisionChecker.checkTile`: nessun controllo sui bordi (usare `isTileCollisionAt`, che li ha già) + `try/catch` finale vuoto.
- `WeaponRegistry.get()` è al 78% del limite JVM di 64 KB per metodo (51.322 byte), `ArmorRegistry.get()` al 65%: spezzare per categoria o passare a dati in `res/`.
- `tile_links.txt` sta in `src/tile/` ma `TileLinkRegistry` lo cerca in `/maps/`: spostarlo in `res/maps/`.
- Creare i PNG delle 10 tile speciali (o `git add` se sono solo sul PC).
- `mapHeight` è calcolata ma nessuno la usa: manca il passo che sceglie l'immagine in base a LOW/MEDIUM/HIGH.
- `git rm -r --cached out .idea`: 332 file di build e file IDE (chat zencoder comprese) sono ancora tracciati.
---
## 42. Audit completo 0.21.3 e ristrutturazione degli item (23/11)

### 42.1 Perché
`WeaponRegistry.get()` era un solo `switch` da 51.322 byte di bytecode (limite JVM: 65.535 per metodo), `ArmorRegistry.get()` da 42.417: ~250 armi in più e la build si rompeva con "code too large". In più ogni id esisteva due volte (nel `switch` e in `LootIndex`, rigenerato a mano con `generate_loot_index.py`). Le 1120 voci erano in realtà dati: 52 forme di istruzione diverse per le armi, 48 per le armature, 12 per gli scudi, tutte del tipo "campo = valore".

### 42.2 Come è fatto ora
```
res/items/weapons.tsv   546 righe   id, subtype, name, affilatezza, pomo, guardia, punta, corda, struttura, legamenti, rarity, synergy, fullSet, mythic
res/items/armors.tsv    448 righe   id, type, weight, name, rifiniture, legamenti, metalli, upperID, lowerID, accepted0, accepted1, rarity, synergy, fullSet, mythic
res/items/shields.tsv   126 righe   id, subtype, name, affilatezza, pomo, metalli, rarity, synergy, fullSet, mythic
items/ItemTable         legge un .tsv (prima riga = nomi colonna, TAB come separatore, cella vuota = campo non impostato)
items/WeaponRegistry, ArmorRegistry, ShieldRegistry   restano con lo stesso get(id) ma costruiscono l'oggetto dalla riga; in più ids()
items/ItemCheck         strumento da lanciare a mano (main): costruisce tutti gli oggetti e segnala righe sbagliate
```
- `LootIndex` non esiste più: `LootTable` e `InventoryDemo` leggono gli id da `XRegistry.ids()` (stesso ordine di prima, quindi stesse estrazioni a parità di seed).
- Le zone di parata degli scudi (identiche per sottotipo in tutte le 126 righe) stanno in `ShieldRegistry` (3 casi), non nella tabella.
- La rarità è nell'id (`..._30_40_mythic`) **e** nella colonna `rarity`: `ItemCheck` controlla che coincidano.
- Costo: ~65 ms in più alla prima lettura del loot (lettura di 213 KB di testo) e ~1,4 MB di heap.

### 42.3 Verifica
Tutti i 1120 oggetti costruiti dal vecchio codice e dalle tabelle e confrontati campo per campo (anche i bonus di `computeBonusPercent()`): 0 differenze, con prova negativa che il confronto rileva le differenze. 300.000 estrazioni di loot con lo stesso seed: stesso risultato. Il gioco headless (300 frame, combattimento, libro) gira come prima.

### 42.4 Come si fa per...
- **Nuova arma/armatura/scudo**: una riga nuova nel `.tsv` giusto (id = `<tipo>_<build>_<livMin>_<livMax>_<rarità>`), poi lanciare `ItemCheck`.
- **Nuovo campo** (es. `peso`): una colonna nel `.tsv` (anche vuota) + una riga nel `get()` del registry (`r.into(w.peso, 0, "peso")` o `w.x = r.num("x")`).
- **Modificare le tabelle**: editor di testo o foglio di calcolo esportato come TSV *senza virgolette*; nessun carattere TAB dentro i testi.
- Lo script `pp/generate_loot_index.py` non serve più; se la scheda Equip va rigenerata, lo script deve produrre i `.tsv` invece del Java.

### 42.5 Altri fix di questo giro (in `fix-minori.patch`)
`CollisionChecker.checkTile` controlla i bordi; `Sound` non va più in crash senza scheda audio e chiude i clip degli effetti; `GamePanel.run()` dorme 1 ms quando non c'è un frame da fare (CPU 97% -> 2% di un core); `player.draw()` non è più chiamato due volte; `update()` scorre `npc.length`. `TileManager` tiene in cache le immagini originali (zoom: 16,6 -> 2,5 ms a chiamata).

### 42.6 TODO aperti
- Zoom (frecce su/giù): NPC, mostri e oggetti non cambiano posizione né dimensione (verificato: NPC colonna 21 -> 18). Decidere se completarlo o togliere i due tasti.
- Spostare `src/tile/tile_links.txt` in `res/maps/` (`TileLinkRegistry` lo cerca lì).
- Creare i PNG delle 10 tile speciali (`res/tiles/<nome>.png`).
- `mapHeight` è calcolata ma non usata dal disegno.
- `git rm -r --cached out .idea pp` e aggiungere `.idea/` al `.gitignore`.
- `UI`: i 6 font si caricano senza chiudere gli stream e un font mancante dà NullPointerException (ne manca uno = niente UI).
- `world2.txt`: id 26-39 inesistenti e 51 colonne; non è raggiungibile (nessun link).
---
## 43. Passaggio a Gradle (24/11)

### 43.1 Cosa c'è
`settings.gradle.kts`, `build.gradle.kts`, `gradlew`, `gradlew.bat`, `gradle/wrapper/*` (Gradle **9.6.1**, scaricato in automatico dal wrapper al primo uso), `.gitattributes`. Struttura delle cartelle invariata: `src/` = codice (package `main`, `entity`, ... dentro `src/`, non in `src/main/java`), `res/` = risorse (classpath: `/tiles/...`, `/maps/...`).
- Toolchain Java **25**: se manca il JDK giusto Gradle dice `No matching toolchains found`; si risolve installando JDK 25 o cambiando il numero in `build.gradle.kts`.
- Compilazione sempre in UTF-8.
- Solo `res/` viene impacchettata: i file non Java dentro `src/` non vengono copiati (era solo `tile_links.txt`, ora in `res/maps/`).

### 43.2 Comandi (PowerShell: `.\gradlew`, Linux/Debian: `./gradlew`)
| Comando | Cosa fa |
|---|---|
| `.\gradlew run` | compila e avvia il gioco (`main.Main`) |
| `.\gradlew build` | compila e crea il jar |
| `.\gradlew jar` | crea `build\libs\Abysson.jar` (38 MB: dentro c'è tutta `res/`); si avvia con `java -jar build\libs\Abysson.jar` |
| `.\gradlew itemCheck` | controlla le righe di `res/items/*.tsv` (prima: configurazione IntelliJ su `ItemCheck`) |
| `.\gradlew colorDump --args="res/a.png res/b.png"` | `ColorDump` sui PNG indicati |
| `.\gradlew clean` | cancella la cartella `build/` |

### 43.3 Differenze rispetto al compilatore di IntelliJ
- L'output è in `build/` (prima `out/`). `build/`, `.gradle/` e `*.iml` sono nel `.gitignore`.
- IntelliJ usa Gradle per "Build" e "Run" (Settings > Build, Execution, Deployment > Build Tools > Gradle > "Build and run using: Gradle"; "Gradle JVM": JDK 25).
- Il primo avvio scarica Gradle (~130 MB) e serve internet.
- Su Debian: `git update-index --chmod=+x gradlew` una volta, poi `./gradlew run`.

### 43.4 Verificato (sandbox, JDK 25 + Gradle 9.6.1)
`build`, `jar`, `itemCheck`, `colorDump`, `run` e `java -jar` (finestra aperta su display virtuale, nessuna eccezione; solo l'avviso audio perché la sandbox non ha scheda audio), wrapper provato da zero con cartella utente di Gradle pulita. Non provato: IntelliJ con le sue impostazioni, né Windows.

---
## 44. Giro 0.22.4 (26/11): zoom completo, sconfitta, decisioni, traduzione, Full Set

### 44.1 Font
`UtilityTool.loadFont("/font/Nome.ttf")` carica un font da `res/` (chiude lo stream; se manca o è rotto scrive l'errore e ripiega su SansSerif). `UI` lo usa per i 6 font: per un font nuovo basta una riga `MioFont = tool.loadFont("/font/Mio.ttf");`.

### 44.2 Zoom e `quest/EventList`
- **`EventHandler`** (main) resta il punto in cui si gestiscono gli eventi; **`quest/EventList`** è il catalogo di tutti gli eventi richiamabili per nome con un valore int (non fa nulla finché non viene chiamato). Chi vuole far scattare un evento lo chiama **sempre tramite `EventHandler`**: `gp.eHandler.zoom(+16)` oppure `gp.eHandler.call("zoom", -16)`, da qualunque classe (cambio mondo, caselle, quest...). Oggi c'è un solo evento, `zoom`. Un evento nuovo = una riga `events.put("nome", valore -> ...)` nel costruttore di `EventList` (e, se lo vuoi con un nome comodo, un metodo di una riga in `EventHandler`). Gli eventi su caselle già esistenti (`damagePit`, `healingPool`) restano in `EventHandler`: hanno parametri (colonna, riga, stato) e non entrano in `EventList` così come sono.
- Esempi: su una casella (in `checkEvent()`): `if (hit(28, 11, "any")) { zoom(+16); eventRect[28][11].eventDone = true; }`; al cambio mondo (nel punto in cui `GamePanel.loadWorld(...)` finisce, o in `Player` dopo un teletrasporto): `gp.eHandler.zoom(+12)` per entrare in una casa e `gp.eHandler.zoom(-12)` all'uscita. Non c'è un timer ("per un tot"): per tornare indietro serve un secondo evento con il valore opposto.
- I tasti freccia su/giù **non fanno più zoom**. Per un tasto di debug: una riga in `KeyHandler` con `gp.eHandler.zoom(1)`.
- Cosa si adatta allo zoom: posizione (`worldX/worldY`) e area di collisione di NPC, mostri e oggetti (`Entity.rescale()`, `Entity.scaleSolidArea()`: i pixel scritti nelle sottoclassi valgono per `GamePanel.DESIGN_TILE_SIZE = 48` e vengono sempre ricalcolati da quelli, quindi nessuna deriva da arrotondamento; `AssetSetter.place()` lo applica anche alle entità create dopo uno zoom); sprite del player (57 px a tile 48, proporzionale); `screenX/screenY` del player (restano al centro); rettangoli degli eventi (`EventHandler.scaled()`). Limiti 16-96 con clamp (prima un salto fuori limite veniva ignorato).
- Bug corretto: la vecchia riga `player.speed = worldWidth / 600` con il mondo 100x100 portava la velocità da 4 a 8 al primo zoom, e `recalculateStats()` poi la riportava a 4. `speed` è sia i pixel per frame sia la stat Velocità del combattimento: **non viene più toccata dallo zoom** (limite noto: a tile grande si cammina più lenti in caselle/secondo; se lo zoom dura a lungo, separare il movimento dalla stat).
- Non coperto: uno zoom a metà di una scripted move (12-20 frame).

### 44.3 Sconfitta ("You were defeated")
- Nuovo stato `defeatState = 8`. Dopo il messaggio di sconfitta, `CombatState` chiama `ui.openDefeatScreen()` se `player.life <= 0`. Voci: **Load last save** (grigia e inattiva finché `gp.hasSave()` è `false`) e **Quit to title** (`player.setDefaultValues()` + schermata del titolo). Tasti W/S e INVIO.
- Per agganciare il salvataggio basta implementare `GamePanel.hasSave()` e `GamePanel.loadGame()`: la schermata li usa già.
- Limite: finché non c'è il salvataggio, "Quit to title" riporta il player al punto di partenza con vita piena ma lascia equipaggiamento, inventario e quest com'erano.

### 44.4 Calendario
Costanti in `GameCalendar`: `MINUTES_PER_DAY = 10`, mese 10 giorni, stagione 30 (3 mesi), anno 120 (4 stagioni). Un anno sono 120 x 10 minuti = **20 ore** reali (stagione 5 ore, mese 100 minuti). Per confronto, in Stardew Valley (se non ricordo male: giorno circa 14 minuti, 28 giorni per stagione) un anno è oltre 25 ore: 20 ore non mi sembrano troppe, e se si vuole accorciare la manopola più semplice è `MINUTES_PER_DAY` (5 -> 10 ore); con 3 stagioni da 30 l'anno sarebbe 90 giorni = 15 ore. L'orologio che fa avanzare giorno e mese **non esiste ancora**. La griglia del calendario nel libro (`InventoryPage`) ha 6x5 = 30 celle, cioè esattamente una stagione, ma disegna `DAYS_PER_MONTH` celle: ora ne segna 10. Per mostrare l'intera stagione basta passare a `DAYS_PER_SEASON` con il giorno contato dentro la stagione.

### 44.5 Combattimento e oggetti
- **Schivata del mostro** (`CombatState.resolveDodge`): `1 - CombatMath.hitChance(player.precision, monster.evasion)`. Scelta mia: uso la **precisione** anche in mischia, perché la velocità del player (4) non è sulla scala 0..1000: con la velocità dentro `hitChance` il mostro schiverebbe sempre (100%), anche con evasione 0. Con la precisione 100: evasione 0 -> 0%, 20 -> 21%, 50 -> 42%, 100 -> 62,5% (misurato 63,6% su 4000 prove). Se si vuole la velocità in mischia, va riportata sulla stessa scala.
- **Abilità a distanza**: tag `RANGED` in `Ability` (Thunderbolt, AcquaJet, Lightray); `Ability.isRanged(id)` lo legge. Una nuova abilità a distanza = il suo id nell'insieme.
- **Scudo a durabilità 0 non para** (deciso 26/11): `Weapon.isUnusable()` ora è `isBroken()` per tutti, e `Player.resolveDefenseWeapon()` lo controlla (prima non lo faceva neanche per il Sai).
- **`Player.pickUpObject(i)`**: aggiunge un `QuestItem` (tipo `WORLD`) con il nome dell'oggetto, notifica `COLLECT` alle quest, mostra "You obtained: ...", toglie l'oggetto dalla mappa. Oggi `AssetSetter` piazza solo la chiave (Chest e Boots non sono piazzati).
- Crescita delle stat per livello: **resta aperto** (deciso: "vediamo dopo"). Durata di Bruciatura Grave: non toccata (5).

### 44.6 Traduzione in inglese
Fatto: `ElementSystem` e `Reaction` (nomi, `displayName`, commenti, tabella delle reazioni), `DamageTypes` e tutti i punti che usano questi nomi; messaggi ed etichette di runtime (menu "Talk/Attack", "You obtained", errori dei loader, "Status", ecc.). Verificato: tabella delle reazioni, moltiplicatori, stat e flag di tutti gli effetti identici a prima (stesso hash su 301 righe).
- `Element`: FISICO->PHYSICAL, LUCE->LIGHT, FUOCO->FIRE, ACQUA->WATER, TERRA->EARTH, ARIA->WIND, FULMINE->ELECTRIC (l'ordine non cambia: la tabella usa `ordinal()`). Importante: `DamageTypes.of(Element)` usa `e.name()` e lo confronta con le stringhe di `DamageTypes`, quindi i due devono restare allineati.
- `StatusEffect`: POTENZIAMENTO->ENHANCEMENT, ACCECAMENTO->BLINDING, ESPLOSIONE->EXPLOSION, INONDAZIONE->FLOODING, RESISTENZA->RESISTANCE, STORDIMENTO->STUN, FOLGORE->LIGHTNING_BOLT, ROTTURA->RUPTURE, ESPOSIZIONE->EXPOSURE, INFIAMMAZIONE->INFLAMMATION, EROSIONE->EROSION, TURBINIO->WHIRLWIND, SCOSSA->JOLT, ABRASIONE->ABRASION, PURIFICAZIONE->PURIFICATION, NATURALIZZAZIONE->NATURALIZATION, ILLUMINAZIONE->ILLUMINATION, RAGGIO->RAY, VAPORIZZAZIONE->VAPORIZATION, CARBONIZZAZIONE->CARBONIZATION, FIRENADO (uguale), SOVRACCARICO->OVERLOAD, INFANGATO->MUDDIED, TEMPESTA->STORM, ELETTRIZZAZIONE->ELECTRIFICATION, POLVERIZZAZIONE->PULVERIZATION, DEVIAZIONE->DEFLECTION, RAMIFICAZIONE->BRANCHING, BRUCIATURA_GRAVE->SEVERE_BURN. `DamageTypes`: VELENO->POISON, PSICHICO->PSYCHIC, SANGUINAMENTO->BLEEDING, MALEDIZIONE->CURSE. I nomi inglesi delle reazioni sono traduzioni letterali: se ne vuoi di più adatti (es. "Enhancement" per un +20% di danni subiti) si cambiano nell'enum e in `getReaction()`.
- **Non tradotto** (da decidere): `StatType` (VITA, DIFESA, VELOCITA, ELUSIONE, PRECISIONE, EFFICIENZA), nomi dei campi e dei sottotipi degli oggetti (`affilatezza`, `pomo`, `guardia`, `punta`, `corda`, `struttura`, `legamenti`, `metalli`, `rifiniture`, `WeaponSubtype`...), nomi e descrizioni degli oggetti nelle tabelle `.tsv` e nei demo, testi Full Set/Mitico, file `res/quests`, commenti e testi del resto del codice.
- Attenzione: il testo dei Full Set e dei Mitici è usato come **chiave** (confronto per prefisso in `SpecialEffectRegistry` e `CombatState.hasActivePassive`): se si traducono i testi nelle tabelle vanno cambiati anche lì (vedi 44.7, punto 1).

### 44.7 Full Set: cosa c'è oggi e come li farei
**Oggi**
- 5 gruppi: tre per il busto (8 pezzi) = Guardia (1), Veggente (2), Voto (3), scelti dal tipo di armatura (Defense / Elemental Def / Special Def); due per le gambe (4 pezzi) = Passo (1) e Ombra (2). Le gambe accettano 2 busti su 3 (`acceptedUpperIDs`): Passo con Guardia o Voto, Ombra con Veggente o Voto, quindi i `fullID` validi (`upperID x 10 + lowerID`) sono 11, 31, 22, 32.
- Il set è attivo se `Player.activeFullID() != 0`: 12 slot pieni, 8 busti con lo stesso `upperID`, 4 gambe con lo stesso `lowerID` che accettano quel busto. I pezzi rotti contano. È tutto-o-niente: niente bonus a soglie.
- Dati: in `armors.tsv` 160 righe hanno un testo `fullSet` (96 testi distinti: Guardia 40 righe, Veggente 40, Voto 40, Passo 20, Ombra 20; sembrano 32 effetti x 3 livelli, dal suffisso "(livello ...)"); 64 righe hanno un testo Mitico e 288 un testo `synergy`. Anche le armi hanno testi (195 `fullSet`, 78 `mythic`). Ogni testo è libero, con tag `[Full Set: Guardia]` e a volte `STUB — `.
- Effetti **reali** (uno per gruppo): Guardia = Elmo Saldo (primo stordimento di ogni combattimento annullato), Veggente = Occhio Limpido (immune a Blinding), Voto = Purga Lenta (a fine round toglie il debuff più vecchio), Passo = Suola Salda (immune a Naturalization/Muddied), più Presa Ferrea (immunità al disarmo) come controllo sul testo. Ombra = Vuoto è ancora STUB (non esiste un sistema di schivata). Gli altri 27 testi Full Set sono solo lore. Ogni pezzo Mitico spara sempre il proprio testo Mitico, indipendentemente dal set. `synergy` non è letto da nessuna logica.
- Mancano: i testi per Passo+Voto e Ombra+Voto (`fullSetStubAlt` è null) e 54 testi Full Set/Mitico che richiedono sistemi che non esistono: sanguinamento, maledizione/marchio/drenaggio/silenzio/confusione (l'intero Voto), critici, schivata/iniziativa/furtività/spinta/rallentamento/danno da terreno, rivelazione in UI, riflessione dei proiettili, risorse accumulabili, un hook "on-heal".

**Come li creerei** (proposta, non applicata)
1. **Separare l'id dal testo.** Nelle tabelle, accanto al testo, colonne `fullSetId` e `mythicId` (es. `guard_steady_helm`) e una colonna `tier` al posto del "(livello ...)" nel testo. `SpecialEffectRegistry` cerca per id; il testo resta solo la descrizione mostrata. Così tradurre o riscrivere un testo non rompe nulla (oggi sì, perché si confronta il prefisso del testo).
2. **Una tabella dei set**, `res/items/sets.tsv`: `fullID, name, upperID, lowerID, effectId`. Aggiungere un set è una riga; `activeFullID()` e `Armor.fullSetStubFor()` diventano un lookup.
3. **Prima i sistemi, poi i set**, uno alla volta: gli stati (sanguinamento, maledizione, marchio, drenaggio, silenzio, confusione) come nuovi `StatusEffect` con tick o modificatori (il meccanismo c'è già); i critici in `CombatMath`; schivata/iniziativa/furtività dove oggi si decide l'ordine dei turni; l'hook on-heal in un solo punto (`heal`). Ogni sistema sblocca i testi che lo usano: sanguinamento e silenzio da soli sbloccano buona parte di Voto, e la schivata sblocca Ombra.
4. **Decidere se il bonus resta tutto-o-niente a 12 pezzi** o diventa a soglie (per esempio 2/4/8/12): le soglie rendono utili i pezzi singoli e il loot meno binario.
5. **Un controllo come `ItemCheck`** per i set (`SetCheck`): per ogni `fullID` valido costruisce i 12 pezzi, verifica che `activeFullID()` lo riconosca e che l'effetto esista nel registry.

### 44.8 TODO aperti dopo questo giro
- [ ] Salvataggio/caricamento (`GamePanel.hasSave()` / `loadGame()`; la schermata di sconfitta è pronta)
- [ ] Orologio di gioco e griglia del calendario nel libro (stagione di 30 celle)
- [ ] Crescita delle stat per livello (da decidere)
- [ ] Separare velocità di movimento e stat Velocità, se lo zoom dura a lungo
- [ ] Full Set: sistemi mancanti, id al posto del testo come chiave, testi Passo+Voto e Ombra+Voto (vedi 44.7)
- [ ] Traduzione: `StatType`, campi e sottotipi degli oggetti, testi nelle tabelle, commenti (da decidere)
- [ ] Equipaggiare/usare gli oggetti dall'Inventario; piazzare Chest e Boots nella mappa
- [ ] Grafica stub: PNG delle 10 tile speciali, uso di `mapHeight` nel disegno, icone degli oggetti
- [ ] Durata di Bruciatura Grave (5, da bilanciare)
