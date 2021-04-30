package com.example.Diamondbacks;

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

    public void getMGGGAtt() {

    }
    public Districting calAverageDistricting(){
        return null;
    }
    public Districting getDistrictingByID(int ID){
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
    public int countRemainDistrictings(Constraints constraints){
        // write method here to count districtings that fit constraints
        return 10;
    }
    public String getJobSummary(){
        return null;
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
    public Districting findDistrictingByID(int id){
        // Implement method for use case 15
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
