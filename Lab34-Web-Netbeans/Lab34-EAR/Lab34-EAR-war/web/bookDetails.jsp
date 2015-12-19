<%-- 
    Document   : bookDetails
    Created on : 2015-12-19, 18:48:05
    Author     : arkad_000
    Version    : 1.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book details</title>
    </head>
    <body>
        <jsp:useBean id="book" scope="request" type="pl.polsl.gabrys.arkadiusz.model.Book" />
        Id: <jsp:getProperty name="book" property="id" /></br>
        Title: <jsp:getProperty name="book" property="title" /></br>
        Pages: <jsp:getProperty name="book" property="pages" /></br>
        Relese date: <jsp:getProperty name="book" property="releaseDate" /></br>

        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:if test="${Book.Author != null}">
            Author: <jsp:getProperty name="book" property="author" /></br>
        </c:if>
    </body>
</html>
