import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Food {
	
	private float xCoor;
	private float yCoor;
	
	private int width;
	private int height;
	
	private Color color;
	
	private double value;
	
	private Rectangle rect;
	private boolean eaten;

	public Food() {
		this.xCoor = randomFloat(20, 480);
		this.yCoor = randomFloat(20, 480);
		
		this.width = 7;
		this.height = 7;
		
		this.eaten = false;
		
		this.rect = new Rectangle((int)xCoor, (int)yCoor, width, height);
		
		this.color = Color.MAGENTA;
		
		this.value = randomDouble(4.5, 7.5);
	}
	
	public void update() {
		this.rect.setBounds((int)xCoor, (int)yCoor, width, height);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(this.color);
		g2d.fillRect((int)this.xCoor, (int)this.yCoor, this.width, this.height);
	}
	
	public float[] getCoordinates() {
		float[] location = new float[2];
		location[0] = this.xCoor + (this.width / 2);
		location[1] = this.yCoor + (this.height / 2);
		return location;
	}
	
	public int getSize() {
		return this.width;
	}
	
	public Rectangle getRectangle() {
		return this.rect;
	}
	
	public void eat() {
		this.eaten = true;
	}
	
	public boolean isEaten() {
		return this.eaten;
	}
	
	public double getValue() {
		return this.value;
	}
	
	private float randomFloat(float min, float max) {
		Random random = new Random();
		return (random.nextFloat() * (max - min)) + min;
	}
	
	private double randomDouble(double min, double max) {
		Random r = new Random();
		return (min + (max - min) * r.nextDouble());
	}

}
