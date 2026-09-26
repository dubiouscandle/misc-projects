package util;

public class Util {
	public static int round(double x) {
		return (int)Math.round(x);
	}

	public static double dist(double x0, double y0, double x1, double y1) {
		return Math.sqrt(Math.pow(x0-x1, 2) + Math.pow(y0-y1, 2));
	}
	
	public static double constrain(double x, double min, double max) {
		if(x < min) {
			return min;
		}else if(x > max){
			return max;
		}else {
			return x;
		}
	}
}
