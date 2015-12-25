<%-- 
    Document   : authorUpdate
    Created on : 2015-12-19, 19:30:18
    Author     : arkad_000
    Version    : 1.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book update</title>
    </head>
    <body>
        <a href="./">Menu</a> <br />
        <%@taglib prefix="c" uri="/view/print/dateprinter" %>
        <jsp:useBean id="book" scope="request" type="pl.polsl.gabrys.arkadiusz.model.Book" />
        <jsp:useBean id="author" scope="request" type="pl.polsl.gabrys.arkadiusz.model.Author" />
        <form name="update" action="Books">
            <input type="text" name="update" hidden="true" value="<jsp:getProperty name="book" property="id" />" /> </br>
            Title: <input type="text" name="title" value="<jsp:getProperty name="book" property="title" />" /> </br>
            Pages: <input type="text" name="pages" value="<jsp:getProperty name="book" property="pages" />" /> </br>
            Release date (yyyy.dd.mm): <input type="text" name="releaseDate" value="<c:DatePrinter date="${book.releaseDate}" />" /> </br>
            Author id: <input type="text" name="authorId" value="<jsp:getProperty name="author" property="id" />" /> </br>
            <input type="submit" value="Save" name="SubmitButton" />
        </form>
    </body>
</html>
