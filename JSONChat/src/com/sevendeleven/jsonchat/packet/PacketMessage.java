package com.sevendeleven.jsonchat.packet;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONNumber;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;

public class PacketMessage extends Packet {
	String message;
	int id;
	public PacketMessage(int id, String message) {
		this.id = id;
		this.message = message;
	}
	public PacketMessage(JSONObject packet) {
		this.id = JSONHelper.getInt(packet, "id");
		this.message = JSONHelper.getString(packet, "msg");
	}
	public String getMessage() {
		return this.message;
	}
	public int getId() {
		return this.id;
	}
	public String getData() {
		JSONObject packet = new JSONObject();
		packet.put("msg", new JSONString(message));
		packet.put("id", new JSONNumber(id));
		return packet.toJSON();
	}
}
