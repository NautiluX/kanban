package com.ntlx.datatest;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.data.Database;
import com.ntlx.data.DatabaseSetup;
import com.ntlx.data.migration.DatabaseSchemaVersion;

public class TestDatabase extends Database {
	private DatabaseSchemaVersion schemaVersion;
	
	public TestDatabase(DatabaseSchemaVersion schemaVersion) throws NamingException, SQLException, ClassNotFoundException {
		initializeDatabaseConnection();
        this.schemaVersion = schemaVersion;
	}

	public void initializeDatabaseConnection() throws NamingException, SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        conn.setAutoCommit(true);
    }

    public static Database getInstance() throws NamingException, SQLException, ClassNotFoundException {
    	return getInstance(DatabaseSchemaVersion.VERSION_UNKNOWN);
    }
	
    public static Database getInstance(DatabaseSchemaVersion schemaVersion) throws NamingException, SQLException, ClassNotFoundException {
		Database db = new TestDatabase(schemaVersion);
        DatabaseSetup dbSetup = new TestDatabaseSetup(db);
        dbSetup.executeSetup();
        return db;
    }

	@Override
	public DatabaseSchemaVersion getCurrentSchemaVersion() throws SQLException {
		return schemaVersion;
	}
}
