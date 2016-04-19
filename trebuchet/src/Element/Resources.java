package Element;

public class Resources {
	
	private long metal; //金属
	private long fule; //燃料
	private long feed; //食物
	private long crystal; //补给
	private int power; //电力
	
	public Resources() {
		
	}
	
	public Resources(long metal, long fule, long feed, long crystal, int power) {
		this.metal = metal;
		this.fule = fule;
		this.feed = feed;
		this.crystal = crystal;
		this.power = power;
	}
	
	
	public long getMetal() {
		return metal;
	}
	public void setMetal(long metal) {
		this.metal = metal;
	}
	public long getFule() {
		return fule;
	}
	public void setFule(long fule) {
		this.fule = fule;
	}
	public long getFeed() {
		return feed;
	}
	public void setFeed(long feed) {
		this.feed = feed;
	}
	public long getCrystal() {
		return crystal;
	}
	public void setCrystal(long crystal) {
		this.crystal = crystal;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	
}
