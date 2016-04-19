package common;

import java.util.ArrayList;
import java.util.List;

public class Player {
	/*baseinfo*/
	long ID;
	String name;
	String code;
	String password;
	int Authlevel;
	long BitCoin;
	long MainPlanetID;
	Planet MainPlanet;
	
	/*secrecy*/
	String Email;
	String Lastip;
	String Ip_at_reg;
	long RegisterTime;
	
	private List<Planet> planets = new ArrayList<Planet>();
	
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
	public List<Planet> getPlanets() {
		return planets;
	}
	public void setPlanets(List<Planet> planets) {
		this.planets = planets;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getBitCoin() {
		return BitCoin;
	}
	public void setBitCoin(long bitCoin) {
		BitCoin = bitCoin;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getAuthlevel() {
		return Authlevel;
	}
	public void setAuthlevel(int authlevel) {
		Authlevel = authlevel;
	}
	public String getLastip() {
		return Lastip;
	}
	public void setLastip(String lastip) {
		Lastip = lastip;
	}
	public String getIp_at_reg() {
		return Ip_at_reg;
	}
	public void setIp_at_reg(String ip_at_reg) {
		Ip_at_reg = ip_at_reg;
	}
	public long getRegisterTime() {
		return RegisterTime;
	}
	public void setRegisterTime(long register_time) {
		RegisterTime = register_time;
	}
	public long getMainPlanetID() {
		return MainPlanetID;
	}
	public void setMainPlanetID(long mainPlanetID) {
		MainPlanetID = mainPlanetID;
	}
	public void setMainPlanet(Planet mainPlanet) {
		MainPlanet = mainPlanet;
	}
	public Planet getMainPlanet() {
		return MainPlanet;
	}	
}
