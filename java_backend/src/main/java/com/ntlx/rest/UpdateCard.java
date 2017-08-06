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
import com.ntlx.board.Lane;
import com.ntlx.board.User;
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.data.DatabaseLaneDAO;
import com.ntlx.data.DatabaseUserDAO;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.CardNotFoundException;
import com.ntlx.exception.KanbanException;


@WebServlet("/updateCard")
public class UpdateCard extends KanbanServlet {
	private static final long serialVersionUID = 1L;
	
    public UpdateCard() throws NamingException, SQLException {

    }
    
    @Override
    protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException, KanbanException {
		String boardId = request.getParameter("boardId");
		String content = request.getParameter("content");
		String cardId = request.getParameter("cardId");
		updateCard(boardId, cardId, content, user);
		writeResponse(response, "{\"status\":\"success\"}");
	}

	private void updateCard(String boardIdStr, String cardIdStr, String content, User user) throws NamingException, SQLException, KanbanException {
		if (!boardIdStr.isEmpty() && !cardIdStr.isEmpty() && !content.isEmpty()) {
			int boardId = Integer.parseInt(boardIdStr);
			int cardId = Integer.parseInt(cardIdStr);
			updateCard(boardId, cardId, content, user);
		}
		
	}

	private void updateCard(int boardId, int cardId, String content, User user) throws NamingException, SQLException, KanbanException {
		DatabaseBoardDAO boardDao = databaseDaoFactory.createDatabaseBoardDAO();
		DatabaseCardDAO cardDao = databaseDaoFactory.createDatabaseCardDAO();
		
		Board board = boardDao.loadSingleDAO(boardId, user);
		Card card = board.getCard(cardId);
		card.setContent(content);
		cardDao.update(board, card);
	}
}
