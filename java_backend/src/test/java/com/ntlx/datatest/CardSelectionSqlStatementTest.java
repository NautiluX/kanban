package com.ntlx.datatest;


import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import com.ntlx.board.Board;
import com.ntlx.board.Card;
import com.ntlx.board.User;
import com.ntlx.boardtest.TestOwner;
import com.ntlx.data.CardSelectionSqlStatement;
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.CardNotFoundException;
import com.ntlx.exception.MigrationFailedException;

public class CardSelectionSqlStatementTest {
	
	@Test
	public void testSqlWithArchivedShown() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException, AuthorizationException {
		CardSelectionSqlStatement statement = new CardSelectionSqlStatement(true, null, 1);
		Assert.assertEquals("SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, BOARD_ID, USER_NAME, IS_ARCHIVED FROM CARDS LEFT JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID WHERE LANE_ID = ?", statement.getSql());
	}

	@Test
	public void testSqlWithoutArchived() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException, AuthorizationException {
		CardSelectionSqlStatement statement = new CardSelectionSqlStatement(false, null, 1);
		Assert.assertEquals("SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, BOARD_ID, USER_NAME, IS_ARCHIVED FROM CARDS LEFT JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID WHERE LANE_ID = ? AND IS_ARCHIVED IS ?", statement.getSql());
	}

	@Test
	public void testSqlWithoutArchivedTag() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException, AuthorizationException {
		CardSelectionSqlStatement statement = new CardSelectionSqlStatement(false, "X", 1);
		Assert.assertEquals("SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, BOARD_ID, USER_NAME, IS_ARCHIVED FROM CARDS LEFT JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID WHERE LANE_ID = ? AND IS_ARCHIVED IS ? AND CONTENT LIKE ?", statement.getSql());
	}

	@Test
	public void testSqlWithArchivedShownTag() throws ClassNotFoundException, NamingException, SQLException, MigrationFailedException, AuthorizationException {
		CardSelectionSqlStatement statement = new CardSelectionSqlStatement(true, "X", 1);
		Assert.assertEquals("SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, BOARD_ID, USER_NAME, IS_ARCHIVED FROM CARDS LEFT JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID WHERE LANE_ID = ? AND CONTENT LIKE ?", statement.getSql());
	}
}
