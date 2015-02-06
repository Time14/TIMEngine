package sk.engine.audio;

import static org.lwjgl.openal.AL10.*;

import java.nio.ByteBuffer;

import sk.engine.util.BufferUtil;
import sk.engine.util.ResourceLoader;

public class AudioClip {
	
	public static final int WAV_HEADER_SIZE = 44;
	public static final int WAV_SAMPLE_RATE_OFFSET = 24;
	public static final int WAV_SAMPLE_RATE_SIZE = 4;
	public static final int WAV_NUM_CHANNELS_OFFSET = 22;
	public static final int WAV_NUM_CHANNELS_SIZE = 2;
	public static final int WAV_BITS_PER_SAMPLE_OFFSET = 34;
	public static final int WAV_BITS_PER_SAMPLE_SIZE = 2;
	
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
	
	public AudioClip() {}
	
	public AudioClip createAudio(String name, String path) {
		this.name = name;
		
		id = alGenBuffers();
		
		try {
			ByteBuffer buffer = ResourceLoader.loadData(path);
			
			//The sample rate/frequency can be found at byte 24
			freq = BufferUtil.getIntFromByteBuffer(buffer, WAV_SAMPLE_RATE_OFFSET, WAV_SAMPLE_RATE_SIZE);
			
			numChannels = BufferUtil.getShortFromByteBuffer(buffer, WAV_NUM_CHANNELS_OFFSET, WAV_NUM_CHANNELS_SIZE);
			
			bitRate = BufferUtil.getShortFromByteBuffer(buffer, WAV_BITS_PER_SAMPLE_OFFSET, WAV_BITS_PER_SAMPLE_SIZE);
			
			if(bitRate == 8)
				format = numChannels == 1 ? FORMAT_MONO8 : FORMAT_STEREO8;
			else if(bitRate == 16)
				format = numChannels == 1 ? FORMAT_MONO16 : FORMAT_STEREO16;
			
			buffer.position(WAV_HEADER_SIZE);
			alBufferData(id, format, buffer.slice(), freq);
			
			if(alGetError() != AL_NO_ERROR) {
				System.err.println("An error occured when initializing audio data!");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public int getID() {
		return id;
	}
}