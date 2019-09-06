package common.util.string;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.JTextComponent;

public class UtilString {

	public static String getGuid() {
		return UUID.randomUUID().toString();
	}
	/**
	 * @Description:
	 * @date Jan 27, 2014
	 * @author:fgq
	 */

	// 字符串相加
	public static String concat(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String str : strings)
			sb.append(str);
		return sb.toString();
	}

	// 判断字符串是佛所有都是数字
	public static boolean allIsNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	// 格式化顺序号
	public static String expandOrder(String curOrder, int orderLen) {
		String newOrder = curOrder;
		while (newOrder.length() < orderLen) {
			newOrder = "0" + newOrder;
		}
		return newOrder;
	}

	// 去掉文件中相同的行
	public static String lineFilter(String filePath) throws IOException {
		String rs = "";
		String line = "";
	// 读取文件原有内容
		@SuppressWarnings("resource")
		BufferedReader fReader = new BufferedReader(new FileReader(filePath));
		Map<String, Integer> map = new HashMap<String, Integer>();
		while ((line = fReader.readLine()) != null) {
			if (map.get(line.trim()) != null)
				continue;
			else
				map.put(line.trim(), 1);

			rs = rs + line + "\n";
		}
		return rs;
	}

	// TextField 插入字符串// TextArea 插入字符串
	public static void addString(JTextComponent com, String str) {
		int position = com.getCaretPosition();
		String text = com.getText();
		text = text.substring(0, position) + str + text.substring(position, text.length());
		com.setText(text);
		com.setCaretPosition(position);
	}

	// 判断null字符串返回指定值
	public static String isNil(String str, String then) {
		if (str == null)
			return then;
		else if (str.equals(""))
			return then;
		return str;
	}
	// 判断null字符串返回指定值
	public static String isNil(Object str, String then) {
		if (str == null)
			return then;
		else if (str.equals(""))
			return then;
		return str.toString();
	}
	// 判断null字符串返回空值
	public static String isNil(String str) {
		if (str == null)
			return "";
		return str;
	}

	// 判断null字符串返回空值
	public static boolean isBlank(String str) {
		if (str == null ||str.equals(""))
			return true;
		return false;
	}

	
	// 判断null对象返回空值
	public static String isNil(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	// 判断取值
	public static String iif(boolean b1, String then, String elseThen) {
		String rs = "";
		try {
			if (b1 == true)
				rs = then;
			else
				rs = elseThen;
		} catch (Exception e) {

		}
		return rs;
	}

	// replaceString说明 replaceString : 需被替换的字符串 tostr :替换replaceString中某一个位置的元素
	// index : 替换位置
	public static String replaceString(String replaceString, String tostr, int index) {
		String rs = "";
		String begin = replaceString.substring(0, index);
		String end = replaceString.substring(index + 1, replaceString.length());
		rs = begin + tostr + end;
		return rs;
	}

	// 获取指定字符在数组中的索引
	public static int getIndex(String[] var, String item) {
		int rs = -1;
		for (int i = 0; i < var.length; i++) {
			if (var[i].equalsIgnoreCase(item.trim())) {
				rs = i;
				break;
			}
		}
		return rs;
	}

	// 分割字符串[,];形式,取某一维度
	public static String[] splitStr(String str, int dimension) {
		String[] rs = null;
		String[] strArray = str.split(";");
		int len = strArray.length;
		rs = new String[len];
		for (int i = 0; i < len; i++) {
			strArray[i] = strArray[i].replace("[", "");
			strArray[i] = strArray[i].replace("]", "").trim();
			String[] p = strArray[i].split(",");
			if (p.length > dimension)
				rs[i] = p[dimension];
			else
				rs[i] = "";
		}
		return rs;
	}

	// 获取sql参数映射
	public static Object[] getSqlRuleParam(String sqlrule) {
		String[] param = null;
		if (sqlrule == null)
			return null;
		if (sqlrule.trim().length() < 1)
			return null;
		String[] rule = sqlrule.split(";");
		int len = rule.length;

		param = new String[len];
		for (int i = 0; i < len; i++) {
			String[] p = rule[i].split(",");
			String pvalue = p[0].replace("[", "");
			pvalue = pvalue.replace("]", "");
			if (pvalue.indexOf("=") >= 0)
				pvalue = pvalue.substring(pvalue.indexOf("=") + 1);
			param[i] = pvalue.trim();
		}
		return param;
	}

	// 获取存储过程参数映射
	public static String[][] getProcedureParamsRule(String ParamsRule) {
		String[][] param = null;
		if (ParamsRule == null)
			return null;
		if (ParamsRule.trim().length() < 1)
			return null;
		String[] rule = ParamsRule.split(";");
		int len = rule.length;

		param = new String[len][5];
		for (int i = 0; i < len; i++) {
			rule[i] = rule[i].replace("[", "");
			rule[i] = rule[i].replace("]", "").trim();
			String[] p = rule[i].split(",");
			for (int j = 0; j < p.length; j++) {
				param[i][j] = p[j].trim();
			}
		}
		return param;
	}

	// 获取执行时段数组
	public static String[][] getExecTime(String execTime) {
		String[][] etime = null;
		if (execTime == null)
			return null;
		if (execTime.trim().length() < 1 || execTime.equalsIgnoreCase("全部"))
			return null;
		String[] execTimeArray = execTime.split(";");
		int len = execTimeArray.length;

		etime = new String[len][2];
		for (int i = 0; i < len; i++) {
			execTimeArray[i] = execTimeArray[i].replace("[", "");
			execTimeArray[i] = execTimeArray[i].replace("]", "").trim();
			String[] p = execTimeArray[i].split(",");
			if (p.length > 0)
				etime[i][0] = p[0];
			else
				etime[i][0] = "";
			if (p.length > 1)
				etime[i][1] = p[1];
			else
				etime[i][1] = "";
		}
		return etime;
	}

	// 分割字符串[,];形式
	public static String[][] splitStr(String str) {
		String[][] rs = null;
		String[] strArray = str.split(";");
		int len = strArray.length;
		rs = new String[len][2];
		for (int i = 0; i < len; i++) {
			strArray[i] = strArray[i].replace("[", "");
			strArray[i] = strArray[i].replace("]", "").trim();
			String[] p = strArray[i].split(",");
			if (p.length > 0)
				rs[i][0] = p[0];
			else
				rs[i][0] = "";
			if (p.length > 1)
				rs[i][1] = p[1];
			else
				rs[i][1] = "";
		}
		return rs;
	}


	/**
	 * @param
	 * @return 去除乱码后字符串
	 * @date 2013-02-29
	 */
	public static String getMessyCode(String str) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		StringBuilder sb = new StringBuilder();
		char[] ch = temp.trim().toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (Character.isLetterOrDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getUUID() {
		return java.util.UUID.randomUUID().toString();
	}
	/**
     * @return 形如 yyyyMMddHHmmssSSS-Z0000019558195832297 的(38位)保证唯一的递增的序列号字符串，
     * 主要用于数据库的主键，方便基于时间点的跨数据库的异步数据同步。
     * 前半部分是currentTimeMillis，后半部分是nanoTime（正数）补齐20位的字符串，
     * 如果通过System.nanoTime()获取的是负数，则通过nanoTime = nanoTime+Long.MAX_VALUE+1;
     * 转化为正数或零。
     */
    public static String getSeq(){
        long nanoTime = System.nanoTime();
        String preFix="";
        if (nanoTime<0){
            preFix="A";//负数补位A保证负数排在正数Z前面,解决正负临界值(如A9223372036854775807至Z0000000000000000000)问题。
            nanoTime = nanoTime+Long.MAX_VALUE+1;
        }else{
            preFix="Z";
        }
        String nanoTimeStr = String.valueOf(nanoTime);
         
        int difBit=String.valueOf(Long.MAX_VALUE).length()-nanoTimeStr.length();
        for (int i=0;i<difBit;i++){
            preFix = preFix+"0";
        }
        nanoTimeStr = preFix+nanoTimeStr;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS"); //24小时制
        String timeMillisSequence=sdf.format(System.currentTimeMillis())+"-"+nanoTimeStr; 
        return timeMillisSequence;      
    }
	public static String getClob(Clob clob) throws SQLException, IOException {
		if(clob==null)return"";
        BufferedReader br = new BufferedReader(clob.getCharacterStream());
        StringBuffer strbf = new StringBuffer();
        String str = "";
        while ((str = br.readLine()) != null) {
            strbf.append(str);
        }
        return strbf.toString();
    }

    public static byte[] getBlob(Blob blob) throws SQLException, IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        InputStream inputStream = blob.getBinaryStream();
        int b = inputStream.read();
        while (b != -1) {
            outStream.write(b);
            b = inputStream.read();
        }
        return outStream.toByteArray();
    }
    
	public static String upperCaseFirst(String string) {
		if(string==null || string.length()==0)return "";
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public static String lowerCaseFirst(String string) {
		return string.substring(0, 1).toLowerCase() + string.substring(1);
	}
	
	public static String rTrim(String data)
	{
	    if(data==null)
	        return null;
	    char[]arr=data.toCharArray();
	    char[] newArr=new char[1];
	    int pos=0;
	    for (int i = 0; i < arr.length; i++)
	    {
	        pos=(arr.length-1)-i;
	        if(arr[pos]!=' ')
	        {
	            newArr= new char[pos+1];
	            System.arraycopy(arr, 0, newArr, 0, pos+1);
	            break;
	        }
	    }
	    return new String(newArr);
	}
	
	
	public static int checkPassword(String passwordStr) {
		String regexZ = "\\d*";
		String regexS = "[a-zA-Z]+";
		String regexT = "\\W+$";
		String regexZT = "\\D*";
		String regexST = "[\\d\\W]*";
		String regexZS = "\\w*";
		String regexZST = "[\\w\\W]*";

		if (passwordStr==null || "".equals(passwordStr)) {
			return 1;
		}
		if (passwordStr.length()<6) {
			return 1;
		}
		if (passwordStr.matches(regexZ)) {
			return 1;
		}
		if (passwordStr.matches(regexS)) {
			return 1;
		}
		if (passwordStr.matches(regexT)) {
			return 1;
		}
		if (passwordStr.matches(regexZT)) {
			return 2;
		}
		if (passwordStr.matches(regexST)) {
			return 2;
		}
		if (passwordStr.matches(regexZS)) {
			return 2;
		}
		if (passwordStr.matches(regexZST)) {
			return 3;
		}
		return -1;
	}
	/**
	 * 下划线转驼峰
	 * @param str
	 * @return
	 */
	public static StringBuffer camel(StringBuffer str) {
		//利用正则删除下划线，把下划线后一位改成大写
		Pattern pattern = Pattern.compile("_(\\w)");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer(str);
		if(matcher.find()) {
			sb = new StringBuffer();
			//将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
			//正则之前的字符和被替换的字符
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
			//把之后的也添加到StringBuffer对象里
			matcher.appendTail(sb);			
		}else {
			return sb;
		}	
		return camel(sb);
	}
	/**
	 * 驼峰转下划线
	 * @param str
	 * @return
	 */
	public static StringBuffer underline(StringBuffer str) {
		Pattern pattern = Pattern.compile("[A-Z]");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer(str);
		if(matcher.find()) {
			sb = new StringBuffer();
			//将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
			//正则之前的字符和被替换的字符
			matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
			//把之后的也添加到StringBuffer对象里
			matcher.appendTail(sb);			
		}else {
			return sb;
		}	
		return underline(sb);
	}
	public static void main(String[] args) {
//		UUID uuid = java.util.UUID.randomUUID();
//		System.out.println(uuid);
//		System.out.println(uuid.toString().length());
//		System.out.println("96D143007A2245C8B9E1FD536D442C88".length());
		System.out.println(checkPassword("123456"));
		System.out.println(checkPassword("1aa23456"));
		System.out.println(checkPassword("12AA3456"));
		System.out.println(checkPassword("AAaa"));
		System.out.println(checkPassword("AAAss_11S$11"));
	}
}
