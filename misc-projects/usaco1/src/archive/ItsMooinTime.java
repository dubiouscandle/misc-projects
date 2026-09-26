package archive;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ItsMooinTime {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int length = s.nextInt();
		int freq = s.nextInt();

		String string = s.next();

		HashMap<String, Integer> frequencies = new HashMap<>();
		HashMap<String, List<Integer>> ranges = new HashMap<>();
		List<String> possibleMoos = new ArrayList<>();

		// first loop through to count wihtout any edits
		for (int i = 1; i < length - 1; i++) {
			char prev = string.charAt(i - 1);
			char cur = string.charAt(i);
			char next = string.charAt(i + 1);

			if (cur == next && prev != cur) {
				String moo = "" + prev + cur + next;
				if (!frequencies.containsKey(moo)) {
					frequencies.put(moo, 1);
					ranges.put(moo, new ArrayList<>());
					ranges.get(moo).add(i - 1);
					possibleMoos.add(moo);
				} else {
					frequencies.put(moo, frequencies.get(moo) + 1);
					ranges.get(moo).add(i - 1);
				}
			}
		}

		{
			Iterator<String> iterator = possibleMoos.iterator();

			while (iterator.hasNext()) {
				String moo = iterator.next();

				if (frequencies.get(moo) <= freq - 2) {
					iterator.remove();
				}
			}
		}

		Iterator<String> iterator = possibleMoos.iterator();
		while (iterator.hasNext()) {
			String moo = iterator.next();

			if (frequencies.get(moo) >= freq) {
				continue;
			}

			boolean canStay = false;

			outer: for (int i = 1; i < length - 1; i++) {
				if (ranges.containsKey(moo))
					for (int x : ranges.get(moo)) {
						if (!(i + 1 < x || i - 1 > x + 2)) {
							continue outer;
						}
					}

				char prev = string.charAt(i - 1);
				char cur = string.charAt(i);
				char next = string.charAt(i + 1);

				int similarity = 0;

				if (prev == moo.charAt(0))
					similarity++;
				if (cur == moo.charAt(1))
					similarity++;
				if (next == moo.charAt(2))
					similarity++;

				if (similarity == 2) {// found one edit
					canStay = true;
					break;
				}
			}

			if (!canStay) {
				iterator.remove();
			}
		}

		if (freq == 1) {
			for (int i = 1; i < length - 1; i++) {
				char prev = string.charAt(i - 1);
				char cur = string.charAt(i);
				char next = string.charAt(i + 1);

				if (cur == next)
					for (char j = 'a'; j <= 'z'; j++) {
						if (!frequencies.containsKey("" + j + cur + cur)) {
							if (j == cur)
								continue;
							possibleMoos.add("" + j + cur + cur);
						}
					}
			}
		}

		Collections.sort(possibleMoos);

		System.out.println(possibleMoos.size());
		for (int i = 0; i < possibleMoos.size(); i++) {
			System.out.println(possibleMoos.get(i));
		}
		
		s.close();
	}

}
