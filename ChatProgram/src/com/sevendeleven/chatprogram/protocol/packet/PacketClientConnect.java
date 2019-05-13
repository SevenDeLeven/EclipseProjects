package com.sevendeleven.chatprogram.protocol.packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sevendeleven.chatprogram.protocol.DataTranslator;
import com.sevendeleven.chatprogram.protocol.PacketInformation;

public class PacketClientConnect extends Packet {

	private int userID;
	private String userName;
	private Byte[] data;
	
	public PacketClientConnect(int userID, String userName) {
		this.userID = userID;
		this.userName = userName;
		List<Byte> dataList = new ArrayList<Byte>();
		byte[] userIdData = DataTranslator.writeInteger(this.userID);
		for (byte b : userIdData) {
			dataList.add(b);
		}
		byte[] userNameData = DataTranslator.writeString(this.userName);
		for (byte b : userNameData) {
			dataList.add(b);
		}
		this.data = new Byte[dataList.size()];
		this.data = dataList.toArray(this.data);
	}
	
	public PacketClientConnect(byte[] data) {
		this.data = DataTranslator.getBytes(data);
		ByteArrayInputStream bs = new ByteArrayInputStream(data);
		byte[] userIdData = new byte[Integer.BYTES];
		try {
		bs.read(userIdData);
		this.userID = DataTranslator.intFromBytes(userIdData);
		byte[] userNameData = new byte[bs.available()];
		bs.read(userNameData);
		this.userName = DataTranslator.readString(userNameData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public String getUsername() {
		return this.userName;
	}
	
	@Override
	public Byte getByte() {
		return PacketInformation.BYTE_CLIENT_CONNECTED;
	}

	@Override
	public Byte[] getData() {
		return data;
	}
	
}
