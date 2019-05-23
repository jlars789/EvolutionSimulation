import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class EndMenu extends Menu {
	
	private Creature finalCreature;

	public EndMenu(Creature finalCreature) {
		this.finalCreature = finalCreature;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.YELLOW);
		Font f = new Font("Arial", Font.BOLD, 26);
		g2d.setFont(f);
		
		g2d.drawString("Final Creature Stats: ", 75, 50);
		
		g2d.drawString("Speed: " + finalCreature.getStats().getSpeed(), 75, 100);
		g2d.drawString("Split Time: " + finalCreature.getStats().getMitosisTime() / 62.5 + "s", 75, 150);
		g2d.drawString("Hunger: " + finalCreature.getStats().getMetabolism(), 75, 200);
		g2d.drawString("Size: " + finalCreature.getStats().getSize(), 75, 250);
		g2d.drawString("Perception: " + finalCreature.getStats().getRange(), 75, 300);

	}

	@Override
	public void interact(int keycode) {

	}

}
