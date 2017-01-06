package com.mysql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateToSelect {

	public static void main(String[] args) {
		try {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/lyang/Desktop/sap-bak.sql"), "GBK"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String tempString;
			String tableName = null;
			StringBuilder sb = new StringBuilder(" select ");
			try {
				while ((tempString = br.readLine()) != null) {
					int indexOf = tempString.indexOf("CREATE TABLE");
					String[] split0 = tempString.split("`");
					if(indexOf!=-1){
						if(split0.length > 1){
							tableName = split0[1];
						}
					} else {
						if(split0.length > 1){
							sb.append(split0[1]).append(",");
						}
					}
				}
				String sql = sb.toString().substring(0, sb.toString().length()-1);
				System.out.println(sql + " from " + tableName);
				
				System.out.println("beeline -u \"jdbc:hive2://\" -e \"INSERT OVERWRITE TABLE WUMART." + tableName + "_temp " + sql + " from WUMART."+tableName+"\"");
				System.out.println("echo \"INSERT OVERWRITE TABLE WUMART." + tableName + "_temp   " + sql + " from WUMART."+tableName+"\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
