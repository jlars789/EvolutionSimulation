import java.awt.Color;

public class RedCreature extends Creature {

	private static final Color color = Color.RED;
	
	public RedCreature() {
		super(color);
	}

	public RedCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y) {
		super(color, speed, range, size, hunger, mitosisTime, x, y);
	}

	@Override
	public Creature offspring() {
		return new RedCreature(super.getSpeed(), super.getRange(), super.getSize(), super.getHunger(), super.getMitosisTime(), super.centerX(), super.centerY());
	}

}
