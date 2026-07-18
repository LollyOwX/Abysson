package main;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Palette swap "software shader" per sprite pixel art.
 *
 * Rimappa colori esatti (RGBA) di un'immagine sorgente verso nuovi colori,
 * secondo una mappa oldColor -> newColor. Concettualmente è l'equivalente
 * di uno shader "palette swap" nei motori 2D classici, ma fatto via CPU
 * (Java2D non espone shader/fragment programs come OpenGL).
 *
 * USO TIPICO (in un'Entity):
 *
 *   Map<Integer,Integer> redPalette = new HashMap<>();
 *   redPalette.put(0xFF3B8C3B, 0xFFB03030); // verde -> rosso (ARGB esatti dei tuoi sprite)
 *   redPalette.put(0xFF276627, 0xFF7A1F1F); // ombra verde -> ombra rossa
 *
 *   BufferedImage redVariant = PaletteSwap.getOrCreate("goblin_red", down1, redPalette);
 *   g2.drawImage(redVariant, x, y, w, h, null);
 *
 * Il risultato viene CACHATO per (chiave, immagine sorgente) — lo swap pixel-per-pixel
 * viene calcolato una sola volta, non ad ogni frame.
 */
public class PaletteSwap {

    // cache: "chiavePalette::identityHash(sourceImage)" -> immagine già ricolorata
    private static final Map<String, BufferedImage> cache = new HashMap<>();

    /**
     * Ritorna la versione ricolorata di `source` secondo `colorMap` (ARGB int -> ARGB int).
     * Se già calcolata in precedenza per la stessa combinazione (key, source), la riusa dalla cache.
     *
     * @param paletteKey identificatore univoco della palette (es. "red", "shiny", "frozen")
     * @param source     sprite originale (deve essere leggibile pixel-per-pixel, va bene TYPE_INT_ARGB)
     * @param colorMap   mappa colore-esatto-sorgente -> colore-esatto-destinazione (0xAARRGGBB)
     */
    public static BufferedImage getOrCreate(String paletteKey, BufferedImage source, Map<Integer, Integer> colorMap) {
        if (source == null) return null;
        String cacheKey = paletteKey + "::" + System.identityHashCode(source);
        BufferedImage cached = cache.get(cacheKey);
        if (cached != null) return cached;

        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                int argb = source.getRGB(x, y);
                Integer mapped = colorMap.get(argb);
                result.setRGB(x, y, mapped != null ? mapped : argb);
            }
        }
        cache.put(cacheKey, result);
        return result;
    }

    /**
     * Variante con struttura ad array invece di Map: ogni riga di `pairs` è {oldARGB, newARGB}.
     * Comodo se vuoi definire la palette direttamente in AssetSetter come dati grezzi,
     * es.: new int[][]{ {0xFF3B8C3B, 0xFFB03030}, {0xFF276627, 0xFF7A1F1F} }
     * Nessun sentinel necessario per "voce vuota": l'array ha semplicemente la lunghezza
     * di cui hai bisogno (0 righe = nessuno swap, ma in quel caso è meglio passare pairs = null).
     */
    public static BufferedImage getOrCreate(String paletteKey, BufferedImage source, int[][] pairs) {
        if (source == null) return null;
        if (pairs == null || pairs.length == 0) return source;
        String cacheKey = paletteKey + "::" + System.identityHashCode(source);
        BufferedImage cached = cache.get(cacheKey);
        if (cached != null) return cached;

        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                int argb = source.getRGB(x, y);
                int mapped = argb;
                for (int[] pair : pairs) {
                    if (pair[0] == argb) { mapped = pair[1]; break; }
                }
                result.setRGB(x, y, mapped);
            }
        }
        cache.put(cacheKey, result);
        return result;
    }

    /** Svuota la cache (es. utile in hot-reload o cambio livello con molti sprite temporanei). */
    public static void clearCache() {
        cache.clear();
    }

    /**
     * Converte una definizione compatta "RRGGBB>RRGGBB,RRGGBB>RRGGBB" in un int[][] di coppie
     * ARGB pronte per getOrCreate(). Ritorna null se def è null/vuota (= nessuno swap).
     * Esempio: parsePalette("3B8C3B>B03030,276627>7A1F1F")
     */
    public static int[][] parsePalette(String def) {
        if (def == null || def.isEmpty()) return null;
        String[] entries = def.split(",");
        int[][] pairs = new int[entries.length][2];
        for (int i = 0; i < entries.length; i++) {
            String[] parts = entries[i].split(">");
            pairs[i][0] = 0xFF000000 | Integer.parseInt(parts[0].trim(), 16);
            pairs[i][1] = 0xFF000000 | Integer.parseInt(parts[1].trim(), 16);
        }
        return pairs;
    }
}
