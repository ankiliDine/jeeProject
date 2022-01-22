package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Album;
import beans.User;
import dao.AlbumDao;

/**
 * Servlet implementation class homePageServlet
 */
@WebServlet({"/homePage","/myAlbums","/charedWithMe"})
public class homePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_HOME_PAGE = "/WEB-INF/vue/homePage.jsp";
	private static final String VUE_MY_ALBUMS = "/WEB-INF/vue/myAlbums.jsp";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String option = request.getServletPath().substring(1);
		ArrayList<Album> albums = new ArrayList<Album> (); 
		HttpSession session = null;
		User user = null;
		
		switch(option) {
			case "homePage":
				albums = AlbumDao.findPublicAlbums();
				request.setAttribute("albums", albums);
				request.setAttribute("title", "Acceuil");
				getServletContext().getRequestDispatcher(VUE_HOME_PAGE).forward(request,response);
				break;
			case "myAlbums":
				session = request.getSession();
				user = (User) session.getAttribute("user");
				albums = AlbumDao.findUserAlbums (user.getEmail());
				request.setAttribute("albums", albums);
				request.setAttribute("title","mes albums");
				getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
			    break;
			case "charedWithMe":
				session = request.getSession();
				user = (User) session.getAttribute("user");
				System.out.println(user.getId()+" idU ");
				albums = AlbumDao.findCharedAlbums (user.getId());
				System.out.println(albums.size()+" tailles ");
				request.setAttribute("albums", albums);
				request.setAttribute("title","partagez avec Moi");
				getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
				break;
			default:
				response.sendRedirect("homePage");
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
