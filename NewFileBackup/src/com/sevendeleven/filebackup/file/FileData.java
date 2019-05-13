package com.sevendeleven.filebackup.file;

import java.io.File;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONNumber;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;

public class FileData {
	
	FileType type;
	FileLocation location;
	String directory;
	String name;
	File file;
	
	public FileData(File file) {
		this.file = file;
		this.name = file.getName();
		this.directory = file.getPath()+"/";
		this.location = FileLocation.LOCAL;
		this.type = this.file.isDirectory() ? FileType.DIRECTORY : FileType.FILE;
	}
	
	public FileData(String fileName, String directory, FileType fileType) {
		this.type = fileType;
		this.name = fileName;
		directory = !directory.endsWith("/") ? directory+"/" : directory;
		this.directory = directory;
		this.location = FileLocation.REMOTE;
		this.file = null;
	}
	
	public FileData(JSONObject data) {
		this.type = JSONHelper.getInt(data, "type") == 0 ? FileType.FILE : FileType.DIRECTORY;
		this.name = JSONHelper.getString(data, "name");
		String directory = JSONHelper.getString(data, "dir");
		this.directory = directory.endsWith("/") ? directory : directory+"/";
		this.location = FileLocation.REMOTE;
		this.file = null;
	}
	
	public FileType getType() {
		return this.type;
	}
	
	public FileLocation getLocation() {
		return this.location;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDirectory() {
		return this.directory;
	}
	
	public String getFullPath() {
		return this.directory + this.name;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", new JSONNumber(this.type.getId()));
		obj.put("name", new JSONString(this.getName()));
		obj.put("dir", new JSONString(this.getDirectory()));
		return obj;
	}
	
	public enum FileLocation {
		LOCAL,
		REMOTE
	}
	
	public enum FileType {
		FILE(0),
		DIRECTORY(1);
		private final int id;
		private FileType(int id) {
			this.id = id;
		}
		public int getId() {
			return this.id;
		}
		public static FileType getTypeById(int id) {
			for (FileType f : FileType.values()) {
				if (f.id == id) {
					return f;
				}
			}
			return null;
		}
	}
	
}
