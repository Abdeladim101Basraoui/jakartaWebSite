<%@ page import="app.quiz.quiz.DTO.Departement" %>
<%@ page import="app.quiz.quiz.DAO.DepartementDAO" %><%--
  Created by IntelliJ IDEA.
  User: Abdeladim Basraoui
  Date: 4/1/2023
  Time: 2:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Liste des départements</title>
</head>
<body>
<h1>Liste des départements</h1>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Nom</th>
  </tr>
  </thead>
<%--  <tbody>--%>
<%--  <%--%>
<%--    for (app.quiz.quiz.DTO.Departement departement : departements) { %>--%>
<%--  <tr>--%>
<%--    <td><%= departement.getId() %></td>--%>
<%--    <td><%= departement.getNom() %></td>--%>
<%--  </tr>--%>
<%--  </tbody>--%>
</table>
<br>
<a href="departements.jsp?action=ajouter">Ajouter un département</a>
</body>
</html>
