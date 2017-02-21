package com.ntlx.boardtest;

import org.junit.*;

import com.ntlx.board.User;

public class UserTest {
	User user = new TestOwner();
	
	@Test
	public void testGetId() {
		Assert.assertEquals(1, user.getId());
	}
	
	@Test
	public void testGetName() {
		Assert.assertEquals("Test User", user.getName());
	}

	@Test
	public void testEquals() {
		User expected = new TestOwner();
		Assert.assertEquals(expected, user);
	}
}
