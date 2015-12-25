<%--
    Document   : authors
    Created on : 2015-12-19, 17:34:40
    Author     : arkad_000
    Version    : 1.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Books</title>
    </head>
    <body>
        <a href="./">Menu</a> <br />
        <a href="./Books?add">Add new</a> <br />

        <form name="findForm" action="Books">
            Find Book with title:
            <input type="text" name="find" value="" hidden="true" />
            <input type="text" name="name" value=""/>
            <input type="submit" value="Find" name="SubmitButton" />
        </form>

        <table border="1">
            <tr>
                <td>
                    Id
                </td>
                <td>
                    Title
                </td>
                <td>
                    Pages
                </td>
                <td>
                    Release date
                </td>
                <td>

                </td>
                <td>

                </td>
                <td>

                </td>
            </tr>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>
                    ${book.id}
                </td>
                <td>
                    ${book.title}
                </td>
                <td>
                    ${book.pages}
                </td>
                <td>
                    ${book.releaseDate}
                </td>
                <td>
                    <a href="./Books?details=${book.id}">Details</a>
                </td>
                <td>
                    <a href="./Books?update=${book.id}">Update</a>
                </td>
                <td>
                    <a href="./Books?delete=${book.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </table>
    </body>
</html>
