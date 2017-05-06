package com.ntlx.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ntlx.data.migration.DatabaseSchemaMigration;
import com.ntlx.data.migration.DatabaseSchemaVersion;
import com.ntlx.data.migration.SchemaMigrator;

import java.sql.PreparedStatement;

public class ProductiveDatabase extends Database {
	
	private static final String DATABASE_NAME = "kanban";
	
	public static ProductiveDatabase instance = null;
	
	private ProductiveDatabase() throws NamingException, SQLException{
		initializeDatabaseConnection();
	}
	
	public void initializeDatabaseConnection() throws NamingException, SQLException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/kanban");
		conn = ds.getConnection();
    }
    
    public static Database getInstance() throws NamingException, SQLException {
    	if (instance == null || instance.isClosed()) {
    		instance = new ProductiveDatabase();
    	}
    	return instance;
    }

	@Override
	public DatabaseSchemaVersion getCurrentSchemaVersion() throws SQLException {
		if (hasVersionTable())
			return DatabaseSchemaVersion.fromNumber(getCurrentVersionFromVersionTable());
		else
			return DatabaseSchemaVersion.VERSION_0;
			
	}

	private boolean hasVersionTable() throws SQLException {
		PreparedStatement versionTableStatement = prepareStatement("SELECT count(*) FROM information_schema.TABLES WHERE (TABLE_SCHEMA = ?) AND (TABLE_NAME = ?);");
		versionTableStatement.setString(1, DATABASE_NAME);
		versionTableStatement.setString(2, VERSION_TABLE_NAME);
		ResultSet rs = versionTableStatement.executeQuery();
		return rs.isAfterLast();
	}
}
