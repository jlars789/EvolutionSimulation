import java.awt.Color;

public class OrangeCreature extends Creature {
	
	private static final Color color = Color.ORANGE;
	
	public OrangeCreature() {
		super(color);
	}

	public OrangeCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y) {
		super(color, speed, range, size, hunger, mitosisTime, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Creature offspring() {
		return new OrangeCreature(super.getSpeed(), super.getRange(), super.getSize(), super.getHunger(), super.getMitosisTime(), super.centerX(), super.centerY());
	}

}
