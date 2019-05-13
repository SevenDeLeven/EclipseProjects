package com.sevendeleven.chatroom.server.main;

public class ConnectionsManager implements Runnable {

	private Thread thread;
	
	public ConnectionsManager() {
		thread = new Thread(this, "ConnectionsManager");
		thread.start();
	}
	
	
	public void sendDataToClient(byte[] data, ConnectionHandler conHand) {
		
	}
	
	
	public void run() {
		while (MainServer.run) {
			
		}
	}
	
}
