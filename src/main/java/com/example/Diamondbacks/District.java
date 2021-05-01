package com.example.Diamondbacks;

import java.util.Collection;

public class District {
    private CensusInfo censusInfo;
    private int districtNumber;
    private Measure districtMeasures;
    private Collection<Precinct> precinctList;
    private Geometry districtGeometry;

    public District(CensusInfo censusInfo, int districtNumber, Measure districtMeasures,
                    Collection<Precinct> precinctList, Geometry districtGeometry) {
        this.censusInfo = censusInfo;
        this.districtNumber = districtNumber;
        this.districtMeasures = districtMeasures;
        this.precinctList = precinctList;
        this.districtGeometry = districtGeometry;
    }

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

    public Geometry getDistrictGeometry() {
        return districtGeometry;
    }

    public void setDistrictGeometry(Geometry districtGeometry) {
        this.districtGeometry = districtGeometry;
    }
}
