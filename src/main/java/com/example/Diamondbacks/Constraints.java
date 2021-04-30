package com.example.Diamondbacks;

import java.util.Collection;

public class Constraints {
    private Collection<Integer> incumbentsID;
    private Minorities minoritySelected;
    private float minorityThreshold;
    private int majorityMinorityDistricts;
    private float totalPopulationEquality;
    private float votingAgePopulationEquality;
    private float citizenAgePopulationEquality;
    private float geographicCompactness;
    private float graphCompactness;
    private float populationFatness;

    public Constraints(Collection<Integer> incumbentsID, Minorities minoritySelected, float minorityThreshold,
                       int majorityMinorityDistricts, float totalPopulationEquality, float votingAgePopulationEquality,
                       float citizenAgePopulationEquality, float geographicCompactness, float graphCompactness,
                       float populationFatness) {
        this.incumbentsID = incumbentsID;
        this.minoritySelected = minoritySelected;
        this.minorityThreshold = minorityThreshold;
        this.majorityMinorityDistricts = majorityMinorityDistricts;
        this.totalPopulationEquality = totalPopulationEquality;
        this.votingAgePopulationEquality = votingAgePopulationEquality;
        this.citizenAgePopulationEquality = citizenAgePopulationEquality;
        this.geographicCompactness = geographicCompactness;
        this.graphCompactness = graphCompactness;
        this.populationFatness = populationFatness;
    }

    public Minorities getMinoritySelected() {
        return minoritySelected;
    }

    public void setMinoritySelected(Minorities minoritySelected) {
        this.minoritySelected = minoritySelected;
    }

    public float getMinorityThreshold() {
        return minorityThreshold;
    }

    public void setMinorityThreshold(float minorityThreshold) {
        this.minorityThreshold = minorityThreshold;
    }

    public Collection<Integer> getIncumbentsID() {
        return incumbentsID;
    }

    public void setIncumbentsID(Collection<Integer> incumbentsID) {
        this.incumbentsID = incumbentsID;
    }

    public int getMajorityMinorityDistricts() {
        return majorityMinorityDistricts;
    }

    public void setMajorityMinorityDistricts(int majorityMinorityDistricts) {
        this.majorityMinorityDistricts = majorityMinorityDistricts;
    }

    public float getTotalPopulationEquality() {
        return totalPopulationEquality;
    }

    public void setTotalPopulationEquality(float totalPopulationEquality) {
        this.totalPopulationEquality = totalPopulationEquality;
    }

    public float getVotingAgePopulationEquality() {
        return votingAgePopulationEquality;
    }

    public void setVotingAgePopulationEquality(float votingAgePopulationEquality) {
        this.votingAgePopulationEquality = votingAgePopulationEquality;
    }

    public float getCitizenAgePopulationEquality() {
        return citizenAgePopulationEquality;
    }

    public void setCitizenAgePopulationEquality(float citizenAgePopulationEquality) {
        this.citizenAgePopulationEquality = citizenAgePopulationEquality;
    }

    public float getGeographicCompactness() {
        return geographicCompactness;
    }

    public void setGeographicCompactness(float geographicCompactness) {
        this.geographicCompactness = geographicCompactness;
    }

    public float getGraphCompactness() {
        return graphCompactness;
    }

    public void setGraphCompactness(float graphCompactness) {
        this.graphCompactness = graphCompactness;
    }

    public float getPopulationFatness() {
        return populationFatness;
    }

    public void setPopulationFatness(float populationFatness) {
        this.populationFatness = populationFatness;
    }
}
