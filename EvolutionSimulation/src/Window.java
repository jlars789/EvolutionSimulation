import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Handles the majority of interactions between objects
 * All method calls root back to this class, whether called in tick() or draw()
 * SIZE: 500 x 500 px
 * Extends JPanel (awt)
 * Implements Runnable and KeyListener
 */

/*
public Window(){
  
	setSize(WIDTH, HEIGHT)
  
    creatureList.add(Red, Blue, Pink, Orange)
  
    start()
  }
  
public void start(){

    new Thread
    thread.start

}

public void stop(){
    thread.join
}
 */

public class Window extends JPanel implements Runnable {
	
	private Thread thread;
	
	private static final long serialVersionUID = 1L;
	public static boolean running;
	public static boolean simRunning;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
	public static int[] teamCount;
	
	private static int ticks = 0;
	
	public static int startTime = (int)System.currentTimeMillis();
	public static int elapsedTime = (int)System.currentTimeMillis();
	
	public static ArrayList<Creature> creatureList; 
	public static ArrayList<Food> foodList;
	
	public static Creature finalCreature = null;
	
	public Window() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); //window size
		
		creatureList = new ArrayList<Creature>();
		foodList = new ArrayList<Food>();
		
		simRunning = true;
		
		teamCount = new int[4];
		
		creatureList.add(new RedCreature());
		creatureList.add(new PinkCreature());
		creatureList.add(new BlueCreature());
		creatureList.add(new OrangeCreature());
		
		start();
	}
	
	/**
	 * Invoking this method begins the simulation
	 */
	
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Invoking this method stops the simulation
	 */
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		/*
		 * Allows for a frame-by-frame painting on the screen.
		 * Clears the entire screen
		 * Sets the color to black
		 * Draws the background as the size of the window
		 */
		
		g2d.clearRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		
		if(simRunning) {
			this.drawSim(g2d);
		} else {
			g2d.setColor(Color.YELLOW);
			Font f = new Font("Arial", Font.BOLD, 26);
			g2d.setFont(f);
			
			g2d.drawString("Final Creature Stats: ", 75, 50);
			
			g2d.drawString("Speed: " + finalCreature.getSpeed(), 75, 100);
			g2d.drawString("Split Time: " + finalCreature.getMitosisTime() / 62.5 + "s", 75, 150);
			g2d.drawString("Hunger: " + finalCreature.getCoefficient(), 75, 200);
			g2d.drawString("Size: " + finalCreature.getSize(), 75, 250);
			g2d.drawString("Perception: " + finalCreature.getRange(), 75, 300);
		}
	}
	
	
	public void tick() {
		
		if(ticks >= 0) { //refreshes the screen
			try {
				Thread.sleep(16); //sets the rate of the game (1000 / integer) frames per second
				} catch(Exception e) {
					e.printStackTrace();
				}
			
			ticks++;
			
			elapsedTime = (int)System.currentTimeMillis();
			
			if(simRunning) {
				
				this.creatureScan();
				
				int mod = ((int)(60 / ((((creatureList.size())/4)) + 1) + 1));
				if(mod < 15) mod = 15;
				if(ticks % mod == 0) {
					foodList.add(new Food());
					ticks = 0;
				}
				int extinctCount = 0;
				for(int i = 0; i < 4; i++) {
					if(teamCount[i] == 0) {
						extinctCount++;
					}
				}
				
				if(extinctCount >= 3) {
					simRunning = false;
					finalCreature = creatureList.get(creatureList.size() - 1);
				}
			}
		}
	}
	
	private boolean checkContact(Creature creature, Food food) {
		boolean intersects = false;
		if(creature.getRectangle().intersects(food.getRectangle())) {
			intersects = true;
		}
		return intersects;
	}
	
	@Override
	public void run() {
		while(running) {
			tick(); //runs ticks while running is true
			repaint(); 
		}
		
	}
	
	/**
	 * Used for drawing the creatures, food, and text in the simulation
	 * @param g2d the Graphics2D object used to paint on the window
	 */
	
	private void drawSim(Graphics2D g2d) {
		if(foodList.size() > 0) {
			for(int i = 0; i < foodList.size(); i++) {
				foodList.get(i).draw(g2d);
			}
		}
		
		if(creatureList.size() > 0) {
			for(int i = 0; i < creatureList.size(); i++) {
				creatureList.get(i).draw(g2d);
			}
		}
		
		g2d.setColor(Color.BLUE);
		if(teamCount[0] > 0) {
			g2d.drawString("Blue: " + teamCount[0], 10, 10);
		} else {
			g2d.drawString("Blue: EXTINCT", 10, 10);
		}
		g2d.setColor(Color.PINK);
		if(teamCount[1] > 0) {
			g2d.drawString("Pink: " + teamCount[1], 10, 21);
		} else {
			g2d.drawString("Pink: EXTINCT", 10, 21);
		}
		
		g2d.setColor(Color.RED);
		if(teamCount[2] > 0) {
			g2d.drawString("Red: " + teamCount[2], 10, 32);
		} else {
			g2d.drawString("Red: EXTINCT", 10, 32);
		}
		
		g2d.setColor(Color.ORANGE);
		if(teamCount[3] > 0) {
			g2d.drawString("Orange: " + teamCount[3], 10, 43);
		} else {
			g2d.drawString("Orange: EXTINCT", 10, 43);
		}
	}
	
	/**
	 * Iterates through and performs actions for each of the creatures in
	 */
	
	private void creatureScan() {
		if(creatureList.size() > 0) {
			int blue = 0;
			int pink = 0;
			int red = 0;
			int orange = 0;
			for(int i = 0; i < creatureList.size(); i++) {
				creatureList.get(i).move();
				
				if(creatureList.get(i).getColor().equals(Color.BLUE)) blue++;
				else if(creatureList.get(i).getColor().equals(Color.PINK)) pink++;
				else if(creatureList.get(i).getColor().equals(Color.RED)) red++;
				else if(creatureList.get(i).getColor().equals(Color.ORANGE)) orange++;
				
				if(creatureList.get(i).procreate()) {
					creatureList.add(creatureList.get(i).offspring());
				}
				if(foodList.size() > 0) {
					for(int j = 0; j < foodList.size(); j++) {
						creatureList.get(i).check(foodList.get(j));
						if(checkContact(creatureList.get(i), foodList.get(j))) {
							creatureList.get(i).eat(foodList.get(j));
						}
						if(foodList.get(j).isEaten()) {
							foodList.remove(j);
						}
					}
				}
				
				if(!creatureList.get(i).isAlive()) {
					creatureList.remove(i);
				}
			}
			
			teamCount[0] = blue;
			teamCount[1] = pink;
			teamCount[2] = red;
			teamCount[3] = orange;
			
		}
	}
	
	public static int elapsedSimTime() {
		return Math.abs(elapsedTime - startTime) * 16;
	}
	
	public static double elapsedTimeInSeconds() {
		return Math.abs(elapsedTime - startTime) / 1000;
	}

}
