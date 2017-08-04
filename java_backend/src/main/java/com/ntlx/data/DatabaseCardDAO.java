package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Board;
import com.ntlx.board.Card;
import com.ntlx.board.Lane;
import com.ntlx.board.UnsetLane;
import com.ntlx.board.UnsetUser;
import com.ntlx.board.User;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.DeleteNotAllowedException;

public class DatabaseCardDAO extends DatabaseDAO<Card> {
	String baseSql = "SELECT CARD_ID, OWNER_ID, CONTENT, AFTER_CARD_ID, LANE_ID, BOARD_ID, USER_NAME FROM CARDS LEFT JOIN USERS ON CARDS.OWNER_ID = USERS.USER_ID WHERE IS_ARCHIVED IS ?";
	String laneCardsSql = baseSql + " AND LANE_ID = ?";
	String singleCardSql = baseSql + " AND CARD_ID = ?";
	String laneCardsSqlTag = laneCardsSql + " AND CONTENT LIKE ?";

	String insertSql = "INSERT INTO CARDS (BOARD_ID, LANE_ID, OWNER_ID, CONTENT) VALUES (?, ?, ?, ?)";
	String updateSql = "UPDATE CARDS SET LANE_ID = ?, CONTENT = ? WHERE CARD_ID = ?";
	String deleteSql = "UPDATE CARDS SET IS_ARCHIVED = 1 WHERE CARD_ID = ?";
	private String tag = null;
	private boolean isShowArchivedCards;
	
	public DatabaseCardDAO(Database database) throws NamingException, SQLException {
		super(database);
	}
	
	public void setParameterShowArchivedCards(PreparedStatement statement) throws SQLException {
		if (isShowArchivedCards)
			statement.setInt(1, 1);
		else
			statement.setNull(1, java.sql.Types.INTEGER);
			//statement.setInt(1, 1);
	}

	public void loadDAOs(Lane lane) throws SQLException {
		CardSelectionSqlStatement cardStatement = new CardSelectionSqlStatement(isShowArchivedCards, tag, lane.getId());
		PreparedStatement statement = cardStatement.prepareStatement(database);
		ResultSet rs = statement.executeQuery();
		addCardsFromResultSet(rs, lane);
	}

	public Card loadCard(int cardId) throws SQLException {
		PreparedStatement statement = database.prepareStatement(singleCardSql);
		setParameterShowArchivedCards(statement);
		statement.setInt(2, cardId);
		ResultSet rs = statement.executeQuery();
		return getSingleCardFromResultSet(rs);
	}

	private Card getSingleCardFromResultSet(ResultSet rs) throws SQLException {
		rs.next();
		return createCardFromResultSet(rs, UnsetUser.Instance, UnsetLane.Instance);
	}

	private void addCardsFromResultSet(ResultSet rs, Lane lane) throws SQLException {
		while (rs.next()) {
			User owner = createUserFromResultSet(rs);
			Card card = createCardFromResultSet(rs, owner, lane);
			lane.addCard(card);
		}
	}

	private User createUserFromResultSet(ResultSet rs) throws SQLException {
		return new User(rs.getInt("OWNER_ID"), rs.getString("USER_NAME"));
	}

	private Card createCardFromResultSet(ResultSet rs, User owner, Lane lane) throws SQLException {
		Card card = new Card(rs.getInt("CARD_ID"), owner, rs.getString("CONTENT"), lane, rs.getInt("BOARD_ID"));
		int afterCardId = rs.getInt("AFTER_CARD_ID");
		if (!rs.wasNull()) {
			card.setAfterCardId(afterCardId);
		}
		return card;
	}

	public void update(Card card) throws SQLException {
		PreparedStatement statement = database.prepareStatement(updateSql);
		statement.setInt(1, card.getLane().getId());
		statement.setString(2, card.getContent());
		statement.setInt(3, card.getId());
		statement.execute();
	}

	public void create(Card card) throws SQLException {
		PreparedStatement statement = database.prepareStatement(insertSql);
		statement.setInt(1, card.getBoardId());
		statement.setInt(2, card.getLane().getId());
		statement.setInt(3, card.getOwnerId());
		statement.setString(4, card.getContent());
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(Board board, Card card) throws SQLException, AuthorizationException {
		if (board.isContributable())
			delete(card);
		else
			throw new DeleteNotAllowedException();
	}

	private void delete(Card card) throws SQLException {
		PreparedStatement statement = database.prepareStatement(deleteSql);
		statement.setInt(1, card.getId());
		statement.executeUpdate();
	}

	public void setTag(String tag) {
		this.tag  = tag;
	}

	public void setShowArchivedCards(boolean isShowArchivedCards) {
		this.isShowArchivedCards  = isShowArchivedCards;
	}
}
