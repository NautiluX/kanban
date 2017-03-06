package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.Board;
import com.ntlx.board.Boards;
import com.ntlx.board.Card;
import com.ntlx.board.Lane;
import com.ntlx.board.User;
import com.ntlx.data.Database;
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.data.DatabaseDAOFactory;
import com.ntlx.data.DatabaseLaneDAO;
import com.ntlx.data.DatabaseUserDAO;

@WebServlet("/moveCard")
public class MoveCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public MoveCard() throws NamingException, SQLException {

    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
    	try {
    		String cardIdString = request.getParameter("cardId");
    		String newLaneIdString = request.getParameter("newLaneId");
    		moveCard(cardIdString, newLaneIdString);
    		response.getWriter().append("{\"status\":\"success\"}");
    	} catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
			e.printStackTrace(response.getWriter());			
		} catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}
	}

	private void moveCard(String cardIdString, String newLaneIdString) throws NamingException, SQLException {
		if (!cardIdString.isEmpty() && !newLaneIdString.isEmpty()) {
			int cardId = Integer.parseInt(cardIdString);
			int newLaneId = Integer.parseInt(newLaneIdString);
			moveCard(cardId, newLaneId);
		}
	}

	private void moveCard(int cardId, int newLaneId) throws NamingException, SQLException {
		DatabaseCardDAO cardDao = DatabaseDAOFactory.createDatabaseCardDAO();
		Card card = cardDao.loadCard(cardId);
		
		DatabaseLaneDAO laneDao = DatabaseDAOFactory.createDatabaseLaneDAO();
		Lane lane = laneDao.loadLane(newLaneId);
		
		card.moveToLane(lane);
		cardDao.update(card);
	}
}
