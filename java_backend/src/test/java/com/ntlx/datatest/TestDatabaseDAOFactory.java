package com.ntlx.datatest;

import java.sql.SQLException;


import javax.naming.NamingException;

import com.ntlx.data.*;

public class TestDatabaseDAOFactory implements DatabaseDAOFactory{
	public DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException, ClassNotFoundException {
		return new DatabaseCardDAO(TestDatabase.getInstance());
	}

	public DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException, ClassNotFoundException {
		return new DatabaseBoardDAO(TestDatabase.getInstance());
	}

	public DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException, ClassNotFoundException {
		return new DatabaseUserDAO(TestDatabase.getInstance());
	}

	public DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException, ClassNotFoundException {
		return new DatabaseLaneDAO(TestDatabase.getInstance());
	}
}
