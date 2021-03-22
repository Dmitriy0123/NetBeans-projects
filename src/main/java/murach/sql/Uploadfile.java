/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package murach.sql;


import java.io.File;
import java.io.IOException;
import javax.servlet.*;
import java.util.*;
import java.nio.file.Path;


import java.util.stream.Collectors;
import java.nio.file.Paths;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Dmitriy
 */
public class Uploadfile extends HttpServlet {
    public static final String SAVE_DIR="uploadDir";
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
        response.setContentType("text/html;charset=UTF-8");
      try {
            String descr=request.getParameter("descr");
            
            String appPath=request.getServletContext().getRealPath("");
            appPath=appPath.replace("\\","/");
            System.out.println(appPath);
            String fullSavePath=null;
            
           if (appPath.endsWith("/")) {
               fullSavePath=appPath+SAVE_DIR;
           }
           else {
               fullSavePath=appPath+"/"+SAVE_DIR;
           }
           File fileSaveDir=new File(fullSavePath); 
           if (!fileSaveDir.exists()){
               fileSaveDir.mkdir();
           }
           List<Part> fileParts=request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
           for (Part filePart:fileParts){
           System.out.println(filePart.getSubmittedFileName());    
           String filename=Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
           System.out.println(filename);
           String filePath=fullSavePath+"/"+filename;
           System.out.println(filePath);
           filePart.write(filePath);
           }
           
           request.setAttribute("errorMessage", "No errors");
       String url="/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
      }
      
      catch (Exception e) {
       e.printStackTrace();
       request.setAttribute("errorMessage", "Error: " + e.getMessage());
       String url="/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
      }
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
