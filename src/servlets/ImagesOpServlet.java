package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Album;
import beans.Image;
import beans.User;
import dao.AlbumDao;
import dao.ImageDao;
import services.AlbumsManager;
import services.ImagesManager;

/**
 * Servlet implementation class ImagesOpServlet
 */
@WebServlet(urlPatterns={"/createImage","/updateImage","/deleteImage"},
			initParams = {
							@WebInitParam(name="path",value="C:\\javaee\\eclipseWorkSpace\\wap\\WebContent\\UsersDirectory\\")
						})

@MultipartConfig( 	fileSizeThreshold = 1048576, 
					maxFileSize = 10485760,
					maxRequestSize = 52428800 )
public class ImagesOpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CHAMP_DESCRIPTION = "description";
	public static final String CHAMP_FICHIER     = "image";
	
	private static final String VUE_ALBUM = "/WEB-INF/vue/album.jsp";
	private static final String VUE_CREATE_IMAGE = "/WEB-INF/vue/createImage.jsp";
	private static final String VUE_MODIFY_IMAGE = "/WEB-INF/vue/modifyImage.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idAlbum,idImage;
		Image image = null;
		String option = request.getServletPath().substring(1);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		switch(option) {
			case "createImage":
				idAlbum = request.getParameter("idal");
				
				if(idAlbum != null && idAlbum.matches("[0-9]+")) {
				
					Album album = AlbumDao.findAlbum (Integer.parseInt(idAlbum));
					if(album == null ) {

						response.sendRedirect("myAlbums");
						break;
					}
					session = request.getSession();
					session.setAttribute("album", album);
				} else {
					
					response.sendRedirect("myAlbums");
					break;
				}
				request.setAttribute("title", "Ajouter une image");
				getServletContext().getRequestDispatcher(VUE_CREATE_IMAGE).forward(request, response);	
				break;
			case "updateImage":
				idImage = request.getParameter("idim");
	
				if(idImage != null && idImage.matches("[0-9]+")) {
					
					image = ImageDao.findImage(Integer.parseInt(idImage));

					if(image == null || !image.getAlbum().getOwner().getEmail().equals(user.getEmail()) ) {
					
						response.sendRedirect("myAlbums");
						break;
					}
					request.setAttribute("album", image.getAlbum());
					request.setAttribute("image", image);
					System.out.println(image.getImagePath());
					getServletContext().getRequestDispatcher(VUE_MODIFY_IMAGE).forward(request, response);	
					break;
				} else {
	
					response.sendRedirect("myAlbums");
					break;
				}
			case "deleteImage":
				ImagesManager manager = new ImagesManager(request);
				request.setAttribute("statusMessage", manager.getStatusMessage());
				request.setAttribute("album", manager.getImageAlbum());
				request.setAttribute("title","mon album");
				getServletContext().getRequestDispatcher(VUE_ALBUM).forward(request, response);
			break;
			
		}	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String option = request.getServletPath().substring(1);
		ImagesManager manager = new ImagesManager(request);
		boolean status = manager.ImageManagement(option);
		
		request.setAttribute("statusMessage", manager.getStatusMessage());
		
	    if(status) {
	    	request.setAttribute("album", manager.getImageAlbum());
	    	getServletContext().getRequestDispatcher(VUE_ALBUM).forward(request, response);	
	    } else {
	    	if(manager.getImageAlbum() == null) {
	    		response.sendRedirect("myAlbums");
	    		//System.out.println("L'abum choisis n'existe pas");
	    	}else {
	    		request.setAttribute("album", manager.getImageAlbum());
	    		request.setAttribute("image", manager.getImage());
		    	request.setAttribute("errors", manager.getErrors());
		    	if("createImage".equals(option)) {
		    		getServletContext().getRequestDispatcher(VUE_CREATE_IMAGE).forward(request, response);		
		    	}
		    	else if ("updateImage".equals(option)) {
		    		getServletContext().getRequestDispatcher(VUE_ALBUM).forward(request, response);		
		    		
		    	}
		    	
	    	}
	    }
		
	
	    
	}

}
