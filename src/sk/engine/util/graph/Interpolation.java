package sk.engine.util.graph;

public class Interpolation {
	private double time;
	private double duration;
	private double difference;
	
	/**
	 * @param time The total amount of time the interpolation takes
	 */
	public Interpolation(double duration) {
		this.time = 0;
		this.duration = duration;
		difference = 1f / time;
	}
	
	public void update(double tick) {
		time += tick;
	}
	
	public void setDuration(double time) {
		this.difference = 1f / time;
	}
	
	public double getLerp() {
		if(time < duration)
			return time * difference;
		else
			return 1;
	}
	
	public double getSlerp() {
		if(time < duration)
			return Math.sin(-time * difference + Math.PI) / Math.sin(-duration * difference + Math.PI);
		else
			return 1;
	}
	
	public void reset() {
		time = 0;
	}
}