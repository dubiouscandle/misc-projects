package usaco_algorithms;

import java.util.List;

public interface State<T extends State<T>> extends Cloneable {
	public List<T> getNext();

	public boolean isGoalState();

	public abstract boolean equals();

	public abstract int hashCode();
}
