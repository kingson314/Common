package module.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import app.AppCon;

import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;

public class DbConnectionDao {
	public static DbConnectionDao dbConnectionDao = null;
	private Connection con;
	private static ConcurrentHashMap<String, DbConnection> MapDbConn = new ConcurrentHashMap<String, DbConnection>();

	public static DbConnectionDao getInstance() {
		if (dbConnectionDao == null)
			dbConnectionDao = new DbConnectionDao();
		return dbConnectionDao;
	}

	// 构造
	private DbConnectionDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconApp);
	}

	// 根据dbName获取任务实例
	public synchronized DbConnection getMapDbConn(String dbName) {
		DbConnection dbConn = MapDbConn.get(dbName);
		if (dbConn == null) {
			dbConn = getDbConn(dbName);
			MapDbConn.put(dbName, dbConn);
		}
		return dbConn;
	}

	// 判断数据库连接信息是否存在
	public boolean DbConnIsExist(String dbName) {
		Statement sm = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from " + AppCon.TN_DbConnection
					+ " where dbName='" + dbName + "'");
			rs.last();
			if (rs.getRow() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			UtilLog.logError(" 判断数据库连接配置是否存在SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 判断数据库连接配置是否存在错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return result;
	}

	// 新增数据库连接信息
	public void addDbCon(DbConnection dbCnn) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into  " + AppCon.TN_DbConnection
					+ "(" + "dbName,dbType,dbClassName,dbCon,"
					+ "dbUser,dbPassword) values (?,?,?,?,?,?)");
			ps.setString(1, dbCnn.getDbName());
			ps.setString(2, dbCnn.getDbType());
			ps.setString(3, dbCnn.getDbClassName());
			ps.setString(4, dbCnn.getDbCon());
			ps.setString(5, dbCnn.getDbUser());
			ps.setString(6, dbCnn.getDbPassword());
			ps.addBatch();
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			UtilLog.logError(" 新增发件人邮件SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 新增发件人邮件错误:", e);
		} finally {
			MapDbConn.put(dbCnn.getDbName(), dbCnn);
			UtilSql.close(ps);
		}
	}

	// 修改数据库连接信息
	public void modDbcon(DbConnection dbConn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("update  " + AppCon.TN_DbConnection
					+ " set " + " dbType=?,dbClassName=?,dbCon=?,"
					+ "dbUser=?,dbPassword=? where dbName='"
					+ dbConn.getDbName() + "'");
			ps.setString(1, dbConn.getDbType());
			ps.setString(2, dbConn.getDbClassName());
			ps.setString(3, dbConn.getDbCon());
			ps.setString(4, dbConn.getDbUser());
			ps.setString(5, dbConn.getDbPassword());
			ps.addBatch();
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			UtilLog.logError(" 修改数据库连接配置SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 修改数据库连接配置错误:", e);
		} finally {
			MapDbConn.put(dbConn.getDbName(), dbConn);
			UtilSql.close(rs, ps);
		}
	}

	// 删除数据库连接信息
	public void delConn(String dbName) {
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sm.addBatch("delete from  " + AppCon.TN_DbConnection
					+ " where dbName='" + dbName + "'");
			sm.executeBatch();
			con.commit();
		} catch (SQLException e) {
			UtilLog.logError(" 删除数据库连接配置SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 删除数据库连接配置错误:", e);
		} finally {
			MapDbConn.remove(dbName);
			UtilSql.close(rs, sm);
		}
	}

	// 获取数据库连接信息表格数组
	public Vector<Vector<String>> getDbConn() {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> dnConnVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_DbConnection);
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();

				rowValue.add(rs.getString("DbName"));
				rowValue.add(rs.getString("dbType"));
				rowValue.add(rs.getString("dbClassName"));
				rowValue.add(rs.getString("dbCon"));
				rowValue.add(rs.getString("dbUser"));
				rowValue.add(rs.getString("dbPassword"));
				dnConnVector.add(rowValue);
			}
		} catch (SQLException e) {
			UtilLog.logError(" 获取数据库连接表格信息SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 获取数据库连接表格信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return dnConnVector;
	}

	// 获取数据库连接
	private DbConnection getDbConn(String dbName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		DbConnection dbconn = new DbConnection();
		try {
			String sql = "select * from  " + AppCon.TN_DbConnection
					+ " where dbName='" + dbName + "'";
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			if (rs.getRow() == 0)
				return null;
			dbconn.setDbName(rs.getString("dbName"));
			dbconn.setDbType(rs.getString("dbType"));
			dbconn.setDbClassName(rs.getString("dbClassName"));
			dbconn.setDbCon(rs.getString("dbCon"));
			dbconn.setDbUser(rs.getString("dbUser"));
			dbconn.setDbPassword(rs.getString("dbPassword"));
		} catch (SQLException e) {
			UtilLog.logError("获取数据库" + dbName + "配置SQL错误:", e);
			return null;
		} catch (Exception e) {
			UtilLog.logError("获取数据库" + dbName + "配置错误:", e);
			return null;
		} finally {
			UtilSql.close(rs, ps);
		}
		return dbconn;
	}
}
