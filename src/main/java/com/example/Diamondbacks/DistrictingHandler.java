package com.example.Diamondbacks;

import java.util.Collection;

public class DistrictingHandler {
    private Districting currentDistricting;
    public ObjectiveValue getObjectiveFunctionScore(int districtingID){
        return null;
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

//    private Geometry getDistrictingGeometry(int districtingID, Job currentJob){
//        this.currentDistricting = currentJob.getDistrictingByID(districtingID);
//    }
    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }
}
