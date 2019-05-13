package com.sevendeleven.chatprogram.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.chatprogram.client.User;
import com.sevendeleven.chatprogram.protocol.packet.Packet;
import com.sevendeleven.chatprogram.protocol.packet.PacketTranslator;

public class ConnectionHandler implements Runnable {
	
	private Thread thread;
	private Socket socket;
	
	private List<Byte> buffer = new ArrayList<>();
	
	private User user;
	
	public ConnectionHandler(Socket s) {
		this.socket = s;
		this.thread = new Thread(this, "ConnectionHandler for " + s.getInetAddress().toString());
		this.thread.start();
	}
	
	public void close() {
		try {
			this.socket.close();
		} catch (Exception e) {
		}
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void sendBytes(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			buffer.add(data[i]);
		}
	}
	
	public void run() {
		try {
		InputStream is = this.socket.getInputStream();
		OutputStream os = this.socket.getOutputStream();
		while (true) {
			while (is.available() != 0) {
				Packet p = PacketTranslator.interpretData(is);
				ServerMain.processPacket(p, this);
			}
			while (buffer.size() > 0) {
				os.write(buffer.get(0));
				buffer.remove(0);
			}
		}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An exception occurred on this thread, closing connection");
		}
		this.close();
	}
	
}
