package com.example.Diamondbacks;

import java.util.Collection;

public class ConstraintHandler {
    private Job currentJob;
    public int setConstraintsHandler(Job currentJob, Minorities selectedMinority, float minorityThreshold, int majMin,
                                     Collection<Integer> incumbents, float pop, float vap, float cvap, float geoComp,
                                     float graphComp, float popFat){
        // construct a constraints object, pass in the constraints to setCurrentConstraints
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
