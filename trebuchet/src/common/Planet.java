package common;

import java.util.List;

import Element.Fleet;
import Element.Position;
import Element.Resources;

public class Planet {
	String name;
	long ID;
	
	private Position position;
	private Resources resources;
	private List<Fleet> fleets;
	private long last_update;
	private long id_owner;
	private int field; //面积
	private int tempture; //温度
	
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
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
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
	public long getLast_update() {
		return last_update;
	}
	public void setLast_update(long last_update) {
		this.last_update = last_update;
	}
	public long getId_owner() {
		return id_owner;
	}
	public void setId_owner(long l) {
		this.id_owner = l;
	}
	public int getField() {
		return field;
	}
	public void setField(int field) {
		this.field = field;
	}
	public int getTempture() {
		return tempture;
	}
	public void setTempture(int tempture) {
		this.tempture = tempture;
	}
	
	
}
