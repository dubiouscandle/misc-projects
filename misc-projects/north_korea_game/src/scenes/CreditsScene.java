package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import north_korea_game.Scene;
import stuf.MyButton;

public class CreditsScene extends Scene {
	private static final long serialVersionUID = 1L;

	{
		MyButton ok = new MyButton("ok");
		ok.setBackground(Color.LIGHT_GRAY);
		this.add(ok);
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int width = getWidth();
				int height = getHeight();

				ok.setBounds(width / 4, height * 5 / 7, width / 2, height * 1 / 5);

				ok.repaint();
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

		ok.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				getFrame().setScene(new TitleScene());
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

		String[] lines = new String[] { 
				"dialog: kolton", 
				"documents: kolton", 
				"design: kolton", 
				"programming: gavin",
				"music: kevin macleod", 
				"sound effects: mixkit", 
				"images: google"};

		for (int i = 0; i < lines.length; i++) {
			Util.drawStringCentered(W / 2, H / 10 + i * H / 12, lines[i], g);
		}
	}
}
