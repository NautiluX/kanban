package com.ntlx.rest;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.data.Database;

/**
 * Servlet implementation class Setup
 */
@WebServlet("/setup")
public class Setup extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * Default constructor. 
     */
	Database db;

    public Setup() {
    }
    
    public void executeSetup() throws SQLException, NamingException{
    	db = Database.getInstance();
		deleteTablesIfExisting();
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
    	deleteTable("USERS");
    }
    
    public void deleteTable(String tableName) throws SQLException {
    	db.executeUpdate("DROP TABLE " + tableName + " CASCADE");
    }
    
    public void createTables() throws NamingException, SQLException{
    	db.executeUpdate("CREATE TABLE USERS (USER_ID INTEGER NOT NULL AUTO_INCREMENT, USER_NAME NVARCHAR(200), PASSWORD NVARCHAR(200), PRIMARY KEY (USER_ID))");
    	db.executeUpdate("CREATE TABLE BOARDS (BOARD_ID INTEGER NOT NULL AUTO_INCREMENT, BOARD_NAME NVARCHAR(200), OWNER_ID INTEGER NOT NULL, PRIMARY KEY (BOARD_ID))");
    	db.executeUpdate("CREATE TABLE LANES (LANE_ID INTEGER NOT NULL AUTO_INCREMENT, BOARD_ID INTEGER NOT NULL, TITLE NVARCHAR(5000), AFTER_LANE_ID INTEGER, PRIMARY KEY (LANE_ID))");
    	db.executeUpdate("CREATE TABLE CARDS (CARD_ID INTEGER NOT NULL AUTO_INCREMENT, LANE_ID INTEGER NOT NULL, OWNER_ID INTEGER NOT NULL, AFTER_CARD_ID INTEGER, CONTENT NVARCHAR(5000), PRIMARY KEY (CARD_ID))");
    	db.executeUpdate("CREATE TABLE TAGS (TAG_ID INTEGER NOT NULL AUTO_INCREMENT, TEXT NVARCHAR(5000), PRIMARY KEY (TAG_ID))");
    	db.executeUpdate("CREATE TABLE CARDS_TAGS (TAG_ID INTEGER NOT NULL AUTO_INCREMENT, CARD_ID INTEGER NOT NULL, PRIMARY KEY (TAG_ID, CARD_ID))");
    }
    
    public void createInitialData() throws SQLException {
    	db.executeUpdate("INSERT INTO USERS (USER_NAME, PASSWORD) VALUES ('admin', 'secret')");
    	
    	db.executeUpdate("INSERT INTO BOARDS (BOARD_NAME, OWNER_ID) VALUES ('Default Board', 1)");
    	
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, NULL, 'Backlog')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 1, 'Ready')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 2, 'Blocked')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 3, 'In progress')");
    	db.executeUpdate("INSERT INTO LANES (BOARD_ID, AFTER_LANE_ID, TITLE) VALUES (1, 4, 'Done')");
    	
    	db.executeUpdate("INSERT INTO CARDS (LANE_ID, AFTER_CARD_ID, CONTENT, OWNER_ID) VALUES (1, NULL, 'Example TODO', 1)");
    	db.executeUpdate("INSERT INTO CARDS (LANE_ID, AFTER_CARD_ID, CONTENT, OWNER_ID) VALUES (4, NULL, 'Example In Progress', 1)");
    	db.executeUpdate("INSERT INTO CARDS (LANE_ID, AFTER_CARD_ID, CONTENT, OWNER_ID) VALUES (5, NULL, 'Example DONE', 1)");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			executeSetup();

			ResultSet rs = db.executeQuery("SELECT CARD_ID, CONTENT, TITLE FROM CARDS INNER JOIN LANES ON CARDS.LANE_ID = LANES.LANE_ID");
	
	        while (rs.next()) {
	            response.getWriter().println(rs.getInt("CARD_ID") + " " + rs.getString("CONTENT") + " " + rs.getString("TITLE"));
	        }
		}
		catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
		}
	}	
}
