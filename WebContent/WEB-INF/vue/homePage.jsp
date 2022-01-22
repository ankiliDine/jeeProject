<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:import url="/WEB-INF/inc/header.jsp"/>  
	<<table>
	  <tr>
	    <th>Nom</th>
	    <th>Theme</th>
	    <th>Detail</th>
	    <th>Visibilite</th>
	    <th>Propriétaire</th>
	    <th>Option</th>
	  </tr>
<c:choose>
		<c:when test="${!empty requestScope.albums }">
			<c:forEach items = "${albums }" var="album">
			  <tr>
			    <td>${album.albumName }</td>
			    <td>${album.theme }</td>
			    <td>${album.detail }</td>
			    <td>${album.visibility }</td>
			    <td>${album.owner.email }</td>
			    <td><a href="<c:url value="/openAlbum?idal=${album.idAlbum}"/>">Ouvrire</a></td>
			  </tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
		<tr>
			<td>Pas d'Albums Publics</td>
		</tr>	
		</c:otherwise>
	</c:choose> 
</table>
	<a href ="myAlbums">mes Albums</a>
	<a href ="charedWithMe">partagez avec moi</a>
    <a href ="createAlbum">creer un Album</a>
<c:import url="/WEB-INF/inc/footer.jsp"/>