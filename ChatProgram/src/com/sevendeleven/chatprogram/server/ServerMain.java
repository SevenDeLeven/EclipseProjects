package com.sevendeleven.chatprogram.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.chatprogram.client.User;
import com.sevendeleven.chatprogram.protocol.packet.Packet;
import com.sevendeleven.chatprogram.protocol.packet.PacketClientConnect;
import com.sevendeleven.chatprogram.protocol.packet.PacketClientDisconnect;
import com.sevendeleven.chatprogram.protocol.packet.PacketClientInformation;
import com.sevendeleven.chatprogram.protocol.packet.PacketDisconnect;
import com.sevendeleven.chatprogram.protocol.packet.PacketMessage;
import com.sevendeleven.chatprogram.protocol.packet.PacketMessageClient;
import com.sevendeleven.chatprogram.protocol.packet.PacketTranslator;

public class ServerMain {
	
	private static int nextUserId = 0;
	
	public static List<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();
	
	public static ServerSocket ssocket;
	
	public static void main(String[] args) {
		
		try {
		ssocket = new ServerSocket(2024);
		while (true) {
			try {
				Socket s = ssocket.accept();
				connections.add(new ConnectionHandler(s));
			} catch (Exception e) {
				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static int getNextUserId() {
		return nextUserId++;
	}
	
	public static void sendPacket(Packet p) {
		byte[] data = PacketTranslator.interpretPacket(p);
		for (int i = 0; i < connections.size(); i++) {
			connections.get(i).sendBytes(data);
		}
	}
	
	public static void sendPacket(Packet p, ConnectionHandler conHandler) {
		byte[] data = PacketTranslator.interpretPacket(p);
		conHandler.sendBytes(data);
	}
	
	public static void processPacket(Packet p, ConnectionHandler conHandler) {
		if (p instanceof PacketClientInformation) {
			PacketClientInformation pci = (PacketClientInformation) p;
			System.out.println("received packet client information");
			User user = new User(getNextUserId(), pci.getUsername());
			conHandler.setUser(user);
			
			for (int i = 0; i < connections.size(); i++) {
				ConnectionHandler c = connections.get(i);
				if (c != conHandler) {
					PacketClientConnect clientJoinPacket = new PacketClientConnect(c.getUser().getID(), c.getUser().getUsername());
					sendPacket(clientJoinPacket, conHandler);
				}
			}
			
			PacketClientConnect clientJoinPacket = new PacketClientConnect(user.getID(), user.getUsername());
			sendPacket(clientJoinPacket);
		} else if (p instanceof PacketMessageClient) {
			PacketMessageClient pmc = (PacketMessageClient) p;
			if (conHandler.getUser() == null) {
				return;
			}
			User user = conHandler.getUser();
			PacketMessage pm = new PacketMessage(user.getID(), pmc.getMessage());
			sendPacket(pm);
		} else if (p instanceof PacketDisconnect) {
			PacketDisconnect pd = (PacketDisconnect) p;
			if (conHandler.getUser() == null) {
				return;
			}
			connections.remove(conHandler);
			User user = conHandler.getUser();
			PacketClientDisconnect pcd = new PacketClientDisconnect(user.getID());
			sendPacket(pcd);
		}
	}
	
}
