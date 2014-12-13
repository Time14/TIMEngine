package sk.engine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import sk.engine.core.Time;
import sk.engine.vector.Vector4f;
import static org.lwjgl.opengl.GL11.*;

public class Window {
	
	private Vector4f color;
	
	private static int width;
	private static int height;
	
	private int fps;
	
	private String title;
	
	private boolean showFPS;
	
	public Window(int width, int height, String title) {
		this(width, height, title, Color.BLACK);
	}
	
	public Window(int width, int height, String title, Vector4f color) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.color = color;
		
		fps = 0;
	}
	
	public void create() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
			
			initOpenGL();
			
			System.out.println("OpenGL v." + glGetString(GL_VERSION));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	private void initOpenGL() {
		glAlphaFunc(GL_GREATER, 0);
		glEnable(GL_ALPHA_TEST);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		//glEnable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
	}
	
	public void update() {
		if(fps > 0)
			Display.sync(fps);
		Display.update();
		
		if(showFPS)
			Display.setTitle(title + " - FPS: " + Time.getFPS());
		
		glClearColor(color.x, color.y, color.z, color.w);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public Window setBackgroundColor(Vector4f color) {
		this.color = color;
		
		return this;
	}
	
	public Window setFPS(int fps) {
		this.fps = fps;
		
		return this;
	}
	
	public void destroy() {
		Display.destroy();
	}
	
	public Window showFPS(boolean showFPS) {
		this.showFPS = showFPS;
		
		return this;
	}
	
	public boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
	
	public static final boolean isCreated() {
		return Display.isCreated();
	}
	
	public static final int getWidth() {
		return width;
	}
	
	public static final int getHeight() {
		return height;
	}
}