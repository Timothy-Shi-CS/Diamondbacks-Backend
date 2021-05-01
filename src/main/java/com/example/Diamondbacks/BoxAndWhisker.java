package com.example.Diamondbacks;

import javax.json.Json;
import java.util.Collection;
import java.util.Map;

public class BoxAndWhisker {
    private Map<Integer, Float> currentDistrictingData;
    private Map<Integer, Float> enactedDistrictingData;
    private Map<Integer,Float> averageMinorityData;
    private Map<Integer,Float> firstQuartileMinorityData;
    private Map<Integer,Float> thirdQuartileMinorityData;
    private Map<Integer,Float> minMinorityData;
    private Map<Integer,Float> maxMinorityData;
    private Map<Integer, Collection<Float>> minorityData;

    public Json generatePlot() {
        return null;
    }
    public Map<Minorities, Collection<Collection<Float>>> calMinorityDataMap(Job constrainedJob){
        return null;
    }
    public Map<Minorities,Collection<Float>> calAverageMinorityMap(){
        return null;
    }


}
