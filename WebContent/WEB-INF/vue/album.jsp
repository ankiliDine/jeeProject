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
  </tr>
  <tr>
    <td>${album.albumName }</td>
    <td>${album.theme }</td>
    <td>${album.detail }</td>
    <td>${album.visibility }</td>
    <td> ${album.owner.email }</td>
  </tr>
</table>
<table>
  <tr>
    <th>Titre</th>
    <th>Description</th>
    <th colspan="3">Options</th>
  </tr>
  <c:choose>
	<c:when test="${!empty requestScope.listImages }">
		<c:forEach items = "${listImages}" var="image">
			<tr> 
					<td><c:out value="${image.title }"/></td>
					<td><c:out value="${image.description }"/></td>
					<td><button>Ouvrire</button></td>
					<td><a href="<c:url value="/updateImage?idim=${image.idImage}"/>">Modifier</a></td>
					<td><a href="<c:url value="/deleteImage?idImage=${image.idImage}&idAlbum=${album.idAlbum}"/>"></a></td>
			</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr> 
				<td colspan="5">Cet album est vide</td>
			</tr>
		</c:otherwise>
	</c:choose> 
</table>

		
	<!-- sidebare -->
	<div><a href="<c:url value="/createImage?idal=${album.idAlbum}"/>">ajouter une image</a></div>
	<div><a href="<c:url value="/chareAlbum?idal=${album.idAlbum}"/>">partager mon Album</a></div>
	<div><a href="<c:url value="/updateAlbum?idal=${album.idAlbum}"/>">modifier mon Album</a></div>
	<div><a href="<c:url value="/deleteAlbum?idal=${album.idAlbum}"/>">supprimer mon album</a></div>
	
<c:import url="/WEB-INF/inc/footer.jsp"/>
