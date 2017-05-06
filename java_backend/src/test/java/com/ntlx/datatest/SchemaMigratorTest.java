package com.ntlx.datatest;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import com.ntlx.data.Database;
import com.ntlx.data.migration.DatabaseSchemaVersion;
import com.ntlx.data.migration.SchemaMigrator;

public class SchemaMigratorTest {
	TestDatabaseDAOFactory factory = new TestDatabaseDAOFactory();
	
	@Test
	public void testInstalledVersionIsNotCurrentVersion() throws ClassNotFoundException, NamingException, SQLException{
		Database db = new TestDatabase(DatabaseSchemaVersion.VERSION_0);
		SchemaMigrator m = new SchemaMigrator(db);
		Assert.assertFalse(m.isCurrentVersion());
	}
	
	@Test
	public void testInstalledVersionIsCurrentVersion() throws ClassNotFoundException, NamingException, SQLException{
		Database db = new TestDatabase(DatabaseSchemaVersion.VERSION_1);
		SchemaMigrator m = new SchemaMigrator(db);
		Assert.assertTrue(m.isCurrentVersion());
	}
}
