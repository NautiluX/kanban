package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.NamingException;

public class DatabaseDAOFactory {
	public static DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException {
		return new DatabaseCardDAO(Database.getInstance());
	}

	public static DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException {
		return new DatabaseBoardDAO(Database.getInstance());
	}

	public static DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException {
		return new DatabaseUserDAO(Database.getInstance());
	}

	public static DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException {
		return new DatabaseLaneDAO(Database.getInstance());
	}
}
