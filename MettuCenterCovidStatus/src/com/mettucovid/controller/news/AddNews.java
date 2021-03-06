package com.mettucovid.controller.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mettucovid.dao.NewsDao;

import com.mettucovid.dto.News;


/**
 * Servlet implementation class AddNews
 */
@WebServlet("/AddNews")
public class AddNews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNews() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		HttpSession session = request.getSession();
		String role= (String) session.getAttribute("role");
		try {
		if(role.equals("Administrator"))
		{
			request.setAttribute("fileName", "include/sidebarmenu.jsp");
		}
		else if(role.equals("PRO"))
		{
			request.setAttribute("fileName", "include/ProSideMenu.jsp");
		}
		
			request.getRequestDispatcher("AddNews.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		try {
			News news = new News();

			news.setUrl(request.getParameter("url"));
			news.setDescription(request.getParameter("description"));
			String role= (String) session.getAttribute("role");

			int result = NewsDao.AddNews(news);
			System.out.println(result);
			if (result > 0) {

				if(role.equals("Administrator"))
				{
					request.setAttribute("fileName", "include/sidebarmenu.jsp");
				}
				else if(role.equals("PRO"))
				{
					request.setAttribute("fileName", "include/ProSideMenu.jsp");
				}
				String SuccessText = "News Registered";
				session.setAttribute("SuccessText", SuccessText);
				
				request.getRequestDispatcher("ShowNews").forward(request, response);

			} else {
				session.setAttribute("FailureText", "Registration Failed");
				request.getRequestDispatcher("AddNews").forward(request, response);
			}
		}catch (Exception  e) {
			System.out.println(e);
			session.setAttribute("FailureText", "Registration Failed ");
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
		}

	}

}
