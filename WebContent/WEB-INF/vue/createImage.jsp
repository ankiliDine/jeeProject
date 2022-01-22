<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:import url="/WEB-INF/inc/header.jsp"/>
<div>Nom : ${album.albumName }</div>
<div class="create_modifiy">
<!-- enctype="multipart/form-data" --> 
  <form action="createImage" method="Post" enctype="multipart/form-data"  class="create_modify">	        
    <input type="text" name="title" placeholder="Titre de l'image" value="${image.title}"/>
    <span class="field-erreur">${errors.title }</span>
    <input type="text" name="description" placeholder="Description "  value="${image.description}"/>
    <span class="field-erreur">${errors.description }</span>
    <input type="date" placeholder="Date de creation"  name="creationDate" value="${image.creationDate}">
    <span class="field-erreur">${errors.creationDate }</span>
    <input type="number" step="0.01" min="0" placeholder="Hauteur de l'image 0.01"  name="height" value="${image.height}">
    <span class="field-erreur">${errors.height }</span>
    <input type="number" step="0.01" min="0" placeholder="Largeur de l'image 0.01"  name="width" value="${image.width}">
    <span class="field-erreur">${errors.width }</span>
    <input type="text" placeholder="Mots clés de l'image"  name="keyWords" value="${image.keyWords}">
    <span class="field-erreur">${errors.keyWords }</span>
    <select name="accessibility" >
      <option value="prive" selected>Prive</option>
      <option value="public">Public</option>
    </select>
    <input type="hidden" name="idAlbum" value="${album.idAlbum}">
    <input type="file" name="image" accept="image/*"
                   onchange="readFilesAndDisplayPreview(this.files);" /> 
    <span class="field-erreur">${errors.image }</span>
    <input type="reset" value="Annuler" name="annuler" />
    <input type="submit" value="Creer" name="creer" />
  </form>
</div>
<c:import url="/WEB-INF/inc/footer.jsp"/>
    