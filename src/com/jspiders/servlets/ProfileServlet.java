package com.jspiders.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		String fname = req.getParameter("fname");
		String mname = req.getParameter("mname");
		String lname = req.getParameter("lname");
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2");
		String[] iknow = req.getParameterValues("iknow");
		String sex = req.getParameter("sex");
		String[] ihave = req.getParameterValues("ihave");
		
		String about = req.getParameter("about");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.print("ID: "+id+"<br />");
		out.print("First Name : "+fname+"<br />");
		out.print("Middle Name : "+mname+"<br />");
		out.print("Last Name : "+lname+"<br />");
		out.print("Address 1 : "+addr1+"<br />");
		out.print("Address 2 : "+addr2+"<br />");
		
		out.print("I Know :");
		for (String knows :iknow) {
			out.print(knows+" ,");
		}
		
		out.print("<br />"+"Gender :" + sex +"<br />");
		
		out.print("I Have :");
		for (String have :ihave) {
			out.print(have+" ,");
		}
		
		out.print("<br />"+"About Me :" + about);
	}
}
