package algorithm;

import java.awt.Point;
import java.util.ArrayList;

import simulate.Location;

public class AlgorithmUtilities {

	public static double calcDistance(Point p1, Point p2){
		return Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
	}

	public static double calcDistanceSum(ArrayList<Location> list){
		double distanceSum = 0;
		for(int j=0; j<list.size(); j++){
			Point p1 = list.get(j).getPoint();
			Point p2 = list.get( ((j+1)>=list.size()) ? 0 : j+1).getPoint();
			distanceSum += AlgorithmUtilities.calcDistance(p1, p2);
		}
		return distanceSum;
	}

	//if p1-p2 path corsses p3-p4 path, return true
	public static boolean isIntersect(Point p1, Point p2, Point p3, Point p4){
		  int ta = (p3.x - p4.x) * (p1.y - p3.y) + (p3.y - p4.y) * (p3.x - p1.x);
		  int tb = (p3.x - p4.x) * (p2.y - p3.y) + (p3.y - p4.y) * (p3.x - p2.x);
		  int tc = (p1.x - p2.x) * (p3.y - p1.y) + (p1.y - p2.y) * (p1.x - p3.x);
		  int td = (p1.x - p2.x) * (p4.y - p1.y) + (p1.y - p2.y) * (p1.x - p4.x);
		  return tc * td < 0 && ta * tb < 0;
	}

	public static boolean isInSameLine(Point p1, Point p2, Point p3){
		return ((p1.y * (p2.x - p3.x)) + (p2.y * (p3.x - p1.x)) + (p3.y * (p1.x - p2.x)) == 0);
	}

}
