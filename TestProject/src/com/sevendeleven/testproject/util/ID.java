package com.sevendeleven.testproject.util;

public class ID {
	private long intId;
	private String uniqueName;
	public ID(long intId, String uniqueName) {
		this.intId = intId;
		this.uniqueName = uniqueName;
	}
	public long getNumber() {
		return intId;
	}
	public String getName() {
		return uniqueName;
	}
}
