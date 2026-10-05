# Abysson — Status del codice (2026-10-05)

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
- **Dialogo NPC**: `Entity.givesQuestId` porta solo il DATO ("quale quest"), impostato in `AssetSetter.place()` (overload a 7 argomenti, `null` = nessuna) o nel costruttore dell'NPC. `Entity.speak()` NON la assegna più da sola: sta alla sottoclasse NPC decidere A QUALE `dialoguesIndex` chiamare `gp.questManager.startQuest(givesQuestId)` — stesso pattern usato per qualunque altro regalo condizionato al dialogo (es. la spada in `Npc_HumanRedWorker`). `startQuest()` resta idempotente, sicuro anche se richiamato più volte.
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

**Weapon**: `WeaponType` (il mio placeholder a 3 valori) sostituito da `WeaponCategory` (SPADE/MAZZE/LANCE/ARMI_A_DISTANZA/DIFENSIVE, 5) + `WeaponSubtype` (16 valori reali, ognuno con la propria `category`). `lama` (che accorpava taglio+contundente) **spezzato** in `taglio`+`contundente` distinti (per il combo di Mazza chiodata/Ascia). Nuovi campi: `disarmChance` (Frusta), `stunChance` (Falce — riusa `StatusEffect.STORDIMENTO` già esistente), `counterattackChance` (Coltello da lancio), `peso` (Spadone: il danno usa questo invece di taglio/contundente/punta), `comboTypes` (`DamageType[]`, null di default — Mazza chiodata: {PERFORANTE,CONTUNDENTE}, Ascia: {TAGLIO,CONTUNDENTE}). Le armi Difensive (Scudo/Broquel/Sai) di default vanno in `OffHand` invece di `MainHand`.

**Deliberatamente non fatto — resta dentro il perimetro "add components" già rimandato al futuro (§12)**:
- Nessun numero è popolato per `disarmChance`/`stunChance`/`counterattackChance`/`comboTypes` — non ho valori reali per "quanto" disarma una Frusta o stordisce una Falce, restano a 0/null finché non li imposta chi crea l'istanza vera (un futuro `WeaponRegistry`, stesso pattern di `Ability`/`QuestRegistry`).
- Nessuna logica in `CombatState` legge ancora questi campi (disarmo su hit, stordimento su hit, contrattacco, combo taglio+contundente, penetrazione per `WeightClass`) — solo la struttura dati, come per tutto il resto della categoria "add components".

## 15. Sessione 27/08 (continua) — Correzione slot + Registry + meccaniche armi in combattimento

**Correzione**: Helmet/Gorget erano finiti condivisi con Head/Neck dei Gioielli in §14 nonostante l'intento fosse tenerli separati — in realtà il codice era già corretto (slot dedicati `helmetSlot`/`gorgetSlot`), solo il commento sopra `Armor.java` diceva ancora "condivisi": corretto anche quello. Gioielli/Armi/Armatura restano completamente distaccati, nessuno slot in comune.

**`WeaponRegistry`/`ArmorRegistry`/`JewelryRegistry`** (nuovi, in `items/`) — stesso pattern statico di `Ability`/`QuestRegistry`. Un solo esempio reale per categoria (`"short_sword_basic"`, `"cuirasse_basic"`, `"iron_ring_basic"`) con numeri placeholder — nessun bilanciamento reale dato, solo per provare che l'impianto giri.

**Meccaniche armi ora vive in `CombatState.dealDamage()`**, automatiche su ogni colpo andato a segno (come deciso):
- **Combo** (`Weapon.comboTypes`): il bonus si somma al danno già inflitto (es. Mazza chiodata: `punta + contundente`).
- **Disarmo** (`disarmChance`): tira, se riesce disequipaggia il MainHand del bersaglio (solo se è il Player — i mostri non hanno equip).
- **Stordimento** (`stunChance`): tira, se riesce applica `StatusEffect.STORDIMENTO` per 1 turno (riusa il sistema già esistente).
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
- [ ] Calcolo PRECISIONE e mitigazione danni % (prossima sessione)
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
- `Item.tipologie` (String[]): l'oggetto perde durabilità solo per azioni di un tipo in lista. Tipi in `items/DamageTypes.java`: 7 elementi (FISICO, LUCE, FUOCO, ACQUA, TERRA, ARIA, FULMINE) + EROSIONE, VELENO, PSICHICO, SANGUINAMENTO, MALEDIZIONE. Default armature e scudi: tutti gli elementi + EROSIONE (non VELENO/PSICHICO/SANGUINAMENTO/MALEDIZIONE). Default armi: solo FISICO.
- Aggancio: `CombatState.wearEquipment()`, chiamata nel ramo "colpo andato a segno" di `dealDamage()`: -1 ai pezzi del bersaglio (armature e scudo) e all'arma di chi attacca (solo il Player) se il tipo (`DamageTypes.of(abilityElement)`, NONE = FISICO) è nella loro lista; poi `Player.refreshEquipment()`. La parata scala `durability` e ricalcola.
- **Durabilità scesa ma > 0**: armature e scudi perdono **solo la DIFESA**, in proporzione (`durabilità/massimo`); gli altri bonus restano. Armi: nessun calo.
- **A 0 (rotto)**: armi e Sai → nessun bonus, `weaponOf()` le ignora (niente danno diretto, disarmo, parata); armature e scudi → nessun bonus assoluto, nessun mitico, ma **tengono gli ID**, quindi i bonus set restano attivi. Riparazione (forgia): non esiste ancora.
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
- `fix_weapon_registry.py` (già eseguito), `convert_components.py` (eseguito), `convert_sets_corda.py` (set + corda archi), `generate_loot_index.py`. Tutti da root progetto: `python script.py src/items`. Si possono rilanciare senza danni.

### 30.7 Come si fa per…
- **Dare un bonus a un component**: una riga `table(Family.X, Part.Y, StatType.Z, primoID, valori…)` in `ComponentRegistry`; poi assegnare l'ID agli oggetti (`w.lama[0] = 2;`).
- **Rinominare un ID**: `rename(Family.MELEE, Part.MANICO, 3, "Manico rifinito");` in fondo al blocco `static`.
- **Nuovo tipo di danno**: una costante in `DamageTypes` (+ aggiungerla al default se armature/scudi devono consumarsi con quel tipo); poi far restituire il tipo dal punto che infligge il danno.
- **Nuovo pezzo/gamba**: nel registry impostare `upperID` (upper) oppure `lowerID` + `acceptedUpperIDs` (gambe); poi rilanciare `generate_loot_index.py`.

### 30.8 Verifica e limiti
Compilazione pulita su tutto il progetto; test con oggetti veri: durabilità per tipologia (veleno non consuma, fuoco sì), DIFESA proporzionale e a 0, rottura arma, `activeFullID()` con 12 pezzi reali (11, 22, 31, 32 validi; 12 e 21 no; elmo diverso, gamba mancante e pezzo rotto verificati), distribuzione del loot su 200.000 estrazioni. **Non testato in-game** (nessun asset grafico nel sandbox); la parata con arma rotta è verificata solo per lettura del codice.
