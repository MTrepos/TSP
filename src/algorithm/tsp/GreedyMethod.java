package algorithm.tsp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import algorithm.AlgorithmUtilities;
import simulate.Location;

public class GreedyMethod implements TSPAlgorithm {

	class PathData implements Comparable<PathData>{
		int srci;
		int dsti;
		double distance;

		public PathData(int srci, int dsti, double distance){
			this.srci = srci;
			this.dsti = dsti;
			this.distance = distance;
		}

		@Override
		public int compareTo(PathData pd) {
			return ((this.distance-pd.distance)<0) ? -1 :
				((this.distance-pd.distance)==0) ? (this.srci-pd.srci) : 1;
		}

		@Override
		public boolean equals(Object o){
			if(o instanceof PathData){
				PathData pd = ((PathData)o);
				if(equals(this.srci==pd.srci) && (this.dsti==pd.dsti) && (this.distance==pd.distance)){
					return true;
				}
			}
			return false;
		}

		@Override
		public int hashCode(){
			return (int) (this.srci * this.dsti * this.distance);
		}

	}

	class PathDataComparator implements Comparator<PathData>{

		@Override
		public int compare(PathData pd1, PathData pd2) {
			return Double.compare(pd1.distance, pd2.distance);
		}

	}

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. make sortedSet
		TreeSet<PathData> pathDataSet = new TreeSet<PathData>(new PathDataComparator());

		// 2. make all PathData which is selected 2 Location in list
		for(int i=0; i<list.size(); i++){
			for(int j=i+1; j<list.size(); j++){
				Point p1 = list.get(i).getPoint();
				Point p2 = list.get(j).getPoint();
				pathDataSet.add(new PathData(i, j, AlgorithmUtilities.calcDistance(p1, p2)));
			}
		}
		for(PathData pd : pathDataSet){
			System.out.println("(" + pd.srci + ", " + pd.dsti + ") : " + pd.distance);
		}

	}

	@Override
	public String toString(){
		return "GreedyMethod";
	}

}
