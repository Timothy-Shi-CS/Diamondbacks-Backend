package com.example.Diamondbacks;

import java.util.Collection;

public class ObjectiveValue {
    private float overallObjectiveValueScore;
    private Collection<Measure> measures;
    public float calOverallObjectiveValueScore(){
        return 0;
    }

    @Override
    public String toString() {
        return "ObjectiveValue{" +
                "overallObjectiveValueScore=" + overallObjectiveValueScore +
                ", measures=" + measures +
                '}';
    }

    public float getOverallObjectiveValueScore() {
        return overallObjectiveValueScore;
    }

    public void setOverallObjectiveValueScore(float overallObjectiveValueScore) {
        this.overallObjectiveValueScore = overallObjectiveValueScore;
    }

    public Collection<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(Collection<Measure> measures) {
        this.measures = measures;
    }
}
