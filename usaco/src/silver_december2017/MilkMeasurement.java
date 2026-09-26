
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

public class MilkMeasurement {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("measurement.in"));
		PrintWriter out = new PrintWriter(new File("measurement.out"));
//		Scanner in = new Scanner(System.in);
//		PrintWriter out = new PrintWriter(new File("measurement.in"));

		int numRecords = in.nextInt();
		int g = in.nextInt();

		Record[] records = new Record[numRecords];

		for (int i = 0; i < numRecords; i++) {
			records[i] = new Record(in.nextInt(), in.nextInt(), in.nextInt());
		}

		in.close();

		Arrays.sort(records);

		HashMap<Integer, Cow> cowsHashMap = new HashMap<>();
		TreeSet<Cow> cows = new TreeSet<>();

		for (int i = 0; i < numRecords; i++) {
			Cow cow = new Cow(g, records[i].cowID);

			cowsHashMap.put(records[i].cowID, cow);
			cows.add(cow);
		}

		int numTimesChanged = 0;
		for (int i = 0; i < numRecords; i++) {
			Record r = records[i];

			Cow prevMax = cows.last();
			int prevMaxOutput = prevMax.output;

			Cow cur = cowsHashMap.get(r.cowID);
			int prevOutput = cur.output;
			cur.output += r.change;

			cows.remove(cur);
			cows.add(cur);

			// only changes if becomes one of the max or was one of the max and went down
			if (prevOutput >= prevMaxOutput && cur.output < prevMaxOutput) {
				numTimesChanged++;
			} else if (cur.output > prevMaxOutput) {// becomes max
				numTimesChanged++;
			} else if (cur.output == prevMaxOutput) {
				TreeSet<Cow> cowsClone = new TreeSet<>(cows);

				boolean wasMaxPreviously = false;

				while (!cowsClone.isEmpty() && cowsClone.last().output == prevMaxOutput) {
					Cow cow = cowsClone.pollLast();

					if (cur.equals(cow)) {
						wasMaxPreviously = true;
						break;
					}
				}

				if (!wasMaxPreviously)
					numTimesChanged++;
			}
		}

		out.println(numTimesChanged);
		out.flush();
	}

	public static class Cow implements Comparable<Cow> {
		private int output, id;

		@Override
		public String toString() {
			return id + " " + output;
		}

		public Cow idHash(int id) {
			return new Cow(0, id);
		}

		public Cow(int output, int id) {
			this.output = output;
			this.id = id;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, output);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cow other = (Cow) obj;

			return id == other.id;
		}

		@Override
		public int compareTo(Cow o) {
			if (this.output == o.output)
				return Integer.compare(id, o.id);

			return Integer.compare(this.output, o.output);
		}

	}

	public static class Record implements Comparable<Record> {
		private int day, cowID, change;

		public Record(int day, int cowID, int change) {
			super();
			this.day = day;
			this.cowID = cowID;
			this.change = change;
		}

		@Override
		public int compareTo(Record o) {
			return Integer.compare(this.day, o.day);
		}

	}
}
