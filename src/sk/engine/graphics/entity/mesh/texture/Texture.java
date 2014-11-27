package sk.engine.graphics.entity.mesh.texture;

import java.awt.image.BufferedImage;

import sk.engine.core.Core;
import sk.engine.graphics.Color;
import sk.engine.util.BufferUtil;
import sk.engine.util.GLUtil;
import sk.engine.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {
	
	private int texMinFilter = GLUtil.NEAREST;
	private int texMagFilter = GLUtil.NEAREST;
	private int texWrapS = GLUtil.REPEAT;
	private int texWrapT = GLUtil.REPEAT;
	
	private int id;
	
	private int width;
	private int height;
	
	public Texture loadTexture(String path) {
		BufferedImage image;
		try {
			image = ResourceLoader.loadTexture(path);
			
			width = image.getWidth();
			height = image.getHeight();
			
			int[] pixels = new int[width * height];
			
			image.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int i = 0; i < pixels.length; i++) {
				pixels[i] = Color.convert(pixels[i], Color.FORMAT_ARGB, Color.FORMAT_ABGR);
			}
			
			loadTexture(pixels);
			
		} catch (Exception e) {
			System.err.println("Failed to load image from \""+path+"\"!");
			e.printStackTrace();
			Core.crash(1);
		}
		
		return this;
	}
	
	public Texture loadTexture(int[] pixels, int width, int height) {
		this.width = width;
		this.height = height;
		return loadTexture(pixels);
	}
	
	public Texture loadTexture(int[] pixels) {
		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, texMinFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, texMagFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, texWrapS);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, texWrapT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtil.toIntBuffer(pixels));
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return this;
	}
	
	public void bind(int target) {
		glActiveTexture(GL_TEXTURE0 + target);
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getID() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void destroy() {
		glDeleteTextures(id);
	}
}