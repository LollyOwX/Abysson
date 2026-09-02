package tile;

/**
 * Tile speciali (id >= TileManager.SPECIAL_TILE_BASE), caricate lazy per mappa.
 *
 * kind distingue COSA fa la tile oltre a bloccare/non bloccare il passaggio:
 *   - DECORATION: solo estetica, nessun comportamento (i vasi/fiori originali)
 *   - HOOK: punto d'aggancio del rampino — richiede Player.hookUnlocked. La destinazione
 *     (dove ti porta) NON è qui sopra: viene cercata in TileLinkRegistry per (mondo,col,row) di
 *     QUESTA tile piazzata sulla mappa — la stessa entry HOOK può quindi avere destinazioni
 *     diverse a seconda di dove la metti. Se il link punta allo stesso mondo è scenograficamente
 *     un "salto lungo" (muro), se punta a un mondo diverso è un cambio di piano/edificio —
 *     stessa meccanica sotto, cambia solo cosa succede a fine animazione (vedi Player.tryHook()).
 *   - CORNICE: salto a senso unico stile Pokémon — passableFrom indica da quale direzione di
 *     marcia si attraversa scendendo; dall'altro lato è collisione piena, sempre disponibile
 *     (non serve hookUnlocked, non serve nemmeno premere il tasto salto: scatta camminandoci
 *     contro dal verso giusto).
 *   - COLLINETTA: ostacolo saltabile in entrambi i sensi col tasto salto — SEMPLIFICAZIONE: la
 *     collisione resta quella dell'intera tile (non un rettangolo ridotto: il sistema di
 *     collisione qui lavora per cella intera, non per rettangoli dentro la cella), il salto
 *     scavalca l'intera tile atterrando su quella subito dopo. Da confermare se va bene così o
 *     se serve davvero la collisione ridotta (più lavoro, vedi STATUS.md).
 */
public enum SpecialTile {

    // ── Decorazione (esistenti) ────────────────────────────────
    vaso_casa1(true, Kind.DECORATION, null),
    vaso_casa1_rotto(true, Kind.DECORATION, null),
    fiore_casa2(false, Kind.DECORATION, null),
    fiore_casa2_appassito(false, Kind.DECORATION, null),

    // ── Rampino (Player.hookUnlocked) ──────────────────────────
    appiglio(true, Kind.HOOK, null),

    // ── Salto (sempre disponibile) ─────────────────────────────
    cornice_giu(true, Kind.CORNICE, Direction.DOWN),
    cornice_su(true, Kind.CORNICE, Direction.UP),
    cornice_sinistra(true, Kind.CORNICE, Direction.LEFT),
    cornice_destra(true, Kind.CORNICE, Direction.RIGHT),
    collinetta(true, Kind.COLLINETTA, null);

    public final boolean collision;
    public final Kind kind;
    public final Direction passableFrom; // solo per CORNICE — null per tutti gli altri kind

    SpecialTile(boolean collision, Kind kind, Direction passableFrom) {
        this.collision    = collision;
        this.kind         = kind;
        this.passableFrom = passableFrom;
    }

    public enum Kind { DECORATION, HOOK, CORNICE, COLLINETTA }
    public enum Direction { UP, DOWN, LEFT, RIGHT }
}
