package com.sevendeleven.diskassessor.main;

import java.io.File;

public class FileData {
	
	public String fileName, filePath;
	public long size, numberOfFiles;
	public boolean directory;
	
	public FileData[] subfiles;
	
	public FileData parentFolder;
	
	private boolean complete = false;
	
	public FileData(File file) {
		this.filePath = file.getAbsolutePath();
		this.fileName = file.getName();
		this.directory = file.isDirectory();
		this.size = getSize(file);
		this.complete = true;
	}
	
	private long getSize(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {
				System.err.println("An exception occured when getting a list of files from " + file.getAbsolutePath());
				subfiles = new FileData[0];
				return 0;
			}
			subfiles = new FileData[files.length];
			for (int i = 0; i < files.length; i++) {
				FileData data = new FileData(files[i]);
				subfiles[i] = data;
				size += data.size;
				numberOfFiles += data.numberOfFiles;
				data.parentFolder = this;
//				currentSize += getSize(files[i]);
			}
			Main.size += file.length();
			return size + file.length();
		} else {
			numberOfFiles = 1;
			Main.size += file.length();
			Main.currentFileLoading = file.getAbsolutePath();
			return file.length();
		}
	}
	
	public double getSizeInMB() {
		return this.size/1000000.0;
	}
	
	public boolean incomplete() {
		return !complete;
	}
	
}
