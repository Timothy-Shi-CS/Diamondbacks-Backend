package com.example.Diamondbacks;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Districtings")
public class Districting {

    @Id
    private String recomb_file;

    @ManyToOne
    private Job job;

    @Transient
    private String state;

    @Column(name = "vot_pop_equality", nullable = false)
    private float vot_pop_equality;

    @Column(name = "dev_enacted_geometric", nullable = false)
    private float dev_enacted_geometric;

    @Column(name = "dev_enacted_population", nullable = false)
    private float dev_enacted_population;

    @Column(name = "dev_avg_geometric", nullable = false)
    private String dev_avg_geometric;

    @Column(name = "dev_avg_population", nullable = false)
    private String dev_avg_population;

    @Column(name = "geographic_comp", nullable = false)
    private float geographic_comp;

    @Column(name = "graph_comp", nullable = false)
    private float graph_comp;

    @Column(name = "population_fatness", nullable = false)
    private float population_fatness;

    @Column(name = "cal_pop_diff_percent", nullable = false)
    private float cal_pop_diff_percent;

    @Column(name = "tot_pop_equality", nullable = false)
    private float tot_pop_equality;

    @Transient
    private CensusInfo censusInfo; //cenus info of the districting

    @Transient
    private ObjectiveValue districtingMeasures; //OBJ value of the districting

    @Transient
    private Map<Integer, District> districtsMap; // Map[districNumber]= District

    @Transient
    private Collection<Incumbent> protectedIncumbentCandidateList; // read in from JSON as list
//    private int districtingID; //unique districting ID read from JSON

    @Transient
    private Map<Integer, Map<Minorities, Integer>> sortedMinorityData; //count of minority for each district
    // read in from JSON as integers


    public Districting(CensusInfo censusInfo, ObjectiveValue districtingMeasures, Map<Integer, District> districtsMap,
                       Collection<Incumbent> protectedIncumbentCandidateList, String districtingID,
                       Map<Integer, Map<Minorities, Integer>> sortedMinorityData) {
        this.censusInfo = censusInfo;
        this.districtingMeasures = districtingMeasures;
        this.districtsMap = districtsMap;
        this.protectedIncumbentCandidateList = protectedIncumbentCandidateList;
        this.recomb_file = districtingID;
        this.sortedMinorityData = sortedMinorityData;
    }

    public Districting() {

    }

