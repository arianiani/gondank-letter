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
    <h1>Input data (.arff dan .csv)</h1>
    <form action="UploadProcess.jsp" method="post" enctype="multipart/form-data">
        Input File : &nbsp;&nbsp; <input type="file" name="file_upload"><br>
        <input type="submit" value="Coba Upload">
    </form>        
    <h1>Convert Data CSV to ARFF</h1>
    <form action="UploadConvert.jsp" method="post" enctype="multipart/form-data">
        Input File : &nbsp;&nbsp; <input type="file" name="file_upload"><br>
        <input type="submit" value="Coba Upload">
    </form>        
    </body>
</html>

