package dev.winterblue.guitest.server;

import java.util.ArrayList;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONObject;

public class Server {

	public static ArrayList<SocketData> connections = new ArrayList<SocketData>();
	
	public static void main(String[] args) {
		new ConnectionHandler();
	}
	public static void Process(JSONObject packet) {
		String ptype = JSONHelper.getString(packet, "ptype");
		
		switch(ptype) {
		case "msg":
			for(int i = 0; i < connections.size(); i++) {
				if(!connections.get(i).s.isConnected()) {
					System.out.println("msg force discon");
					connections.remove(i);
				} else {
					connections.get(i).ps.println(packet.toJSON());						
				}
			}
			break;
		case "con":
			for(int i = 0; i < connections.size(); i++){
				if(!connections.get(i).s.isConnected()) {
					System.out.println("con force discon");
					connections.remove(i);
				} else {
					connections.get(i).username = JSONHelper.getString(packet, "uname");
					connections.get(i).ps.println(packet.toJSON());	
				}
			}
			break;
			
		case "discon":
			for(int i = 0; i < connections.size(); i++) {
				if(!connections.get(i).s.isConnected()) {
					System.out.println("discon force discon");
					connections.remove(i);
				}
				else {			
					connections.get(i).ps.println(packet.toJSON());
				}
			}
		break;				
		}
	}
}
