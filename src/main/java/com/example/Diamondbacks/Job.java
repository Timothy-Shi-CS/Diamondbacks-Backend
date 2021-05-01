package com.example.Diamondbacks;

import javax.swing.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;

public class Job {
    private Collection<Districting> listDistrictings;
    private int coolingPeriod;
    private int rounds;
    private Constraints currentConstraints;
    private Districting currentAvergageDistricting;
    private int id;
    private Analysis analysis;
    private Districting currentDistricting;

    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }

    private float percentError(Float f1, Float f2){
        return Math.abs(f1-f2)/Math.abs(f2);
    }
    public Districting calAverageDistricting(BoxAndWhisker currentBAW){
        //this method is only called by the constrainted jobs object
        Minorities minoritySelected= this.getCurrentConstraints().getMinoritySelected();
        Map<Integer, Float> currentAverages = currentBAW.getAverageMinorityData();
        int num_districts = currentAverages.keySet().size();
        int start = 0;
        int mid = num_districts/2;
        int end = num_districts-1;
        Districting averagedDistricting = null;

        Float start_avg = currentAverages.get(start);
        Float mid_avg = currentAverages.get(mid);
        Float end_avg = currentAverages.get(end);
        float threshold = 0.05f;
        for(Districting dist: this.getListDistrictings()) {
            Float start_val = dist.getSortedMinorityData().get(start).get(minoritySelected);
            Float mid_val = dist.getSortedMinorityData().get(mid).get(minoritySelected);
            Float end_val = dist.getSortedMinorityData().get(end).get(minoritySelected);
            if(percentError(start_avg, start_val)<threshold
                    && percentError(mid_avg, mid_val)<threshold
                    && percentError(mid_avg, mid_val)<threshold){
                averagedDistricting = dist;
                break;
            }
        }
        return averagedDistricting;
    }
    public Districting getDistrictingByID(String id){
        return null;
    }
    public Collection<Float> getAvgDistrictingMinorityStats(String minority){
        return null;
    }
    public Collection<Districting> getDevFromAvgDistrictingGeometric(){
        return null;
    }
    public Collection<Districting> filterJobByConstraint(Constraints constraints){
        return null;
    }

    public int countRemainDistrictings(){
        // write method here to count districtings that fit constraints
        int count = 0;
        for(Districting dist: this.getListDistrictings()){
            if(dist.satisfyConstraints(this.getCurrentConstraints())){
                count++;
            }
        }
        return count;
    }
    public String getJobSummary(){
        String jobSummary = "Cooling Period:" + this.getCoolingPeriod() + "Rounds:" + this.getRounds();
        return jobSummary;
    }

    public String getPrelimAnalysis(){
        return null;
    }
    public Map<Integer, Float> getTopDistrictingsObjectFunction(){
        return null;
    }
    public Map<Integer, Float> getTopDistrictingsByDeviationFromEnacted(){
        return null;
    }
    public Map<Integer, Float> getDistrictingsByMajorMinorityRange(){
        return null;
    }
    public Map<Integer, Float> getVeryDifferentAreaPairDeviations(){
        return null;
    }
    public float calMajMinDevFromAvg(Map<Integer, Float> map){
        return 0;
    }
    public Districting findDistrictingByID(String id, EntityManager em){
        // Implement method for use case 15
        String queryString = "SELECT * FROM Diamondbacks.Districtings WHERE recomb_file = " + id + ".json";
        Query q = em.createNativeQuery(queryString);
        System.out.println(q.getResultList());
        return null;
    }

    @Override
    public String toString() {
        return "Job{" +
                "listDistrictings=" + listDistrictings +
                ", coolingPeriod=" + coolingPeriod +
                ", rounds=" + rounds +
                ", currentConstraints=" + currentConstraints +
                ", currentAvergageDistricting=" + currentAvergageDistricting +
                ", id=" + id +
                ", analysis=" + analysis +
                '}';
    }

    public Collection<Districting> getListDistrictings() {
        return listDistrictings;
    }

    public void setListDistrictings(Collection<Districting> listDistrictings) {
        this.listDistrictings = listDistrictings;
    }

    public int getCoolingPeriod() {
        return coolingPeriod;
    }

    public void setCoolingPeriod(int coolingPeriod) {
        this.coolingPeriod = coolingPeriod;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public Constraints getCurrentConstraints() {
        return currentConstraints;
    }

    public void setCurrentConstraints(Constraints currentConstraints) {
        this.currentConstraints = currentConstraints;
    }

    public Districting getCurrentAvergageDistricting() {
        return currentAvergageDistricting;
    }

    public void setCurrentAvergageDistricting(Districting currentAvergageDistricting) {
        this.currentAvergageDistricting = currentAvergageDistricting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}
