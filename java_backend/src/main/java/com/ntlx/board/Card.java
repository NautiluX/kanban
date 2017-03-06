package com.ntlx.board;

public class Card {
	public static final int NEW_CARD_ID = -1;
	protected int id;
	protected int afterCardId;
	protected int laneId;
	protected User owner;
	protected String content;
	
	public Card(int id, User owner, String content, int laneId) {
		this.id = id;
		this.owner = owner;
		this.content = content;
		this.laneId = laneId;
	}
	
	public void setAfterCardId (int id) {
		this.afterCardId = id;
	}
	
	public int getAfterCardId () {
		return afterCardId;
	}

	public int getLaneId () {
		return laneId;
	}

	public void moveToLane(Lane lane) {
		laneId = lane.getId();
	}

	public int getOwnerId() {
		return owner.getId();
	}

	public String getContent() {
		return content;
	}

	public int getId() {
		return id;
	}
}
