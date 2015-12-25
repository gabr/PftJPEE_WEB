<%-- 
    Document   : error
    Created on : 2015-12-25, 10:35:14
    Author     : arkad_000
    Version    : 1.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <a href="./">Menu</a> <br />
        <jsp:useBean id="error" scope="request" type="javax.servlet.jsp.JspException" />
        Error message: <jsp:getProperty name="error" property="message" /></br>
    </body>
</html>
