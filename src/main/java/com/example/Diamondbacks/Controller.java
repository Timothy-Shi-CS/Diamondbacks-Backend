package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// http://localhost:8080/Diamondbacks-1.0-SNAPSHOT/api/controller
@Path("/controller")
public class Controller {
    @GET
    @Produces("text/plain")
    public String hello() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Diamondbacks");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et=null;
        try{
            et= em.getTransaction();
            et.begin();
            Customer cust = new Customer();
            cust.setId(11);
            cust.setFirstName("Bills");
            cust.setLastName("Hard");
            em.persist(cust);
            et.commit();
        }catch(Exception ex){
            if(et!=null){
                et.rollback();
            }
            ex.printStackTrace();
        }finally {
            emf.close();
        }
        return "Hello, World!";
    }

//    public Response callJobHandler(String stateName){
//
//    }
}
