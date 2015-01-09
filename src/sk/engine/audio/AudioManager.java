package sk.engine.audio;

import java.lang.Runnable;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.openal.AL;

import sk.engine.gamestate.GameStateManager;
import sk.engine.util.BufferUtil;
import static org.lwjgl.openal.AL10.*;

public class AudioManager implements Runnable {
	
	private static boolean enabled = true;
	
	public static final float LOOPS_PER_SECOND = 10;
	
	private static final HashMap<String, AudioClip> audioClips = new HashMap<>();
	
	private ArrayList<String> audioQueue;
	
	private GameStateManager gsm;
	
	private boolean running;
	
	private IntBuffer loopSources;
	
	private IntBuffer sources;
	
	public AudioManager(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	private void init() {
		try {
			audioQueue = new ArrayList<>();
			AL.create();
			gsm.getCore().getGame().registerAudio();
			
			setupAL();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setupAL() {
		loopSources = BufferUtil.createIntBuffer(10);
		sources = BufferUtil.createIntBuffer(246);
		alGenSources(loopSources);
		alGenSources(sources);
		
		
		alListener(AL_POSITION, BufferUtil.toFloatBuffer(new float[]{0, 0, 0}));
		alListener(AL_ORIENTATION, BufferUtil.toFloatBuffer(new float[]{0, 0, 0}));
	}
	
	private void update() {
		if(audioQueue.size() > 0) {
			while(true) {
				String[] data = audioQueue.remove(0).split("\\|");
				switch(data[0]) {
				case "qa":
					playSound(data[1], Float.parseFloat(data[2]), Float.parseFloat(data[3]));
					break;
				case "qla":
					loopSound(data[1], Integer.parseInt(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]), Integer.parseInt(data[5]));
					break;
				case "stop":
					stopLoopSound(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
					break;
				case "pause":
					pauseLoopSound(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
					break;
				case "play":
					playLoopSound(Integer.parseInt(data[1]), Float.parseFloat(data[2]), Integer.parseInt(data[3]));
					break;
				}
				
				if(audioQueue.size() == 0)
					break;
			}
			audioQueue.clear();
		}
		try {
			Thread.sleep((long)(1000 / LOOPS_PER_SECOND));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void playSound(String key, float pitch, float gain) {
		int index = -1;
		
		for(int i = 0; i < sources.capacity(); i++) {
			if(alGetSourcei(sources.get(i), AL_SOURCE_STATE) != AL_PLAYING) {
				index = i;
				break;
			}
		}
		
		if(index == -1)
			return;
		
		alSourcei(sources.get(index), AL_BUFFER, audioClips.get(key).getID());
		alSourcef(sources.get(index), AL_PITCH, pitch);
		alSourcef(sources.get(index), AL_GAIN, gain);
		alSource(sources.get(index), AL_POSITION, BufferUtil.toFloatBuffer(new float[]{0, 0, 0}));
		alSource(sources.get(index), AL_VELOCITY, BufferUtil.toFloatBuffer(new float[]{0, 0, 0}));
		alSourcei(sources.get(index), AL_LOOPING, AL_FALSE);
		alSourcePlay(sources.get(index));
	}
	
	private void fadeLoopSound(int source, float target, int millis) {
		if(millis == 0)
			alSourcef(loopSources.get(source), AL_GAIN, target);
		
		float gain = alGetSourcef(loopSources.get(source), AL_GAIN);
		
		float delta = (gain - target) / (millis / (1000 / LOOPS_PER_SECOND)) ;
		
		if(delta == 0)
			return;
		
		for(int i = 0; i < (delta > 0 ? 1 / delta : -1 / delta); i++) {
			gain -= delta;
			if(gain > 0 && gain < delta)
				gain = 0;
			System.out.println(gain);
			alSourcef(loopSources.get(source), AL_GAIN, gain);
			try {
				Thread.sleep((long)(1000 / LOOPS_PER_SECOND));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void stopLoopSound(int source, int millis) {
		fadeLoopSound(source, 0, millis);
		alSourceStop(loopSources.get(source));
	}
	
	private void pauseLoopSound(int source, int millis) {
		
		fadeLoopSound(source, 0, millis);
		alSourcePause(loopSources.get(source));
	}
	
	private void playLoopSound(int source, float target, int millis) {
		alSourcePlay(loopSources.get(source));
		fadeLoopSound(source, target, millis);
	}
	
	private void loopSound(String key, int source, float pitch, float gain, int millis) {
		if(source >= loopSources.capacity()) {
			throw new IndexOutOfBoundsException("Loop source must be between 0-" + (loopSources.capacity()-1));
		}
		
		if(alGetSourcei(loopSources.get(source), AL_SOURCE_STATE) == AL_PLAYING)
			alSourceStop(loopSources.get(source));
		
		alSourcei(loopSources.get(source), AL_BUFFER, audioClips.get(key).getID());
		alSourcef(loopSources.get(source), AL_PITCH, pitch);
		alSourcef(loopSources.get(source), AL_GAIN, 0);
		alSource(loopSources.get(source), AL_POSITION, BufferUtil.toFloatBuffer(new float[]{0, 0, 0}));
		alSource(loopSources.get(source), AL_VELOCITY, BufferUtil.toFloatBuffer(new float[]{0, 0, 0}));
		alSourcei(loopSources.get(source), AL_LOOPING, AL_TRUE);
		playLoopSound(source, gain, millis);
	}
	
	public synchronized void playLoopAudio(int source, float target, int millis) {
		StringBuilder sb = new StringBuilder();
		sb.append("play|").append(source).append("|").append(target).append("|").append(millis);
		audioQueue.add(sb.toString());
	}
	
	public synchronized void pauseLoopAudio(int source, int millis) {
		StringBuilder sb = new StringBuilder();
		sb.append("pause|").append(source).append("|").append(millis);
		audioQueue.add(sb.toString());
	}
	
	public synchronized void stopLoopAudio(int source, int millis) {
		StringBuilder sb = new StringBuilder();
		sb.append("stop|").append(source).append("|").append(millis);
		audioQueue.add(sb.toString());
	}
	
	public synchronized void queueLoopAudio(String key, int source, float pitch, float gain, int millis) {
		StringBuilder sb = new StringBuilder();
		sb.append("qla|").append(key).append("|").append(source).append("|").append(pitch).append("|").append(gain).append("|").append(millis);
		audioQueue.add(sb.toString());
	}
	
	public synchronized void queueAudio(String key, float pitch, float gain) {
		StringBuilder sb = new StringBuilder();
		sb.append("qa|").append(key).append("|").append(pitch).append("|").append(gain);
		audioQueue.add(sb.toString());
	}
	
	public void run() {
		init();
		
		running = true;
		
		while(running && enabled) {
			update();
		}
		
		cleanUp();
	}
	
	public synchronized void setRunning(boolean running) {
		this.running = running;
	}
	
	private void cleanUp() {
		AL.destroy();
		System.out.println("AudioManager shutdown!");
	}
	
	public static final void registerAudio(String key, String src) {
		audioClips.put(key, new AudioClip().createAudio(key, src));
	}
	
	public static final boolean isEnabled() {
		return enabled;
	}
	
	public static final void setEnabled(boolean enabled) {
		AudioManager.enabled = enabled;
	}
}