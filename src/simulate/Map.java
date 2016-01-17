package simulate;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable{

	Dimension dimension; //マップの大きさ
	ArrayList<Location> locationList;

	public Map(int w, int h){
		this.dimension = new Dimension(w, h);

		//地点リストの初期化
		this.locationList = new ArrayList<Location>(w*h);
		for(int i=0; i<w; i++){
			for(int j=0; j<h; j++){
				this.locationList.add(new Location(i, j, Location.TYPE_NORMAL_LOCATION));
			}
		}
	}

	public int getMapWidth(){
		return this.dimension.width;
	}

	public int getMapHeight(){
		return this.dimension.height;
	}
	
	public void setAllLocationNormal(){
		for(Location l : this.locationList){
			l.type = Location.TYPE_NORMAL_LOCATION;
		}		
	}

	public ArrayList<Location> getLocationList(){
		return this.locationList;
	}
	
	public ArrayList<Location> getPathLocationList(){
		ArrayList<Location> pathLocationList = new ArrayList<Location>();
		for(Location l : this.locationList){
			if(l.type == Location.TYPE_PATH_LOCATION){
				pathLocationList.add(l);
			}
		}
		return pathLocationList;
	}

	public boolean existsLocation(Location l){
		return locationList.contains(l);
	}

	public void setLocationType(Location l, int type) {
		if(isInRange(l)){
			for(Location ll : locationList){
				if(ll.equals(l)){
					ll.type = type;
					//System.out.println("set TYPE_PATH_LOCATION : " + type);
					return;
				}
			}
		}
		System.out.println("out of range");
	}

	private boolean isInRange(Location l){
		int x = l.p.x;
		int y = l.p.y;
		int w = this.dimension.width;
		int h = this.dimension.height;
		return (x>=0) && (x<=w) && (y>=0) && (y<=h);
	}

}
