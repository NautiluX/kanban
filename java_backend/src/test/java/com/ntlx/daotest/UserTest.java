package com.ntlx.daotest;

import org.junit.*;


import com.ntlx.dao.TestOwner;
import com.ntlx.dao.User;

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
