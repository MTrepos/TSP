package algorithm.clustering;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import simulate.Location;

public class Kmean implements ClusteringAlgorithm {

	@Override
	public HashMap<Location, Integer> cluster(ArrayList<Location> list, int k) {
		
		//make HashMap, and put initialize cluster
		HashMap<Location, Integer> locationMap = new HashMap<Location, Integer>(list.size());
		for(int i=0; i<list.size(); i++){
			locationMap.put(list.get(i), i%k);
		}
		
		//calculate means until beforeMeanPoints are same as meanPointList
		Point meanPoint[] = null, beforeMeanPoint[] = null;
		do {
			// 1. set beforeMeanPoint
			beforeMeanPoint = meanPoint;

			// 2. calculate means
			meanPoint = calcMeanPoints(locationMap, k);

			// 3. re-classify Points under its nearest mean
			classify(locationMap, k, meanPoint);

		} while(!isMeanPointSame(k, beforeMeanPoint, meanPoint));
		
		return locationMap;
	}	
	
	private Point[] calcMeanPoints(HashMap<Location, Integer> locationMap ,int k){

		Point meanPoint[] = new Point[k];

		int elementNum[] = new int[k]; // For example, elementNum[1] = 30, the Point that key is "1" is 30.
		long xSum[] = new long[k]; //for calculating avg
		long ySum[] = new long[k];
		for(int i=0; i<k; i++){
			elementNum[i] = 0;
			xSum[i] = 0;
			ySum[i] = 0;
		}

		for(Location l : locationMap.keySet()){
			int index = locationMap.get(l);
			elementNum[index]++;
			xSum[index] += l.getPoint().x;
			ySum[index] += l.getPoint().y;
		}

		int avgX[] = new int[k];
		int avgY[] = new int[k];
		for(int i=0; i<k; i++){
			if(elementNum[i] != 0){
				avgX[i] = Double.valueOf(xSum[i]/elementNum[i]).intValue();
				avgY[i] = Double.valueOf(ySum[i]/elementNum[i]).intValue();
				meanPoint[i] = new Point(avgX[i], avgY[i]);
			}else {
				meanPoint[i] = new Point(0, 0);
			}
			//System.out.println("Mean[" + i + "] = (" + meanPoint[i].x + ", " + meanPoint[i].y + ")" );
		}

		return meanPoint;
	}

	private void classify(HashMap<Location, Integer> locationMap, int k, Point meanPoint[]){
		for(Location l : locationMap.keySet()){
			int nearestClusterIndex = 0;
			double nearestClusterDistance = Double.MAX_VALUE;
			for(int i=0; i<k; i++){
				double distance = calcDistance(l.getPoint(), meanPoint[i]);
				if(distance < nearestClusterDistance){
					nearestClusterIndex = i;
					nearestClusterDistance = distance;
				}
			}
			locationMap.put(l, nearestClusterIndex); //Overwrite cluster into nearest Point's cluster
		}
	}

	private double calcDistance(Point p1, Point p2){
		return Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
	}

	private boolean isMeanPointSame(int k, Point beforeMeanPoint[], Point afterMeanPoint[]){

		if ((beforeMeanPoint == null) || (afterMeanPoint == null)){
			return false;
		}

		for(int i=0; i<k; i++){
			if(!beforeMeanPoint[i].equals(afterMeanPoint[i])){
				return false;
			}
		}

		return true;
	}	
	@Override
	public String toString(){
		return "Kmean";
	}

}
