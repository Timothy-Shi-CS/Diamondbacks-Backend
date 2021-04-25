package com.example.Diamondbacks;

import javax.json.Json;
import java.util.Collection;
import java.util.Map;

public class BoxAndWhisker {
    private Map<Minorities, Collection<Float>> currentDistrictingData;
    private Map<Minorities, Collection<Float>> enactedDistrictingData;
    private Minorities minorityToGraph;
    private Map<Minorities,Collection<Float>> averageMinorityMap;
    private Map<Minorities,Collection<Float>> firstQuartileMinorityMap;
    private Map<Minorities,Collection<Float>> thirdQuartileMinorityMap;
    private Map<Minorities,Collection<Float>> minMinorityMap;
    private Map<Minorities,Collection<Float>> maxMinorityMap;
    private Map<Minorities, Collection<Collection<Float>>> minorityDataMap;

    public Json generatePlot() {
        return null;
    }
    public Map<Minorities, Collection<Collection<Float>>> calMinorityDataMap(Job constrainedJob){
        return null;
    }
    public Map<Minorities,Collection<Float>> calAverageMinorityMap(){
        return null;
    }

    @Override
    public String toString() {
        return "BoxAndWhisker{" +
                "currentDistrictingData=" + currentDistrictingData +
                ", enactedDistrictingData=" + enactedDistrictingData +
                ", minorityToGraph=" + minorityToGraph +
                ", averageMinorityMap=" + averageMinorityMap +
                ", firstQuartileMinorityMap=" + firstQuartileMinorityMap +
                ", thirdQuartileMinorityMap=" + thirdQuartileMinorityMap +
                ", minMinorityMap=" + minMinorityMap +
                ", maxMinorityMap=" + maxMinorityMap +
                ", minorityDataMap=" + minorityDataMap +
                '}';
    }

    public Map<Minorities, Collection<Float>> getCurrentDistrictingData() {
        return currentDistrictingData;
    }

    public void setCurrentDistrictingData(Map<Minorities, Collection<Float>> currentDistrictingData) {
        this.currentDistrictingData = currentDistrictingData;
    }

    public Map<Minorities, Collection<Float>> getEnactedDistrictingData() {
        return enactedDistrictingData;
    }

    public void setEnactedDistrictingData(Map<Minorities, Collection<Float>> enactedDistrictingData) {
        this.enactedDistrictingData = enactedDistrictingData;
    }

    public Minorities getMinorityToGraph() {
        return minorityToGraph;
    }

    public void setMinorityToGraph(Minorities minorityToGraph) {
        this.minorityToGraph = minorityToGraph;
    }

    public Map<Minorities, Collection<Float>> getAverageMinorityMap() {
        return averageMinorityMap;
    }

    public void setAverageMinorityMap(Map<Minorities, Collection<Float>> averageMinorityMap) {
        this.averageMinorityMap = averageMinorityMap;
    }

    public Map<Minorities, Collection<Float>> getFirstQuartileMinorityMap() {
        return firstQuartileMinorityMap;
    }

    public void setFirstQuartileMinorityMap(Map<Minorities, Collection<Float>> firstQuartileMinorityMap) {
        this.firstQuartileMinorityMap = firstQuartileMinorityMap;
    }

    public Map<Minorities, Collection<Float>> getThirdQuartileMinorityMap() {
        return thirdQuartileMinorityMap;
    }

    public void setThirdQuartileMinorityMap(Map<Minorities, Collection<Float>> thirdQuartileMinorityMap) {
        this.thirdQuartileMinorityMap = thirdQuartileMinorityMap;
    }

    public Map<Minorities, Collection<Float>> getMinMinorityMap() {
        return minMinorityMap;
    }

    public void setMinMinorityMap(Map<Minorities, Collection<Float>> minMinorityMap) {
        this.minMinorityMap = minMinorityMap;
    }

    public Map<Minorities, Collection<Float>> getMaxMinorityMap() {
        return maxMinorityMap;
    }

    public void setMaxMinorityMap(Map<Minorities, Collection<Float>> maxMinorityMap) {
        this.maxMinorityMap = maxMinorityMap;
    }

    public Map<Minorities, Collection<Collection<Float>>> getMinorityDataMap() {
        return minorityDataMap;
    }

    public void setMinorityDataMap(Map<Minorities, Collection<Collection<Float>>> minorityDataMap) {
        this.minorityDataMap = minorityDataMap;
    }
}
