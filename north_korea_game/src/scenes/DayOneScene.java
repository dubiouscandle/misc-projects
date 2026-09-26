package scenes;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import north_korea_game.Scene;
import stuf.MyButton;

public class DayOneScene extends Scene {
	private static final long serialVersionUID = -9002252296838388325L;
	{
	}
	public DayOneScene() {
		this.setCursor(MyButton.HAND_CURSOR);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getFrame().setScene(new DayOneAdditionalInfoScene());
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
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g = this.getTransformedGraphics(g);

		Image img = Util.readImg("folder.png");
		Util.drawImageCentered(img, W / 2, H * 3 / 5, 300, g);

		g.setFont(new Font("courier new", Font.BOLD, 22));
		Util.drawStringCentered(W / 2, H * 1 / 20, "Look through", g);
		Util.drawStringCentered(W / 2, H * 2 / 20, "documents/articles", g);
		Util.drawStringCentered(W / 2, H * 3 / 20, "to reasearch and learn", g);
	}
}
