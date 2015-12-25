<%-- 
    Document   : bookAdd
    Created on : 2015-12-19, 19:30:18
    Author     : arkad_000
    Version    : 1.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book add</title>
    </head>
    <body>
        <form name="update" action="Books">
            <input type="text" name="add" hidden="true" value="" /> </br>
            Title: <input type="text" name="title" value="" /> </br>
            Pages: <input type="text" name="pages" value="" /> </br>
            Release date (yyyy.dd.mm): <input type="text" name="releaseDate" value="" /> </br>
            Author id: <input type="text" name="authorId" value="" /> </br>
            <input type="submit" value="Save" name="SubmitButton" />
        </form>
    </body>
</html>
