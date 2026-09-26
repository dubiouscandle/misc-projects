package pch;

import com.jmathanim.jmathanim.JMathAnimScene;
import com.jmathanim.jmathanim.Scene2D;
import com.jmathanim.mathobjects.Shape;

public class myFirstScene extends Scene2D {

	@Override
	public void setupSketch() {
	}

	@Override
	public void runSketch() throws Exception {
		Shape s = Shape.square();
		play.showCreation(s);
		play.rotate(1, 30 * DEGREES, s);
		waitSeconds(5);
	}

	public static void main(String[] args) {
		JMathAnimScene scene = new myFirstScene();
		scene.execute();
	}
}