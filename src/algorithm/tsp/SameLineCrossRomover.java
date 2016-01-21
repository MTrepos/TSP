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

			if(AlgorithmUtilities.isInSameLine(basePathP1, basePathP2, basePathP3)){
				// 2. make list to reverse
				ArrayList<Location> toReverseList = new ArrayList<Location>();

				int j=basePathP3Index;
				while(true){
					int tmpPathP1Index = j;
					int tmpPathP2Index = ((j+1)>=list.size()) ? 0 : j+1;
					Point tmpPathP1 = list.get(tmpPathP1Index).getPoint();
					Point tmpPathP2 = list.get(tmpPathP2Index).getPoint();

					if(AlgorithmUtilities.isInSameLine(tmpPathP1, basePathP2, tmpPathP2)){
						break;
					}
					toReverseList.add(list.get(j));
					++j;
					j=(j>=list.size() ? 0 : j);
				}

				// 3. reverse toReverseList
				Collections.reverse(toReverseList);


			}

		}

	}

}
