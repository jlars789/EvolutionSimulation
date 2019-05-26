import java.awt.Graphics2D;

public abstract class Menu {
	
	private boolean open;

	public Menu() {
		this.open = false;
	}
	
	public abstract void draw(Graphics2D g2d);
	
	public abstract void interact(int keycode);
	
	public boolean isOpen() {
		return open;
	}
	
	public void open(boolean open) {
		this.open = open;
	}

}
