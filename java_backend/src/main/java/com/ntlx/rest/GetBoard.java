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
import com.ntlx.data.DatabaseBoardLoader;

/**
 * Servlet implementation class Setup
 */
@WebServlet("/getBoard")
public class GetBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */

	DatabaseBoardLoader dbl;
	
    public GetBoard() throws NamingException, SQLException {
    	dbl = new DatabaseBoardLoader();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
	    	String boardId = request.getParameter("id");
			if (boardId == null) {
				returnAllBoards(response);
			} else {
				int id = Integer.parseInt(boardId);
				returnBoard(response, id);
			}
    	} catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
		}
	}

	private void returnAllBoards(HttpServletResponse response) throws SQLException, IOException {
		dbl.loadDAOs();
		Boards boards = new Boards(dbl.getBoards());
		response.getOutputStream().print(boards.toString());
	}
	
	private void returnBoard(HttpServletResponse response, int id) throws SQLException, IOException {
		Board board = dbl.loadSingleDAO(id);
		if (board != null) {
			response.getOutputStream().print(board.toString());
		} else {
			response.getOutputStream().print("Board not found: " + id);
		}
	}
}
