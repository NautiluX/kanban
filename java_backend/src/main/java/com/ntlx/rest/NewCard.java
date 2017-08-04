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
import com.ntlx.data.DatabaseUserDAO;


@WebServlet("/newCard")
public class NewCard extends KanbanServlet {
	private static final long serialVersionUID = 1L;
	
    public NewCard() throws NamingException, SQLException {

    }
    
    @Override
    protected void doKanbanPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException {
        String userName = request.getUserPrincipal().getName();
		String boardIdString = request.getParameter("boardId");
		String laneIdString = request.getParameter("laneId");
		String content = request.getParameter("content");
		createCard(boardIdString, laneIdString, userName, content);
		writeResponse(response, "{\"status\":\"success\"}");
	}

	private void createCard(String boardIdString, String laneIdString, String userName, String content) throws NamingException, SQLException {
		if (!boardIdString.isEmpty() && !laneIdString.isEmpty() && !content.isEmpty()) {
			int boardId = Integer.parseInt(boardIdString);
			int laneId = Integer.parseInt(laneIdString);
			createCard(boardId, laneId, content, userName);		
		}
	}

	private void createCard(int boardId, int laneId, String content, String userName) throws NamingException, SQLException {
		DatabaseUserDAO userDao = databaseDaoFactory.createDatabaseUserDAO();
		DatabaseLaneDAO laneDao = databaseDaoFactory.createDatabaseLaneDAO();
		DatabaseCardDAO cardDao = databaseDaoFactory.createDatabaseCardDAO();
		
		User owner = userDao.loadUser(userName);
		Lane lane = laneDao.loadLane(laneId);
		Card card = new Card(Card.NEW_CARD_ID, owner, content, lane, boardId);
		
		cardDao.create(card);
	}
}
