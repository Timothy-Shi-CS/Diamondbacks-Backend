package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import java.util.Collection;

public class ConstraintHandler {
    private Job currentJob;
    public int getRemainingDistrictings(Job curJob, Minorities selectedMinority, double minorityThreshold, int majMin,
                                        Collection<String> incumbents, double pop, double vap, double cvap, double geoComp,
                                        double graphComp, double popFat){
        // construct a constraints object, pass in the constraints to setCurrentConstraints
        this.setCurrentJob(curJob);
        Constraints currentConstraints = new Constraints(incumbents, selectedMinority, minorityThreshold, majMin, pop,
                vap,cvap,geoComp,graphComp,popFat);
        currentJob.setCurrentConstraints(currentConstraints);
        // count the remaining districts
        return currentJob.countRemainDistrictings();
    }

    @Override
    public String toString() {
        return "ConstraintHandler{" +
                "currentJob=" + currentJob +
                '}';
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }
}
