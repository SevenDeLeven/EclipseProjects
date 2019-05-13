package com.sevendeleven.chatprogram.protocol.packet;

import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketDisconnect extends Packet {

	public PacketDisconnect() {
		
	}
	
	@Override
	public Byte getByte() {
		return PacketInformation.BYTE_DISCONNECT;
	}

	@Override
	public Byte[] getData() {
		return new Byte[0];
	}
	
}