    /**
     * This method checks if this districting satisfies the constraints set by the user.
     *
     * @param userConstraints constraints set by the user
     * @return true if the constraint is satisfied
     */
    public boolean satisfyConstraints(Constraints userConstraints) {
        if (userConstraints.getIncumbentsID().size() != 0) {
            Collection<String> protectedCandidateIds = new ArrayList<>();
            // get the incumebents that are currently protected
            for (Incumbent candid : this.getProtectedIncumbentCandidateList()) {
                String[] dbID = candid.getCandidateID().split("-");
                protectedCandidateIds.add(dbID[dbID.length - 1]);
            }
            if (!protectedCandidateIds.containsAll(userConstraints.getIncumbentsID())) {
                //if not all specified incumbents are protected
                return false;
            }
        }
//        if(userConstraints.getMajorityMinorityDistricts() > 0){
//            //check if the majority minority district counts is >= to the user selected counts
//            if(this.countMajorityMinorityDistrict(userConstraints) < userConstraints.getMajorityMinorityDistricts()){
//                return false;
//            }
//
//        }
        if (userConstraints.getTotalPopulationEquality() > 0) {
            //check if the tot pop equality is >= the user selected tot pop equ threshold
            if (this.districtingMeasures.getMeasures().get(MeasureType.TOT_POP_EQU).getMeasureScore()
                    < userConstraints.getTotalPopulationEquality()) {
                return false;
            }
        }
        if (userConstraints.getVotingAgePopulationEquality() > 0) {
            if (this.districtingMeasures.getMeasures().get(MeasureType.VOT_POP_EQU).getMeasureScore()
                    < userConstraints.getVotingAgePopulationEquality()) {
                return false;
            }
        }
        if (userConstraints.getGeographicCompactness() > 0) {
            if (this.districtingMeasures.getMeasures().get(MeasureType.GEOMETRIC_COMPACTNESS).getMeasureScore()
                    < userConstraints.getGeographicCompactness()) {
                return false;
            }
        }
        if (userConstraints.getGraphCompactness() > 0) {
            if (this.districtingMeasures.getMeasures().get(MeasureType.GRAPH_COMPACTNESS).getMeasureScore()
                    < userConstraints.getGraphCompactness()) {
                return false;
            }
        }
        if (userConstraints.getPopulationFatness() > 0) {
            if (this.districtingMeasures.getMeasures().get(MeasureType.POPULATION_FATNESS).getMeasureScore()
                    < userConstraints.getPopulationFatness()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method counts how many majority minority are in this districting based on the
     * minority selected by the user and the threshold selected by the user
     *
     * @param userConstraints constraints set by the user
     * @return number of majority minority districts
     */
    public int countMajorityMinorityDistrict(Constraints userConstraints) {
        int count = 0;
        Minorities minorityLookUp = userConstraints.getMinoritySelected();
        float minorityMin = userConstraints.getMinorityThreshold();
        for (District dist : this.getDistrictsMap().values()) {
            //if the minority of the specified type has percentage > the one user selected
            if (dist.getCensusInfo().getMinorities().get(minorityLookUp) > minorityMin) {
                count++;
            }
        }
        return count;
    }

    public Measure calSplitCounties() {
        //optinal?
        return null;
    }

    /**
     * This method calculates the deviation from average districting by geometric
     * by iterating through the districts to calcuates sum of square differences in area
     *
     * @param avgDistricting the average districting of the constrainted job
     * @return deviation from average districting by geometric score
     */
    public Measure calDevFromAvgDistGeo(Districting avgDistricting) {
        //sum of square differences by area at districting level
        Measure totMeasure = this.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_GEO);
        //resetting the previous dev from Average Districting
        totMeasure.setMeasureScore(0);
        //iterates through all districts in the districting to calculate the SSE
        for (int districtID : this.getDistrictsMap().keySet()) {
            //calculates the SSE for the district
            Measure currentMeasure = this.getDistrictsMap().get(districtID)
                    .calDevFromAvgDistGeo(avgDistricting.getDistrictsMap().get(districtID));
            //add the SSE to the overall score
            totMeasure.setMeasureScore(totMeasure.getMeasureScore() + currentMeasure.getMeasureScore());
        }
        return totMeasure;
    }

    /**
     * This method calculates the deviation from average districting by population
     * by iterating through the districts to calcuates sum of square differences in population
     *
     * @param avgDistricting the average districting of the constrainted job
     * @return deviation from average districting by population score
     */
    public Measure calDevFromAvgDistPop(Districting avgDistricting) {
        //sum of square differences by population at districting level
        Measure totMeasure = this.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_GEO);
        //resetting the previous dev from Average Districting
        totMeasure.setMeasureScore(0);
        //iterates through all districts in the districting to calculate the SSE
        for (int districtID : this.getDistrictsMap().keySet()) {
            //calculates the SSE for the district
            Measure currentMeasure = this.getDistrictsMap().get(districtID)
                    .calDevFromAvgDistPop(avgDistricting.getDistrictsMap().get(districtID));
            //add the SSE to the overall score
            totMeasure.setMeasureScore(totMeasure.getMeasureScore() + currentMeasure.getMeasureScore());
        }
        return totMeasure;
    }

    public District findDistrictByID(String districtID, EntityManager em) {
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

    public String getDistrictingID() {
        return recomb_file;
    }

    public void setDistrictingID(String districtingID) {
        this.recomb_file = districtingID;
    }

    public Collection<Incumbent> getProtectedIncumbentCandidateList() {
        return protectedIncumbentCandidateList;
    }

    public void setProtectedIncumbentCandidateList(Collection<Incumbent> protectedIncumbentCandidateList) {
        this.protectedIncumbentCandidateList = protectedIncumbentCandidateList;
    }

    public Map<Integer, Map<Minorities, Integer>> getSortedMinorityData() {
        return sortedMinorityData;
    }

    public void setSortedMinorityData(Map<Integer, Map<Minorities, Integer>> sortedMinorityData) {
        this.sortedMinorityData = sortedMinorityData;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setDev_avg_population(String dev_avg_population) {
        this.dev_avg_population = dev_avg_population;
    }

    public void setDev_avg_geometric(String dev_avg_geometric) {
        this.dev_avg_geometric = dev_avg_geometric;
    }

    public String getDev_avg_population() {
        return dev_avg_population;
    }

    public String getDev_avg_geometric() {
        return dev_avg_geometric;
    }

    public void setPopulation_fatness(float population_fatness) {
        this.population_fatness = population_fatness;
    }

    public float getPopulation_fatness() {
        return population_fatness;
    }

    public void setGraph_comp(float graph_comp) {
        this.graph_comp = graph_comp;
    }

    public float getGraph_comp() {
        return graph_comp;
    }

    public void setGeographic_comp(float geographic_comp) {
        this.geographic_comp = geographic_comp;
    }

    public float getGeographic_comp() {
        return geographic_comp;
    }

    public void setDev_enacted_population(float dev_enacted_population) {
        this.dev_enacted_population = dev_enacted_population;
    }

    public float getDev_enacted_population() {
        return dev_enacted_population;
    }

    public void setDev_enacted_geometric(float dev_enacted_geometric) {
        this.dev_enacted_geometric = dev_enacted_geometric;
    }

    public float getDev_enacted_geometric() {
        return dev_enacted_geometric;
    }

    public void setVot_pop_equality(float vot_pop_equality) {
        this.vot_pop_equality = vot_pop_equality;
    }

    public float getVot_pop_equality() {
        return vot_pop_equality;
    }

    public void setTot_pop_equality(float tot_pop_equality) {
        this.tot_pop_equality = tot_pop_equality;
    }

    public float getTot_pop_equality() {
        return tot_pop_equality;
    }

    public void setCal_pop_diff_percent(float cal_pop_diff_percent) {
        this.cal_pop_diff_percent = cal_pop_diff_percent;
    }

    public float getCal_pop_diff_percent() {
        return cal_pop_diff_percent;
    }

    public void setRecomb_file(String recomb_file) {
        this.recomb_file = recomb_file;
    }

    public String getRecomb_file() {
        return recomb_file;
    }
}
