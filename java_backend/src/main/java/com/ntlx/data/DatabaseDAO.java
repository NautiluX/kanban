package com.ntlx.data;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

public abstract class DatabaseDAO<DAOType> {
	private HashMap<Integer, DAOType> daos = null;
	protected Database database = null;

	public abstract DAOType loadSingleDAO(int id) throws SQLException;

	public abstract void loadDAOs() throws SQLException;

	public DatabaseDAO() throws NamingException, SQLException {
		daos = new HashMap<Integer, DAOType>();
		database = Database.getInstance();
	}
	
	public DatabaseDAO(Database database) {
		this.database = database;
	}

	protected void addDAO(int id, DAOType dao) {
		daos.put(id, dao);
	}

	protected DAOType getDAO(int id) {
		return daos.get(id);
	}

	public ArrayList<DAOType> getObjects() {
		ArrayList<DAOType> daoArray = new ArrayList<DAOType>(daos.values());
		return daoArray;
	}
}
