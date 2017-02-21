package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.Board;

public class DatabaseBoardLoader extends DatabaseLoader<Board>{
	
	private String baseSql = "SELECT BOARD_ID, BOARD_NAME, USER_NAME, USER_ID FROM BOARDS INNER JOIN USERS ON BOARDS.OWNER_ID = USERS.USER_ID";
	
	public DatabaseBoardLoader() throws NamingException, SQLException {
		super();
	}
	
	@Override
	public void loadDAOs() throws SQLException {
		ResultSet rs = database.executeQuery(baseSql);
		createBoards(rs);
	}

	@Override
	public Board loadSingleDAO(int id) throws SQLException {
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
	
	public void createBoards(ResultSet rs) throws SQLException {
		while (rs.next()) {
			DatabaseUser user = new DatabaseUser(rs.getInt("USER_ID"), rs.getString("USER_NAME"));
			DatabaseBoard board = new DatabaseBoard(rs.getInt("BOARD_ID"), rs.getString("BOARD_NAME"), user);
			addDAO(board.getId(), board);
		}
	}
}
