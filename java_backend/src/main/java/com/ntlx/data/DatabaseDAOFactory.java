package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.NamingException;

public interface DatabaseDAOFactory {
	DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException;
	DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException;
	DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException;
	DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException;
}
