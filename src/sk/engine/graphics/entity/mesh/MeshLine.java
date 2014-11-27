package sk.engine.graphics.entity.mesh;

import sk.engine.graphics.entity.mesh.vertex.Vertex2D;
import sk.engine.util.GLUtil;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;

public class MeshLine extends Mesh {
	
	public MeshLine(Vector2f pt1, Vector2f pt2, float depth) {
		addVertices(GLUtil.LINES, new Vertex2D[]{
				new Vertex2D(new Vector3f(pt1, depth),  new Vector2f(.5f, .5f)),
				new Vertex2D(new Vector3f(pt2, depth),  new Vector2f(.5f, .5f))
		});
	}
}