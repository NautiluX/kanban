package com.ntlx.rest;

import java.sql.SQLException;

import javax.naming.NamingException;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntlx.data.ProductiveDatabaseSetup;
import com.ntlx.data.ProductiveDatabase;

@WebServlet("/setup")
public class Setup extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Setup() {
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductiveDatabaseSetup setup = new ProductiveDatabaseSetup(ProductiveDatabase.getInstance());
			setup.executeSetup();
			response.getWriter().println("setup done.");
		}
		catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
		}
	}	
}
