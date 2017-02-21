package com.ntlx.resttest;

import org.junit.*;

import java.util.ArrayList;


import org.junit.Test;

import com.ntlx.board.Board;
import com.ntlx.board.Boards;
import com.ntlx.boardtest.TestBoard;

public class BoardsTest {

	@Test
	public void ToJsonTest1Object() {
		ArrayList<Board> boardsArray = new ArrayList<Board>();
		boardsArray.add(new TestBoard());
		Boards boards = new Boards(boardsArray);
		Assert.assertEquals("[" + boardsArray.get(0) + "]", boards.toString());
	}
	
	@Test
	public void ToJsonTest2Objects() {
		ArrayList<Board> boardsArray = new ArrayList<Board>();
		boardsArray.add(new TestBoard());
		boardsArray.add(new TestBoard());
		Boards boards = new Boards(boardsArray);
		Assert.assertEquals("[" + boardsArray.get(0) + "," + boardsArray.get(1) + "]", boards.toString());
	}
	
	@Test
	public void ToJsonTestEmpty() {
		ArrayList<Board> boardsArray = new ArrayList<Board>();
		Boards boards = new Boards(boardsArray);
		Assert.assertEquals("[]", boards.toString());
	}
}
