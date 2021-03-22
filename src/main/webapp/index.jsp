<%-- 
    Document   : index
    Created on : 24 сент. 2020 г., 01:26:52
    Author     : Dmitriy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles.main.css" type="text/css">
        <title>JSP Page</title>
    </head>
    <body>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:if test="${sqlStatement == null}">
            <c:set var="sqlStatement" value="select * from murach" />
        </c:if>    
        
        <h1>The SQL Gateway</h1>
        <p> Enter the SQL statement and click the Execute button.</p>
        <p><b>SQL statement</b> </p>
        <form action="/SQLGetewayServlet" method="post">
            <textarea name="sqlStatement" cols="60" rows="8"> ${sqlStatement}</textarea>
            <input type="submit" value="execute">  
        </form>
            <p><b>SQL result:</b></p>
            ${sqlResult}
        
        
            <h1>Upload files to the server</h1> 
            <div>${errorMesage}</div>
            
            <form method="post" action="UploadFile" 
                  enctype="multipart/form-data">
                <label>Select file to upload</label> <br>
                <input type="file" name="file"> <br>
                <input type="file" name="file"> <br>
                <label>Description</label><br>
                <input type="text" name="descr" size="20"> <br>
                <br>
                <input type="submit" value="upload">
                
            </form>

            ${errorMessage};
            
            
    </body>
</html>
