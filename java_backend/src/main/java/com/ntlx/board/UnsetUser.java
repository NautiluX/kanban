package com.ntlx.board;

public class UnsetUser extends User {
	public static final User Instance = new UnsetUser();
	public UnsetUser() {
		super(-1, "UnsetUser");
	}

}
