package com.example.Diamondbacks;

import java.util.Collection;

public class Districting {
    private CensusInfo censusInfo;
    private ObjectiveValue districtingMeasures;
    private Collection<District> districtsList;
    private int districtingID;

    public Geometry calGeometry(){
        return null;
    }
    public void renumberDistricts(){

    }
    public int countMajorityMinorityDistrict(){
        return 0;
    }
    public Measure calSplitCounties(){
        return null;
    }
    public Measure calDeviationFromAvgDist(){
        return null;
    }
    public Measure calDeviationFromEnactDistGeometric(Districting districting){
        return null;
    }
    public Measure calDeviationFromEnactDistPopulation(Districting districting){
        return null;
    }
    public Measure calGeometricCompactness(){
        return null;
    }
    public Measure calGraphCompactness(){
        return null;
    }
    public Measure calPopulationFatness(){
        return null;
    }
    public Measure calPoliticalFairness(){
        return null;
    }
    public ObjectiveValue calSummaryMeasures(){
        return null;
    }
    public District findDistrictByID(int districtID){
        return null;
    }

    @Override
    public String toString() {
        return "Districting{" +
                "censusInfo=" + censusInfo +
                ", districtingMeasures=" + districtingMeasures +
                ", districtsList=" + districtsList +
                ", districtingID=" + districtingID +
                '}';
    }

    public CensusInfo getCensusInfo() {
        return censusInfo;
    }

    public void setCensusInfo(CensusInfo censusInfo) {
        this.censusInfo = censusInfo;
    }

    public ObjectiveValue getDistrictingMeasures() {
        return districtingMeasures;
    }

    public void setDistrictingMeasures(ObjectiveValue districtingMeasures) {
        this.districtingMeasures = districtingMeasures;
    }

    public Collection<District> getDistrictsList() {
        return districtsList;
    }

    public void setDistrictsList(Collection<District> districtsList) {
        this.districtsList = districtsList;
    }

    public int getDistrictingID() {
        return districtingID;
    }

    public void setDistrictingID(int districtingID) {
        this.districtingID = districtingID;
    }
}
