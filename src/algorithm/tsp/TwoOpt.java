package algorithm.tsp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import algorithm.AlgorithmUtilities;
import simulate.Location;

public class TwoOpt implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {
		
		// 1. sort with NearestNeighbor
		NearestNeighbor nn = new NearestNeighbor();
		nn.sort(list);
		
		// 2. 2-opt
		  // A. search crossed path
		for(int basePointIndex=0; basePointIndex<list.size()-1; basePointIndex++){
			Point basePathP1 = list.get(basePointIndex).getPoint();
			Point basePathP2 = list.get(basePointIndex+1).getPoint();
			for(int comparedPointIndex=0; comparedPointIndex<list.size()-1; comparedPointIndex++){
				Point comparedPathP1 = list.get(comparedPointIndex).getPoint();
				Point comparedPathP2 = list.get(comparedPointIndex+1).getPoint();
				if(basePathP1.equals(comparedPathP1)
						|| basePathP1.equals(comparedPathP2)
						|| basePathP2.equals(comparedPathP1)
						|| basePathP2.equals(comparedPathP2)){
					continue;
				}
				
				// B. if crossed, reverse between basePointP2 to comparedPointP1
				if(AlgorithmUtilities.isIntersect(basePathP1, basePathP2, comparedPathP1, comparedPathP2)){
					System.out.println(basePointIndex + " to " + (basePointIndex+1) + " x " + comparedPointIndex + " to " + (comparedPointIndex+1));
					ArrayList<Location> tmpList = new ArrayList<Location>();
					for(int index=basePointIndex+1; index<=comparedPointIndex; index++){
						tmpList.add(list.get(index));
					}
					Collections.reverse(tmpList);
					// C. set reverseList
					for(int i=basePointIndex+1, j=0; i<=comparedPointIndex; i++, j++){
						list.set(i, tmpList.get(j));
					}
					basePointIndex=0;
				}
			}
		}
	}
	
	@Override
	public String toString(){
		return "2-opt Method";
	}

}
