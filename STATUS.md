# Abysson — Status del codice (2026-07-27)

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
| Combattimento a turni | `combat/CombatState.java` | Innescato da collisione, menu Attack/Ability/Inventory/Minimap/Flee, navigabile W/S/ENTER/ESC. Testi tutti in inglese (tradotti da IT il 18/07). |
| Elementi & status | `combat/ElementSystem.java` | Enum `Element` (FISICO/LUCE/FUOCO/ACQUA/TERRA/ARIA/FULMINE), `StatusEffect` (28 effetti), `ActiveEffect`, tabella reazioni 7×7. **Nomi/displayName ancora in italiano** (Raggio, Folgore, Infiammazione, Rottura, Potenziamento, Scossa, Abrasione) — non tradotti, vedi TODO. |
| Abilità | `combat/Ability.java` | Dispatcher statico, facile da estendere (vedi §4). |
| Palette swap | `main/PaletteSwap.java` | Remapping colore-esatto via `getRGB`/`setRGB`, cachato per (chiave, immagine). Formato compatto stringa: `"RRGGBB>RRGGBB,..."`, parsato da `PaletteSwap.parsePalette()`. |
| Menu titolo | `main/UI.java` (`drawMenuItems`) | Un solo metodo condiviso usato da tutti e 3 gli schermi (main menu, classe, difficoltà): slide-in+stagger, float, hover scale/offset/dimming/glow (mouse **o** tastiera, unificati), punch al confirm, delay di 1s prima di eseguire il comando. Glow = sottolineatura `res/ui/menu_hover_glow.png` (81×9, larga e sottile) che cresce in **larghezza** da 0 al pieno, centrata sotto la voce — sistemato il 19/07 (cresceva in altezza per errore, quasi invisibile). |
| Setup entità | `main/AssetSetter.java` | Helper `place(array, index, factory, col, row, paletteDef)` — una riga per entità invece di 3-4 righe separate. |
| Debug colori sprite | `main/ColorDump.java` | Utility standalone (`main()` con path PNG come argomenti) per stampare i colori ARGB unici di uno sprite — serve per scrivere palette corrette. |
| Cinematic (GIF) | `main/GifPlayer.java`, `GamePanel.playCinematic()` | Decodifica un GIF con compositing corretto (gestisce i "disposal method" per frame, non solo il caso banale). `playCinematic(path)` carica da classpath (`res/cinematics/...`), ricorda lo stato da cui arrivi e ci torna da solo a fine riproduzione; overload `(path, loop)` per il loop, `(path, loop, nextState)` per atterrare su uno stato diverso da quello di partenza (usato per il libro, vedi sotto). ENTER/ESC durante la cinematic la saltano (`GamePanel.skipCinematic()`). `paintComponent()` disegna prima lo sfondo di destinazione (`cinematicReturnState`: mondo/titolo/libro) e la cinematic sopra — così la vera trasparenza del GIF rivela quello sfondo invece del nero di base del pannello (fix 19/07). |
| Libro: apertura/pagine | `GamePanel.bookState`, `UI.drawBookScreen()` | Tasto **I** in gioco → cinematic `Open_book.gif` → atterra su `bookState`. Sfondo disegnato con **aspect ratio corretto** (scala uniforme `Math.min(screenW/imgW, screenH/imgH)` + centratura, stessa formula già usata per le cinematic) — **non più stirato** a `screenWidth×screenHeight` (bug del 27/07, vedi §3). **ESC/I** → `GamePanel.closeBook()` (richiude e resetta `bookindex/bookzone/bookpage` a 0). |
| Libro: navigazione a 3 livelli | `GamePanel` (`bookindex`/`bookzone`/`bookpage`, costanti `bookindex_bestiary`…`bookindex_quests`, `selectBookIndex()`, `selectBookZone()`, `turnBookPage()`, `startBookTransition()`), `UI.drawBookScreen()`, `UI.getBookContentText()` | Aggiunto il 27/07, rinominato in stile `gameState` lo stesso giorno su richiesta esplicita: **`bookindex`** = macrozona (bookmark: 6 costanti nominate `bookindex_bestiary=1` … `bookindex_quests=6`, valore `0` = nessuna selezionata), **`bookzone`** = sezione dentro alla macrozona (dato soltanto, nessuna UI ancora), **`bookpage`** = pagina/microzona (LEFT/RIGHT come prima). Attenzione ai nomi metodo, che seguono il CAMPO che impostano, non il livello concettuale: `selectBookIndex(int)` imposta `bookindex` (click su un bookmark), `selectBookZone(int)` imposta `bookzone` (dormiente, nessun bottone la chiama ancora). Ogni cambio passa da `startBookTransition()`, che innesca **sempre** l'animazione `Turning_pages_left/right.gif` — nessuno swap istantaneo. Sfondo: `book.png` se `bookindex == 0`, altrimenti `book_X.png` della macrozona attiva. Contenuto testuale generato da `UI.getBookContentText()`: if/else-if annidato per `bookindex` → `bookzone` → `bookpage` (richiesto esplicitamente in questo stile, invece di un lookup su array), solo placeholder per ogni macrozona. |
| Libro: bottone bookmark esteso | `GamePanel` (`extendedBookmarkImages[]`, `EXTENDED_BOOKMARK_IMAGES`), `UI.drawBookScreen()` | Aggiunto il 27/07, sostituisce i vecchi bottoni testuali "Sezione 1/2" (rimossi, non avevano asset dietro). Sovrappone lo sprite `extended_bookmarks_X.png` della macrozona attiva sopra al bookmark già disegnato dentro `book_X.png` — **volutamente ridondante** (scelta esplicita: "più robusto" invece di scegliere un solo meccanismo). Disegnato e cliccabile **solo quando effettivamente visibile**: `bookindex != 0 && !pageTurnActive` — un bookmark esteso non renderizzato non deve avere alcun effetto. Il click sull'overlay è coperto dallo stesso hit-test del bookmark sottostante (stessa area a schermo), nessun rettangolo separato. |
| Libro: hit-test mouse bookmark | `UI.java` (`BOOKMARK_IMAGE_RECTS`, `bookmarkScreenBounds`, `updateBookMouseHover()`, `handleBookClick()`) | Stesso schema già usato per il menu titolo (`menuItemBounds`/`updateMouseHover`/`handleTitleClick`): rettangoli calcolati ogni frame in coordinate schermo (dipendono dallo scaling del libro), poi hit-test su hover/click. `BOOKMARK_IMAGE_RECTS` sono le 6 macrozone (in coordinate immagine 272×272) misurate confrontando i pixel non trasparenti di `book.png` con ciascun `book_X.png`; indice array = `bookindex - 1` (stesso ordine di `ZONE_IMAGES`: bestiary, calendar, inventory, map, skills, quests). Il mouse handler globale in `GamePanel` (già esistente per il titolo) inoltra anche a `updateBookMouseHover`/`handleBookClick`, che si autolimitano a `gameState == bookState`. |

