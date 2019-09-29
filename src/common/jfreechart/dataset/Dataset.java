package common.jfreechart.dataset;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

import common.util.jdbc.UtilSql;

public class Dataset {
	public Dataset() {
	}

	public DefaultCategoryDataset getBarDataSet(Connection con, String sql, String[] fields) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<HashMap<String, String>> list = UtilSql.queryM(con, sql);
		if (list != null) {
			for (HashMap<String, String> map : list) {
				dataset.addValue(Double.valueOf(map.get(fields[0])), map.get(fields[1]), map.get(fields[2]));
			}
		}
		return dataset;
	}

	public DefaultCategoryDataset getBarDataSet(List<HashMap<String, String>> list, String[] fields) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (list != null) {
			for (HashMap<String, String> map : list) {
				dataset.addValue(Double.valueOf(map.get(fields[0])), map.get(fields[1]), map.get(fields[2]));
			}
		}
		return dataset;
	}
}
