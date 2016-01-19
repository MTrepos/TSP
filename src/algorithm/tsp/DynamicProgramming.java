package algorithm.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import simulate.Location;

public class DynamicProgramming implements TSPAlgorithm {

	class CheckedState{

		boolean[] checkedArr;

		CheckedState(boolean[] checkedArr){
			this.checkedArr = checkedArr;
		}

		@Override
		public boolean equals(Object o){
			if(o instanceof boolean[]){
				return Arrays.equals(checkedArr, ((boolean[])o));
			}
			return false;
		}

		public void setCheckedArrElements(int i, boolean b){
			this.checkedArr[i] = b;
		}

		public boolean getCheckedArrElements(int i){
			return this.checkedArr[i];
		}
	}

	@Override
	public void sort(ArrayList<Location> list) {

		// 1. set variables
		  //distanceTable -> distanceTable[p1][p2] means distance between p1 and p2.
		double distanceTable[][] = new double[list.size()][list.size()-1];

		  //make CheckedState
		boolean isChecked[] = new boolean[list.size()];
		for(int i=0; i<list.size(); i++){
			isChecked[i] = true;
		}
		CheckedState initCheckedState = new CheckedState(isChecked);

		HashMap<CheckedState, Integer> nearestDistanceMap = new HashMap<CheckedState, Integer>();



	}

//	private double tsp(int currentPointIndex, boolean isChecked[][], double[][] distanceTable, int pointSize){
//		//isChecked[currentPointIndex][]
//	}

	@Override
	public String toString(){
		return "DynamicProgramming";
	}
}
