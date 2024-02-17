package com.mani;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/deleteurl")
public class deleteservlet extends HttpServlet {
	private static final String query = "delete from BOOKDATA where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        //get id record
        int id=Integer.parseInt(req.getParameter("id"));
        
        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h2>Database Driver not found</h2>");
            return;
        }

        // Connection and PreparedStatement in a try-with-resources
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "maniyadav45");
             PreparedStatement ps = con.prepareStatement(query)) {
        	ps.setInt(1, id);
            int count = ps.executeUpdate();
        	if (count == 1) {
                pw.println("<h2>Record is Deleted Successfully</h2>");
            } else {
                pw.println("<h2>Record is not deleted Successfully</h2>");
            }
        	
            
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>Database Error: " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
        pw.println("<a href='NewFile.html'>NewFile</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'>Book List</a>");
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req,resp);
	}

}
