package com.sevendeleven.threadding.main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {
	
	private Socket socket;
	private Thread thread;
	private Scanner in;
	private PrintStream out;
	
	public ConnectionHandler(Socket socket) {
		this.socket = socket;
		
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
			return;
		}
		
		thread = new Thread(this, "Client Connection");
		thread.start();
	}
	
	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			String nextMessage = in.nextLine();
			System.out.println(nextMessage);
			MainServer.sendMessage(nextMessage);
			if (nextMessage.equals("/exit")) {
				break;
			}
 		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void sendMessage(String message) {
		out.println(message);
	}
	
	public Scanner getScanner() {
		return this.in;
	}
	
	public PrintStream getPrintStream() {
		return this.out;
	}
	
}
