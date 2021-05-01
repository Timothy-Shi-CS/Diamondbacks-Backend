package com.example.Diamondbacks;

import javax.swing.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Array;
import java.util.*;

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

    private float percentError(Integer f1, Integer f2){
        return Math.abs((float)f1-(float)f2)/Math.abs((float)f2);
    }
    public Districting calAverageDistricting(BoxAndWhisker currentBAW){
        //this method is only called by the constrainted jobs object
        Minorities minoritySelected= this.getCurrentConstraints().getMinoritySelected();
        Map<Integer, Integer> currentAverages = currentBAW.getAverageMinorityData();
        int num_districts = currentAverages.keySet().size();
        int start = 0;
        int mid = num_districts/2;
        int end = num_districts-1;
        Districting averagedDistricting = null;

        Integer start_avg = currentAverages.get(start);
        Integer mid_avg = currentAverages.get(mid);
        Integer end_avg = currentAverages.get(end);
        float threshold = 0.05f;
        for(Districting dist: this.getListDistrictings()) {
            Integer start_val = dist.getSortedMinorityData().get(start).get(minoritySelected);
            Integer mid_val = dist.getSortedMinorityData().get(mid).get(minoritySelected);
            Integer end_val = dist.getSortedMinorityData().get(end).get(minoritySelected);
            if(percentError(start_avg, start_val)<threshold
                    && percentError(mid_avg, mid_val)<threshold
                    && percentError(end_avg, end_val)<threshold){
                averagedDistricting = dist;
                break;
            }
        }
        //sets the current average districting after finding a good fit
        this.setCurrentAvergageDistricting(averagedDistricting);
        //calculates the deviation from average disitrciting for the constrainted job
        this.calDeviationFromAvgDistricting();
        return averagedDistricting;
    }
    public void calDeviationFromAvgDistricting(){
        //this method is only called by the constrained jobs
        for(Districting dist: this.getListDistrictings()){
            dist.calDevFromAvgDistGeo(this.getCurrentAvergageDistricting());
            dist.calDevFromAvgDistPop(this.getCurrentAvergageDistricting());
        }
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

    public Collection<Districting> getTopDistrictingsObjectFunction(){
        //sort the reamining districting by objective function value
        //returns top5 or top 10 districting by objective function value
        //make sure it is sorted from high to low !!!
        Comparator<Districting> objValComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float objValue1 = d1.getDistrictingMeasures().getOverallObjectiveValueScore();
                float objValue2 = d2.getDistrictingMeasures().getOverallObjectiveValueScore();
                return Float.compare(objValue1, objValue2);
            }
        };
        //sort by objective function value
        List<Districting> districtings = (List<Districting>)this.getListDistrictings();
        districtings.sort(objValComparator);
        //get the top 10 from the sorted
        return districtings.subList(0,10);
    }
    public Districting getTopDistrictingsByDeviationFromEnactedGeo(){
        //sort the remaining districting by deviation from enacted
        //return the maximum deviation from enacted by geometric
        //make sure it is sorted from high to low !!!
        Comparator<Districting> devEnactedAreaComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float objValue1 = d1.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_GEO).getMeasureScore();
                float objValue2 = d2.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_GEO).getMeasureScore();
                return Float.compare(objValue1, objValue2);
            }
        };
        //sort by objective function value
        List<Districting> districtings = (List<Districting>)this.getListDistrictings();
        districtings.sort(devEnactedAreaComparator);
        return districtings.get(0);
    }
    public Districting getTopDistrictingsByDeviationFromEnactedPop(){
        //sort the remaining districting by deviation from enacted
        //return the maximum deviation from enacted by population
        //make sure it is sorted from high to low !!!
        Comparator<Districting> devEnactedPopulationComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float objValue1 = d1.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_POP).getMeasureScore();
                float objValue2 = d2.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_POP).getMeasureScore();
                return Float.compare(objValue1, objValue2);
            }
        };
        //sort by objective function value
        List<Districting> districtings = (List<Districting>)this.getListDistrictings();
        districtings.sort(devEnactedPopulationComparator);
        return districtings.get(0);
    }

    public Collection<Districting> getVeryDifferentAreaPairDeviations(){
        //this should return it in pairs?
        //pick a district, then sort the list by the area of that list
        //return the min and max of that list(very different area in terms of the district selected)
        Integer districtToCompareArea = 1;
        Comparator<Districting> areaComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float area1 = d1.getDistrictsMap().get(districtToCompareArea).getDistrictGeometry().getArea();
                float area2 = d2.getDistrictsMap().get(districtToCompareArea).getDistrictGeometry().getArea();
                return Float.compare(area1, area2);
            }
        };

        //sort by objective function value
        List<Districting> districtings = (List<Districting>)this.getListDistrictings();
        districtings.sort(areaComparator);
        List<Districting> result = new ArrayList<Districting>();
        //the first element and the last element have very different area
        result.add(districtings.get(0));
        result.add(districtings.get(districtings.size()-1));
        return result;
    }

    public Collection<Districting> getVeryDifferentPopulationPairDeviations(){
        //this should return it in pairs?
        //pick a district, then sort the list by the population of that list
        //return the min and max of that list(very different population in terms of the district selected)
        Integer districtToCompareArea = 1;
        Comparator<Districting> populationComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float population1 = d1.getDistrictsMap().get(districtToCompareArea).getCensusInfo().getPopulationData()
                        .get(CensusValues.TOTAL_POPULATION);
                float population2 = d2.getDistrictsMap().get(districtToCompareArea).getCensusInfo().getPopulationData()
                        .get(CensusValues.TOTAL_POPULATION);
                return Float.compare(population1, population2);
            }
        };

        //sort by objective function value
        List<Districting> districtings = (List<Districting>)this.getListDistrictings();
        districtings.sort(populationComparator);
        List<Districting> result = new ArrayList<Districting>();
        //the first element and the last element have very different population
        result.add(districtings.get(0));
        result.add(districtings.get(districtings.size()-1));
        return result;
    }

    public Map<Integer, Float> getDistrictingsByMajorMinorityRange(){
        //shouldn't this be all districtings? since it is being constrainted by this?
        return null;
    }
    public float calMajMinDevFromAvg(Map<Integer, Float> map){
        //what is this???
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
