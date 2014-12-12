package sk.engine.audio;

import static org.lwjgl.openal.AL10.*;

import java.nio.ByteBuffer;

import sk.engine.util.BufferUtil;
import sk.engine.util.ResourceLoader;

public class AudioClip {
	
	public static final int WAV_HEADER_SIZE = 44;
	
	public static final int FORMAT_STEREO8 = AL_FORMAT_STEREO8;
	public static final int FORMAT_STEREO16 = AL_FORMAT_STEREO16;
	public static final int FORMAT_MONO8 = AL_FORMAT_MONO8;
	public static final int FORMAT_MONO16 = AL_FORMAT_MONO16;
	
	private String name;
	
	private int id;
	private int numChannels;
	private int bitRate;
	private int freq;
	
	private int format;
	
	public AudioClip() {
		
	}
	
	public void createAudio(String name, String path) {
		id = alGenBuffers();
		//this.format = format;
		
		try {
			ByteBuffer buffer = ResourceLoader.loadData(path);
			
			//The sample rate/frequency can be found at byte 24
			freq = (((int)buffer.get(24)) & 0xFF) | (((int)buffer.get(25)) & 0xFF) << 8 | (((int)buffer.get(26)) & 0xFF) << 16 | (((int)buffer.get(27)) & 0xFF) << 24;
			
			numChannels = (((int)buffer.get(22)) & 0xFF) | (((int)buffer.get(23)) & 0xFF) << 8;
			
			bitRate = (((int)buffer.get(34)) & 0xFF) | (((int)buffer.get(35)) & 0xFF) << 8;
			
			if(bitRate == 8)
				format = numChannels == 1 ? FORMAT_MONO8 : FORMAT_STEREO8;
			else if(bitRate == 16)
				format = numChannels == 1 ? FORMAT_MONO16 : FORMAT_STEREO16;
			
			byte[] data = new byte[buffer.capacity() - WAV_HEADER_SIZE];
			
			buffer.get(data, WAV_HEADER_SIZE, data.length);
			
			alBufferData(id, format, BufferUtil.toByteBuffer(data), freq);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}