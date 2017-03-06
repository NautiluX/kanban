package com.ntlx.data;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

public abstract class DatabaseDAO<DAOType> {
	private HashMap<Integer, DAOType> daos = null;
	protected Database database = null;

	public DatabaseDAO(Database database) throws NamingException, SQLException {
		daos = new HashMap<Integer, DAOType>();
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
