package module.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import common.util.log.UtilLog;
import common.util.security.UtilCrypt;
import consts.VariableApp;

//发件人Bean
public class MailSend {
	// 发送者的用户名
	private String username;
	// 发送者的密码
	private String password;
	// 用于发送EMAIL的发送者地址
	private String address;
	private String status;
	private String failReason;

	// public static void main(String[] arg) {
	// MailSender mail = new MailSender();
	// mail.setMailPort("25");
	// mail.setMailSendAdreess("fenggq@dev.com");
	// mail.setMailSendPassword("fenggq");
	// mail.setMailSendUserName("fenggq");
	// mail.setMailServer("192.1.50.1");
	// mail.setMailValidate("true");
	// String[] file = new String[1];
	// file[0] = "D://交易数据处理设计文档.doc";
	// new MailSend(mail, "fenggq@dev.com", null, "test", "justtest");
	// }

	public MailSend(MailSender mail, String receiver, String[] files, String subject, String content) {
		try {
			UtilCrypt crypt = UtilCrypt.getInstance();
			boolean validate = mail.getMailValidate() == null ? false : Boolean.valueOf(mail.getMailValidate());
			Properties ps = new Properties();
			ps.put("mail.smtp.host", mail.getMailServer());
			ps.put("mail.smtp.port", mail.getMailPort());

			ps.put("mail.smtp.auth", validate ? "true" : "false");
			Session session = Session.getInstance(ps, new Authentic());
			setAddress(mail.getMailSendAdreess());
			setUsername(mail.getMailSendUserName());
			setPassword(crypt.decryptAES(mail.getMailSendPassword(), UtilCrypt.key));
			Message rs = new MimeMessage(session);

			Address from = new InternetAddress(address);
			rs.setFrom(from); // 发送地址
			rs.setRecipient(RecipientType.TO, new InternetAddress(receiver));// 接收地址
			rs.setSubject(subject); // 邮件主题
			Multipart mp = new MimeMultipart();
			BodyPart body = new MimeBodyPart();
			body.setContent(content.replace("\n", "<br>"), "text/html; charset=GBK"); // 邮件HTML内容
			mp.addBodyPart(body);
			if (files != null && files.length > 0) { // 邮件附件
				for (int i = 0; i < files.length; i++) {
					if (files[i] == null)
						continue;
					MimeBodyPart mbp = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(files[i]);
					mbp.setDataHandler(new DataHandler(fds));
					File sfile = new File(files[i]);
					// mbp.setFileName(sfile.getName());
					mbp.setFileName(MimeUtility.encodeText(sfile.getName()));
					mp.addBodyPart(mbp);
					// System.out.println(sfile.getName());
				}
			}

			rs.setContent(mp); // 邮件全部内容
			rs.setSentDate(new Date()); // 发送时间
			Transport.send(rs); // 发送
			this.setStatus("执行成功");
			this.setFailReason("");
		} catch (Exception e) {
			this.setStatus("执行失败");
			this.setFailReason("发送邮件失败:" + UtilLog.getStrackTrace(e, VariableApp.systemParamsValue.getLogLevel()));
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// 验证密码
	class Authentic extends Authenticator {

		public Authentic() {
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}

	}

}
