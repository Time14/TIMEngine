package sk.engine.graphics.entity.mesh.vertex;

public abstract class Vertex {
	
	public abstract int getLength();
	
	public abstract int getSize();
	
	/**
	 * 
	 * A method used to describe data information.
	 * 
	 * This integer array describes for the Mesh class how the glVertexAttribPointer() method will interpret the data for the VBO
	 * 
	 * Each integer describes how many floats belong to each attribute array
	 * 
	 * Example: {2, 4, 2} would assign the values from the VBO to the attribute arrays accordingly:
	 * 2 values to the attribute array with the id 0
	 * 4 values to the attribute array with the id 1
	 * 2 values to the attribute array with the id 2
	 * 
	 * @return Returns the information itself
	 */
	public abstract int[] getComponents();
	
	public abstract float[] getData();
	
}