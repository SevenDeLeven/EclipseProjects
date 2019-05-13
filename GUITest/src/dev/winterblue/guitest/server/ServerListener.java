package dev.winterblue.guitest.server;


import java.io.IOException;
import java.util.NoSuchElementException;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.convert.JSONDecoder;

public class ServerListener implements Runnable {

	private SocketData s;
	private static Thread thread;
	public ServerListener(SocketData s) {
		this.s = s;
		Server.connections.add(s);
		thread = new Thread(this, "ServerListener");
		thread.start();
	}

	public void run() {
		while(true) {
			try {
			String temp = s.sc.nextLine();
			JSONDecoder dc = new JSONDecoder(temp);
			
			JSONObject packet = (JSONObject)dc.decode();
			
			Server.Process(packet);
			
			} catch (NoSuchElementException e) {
				try {
					this.s.s.close();
				} catch (IOException e1) {
				}
				break;
			}
			
		}
		JSONObject disconnect = new JSONObject();
		disconnect.put("ptype", new JSONString("discon"));
		disconnect.put("uname", new JSONString(s.username));
		Server.Process(disconnect);
	}
}
