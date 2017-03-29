package com.ntlx.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.board.Board;
import com.ntlx.exception.AuthorizationException;
import com.ntlx.exception.BoardNotWorldReadable;

@WebServlet("/getBoardWorldReadable")
public class GetBoardWorldReadable extends GetBoard {

	private static final long serialVersionUID = 1L;

	public GetBoardWorldReadable() throws NamingException, SQLException {
		super();
	}
	
	protected void returnBoard(HttpServletResponse response, int id) throws SQLException, IOException, NamingException, AuthorizationException {
		Board board = dbl.loadSingleDAO(id);
		if (board != null) {
			printBoard(response, board);
		} else {
			response.getOutputStream().print("Board not found: " + id);
		}
	}

	private void printBoard(HttpServletResponse response, Board board) throws IOException, BoardNotWorldReadable {
		if (board.isWorldReadable()) {
			response.getOutputStream().print(board.toString());
		} else {
			throw new BoardNotWorldReadable();
		}
	}
}
