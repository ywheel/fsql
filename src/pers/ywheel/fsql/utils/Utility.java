package pers.ywheel.fsql.utils;

import java.util.*;

public class Utility {

	public static String listToString(List<?> list) {
		if (list.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(String.valueOf(list.get(0)));
			for (int index = 1; index < list.size(); index++) {
				buffer.append("\t").append(String.valueOf(list.get(index)));
			}
			return buffer.toString();
		}
		return "";
	}
	
}
