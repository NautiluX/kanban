package com.ntlx.board;

import java.util.Vector;

public class Lane {
	protected int id;
	protected String title;
	protected Vector<Card> cards;
	public Lane(int id, String title) {
		this.id = id;
		this.title = title;
		cards = new Vector<Card>();
	}
	public void addCard(Card card) {
		cards.add(card);
	}
	public int getId() {
		return id;
	}
}
