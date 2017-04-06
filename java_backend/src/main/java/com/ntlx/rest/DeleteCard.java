package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.Card;
import com.ntlx.board.User;
import com.ntlx.data.DatabaseCardDAO;

@WebServlet("/deleteCard")
public class DeleteCard extends KanbanServlet {
	private static final long serialVersionUID = 1L;
	
    public DeleteCard() throws NamingException, SQLException {

    }

    @Override
    protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException {
		String cardIdString = request.getParameter("cardId");
		deleteCard(cardIdString);
		response.getWriter().append("{\"status\":\"success\"}");
	}

	private void deleteCard(String cardIdString) throws NamingException, SQLException {
		if (!cardIdString.isEmpty()) {
			int cardId = Integer.parseInt(cardIdString);
			deleteCard(cardId);
		}
	}

	private void deleteCard(int cardId) throws NamingException, SQLException {
		DatabaseCardDAO cardDao = databaseDaoFactory.createDatabaseCardDAO();
		Card card = cardDao.loadCard(cardId);
		
		cardDao.delete(card);
	}
}
