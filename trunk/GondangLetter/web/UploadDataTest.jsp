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
        <title>Input Data Test</title>
    </head>
    <body>
    <a href="UploadModel.jsp">Back</a><hr>
    <h1>Choose Data Test</h1>
    <form action="UploadProcessDataTest.jsp" method="post" enctype="multipart/form-data">
        Input Data Test : &nbsp;&nbsp; <input type="file" name="file_upload"><br>
        <input type="submit" value="Upload File">
    </form>        
    </body>
</html>

