package com.ntlx.exception;

public class DatabaseSchemaOutdatedException extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Database Schema version does not fit installed backend version. Please try again or ask your administrator for help.";
	}
}
