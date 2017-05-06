package com.ntlx.data.migration;

public enum DatabaseSchemaVersion {
	VERSION_UNKNOWN(-1),
	VERSION_1(1),
	VERSION_0(0, VERSION_1);
	
	private long versionNumber;
	private DatabaseSchemaVersion nextVersion;
	DatabaseSchemaVersion(long versionNumber) {
		this.versionNumber = versionNumber;
		setNextVersion(this);
	}
	DatabaseSchemaVersion(long versionNumber, DatabaseSchemaVersion nextVersion) {
		this.versionNumber = versionNumber;
		this.setNextVersion(nextVersion);
	}
	
	public long getNumber() {
		return this.versionNumber;
	}
	
	public static DatabaseSchemaVersion fromNumber(long versionNumber) {
		if (versionNumber == VERSION_0.getNumber()) {
			return VERSION_0;
		} else if (versionNumber == VERSION_1.getNumber()) {
			return VERSION_1;
		}
		return VERSION_UNKNOWN;
	}
	public DatabaseSchemaVersion getNextVersion() {
		return nextVersion;
	}
	private void setNextVersion(DatabaseSchemaVersion nextVersion) {
		this.nextVersion = nextVersion;
	}
}
