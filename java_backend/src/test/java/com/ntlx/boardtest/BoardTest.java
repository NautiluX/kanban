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
	public void testGetLanes() 
	{
		Board boardWithLanes = BoardTestUtils.createBoardWith3Lanes();
		Assert.assertEquals(3, boardWithLanes.getLanes().size());
	}

	@Test
	public void testGetLanesJson() 
	{
		Board boardWithLanes = BoardTestUtils.createBoardWith3Lanes();
		String json = "{\"name\":\"Test Board\",\"id\":1,\"owner\":{\"id\":1,\"name\":\"Test User\"},\"lanes\":[{\"id\":1,\"title\":\"Test Lane\"},{\"id\":1,\"title\":\"Test Lane\"},{\"id\":1,\"title\":\"Test Lane\"}]}";
		Assert.assertEquals(json, boardWithLanes.toString());
	}
	
	@Test
	public void testToJson()
	{
		String json = "{\"name\":\"Test Board\",\"id\":1,\"owner\":{\"id\":1,\"name\":\"Test User\"},\"lanes\":[]}";
		Assert.assertEquals(json, board.toString());
	}
}
