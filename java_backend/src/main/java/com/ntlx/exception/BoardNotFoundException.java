package com.ntlx.exception;

public class BoardNotFoundException extends KanbanException {
	private static final long serialVersionUID = 1L;
	private int boardId;
	public BoardNotFoundException (int boardId) {
		this.boardId = boardId;
	}

	public String getMessage() {
		return "Board not found. (ID: " + boardId + ")";
	}
}
