package com.ntlx.exception;

public class BoardNotReadableException extends AuthorizationException {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Board not world readable.";
	}
}
