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
        <title>Authors</title>
    </head>
    <body>
        <a href="./Authors?add">Add new</a> <br />
        
        <form name="findForm" action="Authors">
            Find Author with name:
            <input type="text" name="name" value="" size="80" />
            <input type="submit" value="Find" name="SubmitButton" />
        </form>
        
        <table border="1">
            <tr>
                <td>
                    Name
                </td>
                <td>
                    Surname
                </td>
                <td>
                    
                </td>
                <td>
                    
                </td>
                <td>
                    
                </td>
            </tr>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:forEach var="author" items="${authors}">
            <tr>
                <td>
                    ${author.name}
                </td>
                <td>
                    ${author.lastName}
                </td>
                <td>
                    <a href="./Authors?details=${author.id}">Details</a>
                </td>
                <td>
                    <a href="./Authors?update=${author.id}">Update</a>
                </td>
                <td>
                    <a href="./Authors?delete=${author.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </table>
    </body>
</html>
