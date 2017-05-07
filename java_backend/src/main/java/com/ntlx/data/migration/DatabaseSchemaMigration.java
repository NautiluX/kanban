package com.ntlx.data.migration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ntlx.data.Database;

public abstract class DatabaseSchemaMigration {
	
	protected DatabaseSchemaVersion targetVersion = DatabaseSchemaVersion.VERSION_UNKNOWN;
	protected DatabaseSchemaVersion sourceVersion = DatabaseSchemaVersion.VERSION_UNKNOWN;
	protected Database database;
	
	public DatabaseSchemaMigration(Database database, DatabaseSchemaVersion sourceVersion, DatabaseSchemaVersion targetVersion) {
		this.database = database;
		this.sourceVersion = sourceVersion;
		this.targetVersion = targetVersion;
	}
	
	public abstract void migrate() throws SQLException;
	
	public boolean isMigrationCompatible(DatabaseSchemaVersion installedDatabaseSchemaVersion) {
		return installedDatabaseSchemaVersion == sourceVersion && installedDatabaseSchemaVersion.getNextVersion().equals(targetVersion);
	}

	protected void updateVersionTable() throws SQLException {
		PreparedStatement stmt = database.prepareStatement("INSERT INTO " + Database.VERSION_TABLE_NAME + "(" + Database.VERSION_COLUMN + ")VALUES (?)");
		stmt.setLong(1, targetVersion.getNumber());
		stmt.executeUpdate();
	}
}
