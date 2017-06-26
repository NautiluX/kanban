package com.ntlx.board;

import java.util.Vector;

import com.ntlx.exception.CardNotFoundException;

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
	public boolean hasCard(int cardId) {
		for (Card card : cards) {
			if (card.getId() == cardId)
				return true;
		}
		return false;
	}
	public Card getCard(int cardId) throws CardNotFoundException {
		for (Card card : cards) {
			if (card.getId() == cardId)
				return card;
		}
		throw new CardNotFoundException();
	}
}
