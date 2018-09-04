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

public class SearchStudentServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
	String regno = req.getParameter("regno");
	String password = req.getParameter("pass");
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String DBUrl = "jdbc:mysql://localhost:3306/becme1820_db?user=root&password=root";
			con = DriverManager.getConnection(DBUrl);
			
			/*String query = " select * from students_info s1 , guardian_info g1"
					+" where s1.regno = g1.regno and s1.regno=?";*/
			
			String query="select * from students_info si,students_addressinfo sa ,"
					+ "guardian_info gi,students_otherinfo so "
					+ " where si.regno=gi.regno and si.regno=so.regno and si.regno=sa.regno and si.regno=? and so.password=? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(regno));
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			PrintWriter out =resp.getWriter();
			String htmlResp;
			if(rs.next()) {
				
				
				htmlResp= "<head>"
						+"<body>"
						+"<table border='1'>"
						+"<tr><th>Reg No.</th>"
						+"<th>First Name.</th>"
						+"<th>Middle Name</th>"
						+"<th>Last Name</th>"
						+"<th>Guardian First Name.</th>"
						+"<th>Guardian Middle Name</th>"
						+"<th>Guardian Last Name</th></tr>"
						+"<tr><td>"+ rs.getInt("regno") +"</td>"
						+"<td>"+rs.getString("firstname")+"</td>"
						+"<td>"+rs.getString("middlename")+"</td>"
						+"<td>"+rs.getString("lastname")+"</td>"
						+"<td>"+rs.getString("gfirstname")+"</td>"
						+"<td>"+rs.getString("gmiddlename")+"</td>"
						+"<td>"+rs.getString("glastname")+"</td>"
						+"</table></body></html>";
						
				out.print(htmlResp);
				out.print("<table border='1'><tr><td>Address Type</td>"
						+ "<td>Address1</td><td>Address2</td>"
						+ "<td>Landmark</td><td>City</td><td>Pincode</td></tr>");
				do
				{
			out.print("<tr><td>"+rs.getString("addType")+"</td>"
							+ "<td>"+rs.getString("address1")+"</td>"
					+ "<td>"+rs.getString("address2")+"</td>"
					+ "<td>"+rs.getString("landmark")+"</td>"
					+ "<td>"+rs.getString("city")+"</td>"
					+ "<td>"+rs.getString("pincode")+"</td></tr>");
					
				}while(rs.next());
				out.print("</table>");
			}else {
				 htmlResp="<head>"
						+"<body>"
						+"<h3>Record Not Found</h3>"
						+"</body>"
						+ "</head>";
				 out.print(htmlResp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
