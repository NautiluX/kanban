package com.ntlx.board;

public class Card {
	protected int id;
	protected int afterCardId;
	protected User owner;
	protected String content;
	
	public Card(int id, User owner, String content) {
		this.id = id;
		this.owner = owner;
		this.content = content;
	}
	
	public void setAfterCardId (int id) {
		this.afterCardId = id;
	}
	
	public int getAfterCardId () {
		return afterCardId;
	}
}
