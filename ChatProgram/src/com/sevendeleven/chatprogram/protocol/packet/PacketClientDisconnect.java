package com.sevendeleven.chatprogram.protocol.packet;

import com.sevendeleven.chatprogram.protocol.DataTranslator;
import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketClientDisconnect extends Packet {

	private int userID;
	private Byte[] data;
	
	public PacketClientDisconnect(int userID) {
		this.userID = userID;
		this.data = DataTranslator.getBytes(DataTranslator.writeInteger(this.userID));
	}
	
	public PacketClientDisconnect(byte[] data) {
		this.data = DataTranslator.getBytes(data);
		this.userID = DataTranslator.intFromBytes(data);
	}
	
	@Override
	public Byte getByte() {
		return PacketInformation.BYTE_CLIENT_DISCONNECTED;
	}

	@Override
	public Byte[] getData() {
		return data;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
}
