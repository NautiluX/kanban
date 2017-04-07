package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.GlobalUser;
import com.ntlx.board.User;
import com.ntlx.data.DatabaseUserDAO;
import com.ntlx.data.ProductiveDatabaseDAOFactory;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.BoardNotFoundException;

public abstract class KanbanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected ProductiveDatabaseDAOFactory databaseDaoFactory;
	
	public KanbanServlet() {
		databaseDaoFactory = new ProductiveDatabaseDAOFactory();
	}
	
	public User getUser(HttpServletRequest request) throws SQLException, NamingException {
		DatabaseUserDAO dao = databaseDaoFactory.createDatabaseUserDAO();
		if (request.getUserPrincipal() == null) {
			return new GlobalUser();
		} else {
			String username = request.getUserPrincipal().getName();
			User user = dao.loadUser(username);
			return user;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
		try {
			User user = getUser(request);
			doKanbanPost(request, response, user);
		} catch (SQLException e) {
 			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().append("SQL Error: " + e.getMessage());
			e.printStackTrace(response.getWriter());
		} catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
 			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (BoardNotFoundException e) {
 			response.getWriter().append("Error loading Board: " + e.getMessage());
 			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (AuthorizationException e) {
 			response.getWriter().append("Authorization Error: " + e.getMessage());
 			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
		try {
			User user = getUser(request);
			doKanbanGet(request, response, user);
		} catch (SQLException e) {
 			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().append("SQL Error: " + e.getMessage());
			e.printStackTrace(response.getWriter());
		} catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
 			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (BoardNotFoundException e) {
 			response.getWriter().append("Error loading Board: " + e.getMessage());
 			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (AuthorizationException e) {
 			response.getWriter().append("Authorization Error: " + e.getMessage());
 			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 		}
	}
	
	protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException, AuthorizationException, BoardNotFoundException{};
	protected void doKanbanGet(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException, AuthorizationException, BoardNotFoundException{};

}
