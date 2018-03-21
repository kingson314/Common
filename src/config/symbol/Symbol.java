package config.symbol;

/**
 * @msg :货币信息Bean
 * @date :2013-01-13
 * 
 */
public class Symbol {
	// 序列号
	private String id;
	// 服务器名称
	private String server;
	// 货币对名称
	private String symbol;
	// 点差
	private double point;
	// 小数位数
	private int digits;

	// 状态 0 表示正常 1 表示不可用
	private int state;
	// 排序
	private int ord;
	// 说明
	private String memo;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}
}
