package scenes;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dialog.DialogScene;
import north_korea_game.Scene;
import stuf.MyButton;

public class DayTwoScene extends Scene {
	private static final long serialVersionUID = -500738747798562646L;
	{
		IntroScene.audio.stop();
		DialogScene.backgroundAudio.play();
	}
	public DayTwoScene() {
		this.setCursor(MyButton.HAND_CURSOR);

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getFrame().setScene(new DayTwoAdditionalInfoScene());
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

		g.setFont(new Font("courier new", Font.BOLD, 32));
		Util.drawStringCentered(W / 2, H / 2, "DAY 2 NEGOTIATION", g);
	}
}
