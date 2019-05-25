import java.awt.Color;

public class PinkCreature extends Creature {
	
	private static final Color color = Color.PINK;
	
	public PinkCreature() {
		super(color);
	}

	public PinkCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y) {
		super(color, speed, range, size, hunger, mitosisTime, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Creature offspring() {
		return new PinkCreature(super.getSpeed(), super.getRange(), super.getSize(), super.getHunger(), super.getMitosisTime(), super.centerX(), super.centerY());
	}

}
