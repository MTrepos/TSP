package algorithm.tsp;

import java.util.ArrayList;

import algorithm.AlgorithmUtilities;
import algorithm.AlgorithmUtilities.IndexSet;
import simulate.Location;

public class Watanabesan implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. find superior NearestNeighbor list
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
		
		while(true){
			IndexSet is2Opt = AlgorithmUtilities.search2optIndex(nearestDistanceList);
			IndexSet isOnLine = AlgorithmUtilities.searchIndexIntersectWithPath(nearestDistanceList);
			
			if(is2Opt != null){ // 2. Apply 2-opt to nearestDistanceList
				//System.out.println("is2Opt : (" + is2Opt.startIndex + ", " + is2Opt.endIndex + ")");
				AlgorithmUtilities.reverse(nearestDistanceList, is2Opt);
			}else if(isOnLine != null){ // 3. remove SameLineCross & sort
				//System.out.println("isOnline : (" + isOnLine.startIndex + ", " + isOnLine.endIndex + ")");
				AlgorithmUtilities.reverse(nearestDistanceList, isOnLine);
			}else {
				break;
			}
		}

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