## 3. Cose da ricordare (bug pattern ricorrenti + decisioni prese)

- **Direzione di default per entità statiche**: `Entity.draw()` usa `"down"` come fallback (non più `"idle_down"`) quando `direction`/`idleDirection` sono `null` — perché gli oggetti statici (Door, Key, Chest, Boots) settano solo `down1`, mai `downIdle1/2`. Se aggiungi un nuovo oggetto statico, **niente panico**: eredita questo fix automaticamente.
- **Palette swap**: va applicato ad ogni punto che disegna un'immagine, non solo in `Entity.draw()`. Punti attualmente coperti: `Entity.draw()` (ramo normale), `Entity.dyingAnimation()` (blink/morte), `CombatState.drawMonster()`. Se aggiungi un nuovo punto di disegno custom per un'entità con palette, ricorda di applicare `PaletteSwap.getOrCreate(...)` anche lì.
- **Key/Door segnalati come "non funzionanti"** nonostante il fix della direzione di default — da verificare a fondo (vedi TODO), non richiudere la questione finché non è confermato visivamente in gioco.
- **I colori della palette devono combaciare esattamente (bit a bit)** con i pixel reali dello sprite — usa `ColorDump.java` per leggerli, non inventarli.
- **Asset del glow del menu**: è largo e sottile (81×9), non stretto e alto — se lo rifai/sostituisci, ricorda che il codice cresce in **larghezza**, non altezza.
- **Stat init order**: livello va inizializzato prima delle stat derivate (bug ricorrente in passato).
- **Zoom** (`GamePanel.zoomInOut`): richiede il ricaricamento completo delle immagini (tile + player + NPC con `getImage()`), non un semplice scale a runtime.
- **Wrap navigazione menu**: main menu (stato 0) blocca ai bordi (0..3, niente wrap); classe/difficoltà (stati 1/2) fanno wrap risalendo da 0 a 3 ma non ridiscendendo da 3 a 0 — asimmetria del codice originale, mantenuta di proposito per non cambiare comportamento esistente senza che fosse richiesto.
- **`drawImage` a schermo intero senza aspect ratio = stretch** (bug del 27/07): `book.png` veniva disegnato con `g2.drawImage(img, 0, 0, screenWidth, screenHeight, null)`, che **ignora le proporzioni originali** dell'immagine e la stira per riempire tutto lo schermo (768×576, rapporto 4:3) — un'immagine quadrata 272×272 risultava visibilmente più larga. Le cinematic invece usavano già `Math.min(screenW/imgW, screenH/imgH)` + centratura, quindi restavano quadrate. **Regola**: qualunque immagine disegnata a "schermo intero" deve passare da questa stessa formula di scaling uniforme, mai da un `drawImage` con `screenWidth`/`screenHeight` diretti.
- **Attenzione nel copiare una formula da un punto all'altro**: durante il fix di cui sopra, un tentativo di replicare la formula sul frame dell'animazione voltapagina ha lasciato per errore `gp.bookImage` al posto della variabile locale `frame` (sia nei calcoli che nel `drawImage` finale) — il codice compilava ma disegnava l'immagine sbagliata. Quando si copia una formula di scaling/posizionamento, **controllare che ogni occorrenza della variabile "sorgente" del blocco originale sia stata sostituita**, non solo la prima.

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

