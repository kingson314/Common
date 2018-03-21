package module.systemparam;

//系统参数Bean
public class SystemParams {
	// 系统参数所在模块
	private String module;
	// 系统参数名称
	private String paramName;
	// 系统参数值
	private String paramValue;
	// 系统参数说明
	private String memo;
	// 系统参数类型
	private int paramType;

	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
