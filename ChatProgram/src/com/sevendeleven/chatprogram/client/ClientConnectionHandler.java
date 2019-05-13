package com.sevendeleven.chatprogram.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.chatprogram.protocol.packet.Packet;
import com.sevendeleven.chatprogram.protocol.packet.PacketTranslator;

public class ClientConnectionHandler {
	
	private Socket socket;
	private ReceiveThread recv;
	private SendThread send;
	
	public ClientConnectionHandler(Socket socket) {
		this.socket = socket;
		recv = new ReceiveThread();
		send = new SendThread();
	}
	
	public ReceiveThread getRecv() {
		return this.recv;
	}
	
	public SendThread getSend() {
		return this.send;
	}
	
	public class ReceiveThread implements Runnable {
		private Thread thread;
		private ReceiveThread() {
			thread = new Thread(this, "ReceiveThread");
			thread.start();
		}
		public void run() {
			while (ClientMain.running) {
				try {
				Packet p = PacketTranslator.interpretData(socket.getInputStream());
				
				ClientMain.instance.usePacket(p);
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public class SendThread implements Runnable {
		private Thread thread;
		private volatile List<Byte> buffer = new ArrayList<Byte>();
		private SendThread() {
			thread = new Thread(this, "SendThread");
			thread.start();
		}
		public void run() {
			System.out.println("test");
			try {
			
			
			OutputStream output = socket.getOutputStream();
			while (ClientMain.running) {
				while (buffer.size() > 0) {
					output.write(buffer.get(0));
					buffer.remove(0);
				}
			}
			
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public int getBufferLength() {
			return buffer.size();
		}
		public void sendBytes(byte[] bytes) {
			for (byte b : bytes) {
				buffer.add(b);
			}
		}
	}
	
}
