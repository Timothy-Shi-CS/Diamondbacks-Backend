package com.example.Diamondbacks;

import java.util.Collection;

public class DistrictingHandler {
    private Districting currentDistricting;
    public String getObjectiveFunctionScore(Job currentJob, int districtingID){
        Districting currentDistricting = currentJob.findDistrictingByID(districtingID);
        ObjectiveValue districtingMeasures = currentDistricting.getDistrictingMeasures();
        return districtingMeasures.toString();
    }
    public String getDistrictingBoundary(int districtingID){
        return null;
    }
    public String getObjectiveFunctionDetail(Job currentJob, int districtID){
        return null;
    }
    public Collection<Float> calculateDeviationFromEnacted(Job job, int districtingID){
        return null;
    }

    @Override
    public String toString() {
        return "DistrictingHandler{" +
                "currentDistricting=" + currentDistricting +
                '}';
    }

    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }
}
