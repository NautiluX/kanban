package com.ntlx.data.migration;

import java.sql.SQLException;

import com.ntlx.data.Database;

public class DatabaseSchemaMigrationVersion2 extends DatabaseSchemaMigration {
	public DatabaseSchemaMigrationVersion2(Database database) {
		super(database, DatabaseSchemaVersion.VERSION_1, DatabaseSchemaVersion.VERSION_2);
	}
	
	@Override
	public void migrate() throws SQLException {
		database.executeUpdate("ALTER TABLE CARDS ADD COLUMN IS_ARCHIVED INTEGER;");
		updateVersionTable();
	}

}
