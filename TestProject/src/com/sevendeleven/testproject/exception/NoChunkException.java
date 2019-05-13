package com.sevendeleven.testproject.exception;

public class NoChunkException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoChunkException() {
		super();
	}
	public NoChunkException(String message) {
		super(message);
	}
}
