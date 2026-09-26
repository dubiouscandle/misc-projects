package archive;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PalindromeGame {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		for (int i = Byte.parseByte(in.readLine()); i >= 1; i--) {
			String line = in.readLine();
			System.out.println(line.charAt(line.length() - 1) == '0' ? 'E' : 'B');
		}
	}

}
