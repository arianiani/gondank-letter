<%-- 
    Document   : InputProcessForm
    Created on : Apr 22, 2012, 12:28:33 PM
    Author     : masphei
--%>

<jsp:useBean id="weka" class="weka.WekaEngine" scope="session"/>
<jsp:useBean id="inputclassify" class="weka.InputNewData" scope="session"/>
<jsp:setProperty name="inputclassify" property="*" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
<%
        if(inputclassify.getAttr() != null){
            weka.inputForm = true;
            String redirectURL = "KlasifikasiResult.jsp";
            response.sendRedirect(redirectURL);
            //for(int i=0;i<inputclassify.getAttr().length;i++)
              //  out.println((i+1)+":"+inputclassify.getAttr()[i]+"<br>");
            
       }
%>    </body>
</html>
