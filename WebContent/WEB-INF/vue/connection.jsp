<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/inc/header.jsp"/>
    <article class="article-inc" id="article-inc">
      <div class="insc-con-container " id="insc-con-container"> 
        	<div class="form-insc-con-container sign-in-insc-con-container">
        		<form action="connection" method="POST">
	            	<h1>Connection</h1>
	            	<input type="email" placeholder="Email" name="email" value="${requestScope.login}"/>
	            	<span class="field-erreur">${errors.email }</span>
	            	<input type="password" placeholder="Password" name="password" />
	            	<span class="field-erreur">${errors.password }</span>
	            	<input type="submit" value="Connexion" name="connexion" />
          		</form>
          	</div>
          	<div class="overlay-insc-con-container">
          		<div class="overlay">
          			<div class="overlay-panel overlay-right">
		              <h1>Hello, M. MME!</h1>
		              <p class="message">Vous n'avez pas de compte? Inscrivez vous et rejoignez nous</p>
		              <a href="<c:url value="/inscription"/>" class="gost" >Inscription</a>
            		</div>
            	</div>
        	</div>
   
      </div>
    </article>
<c:import url="/WEB-INF/inc/footer.jsp"/>