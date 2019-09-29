package config.dictionary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import common.util.bean.KeyValue;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

/**
 * 
 * @msg :字典信息据库操作
 * @date :2013-01-13
 */
public class DictionaryDao {
    public static DictionaryDao dao = null;
    private Connection con;
    private static HashMap<String, HashMap<String, String>> map;

    public static DictionaryDao getInstance() {
	if (dao == null)
	    dao = new DictionaryDao();
	return dao;
    }

    // 构造
    private DictionaryDao() {
	this.con = UtilJDBCManager.getConnection(Const.DbName);
    }

    public String getValue(String groupName, String name) {
	if (map == null)
	    map = new HashMap<String, HashMap<String, String>>();
	if (map.get(groupName) == null) {
	    String sql = "select name,value  from ts_dictionary  where groupname='" + groupName + "' order by ord ";
	    System.out.println(sql);
	    HashMap<String, String> mapName = new HashMap<String, String>();
	    List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
	    for (HashMap<String, Object> mapTmp : list) {
		mapName.put(mapTmp.get("NAME").toString(), mapTmp.get("VALUE").toString());
	    }
	    map.put(groupName, mapName);
	}
	return map.get(groupName).get(name);

    }

    // public String[][] getDicionary(String groupName) {
    // String sql = "select name,value from ts_dictionary where groupname='"
    // +
    // groupName + "' and state=0 order by ord";
    // List<HashMap<String, Object>> list = Sql.QueryM(con, sql);
    // if (list == null)
    // return null;
    // int size = list.size();
    // String[][] dic = new String[size][2];
    // for (int i = 0; i < list.size(); i++) {
    // dic[i][0] = list.get(i).get("NAME").toString();
    // dic[i][1] = list.get(i).get("VALUE").toString();
    // }
    // return dic;
    // }
    public Vector<?> getDicionary(String groupName) {
	String sql = "select ''name,''value,-1 ord  from dual union all select name,value ,ord from ts_dictionary  where groupname='" + groupName + "' and state=0 order by ord";
	List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
	Vector<KeyValue> vector = new Vector<KeyValue>();
	if (list != null) {
	    for (HashMap<String, Object> map : list) {
		vector.addElement(new KeyValue(map.get("NAME").toString(), map.get("VALUE").toString()));
	    }
	}
	return vector;
    }

    public String[] getDicionaryArr(String groupName) {
	String sql = "select name,value  from ts_dictionary  where groupname='" + groupName + "' and state=0 order by ord";
	List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
	String[] arr = new String[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    arr[i] = list.get(i).get("VALUE").toString();
	}
	return arr;
    }

    public String[] getGroupArr() {
	return DictionaryDao.getInstance().getArr("groupName");
    }

    public String[] getNameArr() {
	return DictionaryDao.getInstance().getArr("name");
    }

    // public String[] getNameArrByGroup(String groupName) {
    // return
    // DictionaryDao.getInstance().getDicionary(groupName)[0];
    // }

    private String[] getArr(String field) {
	String sql = " select distinct " + field + " ,1 ord from ts_dictionary  order by ord ," + field;
	// System.out.println(sql);
	List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
	if (list == null)
	    return null;
	int size = list.size();
	String[] dic = new String[size+1];
	dic[0]="全部";
	for (int i = 0; i < list.size(); i++) {
	    dic[i+1] = list.get(i).get(field.toUpperCase()).toString();
	}
	return dic;
    }

    // 获取信息表格
    public Vector<Vector<String>> getVector(String group, String name) {
	Statement sm = null;
	ResultSet rs = null;
	Vector<Vector<String>> vector = new Vector<Vector<String>>();
	try {
	    sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    String sql = "select * from ts_dictionary where 1=1 ";
	    if (!"".equals(UtilString.iif("全部".equals(group), "", group)))
		sql = sql + " and groupName='" + group + "'";
	    if (!"".equals(UtilString.iif("全部".equals(name), "", name)))
		sql = sql + " and name='" + name + "'";
	    sql = sql + " order by groupname,ord";
	    // System.out.println(sql);
	    rs = sm.executeQuery(sql);
	    while (rs.next()) {
		Vector<String> rowValue = new Vector<String>();
		rowValue.add(UtilString.isNil(rs.getString("Id")));
		rowValue.add(UtilString.isNil(rs.getString("groupName")));
		rowValue.add(UtilString.isNil(rs.getString("name")));
		rowValue.add(UtilString.isNil(rs.getString("value")));
		rowValue.add(UtilString.isNil(rs.getString("ord")));
		rowValue.add(UtilString.isNil(rs.getString("state")));
		rowValue.add(UtilString.isNil(rs.getString("memo")));
		vector.add(rowValue);
	    }
	} catch (Exception e) {
	    UtilLog.logError("获取信息错误:", e);
	} finally {
	    UtilSql.close(rs, sm);
	}
	return vector;
    }

    // 新增账户信息
    public void addDictionary(Dictionary bean) throws Exception {
	String sql = "insert into ts_dictionary(name,groupName,value,ord,state,memo)values (?,?,?,?,?,?)";
	UtilSql.executeUpdate(con, sql, new Object[] { bean.getName(), bean.getGroupName(), bean.getValue(), bean.getOrd(), bean.getState(), bean.getMemo() });
    }

    // 修改账户信息
    public void modDictionary(Dictionary bean) throws Exception {
	String sql = "update ts_dictionary set name=?,groupName=?,value=?,ord=?,state=?,memo=?" + "  where  id='" + bean.getId() + "'";
	UtilSql.executeUpdate(con, sql, new Object[] { bean.getName(), bean.getGroupName(), bean.getValue(), bean.getOrd(), bean.getState(), bean.getMemo() });
    }

    // 删除账户信息
    public void delDictionary(String id) {
	try {
	    String sql = " delete from ts_dictionary where id='" + id + "'";
	    UtilSql.executeUpdate(con, sql, new Object[0]);
	} catch (Exception e) {
	    UtilLog.logError(" 删除信息错误:", e);
	}
    }

}
