package pers.ywheel.fsql.dao.imp;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import pers.ywheel.fsql.dao.DAO;
import pers.ywheel.fsql.utils.Utility;

public class JdbcDAO extends JdbcDaoSupport implements DAO{
	public void query(String sql) {
	    List<Map> result = new ArrayList<Map>();
	    result = this.getJdbcTemplate().queryForList(sql);
	    int num = 0;
	    for (Map row : result) {
	    	Iterator iter = row.entrySet().iterator();
	    	List<String> keys = new ArrayList<String>();
	    	List<String> values = new ArrayList<String>();
	    	while (iter.hasNext()) {
	    		Map.Entry entry = (Map.Entry) iter.next();
	    		String key = String.valueOf(entry.getKey());
	    		String value = String.valueOf(entry.getValue());
	    		keys.add(key);
	    		values.add(value);
	    	}
	    	if (num % 10 == 0) {
	    	    System.out.println(Utility.listToString(keys));
	    	}
	    	System.out.println(Utility.listToString(values));
	    	num++;
	    }
	}
	
	public void createTable(String sql) {
		this.getJdbcTemplate().execute(sql);
	}
	
	public void update(String sql) {
		this.getJdbcTemplate().execute(sql);
	}
	
	public static void main(String[] args) {
		
	}
}