- [ ] Tradurre in inglese `ElementSystem.java` (nomi `Element`/`StatusEffect`/`displayName`) e `Reaction.java` — al momento restano in italiano nonostante `CombatState.java` sia stato tradotto, quindi si vedono nomi come "Raggio"/"Folgore" mescolati a testo inglese.
- [ ] `commandNum == 1` ("Load Game") nel main menu è ancora uno stub (`/* ADD LATER */`) — nessun salvataggio implementato.
- [ ] `Options` (main menu) è uno stub — nessuna schermata opzioni.
- [ ] `Inventory` e `Minimap` in combattimento sono placeholder (mostrano solo un messaggio).
- [ ] Il glow PNG per l'hover del menu va creato/importato — fatto ma non funzionante
- [ ] Verificare che tutti gli altri oggetti statici (Chest, Boots) siano effettivamente istanziati in `AssetSetter` — solo Key e Door sono attivi al momento, **ma segnalati come non funzionanti**: da investigare a fondo (il fix della direzione di default dovrebbe averli sbloccati, ma va confermato in gioco).
- [ ] Aggiungere tutti gli effetti delle reazioni.
- [ ] Implementare il contenuto vero delle pagine del libro per ogni `bookindex`/`bookzone`/`bookpage` — al momento solo scheletro di navigazione + testo placeholder in `UI.getBookContentText()`. `BOOKZONE_COUNT`/`MICRO_PAGE_COUNT` in `GamePanel` sono placeholder uguali per tutte le macrozone (2 e 2) — quasi certamente da rendere specifici per macrozona quando i contenuti veri saranno pronti (es. Quest potrebbe avere più sezioni di Calendario).
- [ ] `bookzone` (sezione dentro alla macrozona) non ha ancora una UI cliccabile — `GamePanel.selectBookZone()` esiste ma nessun bottone lo chiama, servono asset dedicati (non ha senso riusare `extended_bookmarks_X` per questo, sono specifici della macrozona).
- [ ] Nessuna navigazione da tastiera per `bookindex`/`bookzone` (solo mouse per ora) — solo LEFT/RIGHT per `bookpage` restano da tastiera. Da aggiungere se serve accessibilità/controller.

---

*Fine status — prossimo aggiornamento quando accumuliamo un altro blocco di modifiche.*