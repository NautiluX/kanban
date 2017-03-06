package com.ntlx.datatest;

import com.ntlx.data.Database;
import com.ntlx.data.DatabaseSetup;

public class TestDatabaseSetup extends DatabaseSetup {

	public TestDatabaseSetup(Database db) {
		super(db);
		autoIncrement = "";
	}
}
