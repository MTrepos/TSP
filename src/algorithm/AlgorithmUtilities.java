package algorithm;

import java.awt.Point;

public class AlgorithmUtilities {

	public static double calcDistance(Point p1, Point p2){
		return Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
	}

}
