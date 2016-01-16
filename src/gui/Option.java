package gui;

import algorithm.clustering.ClusteringAlgorithm;
import algorithm.tsp.TSPAlgorithm;

public class Option {
	
	int k; //cluster
	ClusteringAlgorithm clusteringAlgorithm;
	TSPAlgorithm tspAlgorithm;

	public Option(int k, ClusteringAlgorithm clusteringAlgorithm, TSPAlgorithm tspAlgorithm){
		this.k = k;
		this.clusteringAlgorithm = clusteringAlgorithm;
		this.tspAlgorithm = tspAlgorithm;
	}
	
}