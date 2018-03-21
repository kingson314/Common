package module.mail;

//邮箱信息Bean
public class MailSender {
	// 发送人邮箱地址
	private String mailSendAdreess;
	// 发送人邮箱名称
	private String mailSendUserName;
	// 发送人邮箱密码
	private String mailSendPassword;
	// SMTP服务器是否要求验证
	private String mailValidate;
	// 邮件服务器SMTP地址
	private String mailServer;
	// 邮件服务器端口
	private String mailPort;

	public String getMailSendAdreess() {
		return mailSendAdreess;
	}

	public void setMailSendAdreess(String mailSendAdreess) {
		this.mailSendAdreess = mailSendAdreess;
	}

	public String getMailSendUserName() {
		return mailSendUserName;
	}

	public void setMailSendUserName(String mailSendUserName) {
		this.mailSendUserName = mailSendUserName;
	}

	public String getMailSendPassword() {
		return mailSendPassword;
	}

	public void setMailSendPassword(String mailSendPassword) {
		this.mailSendPassword = mailSendPassword;
	}

	public String getMailValidate() {
		return mailValidate;
	}

	public void setMailValidate(String mailValidate) {
		this.mailValidate = mailValidate;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getMailPort() {
		return mailPort;
	}

	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}

}
