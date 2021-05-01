package com.example.Diamondbacks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class State {
    private Districting enactedDistricting;
    private Districting currentDistricting;
    private StateName stateName;
    private Map<Integer, Collection<Float>> jobSummaries;
    private Job currentJob;
    private Job constraintedJob;
    private BoxAndWhisker currentBoxAndWhisker;

    public void makeConstraintedJob(){
        Constraints userConstraints = this.getCurrentJob().getCurrentConstraints();
        Job filteredJob = new Job();
        Collection<Districting> filteredDistricting = new ArrayList<>();
        for(Districting districting: this.getCurrentJob().getListDistrictings()){
            if(districting.satisfyConstraints(userConstraints)){
                filteredDistricting.add(districting);
            }
        }
        filteredJob.setCoolingPeriod(this.getCurrentJob().getCoolingPeriod());
        filteredJob.setRounds(this.getCurrentJob().getRounds());
        filteredJob.setCurrentConstraints(this.getCurrentJob().getCurrentConstraints());
        filteredJob.setId(this.getCurrentJob().getId());
        filteredJob.setListDistrictings(filteredDistricting);
    }
    public void calcBoxAndWhisker(){
        Constraints userConstraints = this.getConstraintedJob().getCurrentConstraints();
        Minorities minorityToPlot = userConstraints.getMinoritySelected();

        Map<Integer, Collection<Float>> dataToPlot = new HashMap<Integer, Collection<Float>>();
        for(Districting districting: this.getConstraintedJob().getListDistrictings()){
            for(Integer districtID: districting.getSortedMinorityData().keySet()){
                Float countMinority = districting.getSortedMinorityData().get(districtID).get(minorityToPlot);
                if(!dataToPlot.containsKey(districtID)){
                    //if the key does not exist, intitize it to an empty array list
                    dataToPlot.put(districtID, new ArrayList<Float>());
                }
                dataToPlot.get(districtID).add(countMinority);
            }
        }
        Map<Integer, Float> currentDistrictingData = new HashMap<Integer, Float>();
        Map<Integer, Float> enactedDistrictingData = new HashMap<Integer, Float>();
        for(Integer districtID: this.currentDistricting.getSortedMinorityData().keySet()){
            currentDistrictingData.put(districtID, this.getCurrentDistricting().getSortedMinorityData().
                    get(districtID).get(minorityToPlot));
            enactedDistrictingData.put(districtID, this.getEnactedDistricting().getSortedMinorityData().
                    get(districtID).get(minorityToPlot));
        }
        BoxAndWhisker newBAW = new BoxAndWhisker(currentDistrictingData, enactedDistrictingData, dataToPlot);
        this.setCurrentBoxAndWhisker(newBAW);
        //after setting the BAW object calculate the average districting for the constrainted job
        this.getConstraintedJob().calAverageDistricting(newBAW);
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
