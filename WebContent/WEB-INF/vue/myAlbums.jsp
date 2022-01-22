<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:import url="/WEB-INF/inc/header.jsp"/>  
	<c:choose>
		<c:when test="${!empty requestScope.albums }">
			<c:forEach items = "${albums}" var="album">
				<div> 
					<span><c:out value="${album.idAlbum }"/></span>
					<span><c:out value="${album.albumName }"/></span>
					<span><c:out value="${album.theme }"/></span>
					<span><c:out value="${album.detail }"/></span>
					<span><c:out value="${album.visibility }"/></span>
					<span><c:out value="${album.owner.email }"/></span>
					<span><a href="<c:url value="/openAlbum?idal=${album.idAlbum}"/>">Ouvrire</a></span>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div>Vous n'avez pas d'albums</div>
		</c:otherwise>
	</c:choose> 
    <a href ="createAlbum">creer un Album</a>
<c:import url="/WEB-INF/inc/footer.jsp"/>