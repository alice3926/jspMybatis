package board.common;

import common.Util;

public class UtilBoard extends Util {
	public String tblCheck(String tbl, String defaultTbl) {
		if(tbl==null ||tbl.trim().equals("")) {
			tbl = defaultTbl;
		}
		tbl = tbl.trim();
		return tbl;
	}
	//overloading
	public String[] searchCheck(String search_option, String search_data) {
		String[] result = new String[2];
		if(search_option == null || search_option.trim().equals("")) {
			search_option="";
		}
		search_option = search_option.trim();
		if(search_option.equals("")) {
			
		}else if(search_option.equals("writer") || search_option.equals("subject") || search_option.equals("content")) {
			
		}else if(search_option.equals("writer_subject_content")) {
			
		}else {
			search_option="";
		}
		
		if(search_data == null || search_data.trim().equals("")) {
			search_data="";
		}
		search_data = search_data.trim();
		
		if(search_option.equals("") || search_data.equals("")) {
			search_option="";
			search_data="";
		}
		result[0] = search_option;
		result[1] = search_data;
		return result;
	}
	
	
	
	
	
	
}
