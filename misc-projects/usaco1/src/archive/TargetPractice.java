package archive;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;

public class TargetPractice {
	static int t, c;
	static HashSet<Integer> targets = new HashSet<>();
	static String commands;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		t = in.nextInt();
		c = in.nextInt();

		if (t == 0 || c == 0) {
			System.out.println(0);
			return;
		}

		for (int i = 0; i < t; i++) {
			targets.add(in.nextInt());
		}

		commands = in.next();

		// time to target
		HashMap<Integer, Integer> timesN2 = getTimes(-2);
		HashMap<Integer, Integer> timesN1 = getTimes(-1);
		HashMap<Integer, Integer> timesP1 = getTimes(1);
		HashMap<Integer, Integer> timesP2 = getTimes(2);

		// available targets for each
		HashSet<Integer> targetsN2 = new HashSet(timesN2.values());
		HashSet<Integer> targetsN1 = new HashSet(timesN1.values());
		HashSet<Integer> targetsP1 = new HashSet(timesP1.values());
		HashSet<Integer> targetsP2 = new HashSet(timesP2.values());

		int max = 0;
		int x = 0;
		HashSet<Integer> targetsHit = new HashSet<>();
		for (int i = 0; i < c; i++) {
			if (timesN2.containsKey(i))
				targetsN2.remove(timesN2.get(i));
			if (timesN1.containsKey(i))
				targetsN1.remove(timesN1.get(i));
			if (timesP1.containsKey(i))
				targetsP1.remove(timesP1.get(i));
			if (timesP2.containsKey(i)) {
				targetsP2.remove(timesP2.get(i));
			}

			char command = commands.charAt(i);

			if (command == 'L') {
				int alt1 = targetsP1.size() + targetsHit.size();
				if (targets.contains(x) && !targetsP1.contains(x) && !targetsHit.contains(x))
					alt1++;

				int alt2 = targetsP2.size() + targetsHit.size();

				max = Math.max(max, Math.max(alt1, alt2));
				x--;
			} else if (command == 'R') {
				int alt1 = targetsN1.size() + targetsHit.size();
				if (targets.contains(x) && !targetsN1.contains(x) && !targetsHit.contains(x))
					alt1++;

				int alt2 = targetsN2.size() + targetsHit.size();

				max = Math.max(max, Math.max(alt1, alt2));
				x++;
			} else if (command == 'F') {
				int alt1 = targetsP1.size() + targetsHit.size();
				int alt2 = targetsN1.size() + targetsHit.size();

				max = Math.max(max, Math.max(alt1, alt2));

				if (targets.contains(x)) {
					targetsHit.add(x);
					targetsN2.remove(x);
					targetsN1.remove(x);
					targetsP1.remove(x);
					targetsP2.remove(x);
				}

			}
		}
		
		max = Math.max(max, targetsHit.size());

		System.out.println(max);
	}

	private static HashMap<Integer, Integer> getTimes(int x) {
		HashMap<Integer, Integer> lastTimes = new HashMap<>();
		HashMap<Integer, Integer> times = new HashMap<>();

		for (int i = 0; i < c; i++) {
			char command = commands.charAt(i);

			if (command == 'L')
				x--;
			else if (command == 'R')
				x++;
			else if (targets.contains(x)) {
				lastTimes.put(x, i);
			}
		}

		for (Entry<Integer, Integer> entry : lastTimes.entrySet()) {
			times.put(entry.getValue(), entry.getKey());
		}

		return times;
	}

}
