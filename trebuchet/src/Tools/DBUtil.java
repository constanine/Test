package Tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.transaction.annotation.Transactional;

public class DBUtil {
	private JdbcTemplate jdbcTemplate;	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public  int executeSqlResult(String sql,Object[] args) throws SQLException{
		int result = jdbcTemplate.update(sql, args);
		return result;
	}
	
	/**
	 * 
	 * @param sqlresult ResultSet当前JDBC查询后返回的结果
	 * @return 当前查询结果的列名集合
	 * @throws SQLException
	 */
	public  List<String> getDBColumnName(SqlRowSet sqlresult) throws SQLException{
		List<String> columnNames = new ArrayList<String>();
		SqlRowSetMetaData rsmd = sqlresult.getMetaData();
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
	@Transactional
	public  List<Map<String, Object>> getListOfMapBySql(String sql,Object[] params) throws SQLException {
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, params);
		return result;
	}
	
	/**
	 * @param sql 查询的sql语句,
	 * @return 查询结果,并以RowSet形式返回
	 * @throws SQLException
	 */
	@Transactional
	public  SqlRowSet getRowsetBySql(String sql,Object[] params) throws SQLException {
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, params);
		return result;
	}
}
