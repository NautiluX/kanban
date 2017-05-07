package com.ntlx.datatest;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.ntlx.data.Database;
import com.ntlx.data.migration.DatabaseSchemaVersion;

public class TestDatabaseConstantVersion extends Database {
	private DatabaseSchemaVersion schemaVersion;
	
	public TestDatabaseConstantVersion(DatabaseSchemaVersion schemaVersion) throws NamingException, SQLException, ClassNotFoundException {
        this.schemaVersion = schemaVersion;
	}

	public void initializeDatabaseConnection() throws NamingException, SQLException, ClassNotFoundException {
    }

    public static Database getInstance() throws NamingException, SQLException, ClassNotFoundException {
    	return getInstance(DatabaseSchemaVersion.VERSION_UNKNOWN);
    }
	
    public static Database getInstance(DatabaseSchemaVersion schemaVersion) throws NamingException, SQLException, ClassNotFoundException {
		Database db = new TestDatabaseConstantVersion(schemaVersion);
        return db;
    }

	@Override
	public boolean hasVersionTable() throws SQLException {
		return false;
	}
	
	@Override
	public DatabaseSchemaVersion getCurrentSchemaVersion() throws SQLException {
		return schemaVersion;
	}
}
