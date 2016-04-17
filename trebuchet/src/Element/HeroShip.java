package Element;

import java.util.ArrayList;
import java.util.List;

public class HeroShip extends Ship{
	
	List<ShipModles> modles = new ArrayList<ShipModles>();

	public List<ShipModles> getModles() {
		return modles;
	}

	public void setModles(List<ShipModles> modles) {
		this.modles = modles;
	}
	
}
