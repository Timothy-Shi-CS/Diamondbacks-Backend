package com.example.Diamondbacks;

public class DistrictHandler {
    private District currentDistrict;
    public ObjectiveValue getObjectiveFunctionScore(int districtID){
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
