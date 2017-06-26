package com.ntlx.exception;

public class CardNotFoundException extends Exception {
	public String getMessage() {
		return "The requested card could not be found.";
	}
}
