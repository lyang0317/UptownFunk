package com.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TimeServer {

	AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	
	public TimeServer(int port){
		try {
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
			asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

				@Override
				public void completed(AsynchronousSocketChannel result,
						Object attachment) {
					handle(result);
					//asynchronousServerSocketChannel.accept(null, this);
				}

				private void handle(AsynchronousSocketChannel result) {
					ByteBuffer byteBuffer = ByteBuffer.allocate(32);  
			        try {  
			        	byteBuffer.clear();  
                        result.read(byteBuffer).get();  
                        byteBuffer.flip();  
			        	//result.read(byteBuffer).get();  
			        } catch (Exception e) {  
			            e.printStackTrace();  
			        }
			        byteBuffer.flip();  
			        System.out.println(byteBuffer.get());  
				}

				@Override
				public void failed(Throwable exc, Object attachment) {
					try {
						asynchronousServerSocketChannel.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
