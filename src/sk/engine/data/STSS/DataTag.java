package sk.engine.data.STSS;

import java.nio.ByteBuffer;

import sk.engine.util.BufferUtil;

public class DataTag {
	
	public static final int TYPE_INTEGER = 0;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_FLOAT = 2;
	public static final int TYPE_DOUBLE = 3;
	
	protected final int dataType;
	protected final int size;
	protected final String tag;
	protected Object data;
	
	public DataTag(String tag, int data, int size) {
		dataType = TYPE_INTEGER;
		this.tag = tag;
		this.data = data;
		if(size > Integer.SIZE / 8) {
			size = Integer.SIZE / 8;
			throw new IndexOutOfBoundsException("Integer size is limited to " + Integer.SIZE / 8 + " bytes");
		}
		this.size = size;
	}
	
	public DataTag(String tag, String data) {
		dataType = TYPE_STRING;
		this.tag = tag;
		this.data = data;
		this.size = data.length();
	}
	
	public DataTag(String tag, float data, int size) {
		dataType = TYPE_FLOAT;
		this.tag = tag;
		this.data = data;
		if(size > Float.SIZE / 8) {
			size = Float.SIZE / 8;
			throw new IndexOutOfBoundsException("Float size is limited to " + Float.SIZE / 8 + " bytes");
		}
		this.size = size;
	}
	
	public DataTag(String tag, double data, int size) {
		dataType = TYPE_DOUBLE;
		this.tag = tag;
		this.data = data;
		if(size > Double.SIZE / 8) {
			size = Double.SIZE / 8;
			throw new IndexOutOfBoundsException("Double size is limited to " + Double.SIZE / 8 + " bytes");
		}
		this.size = size;
	}
	
	public ByteBuffer compile() {
		ByteBuffer buffer = BufferUtil.createByteBuffer(size);
		
		return buffer;
	}
}