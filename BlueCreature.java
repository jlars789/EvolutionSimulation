import java.awt.Color;

public class BlueCreature extends Creature {

	private static final long serialVersionUID = 2353967196971584663L;
	private static final Color color = Color.BLUE;
	
	public BlueCreature() {
		super(color);
	}

	public BlueCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y, float variance) {
		super(color, speed, range, size, hunger, mitosisTime, x, y, variance);
	}

	@Override
	public Creature offspring() {
		return new BlueCreature(super.getStats().getSpeed(), super.getStats().getRange(), super.getStats().getSize(), super.getHunger(), super.getStats().getMitosisTime(), super.centerX(), super.centerY(), 1);
	}

}
