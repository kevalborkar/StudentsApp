package com.jspiders.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

@SuppressWarnings("serial")
public class ViewAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Connection con  = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		  
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		try {
			//Load the drivers
			Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			//Get connection to database
			String DBUrl="jdbc:mysql://localhost:3306/BECME1820_db?user=root&password=root";
			con = DriverManager.getConnection(DBUrl);
			//issue sql query
			String query = "select * from students_info";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			//process the database results
			
			String htmlresp = "<html><body><table border=1>"+
					"<tr><th>RegNo</th><th>First Name</th>"+
					"<th>Middle Name</th><th>Last Name </th>";
			
			
			while(rs.next()) {
				int regNo = rs.getInt("regno");
				String Fn = rs.getString("firstname");
				String Mn = rs.getString("middlename");
				String Ln = rs.getString("lastname");
				
				/*out.println("Reg No: "+regNo);
				out.println("First Name :"+Fn);
				out.println("Middle Name :"+Mn);
				out.println("Last Name :"+Ln);
				out.println("------------------------");*/	
				
				htmlresp =htmlresp+"<tr><td>"+regNo+"</td>"+
							"<td>"+Fn+"</td>"+
							"<td>"+Mn+"</td>"+
							"<td>"+Ln+"</td></tr>";
				
			}
			 	htmlresp =htmlresp+"</table></body></html>";
				out.print(htmlresp);
		  }
		  catch (SQLException e) {
				e.printStackTrace();
		  }
		  finally {
			  try {
				  if(con!=null){
				    con.close();
				  }	
				  if(stmt!=null) {
					stmt.close();
				  }
				  if(rs!=null) {
					  rs.close();
				  }
		} catch (SQLException e) {
				
				e.printStackTrace();
		}
	  }
		
	}
}
