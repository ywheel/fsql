package pers.ywheel.fsql.parser.imp;

import java.util.*;
import java.io.*;

import pers.ywheel.fsql.dao.DAO;
import pers.ywheel.fsql.parser.Parser;

public class Load implements Parser {
    private static final String NAME = "LOAD";
    private DAO dao;
    
    public Load(DAO dao) {
    	this.dao = dao;
    }
    
    public String getName() {
    	return NAME;
    }
    
    public boolean parse(String fsql) {
    	if (fsql.trim().startsWith("load")) {
    		String[] item = fsql.trim().substring(5).split(" ");
    		boolean hasHead = false;
    		String filePath = null;
    		String separator = "\t";
    		for (String info : item) {
    			if (info.indexOf("=") > 0) {
    				String[] para = info.split("=");
    				if (para.length != 2) {
    					continue;
    				}
    				if (para[0].trim().equals("hashead") && para[1].trim().equals("1")) {
    					hasHead = true;
    				} else if (para[0].trim().equals("fs")) {
    					separator = para[1].trim();
    				}
    			} else {
    				filePath = info.trim();
    			}
    		}
    		if (filePath != null) {
    			loadFile(filePath, hasHead, separator);
    		}
    	}
    	return false;
    }

    private void loadFile(String filename, boolean hasHead, String separator) {
    	// TODO move this to seperate class, need redesign
    	try {
    		System.out.println("load...");
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			// read 10 rows for detecting type of each column
			List<String[]> data = new ArrayList<String[]>(10);
			int num = hasHead ? 0 : 1;
			for (; num < 10; num++) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				data.add(line.split(separator));
			}
			
			// detecting column name and type, create table
			String tableName = getTableName(filename);
			ColumnType[] columnType = createTable(tableName, data, hasHead);
			// insert the readed lines
			for (int i= hasHead ? 1 : 0; i<data.size(); i++) {
				insert(columnType, data.get(i), tableName);
			}
			
			if (num == 10) {
				// this file have more than 10 rows
				// continue to read and insert
				while (true) {
					String line = reader.readLine();
					if (line == null) {
						break;
					}
					num++;
					insert(columnType, line.split(separator), tableName);
				}
				
			}
			reader.close();
			System.out.println("loaded " + num + " records");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private boolean insert(ColumnType[] type, String[] data, String tableName) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("insert into ").append(tableName).append(" values (");
    	sql.append(type[0] != ColumnType.String ? data[0] : "'" + data[0] + "'");
    	for (int i=1; i<type.length; i++) {
    		sql.append(", ");
    		sql.append(type[i] != ColumnType.String ? data[i] : "'" + data[i] + "'");
    	}
    	sql.append(");");
    	this.dao.update(sql.toString());
    	// TODO dao.update need to return a flag
    	
    	return true;
    }
    
    private ColumnType[] createTable(String tableName, List<String[]> rowData, boolean hasHead) {
    	int index = 0;
    	String[] columnName;
    	if (hasHead && rowData.size() > 1) {
    		index++;
    		columnName = rowData.get(0);
    	} else {
    		columnName = new String[rowData.size()];
    		for (int i=0; i<columnName.length; i++) {
    			columnName[i] = "COLUMN" + String.valueOf(i);
    		}
    	}
    	
    	ColumnType[] type = new ColumnType[columnName.length];
    	for (; index<rowData.size(); index++) {
    		for (int i=0; i<type.length; i++) {
	    		try {
	    			Integer.parseInt(rowData.get(index)[i]);
	    			type[i] = ColumnType.Integer;
	    		} catch (NumberFormatException e1) {
	    			try {
		    			Double.parseDouble(rowData.get(index)[i]);
		    			type[i] = ColumnType.Double;
		    		} catch (NumberFormatException e2) {
		    			type[i] = ColumnType.String;
		    		}
    			}
    		}
    	}
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("create table " + tableName + " (");
    	sql.append(columnName[0]).append(" ").append(type[0].toDBType());
    	for (int i=1; i<type.length; i++) {
    		sql.append(",");
    		sql.append(columnName[i].toLowerCase()).append(" ").append(type[i].toDBType());
    	}
    	sql.append(");");
    	System.out.println(sql.toString());
    	this.dao.createTable(sql.toString());
    	return type;
    }
    
    private String getTableName(String fileName) {
    	File file = new File(fileName);
        String tableName = file.getName();
        int index = tableName.indexOf(".");
        return index > 0 ? tableName.substring(0, index) : tableName;
    }
    
    private enum ColumnType {
    	String,
    	Integer,
    	Double;
    	
    	public String toDBType() {
    		if (this == ColumnType.String) {
    			return "varchar(255)";
    		} else if (this == ColumnType.Integer) {
    			return "int";
    		} else {
    			return "decimal(10,2)";
    		}
    	}
    }
}
