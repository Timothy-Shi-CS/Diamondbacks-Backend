package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.QueryParam;
import java.util.Collection;
import java.util.List;

public class StateHandler {
    public List getJobs(String stateName, EntityManager em){
        /*
         * mySQL query to get jobs
         * */
        // convert stateName to int
        System.out.println(StateName.valueOf(stateName).getStateNumber());
        int stateValue = StateName.valueOf(stateName).getStateNumber();
        String queryString = "SELECT * FROM Diamondbacks.Jobs WHERE state_stateName = " + stateValue;
        Query q = em.createNativeQuery(queryString);
        return q.getResultList();
    }
    public void calculateBoxAndWhiskerData(String state){

    }
}
