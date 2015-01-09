package sk.engine.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import sk.engine.graphics.entity.mesh.vertex.Vertex;

public final class BufferUtil {
	
	public static final int getIntFromByteBuffer(ByteBuffer buffer, int offset, int size) {
		int data = 0x00000000;
		
		if(size > Integer.SIZE / 8) {
			throw new IndexOutOfBoundsException("Integer size is limited to " + Integer.SIZE / 8 + " bytes");
		}
		
		buffer.rewind();
		
		for(int i = 0; i < size; i++) {
			data = data | (((int)buffer.get(offset + i)) & 0xFF) << i * 8;
		}
		
		return data;
	}
	
	public static final FloatBuffer toFloatBuffer(Vertex[] vertices) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * vertices[0].getLength());
		
		for(Vertex v : vertices) {
			buffer.put(v.getData());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static final FloatBuffer toFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static final IntBuffer toIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static final ByteBuffer toByteBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static final ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}
	
	public static final FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static final IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}
}