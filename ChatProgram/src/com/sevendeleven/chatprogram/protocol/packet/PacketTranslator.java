package com.sevendeleven.chatprogram.protocol.packet;

import java.io.IOException;
import java.io.InputStream;

import com.sevendeleven.chatprogram.protocol.DataTranslator;
import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketTranslator {
	
	public static Packet interpretData(InputStream is) {
		try {
			int byteType = is.read();
			if (byteType == -1) {
				return null;
			}
			byte b = (byte) byteType;
			while (is.available() < Integer.BYTES) {}
			int length = DataTranslator.readInteger(is);
			System.out.println(length);
			byte[] data = new byte[length];
			switch (b) {
			case PacketInformation.BYTE_CLIENT_CONNECTED:
				while (is.available() < length) {}
				is.read(data);
				return new PacketClientConnect(data);
			case PacketInformation.BYTE_CLIENT_DISCONNECTED:
				while (is.available() < length) {}
				is.read(data);
				return new PacketClientDisconnect(data);
			case PacketInformation.BYTE_CLIENT_INFORMATION:
				while (is.available() < length) {}
				is.read(data);
				return new PacketClientInformation(data);
			case PacketInformation.BYTE_CLIENT_MESSAGE:
				while (is.available() < length) {}
				is.read(data);
				return new PacketMessageClient(data);
			case PacketInformation.BYTE_DISCONNECT:
				while (is.available() < length) {}
				is.read(data);
				return new PacketDisconnect();
			case PacketInformation.BYTE_MESSAGE:
				while (is.available() < length) {}
				is.read(data);
				return new PacketMessage(data);
			default:
				System.err.println("Data invalid, skipping packet");
				is.skip(length);
				break;
			}
			
		} catch (IOException e) {
			System.out.println("Socket was closed");
		}
		return null;
	}
	
	public static byte[] interpretPacket(Packet p) {
		byte[] data = new byte[p.getData().length+1+Integer.BYTES];
		data[0] = p.getByte();
		byte[] lengthData = DataTranslator.writeInteger(p.getData().length);
		for (int i = 1; i < Integer.BYTES+1; i++) {
			data[i] = lengthData[i-1];
		}
		for (int i = 1+Integer.BYTES; i < data.length; i++) {
			data[i] = p.getData()[i-(1+Integer.BYTES)];
		}
		return data;
	}
	
}
