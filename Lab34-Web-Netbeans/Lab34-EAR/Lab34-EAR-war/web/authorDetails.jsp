<%-- 
    Document   : authorDetails
    Created on : 2015-12-19, 18:48:05
    Author     : arkad_000
    Version    : 1.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author details</title>
    </head>
    <body>
        <a href="./">Menu</a> <br />
        <jsp:useBean id="author" scope="request" type="pl.polsl.gabrys.arkadiusz.model.Author" />
        Id: <jsp:getProperty name="author" property="id" /></br>
        Name: <jsp:getProperty name="author" property="name" /></br>
        Last name: <jsp:getProperty name="author" property="lastName" /></br>

        </br>
        Books:
        <table border="1">
            <tr>
                <td>
                    Title
                </td>
                <td>
                    Pages
                </td>
                <td>
                    Release date
                </td>
            </tr>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>
                    ${book.title}
                </td>
                <td>
                    ${book.pages}
                </td>
                <td>
                    ${book.releaseDate}
                </td>
            </tr>
        </c:forEach>
        </table>
    </body>
</html>
