package com.example.Diamondbacks;

import java.util.Collection;

public class District {
    private CensusInfo censusInfo;
    private int districtNumber;
    private Measure districtMeasures;
    private Collection<Precinct> precinctList;
    private Collection<District> neighborDistricts;
    private Geometry districtGeometry;

    public Geometry calGeometry(){
        return null;
    }
    public Measure calSplitCounties(){
        return null;
    }
    public Measure calDeviationFromAvgDist(){
        return null;
    }
    public Measure calPoliticalFairness(){
        return null;
    }
    public ObjectiveValue calSummaryMeasures() {
        return null;
    }

    @Override
    public String toString() {
        return "District{" +
                "censusInfo=" + censusInfo +
                ", districtNumber=" + districtNumber +
                ", districtMeasures=" + districtMeasures +
                ", precinctList=" + precinctList +
                ", neighborDistricts=" + neighborDistricts +
                ", districtGeometry=" + districtGeometry +
                '}';
    }

    public CensusInfo getCensusInfo() {
        return censusInfo;
    }

    public void setCensusInfo(CensusInfo censusInfo) {
        this.censusInfo = censusInfo;
    }

    public int getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }

    public Measure getDistrictMeasures() {
        return districtMeasures;
    }

    public void setDistrictMeasures(Measure districtMeasures) {
        this.districtMeasures = districtMeasures;
    }

    public Collection<Precinct> getPrecinctList() {
        return precinctList;
    }

    public void setPrecinctList(Collection<Precinct> precinctList) {
        this.precinctList = precinctList;
    }

    public Collection<District> getNeighborDistricts() {
        return neighborDistricts;
    }

    public void setNeighborDistricts(Collection<District> neighborDistricts) {
        this.neighborDistricts = neighborDistricts;
    }

    public Geometry getDistrictGeometry() {
        return districtGeometry;
    }

    public void setDistrictGeometry(Geometry districtGeometry) {
        this.districtGeometry = districtGeometry;
    }
}
