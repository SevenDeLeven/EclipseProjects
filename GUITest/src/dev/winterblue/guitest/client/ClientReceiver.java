package dev.winterblue.guitest.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.convert.JSONDecoder;

public class ClientReceiver implements Runnable {

	@SuppressWarnings("unused")
	private Socket s;
	private Scanner sc;
	private Thread thread;
	
	public ClientReceiver(Socket s) {
		try {
			this.s = s;
			sc = new Scanner(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		thread = new Thread(this, "ClientReceiver");
		thread.start();
		
	}
	
	public void run() {
		while(true) {
			String next = sc.nextLine();
			JSONDecoder dc = new JSONDecoder(next);
			JSONObject packet = (JSONObject) dc.decode();
			
			String ptype = JSONHelper.getString(packet, "ptype");
			switch(ptype) {
			case "msg":
				ClientMain.receive(JSONHelper.getString(packet, "uname") + ": " + JSONHelper.getString(packet, "msg"));
				break;
			case "con":
				ClientMain.receive(JSONHelper.getString(packet, "uname") + " connected to the server.");
				break;
			case "discon":
				ClientMain.receive(JSONHelper.getString(packet, "uname") + " disconnected from the server.");
				break;
			default:
				System.out.println("Invalid packet " + ptype);
				break;
			}
		}
	}

}
