package com.sevendeleven.filebackup.packet;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;

public class PacketRequestDirectory implements Packet {
	
	private String directory;
	public PacketRequestDirectory(String directory) {
		this.directory = directory;
	}
	public PacketRequestDirectory(JSONObject packet) {
		this.directory = JSONHelper.getString(packet, "dir");
	}
	
	public String getName() {
		return "reqDir";
	}
	
	public String getData() {
		return getJSON().toJSON();
	}
	
	public JSONObject getJSON() {
		JSONObject packet = new JSONObject();
		packet.put("pType", new JSONString(this.getName()));
		packet.put("dir", new JSONString(directory));
		return packet;
	}
	
}
