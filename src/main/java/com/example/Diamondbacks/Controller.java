package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

// http://localhost:8080/Diamondbacks-1.0-SNAPSHOT/api/controller
@Path("/controller")
public class Controller {

    private State state;
    private Job currentJob;
    private EntityManager em;

    @GET
    @Path("/data")
    @Produces("text/plain")
    public String hello() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Diamondbacks");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et=null;
        try{
            et= em.getTransaction();
            et.begin();
            Customer cust = new Customer();
            cust.setId(15);
            cust.setFirstName("Billy");
            cust.setLastName("Hardy");
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
        return "Success!";
    }

    @GET
    @Path("/job/state={stateName}")
    public Response callJobHandler(@PathParam("stateName") String stateName){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/box-and-whisker/districting={id}&minority={minority}")
    public Response callBAWHandler(@PathParam("id") int id, @PathParam("minority") String minority){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/constraint/job={jobID}&maj-min={majMin}&incumbent={incumID}&pop={pop}&vap={vap}&cvap={cvap}&geo-comp={geoComp}&graph-comp={graphComp}&pop-fat={popFat}")
    public Response callConstraintHandler(@PathParam("jobID") int jobID, @PathParam("majMin") int majMin,
                                          @PathParam("incumID") String incumbentIDs,
                                          @PathParam("pop") float pop, @PathParam("vap") float vap,
                                          @PathParam("cvap") float cvap, @PathParam("geoComp") float geoComp,
                                          @PathParam("graphComp") float  graphComp, @PathParam("popFat") float popFat){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/districting/id={districtingID}")
    public Response callDistrictingHandler(@PathParam("districtingID") int districtingID){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/district/id={districtID}")
    public Response callDistrictHandler(@PathParam("districtID") int districtID){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/state={stateName}")
    public Response callStateHandler(@PathParam("stateName") String stateName){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/deviation/id={districtingID}")
    public Response calculateDeviation(@PathParam("districtingID") int districtingID){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/objective-value")
    public Response getObjectiveValue(){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/district-objective-value/id={districtID}")
    public Response getDistrictObjectiveValue(@PathParam("districtID") int districtID){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/districting-objective-value/id={districtingID}")
    public Response getDistrictingObjectiveValue(@PathParam("districtingID") int districtingID){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/districting-boundary/id={districtingID}")
    public Response getDistrictingBoundary(@PathParam("districtingID") int districtingID){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/summary")
    public Response getPrelimSummary(){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/jobs={stateName}")
    public Response getJobs(@PathParam("stateName") String stateName){
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/constructed-constraint/job={jobID}&maj-min={majMin}&incumbent={incumID}&pop={pop}&vap={vap}&cvap={cvap}&tvap={tvap}&geo-comp={geoComp}&graph-comp={graphComp}&pop-fat={popFat}")
    public Response constructConstraints(@PathParam("jobID") float jobID, @PathParam("majMin") int majMin,
                                         @PathParam("incumID") String incumbentIDs,
                                         @PathParam("pop") float totalPop, @PathParam("cvap") float cvaPop,
                                         @PathParam("tvap") float tvaPop, @PathParam("geoComp") float geoComp,
                                         @PathParam("graphComp") float graphComp, @PathParam("popFat") float popFat){
        ConstraintHandler constraintHandler = new ConstraintHandler();
//        int remainingDistrictings = constraintHandler.setConstraintsHandler(this.currentJob,majMin,incumbentIDs,totalPop,tvaPop,cvaPop,geoComp,graphComp,popFat);
        //return the number of districtings remaining
        return Response.status(Response.Status.OK).entity("hello").build();
    }
}
