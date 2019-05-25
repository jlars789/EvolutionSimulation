import java.awt.Graphics;
import java.util.Random;

public class Hunger {
	
	private static final double THRESHOLD = 40.0;
	
	private double value;
	private double coefficient;
	private boolean expired;
	private int modifier;
	private double intializeTime;

	public Hunger(double coefficient) {
		this.value = randomDouble(-3.3, 3.3);
		this.coefficient = coefficient;
		this.expired = false;
		this.intializeTime = Window.elapsedTimeInSeconds();
	}
	
	public void draw(Graphics g) {
	}
	
	public void update() {
		
		double x = timeExist();
		
		if(this.value < THRESHOLD) {
			this.value = (this.coefficient * (x*x) + (.3 * x) - this.modifier);
		} else {
			this.expired = true;
		}
		
	}
	
	public void satiate(double value) {
		this.modifier += value;
	}
	
	public double getCoefficient() {
		return this.coefficient;
	}
	
	public boolean isExpired() {
		return this.expired;
	}
	
	public Hunger copy() {
		return new Hunger(this.coefficient);
	}
	
	public double timeExist() {
		return Window.elapsedTimeInSeconds() - this.intializeTime;
	}
	
	private double randomDouble(double min, double max) {
		Random r = new Random();
		return (min + (max - min) * r.nextDouble());
	}

}
