/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.macrobug.readitlater;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author sadjehwty
 */
@WebServlet({"/"})
public class Controller extends HttpServlet {
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
        String username = request.getParameter("username");
        if(username == null || username.isEmpty()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }else{
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session hsession = sessionFactory.getCurrentSession();
            hsession.beginTransaction();
            Publisher user = (Publisher) hsession.get(Publisher.class, username);
            if(user==null){
                hsession.getTransaction().rollback();
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                List<Website> list = user.getWebsites();
                hsession.getTransaction().commit();
                request.setAttribute("user", user);
                request.setAttribute("list", list);
                request.getRequestDispatcher("list.jsp").forward(request, response);
            }
        }
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
        String url = request.getParameter("url");
        if(url==null) url="";
        String title = request.getParameter("title");
        if(title==null) title=url;
        String username = request.getParameter("username");
        if(username==null || username.isEmpty() || url.isEmpty()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }else{
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session hsession = sessionFactory.getCurrentSession();
            hsession.beginTransaction();
            try{
                Publisher user = new Publisher();
                user.setUsername(username);
                Website website = new Website();
                website.setTitle(title);
                website.setUrl(url);
                website.setPublisher(user);
                hsession.saveOrUpdate(user);
                hsession.save(website);
                hsession.getTransaction().commit();
                response.setStatus(HttpServletResponse.SC_CREATED);
            }catch(HibernateException he){
                hsession.getTransaction().rollback();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
    
    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("id");
        if(idString==null) idString = "";
        try{
            long id=Long.parseLong(idString);
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session hsession = sessionFactory.getCurrentSession();
            hsession.beginTransaction();
            try{
                Website website = (Website) hsession.get(Website.class, id);
                Publisher user = website.getPublisher();
                hsession.delete(website);
                hsession.getTransaction().commit();
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }catch(HibernateException he){
                hsession.getTransaction().rollback();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }catch(NumberFormatException nfe){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "TEST";
    }// </editor-fold>

}
