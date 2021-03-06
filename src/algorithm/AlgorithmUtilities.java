package algorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

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

	/**
	 * calcDistanceBetweenPointAndLineSegmenta
	 * @param p1 : line start point
	 * @param p2 : line end point
	 * @param p3 : point
	 * @return
	 */
	public static double calcDistanceBetweenPointAndLineSegmenta(Point p1, Point p2, Point p3){
		double D = Math.abs(((p3.x-p1.x)*(p2.y-p1.y)) - ((p3.y-p1.y)*(p2.x-p1.x)));
		double L = calcDistance(p1, p2);
		double H = D/L;
		if(dot(p1, p2, p3)<0) return calcDistance(p1, p3);
		if(dot(p2, p1, p3)<0) return calcDistance(p2, p3);
		return H;
	}

	/**
	 * dot
	 * @param p1 origin
	 * @param p2
	 * @param p3
	 * @return
	 */
	public static int dot(Point p1, Point p2, Point p3){
		return ((p3.x-p1.x)*(p2.x-p1.x)) + ((p3.y-p1.y)*(p2.y-p1.y));
	}

	/**
	 * isIntersect
	 * if p1-p2 path crosses p3-p4 path, return true
	 * @return
	 */
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

	/**
	 * isOnLine
	 * @param p1 : line start point
	 * @param p2 : line end point
	 * @param p3 : Judged point
	 * @return
	 */
	public static boolean isOnLine(Point p1, Point p2, Point p3){
		double L1 = calcDistance(p1, p2);
		double L2 = calcDistance(p1, p3);
//		System.out.println(L1);
//		System.out.println(L2);

		double j1 = ((p2.x-p1.x)*(p3.x-p1.x)) + ((p2.y-p1.y)*(p3.y-p1.y));
		double j2 = L1*L2;
//		System.out.println(j1);
//		System.out.println(j2);
//		System.out.println("Math.abs(j2-j1) = " + Math.abs(j2-j1));
		return (Math.abs(j1-j2)<=0.0001) && (L1>=L2);
	}

	public static class IndexSet{
		public int index1;
		public int index2;
		public IndexSet(int index1, int index2){
			this.index1 = index1;
			this.index2 = index2;
		}
	}

	public static IndexSet search2optIndex(ArrayList<Location> list){
		// 1. search intersect path
		for(int i=0; i<list.size(); i++){
			int basePathP1Index = i;
			int basePathP2Index = ((i+1)>=list.size()) ? 0 : i+1;
			Point basePathP1 = list.get(basePathP1Index).getPoint();
			Point basePathP2 = list.get(basePathP2Index).getPoint();
			for(int j=0; j<list.size(); j++){
				int comparedPathP1Index = j;
				int comparedPathP2Index =((j+1)>=list.size()) ? 0 : j+1;
				Point comparedPathP1 = list.get(comparedPathP1Index).getPoint();
				Point comparedPathP2 = list.get(comparedPathP2Index).getPoint();
				if(basePathP1.equals(comparedPathP1)
						|| basePathP1.equals(comparedPathP2)
						|| basePathP2.equals(comparedPathP1)
						|| basePathP2.equals(comparedPathP2)){
					continue;
				}

				// 2. if intersected, return Path between basePointP2 to comparedPointP1
				if(isIntersect(basePathP1, basePathP2, comparedPathP1, comparedPathP2)){
//					System.out.println("cross Path : " + basePathP1Index + "---" + basePathP2Index + " x " + comparedPathP1Index + "---" + comparedPathP2Index);
					return new IndexSet(basePathP2Index, comparedPathP1Index);
				}
			}
		}

		// 3. if not crossing, return null
		return null;
	}

	public static IndexSet searchIndexIntersectWithPath(ArrayList<Location> list){
		// 1. search point which is on line
		for(int i=0; i<list.size(); i++){
			int basePathP1Index = i;
			int basePathP2Index = ((i+1)>=list.size()) ? 0 : i+1;
			Point basePathP1 = list.get(basePathP1Index).getPoint();
			Point basePathP2 = list.get(basePathP2Index).getPoint();

			for(int searchPointIndex=0; searchPointIndex<list.size(); searchPointIndex++){
				Point searchPoint = list.get(searchPointIndex).getPoint();

				if(searchPoint.equals(basePathP1) || searchPoint.equals(basePathP2)){
					continue;
				}

				if(AlgorithmUtilities.isOnLine(basePathP1, basePathP2, searchPoint)){
//					System.out.println("Point : " + searchPointIndex + " is on " + basePathP1Index + "---" + basePathP2Index);
					return new IndexSet(basePathP2Index, searchPointIndex);
				}

			}

		}
		return null;
	}

	/**
	 * searchIndexToMoveToNearestLineSegamenta
	 * @param list
	 * @return IndexSet -- index1 is toMoveLocationIndex, index2 is inseart Location Index
	 */
	public static IndexSet searchIndexToMoveToNearestLineSegamenta(ArrayList<Location> list){
		// 1. search objective line
		for(int i=0; i<list.size(); i++){
			int basePathP1Index = i;
			int testPointIndex = ((i+1)>=list.size()) ? 0 : i+1;
			int basePathP2Index = ((i+2)>=list.size()) ? ((i+2)-list.size()) : i+2;
			Point basePathP1 = list.get(basePathP1Index).getPoint();
			Point testPoint = list.get(testPointIndex).getPoint();
			Point basePathP2 = list.get(basePathP2Index).getPoint();
			double currentDistanceBetweenPointToLineSegmenta = calcDistanceBetweenPointAndLineSegmenta(basePathP1, basePathP2, testPoint);
			//System.out.println("lineSegmenta "+ basePathP1Index + "---" + basePathP2Index + " to " + testPointIndex + " disntance is " + currentDistanceBetweenPointToLineSegmenta);
			for (int searchIndex=0; searchIndex<list.size(); searchIndex++){
				int comparedPathP1Index = searchIndex;
				int comparedPathP2Index =((searchIndex+1)>=list.size()) ? 0 : searchIndex+1;
				Point comparedPathP1 = list.get(comparedPathP1Index).getPoint();
				Point comparedPathP2 = list.get(comparedPathP2Index).getPoint();
				if(basePathP1.equals(comparedPathP1)
						|| basePathP1.equals(comparedPathP2)
						|| basePathP2.equals(comparedPathP1)
						|| basePathP2.equals(comparedPathP2)){
					continue;
				}
				double comparedDistanceBetweenPointToLineSegmenta = calcDistanceBetweenPointAndLineSegmenta(comparedPathP1, comparedPathP2, testPoint);
				//System.out.println("lineSegmenta "+ comparedPathP1Index + "---" + comparedPathP2Index + " to " + testPointIndex + " disntance is " + comparedDistanceBetweenPointToLineSegmenta);
				if(comparedDistanceBetweenPointToLineSegmenta<currentDistanceBetweenPointToLineSegmenta){
//					System.out.println("lineSegmenta "+ basePathP1Index + "---" + basePathP2Index + " to " + testPointIndex + " disntance is " + currentDistanceBetweenPointToLineSegmenta);
//					System.out.println("lineSegmenta "+ comparedPathP1Index + "---" + comparedPathP2Index + " to " + testPointIndex + " disntance is " + comparedDistanceBetweenPointToLineSegmenta);
//					System.out.println("basePathP1 : " + basePathP1);
//					System.out.println("basePathP2 : " + basePathP2);
//					System.out.println("testPoint : " + testPoint);
//					System.out.println("comparedPathP1" + comparedPathP1);
//					System.out.println("comparedPathP2" + comparedPathP2);
//					System.out.println("so, move");
					return new IndexSet(testPointIndex, comparedPathP2Index);
				}
			}
		}
		return null;
	}

	public static void applyToMoveIndexSet(ArrayList<Location> list, IndexSet is){
		Location toMoveLocation = list.remove(is.index1);
		list.add(is.index2, toMoveLocation);

	}

	public static void reverse(ArrayList<Location> list, int startIndex, int endIndex){
		// 1. make toReverseList
		ArrayList<Location> toReverseList = new ArrayList<Location>();
		int k = startIndex;
		while(true){
			toReverseList.add(list.get(k));

			if(k==endIndex) break;

			if(++k>=list.size()) k = 0;
		}

		// 2. reverse
		Collections.reverse(toReverseList);

		// 3. set reverseList
		k = startIndex;
		int l = 0;
		while(true){
			list.set(k, toReverseList.get(l));

			if(k==endIndex) break;

			if(++k>=list.size()) k = 0;
			++l;
		}
	}

	public static void reverse(ArrayList<Location> list, IndexSet is){
		reverse(list, is.index1, is.index2);
	}

}
