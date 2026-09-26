package north_korea_game;

import javax.swing.JFrame;

import dialog.DialogParser;
import scenes.TitleScene;

public class Main {

	public static void main(String[] args) {
		GameFrame frame = new GameFrame();

		DialogParser.load();
		frame.setScene(new TitleScene());

		frame.setSize(Scene.getPreferredDimension());
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
