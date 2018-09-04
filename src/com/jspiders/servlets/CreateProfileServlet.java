package com.jspiders.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateProfileServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String regno = req.getParameter("regno");
		String password = req.getParameter("pass");
		String isAdmin = req.getParameter("isadmin");
		String[] courses = req.getParameterValues("course_detais");
		String sfname = req.getParameter("sfname");
		String smname = req.getParameter("smname");
		String slname = req.getParameter("slname");
		String gfname = req.getParameter("gfname");
		String gmname = req.getParameter("gmname");
		String glname = req.getParameter("glname");
		
		String presentAddr1 = req.getParameter("presentaddr1");
		String presentAddr2 = req.getParameter("presentaddr2");
		String presentland = req.getParameter("presentland");
		String presentcity = req.getParameter("presentcity");
		String presentpin = req.getParameter("presentpin");
		
		String permanentAddr1 = req.getParameter("permanentaddr1");
		String permanentAddr2 = req.getParameter("permanentaddr2");
		String permanentland = req.getParameter("permanentland");
		String permanentcity = req.getParameter("permanentcity");
		String permanentpin = req.getParameter("permanentpin");
		/*
		PrintWriter out = resp.getWriter();
		
		out.print("RegNO: "+ regno + "<br />");
		out.print("Password: "+ password + "<br />");
		out.print("Is Admin?: "+ isAdmin + "<br />");
		
		out.print("Student FirstName:   "+ sfname + "<br />");
		out.print("Student MiddleName:  "+ smname + "<br />");
		out.print("Student LastName:    "+ slname + "<br />");
		out.print("Guardian FirstName:  "+ gfname + "<br />");
		out.print("Guardian MiddleName: "+ gmname + "<br />");
		out.print("Guardian LastName:   "+ glname + "<br />");
		
		out.print("Coarses Registered :");
		for (String coarsesreg :courses) {
			out.print(coarsesreg+" ,");
		}
		
		out.print("Present Address Info:<br/>");
		out.print("Present Address1 :   "+ presentAddr1 + "<br />");
		out.print("Present Address2:  "+ presentAddr2 + "<br />");
		out.print("Landmark:    "+ presentland + "<br />");
		out.print("City:  "+ presentcity + "<br />");
		out.print("Pincode: "+ presentpin + "<br />");
		
		out.print("Permanent Address Info:<br/>");
		out.print("Permanent Address1 :   "+ permanentAddr1 + "<br />");
		out.print("Permanent Address2:  "+ permanentAddr2 + "<br />");
		out.print("Landmark:    "+ permanentland + "<br />");
		out.print("City:  "+ permanentcity + "<br />");
		out.print("Pincode: "+ permanentpin + "<br />");*/
		
		Connection con =null;
		PreparedStatement pstmt1 =null;
		PreparedStatement pstmt2 =null;
		PreparedStatement pstmt3 =null;
		PreparedStatement pstmt4 =null;
		PreparedStatement pstmt5 =null;
		
		PrintWriter out = resp.getWriter();
		
		try {
			@SuppressWarnings("deprecation")
			Driver driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			
			String DBUrl = "jdbc:mysql://localhost:3306/becme1820_db?user=root&password=root";
			con = DriverManager.getConnection(DBUrl);
			
			String query1 = "Insert into students_info values (?,?,?,?)";
			pstmt1 =con.prepareStatement(query1);
			pstmt1.setInt(1, Integer.parseInt(regno));
			pstmt1.setString(2, sfname );
			pstmt1.setString(3, smname);
			pstmt1.setString(4, slname);
			pstmt1.executeUpdate();
			
			String query2 = "Insert into guardian_info values (?,?,?,?)";
			pstmt2 =con.prepareStatement(query2);
			pstmt2.setInt(1, Integer.parseInt(regno));
			pstmt2.setString(2, gfname );
			pstmt2.setString(3, gmname);
			pstmt2.setString(4, glname);
			pstmt2.executeUpdate();
			
			String query3 = "Insert into students_otherinfo values(?,?,?)";
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setInt(1, Integer.parseInt(regno));
			pstmt3.setString(2, isAdmin );
			pstmt3.setString(3, password);
			pstmt3.executeUpdate();
			
			String query4 = "Insert into students_addressinfo values(?,?,?,?,?,?,?)";
			pstmt4 = con.prepareStatement(query4);
			
			pstmt4.setInt(1, Integer.parseInt(regno));
			pstmt4.setString(2, "T");
			pstmt4.setString(3, presentAddr1);
			pstmt4.setString(4, presentAddr2);
			pstmt4.setString(5, presentland);
			pstmt4.setString(6, presentcity);
			pstmt4.setString(7, presentpin);
			pstmt4.addBatch();
			
			pstmt4.setInt(1, Integer.parseInt(regno));
			pstmt4.setString(2, "P");
			pstmt4.setString(3, permanentAddr1);
			pstmt4.setString(4, permanentAddr2);
			pstmt4.setString(5, permanentland);
			pstmt4.setString(6, permanentcity);
			pstmt4.setString(7, permanentpin);
			
			pstmt4.addBatch();
			
			pstmt4.executeBatch();
			
			String query5 ="Insert into students_courseinfo values(?,?)";
			pstmt5 = con.prepareStatement(query5);
			for(String course_id : courses) {
				pstmt5.setInt(1, Integer.parseInt(regno));
				pstmt5.setInt(2, Integer.parseInt(course_id));
				pstmt5.addBatch();
			}
			pstmt5.executeBatch();
			
			
			out.print("Profile Created..");
		} catch (Exception e) {
			
			e.printStackTrace();
			out.print("Profile Not Created..");
			
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(pstmt1 !=null && pstmt2 !=null && pstmt3 !=null && pstmt4 !=null && pstmt5 !=null  ) {
				try {
					pstmt1.close();
					pstmt2.close();
					pstmt3.close();
					pstmt4.close();
					pstmt5.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}//End of Outer-Try
		
	}//End of post
}//End of class
