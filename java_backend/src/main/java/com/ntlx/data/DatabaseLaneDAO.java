package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Board;
import com.ntlx.board.Lane;

public class DatabaseLaneDAO extends DatabaseDAO<Lane>{
	private String baseSql = "SELECT LANE_ID, TITLE FROM LANES WHERE BOARD_ID = ?";
	private Board board;
	
	public DatabaseLaneDAO(Board board) throws NamingException, SQLException {
		super();
		this.board = board;
	}

	@Override
	public void loadDAOs() throws SQLException, NamingException {
		PreparedStatement statement = database.prepareStatement(baseSql);
		statement.setInt(1, board.getId());
		ResultSet rs = statement.executeQuery();
		createLanes(rs);
	}

	private void createLanes(ResultSet rs) throws SQLException, NamingException {
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
		DatabaseCardDAO databaseCardDao = new DatabaseCardDAO(lane);
		databaseCardDao.loadDAOs();
	}
}
