package pers.ywheel.fsql.parser.imp;

import pers.ywheel.fsql.parser.Parser;

public class Help implements Parser{
	private static final String NAME = "HELP";
	
    public String getName() {
        return NAME;	
    }
    
    public boolean parse(String fsql) {
    	// TODO
    	if (fsql.trim().equals("help")) {
    		printHelp();
    		return true;
    	}
    	return false;
    }
    
    private void printHelp() {
    	String help = 
    	  "               FILE SQL"                           + "\n"
    	+ "***********************************************"   + "\n"
    	+ "help   --list command; E.g.: >>help load"          + "\n"
    	+ "load   --load the file for analysis;"              + "\n"
    	+ "select --select from a file (without extension)"   + "\n"
    	+ "quit   --exit this application;"                   + "\n"
    	+ "***********************************************";
    	System.out.println(help);
    }
}
