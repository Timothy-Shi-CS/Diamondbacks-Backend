package com.example.Diamondbacks;

import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

// http://localhost:8080/Diamondbacks-1.0-SNAPSHOT/api/controller
@Path("/controller")
public class Controller {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

//    public Response callJobHandler(String stateName){
//
//    }
}
