package com.mani;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/editscreen")
public class EditScreen extends HttpServlet {
	private static final String query = "SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA where id=?";
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
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<form action='editurl?id="+id+"' method='post'>");
        	pw.println("<table align ='center' >");
        	pw.println("<tr>");
        	pw.println("<td> BookName</td>");
        	pw.println("<td><input type='text' name='bookName' value='"+rs.getString(1)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td> BookEdition</td>");
        	pw.println("<td><input type='text' name='bookEdition' value='"+rs.getString(2)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td> BookPrice</td>");
        	pw.println("<td><input type='text' name='bookPrice' value='"+rs.getFloat(3)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<td><input type='submit' value='Edit'></td>");
        	pw.println("<td><input type ='reset' value='cancel'></td>");
        	pw.println("</table>");
        	
        	pw.println("</form>");
            
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>Database Error: " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
        pw.println("<a href='NewFile.html'>NewFile</a>");
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req,resp);
	}
}
        
	


