# Abysson — Status del codice (2026-07-19)

Questo file va tenuto in `docs/STATUS.md` (o dove preferisci nel repo). Non è generato automaticamente: lo aggiorno io a mano ogni tanto, su richiesta, quando facciamo un blocco di modifiche significativo.

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

## 1. Architettura generale

- **Java Swing**, game loop standard (thread + `repaint()`), mondo a tile.
- Classi core: `GamePanel` (loop, stato di gioco, array entità), `TileManager`, `Entity` (classe base), `Player`, NPC/monster che estendono `Entity`.
- Stati di gioco (`gp.gameState`): `titleState`, `playState`, `pauseState`, `dialogueState`, `combatState`, `cinematicState` (cinematic GIF, implementato il 19/07 — vedi §2).
- Segui il tutorial YouTube di RyiSnow come base, ma con sistemi custom molto oltre lo scope del tutorial (combattimento a turni, sistema elementale, palette swap, menu animati).
- **Package `combat`** (nuovo, 19/07): `CombatState`, `ElementSystem`, `Ability`, `Reaction` vivono ora in `src/combat/`, non più in `src/main/`.

## 2. Sistemi principali

| Sistema | File | Note |
|---|---|---|
| Combattimento a turni | `combat/CombatState.java` | Innescato da collisione, menu Attack/Ability/Inventory/Minimap/Flee, navigabile W/S/ENTER/ESC. Testi tutti in inglese (tradotti da IT il 18/07). **04/08, cambio importante**: `dealDamage(attacker, target, abilityId)` accentra la logica di danno prima duplicata quasi identica in `playerUseAbility()`/`monsterTurn()` (precisione/schivata, moltiplicatore elementale, reazione, effetti attivi, contraccolpo Folgore/Infiammazione su un fallimento). **Cambio di comportamento voluto**: prima solo il mostro poteva mancare un colpo (il giocatore colpiva sempre) — unificando la formula, ora si applica a entrambi allo stesso modo. **Ordine dei turni a velocità**: `decideTurnOrder()` confronta `player.speed`/`monster.speed` a inizio di OGNI round (non solo all'inizio del combattimento — `startCombat()` e `advanceRound()` la richiamano entrambi), quindi un cambio di velocità a metà scontro (es. un debuff) si riflette dal round successivo; a parità va il giocatore, come nel comportamento precedente. **Stordimento (`STORDIMENTO`) ha finalmente un effetto**: prima esisteva come `StatusEffect` ma non veniva controllato da nessuna parte — ora chi è stordito salta il turno da solo (`isStunned()`, controllato in `update()` per il giocatore e in testa a `monsterTurn()` per il mostro), e tickano i suoi effetti attivi in quel momento (altrimenti la durata dello stordimento non scenderebbe mai). **Coda azioni** (`queueAction()`/`actionQueue`): i messaggi di combattimento non finiscono più subito in `combatMessage`, vengono accodati e mostrati uno alla volta da `update()` quando `messageTimer` torna a 0 — `onVictory()`/`onDefeat()` sono stati convertiti alla coda per lo stesso motivo (altrimenti il messaggio di vittoria/sconfitta comparirebbe PRIMA di quello del colpo che l'ha causata). `tryFlee()` non è stato toccato (nessun messaggio in coda prima del suo, nessun problema di ordine). |
| Elementi & status | `combat/ElementSystem.java` | Enum `Element` (FISICO/LUCE/FUOCO/ACQUA/TERRA/ARIA/FULMINE), `StatusEffect` (28 effetti), `ActiveEffect`, tabella reazioni 7×7. **Nomi/displayName ancora in italiano** (Raggio, Folgore, Infiammazione, Rottura, Potenziamento, Scossa, Abrasione) — non tradotti, vedi TODO. |
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
- **Zoom** (`GamePanel.zoomInOut`): richiede il ricaricamento completo delle immagini (tile + player + NPC con `getImage()`), non un semplice scale a runtime.
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

- [ ] **Decisione da prendere**: "effetti attivi" post-turno (tick di veleni/debuff) oggi ticchettano ancora sul BERSAGLIO quando viene colpito (dentro `dealDamage`), non su ogni entità alla fine del proprio turno. L'unica eccezione è lo stordimento, che tickano sé stesso quando salta il turno (altrimenti resterebbe stordito per sempre). Se in futuro si vuole che OGNI effetto attivo (non solo lo stordimento) tickhi una volta a round sul proprio portatore invece che "quando viene colpito", è un cambio di design separato, non fatto qui per non alterare il bilanciamento esistente senza deciderlo esplicitamente.
- [ ] `queueAction()`/`actionQueue` è una coda semplice (`ArrayDeque<String>`, un messaggio testuale alla volta) — se in futuro serve accodare azioni più ricche (non solo testo: es. un'animazione per messaggio, o più colpi con dati diversi ciascuno), andrà esteso oltre la semplice stringa.
- [ ] Tradurre in inglese `ElementSystem.java` (nomi `Element`/`StatusEffect`/`displayName`) e `Reaction.java` — al momento restano in italiano nonostante `CombatState.java` sia stato tradotto, quindi si vedono nomi come "Raggio"/"Folgore" mescolati a testo inglese.
- [ ] `commandNum == 1` ("Load Game") nel main menu è ancora uno stub (`/* ADD LATER */`) — nessun salvataggio implementato.
- [ ] `Options` (main menu) è uno stub — nessuna schermata opzioni.
- [ ] `Inventory` e `Minimap` in combattimento sono placeholder (mostrano solo un messaggio).
- [ ] Il glow PNG per l'hover del menu va creato/importato — fatto ma non funzionante
- [ ] Verificare che tutti gli altri oggetti statici (Chest, Boots) siano effettivamente istanziati in `AssetSetter` — solo Key e Door sono attivi al momento, **ma segnalati come non funzionanti**: da investigare a fondo (il fix della direzione di default dovrebbe averli sbloccati, ma va confermato in gioco).
- [ ] Aggiungere tutti gli effetti delle reazioni.
- [ ] Contenuto vero delle pagine del libro: tolto tutto (modello dati `BookEntry`/`unlockedBookZones`, testo placeholder) il 02/08 — da riprogettare da zero quando si è pronti, probabilmente ripartendo da `BookEntry.java` (rimasto nel progetto, non referenziato) o da un approccio diverso.
- [ ] Posizioni placeholder dei pulsanti sottoargomento (`SUBTOPIC_IMAGE_RECTS` in `UI.java`) da aggiustare a vista, stesso procedimento già fatto per i bookmark (aprire `book_X.png` in un editor di immagini, leggere le coordinate pixel).
- [ ] `res/maps/world2.txt` esiste ma non è caricato da nessun codice (nessun `loadMap("/maps/world2.txt")` in giro) — usa anche ID tile 26-39, oltre il terreno definito (0-25) e sotto `SPECIAL_TILE_BASE=100`. Se in futuro verrà caricata andrà sistemata (o quegli ID vanno rimappati, o vanno definiti come terreno aggiuntivo in `getTileImage()`).
- [ ] ESC nel libro chiude tutto in un colpo solo, non risale di un livello alla volta (pagina→voce→area→chiuso) — possibile miglioramento futuro, non richiesto esplicitamente.

---

*Fine status — prossimo aggiornamento quando accumuliamo un altro blocco di modifiche.*