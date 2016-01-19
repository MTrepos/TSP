package algorithm.tsp;

import java.util.ArrayList;
import java.util.Collections;

import simulate.Location;

public class RandomResolve implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {
		Collections.shuffle(list);
		return;
	}

	public String toString(){
		return "Random Resolve";
	}

}
