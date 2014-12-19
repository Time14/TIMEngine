package sk.engine.world;

import java.util.ArrayList;
import java.util.HashMap;

public class World {
	
	private HashMap<String, Group> groups;
	
	public World() {
		groups = new HashMap<>();
	}
	
	public void update() {
		for(Group g : groups.values()) {
			g.update();
		}
	}
	
	public void draw() {
		for(Group g : groups.values()) {
			g.draw();
		}
	}
	
	public void addGroup(String key) {
		groups.put(key, new Group(key));
	}
	
	public void addEntity(String group, Entity e) {
		groups.get(group).addEntity(e);
	}
	
	public ArrayList<Entity> getEntities(String group) {
		return groups.get(group).getEntities();
	}
	
	public Entity getEntity(String group, int index) {
		return groups.get(group).getEntities().get(index);
	}
}