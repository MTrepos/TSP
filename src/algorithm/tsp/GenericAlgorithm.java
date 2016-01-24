package algorithm.tsp;

import java.util.ArrayList;
import java.util.Random;

import simulate.Location;

public class GenericAlgorithm implements TSPAlgorithm {

	@Override
	public void sort(ArrayList<Location> list) {	
		
		// 1. N固体を用意し、現世代のリストに入れる。
		// 2. 評価関数を適用し、上位X個を次世代のリストへ入れる。（淘汰）
		// 3. 交叉によりY個の子孫を作り、次世代リストに入れる (交叉は後述）
		// 4. 突然変異により子孫をつくり、次世代リストに入れる
		// 5. このとき、次世代の固体がN個になるように調節する。
		// 6. 次世代の固体を、現世代のリストに移し変え、2-4をG世代繰り返す。
		// 7. もっとも評価の良い個体を「解」とする。

	}

}

class Gene{
	private Random rnd = new Random();
	private ArrayList<Location> origin;
	private int[] codes;
	private int size;
	
	public Gene(ArrayList<Location> origin){
		this.size = origin.size();
		this.codes = new int[2];
	}
	
	private int[] encode(ArrayList<Location> route){
		ArrayList<Integer> code;
		return codes;
	}
}
