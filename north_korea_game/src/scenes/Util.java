package scenes;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

public class Util {
	private Util() {
	}

	public static File extractResourceToTempFile(String resourceName) throws IOException {
		// Get the resource URL (could be inside a JAR or on the filesystem)
		URL resourceUrl = Util.class.getClassLoader().getResource(resourceName);
		if (resourceUrl == null) {
			throw new FileNotFoundException("Resource not found: " + resourceName);
		}

		// Create a temporary file to store the extracted resource
		File tempFile = new File(System.getProperty("java.io.tmpdir"), "temp_" + resourceName.replace("/", "_"));

		// If the resource is inside a JAR (non-hierarchical URI), copy it to the temp
		// file
		if ("jar".equals(resourceUrl.getProtocol())) {
			try (InputStream inputStream = resourceUrl.openStream()) {
				Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		} else {
			// If the resource is a normal file (e.g., on the filesystem), just use it
			// directly
			try {
				tempFile = new File(resourceUrl.toURI());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return tempFile;
	}

	public static void drawStringCentered(int x, int y, String string, Graphics g) {
		FontMetrics metrics = g.getFontMetrics();

		int width = metrics.stringWidth(string);
		int height = metrics.getHeight();

		g.drawString(string, x - width / 2, y + height / 4);
	}

	public static File getFile(String path) {
		try {
			return extractResourceToTempFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void drawRectCentered(int x, int y, int w, int h, Graphics g) {
		g.drawRect(x - w / 2, y - h / 2, w, h);
	}

	public static void drawImageCentered(Image img, int x, int y, double scale, Graphics g) {
		int w = (int) (img.getWidth(null) * scale);
		int h = (int) (img.getHeight(null) * scale);

		g.drawImage(img, x - w / 2, y - w / 2, w, h, null);
	}

	public static void drawImageCentered(Image img, int x, int y, int width, Graphics g) {
		if (img == null)
			return;

		double scale = (double) width / img.getWidth(null);

		int w = (int) (img.getWidth(null) * scale);
		int h = (int) (img.getHeight(null) * scale);

		g.drawImage(img, x - w / 2, y - h / 2, w, h, null);
	}

	public static BufferedImage readImg(String path) {
		try {
			return ImageIO.read(Util.getFile(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	// thank chatgpt lol
	public static void drawStringWithLineBreaks(int x, int y, int maxWidth, String text, Graphics g) {
		Font font = g.getFont();
		FontMetrics metrics = g.getFontMetrics(font);
		int lineHeight = metrics.getHeight();

		String[] words = text.split(" ");
		StringBuilder currentLine = new StringBuilder();
		int lineY = y;

		for (String word : words) {
			// Check the width of the current line with the new word added
			if (metrics.stringWidth(currentLine.toString() + word) <= maxWidth) {
				currentLine.append(word).append(" ");
			} else {
				// If it exceeds the width, draw the current line and start a new one
				g.drawString(currentLine.toString(), x, lineY);
				lineY += lineHeight;
				currentLine = new StringBuilder(word + " ");
			}
		}
		// Draw the last line
		if (currentLine.length() > 0) {
			g.drawString(currentLine.toString(), x, lineY);
		}
	}

}
