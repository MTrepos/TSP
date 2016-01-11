package simulate;

import java.awt.Point;
import java.io.Serializable;

public class Location implements Serializable{
	
	public static final int TYPE_NORMAL_LOCATION = 0;
	public static final int TYPE_PATH_LOCATION = 1;
	public static final int TYPE_EXCLUDE_LOCATION = 2;
	
	final Point p;
	int type;
	String name;
	
	public Location(int i, int j, int type){
		this.p = new Point(i, j);
		this.type= type;
	}
	
	public Location(int i, int j, int type, String name){
		this(i, j, type);
		this.name = name;
	}
	
	public int getType(){
		return this.type;
	}
	
	public Point getPoint(){
		return p;
	}	
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Location){
			Location l = (Location)o;
			return l.p.equals(p);
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return p.hashCode();
	}
}
