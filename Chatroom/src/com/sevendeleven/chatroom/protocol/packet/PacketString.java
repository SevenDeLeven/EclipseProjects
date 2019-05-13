package com.sevendeleven.chatroom.protocol.packet;

public class PacketString extends Packet {
	
	private String data;
	public PacketString(String data) {
		this.data = data;
	}
	public PacketString(byte[] data) {
		this.setData(data);
	}
	
	public byte[] getData() {
		return data.getBytes();
	}
	
	public void setData(byte[] data) {
		this.data = new String(data);
	}
	
}
