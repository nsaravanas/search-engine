package org.example.model;

public class Error {

	private String message;

	private String exceptionMessage;

	private String action;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

}
