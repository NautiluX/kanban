package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.NamingException;

public class DatabaseSetup {

	protected Database db;
	protected String autoIncrement = "AUTO_INCREMENT";
	String primaryKey = "PRIMARY KEY";

	
    public DatabaseSetup(Database db) {
		this.db = db;
	}
	
	public void executeSetup() throws SQLException, NamingException{
    	createTables();
    	createInitialData();
    }
	
	public void deleteTablesIfExisting(){
    	try {
    		deleteTables();
    	} catch (SQLException e) {
    		//Ignore, they don't necessarily exist
    	}
    }
    
    public void deleteTables() throws SQLException {
    	deleteTable("CARDS_TAGS");
    	deleteTable("TAGS");
    	deleteTable("CARDS");
    	deleteTable("LANES");
    	deleteTable("BOARDS");
    	deleteTable("USER_ROLES");
    	deleteTable("USERS");
    }
    
    public void deleteTable(String tableName) throws SQLException {
    	db.executeUpdate("DROP TABLE " + tableName + " CASCADE");
    }
    
    public void createTables() throws NamingException, SQLException{
		db.executeUpdate("CREATE TABLE USERS (USER_ID INTEGER NOT NULL " + autoIncrement + ", USER_NAME NVARCHAR(200), PASSWORD NVARCHAR(200),  " + primaryKey  + " (USER_ID))");
    	db.executeUpdate("CREATE TABLE USER_ROLES (USER_NAME NVARCHAR(200), ROLE_NAME NVARCHAR(200), PRIMARY KEY (USER_NAME, ROLE_NAME))");
    	db.executeUpdate("CREATE TABLE BOARDS (BOARD_ID INTEGER NOT NULL " + autoIncrement + ", BOARD_NAME NVARCHAR(200), OWNER_ID INTEGER NOT NULL, WORLD_READABLE INTEGER, " + primaryKey  + " (BOARD_ID))");
    	db.executeUpdate("CREATE TABLE LANES (LANE_ID INTEGER NOT NULL " + autoIncrement + ", BOARD_ID INTEGER NOT NULL, TITLE NVARCHAR(5000), AFTER_LANE_ID INTEGER, " + primaryKey  + " (LANE_ID))");
    	db.executeUpdate("CREATE TABLE CARDS (CARD_ID INTEGER NOT NULL " + autoIncrement + ", LANE_ID INTEGER NOT NULL, BOARD_ID INTEGER NOT NULL, OWNER_ID INTEGER NOT NULL, AFTER_CARD_ID INTEGER, CONTENT NVARCHAR(5000), " + primaryKey  + " (CARD_ID))");
    	db.executeUpdate("CREATE TABLE TAGS (TAG_ID INTEGER NOT NULL " + autoIncrement + ", TEXT NVARCHAR(5000), " + primaryKey  + " (TAG_ID))");
    	db.executeUpdate("CREATE TABLE CARDS_TAGS (TAG_ID INTEGER NOT NULL " + autoIncrement + ", CARD_ID INTEGER NOT NULL, PRIMARY KEY (TAG_ID, CARD_ID))");
    }
    
    public void createInitialData() throws SQLException {
    	db.executeUpdate("INSERT INTO BOARDS (BOARD_NAME, OWNER_ID, WORLD_READABLE) VALUES ('Default Board', 1, 1)");
    	
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, NULL, 'Backlog')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 1, 'Ready')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 2, 'Blocked')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 3, 'In progress')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 4, 'Done')");
    	
    	db.executeUpdate("INSERT INTO CARDS (BOARD_ID, LANE_ID, AFTER_CARD_ID, CONTENT, OWNER_ID) VALUES (1, 1, NULL, 'Example TODO', 1)");
    	db.executeUpdate("INSERT INTO CARDS (BOARD_ID, LANE_ID, AFTER_CARD_ID, CONTENT, OWNER_ID) VALUES (1, 4, NULL, 'Example In Progress', 1)");
    	db.executeUpdate("INSERT INTO CARDS (BOARD_ID, LANE_ID, AFTER_CARD_ID, CONTENT, OWNER_ID) VALUES (1, 5, NULL, 'Example DONE', 1)");
    }
}
