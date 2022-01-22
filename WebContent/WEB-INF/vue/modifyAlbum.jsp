<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:import url="/WEB-INF/inc/header.jsp"/>
<div class="create_modifiy">
  <form action="updateAlbum" method="Post" class="create_modify">	        
    <input type="text" name="newalbumName" placeholder="Nom de l'album" value="${album.albumName}"/>
    <span class="field-erreur">${errors.albumName }</span>
    <input type="text" name="theme" placeholder="Theme "  value="${album.theme}"/>
    <span class="field-erreur">${errors.theme }</span>
    <input class="detail" placeholder="Description"  name="detail" value="${album.detail}">
    <span class="field-erreur">${errors.detail }</span>
    <select name="visibility" >
      <option value="prive" selected>Prive</option>
      <option value="public">Public</option>
    </select>
    <input type ="hidden" name="albumName"  value="${album.albumName}"/> 
    <input type ="hidden" name="idAlbum"  value="${album.idAlbum}"/> 
    <input type="reset" value="Annuler" name="annuler" />
    <input type="submit" value="Modifier" name="modifier" />
  </form>
</div>
<c:import url="/WEB-INF/inc/footer.jsp"/>
    