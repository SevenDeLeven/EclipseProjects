package com.sevendeleven.chatroom.protocol.packet;

public abstract class Packet {
	
	protected Packet() {}
	
	public abstract byte[] getData();
	public abstract void setData(byte[] data);
	
}
