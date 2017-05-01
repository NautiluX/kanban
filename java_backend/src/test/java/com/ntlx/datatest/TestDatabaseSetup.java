package com.ntlx.datatest;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.data.Database;
import com.ntlx.data.DatabaseSetup;

public class TestDatabaseSetup extends DatabaseSetup {

	public TestDatabaseSetup(Database db) {
		super(db);
		autoIncrement = "";
	}
	
	public void executeSetup() throws SQLException, NamingException{
		deleteTablesIfExisting();
    	createTables();
    	createInitialData();
    	createTestData();
    }

	private void createTestData() throws SQLException {
    	db.executeUpdate("INSERT INTO USERS (USER_NAME, PASSWORD) VALUES ('example_user', 'secret')");
    	db.executeUpdate("INSERT INTO USERS (USER_NAME, PASSWORD) VALUES ('unauthorized_user', 'evil')");

    	db.executeUpdate("INSERT INTO USER_ROLES (USER_NAME, ROLE_NAME) VALUES ('example_user', 'kanban_user')");
    	db.executeUpdate("INSERT INTO USER_ROLES (USER_NAME, ROLE_NAME) VALUES ('unauthorized_user', 'kanban_user')");
	}
}
