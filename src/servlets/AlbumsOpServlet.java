package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Album;
import beans.User;
import dao.AlbumDao;
import services.AlbumsManager;

/**
 * Servlet implementation class AlbumManagementServlet
 */
@WebServlet({"/openAlbum","/createAlbum","/updateAlbum","/chareAlbum","/findUserForChar","/deleteAlbum"})

@MultipartConfig( 	fileSizeThreshold = 1048576, 
					maxFileSize = 10485760,
					maxRequestSize = 52428800 )
public class AlbumsOpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_ALBUM = "/WEB-INF/vue/album.jsp";
	private static final String VUE_CREATE_ALBUM = "/WEB-INF/vue/createAlbum.jsp";
	private static final String VUE_MODIFY_ALBUM = "/WEB-INF/vue/modifyAlbum.jsp";
	private static final String VUE_CHARE_ALBUM = "/WEB-INF/vue/chareAlbum.jsp";
	private static final String VUE_MY_ALBUMS = "/WEB-INF/vue/myAlbums.jsp";
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String idAlbum = null;
		ArrayList<Album> userAlbums = new ArrayList<Album> ();
		String option = request.getServletPath().substring(1);
		
		switch(option) {
			case "deleteAlbum":
				AlbumsManager management = new AlbumsManager(request);
				management.albumManagement(user, option);
				request.setAttribute("statusMessage", management.getStatusMessage());
				userAlbums = AlbumDao.findUserAlbums (user.getEmail());
				request.setAttribute("userAlbums", userAlbums);
				request.setAttribute("title","mes albums");
				getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
			break;
			case "chareAlbum":
				idAlbum = request.getParameter("idal");
				// on prend le soin de verifier que l'album existe et appartient bien à cette utilisateur
				if(idAlbum != null && idAlbum.matches("[0-9]+")) {
				
					Album album = AlbumDao.findAlbum (Integer.parseInt(idAlbum));
					if(album == null || !album.getOwner().getEmail().equals(user.getEmail()) ) {
				
						userAlbums = AlbumDao.findUserAlbums (user.getEmail());
						request.setAttribute("userAlbums", userAlbums);
						request.setAttribute("title","mes albums");
						getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
						break;
					}
					request.setAttribute("album", album);
				} else {
			
					userAlbums = AlbumDao.findUserAlbums (user.getEmail());
					request.setAttribute("userAlbums", userAlbums);
					request.setAttribute("title","mes albums");
					getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
					break;
				}
				request.setAttribute("title","partager mon album");
				getServletContext().getRequestDispatcher(VUE_CHARE_ALBUM).forward(request, response);
				break;
			case "updateAlbum":
				idAlbum = request.getParameter("idal");
				
				if(idAlbum != null && idAlbum.matches("[0-9]+")) {
					
					Album album = AlbumDao.findAlbum (Integer.parseInt(idAlbum));
					if(album == null || !album.getOwner().getEmail().equals(user.getEmail()) ) {
						response.sendRedirect("myAlbums");
						break;
					}
					request.setAttribute("album", album);
				} else {
					userAlbums = AlbumDao.findUserAlbums (user.getEmail());
					request.setAttribute("userAlbums", userAlbums);
					request.setAttribute("title","mes albums");
					getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
					break;
				}
				request.setAttribute("title","modifier mon album");
				getServletContext().getRequestDispatcher(VUE_MODIFY_ALBUM).forward(request, response);
				break;
			case "createAlbum": 
				request.setAttribute("title","creer un album");
				getServletContext().getRequestDispatcher(VUE_CREATE_ALBUM).forward(request, response);	
				break;
			case "openAlbum":
				idAlbum = request.getParameter("idal");

				if(idAlbum != null && idAlbum.matches("[0-9]+")) {

					Album album = AlbumDao.findAlbum (Integer.parseInt(idAlbum));
					if(album == null ) {

						userAlbums = AlbumDao.findUserAlbums (user.getEmail());
						request.setAttribute("userAlbums", userAlbums);
						request.setAttribute("title","mes albums");
						getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
						break;
					}
					request.setAttribute("album", album);
					request.setAttribute("listImages", album.getImages());
				} else {
					userAlbums = AlbumDao.findUserAlbums (user.getEmail());
					request.setAttribute("userAlbums", userAlbums);
					request.setAttribute("title","mes albums");
					getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
					break;
				}
				request.setAttribute("title","voir mon album");
				getServletContext().getRequestDispatcher(VUE_ALBUM).forward(request, response);	
				break;
			default:
				response.sendRedirect("myAlbums");
				break;
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String option = request.getServletPath().substring(1);
		AlbumsManager manager = new AlbumsManager(request);
		
		if ("createAlbum".equals(option) || "updateAlbum".equals(option)) {
			
			boolean status = manager.albumManagement(user, option);
			
			request.setAttribute("album", manager.getAlbum());
	//..... debug
			System.out.println(request.getAttribute("albumName"));
			if (status) {
	//..... debug
				System.out.println("succès");
				request.setAttribute("statusMessage", manager.getStatusMessage());
				ArrayList<Album> userAlbums = AlbumDao.findUserAlbums (user.getEmail());
				request.setAttribute("userAlbums", userAlbums);
				request.setAttribute("title","mes albums");
				getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
			} else {
	//..... debug
				System.out.println("echeque");
				request.setAttribute("title", option.equals("createAlbum")? "creer un album":"modifier un album");	
				request.setAttribute("errors", manager.getErrors());
				
				if(option.equals("createAlbum")) {
					getServletContext().getRequestDispatcher(VUE_CREATE_ALBUM).forward(request, response);	
				} else {
					getServletContext().getRequestDispatcher(VUE_MODIFY_ALBUM).forward(request, response);	
				}
				
			}
		} 
		else if ("findUserForChar".equals(option) || "chareAlbum".equals(option)) {
				
				boolean status = manager.chareAlbumManager();
				request.setAttribute("statusMessage", manager.getStatusMessage());
				request.setAttribute("album", manager.getAlbum());
				
				if(status) {
					if("chareAlbum".equals(option)) {
						ArrayList<Album> userAlbums = AlbumDao.findUserAlbums (user.getEmail());
						request.setAttribute("userAlbums", userAlbums);
						request.setAttribute("title","mes albums");
						getServletContext().getRequestDispatcher(VUE_MY_ALBUMS).forward(request, response);
					} else {
						request.setAttribute("usersForChareList", manager.getUsersForChare());
						getServletContext().getRequestDispatcher(VUE_CHARE_ALBUM).forward(request, response);	
					}
				} else {
					request.setAttribute("errors", manager.getErrors());
					getServletContext().getRequestDispatcher(VUE_CHARE_ALBUM).forward(request, response);	
				}
		}
		
		
	
	}
			
}
