package com.ntlx.exception;

public class CardNotFoundException extends KanbanException{
	public String getMessage() {
		return "The requested card could not be found.";
	}
}
