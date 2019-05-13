package com.sevendeleven.filebackup.packet;

import de.grobmeier.jjson.JSONObject;

public interface Packet {
	public String getName();
	public String getData();
	public JSONObject getJSON();
}
