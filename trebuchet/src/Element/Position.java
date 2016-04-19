package Element;

public class Position {
	private int latitudes; //维度
	private int longitudes; //经度
	private int galaxy ; // 星系
	
	public Position(){
		
	}
	
	public Position(int latitudes,int longitudes,int galaxy){
		this.latitudes = latitudes;
		this.longitudes = longitudes;
		this.galaxy = galaxy;
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
	
}
