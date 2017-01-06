package com.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TimeClient {

	private AsynchronousSocketChannel asynchronousSocketChannel;  
	 
	public TimeClient(int port){
		try {
			this.asynchronousSocketChannel = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        asynchronousSocketChannel.connect(new InetSocketAddress("localhost", port));
        try {
			asynchronousSocketChannel.write(ByteBuffer.wrap("test".getBytes())).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
	}
}
