package sk.game;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

import sk.engine.audio.AudioClip;
import sk.engine.audio.AudioManager;
import sk.engine.core.Core;
import sk.engine.core.SKFramework;
import sk.engine.debug.Debug;
import sk.engine.gamestate.GameStateManager;
import sk.engine.graphics.Color;
import sk.engine.graphics.Window;
import sk.engine.util.ResourceLoader;

public class Game implements SKFramework {
	
	public Game() {
		Core core = new Core(this);
		
		Window window = new Window(800, 600, "SK's Platformer").setBackgroundColor(Color.CYAN).showFPS(true);
		
		GameStateManager gsm = new GameStateManager();
		
		core.addComponents(window, gsm);
		
		gsm.registerState(new StateMenuMain(gsm));
		gsm.registerState(new StateMenuSecond(gsm));
		
		System.exit(core.start());
	}
	
	public void enableDebugFeatures() {
		Debug.enable(true);
		Debug.setPointSize(10);
		Debug.enableLineStipple(false);
		Debug.setLineStipple(3, (short)100);
		Debug.setLineWidth(5);
	}
	
	public void registerAudio() {
		AudioManager.registerAudio("Repeatedly", "audio/music/repeatedly.wav");
		AudioManager.registerAudio("Mornings", "audio/music/The_Moments_of_Our_Mornings.wav");
		AudioManager.registerAudio("Jump", "audio/sfx/Jump.wav");
	}
	
	public static final void main(String[] args) {
		new Game();
	}
}