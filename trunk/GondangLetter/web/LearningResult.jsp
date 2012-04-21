<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>

<jsp:useBean id="weka" class="weka.WekaEngine" scope="session"/>
<jsp:useBean id="inputpre" class="weka.InputPre" scope="session"/>
<jsp:useBean id="inputoption" class="weka.InputPre" scope="session"/>
<jsp:setProperty name="input" property="*" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Learning</title>
        <script type="text/javascript" src="jquery-1.5.2.min.js"></script>
    </head>
    <body>
        <a href="Upload.jsp">Back</a><hr>
    <form method="post" action="Learning.jsp">
        Preproccess : 
        <select name="preprocessInput">
            <option value="0">none</option>
            <option <%if(inputpre.getpreprocessInput()!=null) if(inputpre.getpreprocessInput().equals("1")) out.println("selected='true'");%> value="1">unsupervised discretize</option>
            <option <%if(inputpre.getpreprocessInput()!=null) if(inputpre.getpreprocessInput().equals("2")) out.println("selected='true'");%> value="2">unsupervised normalize (attribute)</option>
            <option <%if(inputpre.getpreprocessInput()!=null) if(inputpre.getpreprocessInput().equals("3")) out.println("selected='true'");%> value="3">unsupervised replace missing value</option>
            <option <%if(inputpre.getpreprocessInput()!=null) if(inputpre.getpreprocessInput().equals("4")) out.println("selected='true'");%> value="4">supervised Attribute Selection</option>
        </select>
        <input type="submit" value="Apply Preproccess" /><br>
    </form>
        <form>
<%
        
    if(weka.dataWeka != null){
        for(int i=0;i<weka.dataWeka.numAttributes();i++){
            if(weka.dataWeka.attribute(i)!=null)
            out.println("<input id='check' type='checkbox' name='image[]' id='pilihan' value=''> "+weka.dataWeka.attribute(i).name()+"<br>");
        }
               }

 %>
        Algoritma : <select>
            <option>K-NN</option>
            <option>Naive Bayes</option>
            <option>ID3</option>
        </select><br>
        Test Options : <br>
            <input type="radio" name="test_option">Cross Validation : <input type="text" value="10"> Folds<br>
            <input type="radio" name="test_option">Percentage Split : <input type="text" value="80">%<br>
            k-NN : <input type="text"><br>
            <input type="submit" value="Start Learning" />
        
    </form>

<script type="text/javascript">
//$(function() {
//    $('input[type="checkbox"]').change(function() {
//        var countchecked = $('input[type="checkbox"]').filter(":checked").length
//
//        if (countchecked >= <% //out.println(weka.dataWeka.numAttributes()); %>-1) {
//            $("input:checkbox").not(":checked").attr("disabled", true);
//        }else{
//            $("input:checkbox").attr("disabled", false);
//        }
//    });
//});
</script> 
     </body>
</html>
