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

public class DbToExcel {

	private static List<Map<String,String>> alist = new ArrayList<Map<String,String>>();
	private static Map<String,List<String>> cmap = new HashMap<String,List<String>>();
	private static Map<String,List<String>> tmap = new HashMap<String,List<String>>();
	
	public static void main(String[] args) {
		try {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/1.txt"), "GBK"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String tempString;
			String mapKey = null;
			try {
				while ((tempString = br.readLine()) != null) {
					String[] split0 = tempString.split("#");
					if(split0.length > 1){
						mapKey = split0[1];
						Map map = new HashMap();
						map.put(mapKey, split0[2]);
						alist.add(map);
					}
					String[] split1 = tempString.split("`");
					String[] split2 = tempString.split("'");
					if(split1.length > 1 && split2.length > 1){
						List<String> clist = cmap.get(mapKey);
						if(clist==null){
							clist = new ArrayList<String>();
							cmap.put(mapKey, clist);
						}
						List<String> tlist = tmap.get(mapKey);
						if(tlist==null){
							tlist = new ArrayList<String>();
							tmap.put(mapKey, tlist);
						}
						clist.add(split1[1]);
						tlist.add(split2[split2.length-2]);
					}
				}
				System.out.println(tmap);
				
				// 第一步，创建一个webbook，对应一个Excel文件 
				HSSFWorkbook wb = new HSSFWorkbook(); 
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet 
				HSSFSheet sheet = wb.createSheet("数据表"); 
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short 
				HSSFRow row = sheet.createRow((int) 0); 
				// 第四步，创建单元格，并设置值表头 设置表头居中 
				HSSFCellStyle style = wb.createCellStyle(); 
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式 

				HSSFCell cell = null; 
				
				for (int i = 0; i < alist.size(); i++) {
					Set<String> keySet = alist.get(i).keySet();
					Iterator<String> iterator = keySet.iterator();
					String key = iterator.next();
					cell = row.createCell((short) 0);
					cell.setCellValue(key); 
					cell.setCellStyle(style); 
					cell = row.createCell((short) 1);
					cell.setCellValue(alist.get(i).get(key)); 
					cell.setCellStyle(style); 
					
					row = sheet.createRow((int) (i*3+1)); 
					List<String> clist = cmap.get(key);
					for (int j = 0; j < clist.size(); j++) {
						row.createCell((short) j).setCellValue(clist.get(j)); 
					}
					List<String> tlist = tmap.get(key);
					row = sheet.createRow((int) (i*3+2)); 
					for (int j = 0; j < tlist.size(); j++) { 
						row.createCell((short) j).setCellValue(tlist.get(j)); 
					} 
					
					row = sheet.createRow((int) (i*3+3)); 
				}
				// 第六步，将文件存到指定位置 
				FileOutputStream fout = new FileOutputStream("D:/1.xls",true); 
				wb.write(fout); 
				fout.close(); 
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
