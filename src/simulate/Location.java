package simulate;

import java.awt.Point;
import java.io.Serializable;

public class Location implements Serializable{

	final Point p;
	String name;

	public Location(int i, int j){
		this.p = new Point(i, j);
	}

	public Location(int i, int j, String name){
		this(i, j);
		this.name = name;
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
