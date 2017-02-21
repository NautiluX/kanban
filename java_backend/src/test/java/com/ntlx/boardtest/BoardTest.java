package com.ntlx.boardtest;


import org.junit.*;

import com.ntlx.board.*;

public class BoardTest {

	Board board = new TestBoard();
	User owner = new TestOwner();
	
	@Test
	public void testGetBoardName() 
	{
		Assert.assertEquals("Test Board", board.getName());
	}
	
	@Test
	public void testGetBoardId() 
	{
		Assert.assertEquals(1, board.getId());
	}
	
	@Test
	public void testGetOwner() 
	{
		Assert.assertEquals(owner, board.getOwner());
	}
	
	@Test
	public void testToJson()
	{
		String json = "{\"name\":\"Test Board\",\"id\":1,\"owner\":{\"id\":1,\"name\":\"Test User\"}}";
		Assert.assertEquals(json, board.toString());
	}
}
