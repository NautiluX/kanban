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
import com.ntlx.data.DatabaseBoardDAO;

/**
 * Servlet implementation class Setup
 */
@WebServlet("/getBoard")
public class GetBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */

	DatabaseBoardDAO dbl;
	
    public GetBoard() throws NamingException, SQLException {
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
    	try {
        	dbl = new DatabaseBoardDAO();

	    	String boardId = request.getParameter("id");
			if (boardId == null) {
				returnAllBoards(response);
			} else {
				int id = Integer.parseInt(boardId);
				returnBoard(response, id);
			}
    	} catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
		} catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}
	}

	private void returnAllBoards(HttpServletResponse response) throws SQLException, IOException, NamingException {
		dbl.loadDAOs();
		Boards boards = new Boards(dbl.getObjects());
		response.getOutputStream().print(boards.toString());
	}
	
	private void returnBoard(HttpServletResponse response, int id) throws SQLException, IOException, NamingException {
		Board board = dbl.loadSingleDAO(id);
		if (board != null) {
			response.getOutputStream().print(board.toString());
		} else {
			response.getOutputStream().print("Board not found: " + id);
		}
	}
}
