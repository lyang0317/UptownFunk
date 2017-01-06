package com.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ProtoBufTest {

	public static void main(String[] args) {
		writeObjectByProtoBuf();
		readObjectByProtoBuf();
	}
	
	public static void writeObjectByProtoBuf() {
		Request.proto_Bean.Builder builder = Request.proto_Bean.newBuilder();
		builder.setId("123");
		builder.setName("abc");
		Request.proto_Bean request=builder.build();
		FileOutputStream fs;
		try {
			fs = new FileOutputStream(new File("D:/4.txt"));
			request.writeTo(fs);
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readObjectByProtoBuf() {
		FileInputStream fs;
		try {
			fs = new FileInputStream(new File("D:/4.txt"));
			Request.proto_Bean parseFrom = Request.proto_Bean.parseFrom(fs);
			System.out.println(parseFrom.getId());
			System.out.println(parseFrom.getName());
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
