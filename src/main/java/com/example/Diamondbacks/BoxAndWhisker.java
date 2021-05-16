package com.example.Diamondbacks;

import javax.json.Json;
import java.util.*;

public class BoxAndWhisker {
    private Map<Integer, Integer> currentDistrictingData;
    private Map<Integer, Integer> enactedDistrictingData;
    private Map<Integer, Integer> averageMinorityData;
//    private Map<Integer, Integer> firstQuartileMinorityData;
//    private Map<Integer, Integer> thirdQuartileMinorityData;
//    private Map<Integer, Integer> minMinorityData;
//    private Map<Integer, Integer> maxMinorityData;
    private Map<Integer, Collection<Integer>> minorityData;



    public Json generatePlot() {
        return null;
    }

    public Map<Minorities, Collection<Collection<Float>>> calMinorityDataMap(Job constrainedJob) {
        return null;
    }

    public Map<Minorities, Collection<Float>> calAverageMinorityMap() {
        return null;
    }

    public BoxAndWhisker() {

    }

    public BoxAndWhisker(Map<Integer, Integer> currentDistrictingData, Map<Integer, Integer> enactedDistrictingData,
                         Map<Integer, Collection<Integer>> minorityData) {
        this.currentDistrictingData = currentDistrictingData;
        this.enactedDistrictingData = enactedDistrictingData;
        this.minorityData = minorityData;
        this.calculateBoxAndWhiskerData();
    }

    public void calculateBoxAndWhiskerData() {
        Comparator<Integer> IntegerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        this.averageMinorityData = new HashMap<>();
//        this.firstQuartileMinorityData = new HashMap<>();
//        this.thirdQuartileMinorityData = new HashMap<>();
//        this.minMinorityData = new HashMap<>();
//        this.maxMinorityData = new HashMap<>();

        //get min, max, q1, q2, avg data for each district
        for (Integer districtID : this.getMinorityData().keySet()) {
            List<Integer> districtData = (List<Integer>) this.getMinorityData().get(districtID);
            districtData.sort(IntegerComparator);
//            Double tmp = districtData.stream().mapToDouble(val -> val).average().orElse(0.0);
//            Float avg_data = tmp.floatValue();
//            Integer min_data = districtData.get(0);
//            Integer q1_data = districtData.get((districtData.size() / 4));
            Integer avg_data = districtData.get(2 * (districtData.size() / 4));
//            Integer q3_data = districtData.get(3 * (districtData.size() / 4));
//            Integer max_data = districtData.get(districtData.size() - 1);

//            this.firstQuartileMinorityData.put(districtID, q1_data);
            this.averageMinorityData.put(districtID, avg_data);
//            this.thirdQuartileMinorityData.put(districtID, q3_data);
//            this.minMinorityData.put(districtID, min_data);
//            this.maxMinorityData.put(districtID, max_data);
        }
    }

    public Map<Integer, Integer> getAverageMinorityData() {
        return averageMinorityData;
    }

    public Map<Integer, Collection<Integer>> getMinorityData() {
        return minorityData;
    }

    public Map<Integer, Integer> getCurrentDistrictingData() {
        return currentDistrictingData;
    }

    public void setCurrentDistrictingData(Map<Integer, Integer> currentDistrictingData) {
        this.currentDistrictingData = currentDistrictingData;
    }

    public Map<Integer, Integer> getEnactedDistrictingData() {
        return enactedDistrictingData;
    }

    public void setEnactedDistrictingData(Map<Integer, Integer> enactedDistrictingData) {
        this.enactedDistrictingData = enactedDistrictingData;
    }

    public void setMinorityData(Map<Integer, Collection<Integer>> minorityData) {
        this.minorityData = minorityData;
    }


}
