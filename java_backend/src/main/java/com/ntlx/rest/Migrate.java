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
import com.ntlx.data.migration.DatabaseSchemaMigration;
import com.ntlx.data.migration.SchemaMigrator;
import com.ntlx.exception.MigrationFailedException;
import com.ntlx.data.ProductiveDatabase;

@WebServlet("/migrate")
public class Migrate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Migrate() {
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SchemaMigrator migrator = new SchemaMigrator(ProductiveDatabase.getInstance());
			migrator.migrate();
			response.getWriter().println("migration done.");
		}
		catch (NamingException e) {
			response.getWriter().append("Naming Error: " + e.getMessage());
		}catch (SQLException e) {
			response.getWriter().append("SQL Error: " + e.getMessage());
		} catch (MigrationFailedException e) {
			response.getWriter().append("Migration Error: " + e.getMessage());
		}
	}	
}
