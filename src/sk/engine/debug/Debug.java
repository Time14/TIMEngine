package sk.engine.debug;

import sk.engine.graphics.Color;
import sk.engine.graphics.entity.mesh.Mesh;
import sk.engine.graphics.entity.mesh.MeshPoint;
import sk.engine.graphics.entity.mesh.texture.Texture;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public class Debug {
	boolean debugMode = true;
	
	public static void drawPoint(Vector2f point, Color color) {
		Texture t = new Texture().loadTexture(new int[]{color.value}, 1, 1);
		t.bind(0);
		Mesh m = new MeshPoint(point.x, point.y, .5f);
		m.draw();
		t.destroy();
		m.destroy();
	}
	public static void drawLine(Vector2f point1, Vector2f point2) {
		/*Draw a line*/
	}
	public static void drawPolygon(Vector2f[] points) {
		/*Draw a polygon*/
	}
}