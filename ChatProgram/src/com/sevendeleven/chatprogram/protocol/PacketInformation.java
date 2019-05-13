package com.sevendeleven.chatprogram.protocol;

public class PacketInformation {
	

	/*
	 * BYTES beginning with 0 are reserved for DataTypes
	 * 
	 * Bytes from 1 to F are reserved for protocol
	 */
	
	public static final byte BYTE_STR_BEGIN				= 0x01;
	public static final byte BYTE_STR_END				= 0x02;
	public static final byte BYTE_STR_ESCAPE			= 0x03;
	
	public static final byte BYTE_CLIENT_INFORMATION	= 0x11;
	public static final byte BYTE_CLIENT_CONNECTED		= 0x12;
	public static final byte BYTE_CLIENT_MESSAGE		= 0x13;
	public static final byte BYTE_CLIENT_DISCONNECTED	= 0x14;
	public static final byte BYTE_MESSAGE				= 0x15;
	public static final byte BYTE_DISCONNECT			= 0x16;
	
	
}
