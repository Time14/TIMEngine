package sk.engine.audio;

import java.lang.Runnable;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.openal.AL;

import sk.engine.gamestate.GameStateManager;
import sk.engine.util.BufferUtil;
import static org.lwjgl.openal.AL10.*;

public class AudioManager implements Runnable {
	
	private static final HashMap<String, AudioClip> audioClips = new HashMap<>();
	
	private GameStateManager gsm;
	
	private boolean running;
	
	public AudioManager(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	private void init() {
		try {
			AL.create();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void update() {
		System.out.println("Running!");
	}
	
	public void run() {
		gsm.getCore().getGame().registerAudio();
		init();
		
		running = true;
		
		while(running) {
			update();
		}
		
		cleanUp();
	}
	
	public synchronized void setDead(boolean dead) {
		running = !dead;
	}
	
	private void cleanUp() {
		AL.destroy();
	}
	
	public static final void registerAudio(String key, String src) {
		
	}
}