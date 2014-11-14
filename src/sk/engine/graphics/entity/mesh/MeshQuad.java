package sk.engine.graphics.entity.mesh;

import sk.engine.graphics.entity.mesh.vertex.Vertex2D;
import sk.engine.util.GLUtil;
import sk.engine.vector.Vector2f;

public class MeshQuad extends Mesh {
	
	/**
	 * 
	 * The constructor of the quad collecting required data.
	 * 
	 * @param relative True if the origin is relative to the quad or false if the origin is relative to the transform of this mesh
	 * @param centerX The X coordinate of the quad's origin
	 * @param centerY The Y coordinate of the quad's origin
	 * @param width The width of this quad
	 * @param height The height of this quad
	 */
	public MeshQuad(boolean relative, float centerX, float centerY, float width, float height) {
		if(relative) {
			centerX *= -1;
			centerY *= -1;
		}
		addVertices(GLUtil.QUADS, new Vertex2D[]{
				new Vertex2D(new Vector2f(centerX-width/2, centerY-height/2), new Vector2f(0, 0)),
				new Vertex2D(new Vector2f(centerX+width/2, centerY-height/2), new Vector2f(1, 0)),
				new Vertex2D(new Vector2f(centerX+width/2, centerY+height/2), new Vector2f(1, 1)),
				new Vertex2D(new Vector2f(centerX-width/2, centerY+height/2), new Vector2f(0, 1))
		});
	}
	
}