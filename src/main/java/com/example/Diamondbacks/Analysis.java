package com.example.Diamondbacks;

import java.util.Collection;
import java.util.Map;

public class Analysis {
    private float maxObjectiveFunctionValue;
    private int numDistrictingCloseToEnacted;
    private int numDistrictingMajMinWithinRange;
    private float maxAreaPairDeviationValue;
    private String summary;

    public void calMaxObjectiveFunctionValue(Job job){

    }
    public void calNumDistrictingCloseToEnacted(Job job){

    }
    public void calNumDistrictingMajMinWithinRange(Job job){

    }
    public void calMaxAreaPairDeviationValue(Job job){

    }
    public Map<Minorities, Collection<Float>> calcAverages(Job job){
        return null;
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "maxObjectiveFunctionValue=" + maxObjectiveFunctionValue +
                ", numDistrictingCloseToEnacted=" + numDistrictingCloseToEnacted +
                ", numDistrictingMajMinWithinRange=" + numDistrictingMajMinWithinRange +
                ", maxAreaPairDeviationValue=" + maxAreaPairDeviationValue +
                ", summary='" + summary + '\'' +
                '}';
    }

    public float getMaxObjectiveFunctionValue() {
        return maxObjectiveFunctionValue;
    }

    public void setMaxObjectiveFunctionValue(float maxObjectiveFunctionValue) {
        this.maxObjectiveFunctionValue = maxObjectiveFunctionValue;
    }

    public int getNumDistrictingCloseToEnacted() {
        return numDistrictingCloseToEnacted;
    }

    public void setNumDistrictingCloseToEnacted(int numDistrictingCloseToEnacted) {
        this.numDistrictingCloseToEnacted = numDistrictingCloseToEnacted;
    }

    public int getNumDistrictingMajMinWithinRange() {
        return numDistrictingMajMinWithinRange;
    }

    public void setNumDistrictingMajMinWithinRange(int numDistrictingMajMinWithinRange) {
        this.numDistrictingMajMinWithinRange = numDistrictingMajMinWithinRange;
    }

    public float getMaxAreaPairDeviationValue() {
        return maxAreaPairDeviationValue;
    }

    public void setMaxAreaPairDeviationValue(float maxAreaPairDeviationValue) {
        this.maxAreaPairDeviationValue = maxAreaPairDeviationValue;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary(){
        return summary;
    }
}
