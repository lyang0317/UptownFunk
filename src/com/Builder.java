package com;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Builder {
	
	private Map<String, Lifter> lifters = new HashMap<String, Lifter>();
	private Queue<Person> persons = new LinkedBlockingQueue<Person>();
	
	
	public Builder() {
		try {
			final Lifter lifter = new Lifter("A");
			this.addLift(lifter);
			new Thread(new Runnable() {
				@Override
				public void run() {
					lifter.working();
				}
			});
			ServerSocket server = new ServerSocket(8001);
			while (true) {
				Socket socket = server.accept();
				System.out.println(" builder lifter is ready running...");
				InputStream is = socket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Person person = (Person) ois.readObject();
				System.out.println("person:"+person+" is coming");
				this.addPerson(person);
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void addLift(final Lifter lifter){
		lifters.put(lifter.getFlag(),lifter);
		new Thread(new Runnable() {
			@Override
			public void run() {
				lifter.report();
			}
		}).start();
	}
	
	
	/**
	 * 乘客加入坐电梯
	 * 电梯作为观察者监听
	 * @param person
	 */
	public void addPerson(Person person){
		persons.add(person);
		boolean flag = false;
		Lifter lifter = lifters.get(person.getLifter().getFlag());
		flag = lifter.watchPeople(person);
		if(flag == true) {
			persons.poll();
			lifter.sendPeople(person);
		}
	}
	
	public static void main(String[] args) {
	    Builder builder = new Builder();
	}

}
