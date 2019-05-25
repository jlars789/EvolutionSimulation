import javax.swing.JFrame;

public class Main {

	public Main() {
		JFrame frame = new JFrame();
		Window gamepanel = new Window();
		
		frame.add(gamepanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("TEST");
		
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new Main();
	}
}
