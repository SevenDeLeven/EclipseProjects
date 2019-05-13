package dev.winterblue.guitest.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
	
	private Thread thread;
	private ServerSocket ssocket;
	
	public ConnectionHandler() {
		try {
			ssocket = new ServerSocket(777);
		} catch(IOException e) {
			e.printStackTrace();
		}
		thread = new Thread(this, "ConnectionHandler");
		thread.start();
	}
	
	public void run(){
		while(true) {
			try {				
				Socket s = ssocket.accept();
				new ServerListener(new SocketData(s));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
