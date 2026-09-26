package experiments.digits;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import com.dubiouscandle.dubiousdl.ActivationFunction;
import com.dubiouscandle.dubiousdl.Adam;
import com.dubiouscandle.dubiousdl.Batch;
import com.dubiouscandle.dubiousdl.Initializer;
import com.dubiouscandle.dubiousdl.LossFunction;
import com.dubiouscandle.dubiousdl.Matrix;
import com.dubiouscandle.dubiousdl.Model;

public class DigitTraining {
	public static void main(String[] args) throws IOException {
		Random random = new Random();

		ActivationFunction[] activations = { 
				ActivationFunction.LEAKY_RELU, 
				ActivationFunction.LEAKY_RELU, 
				ActivationFunction.LEAKY_RELU,
				ActivationFunction.LEAKY_RELU, ActivationFunction.IDENTITY, };

		int[] layerSizes = { 784, 256, 128, 64, 32, 10 };
		final int batchSize = 128;

		Batch batch = new Batch(DigitLoader.X_TRAIN, DigitLoader.Y_TRAIN, DigitLoader.Y_TRAIN.length, 10, 28 * 28,
				batchSize, random);

		Batch test = new Batch(DigitLoader.X_TEST, DigitLoader.Y_TEST, DigitLoader.Y_TEST.length, 10, 28 * 28,
				batchSize, random);

		Model model = new Model(activations, layerSizes, batchSize, new Initializer.HeNormal(random));
		Adam op = new Adam(model, LossFunction.SOFT_MAX_CROSS_ENTROPY_LOSS, 0.9f, .999f);

		final int MAX_STEPS = 60_000;
		final int PATIENCE = 30;
		final int PRINT_RATE = 50;
		int patienceUsed = 0;
		float minLoss = Float.POSITIVE_INFINITY;

		for (int step = 0; step < MAX_STEPS; step++) {
			batch.next();
			op.step(batch.input(), batch.target(), 0.001f);

			if (step % PRINT_RATE == 0) {
				test.next();
				Matrix output = Matrix.emptyCopyOf(test.target());
				model.forwardPropagate(test.input(), output);

				float loss = LossFunction.SOFT_MAX_CROSS_ENTROPY_LOSS.getLoss(output, test.target());

				if (loss < minLoss) {
					minLoss = loss;
					patienceUsed = 0;
				} else {
					patienceUsed++;
				}

				if (patienceUsed >= PATIENCE) {
					break;
				}

				System.out.println(step + " " + loss);
			}
		}

		Matrix output = new Matrix(10, batchSize);
		Matrix input = batch.input();
		model.forwardPropagate(input, output);

		for (int i = 0; i < 10; i++) {
			float[] digitImage = new float[28 * 28];

			for (int j = 0; j < 28 * 28; j++) {
				digitImage[j] = input.get(j, i);
			}
			printDigit(digitImage);

			float[] outputArr = new float[10];
			for (int j = 0; j < 10; j++) {
				outputArr[j] = output.get(j, i);
			}

			ActivationFunction.softmax(outputArr);

			for (int j = 0; j < 10; j++) {
				System.out.println(j + " " + outputArr[j]);
			}
		}

		try {
			ObjectOutputStream o = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream("src/experiments/digits/digitrecognizer.ser")));
			o.writeObject(model);
			o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void printDigit(float[] digit) {
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 28; j++) {
				float x = 1 - digit[i * 28 + j];
				String grad = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
				out.append(grad.charAt(Math.min(grad.length() - 1, (int) (x * grad.length()))));
			}
			out.append('\n');
		}

		System.out.print(out);
	}
}
