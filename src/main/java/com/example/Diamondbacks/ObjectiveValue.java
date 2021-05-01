package com.example.Diamondbacks;

import java.util.Collection;
import java.util.Map;

public class ObjectiveValue {
    private float overallObjectiveValueScore;
    private Map<MeasureType, Measure> measures;

    public float calOverallObjectiveValueScore(){
        float total_score = 0;
        for(Measure measure:measures.values()){
            total_score+= measure.getMeasureWeight()*measure.getMeasureScore();
        }
        this.setOverallObjectiveValueScore(total_score);
        return total_score;
    }

    public float getOverallObjectiveValueScore() {
        return overallObjectiveValueScore;
    }

    public void setOverallObjectiveValueScore(float overallObjectiveValueScore) {
        this.overallObjectiveValueScore = overallObjectiveValueScore;
    }

    public Map<MeasureType, Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(Map<MeasureType, Measure> measures) {
        this.measures = measures;
    }
}
