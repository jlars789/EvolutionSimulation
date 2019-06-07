import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class that is the parent for all Creature classes
 */

/*
public Creature(){
	randomize variables within set range
}

public void update(){
    rectangle.setLocation(location)
    hunger.update()
}

public void determine(){
	compare(food1, food2)
}
 */

public abstract class Creature implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private float xCoor;
	private float yCoor;
	
	private Stats stats;
	
	//private int size;
	
	//private float range;
	
	//private float speed;
	private int direction;
	
	private Color color;
	
	private int ticks = 0;
	private boolean alive;
	//private int mitosisTime;
	
	private Rectangle rect;
	private ArrayList<Food> food;
	private Food mainObj;
	private Hunger hunger;

	/**
	 * Creates a creature. 
	 * x and y coordinates are randomized in a range from 100-400
	 * Speed is randomized from 0.3 to 2.0
	 * Food detection range is randomized from 35 to 150
	 * Size is randomized from 10 to 30
	 * The hunger coefficient is randomized from 0.05 to 0.1
	 * Time required for reproduction is randomized from 900 to 1200
	 * @param color The color of the creature
	 */
	
	public Creature(Color color) {
		this.xCoor = randomFloat(100, 400);
		this.yCoor = randomFloat(100, 400);
		
		this.stats = new Stats(randomInt(10, 30), randomInt(1100, 1500), randomFloat(0.3f, 2.0f), randomFloat(35, 150), randomDouble(0.1, 0.2));
		
		this.hunger = new Hunger(stats.getMetabolism());
		this.rect = new Rectangle((int)xCoor, (int)yCoor, stats.getSize(), stats.getSize());
		
		this.color = color;
		
		this.alive = true;
		
		this.food = new ArrayList<Food>();
	}
	
	/**
	 * A creature with randomized stats based on the stats inputed
	 * @param color Color of the creature
	 * @param speed Root of randomization of speed (+-0.2)
	 * @param range Root of the randomization of range (+-7.5)
	 * @param size Root of the randomization of the size (+- 1)
	 * @param hunger Root of the randomization of the hunger coefficient (+- 0.1)
	 * @param mitosisTime Root of the randomization for reproduction time (+- 20)
	 * @param x Location on the x-axis
	 * @param y Location on the y-axis
	 */
	
	public Creature(Color color, float speed, float range, int size, Hunger hunger, int mitosisTime, float x, float y, float variance) {
		this.xCoor = x;
		this.yCoor = y;
		
		this.color = color;
		double co = hunger.copy().getCoefficient();
		
		this.stats = new Stats(randomInt((int)(size - (1 * variance)), (int)(size + (1 * variance))), randomInt((int)(mitosisTime - (20 * variance)), (int)(mitosisTime + (20 * variance))),
				randomFloat((speed - (0.2f * variance)), (speed + (0.2f * variance))), randomFloat((range - (7.5f * variance)), (range + (7.5f * variance))), 
				randomDouble((co - (0.01 * variance)), (co + (0.01 * variance))));
		
		this.rect = new Rectangle((int)xCoor, (int)yCoor, stats.getSize(), stats.getSize());
		this.hunger = new Hunger(stats.getMetabolism());
		this.alive = true;
		this.food = new ArrayList<Food>();
	}
	
	/**
	 * Updates the location, hunger, and time alive of the creature
	 */
	
	public void update() {
		this.ticks++;
		this.rect.setLocation((int)xCoor, (int)yCoor);
		
		this.hunger.update();
		if(this.hunger.isExpired()) {
			this.alive = false;
		}
		this.determine();
		
		for(int i = 0; i < Window.creatureList.size(); i++) {
			if(!Window.creatureList.get(i).equals(this)) {
				Creature c = Window.creatureList.get(i);
				if(this.rect.intersects(c.rect)) {
					if(this.stats.getSize() >= c.getStats().getSize()) {
						c.collide(c.getCollide(c.rect, this.rect));
					} 
				}
			}
		}
	}
	
	
	
	/**
	 * Moves the creature depending on proximity to Food objects
	 */
	
	public void move() {
		if(this.mainObj != null) {
			this.hunt();
		} else {
			this.search();
		}
		
		this.update();
	}
	
	/**
	 * Determines if food is within range
	 * If multiple Food Objects are within range, 
	 * the closest one will be selected
	 */
	
	private void determine() {
		Food closest = null;
		if(food.size() > 0) {
			closest = food.get(0);
			for(int i = 0; i < food.size(); i++) {
				if(compare(food.get(i), closest)) {
					closest = food.get(i);
				}
				if(food.get(i).isEaten()) {
					food.remove(i);
				}
			}
		}
		
		this.mainObj = closest;
	}
	
	/**
	 * Returns true if the first argument is closer than the second
	 * @param food1 First Food Object
	 * @param food2 Second Food object
	 * @return Determines if food1 is closer than food2
	 */
	
	private boolean compare(Food food1, Food food2) {
		boolean closer = false;
		if(Math.abs(food1.getCoordinates()[0] - this.xCoor) < Math.abs(food2.getCoordinates()[0] - this.xCoor)) {
			if(Math.abs(food1.getCoordinates()[1] - this.yCoor) < Math.abs(food2.getCoordinates()[1] - this.yCoor)) {
				closer = true;
			}
		}
		return closer;
	}
	
	/**
	 * Causes the creature to move its location to the Food object in its vicinity
	 */
	
	private void hunt() {
		float x = this.centerX() - mainObj.getCoordinates()[0];
		float y = this.centerY() - mainObj.getCoordinates()[1];
		double t = 0;
		
		if(x != 0) {
			t = Math.atan(y/x);
		}
		
		else if(x < 0.001 && x > -0.01) {
			t = Math.PI/2;
		}
		
		double cos = Math.abs(Math.cos(t));
		double sin = Math.abs(Math.sin(t));
		
		if(this.mainObj.getCoordinates()[0] > this.centerX()) {
			this.xCoor += (stats.getSpeed() * cos);
		} else {
			this.xCoor -= (stats.getSpeed() * cos);
		}
		
		if(this.mainObj.getCoordinates()[1] > this.centerY()) {
			this.yCoor += (stats.getSpeed() * sin);
		} else {
			this.yCoor -= (stats.getSpeed() * sin);
		}
	}
	
	/**
	 * Causes the Creature to move in a randomly changing direction
	 */
	
	private void search() {
		if((this.ticks % 125 == 0) && this.outOfBounds() < 0) {
			this.direction = (int)Math.round((Math.random() * 3));
		}
		else if(this.outOfBounds() >= 0) {
			this.bounce();
		}
		
		switch (direction) {
		case 0:
			this.yCoor -= stats.getSpeed();
		break;
		
		case 1:
			this.xCoor += stats.getSpeed();
		break;
		
		case 2:
			this.yCoor += stats.getSpeed();
		break;
		
		case 3:
			this.xCoor -= stats.getSpeed();
		break;
		}
	}
	
	/**
	 * Causes the Creature to immediately move in the opposite direction
	 */
	
	private void bounce() {
		if(this.direction < 2) {
			this.direction += 2;
		} else {
			this.direction -= 2;
		}
	}
	
	/**
	 * Determines if the Creature is within the bounds of the window
	 * @return The integer value of the wall that is impacted (0 = top, 1 = right, 2 = down, 3 = left)
	 */
	
	private int outOfBounds() {
		int bounds = -1;
		
		if(this.xCoor + stats.getSize() > Window.WIDTH) {
			bounds = 1;
		}
		else if(this.xCoor < 0) {
			bounds = 3;
		}
		else if(this.yCoor + stats.getSize() > Window.HEIGHT) {
			bounds = 2;
		}
		else if(this.yCoor < 0) {
			bounds = 0;
		}
		
		return bounds;
	}
	
	/**
	 * Adds the food to the ArrayList of Food within range
	 * @param obj Food within range
	 */
	
	public void perceive(Food obj) {
		this.food.add(obj);
	}
	
	/**
	 * Determines if food is within range of Creature
	 * @param obj Food Object to be checked
	 */
	
	public void check(Food obj) {
		if(Math.abs(obj.getCoordinates()[0] - (this.xCoor + (stats.getSize()/2))) < stats.getRange()){
			if(Math.abs(obj.getCoordinates()[1] - (this.yCoor + (stats.getSize()/2))) < stats.getRange()){
				this.perceive(obj);
			}
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(this.color);
		g2d.fillOval((int)this.xCoor, (int)this.yCoor, stats.getSize(), stats.getSize());
		//g2d.drawRect((int)this.xCoor, (int)yCoor, stats.getSize(), stats.getSize());
		
		/*
		DecimalFormat df = new DecimalFormat("#.##");
		g2d.setColor(Color.WHITE);
		g2d.drawString(df.format(hunger.getCoefficient()) + "x^2 + 0.3x -" + df.format(hunger.getModifier()), this.centerX(), this.centerY());
		g2d.drawString("x = " + hunger.timeExist(), this.centerX(), this.centerY() + 10);
		g2d.drawString("y = " + df.format(hunger.getValue()), this.centerX(), this.centerY() + 20);
		*/
	}
	
	private int getCollide(Rectangle r1, Rectangle r2) {
		int dir = 0;
		
		if(r1.y + r1.height >= r2.y) {
			dir = 0;
		}
		else if(r1.x <= r2.x + r2.width) {
			dir = 1;
		}
		else if(r1.y <= r2.y + r2.height) {
			dir = 2;
		}
		else if(r1.x  + r1.height >= r2.x) {
			dir = 3;
		}
		
		return dir;
	}
	
	private void collide(int direction) {
		this.shift(direction, 1);
	}
	
	public Rectangle getRectangle() {
		return this.rect;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	private void shift(int dir, float dist) {
		switch(direction) {
		case 0:
			this.yCoor -= dist;
		break;
		
		case 1:
			this.xCoor += dist;
		break;
		
		case 2:
			this.yCoor += dist;
		break;
		
		case 3:
			this.xCoor -= dist;
		break;
		}
	}
	
	/**
	 * Adds the value of the Food to applicable places and removes the food from the simulation
	 * @param food
	 */
	
	public void eat(Food food) {
		int energy = (int)(food.getValue() * 10);
		this.hunger.satiate(food.getValue());
		if((this.ticks + energy) < stats.getMitosisTime()) {
			this.ticks += energy;
		} else {
			this.ticks = stats.getMitosisTime() - 1;
		}
		food.eat();
	}
	
	/**
	 * Determines if the Creature is ready to create offspring
	 * @return
	 */
	
	public boolean procreate() {
		return (this.ticks % stats.getMitosisTime() == 0);
	}
	
	/**
	 * - Creates a version of the creature that is randomized based off parent's stats 
	 * @return Creature based off parent
	 */
	public abstract Creature offspring();
	
	public Color getColor() {
		return this.color;
	}
	
	public float centerX() {
		return this.xCoor + (stats.getSize()/2);
	}
	
	public float centerY() {
		return this.yCoor + (stats.getSize()/2);
	}
	
	public Stats getStats() {
		return this.stats;
	}
	
	protected Hunger getHunger() {
		return this.hunger;
	}
	
	public double getCoefficient() {
		return this.hunger.getCoefficient();
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	private float randomFloat(float min, float max) {
		Random random = new Random();
		return (random.nextFloat() * (max - min)) + min;
	}
	
	private int randomInt(int min, int max) {
		Random rand = new Random();
		return  rand.nextInt((max - min) + 1) + min;
	}
	
	private double randomDouble(double min, double max) {
		Random r = new Random();
		return (min + (max - min) * r.nextDouble());
	}

}
