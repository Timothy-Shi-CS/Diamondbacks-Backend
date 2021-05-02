package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.QueryParam;
import java.util.Collection;
import java.util.List;

public class StateHandler {
    public List getJobs(String stateName, EntityManager em) {
        /*
         * mySQL query to get jobs
         * */
        // convert stateName to int
        System.out.println(StateName.valueOf(stateName).getStateNumber());
        int stateValue = StateName.valueOf(stateName).getStateNumber();
        String queryString = "from Job j where j.state.stateName="+stateValue;
        Query q = em.createQuery(queryString);
//        List<Job> jobList = q.getResultList();
//        for (Job j : jobList) {
//            System.out.println(j.toString());
//        }

        return q.getResultList();
    }

    public void loadPrecinctGeometries(State state){
        state.loadPrecinctGeometries();
    }

    public void calculateBoxAndWhiskerData(String state) {

    }

    public void setState(String stateName, State state){
        StateName name = StateName.valueOf(stateName);
        state.setStateName(name);
    }
}
