package com.ntlx.datatest;


import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import com.ntlx.board.Board;
import com.ntlx.board.Card;
import com.ntlx.board.User;
import com.ntlx.boardtest.TestOwner;
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.CardNotFoundException;
import com.ntlx.exception.MigrationFailedException;

public class DatabaseCardDAOTest {
	TestDatabaseDAOFactory factory = new TestDatabaseDAOFactory(TestDatabase.getInstance());
	
	@Test
	public void test() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException {
		DatabaseCardDAO dao = factory.createDatabaseCardDAO();
		Card card = new Card(Card.NEW_CARD_ID, new TestOwner(), "TestContent", 1, 1);
		dao.create(card);
		Card cardRes = dao.loadCard(4);
		Assert.assertEquals("TestContent", cardRes.getContent());
	}

	@Test
	public void testDeleteCardUnauthorized() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException {
		DatabaseCardDAO dao = factory.createDatabaseCardDAO();
		DatabaseBoardDAO boardDao = factory.createDatabaseBoardDAO();
		Card card = new Card(Card.NEW_CARD_ID, new TestOwner(), "TestContent", 1, 1);
		dao.create(card);
		User unauthorizedUser = new User(2, "Evil User");
		Board board = boardDao.loadSingleDAO(card.getBoardId(), unauthorizedUser);
		try {
			dao.delete(board, card);
			Assert.fail("User should not be authorized to delete this card");
		} catch (AuthorizationException e) {
			//ok
		}
	}
	
	@Test
	public void testDeleteCard() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException {
		DatabaseCardDAO dao = factory.createDatabaseCardDAO();
		DatabaseBoardDAO boardDao = factory.createDatabaseBoardDAO();
		User owner = new TestOwner();
		Card card = new Card(Card.NEW_CARD_ID, owner, "TestContent", 1, 1);
		dao.create(card);
		Board board = boardDao.loadSingleDAO(card.getBoardId(), owner);
		try {
			dao.delete(board, card);
		} catch (AuthorizationException e) {
			Assert.fail("User should be authorized to delete this card");
		}
	}
	
	@Test
	public void testDoNotShowArchivedCards() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException, AuthorizationException, CardNotFoundException {
		DatabaseCardDAO dao = factory.createDatabaseCardDAO();
		DatabaseBoardDAO boardDao = factory.createDatabaseBoardDAO();
		User owner = new TestOwner();
		Card card = new Card(Card.NEW_CARD_ID, owner, "TestContent", 1, 1);
		dao.create(card);
		Board board = boardDao.loadSingleDAO(card.getBoardId(), owner);
		System.out.println(board.toString());
		card = board.getCard(4);
		dao.delete(board, card);
		board = boardDao.loadSingleDAO(card.getBoardId(), owner);
		System.out.println(board.toString());
		try {
			board.getCard(4);
			Assert.fail("archived card should not be found");
		} catch (CardNotFoundException e) {
			//ok
		}
	}
	
	@Test
	public void testShowArchivedCards() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException, AuthorizationException, CardNotFoundException {
		DatabaseCardDAO dao = factory.createDatabaseCardDAO();
		DatabaseBoardDAO boardDao = factory.createDatabaseBoardDAO();
		User owner = new TestOwner();
		Card card = new Card(Card.NEW_CARD_ID, owner, "TestContent", 1, 1);
		dao.create(card);
		
		Board board = boardDao.loadSingleDAO(card.getBoardId(), owner);
		card = board.getCard(4);
		dao.delete(board, card);
		boardDao.setShowArchivedCards(true);
		board = boardDao.loadSingleDAO(card.getBoardId(), owner);
		try {
			board.getCard(4);
		} catch (CardNotFoundException e) {
			Assert.fail("archived card should be found");
		}
	}

}
