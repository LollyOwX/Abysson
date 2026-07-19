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

## 1. Architettura generale

- **Java Swing**, game loop standard (thread + `repaint()`), mondo a tile.
- Classi core: `GamePanel` (loop, stato di gioco, array entità), `TileManager`, `Entity` (classe base), `Player`, NPC/monster che estendono `Entity`.
- Stati di gioco (`gp.gameState`): `titleState`, `playState`, `pauseState`, `dialogueState`, `combatState`, `cinematicState` (non ancora usato).
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

## 3. Cose da ricordare (bug pattern ricorrenti + decisioni prese)

- **Direzione di default per entità statiche**: `Entity.draw()` usa `"down"` come fallback (non più `"idle_down"`) quando `direction`/`idleDirection` sono `null` — perché gli oggetti statici (Door, Key, Chest, Boots) settano solo `down1`, mai `downIdle1/2`. Se aggiungi un nuovo oggetto statico, **niente panico**: eredita questo fix automaticamente.
- **Palette swap**: va applicato ad ogni punto che disegna un'immagine, non solo in `Entity.draw()`. Punti attualmente coperti: `Entity.draw()` (ramo normale), `Entity.dyingAnimation()` (blink/morte), `CombatState.drawMonster()`. Se aggiungi un nuovo punto di disegno custom per un'entità con palette, ricorda di applicare `PaletteSwap.getOrCreate(...)` anche lì.
- **Key/Door segnalati come "non funzionanti"** nonostante il fix della direzione di default — da verificare a fondo (vedi TODO), non richiudere la questione finché non è confermato visivamente in gioco.
- **I colori della palette devono combaciare esattamente (bit a bit)** con i pixel reali dello sprite — usa `ColorDump.java` per leggerli, non inventarli.
- **Asset del glow del menu**: è largo e sottile (81×9), non stretto e alto — se lo rifai/sostituisci, ricorda che il codice cresce in **larghezza**, non altezza.
- **Stat init order**: livello va inizializzato prima delle stat derivate (bug ricorrente in passato).
- **Zoom** (`GamePanel.zoomInOut`): richiede il ricaricamento completo delle immagini (tile + player + NPC con `getImage()`), non un semplice scale a runtime.
- **Wrap navigazione menu**: main menu (stato 0) blocca ai bordi (0..3, niente wrap); classe/difficoltà (stati 1/2) fanno wrap risalendo da 0 a 3 ma non ridiscendendo da 3 a 0 — asimmetria del codice originale, mantenuta di proposito per non cambiare comportamento esistente senza che fosse richiesto.

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

---

## 5. TODO aperti

- [ ] Tradurre in inglese `ElementSystem.java` (nomi `Element`/`StatusEffect`/`displayName`) e `Reaction.java` — al momento restano in italiano nonostante `CombatState.java` sia stato tradotto, quindi si vedono nomi come "Raggio"/"Folgore" mescolati a testo inglese.
- [ ] `commandNum == 1` ("Load Game") nel main menu è ancora uno stub (`/* ADD LATER */`) — nessun salvataggio implementato.
- [ ] `Options` (main menu) è uno stub — nessuna schermata opzioni.
- [ ] `Inventory` e `Minimap` in combattimento sono placeholder (mostrano solo un messaggio).
- [x] ~~Il glow PNG per l'hover del menu va creato/importato~~ — fatto, ma cresceva in altezza invece che larghezza: sistemato il 19/07.
- [ ] Verificare che tutti gli altri oggetti statici (Chest, Boots) siano effettivamente istanziati in `AssetSetter` — solo Key e Door sono attivi al momento, **ma segnalati come non funzionanti**: da investigare a fondo (il fix della direzione di default dovrebbe averli sbloccati, ma va confermato in gioco).
- [ ] Aggiungere tutti gli effetti delle reazioni.

---

*Fine status — prossimo aggiornamento quando accumuliamo un altro blocco di modifiche.*