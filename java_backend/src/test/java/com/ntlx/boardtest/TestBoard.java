package com.ntlx.boardtest;

import java.util.HashSet;

import com.ntlx.board.Board;
import com.ntlx.board.Permissions;

public class TestBoard extends Board{

	public TestBoard() {
		super(1, "Test Board", new TestOwner(), getTestPermissions());
	}

	private static HashSet<String> getTestPermissions() {
		HashSet<String> permissions = new HashSet<String>();
		permissions.add(Permissions.READ);
		return permissions;
	}
	
}
