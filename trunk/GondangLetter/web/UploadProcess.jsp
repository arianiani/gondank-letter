<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>
<jsp:useBean id="weka" class="weka.WekaEngine" scope="session"/>

<%
       //out.println("Content Type ="+request.getContentType());
    //out.println("Cookies" + request.getCookies());

    DiskFileUpload fu = new DiskFileUpload();
    // If file size exceeds, a FileUploadException will be thrown
    fu.setSizeMax(1000000);

    List fileItems = fu.parseRequest(request);
    Iterator itr = fileItems.iterator();

    while(itr.hasNext()) {
      FileItem fi = (FileItem)itr.next();

      //Check if not form field so as to only handle the file inputs
      //else condition handles the submit button input
      if(!fi.isFormField()) {
        //out.println("\nNAME: "+fi.getName()+"<br>");
        //out.println("SIZE: "+fi.getSize()+"<br>");
        //System.out.println(fi.getOutputStream().toString());
        File fNew= new File(application.getRealPath("/file"), fi.getName());

        //out.println(fNew.getAbsolutePath()+"<br>");
        fi.write(fNew);
        
        
        weka.fileLoader(fNew.getAbsolutePath());
        //for(int i=0;i<weka.dataWeka.numAttributes();i++){
        //    out.println("<input id='check' type='checkbox' name='image[]' id='pilihan' value=''> "+weka.dataWeka.attribute(i).name()+"<br>");
        //}
        String redirectURL = "Learning.jsp";
        response.sendRedirect(redirectURL);

        
      }
      else {
        //out.println("Field ="+fi.getFieldName()+"<br>");
      }
    }
 %>