package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.exception.MigrationFailedException;

public interface DatabaseDAOFactory {
	DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException;
	DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException;
	DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException;
	DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException, ClassNotFoundException, MigrationFailedException;
}
