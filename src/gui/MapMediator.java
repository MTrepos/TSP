package gui;

import simulate.Location;

public interface MapMediator {
	public boolean existsLocation(Location l);
	public void addLocation(Location l);
	public void removeLocation(Location l);
}
