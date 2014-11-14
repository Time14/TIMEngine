package sk.engine.graphics.shader;

import sk.engine.graphics.Window;

public class OrthographicalShaderProgram extends ShaderProgram {
	
	public OrthographicalShaderProgram() {
		createProgram("gfx/shader/ortho.vsh", "gfx/shader/ortho.fsh");
		sendUniform("width", Window.getWidth());
		sendUniform("height", Window.getHeight());
		sendUniform("texture", 0);
	}
	
	public static final OrthographicalShaderProgram INSTACE = new OrthographicalShaderProgram();
}