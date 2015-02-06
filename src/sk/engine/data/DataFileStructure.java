package sk.engine.data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import sk.engine.data.STSS.DataFileSTSS;

public abstract class DataFileStructure {
	
	protected final String fileName;
	protected final File file;
	protected final RandomAccessFile raf;
	
	public DataFileStructure(String fileName) throws IOException {
		this.fileName = fileName;
		
		file = new File(fileName);
		raf = new RandomAccessFile(file, "rw");
	}
	
	public DataFileStructure createFile() throws IOException {
		if(!file.exists())
			file.createNewFile();
		else
			System.out.println(fileName + " already exists!");
		
		return this;
	}
	
	public abstract DataFileStructure writeToFile(ByteBuffer buffer) throws IOException;
	public abstract void readFromFile() throws IOException;
	public abstract String getName();
	public abstract String getExtension();
}