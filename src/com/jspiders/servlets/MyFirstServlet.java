package com.jspiders.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MyFirstServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		Date date = new Date();
		String fname = req.getParameter("firstname");
		String lname = req.getParameter("lastname");
		String currDate = date.toString();
		String htmlResp = "<html>"
					+"<body>"
					+"<h1>"
					+currDate
					+"</h1>"
					+"First Name: "+fname
					+"<br>"
					+"Last Name: "+lname
					+"</body>"
					+"</html>";
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.print(htmlResp);
	}

}
