package com.ntlx.datatest;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import com.ntlx.data.Database;
import com.ntlx.data.migration.DatabaseSchemaMigrationVersion1;
import com.ntlx.data.migration.DatabaseSchemaVersion;
import com.ntlx.data.migration.SchemaMigrator;
import com.ntlx.exception.MigrationFailedException;

public class SchemaMigratorTest {
	TestDatabaseDAOFactory factory = new TestDatabaseDAOFactory(TestDatabase.getInstance());
	
	@Test
	public void testInstalledVersionIsNotCurrentVersion() throws ClassNotFoundException, NamingException, SQLException{
		Database db = new TestDatabaseConstantVersion(DatabaseSchemaVersion.VERSION_0);
		SchemaMigrator m = new SchemaMigrator(db);
		Assert.assertFalse(m.isCurrentVersion());
	}
	
	@Test
	public void testInstalledVersionIsCurrentVersion() throws ClassNotFoundException, NamingException, SQLException{
		Database db = new TestDatabaseConstantVersion(DatabaseSchemaVersion.VERSION_2);
		SchemaMigrator m = new SchemaMigrator(db);
		Assert.assertTrue(m.isCurrentVersion());
	}
	
	@Test
	public void testVersionTableNotExisting() throws SQLException, ClassNotFoundException, NamingException {
		Database db = new TestDatabase();
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_0, db.getCurrentSchemaVersion());
	}

	@Test
	public void testVersionTableNotCurrentVersion() throws SQLException, ClassNotFoundException, NamingException {
		Database db = new TestDatabase();
		SchemaMigrator m = new SchemaMigrator(db);
		Assert.assertFalse(m.isCurrentVersion());
	}

	@Test
	public void testHasVersionTable() throws SQLException, ClassNotFoundException, NamingException {
		Database db = new TestDatabase();
		Assert.assertFalse(db.hasVersionTable());
	}

	@Test
	public void testCreateVersionTable() throws SQLException, ClassNotFoundException, NamingException, MigrationFailedException {
		Database db = TestDatabase.getInstance(); //Automatically migrated
		Assert.assertTrue(db.hasVersionTable());
	}
	
	@Test
	public void testUpgradeToLatestVersion() throws SQLException, ClassNotFoundException, NamingException, MigrationFailedException {
		Database db = TestDatabase.getInstance();
		SchemaMigrator m = new SchemaMigrator(db);
		m.migrate();
		Assert.assertTrue(m.isCurrentVersion());
	}
}
