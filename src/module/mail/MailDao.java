package module.mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import app.AppCon;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;

public class MailDao {
	public static MailDao maildao = null;
	private Connection con;

	public static MailDao getInstance() {
		if (maildao == null)
			maildao = new MailDao();
		return maildao;
	}

	// 构造
	private MailDao() {
		this.con = UtilJDBCManager.getConnection(AppCon.DbconApp);
	}

	// 获取邮箱信息表格数组
	public Vector<Vector<String>> getMailSenderVector() {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> mailVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_MailSender);
			while (rs.next()) {
				MailSender mailSender = new MailSender();
				mailSender.setMailPort(rs.getString("port"));
				mailSender.setMailSendAdreess(rs.getString("address"));
				mailSender.setMailSendPassword(rs.getString("password"));
				mailSender.setMailSendUserName(rs.getString("username"));
				mailSender.setMailServer(rs.getString("server"));
				mailSender.setMailValidate(rs.getString("validate"));

				Vector<String> rowValue = new Vector<String>();

				rowValue.add(mailSender.getMailSendAdreess());
				rowValue.add(mailSender.getMailSendUserName());
				rowValue.add(mailSender.getMailSendPassword());
				rowValue.add(mailSender.getMailServer());
				mailVector.add(rowValue);
			}
		} catch (SQLException e) {
			UtilLog.logError(" 获取发件人邮件表格信息SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 获取发件人邮件表格信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return mailVector;
	}

	// 获取发件人
	public MailSender getMailSenderByAddress(String address) {
		Statement sm = null;
		ResultSet rs = null;
		MailSender mailSender = null;
		try {
			if (address.lastIndexOf(";") > 0)
				address = address.substring(0, address.length() - 1);
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_MailSender + "  where address='" + address + "'");
			while (rs.next()) {
				mailSender = new MailSender();
				mailSender.setMailPort(rs.getString("port"));
				mailSender.setMailSendAdreess(rs.getString("address"));
				mailSender.setMailSendPassword(rs.getString("password"));
				mailSender.setMailSendUserName(rs.getString("username"));
				mailSender.setMailServer(rs.getString("server"));
				mailSender.setMailValidate(rs.getString("validate"));
			}
		} catch (SQLException e) {
			UtilLog.logError(" 获取发送人邮件信息SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 获取发送人邮件信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return mailSender;
	}

	// 判断邮箱信息是否存在
	public boolean mailSenderIsExist(String address) {
		Statement sm = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery("select * from  " + AppCon.TN_MailSender + "  where address='" + address + "'");
			rs.last();
			if (rs.getRow() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			UtilLog.logError(" 判断发件人是否存在SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 判断发件人是否存在错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return result;
	}

	// 新增邮箱信息
	public void addMail(MailSender mail) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("insert into  " + AppCon.TN_MailSender + " (" + "port,server,\"validate\",username," + "password,address) values (?,?,?,?,?,?)");
			System.out.println("insert into  " + AppCon.TN_MailSender + " (" + "port,server,\"validate\",username," + "password,address) values (?,?,?,?,?,?)");
			ps.setString(1, mail.getMailPort());
			ps.setString(2, mail.getMailServer());
			ps.setString(3, mail.getMailValidate());
			ps.setString(4, mail.getMailSendUserName());
			ps.setString(5, mail.getMailSendPassword());
			ps.setString(6, mail.getMailSendAdreess());

			ps.addBatch();
			ps.executeBatch();
		} catch (SQLException e) {
			UtilLog.logError(" 新增发件人邮件SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 新增发件人邮件错误:", e);
		} finally {
			UtilSql.close(rs, ps);
		}
	}

	// 修改邮箱信息
	public void modmail(MailSender mail) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("update     " + AppCon.TN_MailSender + "  set " + "port=?,server=?,\"validate\"=?,username=?," + "password=? " + " where  address='" + mail.getMailSendAdreess()
					+ "'");
			ps.setString(1, mail.getMailPort());
			ps.setString(2, mail.getMailServer());
			ps.setString(3, mail.getMailValidate());
			ps.setString(4, mail.getMailSendUserName());
			ps.setString(5, mail.getMailSendPassword());

			ps.addBatch();
			ps.executeBatch();
		} catch (SQLException e) {
			UtilLog.logError(" 修改发件人邮件SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 修改发件人邮件错误:", e);
		} finally {
			UtilSql.close(rs, ps);
		}
	}

	// 删除邮箱信息
	public void delmail(MailSender mail) {
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sm.addBatch("delete from   " + AppCon.TN_MailSender + "  where address='" + mail.getMailSendAdreess() + "'");
			sm.executeBatch();
		} catch (SQLException e) {
			UtilLog.logError(" 删除发件人邮件SQL错误:", e);
		} catch (Exception e) {
			UtilLog.logError(" 删除发件人邮件错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
	}
}
