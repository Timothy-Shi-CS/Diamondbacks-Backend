package com.example.Diamondbacks;

import java.util.Collection;

public class Constraints {
    private Collection<String> incumbentsID;
    private Minorities minoritySelected;
    private double minorityThreshold;
    private int majorityMinorityDistricts;
    private double totalPopulationEquality;
    private double votingAgePopulationEquality;
    private double citizenAgePopulationEquality;
    private double geographicCompactness;
    private double graphCompactness;
    private double populationFatness;

    public Constraints(Collection<String> incumbentsID, Minorities minoritySelected, double minorityThreshold,
                       int majorityMinorityDistricts, double totalPopulationEquality, double votingAgePopulationEquality,
                       double citizenAgePopulationEquality, double geographicCompactness, double graphCompactness,
                       double populationFatness) {
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

    public double getMinorityThreshold() {
        return minorityThreshold;
    }

    public void setMinorityThreshold(double minorityThreshold) {
        this.minorityThreshold = minorityThreshold;
    }

    public Collection<String> getIncumbentsID() {
        return incumbentsID;
    }

    public void setIncumbentsID(Collection<String> incumbentsID) {
        this.incumbentsID = incumbentsID;
    }

    public int getMajorityMinorityDistricts() {
        return majorityMinorityDistricts;
    }

    public void setMajorityMinorityDistricts(int majorityMinorityDistricts) {
        this.majorityMinorityDistricts = majorityMinorityDistricts;
    }

    public double getTotalPopulationEquality() {
        return totalPopulationEquality;
    }

    public void setTotalPopulationEquality(double totalPopulationEquality) {
        this.totalPopulationEquality = totalPopulationEquality;
    }

    public double getVotingAgePopulationEquality() {
        return votingAgePopulationEquality;
    }

    public void setVotingAgePopulationEquality(double votingAgePopulationEquality) {
        this.votingAgePopulationEquality = votingAgePopulationEquality;
    }

    public double getCitizenAgePopulationEquality() {
        return citizenAgePopulationEquality;
    }

    public void setCitizenAgePopulationEquality(double citizenAgePopulationEquality) {
        this.citizenAgePopulationEquality = citizenAgePopulationEquality;
    }

    public double getGeographicCompactness() {
        return geographicCompactness;
    }

    public void setGeographicCompactness(double geographicCompactness) {
        this.geographicCompactness = geographicCompactness;
    }

    public double getGraphCompactness() {
        return graphCompactness;
    }

    public void setGraphCompactness(double graphCompactness) {
        this.graphCompactness = graphCompactness;
    }

    public double getPopulationFatness() {
        return populationFatness;
    }

    public void setPopulationFatness(double populationFatness) {
        this.populationFatness = populationFatness;
    }
}
