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

- [x] ~~"effetti attivi" post-turno ticchettano sul BERSAGLIO quando viene colpito~~ — risolto il 27/08: `CombatState.beforeTurn()`/`afterTurn()` ticchettano ogni entità sul PROPRIO turno (vedi §6).
- [x] ~~Aggiungere tutti gli effetti delle reazioni~~ — fatto il 27/08, tutte e 26 implementate (vedi §6). Restano STUB dichiarati solo Illuminazione/Vaporizzazione (nessun sistema di occultamento/volo nel gioco).
- [ ] `queueAction()`/`actionQueue` è una coda semplice (`ArrayDeque<String>`, un messaggio testuale alla volta) — se in futuro serve accodare azioni più ricche (non solo testo: es. un'animazione per messaggio, o più colpi con dati diversi ciascuno), andrà esteso oltre la semplice stringa.
- [ ] Tradurre in inglese `ElementSystem.java` (nomi `Element`/`StatusEffect`/`displayName`) e `Reaction.java` — al momento restano in italiano nonostante `CombatState.java` sia stato tradotto, quindi si vedono nomi come "Raggio"/"Folgore" mescolati a testo inglese.
- [ ] `commandNum == 1` ("Load Game") nel main menu è ancora uno stub (`/* ADD LATER */`) — nessun salvataggio implementato.
- [ ] `Options` (main menu) è uno stub — nessuna schermata opzioni.
- [ ] `Inventory` e `Minimap` in combattimento sono placeholder (mostrano solo un messaggio).
- [ ] Il glow PNG per l'hover del menu va creato/importato — fatto ma non funzionante
- [ ] Verificare che tutti gli altri oggetti statici (Chest, Boots) siano effettivamente istanziati in `AssetSetter` — solo Key e Door sono attivi al momento, **ma segnalati come non funzionanti**: da investigare a fondo (il fix della direzione di default dovrebbe averli sbloccati, ma va confermato in gioco).
- [ ] Contenuto vero delle pagine del libro: tolto tutto (modello dati `BookEntry`/`unlockedBookZones`, testo placeholder) il 02/08 — da riprogettare da zero quando si è pronti, probabilmente ripartendo da `BookEntry.java` (rimasto nel progetto, non referenziato) o da un approccio diverso.
- [ ] **Da confermare/bilanciare (27/08)**: `Ability.isRanged()` classifica Thunderbolt/AcquaJet/Lightray come "a distanza" e il resto come mischia — assunzione mia, il gioco non aveva finora questa nozione; verifica che rispecchi il design voluto (usata da Accecamento/Polverizzazione/Deviazione).
- [ ] **Da bilanciare (27/08)**: durata iniziale della Bruciatura Grave da Esplosione fissata a 5 (valore arbitrario, la formula del danno/rimozione dipende da questo numero — vedi §6). Item.percentBonus dei pezzi esistenti (es. `Sword_Basic_Iron`: +3/-1) ora significano percentuali, non più flat — stesso numero, effetto diverso, da ritarare quando ci sono più item con cui confrontare.
- [ ] **Correzione stessa sessione (27/08)**: ElementoATK/ElementDEF era stato inizialmente accorpato in una sola coppia di stat per tutti gli elementi — sbagliato, era imprecisione mia. Sistemato subito dopo in una coppia ATK/DEF PER ELEMENTO (FireATK/FireDEF...LightATK/LightDEF, vedi §6.5). Le basi del player partono tutte uguali (40/20 su ognuna) solo perché non c'è ancora differenziazione da equip/level design — da rivedere quando ci sarà.
- [ ] Posizioni placeholder dei pulsanti sottoargomento (`SUBTOPIC_IMAGE_RECTS` in `UI.java`) da aggiustare a vista, stesso procedimento già fatto per i bookmark (aprire `book_X.png` in un editor di immagini, leggere le coordinate pixel).
- [ ] `res/maps/world2.txt` esiste ma non è caricato da nessun codice (nessun `loadMap("/maps/world2.txt")` in giro) — usa anche ID tile 26-39, oltre il terreno definito (0-25) e sotto `SPECIAL_TILE_BASE=100`. Se in futuro verrà caricata andrà sistemata (o quegli ID vanno rimappati, o vanno definiti come terreno aggiuntivo in `getTileImage()`).
- [ ] ESC nel libro chiude tutto in un colpo solo, non risale di un livello alla volta (pagina→voce→area→chiuso) — possibile miglioramento futuro, non richiesto esplicitamente.

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
- **Abrasione**: bonificava (per errore) il danno FUOCO subito dal portatore; ora bonifica le SUE azioni FUOCO in uscita (coerente con l'essere classificata come effetto positivo).
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
- `entity/StatType.java`: da `ELEMENTO_ATK`/`ELEMENT_DEF` unici a 6 coppie, una per elemento — `FIRE_ATK`/`FIRE_DEF`, `WATER_ATK`/`WATER_DEF`, `ELECTRIC_ATK`/`ELECTRIC_DEF`, `EARTH_ATK`/`EARTH_DEF`, `WIND_ATK`/`WIND_DEF`, `LIGHT_ATK`/`LIGHT_DEF`. FISICO/NONE restano su `ATTACK`/`DIFESA` (non hanno una coppia elementale).
- `Entity.elementAttack`/`elementDefense`: da singolo `int` a `Map<Element,Integer>` (+ getter `getElementAttack(Element)`/`getElementDefense(Element)`, default 0 per elementi non impostati).
- `Player`: basi `baseElementAttack`/`baseElementDefense` sono ora anch'esse mappe per elemento (private); `recalculateStats()` cicla su tutti gli elementi applicando il bonus % dello `StatType` giusto a ciascuno. Partono tutte uguali (40 ATK / 20 DEF su ognuna) — stesso valore del vecchio stat unico, semplicemente non ancora differenziate.
- `ElementSystem.attackStat(Element)`/`defenseStat(Element)`: nuova mappa Element → StatType, usata da `recalculateStats()`. Ritornano `null` per FISICO/NONE.
- Tutte le formule "X\*ATK" delle reazioni (Folgore, Infiammazione, Raggio, Carbonizzazione, Sovraccarico, Tempesta, Ramificazione, Elettrizzazione, Firenado) ora usano lo stat ATK dell'elemento giusto invece del generico — es. Folgore (Fulmine) usa `ElectricATK`, Infiammazione (Fuoco) usa `FireATK`. Sovraccarico/Tempesta (50/50 tra due elementi) usano l'ATK dell'elemento estratto quel tick, quindi il 50/50 ora cambia davvero il numero, non solo l'etichetta come nel primo giro.
- `MON_Goblin` (usa Thunderbolt/Fulmine): imposta `elementAttack.put(FULMINE, ...)` invece del vecchio campo singolo.

## 7. Sessione 27/08 (continua) — Struttura Quest

Nuovo package `quest/` (7 file): `QuestEventType` (enum: KILL/TALK/REACH_LOCATION/COLLECT/CUSTOM), `QuestTier` (CRITICO/MAGGIORE/FLAVOR, rispecchia i tier del Calendar System §1), `QuestState` (NOT_STARTED/ACTIVE/COMPLETED/FAILED — FAILED è uno stub non ancora agganciato), `QuestStep` (uno step = descrizione + trigger + target + goal, con `matches()`/`isComplete()`), `Quest` (**step machine**: sequenza di `QuestStep`, uno attivo alla volta — `notify()` avanza solo se l'evento combacia con lo step corrente, non con step futuri o già superati), `QuestRegistry` (dispatcher statico delle definizioni, stesso stile di `combat/Ability.java` — vedi il suo header per "come aggiungere una nuova quest"), `QuestManager` (liste `active`/`completed` separate; correzione fatta nella sessione successiva, vedi §8 — non collegate 1:1 a bookzone come scritto qui inizialmente).

**Quest ottenibili sia da dialogo NPC che da trigger di mappa, dal primo giro**:
- Dialogo: nuovo campo `Entity.givesQuestId` (null di default) — se impostato, `speak()` chiama `QuestManager.startQuest()` la prima volta che ci si parla; ad ogni `speak()` notifica anche un evento `TALK` col nome dell'NPC (per step tipo "parla con X").
- Trigger di mappa: due nuovi metodi in `EventHandler` (`questStartEvent()`/`locationEvent()`), stesso pattern di `damagePit()`/`healingPool()` esistenti. **Capacità pronta ma non richiamata da `checkEvent()`** — nessuna mappa ha ancora un punto reale a cui agganciarle; per usarle basta una riga in `checkEvent()` come per gli eventi esistenti.
- Combattimento: `CombatState.onVictory()` notifica `KILL` col nome del mostro sconfitto — già collegato, funziona.

**Un solo esempio concreto** in `QuestRegistry` (`"goblin_bounty"`, un solo step: sconfiggi il Goblin) per provare che l'impianto giri end-to-end con quello che già esiste (l'unico mostro nel gioco). Il campo `giverNpc` è un placeholder ("Villager") — nessun NPC con quel nome esiste ancora, da allineare quando c'è.

**Deliberatamente non collegato**:
- `QuestEventType.COLLECT` — nessun sistema di inventario reale esiste (`Player.pickUpObject()` è uno stub vuoto), quindi non c'è nulla da notificare. Il valore enum esiste già per quando ci sarà.
- Rendering delle quest nel libro (bookzone Quests) — solo la struttura dati è pronta, la UI resta da fare.
- Persistenza: nessun sistema di salvataggio esiste ancora (§5) — lo stato delle quest si perde alla chiusura del gioco, come tutto il resto.

## 8. Sessione 27/08 (continua) — Quest nel libro + notifiche a schermo

**Correzione rispetto a §7**: avevo scritto lì che `BOOKZONE_COUNT` per l'area Quest fosse 2 (Attive/Completate) — falso, il valore vero nel codice è **5** (il commento in `GamePanel`/`UI` diceva "quest=2" ma sia l'array `BOOKZONE_COUNT` sia i `Rectangle` in `SUBTOPIC_IMAGE_RECTS` per quell'area erano già 5: commento sbagliato, corretto anche quello. Il progetto usa la convenzione "bookzone = singola voce, es. Quest 1/Quest 2" (vedi il commento originale sui 3 livelli in `GamePanel.java`) — quindi **una bookzone = una quest**, non uno stato.

- `QuestManager.getKnownQuests()`: lista concatenata active+completed (ordine stabile) — bookzone *N* mostra la N-esima quest conosciuta dal giocatore (non tutte quelle esistenti in `QuestRegistry`: una quest mai iniziata non compare, niente spoiler). Cap a 5 slot per ora (come `BOOKZONE_COUNT`); da alzare (lì + i `Rectangle` corrispondenti in `UI.SUBTOPIC_IMAGE_RECTS`) se `QuestRegistry` supera le 5 quest raggiungibili in una run.
- `UI.drawQuestPageContent()`: disegna testo VERO per la prima volta in una pagina del libro (finora erano solo immagini pre-renderizzate, `book_X.png`, senza overlay di testo). bookpage 0 = titolo + descrizione + stato; bookpage 1 = lista obiettivi con `[x]`/`[ ]` e progresso (`3/5`) sullo step attivo. Area di testo (`PAGE_CONTENT_RECT`) è un **placeholder** (95,20,165,230 nello spazio immagine 272×272) — da aggiustare a vista come i rettangoli di bookmark/subtopic, nessun riferimento precedente da cui partire visto che nessun'altra area disegna ancora testo.
- `QuestManager.getQuestById(id)`: lookup diretto per id (cercando in active poi completed) — utile per far dipendere qualcos'altro dallo stato di UNA quest specifica (es. un dialogo NPC diverso se il giocatore l'ha già completata) senza scorrere a mano le due liste ogni volta.

**Come il giocatore viene informato**: prima di questa sessione, **in nessun modo** — `UI.showMessage()`/`messageOn` esistevano già (usati da `Npc_HumanRedWorker` per "Hai ottenuto: Spada!") ma non erano MAI disegnati da nessuna parte: un aggancio morto, bug preesistente non mio. Aggiunto `UI.drawMessage()` (banner in alto, ~2 secondi, richiamato da `draw()` sempre tranne che nel titolo) e ora `QuestManager` lo usa per tre eventi: avvio quest ("Nuova missione: ..."), avanzamento di step ("Nuovo obiettivo: ..."), completamento ("Missione completata: ..."). Di riflesso, questo ha sistemato anche il messaggio della spada, che ora funziona davvero.
- `QuestManager` tiene ora un riferimento a `GamePanel` (passato nel costruttore, `new QuestManager(this)` in `GamePanel`) per poter chiamare `gp.ui.showMessage()` — stesso pattern di `CombatState`/`EventHandler`/`UI` stesse, che tengono già tutte `gp`.

## 9. Guida pratica — Aggiungere e usare le Quest

Riferimento autonomo (non un log di sessione): tutto quello che serve per lavorare sul sistema quest senza dover rileggere §6-8. Se qualcosa qui e nei commenti del codice (`QuestRegistry`, `QuestManager`, `EventHandler`) diverge, fidati del codice: questa sezione va tenuta aggiornata a mano quando cambia qualcosa di strutturale.

### 9.1 Aggiungere una nuova quest
In `quest/QuestRegistry.get()`, un nuovo `case` con un id univoco:
```java
case "old_man_favor":
    return new Quest(
            "old_man_favor",
            "Un favore per il vecchio",
            "Il vecchio del villaggio ha bisogno di aiuto.",
            QuestTier.FLAVOR,
            "OldMan", // NPC che la assegna (solo informativo, non collegato automaticamente — vedi 9.2)
            List.of(
                    new QuestStep("Torna a parlare con lui", QuestEventType.TALK, "OldMan"),
                    new QuestStep("Sconfiggi il Goblin", QuestEventType.KILL, "Goblin", 3) // goalCount=3
            )
    );
```
- È una **step machine**: gli step si sbloccano in ordine, uno per volta. Un evento che combacerebbe con lo step 2 non fa nulla se la quest è ancora allo step 0.
- `targetId` deve combaciare **esattamente** con quello che passa chi notifica l'evento (case-sensitive): `monster.name` per KILL (es. `"Goblin"`), `entity.name` dell'NPC per TALK, il `locationId` scelto a mano per REACH_LOCATION.
- `goalCount` di default è 1 — per "N volte" usa il costruttore a 4 argomenti.
- Ogni chiamata a `QuestRegistry.get()` crea un'istanza NUOVA (stato mutabile) — non riusarne una condivisa.

### 9.2 Come farla partire
- **Dialogo NPC**: `npc.givesQuestId = "old_man_favor";` dove l'NPC viene istanziato. La prima volta che il giocatore ci parla, `speak()` chiama da solo `QuestManager.startQuest()` — idempotente, sicuro anche se ci riparla altre volte.
- **Trigger di mappa**: in `EventHandler.checkEvent()`, una riga come le altre già presenti (`damagePit`/`healingPool`):
  ```java
  questStartEvent(30, 15, "any", "old_man_favor");
  ```
  `"any"` = da qualunque direzione; altrimenti `"up"`/`"down"`/`"left"`/`"right"`.
- **Combattimento**: nessuna azione da fare per KILL, `CombatState.onVictory()` notifica già ogni vittoria.
- Il campo `giverNpc` in `Quest` è solo testo/riferimento, NON collega automaticamente nulla: l'associazione vera con un NPC è impostare `givesQuestId` su quell'NPC come sopra.

### 9.3 Tipi di obiettivo (QuestEventType)
| Tipo | Chi lo notifica | targetId | Stato |
|---|---|---|---|
| `KILL` | `CombatState.onVictory()` | `monster.name` | Collegato |
| `TALK` | `Entity.speak()` (ogni volta che si parla a un NPC) | `entity.name` | Collegato |
| `REACH_LOCATION` | `EventHandler.locationEvent(col,row,dir,locationId)` — riga da aggiungere in `checkEvent()` | id a piacere | Capacità pronta, nessun punto di mappa la usa ancora |
| `COLLECT` | — | — | **Non collegato**: nessun inventario reale esiste (`Player.pickUpObject()` è uno stub vuoto). Andrebbe agganciato lì, ma prima serve un sistema oggetti minimo |
| `CUSTOM` | a mano, dal punto del codice dove serve: `gp.questManager.notify(QuestEventType.CUSTOM, "un_id")` | id a piacere | Per obiettivi troppo specifici per meritare un tipo tutto loro |

### 9.4 Leggere lo stato di una quest da altrove nel codice
```java
Quest q = gp.questManager.getQuestById("old_man_favor"); // null se mai iniziata
if (q != null && q.state == QuestState.COMPLETED) { /* ... */ }
if (q != null) {
    QuestStep step = q.currentStep(); // step attivo, non null se la quest è ACTIVE
    // step.description, step.currentCount, step.goalCount
}
```
`gp.questManager.getActive()` / `getCompleted()` per le liste intere; `getKnownQuests()` per la lista concatenata (ordine stabile) usata anche dal libro.

### 9.5 Come il giocatore viene informato
Automatico, non serve fare nulla in più: `QuestManager` chiama `gp.ui.showMessage()` (banner in alto, ~2 secondi) da solo su avvio quest, avanzamento di step e completamento. Se serve un messaggio diverso da quello di default (es. testo custom invece di "Nuovo obiettivo: <descrizione step>"), va cambiato dentro `QuestManager.notify()`/`startQuest()` — non c'è un modo per personalizzarlo per singola quest al momento.

### 9.6 Vederla nel libro
Non serve fare nulla in più: qualunque quest **conosciuta** (attiva o completata — non quelle mai iniziate) compare da sola come bookzone nell'area Quest, nell'ordine `getKnownQuests()`. Limite attuale: **5 slot** (`GamePanel.BOOKZONE_COUNT[1]`). Se `QuestRegistry` arriva ad avere più di 5 quest raggiungibili nella stessa run, vanno alzati insieme:
1. `GamePanel.BOOKZONE_COUNT` (il valore per l'area quests, indice 1)
2. Il numero di `Rectangle` in `UI.SUBTOPIC_IMAGE_RECTS[1]` (deve combaciare 1:1)

bookpage 0 = titolo/descrizione/stato, bookpage 1 = lista obiettivi con `[x]`/`[ ]` e progresso. `UI.PAGE_CONTENT_RECT` è la zona di testo — placeholder, da aggiustare a vista sul PNG vero se il testo esce dai margini della pagina disegnata.

### 9.7 Limiti noti (da non dare per scontato che funzionino)
- Nessuna persistenza: lo stato quest si perde alla chiusura del gioco (nessun sistema di salvataggio esiste, §5).
- `QuestState.FAILED` esiste ma non è agganciato a nulla — nessuna quest può "fallire" al momento.
- `COLLECT` non è collegato (9.3).
- Un solo esempio reale in `QuestRegistry` (`"goblin_bounty"`) — tutto il resto in questa guida è dimostrato solo in astratto, non testato in gioco con contenuti veri.

## 10. Sessione 27/08 (continua) — Quest da AssetSetter + messaggi impilati

**`AssetSetter.place()`**: nuovo overload a 7 argomenti (`..., paletteDef, questId`) — se `questId` è `null` non assegna nessuna quest (comportamento invariato per obj/monster, che continuano a usare la versione a 6 argomenti senza toccarla). `setNpc()` ora collega davvero `Npc_HumanRedWorker` alla quest `"goblin_bounty"` (l'unica reale in `QuestRegistry`) — primo esempio end-to-end funzionante: dialogo che assegna la quest, uccisione del Goblin che la completa, libro che la mostra.
- **Nota sulla scelta del punto**: l'alternativa (metterlo nel costruttore dell'NPC stesso, `Npc_HumanRedWorker.java`) resta più coerente con come il resto del progetto è scritto (quell'NPC si autoconfigura interamente lì — dialoghi, hitbox...) — qui è stato messo in `AssetSetter` su richiesta esplicita, perché in questo punto si vede a colpo d'occhio insieme a tutti gli altri piazzamenti. Da tenere a mente se in futuro si aggiungono più NPC della stessa classe con quest diverse: quel caso torna a favorire il costruttore.

**`UI`: da singolo messaggio a coda impilata**: prima un secondo `showMessage()` sovrascriveva silenziosamente il primo se ancora a schermo (helper interno, non esposto). Ora `messages` è una lista di box, ognuno con il proprio timer indipendente (~2 secondi) — più messaggi contemporanei si impilano uno sotto l'altro invece di accavallarsi. Con l'NPC attuale la sovrapposizione non capita mai davvero in pratica (`givesQuestId` scatta al primo dialogo, la spada al terzo — momenti diversi), ma resta la protezione generale corretta per quando due notifiche arriveranno sullo stesso frame.