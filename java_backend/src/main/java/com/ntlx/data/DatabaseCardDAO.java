package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Card;
import com.ntlx.board.Lane;
import com.ntlx.board.User;

public class DatabaseCardDAO extends DatabaseDAO<Card> {
	String baseSql = "SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, USER_NAME FROM CARDS INNER JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID";
	String laneCardsSql = baseSql + " WHERE LANE_ID = ?";
	String singleCardSql = baseSql + " WHERE CARD_ID = ?";

	String insertSql = "INSERT INTO CARDS (LANE_ID, OWNER_ID, CONTENT) VALUES (?, ?, ?)";
	String updateSql = "UPDATE CARDS SET LANE_ID = ? WHERE CARD_ID = ?";
	String deleteSql = "DELETE FROM CARDS WHERE CARD_ID = ?";
	public DatabaseCardDAO(Database database) throws NamingException, SQLException {
		super(database);
	}

	public void loadDAOs(Lane lane) throws SQLException {
		PreparedStatement statement = database.prepareStatement(laneCardsSql);
		statement.setInt(1, lane.getId());
		ResultSet rs = statement.executeQuery();
		addCardsFromResultSet(rs, lane);
	}

	public Card loadCard(int cardId) throws SQLException {
		PreparedStatement statement = database.prepareStatement(singleCardSql);
		statement.setInt(1, cardId);
		ResultSet rs = statement.executeQuery();
		return getSingleCardFromResultSet(rs);
	}

	private Card getSingleCardFromResultSet(ResultSet rs) throws SQLException {
		rs.next();
		return createCardFromResultSet(rs);
	}

	private void addCardsFromResultSet(ResultSet rs, Lane lane) throws SQLException {
		while (rs.next()) {
			Card card = createCardFromResultSet(rs);
			lane.addCard(card);
		}
	}
	
	private Card createCardFromResultSet(ResultSet rs) throws SQLException {
		User owner = createUserFromResultSet(rs);
		return createCardFromResultSet(rs, owner);
	}

	private User createUserFromResultSet(ResultSet rs) throws SQLException {
		return new User(rs.getInt("OWNER_ID"), rs.getString("USER_NAME"));
	}

	private Card createCardFromResultSet(ResultSet rs, User owner) throws SQLException {
		Card card = new Card(rs.getInt("CARD_ID"), owner, rs.getString("CONTENT"), rs.getInt("LANE_ID"));
		int afterCardId = rs.getInt("AFTER_CARD_ID");
		if (!rs.wasNull()) {
			card.setAfterCardId(afterCardId);
		}
		return card;
	}

	public void update(Card card) throws SQLException {
		PreparedStatement statement = database.prepareStatement(updateSql);
		statement.setInt(1, card.getLaneId());
		statement.setInt(2, card.getId());
		statement.execute();
	}

	public void create(Card card) throws SQLException {
		PreparedStatement statement = database.prepareStatement(insertSql);
		statement.setInt(1, card.getLaneId());
		statement.setInt(2, card.getOwnerId());
		statement.setString(3, card.getContent());
		statement.executeUpdate();
	}

	public void delete(Card card) throws SQLException {
		PreparedStatement statement = database.prepareStatement(deleteSql);
		statement.setInt(1, card.getId());
		statement.executeUpdate();
	}
}
