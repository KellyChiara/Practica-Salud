<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style type="text/css">
        body {text-align: center}
    </style>
    <body>
        <h1 align="center">Login</h1>
        <form action ="LoginControlador" method="post">
            <label>Usuario: </label>
            <input type="text" name="usuario">
            <br><br>
            <label>Password: </label>
            <input type="password" name="password">
            <br><br>
            <input type="submit" value="Ingresar">
        </form>
    </body>
</html>
