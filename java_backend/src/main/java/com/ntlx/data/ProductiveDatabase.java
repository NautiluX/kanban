package com.ntlx.data;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductiveDatabase extends Database{
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
}
