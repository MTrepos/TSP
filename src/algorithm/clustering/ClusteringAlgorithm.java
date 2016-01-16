package algorithm.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import simulate.Location;

public interface ClusteringAlgorithm {
	public HashMap<Location, Integer> cluster(ArrayList<Location> list, int k);
}
