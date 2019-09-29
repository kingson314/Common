package module.systemparam;

//程序自动更新
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import app.AppCon;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;

public class SysAutoUpdate {
	private Connection con;
	public static SysAutoUpdate sysAutoUpdate = null;

	public static SysAutoUpdate getInstance() {
		if (sysAutoUpdate == null)
			sysAutoUpdate = new SysAutoUpdate();
		return sysAutoUpdate;
	}

	// 构造
	private SysAutoUpdate() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconApp);
	}

	// 自动更新
	@SuppressWarnings("resource")
	public boolean autoUpdate() {
		Statement sm = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// 20120706
			rs = sm.executeQuery("select module,paramName,paramValue from  " + AppCon.TN_SystemParams + "  where module='LogParam' and  paramName='日志记录器级别' ");
			rs.last();
			if (rs.getRow() == 0) {
				ps = con.prepareStatement("insert into  " + AppCon.TN_SystemParams + " (module,paramName,paramValue,memo,paramtype)" + "values(?,?,?,?,?)");
				ps.setString(1, "LogParam");
				ps.setString(2, "日志记录器级别");
				ps.setString(3, "");
				ps.setString(4, "包含0:记录错误日志；包含1:记录警告日志;包含2:记录信息日志；包含3:记录调试日志；为空时:记录所有日志");
				ps.setInt(5, 0);
				ps.addBatch();
				ps.executeBatch();
			}

			try {
				rs = sm.executeQuery("select taskOrder from  " + AppCon.TN_Task + "  where 1=2 ");
			} catch (Exception e) {
				sm.addBatch("alter table  " + AppCon.TN_Task + "  add taskOrder varchar");
				sm.executeBatch();
			}

			try {
				rs = sm.executeQuery("select * from  " + AppCon.TN_Monitor + "  where 1=2 ");
			} catch (Exception e) {
				try {
					sm.addBatch("create table  " + AppCon.TN_Monitor + " (" + "mCode varchar primary key,mName varchar,mTel varchar,mMailAddress varchar )");
					sm.executeBatch();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			// 2012-09-18
			try {
				rs = sm.executeQuery("select nowDate from  " + AppCon.TN_ScheParam + "  where 1=2 ");
			} catch (Exception e) {
				sm.addBatch("alter table  " + AppCon.TN_ScheParam + "  add nowDate varchar");
				sm.executeBatch();
			}
			{// 2012-09-18
				rs = sm.executeQuery("select module,paramName,paramValue from  " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='当前执行日期数据库连接' ");
				rs.last();
				if (rs.getRow() == 0) {
					ps = con.prepareStatement("insert into  " + AppCon.TN_SystemParams + " (module,paramName,paramValue,memo,paramtype)" + "values(?,?,?,?,?)");
					ps.setString(1, "系统参数");
					ps.setString(2, "当前执行日期数据库连接");
					ps.setString(3, "");
					ps.setString(4, "之前命名为：预设历史日期所在数据库连接名");
					ps.setInt(5, 0);
					ps.addBatch();
					ps.executeBatch();

					ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='预设历史日期所在数据库连接名' ");
					ps.addBatch();
					ps.executeBatch();
				}
			}
			{// 2012-09-20
				rs = sm.executeQuery("select module,paramName,paramValue from  " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='日期类型' ");
				rs.last();
				if (rs.getRow() == 0) {
					ps = con.prepareStatement("insert into  " + AppCon.TN_SystemParams + " (module,paramName,paramValue,memo,paramtype)" + "values(?,?,?,?,?)");
					ps.setString(1, "系统参数");
					ps.setString(2, "日期类型");
					ps.setString(3, "全部;\n沪深交易日,CHEXCH;\n沪深节假日,CHEXCH;\n香港交易日,HK;\n香港节假日,HK;\n银行间交易日,CHBANK;\n银行间节假日,CHBANK;\n工作日(周一至周五);\n节假日(周六周日)");
					ps.setString(4, "日期类型对应t_conf_calendar");
					ps.setInt(5, 0);
					ps.addBatch();
					ps.executeBatch();
				}
			}

			{// 2012-09-21
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='当前执行日期数据库连接' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='程序标题' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='是否使用预设历史日期' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='系统版本号' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='发布日期' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='任务使用权限' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='日志文件' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='是否自动刷新执行结果' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='系统参数' and  paramName='是否记录执行成功的任务日志' ");
				ps.addBatch();
				ps.executeBatch();
				ps = con.prepareStatement("delete from   " + AppCon.TN_SystemParams + "  where module='日志参数' and  paramName='是否记录执行日志(文件)' ");
				ps.addBatch();
				ps.executeBatch();
			}
		} catch (Exception e) {
			UtilLog.logError("自动更新系统参数错误:", e);
			return false;
		} finally {
			UtilSql.close(rs, sm, ps);
		}
		return true;
	}
}
