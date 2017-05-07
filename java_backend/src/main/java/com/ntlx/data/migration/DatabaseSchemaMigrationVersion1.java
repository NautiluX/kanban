package com.ntlx.data.migration;

import java.sql.SQLException;

import com.ntlx.data.Database;

public class DatabaseSchemaMigrationVersion1 extends DatabaseSchemaMigration {
	public DatabaseSchemaMigrationVersion1(Database database) {
		super(database, DatabaseSchemaVersion.VERSION_0, DatabaseSchemaVersion.VERSION_1);
	}
	
	@Override
	public void migrate() throws SQLException {
		database.executeUpdate("CREATE TABLE " + Database.VERSION_TABLE_NAME + " (" + Database.VERSION_COLUMN + " BIGINT NOT NULL)");
		updateVersionTable();
	}

}
