<%
    if (session.getAttribute("logueado") != "OK"){
        response.sendRedirect("login.jsp");
    }
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.emergentes.modelo.Post"%>
<%@page import="java.util.List"%>
<%
    List<Post> lista = (List<Post>) request.getAttribute("lista");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <label style="color:blue;"> ${sessionScope.usuario}________ </label>
        <a href="LoginControlador?action=logout" aling = "center">SALIR [X]</a>
        <h1 align = "center">Blog de salud</h1>
        <a href="PostControlador?op=nuevo">Nueva entrada</a>
        <c:forEach var="item" items="${lista}">
            <p>${item.fecha}</p>
            <b><p>${item.titulo}</p></b>
            <p>${item.contenido}</p>
            <p>${item.autor}
                <a href="PostControlador?op=editar&id=${item.id}"> Editar</a>
                <a href="PostControlador?op=eliminar&id=${item.id}" onclick="return(confirm('esta seguro?'))"> Eliminar</a>
            </p>
            <hr>
        </c:forEach>
    </body>
</html>
