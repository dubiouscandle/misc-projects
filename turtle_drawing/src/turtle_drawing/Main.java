package turtle_drawing;

import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Drawer drawer = new Drawer(1600, 1600);
		Interpreter interpreter = new Interpreter(drawer);
		JPanel renderer = new Renderer(drawer.getImg());
		renderer.setPreferredSize(new Dimension(500, 500));

		frame.add(renderer);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		Scanner in = new Scanner(System.in);
		while (true) {
			String line = in.nextLine();

			if (line.equals("STOP")) {
				break;
			}
			interpreter.run(line);
			renderer.repaint();
		}
		in.close();
	}
}
