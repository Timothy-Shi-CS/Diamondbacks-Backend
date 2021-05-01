package com.example.Diamondbacks;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class District {
    private CensusInfo censusInfo;
    private int districtNumber;
    private ObjectiveValue districtMeasures;
    private Collection<Precinct> precinctList;
    private Geometry districtGeometry;

    public District(CensusInfo censusInfo, int districtNumber, ObjectiveValue districtMeasures,
                    Collection<Precinct> precinctList, Geometry districtGeometry) {
        this.censusInfo = censusInfo;
        this.districtNumber = districtNumber;
        this.districtMeasures = districtMeasures;
        this.precinctList = precinctList;
        this.districtGeometry = districtGeometry;
    }

    public Measure calDevFromAvgDistGeo(District avgDistrict){
        //sum of square differences by area by district
        Measure currentMeasure = this.getDistrictMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_GEO);
        float avgArea = avgDistrict.getDistrictGeometry().getArea();
        float recombArea = this.getDistrictGeometry().getArea();
        float sse = (float) Math.pow(avgArea-recombArea,2);
        currentMeasure.setMeasureScore(sse);
        return currentMeasure;
    }
    public Measure calDevFromAvgDistPop(District avgDistrict){
        //sum of square differences by population by district
        Measure currentMeasure = this.getDistrictMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_POP);
        float avgPop = avgDistrict.getCensusInfo().getPopulationData().get(CensusValues.TOTAL_POPULATION);
        float recombPop = this.getCensusInfo().getPopulationData().get(CensusValues.TOTAL_POPULATION);
        float sse = (float) Math.pow(avgPop-recombPop, 2);
        currentMeasure.setMeasureScore(sse);
        return currentMeasure;
    }

    public Geometry calGeometry(){
        return null;
    }
    public Measure calSplitCounties(){
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

    public ObjectiveValue getDistrictMeasures() {
        return districtMeasures;
    }

    public void setDistrictMeasures(ObjectiveValue districtMeasures) {
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
