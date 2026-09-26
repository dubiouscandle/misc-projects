package scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

import audio.Audio;
import north_korea_game.Scene;
import stuf.MyButton;

public class IntroScene extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2216497719151897548L;

	public static Audio audio = new Audio("audio/Cut and Run.wav");

	{
//		audio.setVolume(-6f);
		audio.play();
	}

	{
		MyButton button = new MyButton("continue");

		button.setBackground(Color.GREEN);
		button.setBorder(new LineBorder(Color.BLACK, 1));

		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getFrame().setScene(new CharacterSelectScene());

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int width = getWidth();
				int height = getHeight();

				button.setBounds(width / 4, height * 19 / 21, width * 1 / 2, height * 1 / 14);

				button.repaint();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});
		this.add(button);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g = getTransformedGraphics(g);

		g.setFont(new Font("courier new", Font.BOLD, 32));
		g.setColor(Color.BLACK);
		Util.drawStringCentered(W * 1 / 2, H * 3 / 7, "Hello and welcome to the game!", g);
		g.setFont(new Font("courier new", Font.BOLD, 20));
		Util.drawStringWithLineBreaks(W / 6, H * 4 / 8 + 10, W * 4 / 6,
				"In this game you play as a United States ambassador to the Democratic People’s Republic of Korea"
						+ " (DPRK) otherwise known as North Korea! You must research documents and data to understand the"
						+ " situation and then you will go head to head wit"
						+ "h Kim Jong-Un (for unrealistic clarity) and negotiate on the issues that revolve North Korea.",
				g);

		try {
			Image img = ImageIO.read(Util.getFile("joe biden happy.png"));

			Util.drawImageCentered(img, W / 2, H * 2 / 10, W / 3, g);

			((Graphics2D) g).setStroke(new BasicStroke(2.0f));
			Util.drawRectCentered(W / 2, H * 1 / 5, W / 3,
					(int) ((double) img.getHeight(null) / img.getWidth(null) * W / 3), g);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
