package module.systemparam;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.AppCon;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;

public class SystemParamsValueDao {
	private Connection con;
	private static SystemParamsValueDao dao = null;

	// 系统参数
	public static SystemParamsValueDao getInstance() {
		if (dao == null)
			dao = new SystemParamsValueDao();
		return dao;
	}

	// 构造
	private SystemParamsValueDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconApp);
	}

	// 系统参数:系统参数值类，每增加一个系统参数得维护此类
	public SystemParamsValue getSystemParamsValue() {
		SystemParamsValue systemParamsValue = new SystemParamsValue();
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select module,paramName,paramValue from  " + AppCon.TN_SystemParams + "    ");
			while (rs.next()) {
				String mudule = rs.getString("module");
				String paramName = rs.getString("paramName");
				String paramVale = UtilString.isNil(rs.getString("paramValue"));
				if (mudule.equalsIgnoreCase("短信报警")) {
					if (paramName.equalsIgnoreCase("短信发送数据库链接"))
						systemParamsValue.setDbLink(paramVale);
					else if (paramName.equalsIgnoreCase("报警间隔时间(分钟)")) {
						systemParamsValue.setInterval(paramVale.equals("") ? "0" : paramVale);
					} else if (paramName.equalsIgnoreCase("存储过程名"))
						systemParamsValue.setListener_Proc_Name(paramVale);
					else if (paramName.equalsIgnoreCase("电话号码串"))
						systemParamsValue.setTel(paramVale);
					else if (paramName.equalsIgnoreCase("短信发送服务名称"))
						systemParamsValue.setMessageServerName(paramVale);
					else if (paramName.equalsIgnoreCase("发送时间"))
						systemParamsValue.setSendTime(paramVale);
					else if (paramName.equalsIgnoreCase("回溯能力"))
						systemParamsValue.setTraceId(paramVale);
					else if (paramName.equalsIgnoreCase("是否启动短信报警"))
						systemParamsValue.setAutoSend(paramVale.equals("") ? "0" : paramVale);
					else if (paramName.equalsIgnoreCase("下行截止时间"))
						systemParamsValue.setCloseTime(paramVale);
				} else if (mudule.equalsIgnoreCase("执行任务")) {
					if (paramName.equalsIgnoreCase("是否显示执行结果"))
						systemParamsValue.setShowExecTaskresult(paramVale.equals("") ? "0" : paramVale);
				} else if (mudule.equalsIgnoreCase("仿真模拟")) {
					if (paramName.equalsIgnoreCase("行情回归时间"))
						systemParamsValue.setReturnTime(paramVale.equals("") ? "00:00" : paramVale);
				} else if (mudule.equalsIgnoreCase("系统参数")) {
					if (paramName.equalsIgnoreCase("是否自动刷新调度状态"))
						systemParamsValue.setAutoRefreshScheResult(paramVale.equals("") ? "0" : paramVale);
					else if (paramName.equalsIgnoreCase("日期类型"))
						systemParamsValue.setDateType(UtilString.isNil(paramVale));
				} else if (mudule.equalsIgnoreCase("日志参数")) {
					if (paramName.equalsIgnoreCase("是否记录行情监控日志"))
						systemParamsValue.setIfLogMonitor(paramVale.equals("") ? "0" : paramVale);
					else if (paramName.equalsIgnoreCase("高频任务日志摘要写库次数值"))
						systemParamsValue.setLogAbridgement_count(paramVale.equals("") ? "0" : paramVale);
					else if (paramName.equalsIgnoreCase("高频任务最近日志记录数"))
						systemParamsValue.setLogLately_count(paramVale.equals("") ? "100" : paramVale);

					else if (paramName.equalsIgnoreCase("日志级别"))
						systemParamsValue.setLogLevel(paramVale.equals("") ? "1" : paramVale);
					else if (paramName.equalsIgnoreCase("日志记录器级别"))
						systemParamsValue.setLoggerLevel(paramVale);
					else if (paramName.equalsIgnoreCase("调度最近日志记录数"))
						systemParamsValue.setLogSche_count(paramVale.equals("") ? "10" : paramVale);
				}
				if (mudule.equalsIgnoreCase("邮件报警")) {
					if (paramName.equalsIgnoreCase("发送邮箱地址"))
						systemParamsValue.setMonitorMailAdress(paramVale);
				}
			}
		} catch (SQLException e) {
			UtilLog.logError(" 获取系统参数SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 获取系统参数错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return systemParamsValue;
	}

}
