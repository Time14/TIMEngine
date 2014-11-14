package sk.engine.gamestate;

public abstract class GameState {
	
	public final int ID;
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm, int id) {
		ID = id;
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void checkMouse(int button, boolean pressed);
	public abstract void checkKeyboard(int key, boolean pressed);
	public abstract void update();
	public abstract void draw();
	public abstract void exit();
	
}