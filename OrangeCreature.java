import java.awt.Color;

public class OrangeCreature extends Creature {
	
	private static final long serialVersionUID = 9000055673068405237L;
	private static final Color color = Color.ORANGE;
	
	public OrangeCreature() {
		super(color);
	}

	public OrangeCreature(float speed, float range, int size, Hunger hunger, int mitosisTime, float x,
			float y, float variance) {
		super(color, speed, range, size, hunger, mitosisTime, x, y, variance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Creature offspring() {
		return new OrangeCreature(super.getStats().getSpeed(), super.getStats().getRange(), super.getStats().getSize(), super.getHunger(), super.getStats().getMitosisTime(), super.centerX(), super.centerY(), 1);
	}

}
