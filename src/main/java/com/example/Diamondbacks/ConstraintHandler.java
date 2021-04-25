package com.example.Diamondbacks;

import java.util.Collection;

public class ConstraintHandler {
    private Job currentJob;
    public void setConstraintsHandler(int jobID, int majMin, Collection<String> incumbents, float pop, float vap, float cvap, float geoComp, float graphComp, float popFat){

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
