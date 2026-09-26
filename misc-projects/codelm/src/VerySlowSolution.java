import java.util.ArrayList;
import java.util.HashMap;

public class VerySlowSolution {
	public static HashMap<Integer, ArrayList<Integer>[]> partitionCache = new HashMap<>();
	
	public static void main(String[] args) {

	}

	public static void solution(int c, int o, int h) {
		long permutation = 0;
	}
	
	public static ArrayList<Integer>[] partitions(int n){
		if(partitionCache.containsKey(n)) {
			return partitionCache.get(n);
		}
		
		for(int i = 1; i <= n - 1; i++) {
			
		}
	}
}
