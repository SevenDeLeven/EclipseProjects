package com.sevendeleven.chatprogram.protocol.packet;

import com.sevendeleven.chatprogram.protocol.DataTranslator;
import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketClientInformation extends Packet {
	
	private String username;
	private Byte[] data;
	
	public PacketClientInformation(String username) {
		this.username = username;
		this.data = DataTranslator.getBytes(DataTranslator.writeString(username));
	}
	
	public PacketClientInformation(byte[] data) {
		this.data = DataTranslator.getBytes(data);
		this.username = DataTranslator.readString(data);
	}

	@Override
	public Byte getByte() {
		return PacketInformation.BYTE_CLIENT_INFORMATION;
	}

	@Override
	public Byte[] getData() {
		return this.data;
	}
	
	public String getUsername() {
		return this.username;
	}
	
}
