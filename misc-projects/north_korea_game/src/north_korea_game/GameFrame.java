package north_korea_game;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8409379500712439329L;
	private Scene scene;

	public GameFrame() {
		super();

		this.setTitle("north korea game");
	}

	public void setScene(Scene scene) {
		if (this.scene != null) {
			this.scene.close();
			this.remove(this.scene);
		}
		
		this.scene = scene;
		this.scene.setFrame(null);
		scene.setFrame(this);

		this.add(scene);

		scene.repaint();
		

		this.repaint();

		this.revalidate();
	}
}
