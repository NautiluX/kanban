package com.ntlx.datatest;

import java.sql.SQLException;


import javax.naming.NamingException;

import com.ntlx.data.*;
import com.ntlx.exception.MigrationFailedException;

public class TestDatabaseDAOFactory implements DatabaseDAOFactory{
	private Database db;

	public TestDatabaseDAOFactory(Database instance) {
		this.db = instance;
	}

	public DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException {
		return new DatabaseCardDAO(db);
	}

	public DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException {
		return new DatabaseBoardDAO(db);
	}

	public DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException {
		return new DatabaseUserDAO(db);
	}

	public DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException {
		return new DatabaseLaneDAO(db);
	}
}
