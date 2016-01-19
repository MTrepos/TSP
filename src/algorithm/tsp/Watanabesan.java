package algorithm.tsp;

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
				System.out.println("distance(" + currentLocationIndex + ", " + searchLocationIndex + ") : " + distance);
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

		double avgDistance = distanceSum / list.size();
		for (int i = 0; i < list.size() - 1; i++) {
			double distance = AlgorithmUtilities.calcDistance(list.get(i).getPoint(), list.get(i + 1).getPoint());
			if (distance > avgDistance) {
				System.out.println("over avg , index = " + i + ", Location = " + list.get(i).getPoint().toString());
				int nearestLocationIndex = 0;
				double nearestDistance = Double.MAX_VALUE;

				for (int j = 0; j < list.size() - 1; j++) {
					double tmpDistance =
							AlgorithmUtilities.calcDistance(list.get(j).getPoint(), list.get(i).getPoint()) +
									AlgorithmUtilities.calcDistance(list.get(i).getPoint(), list.get(j+1).getPoint());
					if(tmpDistance < nearestDistance){
						nearestLocationIndex = j;
						nearestDistance = tmpDistance;
					}
				}
				//swap
				Location tmpLocation = list.remove(i);
				list.add(nearestLocationIndex+1, tmpLocation);
			}

		}

	}

	@Override
	public String toString(){
		return "Watanabesan Algorithm";
	}

}
