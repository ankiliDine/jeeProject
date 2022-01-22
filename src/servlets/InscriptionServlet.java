package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import services.InscriptionsManager;;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet({"/validerInscription","/inscription"})
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_INSCRIPTION = "/WEB-INF/vue/inscription.jsp";
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("title", "Inscription");
		getServletContext().getRequestDispatcher(VUE_INSCRIPTION).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InscriptionsManager inscription = new InscriptionsManager(request);
		
		if (request.getServletPath().equals("/validerInscription")) {
			
			boolean status = inscription.ValiderInscription();
			
			if(!status) {

				request.setAttribute("errors", inscription.getErrors());
				request.setAttribute("user", inscription.getUser());
				
			} else {
				
				HttpSession session = request.getSession();
				session.setAttribute("confirmationCode", inscription.getconfirmationCode());
				session.setAttribute("user", inscription.getUser());
				request.setAttribute("validation",true);
				
			}
			
			getServletContext().getRequestDispatcher(VUE_INSCRIPTION).forward(request,response);
		
		} else if (request.getServletPath().equals("/inscription")) {
			
			HttpSession session = request.getSession();
			
			boolean status = inscription.inscription((User)session.getAttribute("user"), (String) session.getAttribute("confirmationCode"));
			
			if(!status) {

				request.setAttribute("errors", inscription.getErrors());
				request.setAttribute("validation",true);
				getServletContext().getRequestDispatcher(VUE_INSCRIPTION).forward(request,response);
				
			} else {
				
				session.setAttribute("state","connected");
				response.sendRedirect("homePage");
			}
		}
		
	}

}
