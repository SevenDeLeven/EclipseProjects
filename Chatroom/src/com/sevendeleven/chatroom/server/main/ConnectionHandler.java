package com.sevendeleven.chatroom.server.main;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
	
	private Socket socket;
	
	private boolean running = true;
	
	public ConnectionHandler(Socket socket) {
		this.socket = socket;
	}
	
	public void stop() {
		running = false;
	}
	
	/**
	 * Same thing as stop()
	 */
	
	public void close() {
		this.stop();
	}
	
	public void run() {
		
		while (running) {
			
		}
		try {
			socket.close();
		} catch (IOException e1) {
			System.err.println("Error closing socket");
		} finally {
			System.err.println("Terminating thread");
		}
	}
	
	public void sendData(byte[] data) {
		try {
			socket.getOutputStream().write(data);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("There was an error in attempting to send data, terminating connection");
			this.close();
		}
	}
	
}
