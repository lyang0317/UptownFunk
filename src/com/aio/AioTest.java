package com.aio;

public class AioTest {

	public static void main(String[] args) {
		TimeServer ts = new TimeServer(9999);
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TimeClient tc = new TimeClient(9999);
		}
	}
}
