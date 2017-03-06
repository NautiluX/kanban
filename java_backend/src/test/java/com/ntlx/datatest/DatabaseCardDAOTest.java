package com.ntlx.datatest;


import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import com.ntlx.board.Card;
import com.ntlx.boardtest.TestOwner;
import com.ntlx.data.DatabaseCardDAO;

public class DatabaseCardDAOTest {
	TestDatabaseDAOFactory factory = new TestDatabaseDAOFactory();
	@Test
	public void test() throws ClassNotFoundException, NamingException, SQLException {
		DatabaseCardDAO dao = factory.createDatabaseCardDAO();
		Card card = new Card(Card.NEW_CARD_ID, new TestOwner(), "TestContent", 0);
		dao.create(card);
		Card cardRes = dao.loadCard(4);
		Assert.assertEquals("TestContent", cardRes.getContent());
	}

}
