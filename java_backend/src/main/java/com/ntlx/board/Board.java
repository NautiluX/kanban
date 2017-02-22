package com.ntlx.board;

import java.util.Vector;

import com.google.gson.Gson;

public class Board{
	protected String name;
	protected int id;
	protected User owner;
	protected Vector<Lane> lanes;
	
	public Board(int id, String name, User owner) {
		this.name = name;
		this.id = id;
		this.owner = owner;
		this.lanes = new Vector<Lane>();
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
	
	public Vector<Lane> getLanes() {
		return lanes;
	}
	
	public void addLane(Lane lane) {
		lanes.add(lane);
	}
	
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
