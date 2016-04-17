package Element;

import java.util.List;

public class Planet {
	String name;
	long ID;
	private int latitudes; //维度
	private int longitudes; //经度
	private int galaxy ; // 星系
	private Resources resources;
	private List<Fleet> fleets;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public int getLatitudes() {
		return latitudes;
	}
	public void setLatitudes(int latitudes) {
		this.latitudes = latitudes;
	}
	public int getLongitudes() {
		return longitudes;
	}
	public void setLongitudes(int longitudes) {
		this.longitudes = longitudes;
	}
	public int getGalaxy() {
		return galaxy;
	}
	public void setGalaxy(int galaxy) {
		this.galaxy = galaxy;
	}
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}
	public List<Fleet> getFleets() {
		return fleets;
	}
	public void setFleets(List<Fleet> fleets) {
		this.fleets = fleets;
	}
	
	
}
