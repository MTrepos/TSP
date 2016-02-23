package algorithm.tsp;

import java.util.ArrayList;

import algorithm.AlgorithmUtilities;
import algorithm.AlgorithmUtilities.IndexSet;
import simulate.Location;

public class Watanabesan implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. find shortest NearestNeighbor Algorithm Path
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
			//System.out.println("(i ,distance) = (" + i + ", " + distance + ")");

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
			if(is2Opt != null){ // 2. Apply 2-opt to nearestDistanceList
				//System.out.println("is2Opt : (" + is2Opt.index1 + ", " + is2Opt.index2 + ")");
				AlgorithmUtilities.reverse(nearestDistanceList, is2Opt);
				continue;
			}

			IndexSet isOnLine = AlgorithmUtilities.searchIndexIntersectWithPath(nearestDistanceList);
			if(isOnLine != null){ // 3. remove SameLineCross & sort
				//System.out.println("isOnline : (" + isOnLine.index1 + ", " + isOnLine.index2 + ")");
				AlgorithmUtilities.reverse(nearestDistanceList, isOnLine);
				continue;
			}

//			IndexSet isToMove = AlgorithmUtilities.searchIndexToMoveToNearestLineSegamenta(nearestDistanceList);
//			if(isToMove != null){ // 2. Apply 2-opt to nearestDistanceList
//				//System.out.println("Move " + isToMove.index1 + " to " + isToMove.index2);
//				AlgorithmUtilities.applyToMoveIndexSet(nearestDistanceList, isToMove);
//				continue;
//			}

			break;
		}

		// 4. set nearestDistanceList to list
		for(int i=0; i<nearestDistanceList.size(); i++){
			list.set(i, nearestDistanceList.get(i));
		}

	}

	private void sleep(long ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString(){
		return "Remedy Algorithm";
	}

}
