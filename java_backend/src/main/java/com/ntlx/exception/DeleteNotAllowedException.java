package com.ntlx.exception;

public class DeleteNotAllowedException extends AuthorizationException {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "User is not permitted to delete object.";
	}
}
