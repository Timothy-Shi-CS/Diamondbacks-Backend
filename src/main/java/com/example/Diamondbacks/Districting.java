package com.example.Diamondbacks;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Districtings")
public class Districting {
    @Transient
    private boolean satisfiesConstraints;

    @Transient
    private HashMap<Integer, List<Double>> districtsMinority;

    @Id
    private String id;

    @Column(name = "recomb_file", nullable = false)
    private String recomb_file;

    @ManyToOne
    private Job job;

    @Transient
    private String state;

    @Transient
    private double vot_pop_equality;

    @Transient
    private double dev_enacted_geometric;

    @Transient
    private double dev_enacted_population;

    @Transient
    private String dev_avg_geometric;

    @Transient
    private String dev_avg_population;

    @Transient
    private double geographic_comp;

    @Transient
    private double graph_comp;

    @Transient
    private double population_fatness;

    @Transient
    private double cal_pop_diff_percent;

    @Transient
    private double tot_pop_equality;

    @Transient
    private CensusInfo censusInfo; //census info of the districting

    @Transient
    private ObjectiveValue districtingMeasures; //OBJ value of the districting

    @Transient
    private Map<Integer, District> districtsMap; // Map[districNumber]= District

    @Transient
    private Map<Integer, BAWData> bawData;

    @Transient
    private Map<Integer, List<String>> districtData;

    @Transient
    private float totPerimeter;

    @Transient
    private Collection<Integer> sortedTotPop;

//    @Transient
//    private String[] list_incumbent_protected_origin;

    @Transient
    private Collection<String> list_incumbent_protected_origin; // read in from JSON as list
//    private int districtingID; //unique districting ID read from JSON

    @Transient
    private Map<Integer, Map<Minorities, Integer>> sortedMinorityData; //count of minority for each district
    // read in from JSON as integers


    public Districting(CensusInfo censusInfo, ObjectiveValue districtingMeasures, Map<Integer, District> districtsMap,
                       Collection<String> protectedIncumbentCandidateList, String districtingID,
                       Map<Integer, Map<Minorities, Integer>> sortedMinorityData) {
        this.censusInfo = censusInfo;
        this.districtingMeasures = districtingMeasures;
        this.districtsMap = districtsMap;
        this.list_incumbent_protected_origin = protectedIncumbentCandidateList;
        this.recomb_file = districtingID;
        this.sortedMinorityData = sortedMinorityData;
        this.satisfiesConstraints = true;
    }

    public Districting() {
        this.satisfiesConstraints = true;
    }

