package pers.ywheel.fsql.parser.impl;

import pers.ywheel.fsql.parser.Parser;

public class Quit implements Parser {
    private static final String NAME = "QUIT";
    
    public String getName() {
    	return NAME;
    }
    
    public boolean parse(String fsql) {
    	// TODO
//    	System.out.println(getName());
    	System.out.println("Good bye!");
    	System.exit(0);
    	return false;
    }
}
