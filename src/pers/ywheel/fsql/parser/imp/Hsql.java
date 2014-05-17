package pers.ywheel.fsql.parser.imp;

import pers.ywheel.fsql.dao.DAO;
import pers.ywheel.fsql.parser.Parser;

public class Hsql implements Parser{
    private static final String NAME = "HSQL";
    
    private DAO dao;
    
    public Hsql(DAO dao) {
    	this.dao = dao;
    }
    
    public String getName() {
    	return NAME;
    }
    
    public boolean parse(String sql) {
    	// TODO
    	if (sql.trim().startsWith("select")) {
    		this.dao.query(sql);
    	} else if (sql.trim().startsWith("create table")) {
    		this.dao.createTable(sql);
        }else {
    	    this.dao.update(sql);
    	}
    	return false;
    }
}
