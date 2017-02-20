package com.ntlx.dao;

public class TestBoard extends Board{

	@Override
	protected void loadData() {
		id = 1;
		name = "Test Board";
		owner = new TestOwner();
	}
	
}
