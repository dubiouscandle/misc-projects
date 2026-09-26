package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import north_korea_game.Scene;
import stuf.MyButton;

public class CharacterSelectScene extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5474621311446067948L;

	public class CharacterSelectButton extends MyButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1011821795819586770L;
		private Image img;

		{
			this.setBackground(Color.LIGHT_GRAY);
		}

		public CharacterSelectButton(String path) {
			super("");
			img = Util.readImg(path);
		}

		{
		}
		{
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					CharacterSelectScene.this.getFrame().setScene(new DayOneScene());
					CharacterSelectScene.characterImg = img;
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
			Util.drawImageCentered(img, this.getWidth() / 2, this.getHeight() / 2, this.getWidth(), g);
		}
	}

	CharacterSelectButton[] buttons = new CharacterSelectButton[8];

	{
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new CharacterSelectButton(
					"characters/North Korea Ambassador Simulator (" + (i + 1) + ").png");

			this.add(buttons[i]);
		}

		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int width = getWidth();
				int height = getHeight();

				for (int i = 0; i < buttons.length; i++) {
					int x = i % 4;
					int y = i / 4;

					int buttonWidth = width * 18 / 100;
					int buttonHeight = buttonWidth * 4 / 3;

					int hGap = buttonWidth + height / 50;
					int vGap = buttonHeight + width / 50;

					buttons[i].setBounds((width - (hGap * 4)) / 2 + hGap * x, 10 + (height - vGap * 2) / 2 + vGap * y,
							buttonWidth, buttonHeight);
				}
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
	}

	{
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g = this.getTransformedGraphics(g);

		g.setColor(Color.BLACK);
		g.setFont(new Font("courier new", Font.BOLD, 36));
		Util.drawStringCentered(W / 2, H / 20, "CHOOSE YOUR AMBASSADOR", g);

	}

	public static Image characterImg = Util.readImg("characters/North Korea Ambassador Simulator (1).png");

}
