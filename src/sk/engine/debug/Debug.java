package sk.engine.debug;

import sk.engine.graphics.Color;
import sk.engine.graphics.entity.mesh.Mesh;
import sk.engine.graphics.entity.mesh.MeshLine;
import sk.engine.graphics.entity.mesh.MeshPoint;
import sk.engine.graphics.entity.mesh.texture.Texture;
import sk.engine.graphics.shader.OrthographicalShaderProgram;
import sk.engine.vector.Matrix4f;
import sk.engine.vector.Vector2f;
import sk.engine.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public final class Debug {
	private static boolean enabled = false;
	
	private static float pointSize = 1;
	
	private static float lineWidth = 1;
	private static int lineStippleRepeatFactor = 1;
	private static short lineStipplePattern = 1;
	
	public static final void drawPoint(Vector2f point, Color color) {
		if(enabled) {
			OrthographicalShaderProgram.INSTACE.bind();
			OrthographicalShaderProgram.INSTACE.sendMatrix(Matrix4f.IDENTITY());
			Texture t = new Texture().loadTexture(new int[]{color.value}, 1, 1);
			t.bind(0);
			Mesh m = new MeshPoint(point.x, point.y, -1f);
			if(glGetInteger(GL_POINT_SIZE) != pointSize)
				glPointSize(pointSize);
			m.draw();
			t.destroy();
			m.destroy();
		}
	}
	
	public static final void drawLine(Vector2f point1, Vector2f point2, Color color) {
		if(enabled) {
			OrthographicalShaderProgram.INSTACE.bind();
			OrthographicalShaderProgram.INSTACE.sendMatrix(Matrix4f.IDENTITY());
			Texture t = new Texture().loadTexture(new int[]{color.value}, 1, 1);
			t.bind(0);
			Mesh m = new MeshLine(point1, point2, -1f);
			if(glGetInteger(GL_LINE_WIDTH) != lineWidth)
				glLineWidth(lineWidth);
			if(glGetInteger(GL_LINE_STIPPLE_PATTERN) != lineStipplePattern || glGetInteger(GL_LINE_STIPPLE_REPEAT) != lineStippleRepeatFactor)
				glLineStipple(lineStippleRepeatFactor, lineStipplePattern);
			m.draw();
			t.destroy();
			m.destroy();
		}
	}
	
	public static final void drawPolygon(Vector2f[] points) {
		if(enabled) {
			/*Draw a polygon*/
		}
	}
	
	public static final void setPointSize(float size) {
		pointSize = size;
	}
	
	public static final void enableLineStipple(boolean enabled) {
		if(enabled)
			glEnable(GL_LINE_STIPPLE);
		else
			glDisable(GL_LINE_STIPPLE);
	}
	
	public static final void setLineWidth(float width) {
		lineWidth = width;
	}
	
	public static final void setLineStipple(int factor, short pattern) {
		setLineStippleRepeatFactor(factor);
		setLineStipplePattern(pattern);
	}
	
	public static final void setLineStippleRepeatFactor(int factor) {
		lineStippleRepeatFactor = factor;
	}
	
	public static final void setLineStipplePattern(short pattern) {
		lineStipplePattern = pattern;
	}
	
	public static final void enable(boolean enabled) {
		Debug.enabled = enabled;
	}
}