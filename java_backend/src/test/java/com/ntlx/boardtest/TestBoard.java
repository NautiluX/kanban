package com.ntlx.boardtest;

import com.ntlx.board.Board;

public class TestBoard extends Board{

	public TestBoard() {
		id = 1;
		name = "Test Board";
		owner = new TestOwner();
	}
	
}
