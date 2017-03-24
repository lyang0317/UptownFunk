package com;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) {
		personArrived(new Person(new Lifter("A"),3,6));
	}

	private static void personArrived(Person person) {
		try {
			Socket socket = new Socket("localhost", 8001);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(person);
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
