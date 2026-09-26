package war_card_game_sim;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameSimulator {
	public static void main(String[] args) {
		Queue<Card> player1 = new LinkedList<>();
		Queue<Card> player2 = new LinkedList<>();

		for (byte i = 1; i <= 13; i++) {
			player1.add(new Card(i, (byte) 0));
			player1.add(new Card(i, (byte) 1));
			player1.add(new Card(i, (byte) 2));
			player1.add(new Card(i, (byte) 3));
		}

		Collections.shuffle((LinkedList<?>) player1);

		for (int i = 0; i < 26; i++) {
			player2.add(player1.poll());
		}

		while (!player1.isEmpty() && !player2.isEmpty()) {
			Card card1 = player1.poll();
			Card card2 = player2.poll();

			System.out.println(card1 + " " + card2);

			if (card1.value > card2.value) {
				player1.add(card1);
				player1.add(card2);
			} else if (card1.value < card2.value) {
				player2.add(card2);
				player2.add(card1);
			} else {
				Stack<Card> hold = new Stack<>();

			}


		}

	}

	public static class Card {
		public static final byte CLUBS = 0;
		public static final byte DIAMONDS = 1;
		public static final byte HEARTS = 2;
		public static final byte SPADES = 3;

		private static final char[] SYMBOLS = new char[] { '♣', '◆', '♥', '♠' };

		public byte value;
		public byte suite;

		public Card(byte value, byte suite) {
			super();
			this.value = value;
			this.suite = suite;
		}

		@Override
		public String toString() {
			return "" + value + SYMBOLS[suite];
		}

	}
}
