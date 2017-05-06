package com.ntlx.datatest;

import org.junit.Assert;
import org.junit.Test;

import com.ntlx.data.migration.DatabaseSchemaVersion;

public class DatabaseSchemaVersionTest {
	TestDatabaseDAOFactory factory = new TestDatabaseDAOFactory();
	
	@Test
	public void testVersionFromNumberUnknown(){
		DatabaseSchemaVersion version = DatabaseSchemaVersion.fromNumber(-1);
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_UNKNOWN, version);
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_UNKNOWN, version.getNextVersion());
	}
	
	@Test
	public void testVersionFromNumberVersion0(){
		DatabaseSchemaVersion version = DatabaseSchemaVersion.fromNumber(0);
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_0, version);
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_1, version.getNextVersion());
	}
	
	@Test
	public void testVersionFromNumberVersion1(){
		DatabaseSchemaVersion version = DatabaseSchemaVersion.fromNumber(1);
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_1, version);
		Assert.assertEquals(DatabaseSchemaVersion.VERSION_1, version.getNextVersion());
	}
}
