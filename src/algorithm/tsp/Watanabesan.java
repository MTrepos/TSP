package algorithm.tsp;

import java.awt.Point;
import java.util.ArrayList;

import algorithm.AlgorithmUtilities;
import simulate.Location;

public class Watanabesan implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		double distanceSum = 0;

		for(int currentLocationIndex=0; currentLocationIndex<list.size()-1; currentLocationIndex++){

			// 1. deceide Base Location
			Location baseLocation = list.get(currentLocationIndex);

			// 2. search nearest Location
			int nearestLocationIndex = 0; //if nearest Location is found, this Index is updated..
			double distanceBetweenBaseAndNearest = Double.MAX_VALUE;
			for(int searchLocationIndex=currentLocationIndex+1; searchLocationIndex<list.size(); searchLocationIndex++){
				double distance = AlgorithmUtilities.calcDistance(baseLocation.getPoint(), list.get(searchLocationIndex).getPoint());
				//System.out.println("distance(" + currentLocationIndex + ", " + searchLocationIndex + ") : " + distance);
				if(distance < distanceBetweenBaseAndNearest){
					nearestLocationIndex = searchLocationIndex;
					distanceBetweenBaseAndNearest = distance;
				}
			}
			distanceSum += distanceBetweenBaseAndNearest;

			// 3. swap nearestLocation for currentLocation
			Location tmpLocation = list.set(currentLocationIndex+1, list.get(nearestLocationIndex)); //set : overwrite, return before value
			list.set(nearestLocationIndex, tmpLocation);

		}

		// 4. Modify list
		double avgDistance = distanceSum / list.size();
		System.out.println("average distance = " + avgDistance);
		for (int i = 0; i < list.size() - 1; i++) {
			// A. Seach Path longer than avg
			Point p1 = list.get(i).getPoint();
			Point p2 = list.get(i+1).getPoint();
			double distance = AlgorithmUtilities.calcDistance(p1, p2);
			if (distance > avgDistance) {

				System.out.println("OVER AVERAGE (" + p1.x + ", " + p2.x + ") to (" + p2.x + ", " + p2.y + ") distance = " + distance);
				System.out.println("RESEARCH ANOTHER POINT...");

				// B. Search Nearest Point
				int p3Index = 0;
				double nearestDistance = Double.MAX_VALUE;
				Point p3 = null;
				for (int j = 0; j < list.size() - 1; j++) {
					if(i != j){
						p3 = list.get(j).getPoint();
						double distanceP1ToP3 = AlgorithmUtilities.calcDistance(p1, p3);
						if(distanceP1ToP3 < distance){
							p3Index = j;
							nearestDistance = distanceP1ToP3;
						}
					}
				}
				System.out.println("NEAREST POINT : (" + p3.x + ", " + p3.y + ")");

				// C. Compare which path is shorter : (p3-1)to(p3) (p3)to(p3+1)
				int negativeIndex = (p3Index-1>0) ? p3Index-1 : list.size()-1;
				int positiveIndex = (p3Index+1>list.size()+1) ? p3Index+1 : 0;
				Point negativePoint = list.get(negativeIndex).getPoint();
				Point positivePoint = list.get(positiveIndex).getPoint();
				double path1 = AlgorithmUtilities.calcDistance(p3, negativePoint);
				double path2 = AlgorithmUtilities.calcDistance(p3, positivePoint);
				int nearestIndex = (path1 - path2>=0) ? negativeIndex : positiveIndex;

				Point nearestPoint = list.get(nearestIndex).getPoint();
				System.out.println("nearest distance point : (" + nearestPoint.x + ", " + nearestPoint.y + ")");


				// D. Insert Location
				Location tmpLocation = list.remove(p3Index);
				list.add(nearestIndex, tmpLocation);
			}

		}

	}

	@Override
	public String toString(){
		return "Watanabesan Algorithm";
	}

}
