package sk.engine.graphics.entity.mesh.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;

import sk.engine.graphics.Color;
import sk.engine.util.ResourceLoader;

public class SpriteSheet {
	
	private Texture[][] textures;
	
	private int totalWidth;
	private int totalHeight;
	
	private int spritesX;
	private int spritesY;
	
	private int frameWidth;
	private int frameHeight;
	
	/**
	 * 
	 * @param spritesY The total amount of spritesY
	 * @param spritesX The total amount of spritesX for each animation
	 * @param frameWidth The width of each animation frame
	 * @param frameHeight The height of each animation frame
	 */
	public SpriteSheet(int spritesX, int spritesY, int frameWidth, int frameHeight) {
		this.spritesX = spritesX;
		this.spritesY = spritesY;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		textures = new Texture[spritesY][spritesX];
	}
	
	public SpriteSheet loadTexture(String path) {
		
		try {
			BufferedImage image = ResourceLoader.loadTexture(path);
			totalWidth = image.getWidth();
			totalHeight = image.getHeight();
			
			int[] raw = new int[totalWidth * totalHeight];
			image.getRGB(0, 0, totalWidth, totalHeight, raw, 0, totalWidth);
			
			int[][] pixels = new int[totalHeight][totalWidth];
			
			for(int i = 0; i < totalHeight; i++) {
				for(int j = 0; j < totalWidth; j++) {
					pixels[i][j] = raw[j + i * totalWidth];
				}
			}
			
			for(int i = 0; i < spritesY; i++) {
				for(int j = 0; j < spritesX; j++) {
					int[] data = new int[frameWidth * frameHeight];
					for(int k = 0; k < frameHeight; k++) {
						for(int l = 0; l < frameWidth; l++) {
							data[l + k * frameWidth] = Color.convert(pixels[k + i * frameHeight][l + j * frameWidth], Color.FORMAT_ARGB, Color.FORMAT_ABGR);
						}
					}
					textures[i][j] = new Texture().loadTexture(data, frameWidth, frameHeight);
				}
			}
			
		} catch (IOException e) {
			System.err.println("Failed to load texture at \""+path+"\"!");
			e.printStackTrace();
		}
		
		return this;
	}
	
	public void bind(int tileX, int tileY, int target) {
		textures[tileY][tileX].bind(target);
	}
	
	public SpriteSheet setTexture(Texture texture, int x, int y) {
		textures[y][x] = texture;
		
		return this;
	}
	
	public Texture getTexture(int tileX, int tileY) {
		return textures[tileY][tileX];
	}
	
	public int getSpritesX() {
		return spritesX;
	}
	
	public int getSpritesY() {
		return spritesY;
	}
}