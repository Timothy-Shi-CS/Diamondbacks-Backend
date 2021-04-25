package com.example.Diamondbacks;

import java.util.Collection;
import java.util.Map;

public class State {
    private Districting enactedDistricting;
    private Districting currentDistricting;
    private StateName stateName;
    private Map<Integer, Collection<Float>> jobSummaries;
    private Job currentJob;
    private Job constraintedJob;
    private BoxAndWhisker currentBoxAndWhisker;

    public void calcBoxAndWhisker(Job constraintedJob, Districting enactedDistricting, String minority){

    }

    @Override
    public String toString() {
        return "State{" +
                "enactedDistricting=" + enactedDistricting +
                ", currentDistricting=" + currentDistricting +
                ", stateName=" + stateName +
                ", jobSummaries=" + jobSummaries +
                ", currentJob=" + currentJob +
                ", constraintedJob=" + constraintedJob +
                ", currentBoxAndWhisker=" + currentBoxAndWhisker +
                '}';
    }

    public Districting getEnactedDistricting() {
        return enactedDistricting;
    }

    public void setEnactedDistricting(Districting enactedDistricting) {
        this.enactedDistricting = enactedDistricting;
    }

    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }

    public StateName getStateName() {
        return stateName;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    public Map<Integer, Collection<Float>> getJobSummaries() {
        return jobSummaries;
    }

    public void setJobSummaries(Map<Integer, Collection<Float>> jobSummaries) {
        this.jobSummaries = jobSummaries;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public Job getConstraintedJob() {
        return constraintedJob;
    }

    public void setConstraintedJob(Job constraintedJob) {
        this.constraintedJob = constraintedJob;
    }

    public BoxAndWhisker getCurrentBoxAndWhisker() {
        return currentBoxAndWhisker;
    }

    public void setCurrentBoxAndWhisker(BoxAndWhisker currentBoxAndWhisker) {
        this.currentBoxAndWhisker = currentBoxAndWhisker;
    }
}
