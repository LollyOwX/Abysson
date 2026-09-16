package combat;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Estrae l'area di blocco RIGIDA di un'arma dalla sua immagine dettagliata
 * (Weapon.combatItemPath): ogni pixel NON vuoto (alpha non zero) diventa parte dell'area
 * rigida — stesso approccio "software shader" di main.PaletteSwap (scansione pixel-per-pixel,
 * risultato CACHATO per non ricalcolarlo ogni frame), qui applicato per costruire geometria
 * invece che ricolorare.
 *
 * Le zone DEBOLI non sono generate qui — restano da definire a parte.
 *
 * Convenzione di pivot: il punto locale (0,0) — l'impugnatura — è il CENTRO-BASSO
 * dell'immagine; l'asse Y verso l'alto (negativo) è verso la punta, coerente con le zone già
 * definite a mano in WeaponRegistry.
 *
 * Ottimizzazione: invece di un rettangolo per singolo pixel (lentissimo su un'immagine
 * dettagliata — migliaia di Area.add()), unisce i pixel non vuoti CONTIGUI riga per riga in un
 * solo rettangolo a striscia — stessa area risultante, enormemente meno operazioni.
 */
public class WeaponHitboxBuilder {

    private static final Map<String, Area> cache = new HashMap<>();

    /** Area rigida per l'immagine sorgente, cachata per chiave (di norma Weapon.combatItemPath)
     *  — non riscansiona i pixel se già calcolata in precedenza per la stessa chiave. */
    public static Area getOrBuild(String cacheKey, BufferedImage source) {
        if (source == null) return new Area();
        Area cached = cache.get(cacheKey);
        if (cached != null) return cached;

        Area result = new Area();
        int width  = source.getWidth();
        int height = source.getHeight();
        double pivotX = width / 2.0;
        double pivotY = height; // centro-basso: il fondo dell'immagine è l'impugnatura

        for (int y = 0; y < height; y++) {
            int runStart = -1;
            for (int x = 0; x < width; x++) {
                boolean opaque = (source.getRGB(x, y) >>> 24) != 0; // alpha non zero = pixel "pieno"
                if (opaque && runStart == -1) {
                    runStart = x;
                } else if (!opaque && runStart != -1) {
                    result.add(new Area(new Rectangle2D.Double(runStart - pivotX, y - pivotY, x - runStart, 1)));
                    runStart = -1;
                }
            }
            if (runStart != -1) {
                result.add(new Area(new Rectangle2D.Double(runStart - pivotX, y - pivotY, width - runStart, 1)));
            }
        }

        cache.put(cacheKey, result);
        return result;
    }
}
