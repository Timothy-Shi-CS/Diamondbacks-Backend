package com.example.Diamondbacks;

import javax.json.Json;
import java.util.*;

public class BoxAndWhisker {
    private Map<Integer, Float> currentDistrictingData;
    private Map<Integer, Float> enactedDistrictingData;
    private Map<Integer, Float> averageMinorityData;
    private Map<Integer, Float> firstQuartileMinorityData;
    private Map<Integer, Float> thirdQuartileMinorityData;
    private Map<Integer, Float> minMinorityData;
    private Map<Integer, Float> maxMinorityData;
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

    public BoxAndWhisker(Map<Integer, Float> currentDistrictingData, Map<Integer, Float> enactedDistrictingData,
                         Map<Integer, Collection<Float>> minorityData) {
        this.currentDistrictingData = currentDistrictingData;
        this.enactedDistrictingData = enactedDistrictingData;
        this.minorityData = minorityData;
        this.calculateBoxAndWhiskerData();
    }
    public void calculateBoxAndWhiskerData(){
        Comparator<Float> defaultComparator = new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return Float.compare(o1, o2);
            }
        };

        this.averageMinorityData = new HashMap<>();
        this.firstQuartileMinorityData = new HashMap<>();
        this.thirdQuartileMinorityData = new HashMap<>();
        this.minMinorityData = new HashMap<>();
        this.maxMinorityData = new HashMap<>();

        //get min, max, q1, q2, avg data for each district
        for(Integer districtID: this.getMinorityData().keySet()){
            List<Float> districtData = (List<Float>)this.getMinorityData().get(districtID);
            districtData.sort(defaultComparator);
//            Double tmp = districtData.stream().mapToDouble(val -> val).average().orElse(0.0);
//            Float avg_data = tmp.floatValue();
            Float min_data = districtData.get(0);
            Float q1_data = districtData.get((districtData.size()/4));
            Float avg_data = districtData.get(2*(districtData.size()/4));
            Float q3_data = districtData.get(3*(districtData.size()/4));
            Float max_data = districtData.get(districtData.size()-1);

            this.firstQuartileMinorityData.put(districtID, q1_data);
            this.averageMinorityData.put(districtID, avg_data);
            this.thirdQuartileMinorityData.put(districtID, q3_data);
            this.minMinorityData.put(districtID, min_data);
            this.maxMinorityData.put(districtID, max_data);
        }
    }

    public Map<Integer, Collection<Float>> getMinorityData() {
        return minorityData;
    }

    public void setMinorityData(Map<Integer, Collection<Float>> minorityData) {
        this.minorityData = minorityData;
    }
}
