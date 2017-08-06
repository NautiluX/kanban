package com.ntlx.exception;

public class LaneNotFoundException extends KanbanException {
	public String getMessage() {
		return "The requested lane could not be found.";
	}
}
