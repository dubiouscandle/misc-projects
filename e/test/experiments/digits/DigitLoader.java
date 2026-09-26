package experiments.digits;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DigitLoader {
	public static final float[][] X_TRAIN;
	public static final int[] Y_TRAIN;

	public static final float[][] X_TEST;
	public static final int[] Y_TEST;

	static {
		X_TRAIN = (float[][]) readObj(new File("src/experiments/digits/x_train.ser"));
		Y_TRAIN = (int[]) readObj(new File("src/experiments/digits/y_train.ser"));
		
		X_TEST = (float[][]) readObj(new File("src/experiments/digits/x_test.ser"));
		Y_TEST = (int[]) readObj(new File("src/experiments/digits/y_test.ser"));
	}

	public static Object readObj(File file) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
