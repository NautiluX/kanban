package com.ntlx.rest;

import javax.servlet.http.HttpServlet;

import com.ntlx.data.ProductiveDatabaseDAOFactory;

public class KanbanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected ProductiveDatabaseDAOFactory databaseDaoFactory;
	
	public KanbanServlet() {
		databaseDaoFactory = new ProductiveDatabaseDAOFactory();
	}
}
