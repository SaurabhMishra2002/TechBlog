///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package com.user;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author Lenovo
// */
//public class Register extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Register</title>");
//            out.println("</head>");
//            out.println("<body>");
//            //Getting all details
//            
//            // Read form data
//            String name = request.getParameter("user_name");
//            String password = request.getParameter("user_password");
//            String email = request.getParameter("user_mail");
//            
//            try{
//            }catch(Exception e){
//                e.printStackTrace();
//            out.println("<h3>Error: " + e.getMessage() + "</h3>");
//            }
//            
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}

package com.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            // Getting all details from the form
            String name = request.getParameter("user_name");
            String password = request.getParameter("user_password");
            String email = request.getParameter("user_mail");
            
            // Database credentials
            String url = "jdbc:mysql://localhost:3306/project?useSSL=false&serverTimezone=UTC";
            String dbUser = "root"; // <-- Replace with your DB user
            String dbPass = "********"; // <-- Replace with your DB password
            Part part = request.getPart("image");
            
            String filename = part.getSubmittedFileName();
            //out.println(filename);
            
            try {
                // Load JDBC driver
                Thread.sleep(3000);
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish connection
                Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
                
                // SQL query
                String query = "INSERT INTO customer (user_name, user_password, user_mail,profile_image) VALUES (?, ?, ?, ?)";
                
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, password); // ⚠️ For security: Hash this in production
                ps.setString(3, email);
                ps.setString(4,filename);
                
//                int count =
                ps.executeUpdate();
                //Upload image
                
                InputStream is =part.getInputStream();
                byte [] data = new byte[is.available()];
                
                is.read(data);
                String path = request.getRealPath("/")+"img"+File.separator+filename;
                
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(data);
                fos.close(); 
                
                //if (count > 0) {
                    out.println("Done");
                //} else {
                //    out.println("<h3>Registration failed. Please try again.</h3>");
                //}
                
                ps.close();
                conn.close();
                
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h3>Error: " + e.getMessage() + "</h3>");
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to register users into the database";
    }// </editor-fold>

}
