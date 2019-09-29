package config.symbol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.util.bean.KeyValue;
import common.util.collection.UtilCollection;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

/**
 * 
 * @msg :货币信息数据库操作
 * @date :2013-01-13
 */
public class SymbolDao {
	private static HashMap<String, Symbol> MapSymbol = null;

	public static SymbolDao dao = null;

	private Connection con;

	public static SymbolDao getInstance() {
		if (dao == null)
			dao = new SymbolDao();
		return dao;
	}

	// 构造
	private SymbolDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	public HashMap<String, Symbol> getMapSymbol() {
		if (MapSymbol == null) {
			MapSymbol = new HashMap<String, Symbol>();
			Statement sm = null;
			ResultSet rs = null;
			try {
				sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = sm.executeQuery("select * from config_symbol where state=0");
				while (rs.next()) {
					Symbol symbol = new Symbol();
					symbol.setId(UtilString.isNil(rs.getString("Id")));
					symbol.setServer(UtilString.isNil(rs.getString("server")));
					symbol.setSymbol(UtilString.isNil(rs.getString("symbol")));
					symbol.setPoint(Double.valueOf(UtilString.isNil(rs.getString("point"), "0")));
					symbol.setDigits(Integer.valueOf(UtilString.isNil(rs.getString("digits"), "0")));
					symbol.setState(Integer.valueOf(UtilString.isNil(rs.getString("state"), "0")));
					symbol.setOrd(Integer.valueOf(UtilString.isNil(rs.getString("ord"), "0")));
					symbol.setMemo(UtilString.isNil(rs.getString("memo")));
					MapSymbol.put(symbol.getSymbol(), symbol);
				}
			} catch (Exception e) {
				UtilLog.logError(" 获取货币信息错误:", e);
			} finally {
				UtilSql.close(rs, sm);
			}
		}
		return MapSymbol;
	}

	// 获取货币信息Bean
	public Symbol getBean(String server, String symbol) {
		Symbol bean = null;
		try {
			String sql = "select * from config_symbol where server='" + server + "' and symbol ='" + symbol + "' order by ord ";
			Map<String, Object> map = UtilCollection.getObjectMap(UtilSql.QueryA(con, sql));
			bean = (Symbol) UtilConver.convertMap(map, Symbol.class);
		} catch (Exception e) {
			UtilLog.logError(" 获取货币信息错误:", e);
		}
		return bean;
	}

	// 获取某一服务器货币信息数组
	public String[] getSymbolArr(String server) {
		String[] rs = null;
		try {
			String sql = "select symbol from config_symbol  order by ord  ";
			if (!"".equals(UtilString.isNil(server))) {
				sql += " where server='" + server + "' ";
			}
			rs = UtilSql.executeSql(con, sql, "symbol");
		} catch (Exception e) {
			UtilLog.logError(" 获取货币信息错误:", e);
		} finally {
		}
		return rs;
	}

	// 获取某一服务器货币信息数组
	public String[] getSymbolIdArr(String server) {
		String[] rs = null;
		try {
			String sql = "select id from config_symbol  order by ord  ";
			if (!"".equals(UtilString.isNil(server))) {
				sql += " where server='" + server + "' ";
			}
			rs = UtilSql.executeSql(con, sql, "id");
		} catch (Exception e) {
			UtilLog.logError(" 获取货币信息错误:", e);
		} finally {
		}
		return rs;
	}

	// 获取货币信息表格
	public Vector<Vector<String>> getSymbolVector(String symbol) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> SymbolVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (symbol.length() > 1)
				rs = sm.executeQuery("select * from config_symbol where symbol like ('%" + symbol + "%') order by ord ");
			else
				rs = sm.executeQuery("select * from config_symbol");
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(UtilString.isNil(rs.getString("Id")));
				rowValue.add(UtilString.isNil(rs.getString("server")));
				rowValue.add(UtilString.isNil(rs.getString("symbol")));
				rowValue.add(UtilString.isNil(rs.getString("point")));
				rowValue.add(UtilString.isNil(rs.getString("digits")));
				rowValue.add(UtilString.isNil(rs.getString("state")));
				rowValue.add(UtilString.isNil(rs.getString("ord")));
				rowValue.add(UtilString.isNil(rs.getString("memo")));
				SymbolVector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError(" 获取货币信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return SymbolVector;
	}

	// 新增货币信息
	public void addSymbol(Symbol symbol) {
		try {
			String sql = "insert into config_symbol(server,symbol,point,digits,state,ord,memo) values (?,?,?,?,?,?)";
			UtilSql.executeUpdate(con, sql, new Object[] { symbol.getServer(), symbol.getSymbol(), symbol.getPoint(), symbol.getDigits(), symbol.getState(), symbol.getOrd(),
					symbol.getMemo() });
		} catch (Exception e) {
			UtilLog.logError(" 新增货币信息错误:", e);
		}
	}

	// 修改货币信息
	public void modSymbol(Symbol symbol) {
		try {
			String sql = "update config_symbol set server=?,symbol=?,point=?,digits=?,state=?,ord=?,memo=? " + "  where  id='" + symbol.getId() + "'";
			UtilSql.executeUpdate(con, sql, new Object[] { symbol.getServer(), symbol.getSymbol(), symbol.getPoint(), symbol.getDigits(), symbol.getState(), symbol.getOrd(),
					symbol.getMemo() });
		} catch (Exception e) {
			UtilLog.logError(" 修改货币信息错误:", e);
		}
	}

	// 删除货币信息
	public void delSymbol(String id) {
		try {
			String sql = "delete from config_symbol where id='" + id + "'";
			UtilSql.executeUpdate(con, sql, new Object[0]);
		} catch (Exception e) {
			UtilLog.logError(" 删除货币信息错误:", e);
		}
	}

	public Vector<?> getModel() {
		String sql = "select id ,symbol ,ord from config_symbol  where state=0 order by ord";
		List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
		Vector<KeyValue> vector = new Vector<KeyValue>();
		for (HashMap<String, Object> map : list) {
			vector.addElement(new KeyValue(map.get("ID").toString(), map.get("SYMBOL").toString()));
		}
		vector.addElement(new KeyValue("全部", "全部"));
		return vector;
	}

}
