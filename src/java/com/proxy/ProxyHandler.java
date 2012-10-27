/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author amitabhsh
 */
public class ProxyHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            System.out.println(request.getMethod());
            if(request.getMethod()=="GET"){
                String urlParam = request.getParameter("url");
                 
                URL url = new URL(urlParam);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                String wsdlOutput = "";
                while ((line = reader.readLine()) != null) {
                    wsdlOutput+=line;
                }
                reader.close();
                out.println(wsdlOutput);
            }
            else if(request.getMethod()=="POST")
            {       try{
                     String urlParam = request.getParameter("url");
                    System.out.println("Post URL -->"+ urlParam);
                    String data= request.getParameter("soapBody");
                    System.out.println("Data -->" + data);
                    URL url = new URL(urlParam);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "text/xml");
                    
                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                    
                    //write parameters
                    writer.write(data);
                    writer.flush();
                    System.out.println("Post Request Sent...");
                    // Get the response
                    StringBuffer answer = new StringBuffer();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        answer.append(line);
                    }
                    writer.close();
                    reader.close();
                    out.println(answer.toString());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
