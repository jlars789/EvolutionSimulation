import java.io.Serializable;

public class Stats implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int size;
	private int mitosisTime;
	
	private float speed;
	private float range;
	
	private double metabolism;

	public Stats(int size, int time, float speed, float range, double metabolism) {
		this.size = size;
		this.mitosisTime = time;
		this.speed = speed;
		this.range = range;
		this.metabolism = metabolism;
	}
	
	public Stats() {
		new Stats(20, 1300, 1.15f, 92.5f, 0.15);
	}
	
	public float getRange() {
		return this.range;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getMitosisTime() {
		return this.mitosisTime;
	}
	
	public double getMetabolism() {
		return this.metabolism;
	}
	
	

}
