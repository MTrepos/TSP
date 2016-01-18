package simulate;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable{

	Dimension dimension; //マップの大きさ
	ArrayList<Location> locationList;

	public Map(int w, int h){
		this.dimension = new Dimension(w, h);
		this.locationList = new ArrayList<Location>();
	}

	public int getMapWidth(){
		return this.dimension.width;
	}

	public int getMapHeight(){
		return this.dimension.height;
	}

	public void clearLocation(){
		this.locationList.clear();
	}

	public ArrayList<Location> getLocationList(){
		return this.locationList;
	}

	public boolean existsLocation(Location l){
		return locationList.contains(l);
	}

	public boolean addLocation(Location l){
		if(isInRange(l)){
			if(!locationList.contains(l)){
				this.locationList.add(l);
				return true;
			}
		}
		return false;
	}

	public boolean removeLocation(Location l){
		if(isInRange(l)){
			this.locationList.remove(l);
			return true;
		}
		return false;
	}

 private boolean isInRange(Location l){
		int x = l.p.x;
		int y = l.p.y;
		int w = this.dimension.width;
		int h = this.dimension.height;
		return (x>=0) && (x<w) && (y>=0) && (y<h);
	}

}
