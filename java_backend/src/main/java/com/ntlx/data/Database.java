package com.ntlx.data;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ntlx.data.migration.DatabaseSchemaVersion;

public abstract class Database {
	protected Connection conn;
	public static final String DATABASE_NAME = "kanban";
	public static final String VERSION_TABLE_NAME = "SCHEMA_VERSION";
	public static final String VERSION_COLUMN = "VERSION";
	
    public ResultSet executeQuery(String sql) throws SQLException{
    	Statement statement = conn.createStatement();
		return statement.executeQuery(sql);
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException{
    	PreparedStatement statement = conn.prepareStatement(sql);
    	return statement;
    }
    
    public void executeUpdate(String sql) throws SQLException {
    	Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
    }

	protected boolean isClosed() throws SQLException {
		return conn.isClosed();
	}

	protected long getCurrentVersionFromVersionTable() throws SQLException {
		ResultSet rs = executeQuery("SELECT " + VERSION_COLUMN + " FROM " + VERSION_TABLE_NAME + " ORDER BY " + VERSION_COLUMN + " DESC LIMIT 1");
		rs.next();
		return rs.getLong(VERSION_COLUMN);
	}
	
	public DatabaseSchemaVersion getCurrentSchemaVersion() throws SQLException {
		if (hasVersionTable())
			return DatabaseSchemaVersion.fromNumber(getCurrentVersionFromVersionTable());
		else
			return DatabaseSchemaVersion.VERSION_0;
			
	}

	public abstract boolean hasVersionTable() throws SQLException;
}
