package com.sevendeleven.threadding.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {
	
	public static List<ConnectionHandler> conHandlers = new ArrayList<ConnectionHandler>();
	
	static ServerSocket socket;
	
	public static void main(String[] args) {
		
		try {
			socket = new ServerSocket(2024);
			Socket clientConnection;
			while (true) {
				clientConnection = socket.accept();
				ConnectionHandler newCon = new ConnectionHandler(clientConnection);
				conHandlers.add(newCon);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void sendMessage(String message) {
		for (ConnectionHandler c : conHandlers) {
			c.sendMessage(message);
		}
	}
	
}
