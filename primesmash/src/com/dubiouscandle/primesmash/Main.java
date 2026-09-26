package com.dubiouscandle.primesmash;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private Game game;
	private JFrame frame;
	private JPanel panel;

	public void launch() {
		panel = new GamePanel();
		frame = new JFrame();

		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
