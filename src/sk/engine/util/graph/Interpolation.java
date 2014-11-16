package sk.engine.util.graph;

public class Interpolation {
	private double time;
	private double totalTime;
	private double difference;
	
	/**
	 * @param time The total amount of time the interpolation takes
	 */
	public Interpolation(double time) {
		this.time = 0;
		totalTime = time;
		difference = 1f / time;
	}
	
	public void update(double tick) {
		time += tick;
	}
	
	public void set(double time) {
		this.difference = 1f / time;
	}
	
	public double getLerp() {
		if(time < totalTime)
			return time * difference;
		else
			return 1;
	}
	
	public double getSlerp() {
		if(time < totalTime)
			return Math.sin(-time * difference + Math.PI) / Math.sin(-totalTime * difference + Math.PI);
		else
			return 1;
	}
	
	public void reset() {
		time = 0;
	}
}
