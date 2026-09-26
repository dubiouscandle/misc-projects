package usaco_algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSGameSolver<T extends State<T>> {
	private Queue<T> queue = new LinkedList<>();
	private HashSet<T> seen = new HashSet<>();

	public BFSGameSolver(T initialState) {
		queue.add(initialState);
		seen.add(initialState);
	}

	public T findGoal() {
		while (!queue.isEmpty()) {
			T cur = queue.poll();

			if (cur.isGoalState())
				return cur;

			List<T> nextList = cur.getNext();

			for (int i = 0; i < nextList.size(); i++) {
				T next = nextList.get(i);

				if (seen.contains(next))
					continue;

				seen.add(next);
				queue.add(next);
			}
		}

		return null;
	}

}
