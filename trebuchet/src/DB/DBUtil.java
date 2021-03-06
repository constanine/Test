package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class DBUtil {
	private static Logger log = Logger.getLogger(Logger.class);
	private static BasicDataSource dataSource;
	private static Connection connection = null;
	public static BasicDataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(BasicDataSource dataSource) {
		DBUtil.dataSource = dataSource;
	}

	/**
	* 获取数据库连接
	* @return conn
	 * @throws SQLException 
	*/
	private static Connection getConnection(BasicDataSource ds) throws SQLException {	
		if(null == connection){
			connection = ds.getConnection();
		}else{
			if(connection.isClosed()){
				connection = ds.getConnection();
			}
		}
		return connection;
	}
	
	/**
	* 关闭数据库连接
	* @param conn
	 * @throws SQLException 
	*/
	private static void closeConnection(Connection conn){
		if(null != conn){
			try{
				conn.close();
				conn = null;
			}catch(Exception e){
				//Ignore it
			}
		}
	}
		
	/**
	* 关闭数据库资源
	* @param stmt
	 * @throws SQLException 
	*/
	private static void closeStatement(Statement stmt){
		if(null != stmt){
			try{
				stmt.close();
				stmt = null;
			}catch(Exception e){
				//Ignore it
			}
		}		
	}
	
	
	/**
	* 关闭数据库资源
	* @param rs
	 * @throws SQLException 
	*/
	private static void closeResultSet(ResultSet rs){
		if(null != rs){
			try{
				rs.close();
				rs = null;
			}catch(Exception e){
				//Ignore it
			}
		}	
	}	
	
	private static void rollbackWithException(){
		Connection conn = null;
		try{
			conn = getConnection(DBUtil.dataSource);
			if(null != conn){	
				conn.rollback();				
			}
		}catch(Exception e){
			//do Nothing
		}		
	}
	
	public static void finishSqlResult(){
		Connection conn = null;
		try{
			conn = getConnection(DBUtil.dataSource);
			if(null != conn){
				conn.commit();
				closeConnection(conn);
			}
		}catch(Exception e){
			//do Nothing
		}
	}
	
	public static int executeSqlResult(String sql,Object[] args) throws SQLException{
		Connection conn = null;
		PreparedStatement statement = null;
		int result=0;
		try{
			log.info("执行sql语句:"+sql);
			conn = getConnection(DBUtil.dataSource);			
			statement=conn.prepareStatement(sql);
			for(int pIdx=0;pIdx<args.length;pIdx++){
				statement.setObject(pIdx+1,args[pIdx].toString());
			}
			result = statement.executeUpdate();
			log.info("执行sql语句:"+statement.toString());
		}catch(Exception e){
			log.error("Sql:"+sql+",执行报错");
			rollbackWithException();			
		}finally{
			closeStatement(statement);
		}
		return result;
	}
	
	/**
	 * 
	 * @param sqlresult ResultSet当前JDBC查询后返回的结果
	 * @return 当前查询结果的列名集合
	 * @throws SQLException
	 */
	public static List<String> getDBColumnName(ResultSet sqlresult) throws SQLException{
		List<String> columnNames = new ArrayList<String>();
		ResultSetMetaData rsmd = sqlresult.getMetaData();
		int columnNum = rsmd.getColumnCount();
		for(int i=1;i<=columnNum;i++){
			columnNames.add(rsmd.getColumnName(i));
		}
		return columnNames;
	}
	
	/**
	 * @param sql 查询的sql语句,并以list<map>形式返回
	 * @return 查询结果,封装成List<Map<String, Object>>的泛型
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> getListOfMapBySql(String sql,Object[] params) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<String> colNames = new ArrayList<String>();
		ResultSet brs = null;
		Connection conn = null;
		PreparedStatement statement = null;
		try{
			log.info("执行sql语句:"+sql);
			conn = getConnection(DBUtil.dataSource);		
			statement=conn.prepareStatement(sql);
			if(null != params){
				for(int pIdx=0;pIdx<params.length;pIdx++){
					statement.setObject(pIdx+1,params[pIdx].toString());
				}
			}
			log.info("执行sql语句:"+statement.toString());
			brs = statement.executeQuery();
			colNames = getDBColumnName(brs);
			
			brs.beforeFirst();
			while(brs.next()){
				Map<String, Object> line = new HashMap<String, Object>();
				for(String colName: colNames){
					
					Object value = brs.getObject(colName);
					if (brs.getObject(colName) instanceof Number) { // 如果是数字型
						value = brs.getBigDecimal(colName);
					} 
					//放到模型里面的字段名都使用大写
					line.put(colName.toUpperCase(), value);
				}
				result.add(line);
			}
		}catch(Exception e){
			log.error("Sql:"+sql+",执行报错");
			rollbackWithException();			
		}finally{
			closeResultSet(brs);
			closeStatement(statement);
		}
		return result;
	}
}
