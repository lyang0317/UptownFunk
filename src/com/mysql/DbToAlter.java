package com.mysql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Random;

public class DbToAlter {

	public static void main(String[] args) {
		String tableName = " dm_vendor_purchase_return ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		StringBuilder sb = new StringBuilder(" alter table " + tableName);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://10.249.6.50:3306/retail_ps?useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull","retailps","ZGMxZTc1MDlkNjQxZWE3NmFlN");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("select * from " + tableName + " limit 1");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 0;i < rsmd.getColumnCount();i++){
				String colname = rsmd.getColumnName(i+1);//取得全部列名
				int columnType = rsmd.getColumnType(i+1);
				if(columnType == -1){
					sb.append(" change ").append(colname+" "+colname+" ").append("varchar(50) ,");
				}
			}
			System.out.println(sb.toString());
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
