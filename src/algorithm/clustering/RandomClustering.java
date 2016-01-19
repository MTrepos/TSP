package algorithm.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import simulate.Location;

public class RandomClustering implements ClusteringAlgorithm {

	@Override
	public HashMap<Location, Integer> cluster(ArrayList<Location> list, int k) {

		//make HashMap, and put initialize cluster
		HashMap<Location, Integer> locationMap = new HashMap<Location, Integer>(list.size());

		Random r = new Random();
		int bound = (k>=1) ? k : 1;
		for(int i=0; i<list.size(); i++){
			locationMap.put(list.get(i), r.nextInt(bound));
		}

		return locationMap;
	}

	@Override
	public String toString(){
		return "Random Clustering";
	}

}
