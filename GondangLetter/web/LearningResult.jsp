<%@page import="java.util.ArrayList"%>
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
        <script type="text/javascript" src="jquery-1.5.2.min.js"></script>
    </head>
    <body>
        <a href="Upload.jsp">Back</a><hr>
        <table align="center" width="900" border="1">
            <tr align="center">
                <td>Weka</td>
                <td>Our</td>
            </tr>
            <tr>
    <%
        if(inputlearn.getAlgoritma()!=0 && weka.dataWeka!=null){
            ArrayList<Integer> getIdx = new ArrayList<Integer>();
            for(int i=0;i<inputlearn.getCheckAttribute().length;i++){
                if(Integer.parseInt(inputlearn.getCheckAttribute()[i])==0){
                    getIdx.add(i);
                }
            }
            int[] remAttr = new int[getIdx.size()];
            for(int i=0;i<getIdx.size();i++){
                remAttr[i]=getIdx.get(i);
                //out.println(remAttr[i]);
            }
            out.println(weka.evaluateModel(inputlearn.getAlgoritma(), inputlearn.getKnn(), remAttr));
        }else {
            out.println("<td>No algoritma or weka null</td>");
        }
    %>
            </tr>
        </table>
            <%        
            if(inputlearn.getCheckAttribute()!=null){
                //for(int i=0;i<inputlearn.getCheckAttribute().length;i++){
                  //  out.println(inputlearn.getCheckAttribute()[i]);
                
                //}
            }else{
                out.println("tidak ada check");
            }
            %>
     </body>
</html>
