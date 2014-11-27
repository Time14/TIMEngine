package sk.engine.graphics.entity.mesh;

import sk.engine.graphics.Color;
import sk.engine.graphics.entity.mesh.vertex.Vertex2D;
import sk.engine.util.GLUtil;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public class MeshPoint extends Mesh {
	
	public MeshPoint(float x, float y, float z) {
		addVertices(GLUtil.POINTS, new Vertex2D[]{new Vertex2D(new Vector3f(x, y, z), new Vector2f(0, 0))});
	}
}