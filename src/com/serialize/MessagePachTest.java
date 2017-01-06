package com.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.msgpack.MessagePack;

public class MessagePachTest {

	public static void main(String[] args) {
		readObjectBySerializable();
	}
	
	public static void writeObjectByMessagePack() {
		MessagePack mp = new MessagePack();
 		try {
			PojoBean pojoBean = new PojoBean();
			pojoBean.setId("1");
			pojoBean.setName("pojoBean1");
			byte[] bytes = mp.write(pojoBean);
			FileOutputStream fs = new FileOutputStream(new File("D:/2.txt"));
			fs.write(bytes);
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readObjectByMessagePack() {
		MessagePack mp = new MessagePack();
		byte[] bytes = null;
 		try {
 			FileInputStream fs = new FileInputStream(new File("D:/2.txt"));
 			PojoBean read = mp.read(fs, PojoBean.class);
			System.out.println(read);
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeObjectBySerializable() {
 		try {
 			PojoClass pojoBean = new PojoClass();
			pojoBean.setId("1");
			pojoBean.setName("pojoBean1");
			FileOutputStream fs = new FileOutputStream(new File("D:/3.txt"));
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(pojoBean);
			fs.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readObjectBySerializable() {
 		try {
			FileInputStream fs = new FileInputStream(new File("D:/3.txt"));
			ObjectInputStream os = new ObjectInputStream(fs);
			Object readObject = os.readObject();
			System.out.println(readObject);
			fs.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
