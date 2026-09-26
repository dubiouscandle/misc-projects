/* Problem 6
 * 
 * An abbreviation is a shortened form of a word or phrase. In this problem, you must write an automated tool that replaces a sequence of capitalized
 * words with the corresponding abbreviation, which consists of the first upper case letters only.
 * 
 * Definitions:
 * 1. A word is defined as a sequence of lower and upper case English letters only.
 * 2. A capitalized word is a word that starts with an upper case letter followed by one or more lower case letters.
 * For example:
 *    Capitalized words: "Ab", "Abc", "Abcd, "Abcde"
 *    Non-capitalized words: "ab", "A", "AB", "ABc", "AbC", "I"
 * 3. An abbreviatable sequence is a sequence of two or more capitalized words that are separated by exactly one space.
 * 4. An abbreviation of an abbreviatable sequence consists of the first upper case letter of each capitalized word, followed by a single space.
 * 
 * Input: 
 * A string of characters consisting of words, spaces, and punctuation.
 * Output:
 * String of characters, with all abbreviatable sequences replaced by an abbreviation keeping all not abbreviatable sequences and punctuation without change.
 * An abbreviation is only formed when there is a valid sequence of two or more capitalized words
 * 
 * Example 1
 * Input: Central Intelligence Agency, Department Of Homeland Security
 * Output: CIA, DOHS
 * 
 * Example 2
 * Input: This is the Best. Contest. Ever. A Great Opportunity for all contestants.
 * Output: This is the Best. Contest. Ever. A GO for all contestants.
 * 
 * Example 3
 * Input: Oh    No    Extra Spaces. And, Punctuation Ruin Everything
 * Output: Oh    No    ES. And, PRE
 * */

package widener2024_solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem6 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		String sentence = s.nextLine();

		List<Chunk> chunks = new ArrayList<>();
		chunks.add(new Chunk());
		int currentChunkIndex = 0;

		boolean prevWasNotAlphabetic = false;

		for (int i = 0; i < sentence.length(); i++) {
			char currentChar = sentence.charAt(i);

			if (currentChar == ' ' || currentChar == ',' || currentChar == '.' || currentChar == ';') {
				currentChunkIndex++;
				prevWasNotAlphabetic = true;
				chunks.add(new Chunk());
			} else

			if (prevWasNotAlphabetic) {
				currentChunkIndex++;
				prevWasNotAlphabetic = false;
				chunks.add(new Chunk());
			}

			chunks.get(currentChunkIndex).value += currentChar;

		}

		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);

			if (chunk.value.equals(" ")) {
				chunk.id = SPACE;
			} else if (chunk.value.equals(",") || chunk.value.equals(".") || chunk.value.equals(";")) {
				chunk.id = PUNCTUATION;
			} else {
				if (!Character.isUpperCase(chunk.value.charAt(0))) {
					chunk.id = NON_CAPITAL_WORD;
				} else {
					chunk.id = CAPITAL_WORD;

					// check rest of letters if they are lower case
					for (int j = 1; j < chunk.value.length(); j++) {
						if (!Character.isLowerCase(chunk.value.charAt(j))) {
							chunk.id = NON_CAPITAL_WORD;
							break;
						}
					}

				}
			}
		}

		String output = "";
		String abbreviation = "";

		int capitalRun = 0;

		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);

			if (chunk.id == CAPITAL_WORD) {
				capitalRun++;
				abbreviation += chunk.value.charAt(0);
			} else {
				if (capitalRun == 0 || capitalRun == 1) {
					output += chunk.value;
				} else {
					output += abbreviation + " ";
				}

				abbreviation = "";
				capitalRun = 0;

			}
		}

		System.out.println(output);

	}

	public static class Chunk {
		String value = "";

		int id = -100;

	}

	public static final int CAPITAL_WORD = 1;
	public static final int NON_CAPITAL_WORD = 2;
	public static final int SPACE = 3;
	public static final int PUNCTUATION = 4;

}
