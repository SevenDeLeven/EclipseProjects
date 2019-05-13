package com.sevendeleven.chatprogram.protocol.packet;

import com.sevendeleven.chatprogram.protocol.DataTranslator;
import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketMessageClient extends Packet {
	
	private String message;
	private Byte[] data;
	
	public PacketMessageClient(String message) {
		this.message = message;
		this.data = DataTranslator.getBytes(DataTranslator.writeString(message));
	}
	
	public PacketMessageClient(byte[] data) {
		this.data = DataTranslator.getBytes(data);
		this.message = DataTranslator.readString(data);
	}
	
	@Override
	public Byte getByte() {
		return PacketInformation.BYTE_CLIENT_MESSAGE;
	}
	@Override
	public Byte[] getData() {
		return data;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
