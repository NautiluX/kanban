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
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.data.DatabaseCardDAO;
import com.ntlx.data.DatabaseLaneDAO;
import com.ntlx.data.DatabaseUserDAO;

/**
 * Servlet implementation class Setup
 */
@WebServlet("/newCard")
public class NewCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */

	
    public NewCard() throws NamingException, SQLException {

    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String userName = request.getUserPrincipal().getName();
    	try {
    		String boardIdString = request.getParameter("boardId");
    		String laneIdString = request.getParameter("laneId");
    		String content = request.getParameter("content");
    		createCard(boardIdString, laneIdString, userName, content);
    		response.getWriter().append("{\"status\":\"success\"}");
    	} catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
			e.printStackTrace(response.getWriter());			
		} catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}
	}

	private void createCard(String boardIdString, String laneIdString, String userName, String content) throws NamingException, SQLException {
		// TODO Auto-generated method stub
		if (!boardIdString.isEmpty() && !laneIdString.isEmpty() && !content.isEmpty()) {
			int boardId = Integer.parseInt(boardIdString);
			int laneId = Integer.parseInt(laneIdString);
			createCard(boardId, laneId, content, userName);		
		}
	}

	private void createCard(int boardId, int laneId, String content, String userName) throws NamingException, SQLException {
		DatabaseBoardDAO boardDao = new DatabaseBoardDAO();

		Board board = boardDao.loadSingleDAO(boardId);
		System.err.println(boardId + " " + laneId);
		Lane lane = boardDao.loadLane(board, laneId);

		DatabaseUserDAO userDao = new DatabaseUserDAO();
		User owner = userDao.loadUser(userName);
		
		DatabaseCardDAO cardDao = new DatabaseCardDAO(lane);
		cardDao.insertCard(lane, owner, content);
	}
}
