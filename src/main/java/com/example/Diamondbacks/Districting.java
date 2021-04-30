package com.example.Diamondbacks;

import java.util.Collection;
import java.util.Map;

public class Districting {

    private CensusInfo censusInfo;
    private ObjectiveValue districtingMeasures;
    private Map<Integer, District> districtsMap;
    private Collection<IncumbentCandidate> protectedIncumbentCandidateList;
    private int districtingID;

    public Districting(CensusInfo censusInfo, ObjectiveValue districtingMeasures, Map<Integer, District> districtsMap,
                       Collection<IncumbentCandidate> protectedIncumbentCandidateList, int districtingID) {
        this.censusInfo = censusInfo;
        this.districtingMeasures = districtingMeasures;
        this.districtsMap = districtsMap;
        this.protectedIncumbentCandidateList = protectedIncumbentCandidateList;
        this.districtingID = districtingID;
    }

    public boolean satisfyConstraints(Constraints userConstraints){
//        this.incumbentsID = incumbentsID;

        if(userConstraints.getIncumbentsID().size() != 0){
            //check if the incumebents are protected
            return false;
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

    public Measure calDevFromAvgDistGeo(){
        return null;
    }
    public Measure calDevFromAvgDistPop(){
        return null;
    }

    public ObjectiveValue calSummaryMeasures(){
        return this.getDistrictingMeasures();
    }
    public District findDistrictByID(int districtID){
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
}
