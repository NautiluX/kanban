package com.ntlx.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
	protected Connection conn;
	
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
}
