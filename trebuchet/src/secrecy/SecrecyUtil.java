package secrecy;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Player;

import DB.DBUtil;


public class SecrecyUtil {
	//登入的检查
	public static Map<String,Object> login(String playerCode,String password) throws SQLException{
		Map<String,Object> result= new HashMap<String,Object>();
		String sql = "SELECT ID FROM CP_Player where code =?";
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
				player.setMainPlanet(Integer.valueOf(playerMap.get("MAINPLANET").toString()));
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
	
	public static Map<String,Object> register(String playerCode,String name,String password,String email) throws SQLException{
		Map<String,Object> result= new HashMap<String,Object>();
		//插入用户数据
		String sql_create = "INSERT INTO CP_Player (code,name,password,Email,Authlevel) VALUES (?,?,?,?,1)";
		DBUtil.executeSqlResult(sql_create, new Object[]{playerCode,name,password,email});
		
		//获取新插入的用户ID
		String sql = "SELECT ID FROM CP_Player where code =?";
		List<Map<String, Object>> sqlRs = DBUtil.getListOfMapBySql(sql, new Object[]{playerCode});
		sqlRs = DBUtil.getListOfMapBySql(sql, new Object[]{playerCode});
		int playID = Integer.valueOf(sqlRs.get(0).get("ID").toString());
		
		Player player = new Player();
		player.setID(playID);
		player.setName(name);
		player.setCode(playerCode);
		player.setBitCoin(0);
		player.setMainPlanet(1);
		player.setAuthlevel(1);	
		result.put("result", "sucess");
		result.put("player", player);
		return result;
	}
	

}
