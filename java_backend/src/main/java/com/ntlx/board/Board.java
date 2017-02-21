package com.ntlx.board;

import com.google.gson.Gson;

public abstract class Board{
	protected String name;
	protected int id;
	protected User owner;
	
	public Board() {
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
