package module.datetype;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import app.AppCon;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

/**
 * 
 * @author fgq
 * @see 当前执行日期
 */
public class NowDateDao {
	private static NowDateDao dao;
	public static String nowDate = null;
	private Connection con;

	public static NowDateDao getInstance() {
		if (dao == null)
			dao = new NowDateDao();
		return dao;
	}

	// 构造
	private NowDateDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconNowDate);
	}

	// 获取当前执行日期
	public String getNowDate(boolean isnowDate, String eorroMsg) throws SQLException {
		if (isnowDate) {
			if ("".equals(UtilString.isNil(nowDate))) {
				nowDate = get_t_sys_Date();
			}
			if ("".equals(nowDate)) {
				UtilLog.logWarn(eorroMsg + "获取t_sys_date 为空");
			}
		} else {
			return UtilConver.dateToStr(Const.fm_yyyyMMdd);
		}
		return UtilString.isNil(nowDate);
	}

	// 读取数据库当前执行日期
	private String get_t_sys_Date() {
		String result = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select  DATE_FORMAT(max(t_date),'%Y%m%d')  tdate from t_sys_date where is_able='Y' ");
			rs.last();
			if (rs.getRow() > 0) {
				result = rs.getString("tdate");
			}
		} catch (Exception e) {
			return "";
		} finally {
			UtilSql.close(rs, sm, con);
		}
		return UtilString.isNil(result);
	}

	// 手动更新当前执行日期接口
	public void setNowDate() {
		try {
			nowDate = get_t_sys_Date();
		} catch (Exception e) {
			UtilLog.logError("更新当前执行日期错误", e);
		}
	}
}
