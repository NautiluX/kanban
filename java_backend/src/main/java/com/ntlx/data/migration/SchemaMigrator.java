package com.ntlx.data.migration;

import java.sql.SQLException;
import java.util.Vector;

import com.ntlx.data.Database;
import com.ntlx.exception.MigrationFailedException;

public class SchemaMigrator {
	private Database database;
	private DatabaseSchemaVersion installedDatabaseSchemaVersion;
	private Vector<DatabaseSchemaMigration> schemaMigrations;
	
	public SchemaMigrator(Database database) throws SQLException {
		this.database = database;
		initializeSchemaMigrations();
		installedDatabaseSchemaVersion = getInstalledDatabaseSchemaVersion();
	}
	
	private void initializeSchemaMigrations() {
		schemaMigrations = new Vector<DatabaseSchemaMigration>();
		schemaMigrations.add(new DatabaseSchemaMigrationVersion1(database));
		schemaMigrations.add(new DatabaseSchemaMigrationVersion2(database));
	}

	public boolean isCurrentVersion() {
		return installedDatabaseSchemaVersion == DatabaseSchemaVersion.latestDatabaseSchemaVersion;
	}
	
	public void migrate() throws SQLException, MigrationFailedException {
		while (!isCurrentVersion())
		{
			DatabaseSchemaMigration migration = getSchemaMigration(installedDatabaseSchemaVersion);
			migration.migrate();
			if (getInstalledDatabaseSchemaVersion() == installedDatabaseSchemaVersion) {
				throw new MigrationFailedException(installedDatabaseSchemaVersion);
			} else {
				this.installedDatabaseSchemaVersion = getInstalledDatabaseSchemaVersion();
			}
		}
	}

	private DatabaseSchemaMigration getSchemaMigration(DatabaseSchemaVersion installedDatabaseSchemaVersion2) throws MigrationFailedException {
		for (DatabaseSchemaMigration migration : schemaMigrations) {
			if (migration.isMigrationCompatible(installedDatabaseSchemaVersion))
			{
				return migration;
			}
		}
		throw new MigrationFailedException(installedDatabaseSchemaVersion);
	}

	private DatabaseSchemaVersion getInstalledDatabaseSchemaVersion() throws SQLException {
		return database.getCurrentSchemaVersion();
	}
}
