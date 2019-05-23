import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

public class StartMenu extends Menu {
	
	private int selector;

	public StartMenu() {
		this.selector = 0;
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		Font big = new Font("Arial", Font.BOLD, 26);
		Font norm = new Font("Arial", Font.PLAIN, 22);
		Font small = new Font("Arial", Font.PLAIN, 18);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(big);
		g2d.drawString("Evolution Simulation", 115, 50);
		
		g2d.setFont(norm);
		g2d.drawString("Start New", 200, 150);
		g2d.drawString("Continue", 205, 200);
		
		switch(this.selector) {
		case 0:
			g2d.drawLine(200, 155, 300, 155);
			break;
			
		case 1:
			g2d.drawLine(205, 205, 300, 205);
			break;
		}
		
		g2d.drawString("Winner Averages: ", 165, 270);
		g2d.setFont(small);
		
		g2d.drawString("Size: " + df.format(Window.getAverages().averageSize()) + " (20)", 40, 310);
		g2d.drawString("Mitosis Time: " + df.format(Window.getAverages().averageMitosis()) + " (1300)", 40, 340);
		g2d.drawString("Speed: " + df.format(Window.getAverages().averageSpeed()) + " (1.15)", 40, 370);
		g2d.drawString("Perception: " + df.format(Window.getAverages().averageRange()) + " (92.5)", 40, 400);
		g2d.drawString("Metabolism: " + df.format(Window.getAverages().averageMetabolism()) + "(0.15)", 40, 430);

	}

	@Override
	public void interact(int keycode) {
		
		if(keycode == KeyEvent.VK_ENTER) {
			if(this.selector == 0) {
				Window.setNewSimRunning();
			}
			else if(this.selector == 1) {
				Window.setContinueSim();
			}
		}
		
		if(keycode == KeyEvent.VK_DOWN) {
			if(this.selector == 0) this.selector = 1;
			else this.selector = 0;
		} 
		else if(keycode == KeyEvent.VK_UP) {
			if(this.selector == 0) this.selector = 1;
			else this.selector = 0;
		}

	}

}
