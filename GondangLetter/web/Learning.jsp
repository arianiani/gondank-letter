<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>

<jsp:useBean id="weka" class="weka.WekaEngine" scope="session"/>
<jsp:useBean id="inputpre" class="weka.InputPre" scope="session"/>
<jsp:useBean id="inputlearn" class="weka.InputLearning" scope="session"/>
<jsp:setProperty name="inputpre" property="*" />
<jsp:setProperty name="inputlearn" property="*" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Learning</title>
	<link rel="stylesheet" type="text/css" href="css/pretty.css" />
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
        <form method="post" action="Learning.jsp">
<%
        
    if(weka.dataWeka != null){
        for(int i=0;i<weka.dataWeka.numAttributes();i++){
            if(weka.dataWeka.attribute(i)!=null)
            out.println("<input id='check' type='checkbox' name='image[]' id='pilihan' value=''> "+weka.dataWeka.attribute(i).name()+"<br>");
        }
               }

 %>
        Algoritma : <select name="algoritma">
            <option value="1">K-NN</option>
            <option value="2">Naive Bayes</option>
            <option value="3">ID3</option>
        </select><br>
        Test Options : (menggunakan Cross Validation dengan jumlah folds 10)<br>
            <input type="submit" value="Start Learning" />
        
    </form>
<%
    if(inputlearn.getAlgoritma()!=0 && weka.dataWeka!=null){
        out.println("<hr>"+weka.classify(inputle));
    }
