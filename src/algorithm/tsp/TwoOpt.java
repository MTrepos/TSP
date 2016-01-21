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
		  // A. search crossing path
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

				// B. if crossing, reverse between basePointP2 to comparedPointP1
				if(AlgorithmUtilities.isIntersect(basePathP1, basePathP2, comparedPathP1, comparedPathP2)){
					//System.out.println("cross Path : " + basePathP1Index + "---" + basePathP2Index + " x " + comparedPathP1Index + "---" + comparedPathP2Index);
					ArrayList<Location> toReverseList = new ArrayList<Location>();
//					System.out.println("basePathP1Index : " + basePathP1Index);
//					System.out.println("basePathP2Index : " + basePathP2Index);
//					System.out.println("comparedPathP1Index : " + comparedPathP1Index);
//					System.out.println("comparedPathP2Index : " + comparedPathP2Index);
					for(int k=basePathP2Index; k!=comparedPathP2Index; ++k, k=(k>=list.size() ? 0 : k)){
						toReverseList.add(list.get(k));
					}
					//System.out.println("reverse, size = " + toReverseList.size());
					Collections.reverse(toReverseList);
					// C. set reverseList
					for(int l=basePathP2Index, m=0; l!=comparedPathP2Index; ++l, l=(l>=list.size() ? 0 :l),m++){
						list.set(l, toReverseList.get(m));
					}
					i=0; // reset
				}
			}
		}

	}

	@Override
	public String toString(){
		return "2-opt Method";
	}

}
