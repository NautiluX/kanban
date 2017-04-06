package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.Board;
import com.ntlx.board.Boards;
import com.ntlx.board.User;
import com.ntlx.data.DatabaseBoardDAO;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.BoardNotFoundException;
import com.ntlx.exception.BoardNotReadableException;

@WebServlet("/getBoard")
public class GetBoard extends KanbanServlet {
	private static final long serialVersionUID = 1L;

	DatabaseBoardDAO dbl;
	
    public GetBoard() throws NamingException, SQLException {
    }

    @Override
    protected void doKanbanGet(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, NamingException, SQLException, AuthorizationException, BoardNotFoundException {
     	dbl = databaseDaoFactory.createDatabaseBoardDAO();

    	String boardId = request.getParameter("id");
		if (boardId == null) {
			response.getWriter().append("Board id missing");
		} else {
			int id = Integer.parseInt(boardId);
			returnBoard(response, id, user);
		}
	}

	protected void returnAllBoards(HttpServletResponse response, User user) throws SQLException, IOException, NamingException {
		dbl.loadDAOs(user);
		Boards boards = new Boards(dbl.getObjects());
		response.getOutputStream().print(boards.toString());
	}
	
	protected void returnBoard(HttpServletResponse response, int id, User user) throws SQLException, IOException, NamingException, AuthorizationException, BoardNotFoundException {
		Board board = dbl.loadSingleDAO(id, user);
		if (board != null) {
			printBoard(response, board);
		} else {
			throw new BoardNotFoundException(id);
		}
	}
	
	protected void printBoard(HttpServletResponse response, Board board) throws IOException, BoardNotReadableException {
		response.getOutputStream().print(board.toString());
	}
}
