package algorithm.tsp;

import java.util.ArrayList;

import algorithm.AlgorithmUtilities;
import simulate.Location;

public class Watanabesan implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. find superior nn list
		ArrayList<Location> nearestDistanceList = null;
		double nearestDistance = Double.MAX_VALUE;
		NearestNeighbor nn = new NearestNeighbor();

		for(int i=0; i<list.size(); i++){
			// A. copy list to tmpList
			ArrayList<Location> tmpList = new ArrayList<Location>(list.size());
			for(Location l : list){
				tmpList.add(l);
			}

			// B. swap tmpList head for Location(i)
			Location l = tmpList.remove(i);
			tmpList.add(0, l);

			// C. sort & calc distance
			nn.sort(tmpList);
			double distance = AlgorithmUtilities.calcDistanceSum(tmpList);
			System.out.println("(i ,distance) = (" + i + ", " + distance + ")");

			// D. compare
			if(distance<nearestDistance){
				nearestDistance = distance;
				nearestDistanceList = tmpList;
			}

		}

//		System.out.println("nearestDistance = " + nearestDistance);
//		System.out.println("nearestDistanceList's Sum = " + AlgorithmUtilities.calcDistanceSum(nearestDistanceList));

		// 2. Apply 2-opt to nearestDistanceList
		TwoOpt to = new TwoOpt();
		to.sort(nearestDistanceList);

		// 3. remove SameLineCross & sort


		// 4. set nearestDistanceList to list
		for(int i=0; i<nearestDistanceList.size(); i++){
			list.set(i, nearestDistanceList.get(i));
		}

	}

	@Override
	public String toString(){
		return "Watanabe san Algorithm";
	}

}
