package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class StateHandler {
    public List getJobs(String stateName, EntityManager em){
        /*
         * mySQL query to get jobs
         * */
        Query q = em.createNativeQuery("SELECT * FROM Diamondbacks.Jobs WHERE state_stateName = 2");
        return q.getResultList();
    }
    public void calculateBoxAndWhiskerData(String state){

    }
}
