package com.sevendeleven.chatroom.server.main;

import java.util.Map.Entry;

public class CommandHandler {
	
	public static void parseCommand(String cmdString) {
		String[] cmd = cmdString.toLowerCase().split(" ");
		switch (cmd[0]) {
		case "stop":
			System.out.println("Exiting all connections");
			for (Entry<Integer, ConnectionHandler> connection : MainServer.getConnections().entrySet()) {
				connection.getValue().close();
			}
			System.out.println("Waiting for connections to close");
			
			break;
		default:
			System.err.println("Did not understand command " + cmd[0]);
			break;
		}
	}
	
}
