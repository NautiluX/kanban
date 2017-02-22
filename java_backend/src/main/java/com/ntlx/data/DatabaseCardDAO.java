package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Card;
import com.ntlx.board.Lane;
import com.ntlx.board.User;

public class DatabaseCardDAO extends DatabaseDAO<Card> {
	Lane lane;
	String baseSql = "SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, USER_NAME FROM CARDS INNER JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID WHERE LANE_ID = ?";
	public DatabaseCardDAO(Lane lane) throws NamingException, SQLException {
		super();
		this.lane = lane;
	}

	@Override
	public void loadDAOs() throws SQLException {
		PreparedStatement statement = database.prepareStatement(baseSql);
		statement.setInt(1, lane.getId());
		ResultSet rs = statement.executeQuery();
		addCardsFromResultSet(rs);
	}

	private void addCardsFromResultSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			User owner = createUserFromResultSet(rs);
			Card card = createCardFromResultSet(rs, owner);
			lane.addCard(card);
		}
	}

	private User createUserFromResultSet(ResultSet rs) throws SQLException {
		return new User(rs.getInt("OWNER_ID"), rs.getString("USER_NAME"));
	}

	private Card createCardFromResultSet(ResultSet rs, User owner) throws SQLException {
		Card card = new Card(rs.getInt("CARD_ID"), owner, rs.getString("CONTENT"));
		int afterCardId = rs.getInt("AFTER_CARD_ID");
		if (!rs.wasNull()) {
			card.setAfterCardId(afterCardId);
		}
		return card;
	}

}
