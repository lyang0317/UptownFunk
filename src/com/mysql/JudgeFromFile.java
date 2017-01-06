package com.mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JudgeFromFile {

	public static void main(String[] args) {
		int k = 0;
		List<String> result = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("d:/1111.txt"))));
			for (int i = 0; i< 380; i++) {
				String string = br.readLine();
				list.add(string);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://10.13.3.20:3306/wm_ms_erp?useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull","qaprogram","qaprogram123");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = " select count(1) from erp_user where user_no=? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			for (String string : list) {
				ps.setString(1, string);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					if(rs.getInt(1)==0) {
						result.add(string);
						k++;
					}
				}
				rs.close();
			}
			ps.close();
			conn.close();
			System.out.println(k);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
