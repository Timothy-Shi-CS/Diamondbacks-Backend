package com.example.Diamondbacks;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

// http://localhost:8080/Diamondbacks-1.0-SNAPSHOT/api/controller
@Path("/controller")
public class Controller {

    private final State STATE = new State();
    private Minorities minority;
    private Job currentJob;
    private EntityManager em;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Diamondbacks");

    @GET
    @Path("/data")
    @Produces("text/plain")
    public String hello() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Customer cust = new Customer();
            cust.setId(15);
            cust.setFirstName("Billy");
            cust.setLastName("Hardy");
            em.persist(cust);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            emf.close();
        }
        return "Success!";
    }

    @GET
    @Path("/loadprecicntgeometries")
    public Response loadPrecinctGeometries(){
        StateHandler stateHandler = new StateHandler();
        stateHandler.loadPrecinctGeometries(this.STATE);
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/job/state={stateName}")
    public Response callJobHandler(@PathParam("stateName") String stateName) {
        JobHandler job = new JobHandler();
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/box-and-whisker/districting={id}&minority={minority}")
    public Response callBAWHandler(@PathParam("id") String id, @PathParam("minority") String minority) {
        BoxAndWhiskerHandler baw = new BoxAndWhiskerHandler();
        Minorities minorityName = this.minority;
        baw.makeBoxAndWhisker(this.STATE, id, minorityName);
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/constraint/job={jobID}&maj-min={majMin}&incumbent={incumID}&pop={pop}&vap={vap}&cvap={cvap}&geo-comp={geoComp}&graph-comp={graphComp}&pop-fat={popFat}")
    public Response callConstraintHandler(@PathParam("jobID") String jobID, @PathParam("majMin") int majMin,
                                          @PathParam("incumID") String incumbentIDs,
                                          @PathParam("pop") float pop, @PathParam("vap") float vap,
                                          @PathParam("cvap") float cvap, @PathParam("geoComp") float geoComp,
                                          @PathParam("graphComp") float graphComp, @PathParam("popFat") float popFat) {
        ConstraintHandler constraint = new ConstraintHandler();
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/districting/id={districtingID}")
    public Response callDistrictingHandler(@PathParam("districtingID") String districtingID) {
        DistrictingHandler districting = new DistrictingHandler();
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/district/id={districtID}")
    public Response callDistrictHandler(@PathParam("districtID") String districtID) {
        DistrictHandler district = new DistrictHandler();
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/state={stateName}")
    public Response callStateHandler(@PathParam("stateName") String stateName) {
        StateHandler stateHandler = new StateHandler();
        stateHandler.setState(stateName,STATE);
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/deviation/districting={id}")
    public Response calculateDeviation(@PathParam("id") String districtingID) {
//        DistrictingHandler districting =
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/objective-value")
    public Response getObjectiveValue() {
        DistrictingHandler districting = new DistrictingHandler();
//        districting.getObjectiveFunctionScore();
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/district-objective-value/id={districtID}")
    public Response getDistrictObjectiveValue(@PathParam("districtID") String districtID) {
        DistrictHandler districtHandler = new DistrictHandler();
        em = emf.createEntityManager();
        String result = districtHandler.getObjectiveFunctionDetail(this.currentJob, districtID, this.em);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/districting-objective-value/id={districtingID}")
    public Response getDistrictingObjectiveValue(@PathParam("districtingID") String districtingID) {
        DistrictingHandler districtingHandler = new DistrictingHandler();
        String result = districtingHandler.getObjectiveFunctionScore(this.currentJob, districtingID, this.em);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/districting-boundary/id={districtingID}")
    public Response getDistrictingBoundary(@PathParam("districtingID") String districtingID) {
        DistrictingHandler districtingHandler = new DistrictingHandler();
        Map<Integer,Geometry> geometries = districtingHandler.getDistrictingGeometry(districtingID, this.currentJob);
        return Response.status(Response.Status.OK).entity(geometries).build();
    }

    @GET
    @Path("/summary")
    public Response getPrelimSummary() {
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/jobs={stateName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobs(@PathParam("stateName") String stateName) {
        em = emf.createEntityManager();
        StateHandler state = new StateHandler();
        List jobs = state.getJobs(stateName, em);
        return Response.status(Response.Status.OK).entity(jobs).build();
    }

    @Path("/constructed-constraint/job={jobID}&minority={minority}&threshold={threshold}&maj-min={majMin}&incumbent={incumID}&pop={pop}&vap={vap}&cvap={cvap}&tvap={tvap}&geo-comp={geoComp}&graph-comp={graphComp}&pop-fat={popFat}")
    @GET
    public Response constructConstraints(@PathParam("jobID") String jobID, @PathParam("minority") String minority,
                                         @PathParam("threshold") float minorityThreshold, @PathParam("majMin") int majMin,
                                         @PathParam("incumID") String incumbentIDs,
                                         @PathParam("pop") float totalPop, @PathParam("cvap") float cvaPop,
                                         @PathParam("tvap") float tvaPop, @PathParam("geoComp") float geoComp,
                                         @PathParam("graphComp") float graphComp, @PathParam("popFat") float popFat) {
        ConstraintHandler constraintHandler = new ConstraintHandler();
        Collection<Integer> incumbentsProtected = convertIncumbentsArray(incumbentIDs);
        Minorities minorityName = null;
        switch (minority) {
            case "hispanic":
                minorityName = Minorities.HISPANIC;
                break;
            case "black":
                minorityName = Minorities.BLACK;
                break;
            case "asian":
                minorityName = Minorities.ASIAN;
                break;
        }
        this.minority = minorityName;
        int remainingDistrictings = constraintHandler.setConstraintsHandler(this.currentJob, minorityName,
                minorityThreshold, majMin, incumbentsProtected, totalPop, tvaPop, cvaPop, geoComp, graphComp, popFat);
        //return the number of districtings remaining
        return Response.status(Response.Status.OK).entity(Integer.toString(remainingDistrictings)).build();
    }

    private Collection<Integer> convertIncumbentsArray(String incumbentIDs) {
        String[] incumbents = incumbentIDs.split(",");
        Collection<Integer> incumbentsProtected = new ArrayList<>();
        for (String incumbent : incumbents) {
            incumbentsProtected.add(Integer.parseInt(incumbent));
        }
        return incumbentsProtected;
    }
}
