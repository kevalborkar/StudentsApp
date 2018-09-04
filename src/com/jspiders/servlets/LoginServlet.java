package com.jspiders.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String regno = req.getParameter("regno");
		String password = req.getParameter("pass");
		
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DBUrl = "jdbc:mysql://localhost:3306/becme1820_db?user=root&password=root";
			con = DriverManager.getConnection(DBUrl);
			
			String query = " select * from students_info s1 , guardian_info g1, students_otherinfo so"
					+" where s1.regno = g1.regno and s1.regno = so.regno and s1.regno=? and "
					+ "so.password = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(regno));
			pstmt.setString(2, password);
			rs= pstmt.executeQuery();
			
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			
			if(rs.next())
			{
				String htmlResp = "<table>"
						+"<tr><th>Regno</th>"
						+"<th>First Name</th>"
						+"<th>Middle Name</th>"
						+"<th>Last Name</th>"
						+"<th>Guardian First Name</th>"
						+"<th>Guardian Middle Name</th>"
						+"<th>Guardian Last Name</th>"
						+"<th>Is Admin</th></tr>"
						+"<tr><td>"+rs.getInt("regno")+"</td>"
						+"<td>"+rs.getString("firstname")+"</td>"
						+"<td>"+rs.getString("middlename")+"</td>"
						+"<td>"+rs.getString("lastname")+"</td>"
						+"<td>"+rs.getString("gfirstname")+"</td>"
						+"<td>"+rs.getString("gmiddlename")+"</td>"
						+"<td>"+rs.getString("glastname")+"</td>"
						+"<td>"+rs.getString("isadmin")+"</td></tr>"
						+"</table>";								;
				
				out.print(htmlResp);
			}
			else {
				out.print("<h1>Invalid Credentials try again</h1>");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con!=null)
			{
				try {
					con.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(pstmt!=null)
			{
				try {
					pstmt.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(rs!=null)
			{
				try {
					rs.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}						
}

