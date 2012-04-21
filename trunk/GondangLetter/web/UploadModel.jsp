<%-- 
    Document   : Learning
    Created on : Apr 18, 2012, 3:41:48 PM
    Author     : masphei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Learning</title>
    </head>
    <body>
    <a href="index.jsp">Back</a><hr>
    <h1>Choose Model</h1>
    <form action="UploadProcessModel.jsp" method="post" enctype="multipart/form-data">
        Input Model : &nbsp;&nbsp; <input type="file" name="file_upload"><br>
        <input type="submit" value="Coba Upload">
    </form>        
    </body>
</html>

