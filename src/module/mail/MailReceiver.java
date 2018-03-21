package module.mail;
//收件人Bean
public class MailReceiver {
	// 收件人邮箱地址
	private String mailEmail;
	// 收件人姓名
	private String mailReceiver;

	public String getMailEmail() {
		return mailEmail;
	}

	public void setMailEmail(String mailEmail) {
		this.mailEmail = mailEmail;
	}

	public String getMailReceiver() {
		return mailReceiver;
	}

	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}
}
