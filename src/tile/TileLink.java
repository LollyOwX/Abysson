package tile;

/** Un collegamento tra due posizioni (mondo,col,row) — vedi TileLinkRegistry per il formato
 *  testuale e il parsing. Se fromWorld==toWorld è un "salto lungo" nello stesso piano
 *  (scenograficamente un'arrampicata su muro); se sono diversi è un cambio di mondo/piano
 *  (scenograficamente la stessa arrampicata, ma sotto cambia la mappa) — vedi SpecialTile. */
public class TileLink {
    public final String fromWorld;
    public final int fromCol;
    public final int fromRow;
    public final String toWorld;
    public final int toCol;
    public final int toRow;

    public TileLink(String fromWorld, int fromCol, int fromRow, String toWorld, int toCol, int toRow) {
        this.fromWorld = fromWorld;
        this.fromCol   = fromCol;
        this.fromRow   = fromRow;
        this.toWorld   = toWorld;
        this.toCol     = toCol;
        this.toRow     = toRow;
    }
}
