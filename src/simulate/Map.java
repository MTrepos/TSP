package simulate;

import java.util.ArrayList;

public class Map {

	int mw, mh; //マップの大きさ
	ArrayList<Location> locationList;
	
	public Map(int mw, int mh){
		this.mw = mw;
		this.mh = mh;
		
		//地点リストの初期化
		this.locationList = new ArrayList<Location>(mw*mh);
		for(int i=0; i<mw; i++){
			for(int j=0; j<mh; j++){
				this.locationList.add(new Location(i, j, Location.TYPE_NORMAL_LOCATION));
			}
		}
	}
	
	public boolean existsLocation(Location l){
		return locationList.contains(l);
	}

	public void addLocation(Location l) {
		if(isInRange(l)){
			locationList.add(l);
		}
	}

	public void removeLocation(Location l) {
		if(isInRange(l)){
			locationList.remove(l);
		}
	}
	
	private boolean isInRange(Location l){
		int x = l.p.x;
		int y = l.p.y;
		return ((x>=0) && (x<=mw) && (y>=0) && (y<=mh)) ? true : false;
	}
	
}
