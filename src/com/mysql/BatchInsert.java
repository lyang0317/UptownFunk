package com.mysql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BatchInsert {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://10.249.6.50:3306/retail_ps?useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull","retailps","ZGMxZTc1MDlkNjQxZWE3NmFlN");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement ps = null;
		try {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/xx.txt")));
				String tempString;
				try {
					while ((tempString = br.readLine()) != null) {
						ps = conn.prepareStatement("insert into dm_site_article_sale_day (area_id,date,site_no,article_no,tax_sale,untax_sale_cost,sale_num) values (?,?,?,?,?,?,?) ");
						String[] splitString = tempString.split("\\*");
						ps.setString(1, "300");
						ps.setString(2, splitString[2]);
						ps.setString(3, splitString[0]);
						ps.setString(4, splitString[1]);
						ps.setDouble(5, new Random().nextInt(20));
						ps.setDouble(6, new Random().nextInt(20));
						ps.setString(7, String.valueOf(new Random().nextInt(10)));
						int result = ps.executeUpdate();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
