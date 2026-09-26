package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.LineBorder;

import north_korea_game.Scene;
import stuf.MyButton;

public class ResearchScene extends Scene {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6037975503005119408L;
	{
	}

	private String[] docs = new String[] {
			"The North Koreans tend to display unpredictable and peculiar behavior in talks about the future of their country. They constantly switch their point of views, reasons, and opinions for no apparent reason. The most prominent hypothesis on why they behave this way is that it tends to give them the upper hand in negotiations and never lets the other parties know their next move. This is because they know and develop the western knowledge of the instability of North Korea. In other words, they don’t have much to lose by blundering the negotiations because the other countries are the ones who really want to engage and will always come crawling back due to the danger of the DPRK’s nuclear weapons.",

			"The primary reason the Kim Regime strongly wants to keep and develop nuclear weapons and other weapons of mass destruction (WMD) is because it ensures their safety. In combination with their unpredictable nature discussed in Document A, this makes sure that no other countries such as the United States of America or South Korea invade them. Due to the decades of research, development, and extreme funding that the DPRK has put into these weapons, it is logical that they wouldn’t give them up easily, especially since they are the main reason for their continued existence. Experts estimate that North Korea spends hundreds of millions dollars each year to upgrade their nuclear weapons alone. This military development has been pushed for decades including during the 1990s North Korean Great Famine where up to millions of their approximately 22 million population died. Nuclear weapons have become extreme national pride for North Korea, with the country being one of the very small handful of countries on earth to have access to nuclear weapons and it is strongly invested in their future and defense of the nation.",

			"North Korea has very limited foreign relations, especially after the fall of the Soviet Union and its eastern bloc. Currently the biggest allies or backers of North Korea include China and Russia, however trade has actually expanded to other countries such as South Korea, Japan, and even India. This is somewhat surprising considering North Korea’s seclusion from the rest of the world and their infamous Juche Ideology. This ideology is extremely prominent in North Korea and is constantly pushed by the government. Essentially the message that the ideology conveys is that North Koreans are the greatest people on earth and their country is the greatest and most prosperous in the world. Therefore, since they are the greatest, they can and should rely on themselves to conserve this strength. This leads to many complications with foreign aid and assistance because not only are they skeptical of whether the aid is legitimate, but also have a strong belief system of self reliance and independence.",

			"North Korean infrastructure has had a hard time over the last few decades with it in decline since the 1990s. Main sources of electricity come from hydroelectricity and coal, while loss and lack of power/electricity is common. In the past, North Korea has been open for exchanges involving power or power plants, so keep this in mind while negotiating.",

			"North Korea is infamous for its widespread poverty. It is estimated that the majority of the North Korean population lives in poverty, however the DPRK government doesn’t acknowledge this. Food shortages and power shortages are common, partly due to the failed communist economic systems and the suppression of markets. The North Korean Public Distribution System is in charge of distributing food and other resources, but to survive many North Koreans have been forced to grow their own food or buy it from the North Korean Black Market due to the constant failure of this system.",

			"The DPRK government is extremely restrictive on the rights and freedoms of the North Korean population. Almost no one has any freedoms of speech, religion, expression, knowledge, or true democracy. People are constantly and inhumanly executed and imprisoned for the smallest of crimes including talking out against the government, trying to exit the country, listening or being in possession of outside information such as from the US or South Korea, or even practicing your own religion. There are brutal and horrific public execution and prison camps to strongly discourage any crimes or uprisings and there are many rumors about these places. The DPRK themselves have not released any information on these topics obviously, but there have been information leaked about the torture and extremely inhumane treatment in these so-called “North Korean Gulags.” There are several deaths from torture and starvation, however even more horrifying is the mass killing of infants and children that often occurs. Overall, while this is a horrible reality for the citizens of North Korea, however, all outsiders have been able to do is provide humanitarian aid such as food, water, and health care." };

	private String[] titles = new String[] { "Document A - Behavior in Negotiations", "Document B - Nuclear Weapons",
			"Document C - Foreign Relations", "Document D - Infrastructure and Power", "Document E - Poverty",
			"Document F - Human Rights Violations" };

	private MyButton nextButton = new MyButton("next");
	private MyButton backButton = new MyButton("back");

	{
		backButton.setVisible(false);
		nextButton.setBackground(Color.GREEN);
		backButton.setBackground(Color.yellow);
	}

	{
		this.add(nextButton);
		this.add(backButton);

		nextButton.setScale(0.7);
		backButton.setScale(0.7);

		nextButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				currentDoc++;

				if (currentDoc != 0)
					backButton.setVisible(true);

				if (currentDoc == docs.length - 1) {
					nextButton.setText("continue");
				}
				if (currentDoc >= docs.length) {
					getFrame().setScene(new DayTwoScene());
				} else
					ResearchScene.this.repaint();
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
		backButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				currentDoc = Math.max(0, currentDoc - 1);

				if (currentDoc == 0)
					backButton.setVisible(false);

				ResearchScene.this.repaint();

				nextButton.setText("next");
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

		backButton.setBorder(new LineBorder(Color.BLACK, 1));
		nextButton.setBorder(new LineBorder(Color.BLACK, 1));
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int width = getWidth();
				int height = getHeight();

				nextButton.setBounds(width * 15 / 20, height * 6 / 7, width * 2 / 10, height * 1 / 9);
				backButton.setBounds(width / 20, height * 6 / 7, width * 2 / 10, height * 1 / 9);
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

	int currentDoc = 0;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g = this.getTransformedGraphics(g);

		g.setColor(Color.BLACK);
		g.setFont(new Font("courier new", Font.BOLD, 22));
		Util.drawStringCentered(W / 2, H / 15, titles[currentDoc], g);
		g.setFont(new Font("courier new", Font.BOLD, 15));
		Util.drawStringWithLineBreaks(W / 8, H * 2 / 15, W * 6 / 8, docs[currentDoc], g);
	}
}
