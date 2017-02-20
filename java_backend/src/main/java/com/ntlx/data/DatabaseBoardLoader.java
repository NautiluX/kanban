package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.dao.Board;
import com.ntlx.dao.DatabaseBoard;

public class DatabaseBoardLoader extends DatabaseLoader<Board>{
	
	
	public DatabaseBoardLoader() throws NamingException, SQLException {
		super();
	}

	public void loadDAOs() throws SQLException{
		ResultSet rs = database.executeQuery("SELECT BOARD_ID, BOARD_NAME FROM BOARDS");
		createBoards(rs);
	}

	public Board loadSingleDAO(int id) throws SQLException{
		ResultSet rs = executeSingleBoardQuery(id);
		createBoards(rs);
		return getDAO(id);
	}
	
	public ResultSet executeSingleBoardQuery(int id) throws SQLException {
		PreparedStatement stmt = database.prepareStatement("SELECT BOARD_ID, BOARD_NAME FROM BOARDS WHERE BOARD_ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	public void createBoards(ResultSet rs) throws SQLException {
		while (rs.next()) {
			DatabaseBoard board = new DatabaseBoard(rs.getInt("BOARD_ID"), rs.getString("BOARD_NAME"));
			addDAO(board.getId(), board);
		}
	}
}
