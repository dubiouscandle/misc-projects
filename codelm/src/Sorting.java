import java.util.Arrays;

public class Sorting {
	public static void main(String[] args) {
		String[][] dinosaurs = 
			{{"Bob", "120", "271"}, {"Frank", "999", "5000"}, {"Gertrude", "52", "9"}};

		int[] values = new int[dinosaurs.length];

		for (int i = 0; i < dinosaurs.length; i++) {
			String[] dino = dinosaurs[i];

			String name = dino[0];
			String age = dino[1];
			String height = dino[2];

			// vowels - consonants
			int value = 0;

			for (char c : name.toCharArray()) {
				if (isVowel(c)) {
					value--;
				} else {
					value++;
				}
			}

			// multiply by reverse of the age
			value *= Integer.parseInt(new StringBuilder(age).reverse().toString());

			// if height is perfect square, mul by sqrt height, else mul by highest digit
			double sqrtHeight = Math.sqrt(Integer.parseInt(height));

			if (sqrtHeight % 1 == 0) {//perfect square
				value *= sqrtHeight;
			} else {
				int maxDigit = -1;
				
				for(char c : height.toCharArray()) {
					int digit = Integer.parseInt("" + c);
					if(digit > maxDigit) {
						maxDigit = digit;
					}
				}
				
				value *= maxDigit;
			}

			values[i] = value;
		}
		
		Arrays.sort(values);
		
		long sum = 0;
		
		for(int i = 0; i < values.length; i++) {
			sum += (i+1) * values[i];
		}
		
		System.out.println(sum);

	}

	public static boolean isVowel(char c) {
		c = Character.toLowerCase(c);

		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
	}

}
