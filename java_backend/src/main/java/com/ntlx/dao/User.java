package com.ntlx.dao;

public abstract class User extends DAO{
	int id;
	String name;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

    @Override
	public boolean equals(Object rhs) {
    	User rhsUser = (User)rhs;
		return name == rhsUser.getName()
				&& id == rhsUser.getId();
	}
}
