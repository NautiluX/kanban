package com.ntlx.exception;

public class BoardNotWorldReadable extends AuthorizationException {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Board not world readable.";
	}
}
