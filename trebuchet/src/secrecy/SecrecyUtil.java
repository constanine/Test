package secrecy;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DB.DBUtil;

import common.Planet;
import common.Player;

import functions.PlanetUtil;


public class SecrecyUtil {
	//登入的检查
	private final static String PLAY_TABLE_NAME ="CP_Player";
	public static Map<String,Object> login(String playerCode,String password) throws SQLException{
		Map<String,Object> result= new HashMap<String,Object>();
		String sql = "SELECT ID FROM "+PLAY_TABLE_NAME+" where code =?";
		List<Map<String, Object>> sqlRs = DBUtil.getListOfMapBySql(sql, new Object[]{playerCode});
		if(sqlRs.size()>0){
			String sql2 = "SELECT * FROM CP_Player where code =? and password=?";
			List<Map<String, Object>> playRs = DBUtil.getListOfMapBySql(sql2, new Object[]{playerCode,password});
			if(playRs.size()>0){
				Map<String,Object> playerMap = playRs.get(0);
				Player player = new Player();
				player.setID(Integer.valueOf(playerMap.get("ID").toString()));
				player.setName(playerMap.get("NAME").toString());
				player.setCode(playerCode);
				player.setBitCoin(Integer.valueOf(playerMap.get("BITCOIN").toString()));
				long planetID = Long.valueOf(playerMap.get("MAINPLANETID").toString());
				player.setMainPlanetID(planetID);
				Planet planet = PlanetUtil.getPlanetByID(planetID);
				player.setMainPlanet(planet);
				player.setAuthlevel(Integer.valueOf(playerMap.get("AUTHLEVEL").toString()));				
				result.put("result", "sucess");
				result.put("player", player);
			}else{
				result.put("result", "failure");
				result.put("error", "账号密码不对");
			}
		}else{
			result.put("result", "failure");
			result.put("error", "用户不存在");
		}
		return result;
	}
	
	public static boolean isNameValid(String name){
		Pattern pattern = Pattern.compile("^[a-zA-z0-9\\_\\-\\.]*");
		Matcher matcher = pattern.matcher(name);
		boolean b= matcher.matches();
		return b;
	}
	
	public static Map<String,Object> register(String playerCode,String name,String password,String email) throws SQLException{
		Map<String,Object> result= new HashMap<String,Object>();
		//插入用户数据
		String sql_create = "INSERT INTO "+PLAY_TABLE_NAME+" (code,name,password,Email,Authlevel) VALUES (?,?,?,?,1)";
		DBUtil.executeSqlResult(sql_create, new Object[]{playerCode,name,password,email});
		
		//获取新插入的用户ID
		String sql = "SELECT ID FROM CP_Player where code =?";
		List<Map<String, Object>> sqlRs = DBUtil.getListOfMapBySql(sql, new Object[]{playerCode});
		int playID = Integer.valueOf(sqlRs.get(0).get("ID").toString());
		
		Player player = new Player();
		player.setID(playID);
		player.setName(name);
		player.setCode(playerCode);
		player.setBitCoin(0);
		player.setAuthlevel(1);
		
		//创建相应的主星
		Planet planet = PlanetUtil.createPlanet(player);
		//List<Planet> myPlanets = new ArrayList<Planet>();
		//myPlanets.add(planet);
		player.setMainPlanet(planet);
		updatePlayerMainPlanet(player, planet);
		//player.setPlanets(myPlanets);
		
		result.put("result", "sucess");
		result.put("player", player);
		return result;
	}
	
	public static void updatePlayerMainPlanet(Player player,Planet planet) throws SQLException{
		String sql_create = "UPDATE "+PLAY_TABLE_NAME+" SET MainPlanetID=? WHERE ID=?";
		long playerID = player.getID();
		long planetID = planet.getID();
		DBUtil.executeSqlResult(sql_create, new Object[]{playerID,planetID});
	}

	public static boolean registerCheckName(String name) throws SQLException {
		boolean result = true;
		String sql = "SELECT ID FROM "+PLAY_TABLE_NAME+" where Name =?";
		List<Map<String, Object>> sqlRs = DBUtil.getListOfMapBySql(sql, new Object[]{name});
		if(sqlRs.size()>0){
			result = false;
		}
		return result;
	}

	public static boolean registerCheckPlayerCode(String playerCode) throws SQLException {
		boolean result = true;
		String sql = "SELECT ID FROM "+PLAY_TABLE_NAME+" where code =?";
		List<Map<String, Object>> sqlRs = DBUtil.getListOfMapBySql(sql, new Object[]{playerCode});
		if(sqlRs.size()>0){
			result = false;
		}
		return result;
	}
	

}
