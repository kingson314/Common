package module.systemparam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import app.AppCon;

import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.VariableApp;

public class SystemParamsDao {
	private Connection con;
	public static SystemParamsDao systemParamsDao = null;

	public static SystemParamsDao getInstance() {
		if (systemParamsDao == null)
			systemParamsDao = new SystemParamsDao();
		return systemParamsDao;
	}

	// 构造
	private SystemParamsDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconApp);
	}

	// 获取系统参数表格数组
	public Vector<Vector<String>> getSysgemParams(String module, String paramName) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> systemParams = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String vsql = "select * from  " + AppCon.TN_SystemParams + "  where paramtype=0 and 1=1 ";
			if (module.length() > 1) {
				vsql = vsql + " and module='" + module + "'";
			}
			if (paramName.length() > 1) {
				vsql = vsql + " and paramName='" + paramName + "'";
			}
			rs = sm.executeQuery(vsql);
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(rs.getString("module") == null ? "" : rs.getString("module"));
				rowValue.add(rs.getString("paramName") == null ? "" : rs.getString("paramName"));
				rowValue.add(rs.getString("paramValue") == null ? "" : rs.getString("paramValue"));
				rowValue.add(rs.getString("memo") == null ? "" : rs.getString("memo"));
				rowValue.add(rs.getString("paramType") == null ? "" : rs.getString("paramType"));
				systemParams.add(rowValue);
			}
		} catch (SQLException e) {
			UtilLog.logError(" 获取系统参数SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 获取系统参数错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return systemParams;
	}

	// 修改系统参数
	public boolean modSystemParams(SystemParams systemParams) {
		boolean rs = true;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update  " + AppCon.TN_SystemParams + "  " + " set paramValue=? ,memo=? " + " where  module=? and paramName=?");

			ps.setString(1, systemParams.getParamValue());
			ps.setString(2, systemParams.getMemo());
			ps.setString(3, systemParams.getModule());
			ps.setString(4, systemParams.getParamName());
			ps.addBatch();

			ps.executeBatch();
		} catch (SQLException e) {
			UtilLog.logError("修改系统参数SQL错误:", e);
			return false;
		} catch (Exception e) {
			UtilLog.logError("修改系统参数错误:", e);
			return false;
		} finally {
			VariableApp.systemParamsValue = SystemParamsValueDao.getInstance().getSystemParamsValue();
			UtilSql.close(ps);
		}
		return rs;
	}

	// 获取系统参数对象
	public SystemParams getSystemParams(String module, String paramName) {
		SystemParams systemParams = new SystemParams();
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("SELECT paramValue,memo,paramType FROM  " + AppCon.TN_SystemParams + "  where module='" + module + "' and paramName='" + paramName + "' ");
			rs.last();
			if (rs.getRow() > 0) {
				systemParams.setModule(module);
				systemParams.setParamName(paramName);
				systemParams.setParamValue(rs.getString("paramValue"));
				systemParams.setMemo(rs.getString("memo"));
				systemParams.setParamType(Integer.valueOf(UtilString.isNil(rs.getString("paramType"), "0")));
			}
		} catch (Exception e) {
			UtilLog.logError("获取系统参数数据集错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return systemParams;
	}

	// 获取系统参数list
	public List<String> getSystemParamsKey(int flag) {
		Statement sm = null;
		ResultSet rs = null;
		List<String> list = null;
		try {
			sm = con.createStatement();// distinct 采用默认方式
			switch (flag) {
			case 0: {
				rs = sm.executeQuery("SELECT distinct module as f  FROM  " + AppCon.TN_SystemParams + "  where paramtype=0 order by module ");
				break;
			}
			case 1: {
				rs = sm.executeQuery("SELECT distinct  paramName as f FROM  " + AppCon.TN_SystemParams + "  where paramtype=0  ORDER BY paramName ");
				break;
			}
			}

			list = new LinkedList<String>();
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("f"));
			}
		} catch (SQLException e) {
			UtilLog.logError("获取系统参数数据集SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("获取系统参数数据集错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return list;
	}
}
