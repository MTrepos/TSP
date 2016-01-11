package gui;

import java.util.ArrayList;

import simulate.Location;

public interface MapMediator {
	public ArrayList<Location> getLocationList();
	public boolean existsLocation(Location l);
	public void setLocationType(Location l, int type);
	public void createNewMap(int mw, int mh);
}
