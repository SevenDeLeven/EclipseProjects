package com.sevendeleven.jsonchat.packet;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONNumber;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;

public class PacketJoin extends Packet {
	
	int id;
	String username;
	
	public PacketJoin(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public PacketJoin(JSONObject packet) {
		this.id = JSONHelper.getInt(packet, "id");
		this.username = JSONHelper.getString(packet, "user");
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getData() {
		JSONObject packet = new JSONObject();
		packet.put("id", new JSONNumber(this.id));
		packet.put("user", new JSONString(this.username));
		return packet.toJSON();
	}
	
}
