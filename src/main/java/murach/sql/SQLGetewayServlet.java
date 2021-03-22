/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package murach.sql;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.lang.*;

/**
 *
 * @author Dmitriy
 */
@WebServlet(urlPatterns = {"/SQLGetewayServlet"})
public class SQLGetewayServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sqlStatement=request.getParameter("sqlStatement");
        String sqlResult="";
        try {
        // load driver
        Class.forName("com.mysql.jdbc.Driver");
        
        
        //get a Connection
        String dbURL="jdbc:mysql://localhost:3306/mydatatest";
        String username="root";
        String password="";
        Connection connection=DriverManager.getConnection(dbURL,username,password);
        
        //create statement
        Statement statement=connection.createStatement();
        
        //parse the SQK string
        sqlStatement=sqlStatement.trim();
        if (sqlStatement.length()>=6) {
        String sqlType=sqlStatement.substring(0,6);
            if (sqlType.equalsIgnoreCase("select")) {
                //create the HTML for the resuly set
                ResultSet resultSet=statement.executeQuery(sqlStatement);
                sqlResult=SQLUtil.getHtmlTable(resultSet);
                resultSet.close();}
                
                else {
                    int i=statement.executeUpdate(sqlStatement);
                    if (i==0) {//DDL Statement
                        sqlResult="<p>The statement executed unsuccesfully</p>" ;}
                    else { //an INSERT, UPDATE, or DELETE statement
                        sqlResult="<p>The statement executed succesfully</p>";}
                     }
        }
        statement.close();
        connection.close();
        }
        catch (ClassNotFoundException e){
            sqlResult="<p>Error loading the database driver:<br>"+e.getMessage()+"</p>";}
        catch (SQLException e) {
            sqlResult="<p>Error executing the SQL statement:<br>"+e.getMessage()+"</p>";}
        
        HttpSession session=request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);
        
        String url="/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }   
      
    
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
