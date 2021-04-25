package com.example.Diamondbacks;

public class Measure {
    private MeasureType measureType;
    private float measureWeight;
    private float measureScore;

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
