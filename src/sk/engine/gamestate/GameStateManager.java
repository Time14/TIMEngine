package sk.engine.gamestate;

import java.util.HashMap;

import sk.engine.core.Core;
import sk.engine.graphics.Window;
import sk.engine.io.input.Keyboard;
import sk.engine.io.input.Mouse;

public class GameStateManager {
	
	private GameState currentState;
	
	private HashMap<Integer, GameState> gameStates;
	
	private Core core;
	
	public GameStateManager() {
		
		gameStates = new HashMap<>();
	}
	
	public void enterState(int id) {
		enterState(gameStates.get(id));
	}
	
	public void enterState(GameState gs) {
		if(currentState != null)
			currentState.exit();
		
		currentState = gs;
		currentState.init();
	}
	
	public void update() {
		checkMouse();
		checkKeyboard();
		currentState.update();
		currentState.draw();
	}
	
	private void checkMouse() {
		while(Mouse.next()) {
			currentState.checkMouse(Mouse.getEventButton(), Mouse.getEventButtonState());
		}
	}
	
	private void checkKeyboard() {
		while(Keyboard.next()) {
			currentState.checkKeyboard(Keyboard.getEventKey(), Keyboard.getEventKeyState());
		}
	}
	
	public void initCore(Core core) {
		this.core = core;
		enterState(0);
	}
	
	public void registerState(GameState gs) {
		gameStates.put(gs.ID, gs);
	}
	
	public Window getWindow() {
		return core.getWindow();
	}
	
	public Core getCore() {
		return core;
	}
}