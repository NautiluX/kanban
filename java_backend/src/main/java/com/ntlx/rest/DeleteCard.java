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

@WebServlet("/deleteCard")
public class DeleteCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DeleteCard() throws NamingException, SQLException {

    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
    	try {
    		String cardIdString = request.getParameter("cardId");
    		deleteCard(cardIdString);
    		response.getWriter().append("{\"status\":\"success\"}");
    	} catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
			e.printStackTrace(response.getWriter());			
		} catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}
	}

	private void deleteCard(String cardIdString) throws NamingException, SQLException {
		if (!cardIdString.isEmpty()) {
			int cardId = Integer.parseInt(cardIdString);
			deleteCard(cardId);
		}
	}

	private void deleteCard(int cardId) throws NamingException, SQLException {
		DatabaseCardDAO cardDao = new DatabaseCardDAO();
		Card card = cardDao.loadCard(cardId);
		
		cardDao.delete(card);
	}
}
