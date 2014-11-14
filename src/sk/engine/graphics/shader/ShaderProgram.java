package sk.engine.graphics.shader;

import sk.engine.core.Core;
import sk.engine.util.BufferUtil;
import sk.engine.util.ResourceLoader;
import sk.engine.vector.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
	
	public static final int VERTEX_SHADER = GL_VERTEX_SHADER;
	public static final int FRAGMENT_SHADER = GL_FRAGMENT_SHADER;
	
	private float aspectRatio;
	
	private int id;
	
	public void createProgram(String vshPath, String fshPath) {
		int vsh = addShader(ResourceLoader.loadSource(vshPath), VERTEX_SHADER);
		int fsh = addShader(ResourceLoader.loadSource(fshPath), FRAGMENT_SHADER);
		
		id = glCreateProgram();
		
		glAttachShader(id, vsh);
		glAttachShader(id, fsh);
		
		glLinkProgram(id);
		
		if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println("Failed to link shader program!");
			System.err.println(glGetProgramInfoLog(id, glGetProgrami(id, GL_INFO_LOG_LENGTH)));
			Core.crash(1);
		}
	}
	
	public int addShader(String src, int type) {
		int id = glCreateShader(type);
		
		glShaderSource(id, src);
		
		glCompileShader(id);
		
		if(glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile " + (type == VERTEX_SHADER ? "vertex" : "fragment") + " shader!");
			System.err.println(glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH)));
			Core.crash(1);
		}
		
		return id;
	}
	
	public void bind() {
		glUseProgram(id);
	}
	
	public void sendMatrix(Matrix4f matrix) {
		int active = glGetInteger(GL_CURRENT_PROGRAM);
		if(active != id)
			glUseProgram(id);
		
		int location = glGetUniformLocation(id, "matrix");
		glUniformMatrix4(location, false, BufferUtil.toFloatBuffer(matrix.toFloatArray()));
		
		glUseProgram(active);
	}
	
	public void sendUniform(String uniform, float value) {
		int active = glGetInteger(GL_CURRENT_PROGRAM);
		if(active != id)
			glUseProgram(id);
		
		int location = glGetUniformLocation(id, uniform);
		
		glUniform1f(location, value);
		
		glUseProgram(active);
	}
	
	public void changeAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	
	public int getID() {
		return id;
	}
	
}