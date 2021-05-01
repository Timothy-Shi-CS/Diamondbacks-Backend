package com.example.Diamondbacks;

/**
 * The Measure class encapsules the different types of measures for the district and districting
 */
public class Measure {
    private MeasureType measureType; //Type of measure
    private float measureWeight; //the weight of the measure set by the user
    private float measureScore; //the score of the measure calculated(preprocessing or on the fly)

    public Measure(MeasureType measureType, float measureWeight, float measureScore) {
        this.measureType = measureType;
        this.measureWeight = measureWeight;
        this.measureScore = measureScore;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public float getMeasureWeight() {
        return measureWeight;
    }

    public void setMeasureWeight(float measureWeight) {
        this.measureWeight = measureWeight;
    }

    public float getMeasureScore() {
        return measureScore;
    }

    public void setMeasureScore(float measureScore) {
        this.measureScore = measureScore;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "measureType=" + measureType +
                ", measureWeight=" + measureWeight +
                ", measureScore=" + measureScore +
                '}';
    }
}
