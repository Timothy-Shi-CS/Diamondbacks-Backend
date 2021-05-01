package com.example.Diamondbacks;

import javax.persistence.EntityManager;

public class DistrictHandler {
    private District currentDistrict;
    public ObjectiveValue getObjectiveFunctionScore(String districtID){
        return null;
    }
    public String getObjectiveFunctionDetail(Job currentJob, String districtID, EntityManager em){
        Districting currentDistricting = currentJob.getCurrentDistricting();
        currentDistricting.findDistrictByID(districtID, em);
        return null;
    }
    @Override
    public String toString() {
        return "DistrictHandler{" +
                "currentDistrict=" + currentDistrict +
                '}';
    }

    public District getCurrentDistrict() {
        return currentDistrict;
    }

    public void setCurrentDistrict(District currentDistrict) {
        this.currentDistrict = currentDistrict;
    }
}
