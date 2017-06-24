/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.macrobug.readitlater;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author sadjehwty
 */
@Entity
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String password;
    
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    
    @Override
    public String toString() {
        return "dev.macrobug.readitlater.User[ username=" + username + " ]";
    }
    
    public List<Website> getWebsites(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session hsession = sessionFactory.getCurrentSession();
        boolean nested=hsession.getTransaction().isActive();
        if(!nested) hsession.beginTransaction();
        List<Website> list = hsession.createQuery("from Website ws INNER JOIN ws.publisher us where us.username ='"+username+"'").list();
        if(!nested) hsession.getTransaction().commit();
        return list;
    }
}
