package com.sevendeleven.sockets.server;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {
	
	Thread t;
	Socket s;
	
	public ConnectionHandler(Socket s) {
		this.s = s;
		t = new Thread(this, "ConnectionHandler");
		t.start();
	}
	
	public void run() {
		Scanner sc;
		try {
			sc = new Scanner(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		while (true) {
			String info = "";
			try {
				info = sc.nextLine();
			} catch (NoSuchElementException e) {
				break;
			}
			if (info.equals("/exit")) {
				break;
			}
			MainServer.addInformation(info);
		}
		sc.close();
		try {
			s.close();
		} catch (IOException e) {
			
		}
		MainServer.connections.remove(this);
	}
	
}
