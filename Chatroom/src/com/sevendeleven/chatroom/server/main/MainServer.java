package com.sevendeleven.chatroom.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import com.sevendeleven.chatroom.protocol.Information;

public class MainServer {
	
	public static volatile ServerSocket ssocket;
	
	public static volatile boolean run = true;
	
	private static volatile int nextID = 0;
	private static volatile HashMap<Integer, ConnectionHandler> connections = new HashMap<Integer, ConnectionHandler>();
	
	
	
	
	public static void main(String[] args) {
		System.out.println("Starting Server");
		try {
			ssocket = new ServerSocket(Information.DEFAULT_PORT);
			while (run) {
				Socket socket = ssocket.accept();
				connections.put(nextID++, new ConnectionHandler(socket));
			}
			ssocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An error occurred, please close all instances of Chatroom to run a new server");
		}
		System.out.println("Stopping Server...");
	}
	
	public static HashMap<Integer, ConnectionHandler> getConnections() {
		return connections;
	}
	
	
	
	
	
	
	
	
	public class CommandThread implements Runnable {
		
		private Thread thread;
		
		public CommandThread() {
			thread = new Thread(this, "CommandThread");
			thread.start();
		}
		
		public void run() {
			Scanner sc = new Scanner(System.in);
			while (run) {
				String nextCommand = sc.nextLine();
				CommandHandler.parseCommand(nextCommand);
			}
			sc.close();
		}
		
	}
	
}
