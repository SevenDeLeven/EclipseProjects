package com.sevendeleven.filebackup.main;

public class FileData {

	String filePath, name;	//filePath does not contain the fileName
	boolean directory;		//Whether or not the file is a directory
	boolean local;			//Whether or not the file is a local file
	
	public FileData(String filePath, String name, boolean directory, boolean local) {
		this.filePath = filePath;
		this.name = name;
		this.directory = directory;
		this.local = local;
	}
	
	public String getAbsolutePath() {
		return filePath+name;
	}
	
}
