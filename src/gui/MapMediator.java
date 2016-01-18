package gui;

import java.util.ArrayList;

import simulate.Location;

public interface MapMediator {
	public int getMapWidth();
	public int getMapHeight();
	public void clearLocation();
	public ArrayList<Location> getLocationList();
	public boolean existsLocation(Location l);
	public boolean addLocation(Location l);
	public boolean removeLocation(Location l);
	public void createNewMap(int mw, int mh);
}
