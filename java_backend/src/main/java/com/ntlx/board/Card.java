package com.ntlx.board;

public class Card {
	public static final int NEW_CARD_ID = -1;
	protected int id;
	protected int afterCardId;
	protected transient Lane lane;
	protected User owner;
	protected String content;
	protected int boardId;
	private boolean isArchived = false;
	
	public Card(int id, User owner, String content, Lane lane, int boardId) {
		this.id = id;
		this.owner = owner;
		this.content = content;
		this.lane = lane;
		this.boardId = boardId;
	}
	
	public void setAfterCardId (int id) {
		this.afterCardId = id;
	}
	
	public int getAfterCardId () {
		return afterCardId;
	}

	public Lane getLane () {
		return lane;
	}

	public void moveToLane(Lane lane) {
		this.lane = lane;
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

	public int getBoardId() {
		return boardId;
	}

	public void setContent(String string) {
		this.content = string;
	}

	public void setIsArchived(boolean b) {
		this.isArchived  = b;
	}
}