    /**
     * This method checks if this districting satisfies the constraints set by the user.
     *
     * @param userConstraints constraints set by the user
     * @return true if the constraint is satisfied
     */
    public boolean satisfyConstraints(Constraints userConstraints) {

        if (this.getList_incumbent_protected_origin() != null && userConstraints.getIncumbentsID().size() > 1) {
            // get the incumebents that are currently protected

            Collection<String> protectedCandidateIds = new ArrayList<>(this.getList_incumbent_protected_origin());

            if (!protectedCandidateIds.containsAll(userConstraints.getIncumbentsID())) {

                //if not all specified incumbents are protected
                return false;
            }
        }

        if (this.districtingMeasures != null) {
            System.out.println("in here");
            if (userConstraints.getTotalPopulationEquality() > 0) {
                //check if the tot pop equality is >= the user selected tot pop equ threshold
                if (this.districtingMeasures.getMeasures().get(MeasureType.TOT_POP_EQU).getMeasureScore()
                        > userConstraints.getTotalPopulationEquality()) {
                    System.out.println("failed totalpop: " + this.districtingMeasures.getMeasures().get(MeasureType.TOT_POP_EQU).getMeasureScore() + ", user: " + userConstraints.getTotalPopulationEquality());

                    return false;
                }
            }

            if (userConstraints.getVotingAgePopulationEquality() > 0) {
                if (this.districtingMeasures.getMeasures().get(MeasureType.VOT_POP_EQU).getMeasureScore()
                        > userConstraints.getVotingAgePopulationEquality()) {
                    System.out.println("failed votepop");
                    return false;
                }
            }
            if (userConstraints.getGeographicCompactness() > 0) {
                if (this.districtingMeasures.getMeasures().get(MeasureType.GEOMETRIC_COMPACTNESS).getMeasureScore()
                        > userConstraints.getGeographicCompactness()) {
                    System.out.println("failed geo");
                    return false;
                }
            }
            if (userConstraints.getGraphCompactness() > 0) {
                if (this.districtingMeasures.getMeasures().get(MeasureType.GRAPH_COMPACTNESS).getMeasureScore()
                        > userConstraints.getGraphCompactness()) {
                    System.out.println("failed graphpop");
                    return false;
                }
            }
            if (userConstraints.getPopulationFatness() > 0) {
                if (this.districtingMeasures.getMeasures().get(MeasureType.POPULATION_FATNESS).getMeasureScore()
                        > userConstraints.getPopulationFatness()) {
                    System.out.println("failed popfat");
                    return false;
                }
            }
        } else {
            return false;
        }

        if (this.districtsMinority != null && userConstraints.getMajorityMinorityDistricts() > 0) {
            //check if the majority minority district counts is >= to the user selected counts
            if (this.countMajorityMinorityDistrict(userConstraints) < userConstraints.getMajorityMinorityDistricts()) {
                System.out.println("failed majmin: " + this.countMajorityMinorityDistrict(userConstraints) + ", user: " + userConstraints.getMajorityMinorityDistricts());
                return false;
            }

        } else {
            return false;
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
        double minorityMin = userConstraints.getMinorityThreshold();
        for (Integer key : districtsMinority.keySet()) {
            //if the minority of the specified type has percentage > the one user selected
            if (minorityLookUp == Minorities.ASIAN) {
                if (districtsMinority.get(key).get(0) >= minorityMin) {
                    count++;
                }
            } else if (minorityLookUp == Minorities.BLACK) {
                if (districtsMinority.get(key).get(1) >= minorityMin) {
                    count++;
                }
            } else {
                if (districtsMinority.get(key).get(2) >= minorityMin) {
                    count++;
                }
            }

//                if (dist.getCensusInfo().getMinorities().get(minorityLookUp) >= minorityMin) {
//                    count++;
//                }
        }
        return count;
//        }
//        return -1;

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
        for (int districtID : this.getDistrictData().keySet()) {
            float avgArea = Float.parseFloat(avgDistricting.getDistrictData().get(districtID).get(2));
            float recombArea = Float.parseFloat(this.getDistrictData().get(districtID).get(2));
            float sse = (float) Math.pow(avgArea - recombArea, 2);
            this.getDistrictData().get(districtID).set(6,String.valueOf(sse));
            //calculates the SSE for the district
//            Measure currentMeasure = this.getDistrictsMap().get(districtID)
//                    .calDevFromAvgDistGeo(avgDistricting.getDistrictsMap().get(districtID));
            //add the SSE to the overall score
            totMeasure.setMeasureScore(totMeasure.getMeasureScore() + sse);
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
        Measure totMeasure = this.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_AVERAGE_POP);
        //resetting the previous dev from Average Districting
        totMeasure.setMeasureScore(0);
        //iterates through all districts in the districting to calculate the SSE
        for (int districtID : this.getDistrictData().keySet()) {
            float avgPop = Float.parseFloat(avgDistricting.getDistrictData().get(districtID).get(1));
            float recombPop = Float.parseFloat(this.getDistrictData().get(districtID).get(1));
            float sse = (float) Math.pow(avgPop - recombPop, 2);
            this.getDistrictData().get(districtID).set(7,String.valueOf(sse));
            //calculates the SSE for the district
//            Measure currentMeasure = this.getDistrictsMap().get(districtID)
//                    .calDevFromAvgDistPop(avgDistricting.getDistrictsMap().get(districtID));
            //add the SSE to the overall score
            totMeasure.setMeasureScore(totMeasure.getMeasureScore() + sse);
        }
        return totMeasure;
    }

    public float calTotPerimeter(){
        float perimeter = 0;
        for(int districtID : this.getDistrictData().keySet()){
            perimeter+=Float.parseFloat(this.getDistrictData().get(districtID).get(3));
//            perimeter+=dist.getDistrictGeometry().getPerimeter();
        }
        this.setTotPerimeter(perimeter);
        return perimeter;
    }
    public District findDistrictByID(String districtID, EntityManager em) {
        Query q = em.createNativeQuery("SELECT * FROM Diamondbacks.Districts WHERE districtID = " + districtID);
        List districtInfo = q.getResultList();
        System.out.println(districtInfo);
//        ArrayList<String> info = new ArrayList<>();
//        double devEnactedGeometric = (double) districtInfo.get(3);
//        double devEnactedPopulation = (double)
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

//    public Collection<String> getProtectedIncumbentCandidateList() {
//        return protectedIncumbentCandidateList;
//    }

//    public void setProtectedIncumbentCandidateList(Collection<String> protectedIncumbentCandidateList) {
//        this.protectedIncumbentCandidateList = protectedIncumbentCandidateList;
//    }

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

    public void setPopulation_fatness(double population_fatness) {
        this.population_fatness = population_fatness;
    }

    public double getPopulation_fatness() {
        return population_fatness;
    }

    public void setGraph_comp(double graph_comp) {
        this.graph_comp = graph_comp;
    }

    public double getGraph_comp() {
        return graph_comp;
    }

    public void setGeographic_comp(double geographic_comp) {
        this.geographic_comp = geographic_comp;
    }

    public double getGeographic_comp() {
        return geographic_comp;
    }

    public void setDev_enacted_population(double dev_enacted_population) {
        this.dev_enacted_population = dev_enacted_population;
    }

    public double getDev_enacted_population() {
        return dev_enacted_population;
    }

    public void setDev_enacted_geometric(double dev_enacted_geometric) {
        this.dev_enacted_geometric = dev_enacted_geometric;
    }

    public double getDev_enacted_geometric() {
        return dev_enacted_geometric;
    }

    public void setVot_pop_equality(double vot_pop_equality) {
        this.vot_pop_equality = vot_pop_equality;
    }

    public double getVot_pop_equality() {
        return vot_pop_equality;
    }

    public void setTot_pop_equality(double tot_pop_equality) {
        this.tot_pop_equality = tot_pop_equality;
    }

    public double getTot_pop_equality() {
        return tot_pop_equality;
    }

    public void setCal_pop_diff_percent(double cal_pop_diff_percent) {
        this.cal_pop_diff_percent = cal_pop_diff_percent;
    }

    public double getCal_pop_diff_percent() {
        return cal_pop_diff_percent;
    }

    public void setRecomb_file(String recomb_file) {
        this.recomb_file = recomb_file;
    }

    public String getRecomb_file() {
        return recomb_file;
    }

    public void setList_incumbent_protected_origin(Collection<String> list_incumbent_protected_origin) {
        this.list_incumbent_protected_origin = list_incumbent_protected_origin;
    }

    public Collection<String> getList_incumbent_protected_origin() {
        return this.list_incumbent_protected_origin;
    }


    public void setBawData(Map<Integer, BAWData> bawData) {
        this.bawData = bawData;
    }

    public Map<Integer, BAWData> getBawData() {
        return bawData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<Integer, List<Double>> getDistrictsMinority() {
        return districtsMinority;
    }

    public void setDistrictsMinority(HashMap<Integer, List<Double>> districtsMinority) {
        this.districtsMinority = districtsMinority;
    }

    public boolean getSatisfiesConstraints() {
        return satisfiesConstraints;
    }

    public void setSatisfiesConstraints(boolean satisfiesConstraints) {
        this.satisfiesConstraints = satisfiesConstraints;
    }

    public Map<Integer, List<String>> getDistrictData() {
        return districtData;
    }

    public void setDistrictData(Map<Integer, List<String>> districtData) {
        this.districtData = districtData;
    }

    public float getTotPerimeter() {
        return totPerimeter;
    }

    public void setTotPerimeter(float totPerimeter) {
        this.totPerimeter = totPerimeter;
    }

    public Collection<Integer> getSortedTotPop() {
        return sortedTotPop;
    }

    public void setSortedTotPop(Collection<Integer> sortedTotPop) {
        this.sortedTotPop = sortedTotPop;
    }
}
