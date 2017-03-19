package com.ntlx.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.board.User;

public class DatabaseUserDAO extends DatabaseDAO<User> {
	String baseSql = "SELECT USER_ID, USER_NAME FROM USERS";
	String singleSql = baseSql + " WHERE USER_NAME = ?";
	public DatabaseUserDAO(Database db) throws NamingException, SQLException {
		super(db);
	}

	public void loadDAOs() throws SQLException {
		PreparedStatement statement = database.prepareStatement(baseSql);
		ResultSet rs = statement.executeQuery();
		addUserFromResultSet(rs);
	}

	public User loadUser(String userName) throws SQLException {
		PreparedStatement statement;
		statement = database.prepareStatement(singleSql);
		statement.setString(1, userName);
		ResultSet rs = statement.executeQuery();
		rs.next();
		return createUserFromResultSet(rs);
	}

	private void addUserFromResultSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			User user = createUserFromResultSet(rs);
			addDAO(user.getId(), user);
		}
	}

	private User createUserFromResultSet(ResultSet rs) throws SQLException {
		return new User(rs.getInt("USER_ID"), rs.getString("USER_NAME"));
	}

}
