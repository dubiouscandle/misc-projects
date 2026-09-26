package archive;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class FencePlan {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("fenceplan.in"));
		PrintWriter out = new PrintWriter(new File("fenceplan.out"));

		int n = in.nextInt();
		int m = in.nextInt();

		Point[] nodes = new Point[n];
		HashMap<Point, ArrayList<Point>> edges = new HashMap<>();

		for (int i = 0; i < n; i++) {
			nodes[i] = new Point(in.nextInt(), in.nextInt());
			edges.put(nodes[i], new ArrayList<>());
		}

		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;

			edges.get(nodes[a]).add(nodes[b]);
			edges.get(nodes[b]).add(nodes[a]);
		}

		in.close();

		ArrayList<ArrayList<Point>> components = new ArrayList<>();

		HashSet<Point> seen = new HashSet<>();

		for (int i = 0; i < n; i++) {
			Point node = nodes[i];

			if (seen.contains(node))
				continue;

			ArrayList<Point> component = new ArrayList<>();
			Stack<Point> stack = new Stack<>();
			stack.add(node);
			seen.add(node);
			while (stack.size() > 0) {
				Point cur = stack.pop();
				component.add(cur);

				for (Point adj : edges.get(cur)) {
					if (seen.contains(adj))
						continue;

					stack.add(adj);
					seen.add(adj);
				}
			}

			components.add(component);
		}

		int minPerim = Integer.MAX_VALUE;
		for (ArrayList<Point> component : components) {
			int xMin = Integer.MAX_VALUE;
			int xMax = Integer.MIN_VALUE;
			int yMin = Integer.MAX_VALUE;
			int yMax = Integer.MIN_VALUE;

			for (Point p : component) {
				if (p.x > xMax) {
					xMax = p.x;
				}
				if (p.x < xMin) {
					xMin = p.x;
				}
				if (p.y > yMax) {
					yMax = p.y;
				}
				if (p.y < yMin) {
					yMin = p.y;
				}
			}

			int perim = 2 * (xMax - xMin + yMax - yMin);
//			System.out.println(perim);
			
			if(perim < minPerim)
				minPerim = perim;
		}

//		System.out.println(components);
		out.println(minPerim);
		out.flush();
	}

}
