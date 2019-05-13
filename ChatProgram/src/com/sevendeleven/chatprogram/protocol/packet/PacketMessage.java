package com.sevendeleven.chatprogram.protocol.packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.chatprogram.protocol.DataTranslator;
import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketMessage extends Packet {
	
	private int userID;
	private String message;
	private Byte[] data;
	
	public PacketMessage(int userID, String userName) {
		this.userID = userID;
		this.message = userName;
		List<Byte> dataList = new ArrayList<Byte>();
		byte[] userIdData = DataTranslator.writeInteger(this.userID);
		for (byte b : userIdData) {
			dataList.add(b);
		}
		byte[] messageData = DataTranslator.writeString(this.message);
		for (byte b : messageData) {
			dataList.add(b);
		}
		this.data = new Byte[dataList.size()];
		this.data = dataList.toArray(this.data);
	}
	
	public PacketMessage(byte[] data) {
		this.data = DataTranslator.getBytes(data);
		ByteArrayInputStream bs = new ByteArrayInputStream(data);
		byte[] userIdData = new byte[Integer.BYTES];
		try {
		bs.read(userIdData);
		this.userID = DataTranslator.intFromBytes(userIdData);
		byte[] messageData = new byte[bs.available()];
		bs.read(messageData);
		this.message = DataTranslator.readString(messageData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Byte getByte() {
		return PacketInformation.BYTE_MESSAGE;
	}

	@Override
	public Byte[] getData() {
		return data;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
