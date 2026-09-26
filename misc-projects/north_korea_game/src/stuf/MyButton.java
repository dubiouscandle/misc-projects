package stuf;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import audio.Audio;
import scenes.Util;

public class MyButton extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 154905577966708250L;

	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	private String text;

	public boolean playSound = true;
//	private boolean isHovering = false;

	{
		this.setCursor(HAND_CURSOR);
	}

	{
		this.addMouseListener(new MouseListener() {
			Color originalColor = MyButton.this.getBackground();

			@Override
			public void mouseClicked(MouseEvent e) {
				if (playSound)
					Audio.playFile("button-202966.wav");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				originalColor = MyButton.this.getBackground();

				setBackground(originalColor.darker());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(originalColor);
			}

		});
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private double scale = 1;
	private String fontString = "courier new";
	private int modifier = Font.BOLD;

	private Color textColor;

	public MyButton(String text) {
		this.text = text;
	}

	public void setFont(String fontString, int modifier) {
		this.fontString = fontString;
		this.modifier = modifier;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Font font = new Font(fontString, modifier, (int) (this.getHeight() * scale));

		g.setColor(textColor);
		g.setFont(font);

		Util.drawStringCentered(this.getWidth() / 2, this.getHeight() / 2, text, g);
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
}
