package com.sevendeleven.chatprogram.protocol.packet;

import java.io.InputStream;

public abstract class Packet {
	public abstract Byte getByte();
	public abstract Byte[] getData();
	public static Packet fromData(InputStream stream) {return null;}
}
