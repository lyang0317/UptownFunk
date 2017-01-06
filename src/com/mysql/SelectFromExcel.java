package com.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

public class SelectFromExcel {

	private static List<Map<String,String>> alist = new ArrayList<Map<String,String>>();
	private static Map<String,List<String>> cmap = new HashMap<String,List<String>>();
	private static Map<String,List<String>> tmap = new HashMap<String,List<String>>();
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://192.168.202.253:3306/retail_ps?useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull","retailps","ZGMxZTc1MDlkNjQxZWE3NmFlN");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select matnr,werks,mmsta from m_sitearticle where mandt='300' and werks=? and matnr=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(new FileInputStream(new File("D:/1.xls")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		for (int i = 1; i < rowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell0 = row.getCell(0);
			HSSFCell cell1 = row.getCell(1);
			String werks = String.valueOf((long)cell0.getNumericCellValue());
			String matnr = "000000000000"+String.valueOf((long)cell1.getNumericCellValue());
			try {
				ps.setString(1, werks);
				ps.setString(2, matnr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResultSet rs;
			try {
				rs = ps.executeQuery();
				if(rs.next()){
					System.out.println(rs.getString(1));
					System.out.println(rs.getString(2));
					System.out.println(rs.getString(3));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
