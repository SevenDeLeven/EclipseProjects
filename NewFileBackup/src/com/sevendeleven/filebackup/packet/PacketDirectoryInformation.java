package com.sevendeleven.filebackup.packet;

import java.util.List;

import com.sevendeleven.filebackup.file.FileData;

import de.grobmeier.jjson.JSONArray;
import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.JSONValue;

public class PacketDirectoryInformation implements Packet {
	
	private FileData[] files;
	private String directory;
	
	public PacketDirectoryInformation(String directory, FileData[] files) {
		this.directory = directory;
		this.files = files;
	}
	
	public PacketDirectoryInformation(JSONObject packet) {
		this.directory = JSONHelper.getString(packet, "dir");
		JSONArray arr = (JSONArray) packet.getValue().get("files");
		List<JSONValue> fileList = arr.getValue();
		files = new FileData[fileList.size()];
		for (int i = 0; i < files.length; i++) {
			files[i] = new FileData((JSONObject)fileList.get(i));
		}
	}
	
	public String getDirectory() {
		return this.directory;
	}
	
	public FileData[] getFiles() {
		return this.files;
	}
	
	public String getName() {
		return "dirInfo";
	}
	
	public JSONObject getJSON() {
		JSONObject packet = new JSONObject();
		JSONArray fileArray = new JSONArray();
		for (int i = 0; i < files.length; i++) {
			fileArray.add(files[i].getJSON());
		}
		packet.put("dir", new JSONString(directory));
		packet.put("files", fileArray);
		return packet;
	}
	
	public String getData() {
		return getJSON().toJSON();
	}
	
}
