package com.example.Diamondbacks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// http://localhost:8080/Diamondbacks-1.0-SNAPSHOT/api/controller
@Path("/controller")
public class Controller {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}
