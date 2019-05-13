package com.sevendeleven.jsonchat.packet;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONNumber;
import de.grobmeier.jjson.JSONObject;

public class PacketLeave extends Packet {
	
	public int id;
	
	public PacketLeave(int id) {
		this.id = id;
	}
	
	public PacketLeave(JSONObject packet) {
		this.id = JSONHelper.getInt(packet, "id");
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getData() {
		JSONObject packet = new JSONObject();
		packet.put("id", new JSONNumber(this.id));
		return packet.toJSON();
	}
	
}
