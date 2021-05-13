package com.example.Diamondbacks;

import java.util.Map;

public class CensusInfo {
    private Map<CensusValues, Integer> populationData;
    private Map<Minorities, Double> minorities; // percent of minorities in each district

    public CensusInfo(Map<CensusValues, Integer> populationData, Map<Minorities, Double> minorities) {
        this.populationData = populationData;
        this.minorities = minorities;
    }

    public CensusInfo(){

    }

    public Map<CensusValues, Integer> getPopulationData() {
        return populationData;
    }

    public void setPopulationData(Map<CensusValues, Integer> populationData) {
        this.populationData = populationData;
    }

    public Map<Minorities, Double> getMinorities() {
        return minorities;
    }

    public void setMinorities(Map<Minorities, Double> minorities) {
        this.minorities = minorities;
    }
}
