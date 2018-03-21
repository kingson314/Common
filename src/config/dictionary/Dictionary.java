package config.dictionary;

/**
 * 
 * @msg :字典信息Bean
 * @date :2013-01-19
 */
public class Dictionary {
	// 序列号
	private String id;
	// 标志
	private String name;
	// 所属分组
	private String groupName;
	// 值
	private String value;
	// 状态 0 表示正常 1 表示不可用
	private int state;
	// 排序
	private int ord;
	// 说明
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
