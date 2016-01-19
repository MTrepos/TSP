package algorithm.tsp;

import java.util.ArrayList;

import simulate.Location;

public class Memoize implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. set variables
		  //distanceTable -> distanceTable[p1][p2] means distance between p1 and p2.
		double distanceTable[][] = new double[list.size()][list.size()-1];

		  // isCehcked // get true if checked
		boolean isChecked[] = new boolean[list.size()];

	}

}
