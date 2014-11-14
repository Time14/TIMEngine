package sk.engine.util.graph;

import sk.engine.core.Time;

public class Slerp {
	private float time;
	private float totalTime;
	private float difference;
	
	/**
	 * @param sigmDeltaTime The total amount of time the interpolation takes
	 * @param start The starting point value of the interpolation
	 * @param end The end point of the interpolation
	 */
	public Slerp(float time) {
		this.time = 0;
		this.totalTime = time;
		difference = 1 / time;
	}
	/**
	 * 
	 * @param tick
	 * @return returns the slerp value
	 */
	public void update(double tick) {
		time += tick;
	}
	
	public void set(float time) {
		this.difference = 1 / time;
	}
	
	public double getLerp() {
		if( time < totalTime)
			return time * difference;
		else
			return 1;
	}
	public double getSlerp() {
		if( time < totalTime)
			return Math.sin(-time * difference + Math.PI);
		else
			return 1;
	}
	
	public void reset() {
		time = 0;
	}
}
