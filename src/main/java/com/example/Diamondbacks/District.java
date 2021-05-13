package com.example.Diamondbacks;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Entity
@Table(name="Districts")
public class District {
    @Transient
    private CensusInfo censusInfo; //the overall population and minority counts

    @Column(name="districtNumber",nullable = false)
    private int districtNumber; //the district number

    @Id
    private String districtID;

    @Transient
    private ObjectiveValue districtMeasures; //OBJ value of the districting

    @Transient
    private Collection<String> precinctList; //the list of preincts within this district

    @Transient
    private Geometry districtGeometry; // the geometry of the district

    @Column(name="ASIAN",nullable = false)
    private Double ASIAN;

    @Column(name="BLACK",nullable = false)
    private Double BLACK;

    @Column(name="HISPANIC",nullable = false)
    private Double HISPANIC;

    @ManyToOne
    private Districting districting;

    public District() {

    }

    /**
     * This method calculates the sum of square difference from the average district by area
     *
     * @param avgDistrict the district from average districting with corresponding districtID
     * @return the sum of square difference from the average district by area
     */
    public Measure calDevFromAvgDistGeo(District avgDistrict) {
        //sum of square differences by area by district
        Measure currentMeasure = this.getDistrictMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_GEO);
        float avgArea = avgDistrict.getDistrictGeometry().getArea();
        float recombArea = this.getDistrictGeometry().getArea();
        float sse = (float) Math.pow(avgArea - recombArea, 2);
        currentMeasure.setMeasureScore(sse);
        return currentMeasure;
    }

    /**
     * This method calculates the sum of square difference from the average district by population
     *
     * @param avgDistrict the district from average districting with corresponding districtID
     * @return the sum of square difference from the average district by population
     */
    public Measure calDevFromAvgDistPop(District avgDistrict) {
        //sum of square differences by population by district
        Measure currentMeasure = this.getDistrictMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_POP);
        float avgPop = avgDistrict.getCensusInfo().getPopulationData().get(CensusValues.TOTAL_POPULATION);
        float recombPop = this.getCensusInfo().getPopulationData().get(CensusValues.TOTAL_POPULATION);
        float sse = (float) Math.pow(avgPop - recombPop, 2);
        currentMeasure.setMeasureScore(sse);
        return currentMeasure;
    }


    public Measure calSplitCounties() {
        return null;
    }

    public Measure calPoliticalFairness() {
        return null;
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

    public Collection<String> getPrecinctList() {
        return precinctList;
    }

    public void setPrecinctList(Collection<String> precinctList) {
        this.precinctList = precinctList;
    }

    public Geometry getDistrictGeometry() {
        return districtGeometry;
    }

    public void setDistrictGeometry(Geometry districtGeometry) {
        this.districtGeometry = districtGeometry;
    }

    public Double getASIAN() {
        return ASIAN;
    }

    public Double getBLACK() {
        return BLACK;
    }

    public Double getHISPANIC() {
        return HISPANIC;
    }

    public void setASIAN(Double ASIAN) {
        this.ASIAN = ASIAN;
    }

    public void setBLACK(Double BLACK) {
        this.BLACK = BLACK;
    }

    public void setHISPANIC(Double HISPANIC) {
        this.HISPANIC = HISPANIC;
    }

    public Districting getDistricting() {
        return districting;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public void setDistricting(Districting districting) {
        this.districting = districting;
    }

    public String getDistrictID() {
        return districtID;
    }
}
