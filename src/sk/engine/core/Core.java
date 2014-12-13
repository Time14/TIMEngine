package sk.engine.core;

import sk.engine.audio.AudioManager;
import sk.engine.gamestate.GameStateManager;
import sk.engine.graphics.Window;
import sk.engine.physics.PhysicsEngine;

/**
 * 
 * The Time Engine is an engine designed to benefit the needs for "Time: The Game"
 * 
 * @version v1.0
 * @author Alfred Sporre & Edvard Thörnros
 */
public final class Core {
	
	private int error;
	
	private SKFramework game;
	
	private GameStateManager gsm;
	
	private Window window;
	
	private AudioManager am;
	private Thread audioThread; 
	
	private static boolean running;
	
	public Core(SKFramework game) {
		this.game = game;
		running = false;
	}
	
	public int start() {
		
		window.create();
		
		game.enableDebugFeatures();
		
		gsm.initCore(this);
		
		am = new AudioManager(gsm);
		
		audioThread = new Thread(am, "AudioManager");
		
		audioThread.start();
		
		running = true;
		
		while(!window.isCloseRequested() && running) {
			Time.update();
			gsm.update();
			window.update();
		}
		
		cleanUp();
		
		return error;
	}
	
	public void exit(int error) {
		running = false;
		this.error = error;
		window.destroy();
	}
	
	private void cleanUp() {
		am.setRunning(false);
		while(audioThread.isAlive()){}
	}
	
	public void addComponents(Window window, GameStateManager gsm) {
		this.window = window;
		this.gsm = gsm;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public SKFramework getGame() {
		return game;
	}
	
	public AudioManager getAudioManager() {
		return am;
	}
	
	public static final void crash(int error) {
		System.exit(error);
	}
}