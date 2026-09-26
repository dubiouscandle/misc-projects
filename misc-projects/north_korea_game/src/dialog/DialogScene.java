package dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import audio.Audio;
import dialog.DialogParser.Dialog;
import north_korea_game.Scene;
import scenes.CharacterSelectScene;
import scenes.Util;
import stuf.MyButton;

public class DialogScene extends Scene {
	/**
	 * 
	 * 
	 * 
	 */
	private static final long serialVersionUID = 5748400695380493065L;

	public static Audio backgroundAudio = new Audio("audio/Decisions.wav");

	private Dialog currentDialog = DialogParser.dialogs.get(0);

	private MyButton[] buttons = new DialogButton[] { new DialogButton(""), new DialogButton(""), new DialogButton(""),
			new DialogButton(""), };

	int r = 235;
	Color[] backgrounds = new Color[] { new Color(r, r, r), new Color(r, r, r), new Color(r, r, r),
			new Color(r, r, r), };
	{
		buttons[0].setBackground(backgrounds[0]);
		buttons[1].setBackground(backgrounds[1]);
		buttons[2].setBackground(backgrounds[2]);
		buttons[3].setBackground(backgrounds[3]);

		for (MyButton b : buttons) {
			b.setBorder(new LineBorder(Color.BLACK));
		}
	}

	private static class DialogButton extends MyButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2395372617588032706L;

		public DialogButton(String text) {
			super(text);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(this.getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier New", Font.BOLD, this.getHeight() / 7));
			int height = g.getFontMetrics().getHeight();

			int margin = 10;
			Util.drawStringWithLineBreaks(margin, height, this.getWidth() - margin * 2, this.getText(), g);
		}
	}

	{
		for (int i = 0; i < 4; i++) {
			buttons[i].setScale(0.1);
			buttons[i].setBackground(backgrounds[i]);
			this.add(buttons[i]);
		}

		this.setBackground(Color.LIGHT_GRAY);

		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int width = getWidth();
				int height = getHeight();

				int buttonWidth = width * 7 / 15;
				int hGap = (width - 2 * buttonWidth) / 3;

				int buttonHeight = height * 1 / 5;

				int x1 = hGap;
				int x2 = (width + hGap) / 2;
				int y1 = height - buttonHeight * 2 - hGap * 2;
				int y2 = height - buttonHeight - hGap;

				buttons[0].setBounds(x1, y1, buttonWidth, buttonHeight);
				buttons[1].setBounds(x2, y1, buttonWidth, buttonHeight);
				buttons[2].setBounds(x1, y2, buttonWidth, buttonHeight);
				buttons[3].setBounds(x2, y2, buttonWidth, buttonHeight);
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

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g = this.getTransformedGraphics(g);

		if (currentDialog == DialogParser.dialogs.get(0)) {
			renderFirst(g);
		} else if (currentDialog.options.size() == 0) {
			renderEnd(g);
		} else {
			renderStandard(g);
		}

	}

	{
		updateDialog();
	}

	private void renderEnd(Graphics g) {
		for (int i = 0; i < 4; i++) {
			buttons[i].setVisible(false);
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, W, H);

		g.setColor(Color.WHITE);

		g.setFont(new Font("Courier New", Font.BOLD, 19));

		Util.drawStringWithLineBreaks(W / 5, H / 12, W * 3 / 5, currentDialog.desc, g);

		try {
			if (currentDialog.id == 101)
				Util.drawImageCentered(ImageIO.read(Util.getFile("endings/" + currentDialog.id + ".png")), W * 1 / 2,
						H * 2 / 3, W * 4 / 10, g);
			else {
				Util.drawImageCentered(ImageIO.read(Util.getFile("endings/" + currentDialog.id + ".png")), W * 1 / 2,
						H * 2 / 3, W * 1 / 2, g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void renderStandard(Graphics g) {
		for (int i = 0; i < 4; i++) {
			buttons[i].setVisible(true);
		}
		g.setFont(new Font("Courier New", Font.BOLD, 17));
		Util.drawStringWithLineBreaks(W / 15, H / 12, W * 10 / 20, currentDialog.desc, g);

		Util.drawImageCentered(CharacterSelectScene.characterImg, W * 1 / 8, H * 4 / 10, W / 7, g);

		g.drawImage(currentDialog.img, W * 9 / 15, H * 1 / 20, (int) (W * 3 / 8), H * 6 / 13, null);
	}

	private void renderFirst(Graphics g) {
		g.setFont(new Font("Courier New", Font.BOLD, 32));
		Util.drawStringCentered(W / 2, H / 5, currentDialog.desc, g);
	}

	private Audio endingAudio;

	private void handleMouseClicked(int option) {
		if (option == -1) {
			if (currentDialog.options.size() == 0) {
				if (endingAudio != null) {
					endingAudio.stop();
					backgroundAudio.play();
				}

				currentDialog = DialogParser.dialogs.get(0);
				updateDialog();
				repaint();
				return;
			}
		}

		if (option >= currentDialog.options.size() || option < 0)
			return;

		JPanel blackPanel = new JPanel();
		blackPanel.setBackground(Color.BLACK);
		blackPanel.setBounds(0, 0, getWidth(), getHeight());
		add(blackPanel);

		for (MyButton b : buttons) {
			b.setVisible(false);
			b.repaint();
		}

		repaint();

		SwingUtilities.invokeLater(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			remove(blackPanel);
			for (MyButton b : buttons)
				b.setVisible(true);

			revalidate();
			repaint();
		});

		Dialog next = DialogParser.dialogs.get(currentDialog.pointers.get(option));
		currentDialog = next;

		if (next.options.size() == 0) {
			backgroundAudio.stop();
			endingAudio = new Audio("endings/" + next.id + ".wav");
			endingAudio.play();
		}

		updateDialog();
		repaint();
	}

	private void updateDialog() {
		if (currentDialog.options.size() == 0) {
			for (int i = 0; i < 4; i++) {
				buttons[i].setVisible(false);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				buttons[i].setVisible(true);
			}
		}

		for (int i = 0; i < 4; i++) {
			if (i < currentDialog.options.size()) {
				buttons[i].setText(currentDialog.options.get(i));
			} else {
				buttons[i].setText("");
			}
			buttons[i].repaint();
		}

		repaint();
	}

	{
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				DialogScene.this.handleMouseClicked(-1);
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

	{
		for (int i = 0; i < 4; i++) {
			MyButton b = buttons[i];

			final int i1 = i;
			b.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					handleMouseClicked(i1);
				}

			});
		}
	}
}
