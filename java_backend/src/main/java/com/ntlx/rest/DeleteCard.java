package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.Board;
import com.ntlx.board.Card;
import com.ntlx.board.User;
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.exception.AuthorizationException;

@WebServlet("/deleteCard")
public class DeleteCard extends KanbanServlet {
	private static final long serialVersionUID = 1L;
	
    public DeleteCard() throws NamingException, SQLException {

    }

    @Override
    protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException, AuthorizationException {
		String cardIdString = request.getParameter("cardId");
		deleteCard(cardIdString, user);
		response.getWriter().append("{\"status\":\"success\"}");
	}

	private void deleteCard(String cardIdString, User user) throws NamingException, SQLException, AuthorizationException {
		if (!cardIdString.isEmpty()) {
			int cardId = Integer.parseInt(cardIdString);
			deleteCard(cardId, user);
		}
	}

	private void deleteCard(int cardId, User user) throws NamingException, SQLException, AuthorizationException {
		DatabaseCardDAO cardDao = databaseDaoFactory.createDatabaseCardDAO();
		DatabaseBoardDAO boardDao = databaseDaoFactory.createDatabaseBoardDAO();
		Card card = cardDao.loadCard(cardId);
		Board board = boardDao.loadSingleDAO(card.getBoardId(), user);
		cardDao.delete(board, card);
	}
}
