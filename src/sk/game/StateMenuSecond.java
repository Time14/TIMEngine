package sk.game;

import sk.engine.core.Time;
import sk.engine.gamestate.GameState;
import sk.engine.gamestate.GameStateManager;
import sk.engine.io.input.Keyboard;

public class StateMenuSecond extends GameState {
	
	public StateMenuSecond(GameStateManager gsm) {
		super(gsm, 1);
	}
	
	public void init() {}
	
	public void update() {
		
	}
	
	public void checkMouse(int b, boolean p) {}
	
	public void checkKeyboard(int k, boolean p) {
		if(!p)
			gsm.enterState(0);
	}
	
	public void draw() {}
	
	public void exit() {}
	
}