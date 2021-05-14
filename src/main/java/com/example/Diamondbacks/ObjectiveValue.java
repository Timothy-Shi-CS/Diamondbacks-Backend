package com.example.Diamondbacks;

import java.util.Collection;
import java.util.Map;

public class ObjectiveValue {
    private float overallObjectiveValueScore; //the total object value score
    private Map<MeasureType, Measure> measures; //hashmap of all possible measure types

    public double sigmoidFunction(Double sse){
        return 1/(1+Math.exp(Math.pow(-10,-23)*sse));
    }
    /**
     * The method iterates through the measures hashmap and calculates the overall total score
     * @return overall total objective value score
     */
    public float calOverallObjectiveValueScore(){
        float total_score = 0;
        for(MeasureType tp: this.getMeasures().keySet()){
            Measure measure = this.getMeasures().get(tp);
            if(measure.getMeasureWeight()>0){
                if(tp.equals(MeasureType.DEV_AVERAGE_GEO) || tp.equals(MeasureType.DEV_AVERAGE_POP) ||
                        tp.equals(MeasureType.DEV_ENACTED_GEO) ||tp.equals(MeasureType.DEV_ENACTED_POP)){
                    total_score+= measure.getMeasureWeight()*sigmoidFunction(measure.getMeasureScore());
                }else{
                    total_score+= measure.getMeasureWeight()*measure.getMeasureScore();
                }
            }
        }
        this.setOverallObjectiveValueScore(total_score);
        System.out.println(total_score);
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
