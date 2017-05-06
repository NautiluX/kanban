package com.ntlx.data.migration;

import java.sql.SQLException;

import com.ntlx.data.Database;

public class SchemaMigrator {
	private Database database;
	private DatabaseSchemaVersion installedDatabaseSchemaVersion;
	public static final DatabaseSchemaVersion currentDatabaseSchemaVersion = DatabaseSchemaVersion.VERSION_1;
	public SchemaMigrator(Database database) throws SQLException {
		this.database = database;
		this.installedDatabaseSchemaVersion = database.getCurrentSchemaVersion();
	}
	
	public boolean isCurrentVersion() {
		return installedDatabaseSchemaVersion == currentDatabaseSchemaVersion;
	}
	
	public void migrate() throws SQLException {
		if (!isCurrentVersion())
		{
			installedDatabaseSchemaVersion.getNextVersion();
		}
	}
}
