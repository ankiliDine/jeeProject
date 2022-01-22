<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:import url="/WEB-INF/inc/header.jsp"/>
<div class="create_modifiy">
  <form action="createAlbum" method="Post" enctype="multipart/form-data" class="create_modify">	        
    <input type="text" name="albumName" placeholder="Nom de l'album" value="${album.albumName}"/>
    <span class="field-erreur">${errors.albumName }</span>
    <input type="text" name="theme" placeholder="Theme "  value="${album.theme}"/>
    <span class="field-erreur">${errors.theme }</span>
    <input class="detail" placeholder="Description"  name="detail" value="${album.detail}">
    <span class="field-erreur">${errors.detail }</span>
    <select name="visibility" >
      <option value="prive" selected>Prive</option>
      <option value="public">Public</option>
    </select>
    <input type="file" name="profil" accept="image/*"
                   onchange="readFilesAndDisplayPreview(this.files);" /> 
    <span class="field-erreur">${errors.profil }</span>
    <input type="reset" value="Annuler" name="annuler" />
    <input type="submit" value="Creer" name="creer" />
  </form>
</div>
<c:import url="/WEB-INF/inc/footer.jsp"/>
    