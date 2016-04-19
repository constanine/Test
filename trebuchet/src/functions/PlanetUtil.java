package functions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import DB.DBUtil;
import Element.Position;
import Element.Resources;

import common.Planet;
import common.Player;

public class PlanetUtil {
	private final static String PLANET_DB_TABLE = "CP_Planet"; 
	private final static int PLANET_LAT_LENGTH = 12; 
	private final static int PLANET_LONG_LENGTH = 12; 
	//private final static int PLANET_GAL_LENGTH = 255; 
	
	public static boolean isPositionFree(int latitudes,int longitudes,int galaxy) throws SQLException{
		boolean result = true;
		String sql = "SELECT ID FROM "+PLANET_DB_TABLE+" WHERE latitudes=? AND longitudes=? AND galaxy=?";
		List<Map<String, Object>> rs = DBUtil.getListOfMapBySql(sql, new Object[]{latitudes,longitudes,galaxy});
		if(rs.size()>0){
			result = false;
		}
		return result;
	}
	
	public static double calculateMoonChance(){
		double rate = 0d;
		return rate;
	}
	
	public static Planet createPlanet(Player player) throws SQLException{
		Planet planet = new Planet();
		Random r = new Random();
		int tempture = r.nextInt(40);
		int field = 150+r.nextInt(150);
		planet.setTempture(tempture);
		planet.setField(field);
		Position position = getLastPlanetPosition();
		position = getRandomPosition(position);
		planet.setPosition(position);
		planet.setId_owner(player.getID());
		planet.setLast_update(System.currentTimeMillis());
		planet.setName(player.getName()+"-主星");
		int planetID = recordNewPlant(planet);
		planet = getPlanetByID(planetID);
		return planet;		
	}
	
	public static Planet getPlanetByID(long planetID) throws SQLException{
		Planet planet = new Planet();
		String findSql = "SELECT * FROM " +PLANET_DB_TABLE
				+ " WHERE ID=?";
		List<Map<String, Object>> rs = DBUtil.getListOfMapBySql(findSql, new Object[]{planetID});
		String name=(String) rs.get(0).get("NAME");
		int latitudes = Integer.valueOf(rs.get(0).get("LATITUDES").toString());
		int longitudes = Integer.valueOf(rs.get(0).get("LONGITUDES").toString());
		int galaxy = Integer.valueOf(rs.get(0).get("GALAXY").toString());
		int field = Integer.valueOf(rs.get(0).get("FIELD").toString());
		int tempture = Integer.valueOf(rs.get(0).get("TEMPTURE").toString());
		long id_owner = Long.valueOf(rs.get(0).get("ID_OWNER").toString());
		long last_update = Long.valueOf(rs.get(0).get("LAST_UPDATE").toString());
		long metal = Long.valueOf(rs.get(0).get("METAL").toString());
		long fule = Long.valueOf(rs.get(0).get("FULE").toString());
		long feed = Long.valueOf(rs.get(0).get("FEED").toString());
		long crystal = Long.valueOf(rs.get(0).get("CRYSTAL").toString());
		int power= Integer.valueOf(rs.get(0).get("POWER").toString());
		Position position = new Position(latitudes,longitudes,galaxy);
		planet.setPosition(position);
		Resources resources = new Resources(metal,fule,feed,crystal,power);
		planet.setResources(resources);
		planet.setID(planetID);
		planet.setName(name);
		planet.setField(field);
		planet.setLast_update(last_update);
		planet.setTempture(tempture);
		planet.setId_owner(id_owner);
		return planet;
	}
	
	private static int recordNewPlant(Planet planet) throws SQLException {		
		String createSql = "INSERT INTO "+PLANET_DB_TABLE
				+ "(name,latitudes,longitudes,galaxy,tempture,field,id_owner,last_update)"
				+ " VALUES "
				+ " (?,?,?,?,?,?,?,?)";
		String name=planet.getName();
		int latitudes = planet.getPosition().getLatitudes();
		int longitudes = planet.getPosition().getLongitudes();
		int galaxy = planet.getPosition().getGalaxy();
		int field = planet.getField();
		int tempture = planet.getTempture();
		long id_owner = planet.getId_owner();
		long last_update = planet.getLast_update();
		DBUtil.executeSqlResult(createSql, new Object[]{name,latitudes,longitudes,galaxy,field,tempture,id_owner,last_update});		
		String findSql = "SELECT ID FROM " +PLANET_DB_TABLE
				+ " WHERE id_owner=? AND last_update=?";
		List<Map<String, Object>> rs = DBUtil.getListOfMapBySql(findSql, new Object[]{id_owner,last_update});
		int id = Integer.valueOf(rs.get(0).get("ID").toString());
		return id;
	}

	private static Position getLastPlanetPosition() throws SQLException{
		Position position = new Position();
		//FIX ME 如何根据数据库做到 limit ,rownum,转换
		String sql = "SELECT * FROM (SELECT latitudes,longitudes,galaxy FROM "+PLANET_DB_TABLE
				+ " ORDER BY ID DESC) a LIMIT 1";
		List<Map<String, Object>> rs = DBUtil.getListOfMapBySql(sql, null);
		if(rs.size()>0){
			Integer latitudes = Integer.valueOf(rs.get(0).get("latitudes").toString());
			Integer longitudes = Integer.valueOf(rs.get(0).get("longitudes").toString());
			Integer galaxy = Integer.valueOf(rs.get(0).get("galaxy").toString());
			position.setLatitudes(latitudes);
			position.setLongitudes(longitudes);
			position.setGalaxy(galaxy);
		}else{
			position.setLatitudes(3);
			position.setLongitudes(3);
			position.setGalaxy(1);
		}
		return position;		
	}
	
	private static Position getRandomPosition(Position position) throws SQLException{
		Integer latitudes = position.getLatitudes();
		Integer longitudes = position.getLongitudes();
		Integer galaxy = position.getGalaxy();
		Random r = new Random();
		Position randomPosition = new Position();
		do{
			if(latitudes >PLANET_LAT_LENGTH-3 && latitudes>PLANET_LONG_LENGTH-3){
				galaxy ++ ;
			}
			latitudes = latitudes+r.nextInt(7) < PLANET_LAT_LENGTH-3 ?
					latitudes+r.nextInt(7) : PLANET_LAT_LENGTH-3;			
			longitudes = longitudes+r.nextInt(7) < PLANET_LONG_LENGTH-3 ?
					longitudes+r.nextInt(7) : PLANET_LONG_LENGTH-3;			
		}while(!isPositionFree(latitudes, longitudes, galaxy));
		randomPosition.setLatitudes(latitudes);
		randomPosition.setLongitudes(longitudes);
		randomPosition.setGalaxy(galaxy);
		return randomPosition;		
	}
}