%>
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

	<div class="bar">
				<ul class="TGraph">
				<li class="p1" style="height: 327px; left: 0px;" title="Today, 6 Feb 2007"><p>A</p></li>
				<li class="p2" style="height: 227px; left: 0px;" title="Today, 6 Feb 2007"><p>27</p></li>
				<li class="p3" style="height: 107px; left: 0px;" title="Today, 6 Feb 2007"><p>A</p></li>
				<li class="p1" style="height: 87px; left: 0px;" title="Today, 6 Feb 2007"><p>A</p></li>
				<li class="p2" style="height: 67px; left: 0px;" title="Today, 6 Feb 2007"><p>A</p></li>
				<li class="p3" style="height: 47px; left: 0px;" title="Today, 6 Feb 2007"><p>A</p></li>
				<li class="p1" style="height: 27px; left: 0px;" title="Today, 6 Feb 2007"><p>A</p></li>
				
				<li class="p1" style="height: 144px; left: 40px;" title="Monday, 5 Feb 2007"><p>144</p></li>
				<li class="p2" style="height: 74px; left: 40px;" title="Monday, 5 Feb 2007"><p>74</p></li>
				<li class="p3" style="height: 54px; left: 40px;" title="Monday, 5 Feb 2007"><p>54</p></li>
				
				<li class="p1" style="height: 153px; left: 80px;" title="Sunday, 4 Feb 2007"><p>153</p></li>
				<li class="p2" style="height: 93px; left: 80px;" title="Sunday, 4 Feb 2007"><p>93</p></li>
				<li class="p3" style="height: 73px; left: 80px;" title="Sunday, 4 Feb 2007"><p>73</p></li>
				
				<li class="p1" style="height: 152px; left: 120px;" title="Saturday, 3 Feb 2007"><p>152</p></li>
				<li class="p2" style="height: 92px; left: 120px;" title="Saturday, 3 Feb 2007"><p>92</p></li>
				<li class="p3" style="height: 68px; left: 120px;" title="Saturday, 3 Feb 2007"><p>68</p></li>
				
				<li class="p1" style="height: 152px; left: 160px;" title="Friday, 2 Feb 2007"><p>152</p></li>
				<li class="p2" style="height: 92px; left: 160px;" title="Friday, 2 Feb 2007"><p>92</p></li>
				<li class="p3" style="height: 72px; left: 160px;" title="Friday, 2 Feb 2007"><p>72</p></li>
				
				<li class="p1" style="height: 160px; left: 200px;" title="Thursday, 1 Feb 2007"><p>160</p></li>
				<li class="p2" style="height: 100px; left: 200px;" title="Thursday, 1 Feb 2007"><p>100</p></li>
				<li class="p3" style="height: 58px; left: 200px;" title="Thursday, 1 Feb 2007"><p>58</p></li>
				
				<li class="p1" style="height: 155px; left: 240px;" title="Wednesday, 31 Jan 2007"><p>155</p></li>
				<li class="p2" style="height: 90px; left: 240px;" title="Wednesday, 31 Jan 2007"><p>90</p></li>
				<li class="p3" style="height: 50px; left: 240px;" title="Wednesday, 31 Jan 2007"><p>50</p></li>
				
				<li class="p1" style="height: 150px; left: 280px;" title="Tuesday, 30 Jan 2007"><p>150</p></li>
				<li class="p2" style="height: 80px; left: 280px;" title="Tuesday, 30 Jan 2007"><p>80</p></li>
				<li class="p3" style="height: 62px; left: 280px;" title="Tuesday, 30 Jan 2007"><p>62</p></li>
				
				<li class="p1" style="height: 157px; left: 320px;" title="Monday, 29 Jan 2007"><p>157</p></li>
				<li class="p2" style="height: 87px; left: 320px;" title="Monday, 29 Jan 2007"><p>87</p></li>
				<li class="p3" style="height: 70px; left: 320px;" title="Monday, 29 Jan 2007"><p>70</p></li>
				
				<li class="p1" style="height: 167px; left: 360px;" title="Sunday, 28 Jan 2007"><p>167</p></li>
				<li class="p2" style="height: 97px; left: 360px;" title="Sunday, 28 Jan 2007"><p>97</p></li>
				<li class="p3" style="height: 66px; left: 360px;" title="Sunday, 28 Jan 2007"><p>66</p></li>
			
				<li class="p1" style="height: 180px; left: 400px;" title="Saturday, 27 Jan 2007"><p>180</p></li>
				<li class="p2" style="height: 110px; left: 400px;" title="Saturday, 27 Jan 2007"><p>110</p></li>
				<li class="p3" style="height: 92px; left: 400px;" title="Saturday, 27 Jan 2007"><p>92</p></li>
				
				<li class="p1" style="height: 180px; left: 440px;" title="Friday, 26 Jan 2007"><p>180</p></li>
				<li class="p2" style="height: 110px; left: 440px;" title="Friday, 26 Jan 2007"><p>110</p></li>
				<li class="p3" style="height: 95px; left: 440px;" title="Friday, 26 Jan 2007"><p>95</p></li>
				
				<li class="p1" style="height: 192px; left: 480px;" title="Thursday, 25 Jan 2007"><p>192</p></li>
				<li class="p2" style="height: 122px; left: 480px;" title="Thursday, 25 Jan 2007"><p>122</p></li>
				<li class="p3" style="height: 91px; left: 480px;" title="Thursday, 25 Jan 2007"><p>91</p></li>
				
				<li class="p1" style="height: 190px; left: 520px;" title="Wednesday, 24 Jan 2007"><p>190</p></li>
				<li class="p2" style="height: 126px; left: 520px;" title="Wednesday, 24 Jan 2007"><p>126</p></li>
				<li class="p3" style="height: 95px; left: 520px;" title="Wednesday, 24 Jan 2007"><p>95</p></li>
				
				<li class="p1" style="height: 183px; left: 560px;" title="Tuesday, 23 Jan 2007"><p>183</p></li>
				<li class="p2" style="height: 130px; left: 560px;" title="Tuesday, 23 Jan 2007"><p>130</p></li>
				<li class="p3" style="height: 95px; left: 560px;" title="Tuesday, 23 Jan 2007"><p>95</p></li>
		</ul>
		
	</div>
     </body>
</html>
