package algorithm.tsp;

import java.util.ArrayList;

import algorithm.AlgorithmUtilities;
import simulate.Location;

public class NearestNeighbor implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		for(int currentLocationIndex=0; currentLocationIndex<list.size()-1; currentLocationIndex++){

			// 1. deceide Base Location
			Location baseLocation = list.get(currentLocationIndex);

			// 2. search nearest Location from BaseLocation
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

			// 3. swap nearestLocation for currentLocation
			Location tmpLocation = list.set(currentLocationIndex+1, list.get(nearestLocationIndex)); //set : overwrite, return before value
			list.set(nearestLocationIndex, tmpLocation);

		}

	}

	@Override
	public String toString(){
		return "NearestNeighbor";
	}

}
