import java.awt.Color;

public class RedCreature extends Creature {

	private static final long serialVersionUID = 2503151355032338059L;
	private static final Color color = Color.RED;
	
	public RedCreature() {
		super(color);
	}

	public RedCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y, float variance) {
		super(color, speed, range, size, hunger, mitosisTime, x, y, variance);
	}

	@Override
	public Creature offspring() {
		return new RedCreature(super.getStats().getSpeed(), super.getStats().getRange(), super.getStats().getSize(), super.getHunger(), super.getStats().getMitosisTime(), super.centerX(), super.centerY(), 1);
	}

}
