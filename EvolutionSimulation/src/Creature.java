import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public abstract class Creature {
	
	private float xCoor;
	private float yCoor;
	
	private int size;
	
	private float range;
	
	private float speed;
	private int direction;
	
	private Color color;
	
	private int ticks;
	private boolean alive;
	private int mitosisTime;
	
	private Rectangle rect;
	private ArrayList<Food> food;
	private Food mainObj;
	private Hunger hunger;

	public Creature(Color color) {
		this.xCoor = randomFloat(100, 400);
		this.yCoor = randomFloat(100, 400);
		
		this.speed = randomFloat(0.3f, 2.0f);
		this.range = randomFloat(35, 150);
		
		this.size = randomInt(10, 30);
		
		System.out.println(size);
		
		this.hunger = new Hunger(randomDouble(0.05, 0.1));
		
		this.mitosisTime = randomInt(900, 1200);
		
		this.rect = new Rectangle((int)xCoor, (int)yCoor, this.size, this.size);
		
		this.color = color;
		
		this.alive = true;
		
		this.food = new ArrayList<Food>();
	}
	
	public Creature(Color color, float speed, float range, int size, Hunger hunger, int mitosisTime, float x, float y) {
		this.xCoor = x;
		this.yCoor = y;
		
		this.color = color;
		
		this.speed = randomFloat((speed - 0.2f), (speed + 0.2f));
		if(this.speed < 0) this.speed = 0.05f;
		this.range = randomFloat((range - 7.5f), (range + 7.5f));
		
		this.size = randomInt((size - 1), (size + 1));
		
		this.rect = new Rectangle((int)xCoor, (int)yCoor, this.size, this.size);
		
		double co = hunger.copy().getCoefficient();
		this.hunger = new Hunger(randomDouble((co - .01), (co + 0.1)));
		
		this.mitosisTime = randomInt((mitosisTime - 20), (mitosisTime + 20));
		
		this.alive = true;
		
		this.food = new ArrayList<Food>();
	}
	
	public void update() {
		this.ticks++;
		this.rect.setLocation((int)xCoor, (int)yCoor);
		
		this.hunger.update();
		if(this.hunger.isExpired()) {
			this.alive = false;
		}
		this.determine();
	}
	
	public void move() {
		if(this.mainObj != null) {
			this.hunt();
		} else {
			this.search();
		}
		
		this.update();
	}
	
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
	 * @param food1 First food
	 * @param food2 Second food
	 * @return Determines if the food is closer
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
	
	private void hunt() {
		if(this.mainObj.getCoordinates()[0] > this.centerX()) {
			this.xCoor += this.speed;
		} else {
			this.xCoor -= this.speed;
		}
		
		if(this.mainObj.getCoordinates()[1] > this.centerY()) {
			this.yCoor += this.speed;
		} else {
			this.yCoor -= this.speed;
		}
	}
	
	private void search() {
		if((this.ticks % 125 == 0) && this.outOfBounds() < 0) {
			this.direction = (int)Math.round((Math.random() * 3));
		}
		else if(this.outOfBounds() >= 0) {
			this.bounce();
		}
		
		switch (direction) {
		case 0:
			this.yCoor -= this.speed;
		break;
		
		case 1:
			this.xCoor += this.speed;
		break;
		
		case 2:
			this.yCoor += this.speed;
		break;
		
		case 3:
			this.xCoor -= this.speed;
		break;
		}
	}
	
	private void bounce() {
		if(this.direction < 2) {
			this.direction += 2;
		} else {
			this.direction -= 2;
		}
	}
	
	private int outOfBounds() {
		int bounds = -1;
		
		if(this.xCoor + this.size > Window.WIDTH) {
			bounds = 1;
		}
		else if(this.xCoor < 0) {
			bounds = 3;
		}
		else if(this.yCoor + this.size > Window.HEIGHT) {
			bounds = 2;
		}
		else if(this.yCoor < 0) {
			bounds = 0;
		}
		
		return bounds;
	}
	
	public void perceive(Food obj) {
		this.food.add(obj);
	}
	
	public void check(Food obj) {
		if(Math.abs(obj.getCoordinates()[0] - (this.xCoor + (this.size/2))) < this.range){
			if(Math.abs(obj.getCoordinates()[1] - (this.yCoor + (this.size/2))) < this.range){
				this.perceive(obj);
			}
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(this.color);
		g2d.fillRect((int)xCoor, (int)yCoor, size, size);
		hunger.draw(g2d);
	}
	
	public Rectangle getRectangle() {
		return this.rect;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void eat(Food food) {
		int energy = (int)(food.getValue() * 10);
		this.hunger.satiate(food.getValue());
		if((this.ticks + energy) < this.mitosisTime) {
			this.ticks += energy;
		} else {
			this.ticks = mitosisTime - 1;
		}
		food.eat();
	}
	
	public boolean procreate() {
		return (this.ticks % this.mitosisTime == 0);
	}
	
	public abstract Creature offspring();
		//return new Creature(this.color, this.speed, this.range, this.size, this.hunger, this.mitosisTime, this.xCoor, this.yCoor);
	
	public Color getColor() {
		return this.color;
	}
	
	protected float centerX() {
		return this.xCoor + (this.size/2);
	}
	
	protected float centerY() {
		return this.yCoor + (this.size/2);
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public float getRange() {
		return this.range;
	}
	
	public int getSize() {
		return this.size;
	}
	
	protected Hunger getHunger() {
		return this.hunger;
	}
	
	public double getCoefficient() {
		return this.hunger.getCoefficient();
	}
	
	public int getMitosisTime() {
		return this.mitosisTime;
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
