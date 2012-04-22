<%@page import="java.util.ArrayList"%>
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
        <a href="Klasifikasi.jsp">Back</a><hr>
    <%    
        if(inputclassify.getAttr() != null){
            for(int i=0;i<inputclassify.getAttr().length;i++)
                System.out.println((i+1)+":"+inputclassify.getAttr()[i]+"<br>");
       //}\
        
            if(weka.inputForm){
                int[] getInput = new int[inputclassify.getAttr().length];
                for(int i=0;i<inputclassify.getAttr().length;i++){
                    if(inputclassify.getAttr()[i].equals(""))
                        getInput[i] = 0;
                   else{
                        if(Integer.parseInt(inputclassify.getAttr()[i])<0){
                            getInput[i] = 0;                            
                        }else if (Integer.parseInt(inputclassify.getAttr()[i])>15){
                            getInput[i] = 0;                        
                        }else
                            getInput[i] = Integer.parseInt(inputclassify.getAttr()[i]);
                   }
                    System.out.println(getInput[i]);
                }
                weka.inputDataForm = getInput;
                out.println(weka.processingInputForm(false));
            }
       }else if(!weka.inputForm){
                out.println(weka.classify(false));
            }
     %>
    <form method="post" action="LearnNewData.jsp"/>
        <input type="submit" value="Tambahkan Data ke Data Training"/>
    </form>
     </body>
</html>
