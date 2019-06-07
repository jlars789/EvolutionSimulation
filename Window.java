import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

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

public void tick(){
    try{
        thread.sleep(16) (lets the CPU rest between iterations)
    }
    catch (Exception e) {
         
    }
    
    creatureScan();
}

public void creatureScan(){
	creature.get(i).update()
	if(creature.get(i).procreate()){
	    creatureList.add(creature.get(i).offspring())
	}
	
	
	if(checkContact(creature.get(i), foodList.get(i)){
	    creature.eat(foodList.get(i)
	}
}
 */

public class Window extends JPanel implements Runnable, KeyListener {
	
	private Thread thread;
	
	private static final long serialVersionUID = 1L;
	public static boolean running;
	private static boolean simRunning;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
	public static int[] teamCount;
	
	private static int ticks = 0;
	
	public static int startTime = (int)System.currentTimeMillis();
	public static int elapsedTime = (int)System.currentTimeMillis();
	
	public static ArrayList<Creature> creatureList; 
	public static ArrayList<Food> foodList;
	
	private static AverageStats avg;
	
	public static Menu[] menu = new Menu[2];
	
	public static Creature finalCreature = null;
	
	public static WritableList wl;
	
	public Window() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); //window size
		addKeyListener(this);
		
		creatureList = new ArrayList<Creature>();
		foodList = new ArrayList<Food>();
		
		simRunning = false;
		teamCount = new int[4];
		menu[0] = new StartMenu();
		menu[1] = new EndMenu(null);
		
		wl = DataReader.readStats();
		System.out.println("List Size: " + wl.getList().size());
		avg = new AverageStats(wl);
		
		
		start();
	}
	
	/**
	 * Invoking this method begins the simulation
	 */
	
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
		menu[0].open(true);
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
			
			for(int i = 0; i < menu.length; i++) {
				if(menu[i].isOpen()) {
					menu[i].draw(g2d);
				}
			}
		}
	}
	
	/**
	 * Iterates through all applicable methods each time called. All actions of any objects should be through this method.
	 */
	
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
				
				if((elapsedTime - startTime) > 30000) {
					extinctionEvent();
				}
				
				int mod = ((int)(60 / ((((creatureList.size())/4)) + 1) + 1));
				if(mod < 30) mod = 30;
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
				
				if(extinctCount == 3) {
					finalCreature = creatureList.get(creatureList.size() - 1);
					endSim();
				} 
				else if(extinctCount == 4) {
					endSim();
				}
			}
		}
	}
	
	private static void endSim() {
		simRunning = false;
		menu[1] = new EndMenu(finalCreature);
		DataWriter.writeOut(finalCreature);
		wl.add(finalCreature.getStats());
		DataWriter.createStats(wl);
		menu[1].open(true);
	}
	
	/**
	 * Checks to see if a Food object and a Creature object have contact
	 * @param creature
	 * @param food
	 * @return
	 */
	
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

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		for(int i = 0; i < menu.length; i++) {
			if(menu[i].isOpen()) {
				menu[i].interact(key);
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public static void setNewSimRunning() {
		startTime = (int)System.currentTimeMillis();
		creatureList.clear();
		creatureList.add(new RedCreature());
		creatureList.add(new PinkCreature());
		creatureList.add(new BlueCreature());
		creatureList.add(new OrangeCreature());
		menu[0].open(false);
		menu[1].open(false);
		simRunning = true;
	}
	
	public static void setContinueSim() {
		startTime = (int)System.currentTimeMillis();
		creatureList.clear();
		
		DataReader.readCreature();
		
		Creature parent = DataReader.creatureRead();
		float[] v = new float[4];
		int[] s = new int[2];
		
		
		
		if(parent != null) {
			Hunger h = parent.getHunger();
			v[0] = parent.getStats().getSpeed(); v[1] = parent.getStats().getRange(); v[2] = randomFloat(100, 400); v[3] = randomFloat(100, 400);
			s[0] = parent.getStats().getSize(); s[1] = parent.getStats().getMitosisTime();
			
			creatureList.add(new RedCreature(v[0], v[1], s[0], h.copy(), s[1], v[2], v[3], 3));
			v[2] = randomFloat(100, 400); v[3] = randomFloat(100, 400);
			creatureList.add(new PinkCreature(v[0], v[1], s[0], h.copy(), s[1], v[2], v[3], 3));
			v[2] = randomFloat(100, 400); v[3] = randomFloat(100, 400);
			creatureList.add(new BlueCreature(v[0], v[1], s[0], h.copy(), s[1], v[2], v[3], 3));
			v[2] = randomFloat(100, 400); v[3] = randomFloat(100, 400);
			creatureList.add(new OrangeCreature(v[0], v[1], s[0], h.copy(), s[1], v[2], v[3], 3));
			
			menu[0].open(false);
			simRunning = true;
		} 
		
	}
	
	private static void extinctionEvent() {
		Color winningTeam = null;
		int blue = 0;
		int pink = 0;
		int red = 0;
		int orange = 0;
		for(int i = 0; i < creatureList.size(); i++) {
			if(creatureList.get(i).getColor().equals(Color.BLUE)) blue++;
			else if(creatureList.get(i).getColor().equals(Color.PINK)) pink++;
			else if(creatureList.get(i).getColor().equals(Color.RED)) red++;
			else if(creatureList.get(i).getColor().equals(Color.ORANGE)) orange++;
		}
		
		
		boolean blu = (blue > pink);
		boolean re = (red > orange);
		if(!blu && !re) {
			if(pink > orange) {
				winningTeam = Color.PINK;
			} else {
				winningTeam = Color.ORANGE;
			}
		}
		else if(!blu && re) {
			if(red > pink) {
				winningTeam = Color.RED;
			} else {
				winningTeam = Color.PINK;
			}
		} else if(blu && !re){
			if(blue > orange) {
				winningTeam = Color.BLUE;
			} else {
				winningTeam = Color.ORANGE;
			}
		} else {
			if(blue > red) {
				winningTeam = Color.BLUE;
			} else {
				winningTeam = Color.RED;
			}
		}
		
		for(int i = creatureList.size() -1; i >= 0; i--) {
			if(creatureList.get(i).getColor().equals(winningTeam)) {
				finalCreature = creatureList.get(i);
				creatureList.clear();
				for(int j = 0; j < 4; j++) {
					teamCount[j] = 0;
				}
				break;
			}
		}
	}
	
	//TODO Add ecosystem collapse
	
	private static float randomFloat(float min, float max) {
		Random random = new Random();
		return (random.nextFloat() * (max - min)) + min;
	}
	
	public static AverageStats getAverages() {
		return avg;
	}
	
	public static void eraseFood() {
		foodList.clear();
	}

}
