package com.ntlx.data;

import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.naming.NamingException;

import com.ntlx.board.Board;
import com.ntlx.board.User;
import com.ntlx.board.Lane;
import com.ntlx.board.Permissions;

public class DatabaseBoardDAO extends DatabaseDAO<Board>{
	
	private String baseSql = "SELECT BOARD_ID, BOARD_NAME, USER_NAME, USER_ID, WORLD_READABLE FROM BOARDS LEFT JOIN USERS ON BOARDS.OWNER_ID = USERS.USER_ID";
	private String tag = null;
	private boolean isShowArchivedCards = false;
	
	public DatabaseBoardDAO(Database db) throws NamingException, SQLException {
		super(db);
	}
	
	public void loadDAOs(User user) throws SQLException, NamingException {
		ResultSet rs = database.executeQuery(baseSql);
		createBoards(rs, user);
	}
	
	public Board loadSingleDAO(int id, User user) throws SQLException, NamingException {
		ResultSet rs = executeSingleBoardQuery(id);
		createBoards(rs, user);
		return getDAO(id);
	}
	
	public ResultSet executeSingleBoardQuery(int id) throws SQLException {
		PreparedStatement stmt = database.prepareStatement(baseSql + " WHERE BOARD_ID = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	public Board loadSingleDAO(int id, User user, String tag) throws SQLException, NamingException {
		ResultSet rs = executeSingleBoardQuery(id);
		this.tag  = tag;
		createBoards(rs, user);
		return getDAO(id);
	}
	
	public void createBoards(ResultSet rs, User user) throws SQLException, NamingException {
		while (rs.next()) {
			User owner = new User(rs.getInt("USER_ID"), rs.getString("USER_NAME"));
			boolean isWorldReadable = rs.getInt("WORLD_READABLE") == 1;
			HashSet<String> permissions = getBoardPermissions(isWorldReadable, user, owner);
			Board board = new Board(rs.getInt("BOARD_ID"), rs.getString("BOARD_NAME"), owner, permissions);
			loadLanes(board);
			addDAO(board.getId(), board);
		}
	}
	
	private HashSet<String> getBoardPermissions(boolean isWorldReadable, User user, User owner) {
		HashSet<String> permissions = new HashSet<String>();
		if (isWorldReadable) {
			permissions.add(Permissions.READ);
		}
		if (user.getId() == owner.getId())
		{
			permissions.add(Permissions.MANAGE);
			permissions.add(Permissions.CONTRIBUTE);
			permissions.add(Permissions.READ);
		}
		return permissions;
	}

	public void loadLanes(Board board) throws SQLException, NamingException {
		DatabaseLaneDAO databaseLaneDao = new DatabaseLaneDAO(database);
		databaseLaneDao.setShowArchivedCards(isShowArchivedCards);
		databaseLaneDao.setTag(tag);
		databaseLaneDao.loadDAOs(board);
	}

	public Lane loadLane(Board board, int laneId) throws NamingException, SQLException {
		DatabaseLaneDAO databaseLaneDao = new DatabaseLaneDAO(database);
		return databaseLaneDao.loadLane(laneId);
	}

	public void setShowArchivedCards(boolean isShowArchivedCards) {
		this.isShowArchivedCards  = isShowArchivedCards;
	}
}
