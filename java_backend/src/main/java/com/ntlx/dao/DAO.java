package com.ntlx.dao;

public abstract class DAO {
	protected abstract void loadData();
	public DAO() {
		loadData();
	}
}
