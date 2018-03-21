package module.ftp;

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

public class FtpDao {
	public static FtpDao ftpdao = null;
	private Connection con;

	public static FtpDao getInstance() {
		if (ftpdao == null)
			ftpdao = new FtpDao();
		return ftpdao;
	}

	// 构造
	private FtpDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconApp);
	}

	// 获取ftp信息表格数组
	public Vector<?> getFtpSiteVector() {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> ftpSiteVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from   " + AppCon.TN_Ftpsite + "  order by ftpid");

			while (rs.next()) {
				FtpSite ftpSite = new FtpSite();
				ftpSite.setFtpId(new Long(rs.getInt("ftpid")));
				ftpSite.setFtpName(rs.getString("ftpname"));
				ftpSite.setFtpType(rs.getString("ftpType") == null ? "" : rs.getString("ftpType"));
				ftpSite.setFtpIp(rs.getString("ftpip"));
				ftpSite.setFtpPort(rs.getString("ftpport"));
				ftpSite.setFtpUser(rs.getString("ftpuser"));
				ftpSite.setFtpPassword(rs.getString("ftppassword"));
				ftpSite.setFtpFolder(rs.getString("ftpfolder"));
				ftpSite.setFtpInfo(rs.getString("ftpinfo"));

				Vector<String> rowValue = new Vector<String>();
				rowValue.add(String.valueOf(ftpSite.getFtpId()));
				rowValue.add(ftpSite.getFtpName());
				rowValue.add(ftpSite.getFtpType());
				rowValue.add(ftpSite.getFtpIp());
				rowValue.add(ftpSite.getFtpPort());
				rowValue.add(ftpSite.getFtpUser());
				rowValue.add(ftpSite.getFtpPassword());
				rowValue.add(ftpSite.getFtpFolder());
				rowValue.add(ftpSite.getFtpInfo());
				ftpSiteVector.add(rowValue);
			}
		} catch (SQLException e) {
			UtilLog.logError("获取FTP表格参数列表SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("获取FTP表格参数列表错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return ftpSiteVector;
	}

	// 获取ftp信息 list
	public List<FtpSite> getFtpSiteList() {
		Statement sm = null;
		ResultSet rs = null;
		List<FtpSite> ftpSiteList = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_Ftpsite + "  order by ftpid");
			ftpSiteList = new LinkedList<FtpSite>();
			while (rs.next()) {
				FtpSite ftpSite = new FtpSite();
				ftpSite.setFtpId(new Long(rs.getInt("ftpid")));
				ftpSite.setFtpName(rs.getString("ftpname"));
				ftpSite.setFtpType(rs.getString("ftpType") == null ? "" : rs.getString("ftpType"));
				ftpSite.setFtpIp(rs.getString("ftpip"));
				ftpSite.setFtpPort(rs.getString("ftpport"));
				ftpSite.setFtpUser(rs.getString("ftpuser"));
				ftpSite.setFtpPassword(rs.getString("ftppassword"));
				ftpSite.setFtpFolder(rs.getString("ftpfolder"));
				ftpSite.setFtpInfo(rs.getString("ftpinfo"));
				ftpSiteList.add(ftpSite);
			}
		} catch (SQLException e) {
			UtilLog.logError("获取FTP信息列表SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("获取FTP信息列表错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return ftpSiteList;
	}

	// 获取ftp信息对象
	public FtpSite getFtpSiteByName(String ftpNameStr) {
		Statement sm = null;
		ResultSet rs = null;
		FtpSite ftpSite = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_Ftpsite + "  where ftpname='" + ftpNameStr + "'");
			rs.last();
			if (rs.getRow() > 0) {
				ftpSite = new FtpSite();
				ftpSite.setFtpId(new Long(rs.getInt("ftpid")));
				ftpSite.setFtpName(rs.getString("ftpname"));
				ftpSite.setFtpType(rs.getString("ftpType") == null ? "" : rs.getString("ftpType"));
				ftpSite.setFtpIp(rs.getString("ftpip"));
				ftpSite.setFtpPort(rs.getString("ftpport"));
				ftpSite.setFtpUser(rs.getString("ftpuser"));
				ftpSite.setFtpPassword(rs.getString("ftppassword"));
				ftpSite.setFtpFolder(rs.getString("ftpfolder"));
				ftpSite.setFtpInfo(rs.getString("ftpinfo"));
			}
		} catch (SQLException e) {
			UtilLog.logError("根据FTP名称获取FTP信息SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("根据FTP名称获取FTP信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return ftpSite;
	}

	// 判断是否存在ftp
	public boolean getFtpSiteID(Long FtpID) {
		Statement sm = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_Ftpsite + "  where FtpID=" + FtpID);
			rs.last();
			if (rs.getRow() > 0)
				result = true;
		} catch (SQLException e) {
			UtilLog.logError("根据FTP.ID获取FTP信息SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("根据FTP.ID获取FTP信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return result;
	}

	// 新增ftp信息
	public void addFtpSite(FtpSite ftpSite) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("insert into  " + AppCon.TN_Ftpsite + " (ftpId,ftpName,ftpIp,ftpPort,ftpUser," + "ftpPassword,ftpFolder,ftpInfo,ftpType)" + "values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, String.valueOf(ftpSite.getFtpId()));
			ps.setString(2, ftpSite.getFtpName());
			ps.setString(3, ftpSite.getFtpIp());
			ps.setString(4, ftpSite.getFtpPort());
			ps.setString(5, ftpSite.getFtpUser());
			ps.setString(6, ftpSite.getFtpPassword());
			ps.setString(7, ftpSite.getFtpFolder());
			ps.setString(8, ftpSite.getFtpInfo());
			ps.setString(9, ftpSite.getFtpType());
			ps.addBatch();
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			UtilLog.logError("新增FTP SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("新增FTP错误:", e);
		} finally {
			UtilSql.close(rs, ps);
		}
	}

	// 修改ftp信息
	public void modFtpSite(FtpSite ftpSite) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("update   " + AppCon.TN_Ftpsite + "  set " + "ftpName=?,ftpIp=?,ftpPort=?,ftpUser=?," + "ftpPassword=?,ftpFolder=?,ftpInfo=?,ftpType=? " + " where ftpid="
					+ ftpSite.getFtpId());
			ps.setString(1, ftpSite.getFtpName());
			ps.setString(2, ftpSite.getFtpIp());
			ps.setString(3, ftpSite.getFtpPort());
			ps.setString(4, ftpSite.getFtpUser());
			ps.setString(5, ftpSite.getFtpPassword());
			ps.setString(6, ftpSite.getFtpFolder());
			ps.setString(7, ftpSite.getFtpInfo());
			ps.setString(8, ftpSite.getFtpType());
			ps.addBatch();
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			UtilLog.logError("修改FTP SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("修改FTP错误:", e);
		} finally {
			UtilSql.close(rs, ps);
		}
	}

	// 删除ftp信息
	public void delFtpSite(FtpSite ftpSite) {
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sm.addBatch("delete from  " + AppCon.TN_Ftpsite + "  where ftpid=" + ftpSite.getFtpId());
			sm.executeBatch();
			con.commit();
		} catch (SQLException e) {
			UtilLog.logError("删除FTP SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError("删除FTP错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
	}
}
