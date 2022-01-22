<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/inc/header.jsp"/>
    <article class="article-inc" id="article-inc">
      <div class="insc-con-container right-panel-active" id="insc-con-container">
        <c:choose>
        	<c:when test="${!empty requestScope.validation }">
        		<div class="form-insc-con-container sign-in-insc-con-container">
          		<form action="inscription" method="POST">
            		<h1 >Finalisez votre inscription </h1>
		            <input type="text" placeholder="Entrer votre code de confirmation" name="confirmationCode" />
		            <span class="field-erreur">${errors.confirmationCode }</span>
		            <input type="submit" value="Inscription" name="connexion"  />
		         </form>
        		</div>
        	</c:when>
        	<c:otherwise>
        		<div class="form-insc-con-container sign-up-insc-con-container">
		          <form class="inscription" action="validerInscription" method="POST">
		            <h2>Creez un compte</h2>
		            <input 
		              class="inp_insc" 
		              name="name" 
		              type="text" 
		              placeholder="Name" 
		              value="${user.name}"
		             />
					<span class="field-erreur">${errors.name }</span>
		            <input
		              class="inp_insc"
		              name="firstName"
		              type="text"
		              placeholder="FirstName"
		              value="${user.firstName}"
		            />
					<span class="field-erreur">${errors.firstName }</span>
		            <input
		              class="inp_insc"
		              name="email"
		              type="email"
		              placeholder="Email"
		              value="${user.email}"
		            />
					<span class="field-erreur">${errors.email }</span>
		            <input
		              class="inp_insc"
		              name="password"
		              type="password"
		              placeholder="Password"
		              
		            />
		            <span class="field-erreur">${errors.password }</span>
					<span class="field-erreur"></span>
		            <input
		              class="inp_insc"
		              name="passwordBis"
		              type="password"
		              placeholder="Confirm your password"
		              
		            />
		            <span class="field-erreur">${errors.passwordBis }</span>
		            <input type="submit" value="Inscription" name="inscription" />
		
		          </form>
		        </div>
        	</c:otherwise>
        </c:choose>
        
		<!--
        <div class="form-insc-con-container sign-in-insc-con-container">
        
          		<form action="inscription" method="POST">
            		<h1 >Finalisez votre inscription </h1>
		            <input type="text" placeholder="Entrer votre code de confirmation" name="confirmationCode" />
		            <input type="submit" value="Inscription" name="connexion"  />
		         </form>
        </div>
        -->
        <div class="overlay-insc-con-container">
          <div class="overlay">
            <div class="overlay-panel overlay-left">
              <h1>Welcome !</h1>
              <p class="message">
              	${ empty requestScope.validation ? "Un Email de confirmation vous sera envoyé une fois l'inscription validée":"Entrez le code de confirmation qu'on vous a envoyé par mail pour finaliser votre inscription" }
              </p>
              <a href="<c:url value="/connection"/>" class="gost" >Connection</a>
            </div>
            <!--  
            <div class="overlay-panel overlay-right">
              <h1>Hello, M. MME!</h1>
              <p class="message"></p>
              <button class="ghost" id="signUp">Inscription</button>
            </div>
            -->
          </div>
        </div>
         -->
      </div>
    </article>
<c:import url="/WEB-INF/inc/footer.jsp"/>