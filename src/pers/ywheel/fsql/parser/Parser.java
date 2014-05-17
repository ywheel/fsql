package pers.ywheel.fsql.parser;

public interface Parser {

	public boolean parse(String fsql);
	
	public String getName();
}
