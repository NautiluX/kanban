package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.NamingException;

public class ProductiveDatabaseDAOFactory implements DatabaseDAOFactory{
	public DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException {
		return new DatabaseCardDAO(ProductiveDatabase.getInstance());
	}

	public DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException {
		return new DatabaseBoardDAO(ProductiveDatabase.getInstance());
	}

	public DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException {
		return new DatabaseUserDAO(ProductiveDatabase.getInstance());
	}

	public DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException {
		return new DatabaseLaneDAO(ProductiveDatabase.getInstance());
	}
}
