package sk.engine.graphics.entity.mesh.texture;

public class DynamicTexture {
	
	private SpriteSheet sprites;
	
	private int currentSpriteX;
	private int currentSpriteY;
	
	public DynamicTexture() {}
	
	public DynamicTexture(Texture texture) {
		this(new SpriteSheet(1, 1, texture.getWidth(), texture.getHeight()).setTexture(texture, 0, 0));
	}
	
	public DynamicTexture(SpriteSheet sprites) {
		this.sprites = sprites;
		currentSpriteX = 0;
		currentSpriteY = 0;
	}
	
	public DynamicTexture sendSpriteSheet(SpriteSheet sprites) {
		this.sprites = sprites;
		
		return this;
	}
	
	public void swap(int x, int y) {
		swapX(x);
		swapY(y);
	}
	
	public void swapX(int x) {
		currentSpriteX = x;
	}
	
	public void swapY(int y) {
		currentSpriteY = y;
	}
	
	public void bind() {
		sprites.bind(currentSpriteX, currentSpriteY, 0);
	}
	
	public Texture getTexture() {
		return sprites.getTexture(currentSpriteX, currentSpriteY);
	}
	
	public SpriteSheet getSpriteSheet() {
		return sprites;
	}
	
	public int getSpriteX() {
		return currentSpriteX;
	}
	
	public int getSpriteY() {
		return currentSpriteY;
	}
}