package pers.ywheel.fsql.parser.imp;

import java.util.Map;

import pers.ywheel.fsql.parser.Parser;

public class ParserSelector implements Parser {
    private static final String NAME = "PARSER_SELECTOR";
    private Map<String, Parser> selector;
    
    public ParserSelector(Map<String, Parser> selector) {
    	this.selector = selector;
    }
    
    public String getName() {
    	return NAME;
    }
    
    public boolean parse(String fsql) {
//    	System.out.println(getName());
    	String queryType = getQueryType(fsql);
    	if (selector != null && selector.containsKey(queryType)) {
    		return selector.get(queryType).parse(fsql);
    	}
    	return false;
    }
    
    private static String getQueryType(String rawFsql) {
    	String fsql = rawFsql.trim();
    	int index = fsql.indexOf(" ");
    	return index > 0 ? fsql.substring(0, index) : fsql;
    }
}
