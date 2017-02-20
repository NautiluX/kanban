package com.ntlx.dao;

public class TestOwner extends User{

	@Override
	protected void loadData() {
		id = 1;
		name = "Test User";
	}
	
}
