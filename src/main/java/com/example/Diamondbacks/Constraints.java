package com.example.Diamondbacks;

import java.util.Collection;

public class Constraints {
    private Collection<Integer> incumbentsID;
    private int majorityMinorityDistricts;
    private float totalPopulation;
    private float votingAgePopulation;
    private float citizenAgePopulation;
    private float geographicCompactness;
    private float graphCompactness;
    private float populationFatness;

    public Constraints(Collection<Integer> incumbentsID, int majorityMinorityDistricts, float totalPopulation, float votingAgePopulation, float citizenAgePopulation, float geographicCompactness, float graphCompactness, float populationFatness) {
        this.incumbentsID = incumbentsID;
        this.majorityMinorityDistricts = majorityMinorityDistricts;
        this.totalPopulation = totalPopulation;
        this.votingAgePopulation = votingAgePopulation;
        this.citizenAgePopulation = citizenAgePopulation;
        this.geographicCompactness = geographicCompactness;
        this.graphCompactness = graphCompactness;
        this.populationFatness = populationFatness;
    }

    @Override
    public String toString() {
        return "Constraints{" +
                "incumbentsID=" + incumbentsID +
                ", majorityMinorityDistricts=" + majorityMinorityDistricts +
                ", totalPopulation=" + totalPopulation +
                ", votingAgePopulation=" + votingAgePopulation +
                ", citizenAgePopulation=" + citizenAgePopulation +
                ", geographicCompactness=" + geographicCompactness +
                ", graphCompactness=" + graphCompactness +
                ", populationFatness=" + populationFatness +
                '}';
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

    public float getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(float totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public float getVotingAgePopulation() {
        return votingAgePopulation;
    }

    public void setVotingAgePopulation(float votingAgePopulation) {
        this.votingAgePopulation = votingAgePopulation;
    }

    public float getCitizenAgePopulation() {
        return citizenAgePopulation;
    }

    public void setCitizenAgePopulation(float citizenAgePopulation) {
        this.citizenAgePopulation = citizenAgePopulation;
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
