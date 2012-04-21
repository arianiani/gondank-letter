<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>

<jsp:useBean id="weka" class="weka.WekaEngine" scope="session"/>
<jsp:useBean id="inputclassify" class="weka.InputNewData" scope="session"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Klasifikasi</title>
    </head>
    <body>
        <a href="Klasifikasi.jsp">Back</a><hr>
<%    
    if(inputclassify.getAttr() != null){
        for(int i=0;i<inputclassify.getAttr().length;i++)
            out.println((i+1)+":"+inputclassify.getAttr()[i]+"<br>");
   }
 %>
    <form method="post" action=""/>
        <input type="submit" value="Tambahkan Data ke Data Training"/>
    </form>
     </body>
</html>
