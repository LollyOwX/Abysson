package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UtilityTool {
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}

	/** Carica un font da res/ (es. "/font/MioFont.ttf"). Se manca o è rotto, scrive l'errore e ripiega su SansSerif. */
	public Font loadFont(String resourcePath) {
		try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
			if (is == null) throw new IOException("resource not found");
			return Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			System.err.println("ERROR: font " + resourcePath + ": " + e.getMessage());
			return new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		}
	}
}
