package com.example.Diamondbacks;

import java.util.Collection;

public class Precinct {
    private CensusInfo censusInfo;
    private String precinctName;
    private int enactedDistrict;
    private IncumbentCandidate incumbent;
    private Collection<Precinct> neighborPrecincts;
    private Geometry precinctGeometry;

    public float calArea(){
        return 0;
    }
    public float calPerimeter(){
        return 0;
    }

    @Override
    public String toString() {
        return "Precinct{" +
                "censusInfo=" + censusInfo +
                ", precinctName='" + precinctName + '\'' +
                ", enactedDistrict=" + enactedDistrict +
                ", incumbent=" + incumbent +
                ", neighborPrecincts=" + neighborPrecincts +
                ", precinctGeometry=" + precinctGeometry +
                '}';
    }

    public CensusInfo getCensusInfo() {
        return censusInfo;
    }

    public void setCensusInfo(CensusInfo censusInfo) {
        this.censusInfo = censusInfo;
    }

    public String getPrecinctName() {
        return precinctName;
    }

    public void setPrecinctName(String precinctName) {
        this.precinctName = precinctName;
    }

    public int getEnactedDistrict() {
        return enactedDistrict;
    }

    public void setEnactedDistrict(int enactedDistrict) {
        this.enactedDistrict = enactedDistrict;
    }

    public IncumbentCandidate getIncumbent() {
        return incumbent;
    }

    public void setIncumbent(IncumbentCandidate incumbent) {
        this.incumbent = incumbent;
    }

    public Collection<Precinct> getNeighborPrecincts() {
        return neighborPrecincts;
    }

    public void setNeighborPrecincts(Collection<Precinct> neighborPrecincts) {
        this.neighborPrecincts = neighborPrecincts;
    }

    public Geometry getPrecinctGeometry() {
        return precinctGeometry;
    }

    public void setPrecinctGeometry(Geometry precinctGeometry) {
        this.precinctGeometry = precinctGeometry;
    }
}
