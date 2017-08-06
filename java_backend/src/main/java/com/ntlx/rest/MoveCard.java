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
import com.ntlx.exception.CardNotFoundException;
import com.ntlx.exception.KanbanException;

@WebServlet("/moveCard")
public class MoveCard extends KanbanServlet {
	private static final long serialVersionUID = 1L;
	
    public MoveCard() throws NamingException, SQLException {

    }
    
    @Override
    protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException, KanbanException {
		String cardIdString = request.getParameter("cardId");
		String newLaneIdString = request.getParameter("newLaneId");
		String boardId = request.getParameter("boardId");
		moveCard(boardId, cardIdString, newLaneIdString, user);
		writeResponse(response, "{\"status\":\"success\"}");
	}

	private void moveCard(String boardIdString, String cardIdString, String newLaneIdString, User user) throws NamingException, SQLException, KanbanException {
		if (!cardIdString.isEmpty() && !newLaneIdString.isEmpty()) {
			int boardId = Integer.parseInt(boardIdString);
			int cardId = Integer.parseInt(cardIdString);
			int newLaneId = Integer.parseInt(newLaneIdString);
			moveCard(boardId, cardId, newLaneId, user);
		}
	}

	private void moveCard(int boardId, int cardId, int newLaneId, User user) throws NamingException, SQLException, KanbanException {
		DatabaseCardDAO cardDao = databaseDaoFactory.createDatabaseCardDAO();
		DatabaseBoardDAO boardDao = databaseDaoFactory.createDatabaseBoardDAO();
		Board board = boardDao.loadSingleDAO(boardId, user);
		Lane lane = board.getLane(newLaneId);
		Card card = board.getCard(cardId);
		card.moveToLane(lane);
		cardDao.update(board, card);
	}
}
