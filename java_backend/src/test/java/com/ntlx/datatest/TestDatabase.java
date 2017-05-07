package com.ntlx.datatest;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	@Override
	public boolean hasVersionTable() throws SQLException  {
		PreparedStatement versionTableStatement = prepareStatement("SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name = ?;");
		versionTableStatement.setString(1, VERSION_TABLE_NAME);
		ResultSet rs = versionTableStatement.executeQuery();
		rs.next();
		return rs.getLong(1) != 0;
	}
}
