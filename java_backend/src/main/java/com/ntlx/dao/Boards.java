package com.ntlx.dao;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Boards {
	
	ArrayList<Board> boards;

	public Boards(ArrayList<Board> boardsArray) {
		this.boards = boardsArray;
	}

	public String toString() {

		Gson gson = new Gson();
		String json = gson.toJson(boards);  
		return json;
	}

}
