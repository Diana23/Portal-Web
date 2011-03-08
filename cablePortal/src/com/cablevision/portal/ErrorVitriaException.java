package com.cablevision.portal;

public class ErrorVitriaException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ErrorVitriaException() {
		super();
	}

	public ErrorVitriaException(String message) {
		super(message);
	}

	public ErrorVitriaException(Throwable cause) {
		super(cause);
	}

	public ErrorVitriaException(String message, Throwable cause) {
		super(message, cause);
	}
}
