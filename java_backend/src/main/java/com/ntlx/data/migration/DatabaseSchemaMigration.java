package com.ntlx.data.migration;

import java.sql.SQLException;

import com.ntlx.data.Database;

public abstract class DatabaseSchemaMigration {
	
	protected DatabaseSchemaVersion targetVersion = DatabaseSchemaVersion.VERSION_UNKNOWN;
	protected DatabaseSchemaVersion sourceVersion = DatabaseSchemaVersion.VERSION_UNKNOWN;
	protected Database database;
	public abstract void migrate();
	public boolean checkCompatibleVersion() throws SQLException {
		return database.getCurrentSchemaVersion() == sourceVersion;
	}
}
