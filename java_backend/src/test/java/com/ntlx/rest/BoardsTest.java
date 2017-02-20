package com.ntlx.rest;

import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.junit.Test;

import com.ntlx.dao.Board;
import com.ntlx.dao.Boards;
import com.ntlx.dao.DatabaseBoard;
import com.ntlx.dao.TestBoard;

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
