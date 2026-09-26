//Name: Gavin Liu
//Date: Nov 23, 2024
//Title: SingleElementFrame.java
//Description: a jframe which holds one element and then resizes it so it always has the correct aspect ratio

package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class SingleElementFrame extends JFrame {
	private static final int TAB_THICKNESS = 27;// also this only works for java machines which have a window tab
												// thichness of 27 so if you have an ipad or something it might not work
	private static final long serialVersionUID = 1L;

	private double aspectRatio;
	private Component component = null;

	public SingleElementFrame(double aspectRatio, Component c) {
		super();
		this.getContentPane().setBackground(Color.BLACK);

		this.aspectRatio = aspectRatio;
		int h = 720;

		int w = (int) (aspectRatio * h);
		this.setSize(new Dimension(w, h + TAB_THICKNESS));

		this.setLayout(null);

		component = c;
		this.add(component);

		updateComponentSize(w, h);

		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateComponentSize(getWidth(), getHeight());
			}
		});

	}

	//// method: updateComponentSize
	// parameters: int frameWidth, int frameHeight
	// return: void
	// description: it updates the components size
	private void updateComponentSize(int frameWidth, int frameHeight) {
		// i forgor what a lot of this stuff did oh well
		frameHeight -= TAB_THICKNESS;

		int newWidth = frameWidth;
		int newHeight = (int) (newWidth / aspectRatio);

		if (newHeight > frameHeight) {
			newHeight = frameHeight;
			newWidth = (int) (newHeight * aspectRatio);
		}

		component.setBounds((frameWidth - newWidth) / 2, (frameHeight - newHeight) / 2, newWidth, newHeight);
		component.setPreferredSize(new Dimension(newWidth, newHeight));
		component.revalidate();
	}
}
