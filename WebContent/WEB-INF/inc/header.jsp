<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${ requestScope.title }</title>
    <link rel="stylesheet" href="cssFilesFolder/inscription-connection.css" />
    <link
      rel="stylesheet"
      href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
      integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
      crossorigin="anonymous"
    />
    <script src="jsFilesFolder/inscription-connection.js" async></script>
  </head>
  <body class="body-inc">
    <header class="header-inc">
      <h1 class="logo">WEB PHOTO</h1>
      <!-- <div class="seach-bar">
        <input type="text" name="contenu" id="contenu" />
        <select name="seach-option" id="seach-option">
          <option value="image" selected>Image</option>
          <option value="album" selected>Album</option>
          <option value="theme" selected>Theme</option>
        </select>
      </div> -->
      <nav class="menu-inc">
        <a href="#"> <i class="fas fa-home"></i> Acceuil</a>
      </nav>
    </header>