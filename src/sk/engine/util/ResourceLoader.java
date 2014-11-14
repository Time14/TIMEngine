package sk.engine.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public final class ResourceLoader {
	
	public static final String loadSource(String path) {
		Scanner scanner = new Scanner(getInputStream(path));
		StringBuilder src = new StringBuilder();
		
		while(scanner.hasNextLine()) {
			src.append(scanner.nextLine());
			if(scanner.hasNextLine())
				src.append("\n");
		}
		
		return src.toString();
	}
	
	public static final BufferedImage loadTexture(String path) throws IOException {
		return ImageIO.read(getInputStream(path));
	}
	
	public static final InputStream getInputStream(String path) {
		return ResourceLoader.class.getClassLoader().getResourceAsStream(path);
	}
}