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

import dialog.DialogScene;
import north_korea_game.Scene;
import stuf.MyButton;

public class DayTwoAdditionalInfoScene extends Scene {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2216497719151897548L;

	{
		MyButton button = new MyButton("continue");

		button.setBackground(Color.GREEN);
		button.setBorder(new LineBorder(Color.BLACK, 1));

		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getFrame().setScene(new DialogScene());

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
		g.setFont(new Font("courier new", Font.BOLD, 20));
		Util.drawStringWithLineBreaks(W / 6, H * 5 / 8 + 5, W * 4 / 6,
				"Bad news: Turns out the only way that the North Koreans would agree to the conference was if it happened on their turf. This means that no one can protect you if Kim Jong-Un gets a little too angry. Tread lightly.",
				g);

		try {
			Image img = ImageIO.read(Util.getFile("joe_biden_2.png"));

			Util.drawImageCentered(img, W / 2, H * 3 / 10, W / 2, g);

			((Graphics2D) g).setStroke(new BasicStroke(2.0f));
			Util.drawRectCentered(W / 2, H * 3 / 10, W / 2,
					(int) ((double) img.getHeight(null) / img.getWidth(null) * W / 2), g);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
