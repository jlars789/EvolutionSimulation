import java.awt.Color;

public class BlueCreature extends Creature {

	private static final Color color = Color.BLUE;
	
	public BlueCreature() {
		super(color);
	}

	public BlueCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y) {
		super(color, speed, range, size, hunger, mitosisTime, x, y);
	}

	@Override
	public Creature offspring() {
		return new BlueCreature(super.getSpeed(), super.getRange(), super.getSize(), super.getHunger(), super.getMitosisTime(), super.centerX(), super.centerY());
	}

}
