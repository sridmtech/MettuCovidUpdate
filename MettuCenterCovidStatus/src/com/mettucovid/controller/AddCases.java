package com.mettucovid.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mettucovid.dao.CasesDao;
import com.mettucovid.dao.UserDao;
import com.mettucovid.dto.CaseNumbers;
import com.mettucovid.dto.User;

/**
 * Servlet implementation class AddCases
 */
@WebServlet("/AddCases")
public class AddCases extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCases() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CaseNumbers ethCases = new CaseNumbers();
		HttpSession session = request.getSession();
		String role= (String) session.getAttribute("role");
		int id=1;
		try {
			ethCases = CasesDao.findOne(id);
			request.setAttribute("ethCases", ethCases);
			if(role.equals("Administrator"))
			{
				request.setAttribute("fileName", "include/sidebarmenu.jsp");
			}
			else if(role.equals("PRO"))
			{
				request.setAttribute("fileName", "include/ProSideMenu.jsp");
			}

			request.getRequestDispatcher("AddCases.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CaseNumbers ethCases = new CaseNumbers();

		ethCases.setTotal(Integer.parseInt(request.getParameter("total")));
		ethCases.setActive(Integer.parseInt(request.getParameter("active")));
		ethCases.setCured(Integer.parseInt(request.getParameter("cured")));
		ethCases.setCritical(Integer.parseInt(request.getParameter("critical")));
		ethCases.setDeath(Integer.parseInt(request.getParameter("deaths")));

		int id=1;

		try {
			int result = CasesDao.updateCases(ethCases, id);
			System.out.println(result);

			HttpSession session = request.getSession();
			String role= (String) session.getAttribute("role");
			if(role.equals("Administrator"))
			{
				request.setAttribute("fileName", "include/sidebarmenu.jsp");
				response.sendRedirect("AdminHome");
			}
			else if(role.equals("PRO"))
			{
				request.setAttribute("fileName", "include/ProSideMenu.jsp");
				response.sendRedirect("StaffHome");
			}

			//request.getRequestDispatcher("admin.jsp").forward(request, response);



		} catch (SQLException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
