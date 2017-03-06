package com.ntlx.datatest;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.data.Database;
import com.ntlx.data.DatabaseSetup;

public class TestDatabase extends Database {
	public TestDatabase() throws NamingException, SQLException, ClassNotFoundException {
		initializeDatabaseConnection();
	}

	public void initializeDatabaseConnection() throws NamingException, SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        conn.setAutoCommit(true);
    }
	
    public static Database getInstance() throws NamingException, SQLException, ClassNotFoundException {
		Database db = new TestDatabase();
        DatabaseSetup dbSetup = new TestDatabaseSetup(db);
        dbSetup.executeSetup();
        return db;
    }
}
