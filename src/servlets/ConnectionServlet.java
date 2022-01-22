package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import services.AuthentificationsManager;;

/**
 * Servlet implementation class ConnectionServlet
 */
@WebServlet({"","/connection"})
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_CONNECTION = "/WEB-INF/vue/connection.jsp";
       
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("title", "connection");
		getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AuthentificationsManager authentification = new AuthentificationsManager(request);
		boolean status = authentification.authentifier();
		
		
		if(status) {
			
			HttpSession session = request.getSession();
			session.setAttribute("user", authentification.getUser());
			User user = (User) session.getAttribute("user");
			System.out.println(user.getEmail());
			session.setAttribute("state","connected");
			response.sendRedirect("homePage");
		
		} else {
			
			request.setAttribute("errors", authentification.getErrors());
			request.setAttribute("login", authentification.getEmail());
			request.setAttribute("title", "Connexion");
			getServletContext().getRequestDispatcher(VUE_CONNECTION).forward(request, response);
		
		}
	}

}
