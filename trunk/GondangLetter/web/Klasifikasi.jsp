<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>

<jsp:useBean id="weka" class="weka.WekaEngine" scope="session"/>
<jsp:useBean id="inputclassify" class="weka.InputNewData" scope="session"/>
<jsp:setProperty name="inputclassify" property="*" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Klasifikasi</title>
    </head>
    <body>
        <a href="UploadModel.jsp">Back</a><hr>
    <h1>Choose Data Test</h1>
    <form action="UploadProcessDataTest.jsp" method="post" enctype="multipart/form-data">
        Input Data Test : &nbsp;&nbsp; <input type="file" name="file_upload"><br>
        <input type="submit" value="Upload File">
    </form>        
    <hr/>
        <h1>Input Data Manual</h1>
    <form method="post" action="InputProcessForm.jsp">
        Input data baru :<br> 
        x_box : <input type="text" name="attr"/><br/>
        y_box : <input type="text" name="attr"/><br/>
        width : <input type="text" name="attr"/><br/>
        high : <input type="text" name="attr"/><br/>
        onpix : <input type="text" name="attr"/><br/>
        x_bar : <input type="text" name="attr"/><br/>
        y_bar : <input type="text" name="attr"/><br/>
        x2bar : <input type="text" name="attr"/><br/>
        y2bar : <input type="text" name="attr"/><br/>
        xybar : <input type="text" name="attr"/><br/>
        x2ybr : <input type="text" name="attr"/><br/>
        xy2br : <input type="text" name="attr"/><br/>
        x_ege : <input type="text" name="attr"/><br/>
        xegvy : <input type="text" name="attr"/><br/>
        y_ege : <input type="text" name="attr"/><br/>
        yegvx : <input type="text" name="attr"/><br/>
        <input type="submit" value="submit"/>
    </form>
     </body>
</html>
