import java.io.Serializable;
import java.util.Random;

/**
 * Class that handles the energy and hunger of a creature
 */

/*
public void update(){
	x = timeElapsed
	function = coefficient * (x * x) + 0.3 * x - modifier
	if(function > threshold){
		expired = true
	}
}

 */

public class Hunger implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5803710416336570585L;

	private static final double THRESHOLD = 35.0;
	
	private double value;
	private double coefficient;
	private boolean expired;
	private double modifier;
	private double intializeTime;

	/**
	 * Creates a Hunger object with a starting value of 0, and a random modifier of (+-3.3)
	 * @param coefficient The leading coefficient in the quadratic that is representative of the 
	 * Creature's lifespan (terminates at 40)
	 */
	
	public Hunger(double coefficient) {
		this.modifier = randomDouble(-3.3, 3.3);
		
		this.coefficient = coefficient;
		if(coefficient < 0) {
			this.coefficient = 0;
		}
		this.expired = false;
		this.intializeTime = Window.elapsedTimeInSeconds();
	}
	
	/**
	 * Updates the quadratic (x^2 + .3x - modifier) with an x value representative of time the creature has been alive 
	 * in seconds elapsed
	 */
	
	public void update() {
		
		double x = timeExist();
		
		if(this.value < THRESHOLD) {
			this.value = (this.coefficient * (x*x) + (.3 * x) - this.modifier);
		} else {
			this.expired = true;
		}
		
	}
	
	/**
	 * Extends the duration of the hunger value by increasing the modifier within the quadratic
	 * @param value The value in which the modifier should be incremented
	 */
	
	public void satiate(double value) {
		this.modifier += value;
	}
	
	public double getCoefficient() {
		return this.coefficient;
	}
	
	public double getModifier() {
		return this.modifier;
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
	
	public double getValue() {
		return this.value;
	}
	
	private double randomDouble(double min, double max) {
		Random r = new Random();
		return (min + (max - min) * r.nextDouble());
	}

}
