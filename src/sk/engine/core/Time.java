package sk.engine.core;

public final class Time {
	
	private static final long startTimeNano = System.nanoTime();
	private static final long startTimeMillis = System.currentTimeMillis();
	
	private static long previousTime = getNanoTime();
	private static double delta = 0;
	
	private static double deltaStack = 0;
	private static int fpsCounter = 0;
	private static int fps = 0;
	
	public static final long getNanoTime() {
		return System.nanoTime() - startTimeNano;
	}
	
	public static final long getMilliTime() {
		return System.currentTimeMillis() - startTimeMillis;
	}
	
	public static final void update() {
		long currentTime = getNanoTime();
		delta = currentTime - previousTime;
		delta /= 1000000000;
		previousTime = currentTime;
		
		deltaStack += getDelta();
		fpsCounter++;
		if(deltaStack >= 1) {
			fps = fpsCounter;
			deltaStack = 0;
			fpsCounter = 0;
		}
	}
	
	public static final double getDelta() {
		return delta;
	}
	
	public static final int getFPS() {
		return fps;
	}
	
}