package sk.game;

import sk.engine.core.Core;
import sk.engine.core.SKFramework;
import sk.engine.gamestate.GameStateManager;
import sk.engine.graphics.Color;
import sk.engine.graphics.Window;

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
	
	public static final void main(String[] args) {
		new Game();
	}
}