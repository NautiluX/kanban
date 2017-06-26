package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Board;
import com.ntlx.board.Lane;

public class DatabaseLaneDAO extends DatabaseDAO<Lane>{
	private String baseSql = "SELECT LANE_ID, TITLE FROM LANES";
	private String boardLanesSql = baseSql + " WHERE BOARD_ID = ?";
	private String singleLaneSql = baseSql + " WHERE LANE_ID = ?";
	
	private String tag;
	private boolean isShowArchivedCards;
	
	public DatabaseLaneDAO(Database db) throws NamingException, SQLException {
		super(db);
	}
	
	public void loadDAOs(Board board) throws SQLException, NamingException {
		PreparedStatement statement = database.prepareStatement(boardLanesSql);
		statement.setInt(1, board.getId());
		ResultSet rs = statement.executeQuery();
		createLanes(rs, board);
	}

	public Lane loadLane(int id) throws SQLException, NamingException {
		PreparedStatement statement = database.prepareStatement(singleLaneSql);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		rs.next();
		return createLaneFromResultSet(rs);
	}

	private void createLanes(ResultSet rs, Board board) throws SQLException, NamingException {
		while (rs.next()) {
			Lane lane = createLaneFromResultSet(rs);
			board.addLane(lane);
		}
	}
	
	private Lane createLaneFromResultSet(ResultSet rs) throws SQLException, NamingException {
		Lane lane = new Lane(rs.getInt("LANE_ID"), rs.getString("TITLE"));
		addCards(lane);
		return lane;
	}
	
	private void addCards(Lane lane) throws SQLException, NamingException {
		DatabaseCardDAO databaseCardDao = new DatabaseCardDAO(database);
		databaseCardDao.setTag(tag);
		databaseCardDao.setShowArchivedCards(isShowArchivedCards);
		databaseCardDao.loadDAOs(lane);
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}

	
	public void setShowArchivedCards(boolean isShowArchivedCards) {
		this.isShowArchivedCards  = isShowArchivedCards;
	}
}
