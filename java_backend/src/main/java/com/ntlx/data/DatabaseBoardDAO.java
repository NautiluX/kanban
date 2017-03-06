package com.ntlx.data;

import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Board;
import com.ntlx.board.User;
import com.ntlx.board.Lane;

public class DatabaseBoardDAO extends DatabaseDAO<Board>{
	
	private String baseSql = "SELECT BOARD_ID, BOARD_NAME, USER_NAME, USER_ID FROM BOARDS INNER JOIN USERS ON BOARDS.OWNER_ID = USERS.USER_ID";
	
	public DatabaseBoardDAO(Database database) throws NamingException, SQLException {
		super(database);
	}
	
	public void loadDAOs() throws SQLException, NamingException {
		ResultSet rs = database.executeQuery(baseSql);
		createBoards(rs);
	}

	public Board loadSingleDAO(int id) throws SQLException, NamingException {
		ResultSet rs = executeSingleBoardQuery(id);
		createBoards(rs);
		return getDAO(id);
	}
	
	public ResultSet executeSingleBoardQuery(int id) throws SQLException {
		PreparedStatement stmt = database.prepareStatement(baseSql + " WHERE BOARD_ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	public void createBoards(ResultSet rs) throws SQLException, NamingException {
		while (rs.next()) {
			User owner = new User(rs.getInt("USER_ID"), rs.getString("USER_NAME"));
			Board board = new Board(rs.getInt("BOARD_ID"), rs.getString("BOARD_NAME"), owner);
			loadLanes(board);
			addDAO(board.getId(), board);
		}
	}
	
	public void loadLanes(Board board) throws SQLException, NamingException {
		DatabaseLaneDAO databaseLaneDao = new DatabaseLaneDAO(database);
		databaseLaneDao.loadDAOs(board);
	}

	public Lane loadLane(Board board, int laneId) throws NamingException, SQLException {
		DatabaseLaneDAO databaseLaneDao = new DatabaseLaneDAO(database);
		return databaseLaneDao.loadLane(laneId);
	}
}
