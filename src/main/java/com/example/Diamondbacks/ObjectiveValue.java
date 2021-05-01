package com.example.Diamondbacks;

import java.util.Collection;
import java.util.Map;

public class ObjectiveValue {
    private float overallObjectiveValueScore; //the total object value score
    private Map<MeasureType, Measure> measures; //hashmap of all possible measure types

    /**
     * The method iterates through the measures hashmap and calculates the overall total score
     * @return overall total objective value score
     */
    public float calOverallObjectiveValueScore(){
        float total_score = 0;
        for(Measure measure:measures.values()){
            if(measure.getMeasureWeight()>0){
                total_score+= measure.getMeasureWeight()*measure.getMeasureScore();
            }
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
