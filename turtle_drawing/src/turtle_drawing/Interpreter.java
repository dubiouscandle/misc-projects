package turtle_drawing;

import java.util.Scanner;

public class Interpreter {
	private Drawer drawer;

	public Interpreter(Drawer drawer) {
		this.drawer = drawer;
	}

	public void run(String script) {
		try (Scanner s = new Scanner(script)) {
			while (s.hasNext()) {
				String next = s.next();

				if (next.equals("MOVE_TO")) {
					drawer.moveTo(s.nextFloat(), s.nextFloat());
				} else if (next.equals("MOVE")) {
					drawer.move(s.nextFloat(), s.nextFloat());
				} else if (next.equals("PEN_UP")) {
					drawer.setPen(false);
				} else if (next.equals("PEN_DOWN")) {
					drawer.setPen(true);
				} else if (next.equals("SET_COLOR")) {
					drawer.setColor(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
				} else {
					throw new RuntimeException("bruh");
				}
			}
		}
	}

	/*
	 * SYNTAX IDEAS: MOVE_TO x y MOVE x y PEN_UP PEN_DOWN
	 * 
	 * 
	 */
}
