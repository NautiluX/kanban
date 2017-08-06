package com.ntlx.exception;

public class UpdateNotAllowedException extends AuthorizationException {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "User is not permitted to update object.";
	}
}
