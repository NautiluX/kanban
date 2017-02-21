package com.ntlx.data;

import com.ntlx.board.Board;
import com.ntlx.board.User;

public class DatabaseBoard extends Board {
	public DatabaseBoard(int id, String name, User owner) {
		this.id = id;
		this.name = name;
		this.owner = owner;
	}
}
