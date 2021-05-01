package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

public class Districting {

    private CensusInfo censusInfo; //cenus info of the districting
    private ObjectiveValue districtingMeasures; //OBJ value of the districting
    private Map<Integer, District> districtsMap; // Map[districNumber]= District
    private Collection<IncumbentCandidate> protectedIncumbentCandidateList; // read in from JSON as list
    private int districtingID; //unique districting ID read from JSON
    private Map<Integer, Map<Minorities, Integer>> sortedMinorityData; //count of minority for each district
    // read in from JSON as integers


    public Districting(CensusInfo censusInfo, ObjectiveValue districtingMeasures, Map<Integer, District> districtsMap,
                       Collection<IncumbentCandidate> protectedIncumbentCandidateList, int districtingID,
                       Map<Integer, Map<Minorities, Integer>> sortedMinorityData) {
        this.censusInfo = censusInfo;
        this.districtingMeasures = districtingMeasures;
        this.districtsMap = districtsMap;
        this.protectedIncumbentCandidateList = protectedIncumbentCandidateList;
        this.districtingID = districtingID;
        this.sortedMinorityData = sortedMinorityData;
    }

    public boolean satisfyConstraints(Constraints userConstraints){
        if(userConstraints.getIncumbentsID().size() != 0){
            Collection<Integer> protectedCandidateIds = new ArrayList<>();
            // get the incumebents that are currently protected
            for(IncumbentCandidate candid: this.getProtectedIncumbentCandidateList()){
                protectedCandidateIds.add(candid.getCandidateID());
            }
            if(!Arrays.asList(protectedCandidateIds).containsAll(Arrays.asList(userConstraints))){
                //if not all specified incumbents are protected
                return false;
            }
        }
        if(userConstraints.getMajorityMinorityDistricts() > 0){
            //check if the majority minority district counts is >= to the user selected counts
            if(this.countMajorityMinorityDistrict(userConstraints) < userConstraints.getMajorityMinorityDistricts()){
                return false;
            }

        }
        if(userConstraints.getTotalPopulationEquality() > 0){
            //check if the tot pop equality is >= the user selected tot pop equ threshold
            if(this.districtingMeasures.getMeasures().get(MeasureType.TOT_POP_EQU).getMeasureScore()
                    < userConstraints.getTotalPopulationEquality()){
                return false;
            }
        }
        if(userConstraints.getVotingAgePopulationEquality() > 0){
            if(this.districtingMeasures.getMeasures().get(MeasureType.VOT_POP_EQU).getMeasureScore()
                    < userConstraints.getVotingAgePopulationEquality()){
                return false;
            }
        }
        if(userConstraints.getCitizenAgePopulationEquality() > 0){
            if(this.districtingMeasures.getMeasures().get(MeasureType.CITZEN_POP_EQU).getMeasureScore()
                    < userConstraints.getVotingAgePopulationEquality()){
                return false;
            }
        }
        if(userConstraints.getGeographicCompactness() > 0){
            if(this.districtingMeasures.getMeasures().get(MeasureType.GEOMETRIC_COMPACTNESS).getMeasureScore()
                    < userConstraints.getGeographicCompactness()){
                return false;
            }
        }
        if(userConstraints.getGraphCompactness() > 0){
            if(this.districtingMeasures.getMeasures().get(MeasureType.GRAPH_COMPACTNESS).getMeasureScore()
                    < userConstraints.getGraphCompactness()){
                return false;
            }
        }
        if(userConstraints.getPopulationFatness() > 0){
            if(this.districtingMeasures.getMeasures().get(MeasureType.POPULATION_FATNESS).getMeasureScore()
                    < userConstraints.getPopulationFatness()){
                return false;
            }
        }
        return true;
    }
    public int countMajorityMinorityDistrict(Constraints userConstraints){
        int count = 0;
        Minorities minorityLookUp = userConstraints.getMinoritySelected();
        float minorityMin = userConstraints.getMinorityThreshold();
        for(District dist: this.getDistrictsMap().values()){
            //if the minority of the specified type has percentage > the one user selected
            if(dist.getCensusInfo().getMinorities().get(minorityLookUp) > minorityMin){
                count++;
            }
        }
        return count;
    }
    public Measure calSplitCounties(){
        //optinal?
        return null;
    }

    public Measure calDevFromAvgDistGeo(Districting avgDistricting){
        //sum of square differences by area at districting level
        Measure totMeasure = this.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_GEO);
        //resetting the previous dev from Average Districting
        totMeasure.setMeasureScore(0);
        for(int districtID: this.getDistrictsMap().keySet()){
            Measure currentMeasure = this.getDistrictsMap().get(districtID)
                    .calDevFromAvgDistGeo(avgDistricting.getDistrictsMap().get(districtID));
            totMeasure.setMeasureScore(totMeasure.getMeasureScore() + currentMeasure.getMeasureScore());
        }
        return totMeasure;
    }
    public Measure calDevFromAvgDistPop(Districting avgDistricting){
        //sum of square differences by population at districting level
        Measure totMeasure = this.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_GEO);
        //resetting the previous dev from Average Districting
        totMeasure.setMeasureScore(0);
        for(int districtID: this.getDistrictsMap().keySet()){
            Measure currentMeasure = this.getDistrictsMap().get(districtID)
                    .calDevFromAvgDistPop(avgDistricting.getDistrictsMap().get(districtID));
            totMeasure.setMeasureScore(totMeasure.getMeasureScore() + currentMeasure.getMeasureScore());
        }
        return totMeasure;
    }

    public ObjectiveValue calSummaryMeasures(){
        return this.getDistrictingMeasures();
    }
    public District findDistrictByID(String districtID, EntityManager em){
        Query q = em.createNativeQuery("SELECT * FROM Diamondbacks.Districts WHERE districtID = " + districtID);
        List districtInfo = q.getResultList();
        System.out.println(districtInfo);
//        ArrayList<String> info = new ArrayList<>();
//        float devEnactedGeometric = (float) districtInfo.get(3);
//        float devEnactedPopulation = (float)
//        for (Object c : districtInfo){
//            info.add((String) c);
//        }
//        District currentDistrict = new District()
        return null;
    }


    public CensusInfo getCensusInfo() {
        return censusInfo;
    }

    public void setCensusInfo(CensusInfo censusInfo) {
        this.censusInfo = censusInfo;
    }

    public ObjectiveValue getDistrictingMeasures() {
        return districtingMeasures;
    }

    public void setDistrictingMeasures(ObjectiveValue districtingMeasures) {
        this.districtingMeasures = districtingMeasures;
    }

    public Map<Integer, District> getDistrictsMap() {
        return districtsMap;
    }

    public void setDistrictsMap(Map<Integer, District> districtsMap) {
        this.districtsMap = districtsMap;
    }

    public int getDistrictingID() {
        return districtingID;
    }

    public void setDistrictingID(int districtingID) {
        this.districtingID = districtingID;
    }

    public Collection<IncumbentCandidate> getProtectedIncumbentCandidateList() {
        return protectedIncumbentCandidateList;
    }

    public void setProtectedIncumbentCandidateList(Collection<IncumbentCandidate> protectedIncumbentCandidateList) {
        this.protectedIncumbentCandidateList = protectedIncumbentCandidateList;
    }

    public Map<Integer, Map<Minorities, Integer>> getSortedMinorityData() {
        return sortedMinorityData;
    }

    public void setSortedMinorityData(Map<Integer, Map<Minorities, Integer>> sortedMinorityData) {
        this.sortedMinorityData = sortedMinorityData;
    }
}
