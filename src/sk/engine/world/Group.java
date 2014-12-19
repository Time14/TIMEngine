package sk.engine.world;

import java.util.ArrayList;

public class Group {
	
	private ArrayList<Entity> entities;
	
	private String name;
	
	private boolean alive;
	private boolean visible;
	
	public Group(String name) {
		this.name = name;
		alive = true;
		visible = true;
	}
	
	public void update() {
		if(alive) {
			ArrayList<Entity> elements = (ArrayList<Entity>) entities.clone();
			for(Entity e : elements) {
				if(!e.isAlive())
					entities.remove(e);
				e.update();
			}
			elements.clear();
		}
	}
	
	public void draw() {
		if(visible) {
			ArrayList<Entity> elements = (ArrayList<Entity>) entities.clone();
			for(Entity e : elements)
				e.draw();
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public Group setAlive(boolean alive) {
		this.alive = alive;
		
		return this;
	}
	
	public Group setVisible(boolean visible) {
		this.visible = visible;
		
		return this;
	}
	
}