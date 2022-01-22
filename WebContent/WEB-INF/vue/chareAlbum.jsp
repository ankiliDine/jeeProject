<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:import url="/WEB-INF/inc/header.jsp"/>
<c:if test="${!empty requestScope.statusMessage }">
	<div>
		<span class ="field-erreur"><c:out value="${statusMessage }"/> </span>
		<span class ="hideen">x</span>
	</div>
</c:if>
<c:choose>
	<c:when test="${!empty requestScope.usersForChareList}">
		<form method = "POST" action ="chareAlbum">
				<c:forEach items = "${usersForChareList }" var="user">
					<div class="userForChar">
		      			<input type="checkbox" name="idUserForChar" value ="<c:out value="${user.id }"/>" checked>
		      			<c:out value="${user.profil }"/>
		      			<c:out value="${user.email }"/>
		      			<input type="text" name="idAlbum" value="<c:out value="${album.idAlbum}"/>">
		      		</div>
		      	</c:forEach>
		      		<div class="chareInput">
		      			<input type ="submit" value="Partager"/>
		      		</div>
					
		</form>
	</c:when>
	<c:otherwise>
		<form method = "POST" action ="findUserForChar">
			<input type="text" name="emailsUsersForChare" placeholder="Saisir les Emails des utilisateurs à qui vous sohaitez partager cet album separés par des ;">
			<span class="field-erreur"><c:out value="${errors.emailsUsersForChare }"/></span>
			<input type="text" name="idAlbum" value="<c:out value="${album.idAlbum}"/>">
			<input type="submit" value="cherercher">
			<input type=reset value="Annuler">
		</form>
	</c:otherwise>
</c:choose> 
<c:import url="/WEB-INF/inc/footer.jsp"/>