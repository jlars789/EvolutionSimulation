import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class EndMenu extends Menu {
	
	private Creature finalCreature;

	public EndMenu(Creature finalCreature) {
		this.finalCreature = finalCreature;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.YELLOW);
		Font f = new Font("Arial", Font.BOLD, 24);
		Font g = new Font("Arial", Font.PLAIN, 18);
		g2d.setFont(f);
		
		g2d.drawString("Final Creature Stats: ", 75, 50);
		
		g2d.drawString("Speed: " + finalCreature.getStats().getSpeed(), 75, 100);
		g2d.drawString("Split Time: " + finalCreature.getStats().getMitosisTime() / 62.5 + "s", 75, 140);
		g2d.drawString("Hunger: " + finalCreature.getStats().getMetabolism(), 75, 180);
		g2d.drawString("Size: " + finalCreature.getStats().getSize(), 75, 220);
		g2d.drawString("Perception: " + finalCreature.getStats().getRange(), 75, 260);
		
		g2d.setFont(g);
		
		g2d.drawString("Press [Space] to Start New", 100, 320);
		g2d.drawString("Press [Enter] to Continue", 100, 360);

	}

	@Override
	public void interact(int keycode) {
		
		if(keycode == KeyEvent.VK_SPACE) {
			Window.setNewSimRunning();
		} 
		else if(keycode == KeyEvent.VK_ENTER) {
			Window.setContinueSim();
			Window.eraseFood();
		}

	}

}
