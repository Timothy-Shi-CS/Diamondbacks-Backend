package com.example.Diamondbacks;

/**
 * The Measure class encapsules the different types of measures for the district and districting
 */
public class Measure {
    private MeasureType measureType; //Type of measure
    private double measureWeight; //the weight of the measure set by the user
    private double measureScore; //the score of the measure calculated(preprocessing or on the fly)

    public Measure(MeasureType measureType, double measureWeight, double measureScore) {
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

    public double getMeasureWeight() {
        return measureWeight;
    }

    public void setMeasureWeight(float measureWeight) {
        this.measureWeight = measureWeight;
    }

    public double getMeasureScore() {
        return measureScore;
    }

    public void setMeasureScore(double measureScore) {
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
