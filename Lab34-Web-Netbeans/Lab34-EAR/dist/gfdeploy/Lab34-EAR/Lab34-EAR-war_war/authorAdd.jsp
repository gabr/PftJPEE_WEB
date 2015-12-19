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
        <title>Author update</title>
    </head>
    <body>      
        <form name="update" action="Authors">
            <input type="text" name="add" hidden="true" value="" /> </br>
            Name: <input type="text" name="name" value="" /> </br>
            LastName: <input type="text" name="lastName" value="" /> </br>
            <input type="submit" value="Save" name="SubmitButton" />
        </form>
    </body>
</html>
