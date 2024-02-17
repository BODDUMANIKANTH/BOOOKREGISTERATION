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
@WebServlet("/register")
public class registerservlet extends HttpServlet {
	 private static final String query = "INSERT INTO BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        String bookName = req.getParameter("BookName");
        String bookEdition = req.getParameter("BookEdition");

        // Validate BookPrice input
        float bookPrice;
        try {
            bookPrice = Float.parseFloat(req.getParameter("BookPrice"));
        } catch (NumberFormatException e) {
            pw.println("<h2>Invalid Book Price</h2>");
            return;
        }
        

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

            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record Is Registered Successfully</h2>");
            } else {
                pw.println("<h2>Record not Registered Successfully</h2>");
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
