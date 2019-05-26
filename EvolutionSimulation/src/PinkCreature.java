import java.awt.Color;

public class PinkCreature extends Creature {
	
	private static final long serialVersionUID = -2161641747806911256L;
	private static final Color color = Color.PINK;
	
	public PinkCreature() {
		super(color);
	}

	public PinkCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y, float variance) {
		super(color, speed, range, size, hunger, mitosisTime, x, y, variance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Creature offspring() {
		return new PinkCreature(super.getStats().getSpeed(), super.getStats().getRange(), super.getStats().getSize(), super.getHunger(), super.getStats().getMitosisTime(), super.centerX(), super.centerY(), 1);
	}

}
