package com.example.Diamondbacks;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.util.*;

// http://localhost:8080/Diamondbacks-1.0-SNAPSHOT/api/controller
@Path("/controller")
public class Controller {

    @Context
    ServletContext servletContext;

    private State state = new State();
    private Job currentJob;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Diamondbacks");
    private EntityManager em = emf.createEntityManager();

    @GET
    @Path("/data")
    @Produces("text/plain")
    public String hello() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;

        return "Success!";
    }

    @GET
    @Path("/loadprecicntgeometries")
    public Response loadPrecinctGeometries() {
        StateHandler stateHandler = new StateHandler();
        stateHandler.loadPrecinctGeometries(this.state);
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
        Minorities minorityName = Minorities.valueOf(((String) (servletContext.getAttribute("minority"))).toUpperCase());
        baw.makeBoxAndWhisker(this.state, id, minorityName);
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
    @Path("/state={stateName}&job={jobID}")
    public Response setStateAndJob(@PathParam("stateName") String stateName, @PathParam("jobID") String jobID) {
        StateHandler stateHandler = new StateHandler();
        JobHandler jobHandler = new JobHandler();

        if (servletContext.getAttribute("state") != null) {
            State curstate = (State) servletContext.getAttribute("state");
            curstate.setCurrentJob(null);
            curstate.setEnactedDistricting(null);
            curstate.setCurrentBoxAndWhisker(null);
            curstate.setCurrentDistricting(null);
            servletContext.setAttribute("state", curstate);
        }

        stateHandler.setState(stateName, state);
        stateHandler.setEnactedDistricting(state, stateName);
        currentJob = jobHandler.getJobByID(jobID, em, emf);
//        jobHandler.loadDistrictMinority(currentJob);
//        jobHandler.loadSecDistrictMinority(currentJob);

        state.setCurrentJob(currentJob);
        servletContext.setAttribute("state", state);
        Query incumQ = jobHandler.getIncumbents(state, em);
        return Response.status(Response.Status.OK).entity(incumQ.getResultList()).build();
    }

    @GET
    @Path("/setWeights/popEquality={populationEquality}&devAvgGeo={devAvgDistGeo}&devAvgPop={devAvgDistPop}&devEnactedGeo={devEnDistGeo}&devEnactedPop={devEnDistPop}&geoCompact={geo}&graphCompact={graph}&popFat={popFat}")
    public Response setWeights(@PathParam("populationEquality") String populationEquality, @PathParam("devAvgDistGeo") String devAvgDistGeo, @PathParam("devAvgDistPop") String devAvgDistPop,
                               @PathParam("devEnDistGeo") String devEnDistGeo, @PathParam("devEnDistPop") String devEnDistPop,
                               @PathParam("geo") String geo,
                               @PathParam("graph") String graph, @PathParam("popFat") String popFat) {
        State state = (State) servletContext.getAttribute("state");
        StateHandler stateHandler = new StateHandler();

        state = stateHandler.resetDistricts(state);
        state = stateHandler.setDistrictsForDistrictings(state);
        System.out.println(populationEquality + ", " + devAvgDistGeo + ", " + devAvgDistPop + ", " + devEnDistGeo + ", " + devEnDistPop + ", " + geo + ", " + graph + ", " + popFat);
        servletContext.setAttribute("state", stateHandler.setObjectiveValueAndCalcBAW(state, Double.parseDouble(populationEquality), Double.parseDouble(devAvgDistGeo), Double.parseDouble(devAvgDistPop), Double.parseDouble(devEnDistGeo), Double.parseDouble(devEnDistPop), Double.parseDouble(geo), Double.parseDouble(graph), Double.parseDouble(popFat)));

        Job curJob = state.getCurrentJob();

        List<Districting> top10Dists = (List<Districting>) curJob.findTopDistrictingsObjectFunction();
        List<ObjectiveValue> res = new ArrayList<>();
//        LinkedHashMap<Integer, HashMap<String,ObjectiveValue>> res = new LinkedHashMap<>();
        for (Districting districting : top10Dists) {
            districting.getDistrictingMeasures().setFileName(districting.getRecomb_file());
            res.add(districting.getDistrictingMeasures());
        }
        Districting districtingDeviationFromEnactedGeo = curJob.findTopDistrictingsByDeviationFromEnactedGeo();
        Districting districtingDeviationFromEnactedPop = curJob.findTopDistrictingsByDeviationFromEnactedPop();

        districtingDeviationFromEnactedPop.getDistrictingMeasures().setFileName(districtingDeviationFromEnactedPop.getRecomb_file());
        districtingDeviationFromEnactedGeo.getDistrictingMeasures().setFileName(districtingDeviationFromEnactedGeo.getRecomb_file());
        res.add(districtingDeviationFromEnactedPop.getDistrictingMeasures());
        res.add(districtingDeviationFromEnactedGeo.getDistrictingMeasures());

        List<Districting> areaPair = (List<Districting>) curJob.findVeryDifferentAreaPairDeviations();
        for (Districting districting : areaPair) {
            districting.getDistrictingMeasures().setFileName(districting.getRecomb_file());
            res.add(districting.getDistrictingMeasures());
        }
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET
    @Path("/deviation/districting={id}")
    public Response calculateDeviation(@PathParam("id") String districtingID) {
//        DistrictingHandler districting =
        return Response.status(Response.Status.OK).entity("Hello").build();
    }

    @GET
    @Path("/setDistricting/districtingID={id}")
    public Response setDistricting(@PathParam("id") String id) {
        State curState = (State) servletContext.getAttribute("state");
        Job curJob = curState.getCurrentJob();
        for (Districting districting : curJob.getListDistrictings()) {
            if (districting.getRecomb_file().equals("\"" + id + "\"")) {
                curState.setCurrentDistricting(districting);
                break;
            }
        }
        servletContext.setAttribute("state", curState);

        return Response.status(Response.Status.OK).entity(((State) servletContext.getAttribute("state")).getCurrentDistricting().getRecomb_file()).build();
    }

    @GET
    @Path("/getBAW")
    public Response getBAW() {
        State curState = (State) servletContext.getAttribute("state");


        List<Object> res = new ArrayList<>();
        Map<Integer, Float> curDistrictingData = curState.calCurrentBoxAndWhiskerPercent();
        Map<Integer, Float> enactedDistrictingData = curState.calEnactedBoxAndWhiskerPercent();
        Map<Integer, Collection<Float>> bawData =  curState.calAllBoxAndWhiskerPercent();

        res.add(curDistrictingData);
        res.add(enactedDistrictingData);
        res.add(bawData);
        return Response.status(Response.Status.OK).entity(res).build();
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
        Map<Integer, Geometry> geometries = districtingHandler.getDistrictingGeometry(districtingID, this.currentJob);
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
        StateHandler stateHandler = new StateHandler();
        List jobs = stateHandler.getJobs(stateName, em);
        return Response.status(Response.Status.OK).entity(jobs).build();
    }

    @GET
    @Path("/construct-constraint/job={jobID}&minority={minority}&threshold={threshold}&majMin={majMin}&incumbent={incumID}&pop={pop}&vap={vap}&cvap={cvap}&geoComp={geoComp}&graphComp={graphComp}&popFat={popFat}")
    public Response constructConstraints(@PathParam("jobID") String jobID, @PathParam("minority") String minority,
                                         @PathParam("threshold") String minorityThreshold, @PathParam("majMin") String majMin,
                                         @PathParam("incumID") String incumbentIDs,
                                         @PathParam("pop") String totalPop, @PathParam("vap") String vaPop,
                                         @PathParam("cvap") String cvaPop, @PathParam("geoComp") String geoComp,
                                         @PathParam("graphComp") String graphComp, @PathParam("popFat") String popFat) {
        ConstraintHandler constraintHandler = new ConstraintHandler();
        Collection<String> incumbentsProtected = convertIncumbentsArray(incumbentIDs);
        Minorities minorityName = Minorities.valueOf(minority.toUpperCase());
        servletContext.setAttribute("minority", minorityName);
        State state = (State) servletContext.getAttribute("state");
        currentJob = state.getCurrentJob();
        int remainingDistrictings = constraintHandler.getRemainingDistrictings(currentJob, minorityName,
                Double.parseDouble(minorityThreshold), Integer.parseInt(majMin), incumbentsProtected, Double.parseDouble(totalPop), Double.parseDouble(vaPop), Double.parseDouble(cvaPop), Double.parseDouble(geoComp), Double.parseDouble(graphComp), Double.parseDouble(popFat));
        System.out.println(remainingDistrictings);
        servletContext.setAttribute("state", state);
//        int count=0;
//        for(Districting districting: state.getCurrentJob().getListDistrictings()){
//            if(districting.getSatisfiesConstraints()==true){
//                count++;
//            }
//        }
        //return the number of districtings remaining
        return Response.status(Response.Status.OK).entity(Integer.toString(remainingDistrictings)).build();
    }

    private Collection<String> convertIncumbentsArray(String incumbentIDs) {
        String[] incumbents = incumbentIDs.substring(1, incumbentIDs.length() - 1).split(",");
        Collection<String> incumbentsProtected = new ArrayList<>();
        for (String incumbent : incumbents) {
            incumbentsProtected.add(incumbent);
        }
        return incumbentsProtected;
    }
}
