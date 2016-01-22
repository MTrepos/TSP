package algorithm.tsp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import algorithm.AlgorithmUtilities;
import simulate.Location;

public class SameLineCrossRomover implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. search sameLine points
		for(int i=0; i<list.size(); i++){
			int basePathP1Index = i;
			int basePathP2Index = ((i+1)>=list.size()) ? 0 : i+1;
			int basePathP3Index = ((i+2)>=list.size()) ? (i+2)-(list.size()) : i+2;
			Point basePathP1 = list.get(basePathP1Index).getPoint();
			Point basePathP2 = list.get(basePathP2Index).getPoint();
			Point basePathP3 = list.get(basePathP3Index).getPoint();

			if(AlgorithmUtilities.isOnLine(basePathP1, basePathP3, basePathP2)){
				// 2. search another same line path
				int j=basePathP3Index;
				int stopIndex = -1;

				while(true){
					int searchingPathP1Index = j;
					int searchingPathP2Index = ((j+1)>=list.size()) ? 0 : j+1;
					Point searchingPathP1 = list.get(searchingPathP1Index).getPoint();
					Point searchingPathP2 = list.get(searchingPathP2Index).getPoint();

					if(AlgorithmUtilities.isOnLine(searchingPathP1, searchingPathP2, basePathP2)){ //if found
						stopIndex = searchingPathP1Index;
						break;
					}

					++j;
					j=(j>=list.size()) ? 0 : j;
					if(searchingPathP2Index == basePathP1Index){ //if return to basePoint
						break;
					}
				}

				if(stopIndex != -1){
					// 3. make list to reverse
					ArrayList<Location> toReverseList = new ArrayList<Location>();
					for(int k=basePathP2Index; k!=0; ++k, k=(k>=list.size() ? 0 : k)){
						toReverseList.add(list.get(k));
					}
					toReverseList.add(list.get(j));

					// 4. reverse toReverseList
					Collections.reverse(toReverseList);
				}

			}

		}

	}

}
