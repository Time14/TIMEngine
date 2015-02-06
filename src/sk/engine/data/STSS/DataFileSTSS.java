package sk.engine.data.STSS;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import sk.engine.data.DataFileStructure;
import sk.engine.util.BufferUtil;

public class DataFileSTSS extends DataFileStructure {
	
	public static final String FILE_FORMAT_NAME = "Sequencial Tag Saving System";
	public static final String FILE_EXTENSION = "STSS";
	
	public DataFileSTSS(String fileName) throws IOException {
		super(fileName);
	}
	
	public DataFileStructure writeToFile(ByteBuffer buffer) throws IOException {
		FileChannel fch = raf.getChannel();
		fch.write(buffer);
		
		return this;
	}
	
	public void readFromFile() throws IOException {
		FileChannel fch = raf.getChannel();
		ByteBuffer buffer = BufferUtil.createByteBuffer((int)fch.size());
		fch.read(buffer);
		System.out.println(Integer.toHexString(BufferUtil.getIntFromByteBuffer(buffer, 0, 2)));
	}
	
	public String getName() {
		return FILE_FORMAT_NAME;
	}
	
	public String getExtension() {
		return "." + FILE_EXTENSION;
	}
}