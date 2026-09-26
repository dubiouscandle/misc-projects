package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.LineBorder;

import audio.Audio;
import north_korea_game.Scene;
import stuf.MyButton;

public class TitleScene extends Scene {
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Audio audio;

	{
		audio = new Audio("audio/The Descent.wav");
		audio.play();
	}
	
	private MyButton creditsButton = new MyButton("credits and stuff");
	{
		creditsButton.setBackground(Color.GREEN);
		creditsButton.setBorder(new LineBorder(Color.BLACK));
	}
	{
		this.add(creditsButton);
		
		creditsButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				TitleScene.this.getFrame().setScene(new CreditsScene());
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

				creditsButton.setScale(0.4);
				creditsButton.setBounds(0, height * 14 / 15, width * 5 / 19, height * 1 / 15 + 2);

				creditsButton.repaint();
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
	private MyButton startButton = new MyButton("start");
	{
		startButton.playSound = false;
	}
	{
		startButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				audio.stop();
				getFrame().setScene(new TransitionScene());
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

		this.add(startButton);

		startButton.setOpaque(false);
		startButton.setBackground(new Color(0, 0, 0, 0));
		startButton.setTextColor(new Color(0, 0, 0, 0));
		startButton.setBorder(null);

		startButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
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

				startButton.setBounds(width * 3 / 10, height * 3 / 10, width * 4 / 10, height * 2 / 5);

				startButton.repaint();
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
	public void close() {
		audio.stop();
	}

	private static final Image BIG_RED_BUTTON_IMG = Util.readImg("red_button.png");

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g = getTransformedGraphics(g);

		g.setFont(new Font("courier new", Font.BOLD, 64));
		g.setColor(Color.BLACK);
		Util.drawStringCentered(W / 2, H / 5, "NORTH KOREA GAME", g);

		g.setFont(new Font("courier new", Font.BOLD, 32));
		Util.drawStringCentered(W / 2, H * 4 / 5, "PRESS THE BUTTON TO START", g);

		startButton.repaint();

		g.translate(W / 2, H * 6 / 10);
		Util.drawImageCentered(BIG_RED_BUTTON_IMG, 0, -W / 20, W * 4 / 10, g);
	}

	private static class TransitionScene extends Scene {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6877928800379944806L;
		int imgIndex = 0;
		Audio audio = new Audio(Audio.NUKE_SOUND);
		{
			audio.play();
		}
		@Override
		public void close(){
			audio.stop();
		}
		{

			new Thread() {
				@Override

				public void run() {
					while (imgIndex < 25) {
						try {
							Thread.sleep(60);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						imgIndex++;
						repaint();
					}

					getFrame().setScene(new IntroScene());

				}
			}.start();
		}

		private Image explosionImg = Util.readImg("explosion.png");

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g = this.getTransformedGraphics(g);

			int x = imgIndex % 4;
			int y = imgIndex / 4;

			g.drawImage(explosionImg, -x * W, -y * H, W * 4, H * 4, null);
		}

	}

}
