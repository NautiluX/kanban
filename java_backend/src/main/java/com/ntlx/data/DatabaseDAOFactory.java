package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.NamingException;

public interface DatabaseDAOFactory {
	DatabaseLaneDAO createDatabaseLaneDAO() throws NamingException, SQLException, ClassNotFoundException;
	DatabaseUserDAO createDatabaseUserDAO() throws NamingException, SQLException, ClassNotFoundException;
	DatabaseBoardDAO createDatabaseBoardDAO() throws NamingException, SQLException, ClassNotFoundException;
	DatabaseCardDAO createDatabaseCardDAO() throws NamingException, SQLException, ClassNotFoundException;
}
