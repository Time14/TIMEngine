package sk.engine.core;

import sk.engine.gamestate.GameStateManager;
import sk.engine.graphics.Window;
import sk.engine.physics.PhysicsEngine;

/**
 * 
 * The Time Engine is an engine designed to benefit the needs for "Time: The Game"
 * 
 * @version v1.0
 * @author Alfred Sporre & Edvard Th�rnros
 */
public final class Core {
	
	private int error;
	
	private SKFramework game;
	
	private GameStateManager gsm;
	
	private Window window;
	
	private static boolean running;
	
	public Core(SKFramework game) {
		this.game = game;
		running = false;
	}
	
	public int start() {
		
		window.create();
		
		gsm.initCore(this);
		
		running = true;
		
		while(!window.isCloseRequested() && running) {
			Time.update();
			gsm.update();
			window.update();
		}
		
		return error;
	}
	
	public void exit(int error) {
		running = false;
		this.error = error;
		window.destroy();
	}
	
	public void addComponents(Window window, GameStateManager gsm) {
		this.window = window;
		this.gsm = gsm;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public static final void crash(int error) {
		System.exit(error);
	}
}