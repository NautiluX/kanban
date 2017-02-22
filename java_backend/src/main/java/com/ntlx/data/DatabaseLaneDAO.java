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
	
	public DatabaseLaneDAO(Board board, Database database) {
		super(database);
		this.board = board;
	}

	@Override
	public void loadDAOs() throws SQLException {
		PreparedStatement statement = database.prepareStatement(baseSql);
		statement.setInt(1, board.getId());
		ResultSet rs = statement.executeQuery();
		createLanes(rs);
	}

	private void createLanes(ResultSet rs) throws SQLException {
		while (rs.next()) {
			Lane lane = createLaneFromResultSet(rs);
			board.addLane(lane);
		}
	}
	
	private Lane createLaneFromResultSet(ResultSet rs) throws SQLException {
		return new Lane(rs.getInt("LANE_ID"), rs.getString("TITLE"));
	}

	@Override
	public Lane loadSingleDAO(int id) throws SQLException {
		PreparedStatement statement = database.prepareStatement(baseSql + " AND ID = ?");
		statement.setInt(1, board.getId());
		statement.setInt(2, id);
		ResultSet rs = statement.executeQuery();
		return createLaneFromResultSet(rs);
	}
}
