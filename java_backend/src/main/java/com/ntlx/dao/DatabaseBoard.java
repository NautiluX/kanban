package com.ntlx.dao;

public class DatabaseBoard extends Board {
	public DatabaseBoard(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	protected void loadData(){
	}
}
