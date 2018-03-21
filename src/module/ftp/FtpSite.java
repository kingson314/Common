package module.ftp;

//FTP信息Bean
public class FtpSite {
	// FTP标志
	private Long ftpId;
	// FTP名称
	private String ftpName;
	// FTPip地址
	private String ftpIp;
	// FTP端口
	private String ftpPort;
	// FTP用户名
	private String ftpUser;
	// FTP密码
	private String ftpPassword;
	// FTP默认路径
	private String ftpFolder;
	// FTP说明
	private String ftpInfo;
	// FTP类型
	private String ftpType;

	public String getFtpType() {
		return ftpType;
	}

	public void setFtpType(String ftpType) {
		this.ftpType = ftpType;
	}

	public Long getFtpId() {
		return ftpId;
	}

	public void setFtpId(Long ftpId) {
		this.ftpId = ftpId;
	}

	public String getFtpName() {
		return ftpName;
	}

	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpFolder() {
		return ftpFolder;
	}

	public void setFtpFolder(String ftpFolder) {
		this.ftpFolder = ftpFolder;
	}

	public String getFtpInfo() {
		return ftpInfo;
	}

	public void setFtpInfo(String ftpInfo) {
		this.ftpInfo = ftpInfo;
	}

}
