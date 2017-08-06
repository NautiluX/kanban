package com.ntlx.board;

import java.util.HashSet;
import java.util.Vector;

import com.google.gson.Gson;
import com.ntlx.exception.CardNotFoundException;
import com.ntlx.exception.LaneNotFoundException;

public class Board{
	protected String name;
	protected int id;
	protected User owner;
	protected Vector<Lane> lanes;
	protected HashSet<String> permissions;
	
	public Board(int id, String name, User owner, HashSet<String> permissions) {
		this.name = name;
		this.id = id;
		this.owner = owner;
		this.lanes = new Vector<Lane>();
		this.permissions = permissions;
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

	public boolean isReadable() {
		return hasPermission(Permissions.READ);
	}

	public boolean isContributable() {
		return hasPermission(Permissions.CONTRIBUTE);
	}

	private boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
	
	public Card getCard(int cardId) throws CardNotFoundException {
		for (Lane lane : lanes) {
			if (lane.hasCard(cardId)) {
				return lane.getCard(cardId);
			}
		}
		throw new CardNotFoundException();
	}

	public Lane getLane(int laneId) throws LaneNotFoundException {
		for (Lane lane : lanes) {
			if (lane.id == laneId) {
				return lane;
			}
		}
		throw new LaneNotFoundException();
	}
}
