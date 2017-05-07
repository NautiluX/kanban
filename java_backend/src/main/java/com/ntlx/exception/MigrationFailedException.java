package com.ntlx.exception;

import com.ntlx.data.migration.DatabaseSchemaVersion;

public class MigrationFailedException extends Exception {
	private static final long serialVersionUID = 1L;
	DatabaseSchemaVersion version;
	public MigrationFailedException(DatabaseSchemaVersion version) {
		this.version = version;
	}
	
	public String getMessage() {
		return "Database migration from Version " + version.getNumber() + " to " + version.getNextVersion().getNumber() + " failed! Please file a bug for this issue.";
	}
}
