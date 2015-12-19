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
        <jsp:useBean id="author" scope="request" type="pl.polsl.gabrys.arkadiusz.model.Author" />        
        <form name="update" action="Authors">
            <input type="text" name="update" hidden="true" value="<jsp:getProperty name="author" property="id" />" /> </br>
            Name: <input type="text" name="name" value="<jsp:getProperty name="author" property="name" />" /> </br>
            LastName: <input type="text" name="lastName" value="<jsp:getProperty name="author" property="lastName" />" /> </br>
            <input type="submit" value="Save" name="SubmitButton" />
        </form>
    </body>
</html>
