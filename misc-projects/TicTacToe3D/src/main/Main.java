//Name: Gavin Liu
//Date: Nov 23, 2024
//Title: Main.java
//Description: the main

package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import game.TicTacToe3D;

public class Main {

	// method: main
	// parameters: String[] args
	// return: void
	// description: it makes a game object then puts it in a renderer and puts that
	// into a jframe
	public static void main(String[] args) {
		TicTacToe3D game = new TicTacToe3D(4, 4);

		Simple3DRenderer simple3DRenderer = new Simple3DRenderer(game);
		JFrame frame = new JFrame();

		frame.add(simple3DRenderer);

		frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

		simple3DRenderer.repaint();
	}

}
