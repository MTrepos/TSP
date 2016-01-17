package gui;

import java.util.ArrayList;

import simulate.Location;

public interface MapMediator {
	public int getMapWidth();
	public int getMapHeight();
	public void setAllLocationNormal();
	public ArrayList<Location> getLocationList();
	public ArrayList<Location> getPathLocationList();
	public boolean existsLocation(Location l);
	public void setLocationType(Location l, int type);
	public void createNewMap(int mw, int mh);
}
