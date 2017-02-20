package com.ntlx.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

import com.ntlx.dao.Board;
import com.ntlx.dao.DAO;


public abstract class DatabaseLoader<DAOType extends DAO> {
	private HashMap<Integer, DAOType> daos = null;
	protected Database database = null;

	public abstract DAOType loadSingleDAO(int id) throws SQLException;

	public abstract void loadDAOs() throws SQLException;

	public DatabaseLoader() throws NamingException, SQLException {
		daos = new HashMap<Integer, DAOType>();
		database = new Database();
	}
	
	protected void addDAO(int id, DAOType dao) {
		daos.put(id, dao);
	}

	protected DAOType getDAO(int id) {
		return daos.get(id);
	}

	public ArrayList<DAOType> getBoards() {
		ArrayList<DAOType> daoArray = new ArrayList<DAOType>(daos.values());
		return daoArray;
	}
}
