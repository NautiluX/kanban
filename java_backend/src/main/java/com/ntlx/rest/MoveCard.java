package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.Card;
import com.ntlx.board.Lane;
import com.ntlx.board.User;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.data.DatabaseLaneDAO;

@WebServlet("/moveCard")
public class MoveCard extends KanbanServlet {
	private static final long serialVersionUID = 1L;
	
    public MoveCard() throws NamingException, SQLException {

    }
    
    @Override
    protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException {
		String cardIdString = request.getParameter("cardId");
		String newLaneIdString = request.getParameter("newLaneId");
		moveCard(cardIdString, newLaneIdString);
		writeResponse(response, "{\"status\":\"success\"}");
	}

	private void moveCard(String cardIdString, String newLaneIdString) throws NamingException, SQLException {
		if (!cardIdString.isEmpty() && !newLaneIdString.isEmpty()) {
			int cardId = Integer.parseInt(cardIdString);
			int newLaneId = Integer.parseInt(newLaneIdString);
			moveCard(cardId, newLaneId);
		}
	}

	private void moveCard(int cardId, int newLaneId) throws NamingException, SQLException {
		DatabaseCardDAO cardDao = databaseDaoFactory.createDatabaseCardDAO();
		Card card = cardDao.loadCard(cardId);
		
		DatabaseLaneDAO laneDao = databaseDaoFactory.createDatabaseLaneDAO();
		Lane lane = laneDao.loadLane(newLaneId);
		
		card.moveToLane(lane);
		cardDao.update(card);
	}
}
