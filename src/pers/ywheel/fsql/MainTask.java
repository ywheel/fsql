package pers.ywheel.fsql;

import java.io.*;

import pers.ywheel.fsql.parser.Parser;

public class MainTask {
	private Parser parserSelector;
	
	public MainTask(Parser selector) {
		this.parserSelector = selector;
	}
	
	public void process(String fsql) {
//		System.out.println(fsql);
		parserSelector.parse(fsql);
		// TODO
	}
	
	public void start() throws IOException {
		process("help");
		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
		String line;
		while (true) {
			System.out.print(">>");
			line = reader.readLine();
			process(line);
		}
	}
}
