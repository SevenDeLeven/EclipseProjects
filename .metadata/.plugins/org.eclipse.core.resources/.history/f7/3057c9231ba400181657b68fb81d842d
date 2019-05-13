package com.sevendeleven.filebackup.main;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
	
	public Socket socket;
	
	public ClientHandler() {
		try {
			socket = new Socket("localhost", 40304);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't conenct to the server");
			System.exit(-1);
		}
	}
	
	/**
	 * Requests current directory information from the Server, and then parses the information, then stores it in the Remote Chooser Pane
	 */
	
	public void requestCDI() {
		
	}
	
	public void close() {
		try {
			socket.close();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
