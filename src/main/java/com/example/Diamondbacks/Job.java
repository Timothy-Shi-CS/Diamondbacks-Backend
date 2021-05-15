package com.example.Diamondbacks;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;
import java.sql.Array;
import java.util.*;

@Entity
@Table(name = "Jobs")
public class Job implements Serializable {
    @Transient
    private Collection<Districting> listDistrictings;

    @Column(name = "cooling_period", nullable = false)
    private int coolingPeriod;

    @Column(name = "rounds", nullable = false)
    private int rounds;

    @Column(name = "num_of_districtings", nullable = false)
    private int numDistrictings;

    @Transient
    private Constraints currentConstraints;

    @Transient
    private Districting currentAverageDistricting;

    @Transient
    private BoxAndWhisker boxAndWhisker;

    @Id
    private String id;

    @Transient
    private Analysis analysis;

    @Transient
    private Districting currentDistricting;

    @ManyToOne
    private State state;

    public int getNumDistrictings() {
        return numDistrictings;
    }

    public void setNumDistrictings(int numDistrictings) {
        this.numDistrictings = numDistrictings;
    }

    private float percentError(Integer f1, Integer f2) {
        return Math.abs((float) f1 - (float) f2) / Math.abs((float) f2);
    }

    /**
     * This metho iternates through the list of districtings in the constrained job and picks
     * a districting with the closes minority data of the box and whisker averages
     *
     * @param currentBAW the box and whisker object with the averages
     * @return the averaged districting based on the constraint set by the user
     */
    public Districting calAverageDistricting(BoxAndWhisker currentBAW) {
        //this method is only called by the constrainted jobs object
        Minorities minoritySelected = this.getCurrentConstraints().getMinoritySelected();
        Map<Integer, Integer> currentAverages = currentBAW.getAverageMinorityData();
        int num_districts = currentAverages.keySet().size();
        int start = 0;
        int mid = num_districts / 2;
        int end = num_districts - 1;
        Districting averagedDistricting = null;

        Integer start_avg = currentAverages.get(start);
        Integer mid_avg = currentAverages.get(mid);
        Integer end_avg = currentAverages.get(end);
        float threshold = 0.05f;
        for (Districting dist : this.getListDistrictings()) {
            if (dist.getSatisfiesConstraints()) {
                //if the dsitricting satisfies the constraints
                // add data to the box and whisker object
                int start_val = dist.getBawData().get(start).getTotAsianPop();
                if (minoritySelected == Minorities.BLACK) {
                    start_val = dist.getBawData().get(start).getTotBlackPop();
                } else if (minoritySelected == Minorities.HISPANIC) {
                    start_val = dist.getBawData().get(start).getTotHispanicPop();
                }

                int mid_val = dist.getBawData().get(mid).getTotAsianPop();
                if (minoritySelected == Minorities.BLACK) {
                    mid_val = dist.getBawData().get(mid).getTotBlackPop();
                } else if (minoritySelected == Minorities.HISPANIC) {
                    mid_val = dist.getBawData().get(mid).getTotHispanicPop();
                }

                int end_val = dist.getBawData().get(end).getTotAsianPop();
                if (minoritySelected == Minorities.BLACK) {
                    end_val = dist.getBawData().get(end).getTotBlackPop();
                } else if (minoritySelected == Minorities.HISPANIC) {
                    end_val = dist.getBawData().get(end).getTotHispanicPop();
                }

                if (percentError(start_avg, start_val) < threshold
                        && percentError(mid_avg, mid_val) < threshold
                        && percentError(end_avg, end_val) < threshold) {
                    averagedDistricting = dist;
                    break;
                }
            }
        }
        //sets the current average districting after finding a good fit
        this.setcurrentAverageDistricting(averagedDistricting);
        //calculates the deviation from average disitrciting for the constrainted job
        this.calDeviationFromAvgDistricting();
        return averagedDistricting;
    }

    /**
     * This method iternates through all the districtings in the constrained Job to calculate the
     * deviation from average distircting
     */
    public void calDeviationFromAvgDistricting() {
        //this method is only called by the constrained jobs
        for (Districting dist : this.getListDistrictings()) {
            if(dist.getSatisfiesConstraints()) {
                dist.calDevFromAvgDistGeo(this.getcurrentAverageDistricting());
                dist.calDevFromAvgDistPop(this.getcurrentAverageDistricting());
                dist.calTotPerimeter();
                dist.getDistrictingMeasures().calOverallObjectiveValueScore();
            }
        }
    }

    public Districting getDistrictingByID(String id) {
        return null;
    }

    public Collection<Float> getAvgDistrictingMinorityStats(String minority) {
        return null;
    }

    public Collection<Districting> getDevFromAvgDistrictingGeometric() {
        return null;
    }

    public Collection<Districting> filterJobByConstraint(Constraints constraints) {
        return null;
    }

    /**
     * This method counts the remaining districting after the user selects the constraints
     * but doesn't actually create constrained Job due to the possibility of changing constraints
     * back and forth
     *
     * @return the number of remaining districtings after the user selects the contraints
     */
    public int countRemainDistrictings() {
        // write method here to count districtings that fit constraints
        int count = 0;
        int districtings = 0;
        for (Districting districting : this.getListDistrictings()) {
//            if(count==2000){
//                return count;
//            }
//            if(districtings==50000){
//                return count;
//            }

//            System.out.println(districting.getGeographic_comp());

            if (districting.satisfyConstraints(this.getCurrentConstraints())) {
                districting.setSatisfiesConstraints(true);
                count++;
                System.out.println(count);

            } else {
                districting.setSatisfiesConstraints(false);
            }
            districtings++;
        }
        System.out.println(count);

        return count;
    }

    public String getJobSummary() {
        return "Cooling Period:" + this.getCoolingPeriod() + "Rounds:" + this.getRounds();
    }

    public String getPrelimAnalysis() {
        return null;
    }

    public Collection<Districting> findTopDistrictingsObjectFunction() {
        //sort the reamining districting by objective function value
        //returns top5 or top 10 districting by objective function value
        //make sure it is sorted from high to low !!!
        Comparator<Districting> objValComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float objValue1 = d1.getDistrictingMeasures().getOverallObjectiveValueScore();
                float objValue2 = d2.getDistrictingMeasures().getOverallObjectiveValueScore();
                return -1*Float.compare(objValue1, objValue2);
            }
        };
        //sort by objective function value
        List<Districting> districtings = (List<Districting>) this.getListDistrictings();
        districtings.sort(objValComparator);
        System.out.println("AYOOOOOO 1: "+districtings.get(0).getDistrictingMeasures().getOverallObjectiveValueScore()+", 2: "+districtings.get(1).getDistrictingMeasures().getOverallObjectiveValueScore()+
        ", 3: "+districtings.get(2).getDistrictingMeasures().getOverallObjectiveValueScore());
        //get the top 10 from the sorted
        return districtings.subList(0, 10);
    }

    /**
     * This method sorts the constrained job by the deviation from enacted by area and return the districting
     * with the highest deviation from enacted districting by area
     *
     * @return districting with the highest deviation from enacted districting by area
     */
    public Districting findTopDistrictingsByDeviationFromEnactedGeo() {
        //sort the remaining districting by deviation from enacted
        //return the maximum deviation from enacted by geometric
        //make sure it is sorted from high to low !!!
        Comparator<Districting> devEnactedAreaComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                double enactedGeo1 = -1;
                double enactedGeo2 = -1;
                if(d1.getSatisfiesConstraints()){
                    enactedGeo1 = d1.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_GEO).getMeasureScore();
                }
                if(d2.getSatisfiesConstraints()){
                    enactedGeo2 = d2.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_GEO).getMeasureScore();
                }
                return -1*Double.compare(enactedGeo1, enactedGeo2);
            }
        };
        //sort by objective function value
        List<Districting> districtings = (List<Districting>) this.getListDistrictings();
        districtings.sort(devEnactedAreaComparator);
        return districtings.get(0);
    }

    /**
     * This method sorts the constrained job by the deviation from enacted by population and return the districting
     * with the highest deviation from enacted districting by population
     *
     * @return districting with the highest deviation from enacted districting by population
     */
    public Districting findTopDistrictingsByDeviationFromEnactedPop() {
        //sort the remaining districting by deviation from enacted
        //return the maximum deviation from enacted by population
        //make sure it is sorted from high to low !!!
        Comparator<Districting> devEnactedPopulationComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {

                double enactedPop1 = -1;
                double enactedPop2 = -1;
                if(d1.getSatisfiesConstraints()){
                    enactedPop1 = d1.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_POP).getMeasureScore();
                }
                if(d2.getSatisfiesConstraints()){
                    enactedPop2 = d2.getDistrictingMeasures().getMeasures().get(MeasureType.DEV_ENACTED_POP).getMeasureScore();
                }
                return -1*Double.compare(enactedPop1, enactedPop2);
            }
        };
        //sort by objective function value
        List<Districting> districtings = (List<Districting>) this.getListDistrictings();
        districtings.sort(devEnactedPopulationComparator);
        return districtings.get(0);
    }

    /**
     * This method sorts the constrained job by the area of a specific district to find the pair of the districting
     * with very different area
     *
     * @return a pair of districting with very different area by a specific district
     */
    public Collection<Districting> findVeryDifferentAreaPairDeviations() {
        //using the total perimeter attribute
        //sort by the total perimeter(sum of all perimeters of all districts in districting)
        //pair smallest with largest = very different area
        Integer districtToCompareArea = 1;
        Comparator<Districting> areaComparator = new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                float area1 = -1;
                float area2 = -1;
                if(d1.getSatisfiesConstraints()){
                    area1 = d1.getTotPerimeter();
                }
                if(d2.getSatisfiesConstraints()){
                    area2 = d2.getTotPerimeter();
                }
                return -1*Float.compare(area1, area2);
            }
        };
        int indexValid = 0;
        for(Districting districting:this.getListDistrictings()){
            if(districting.getSatisfiesConstraints()){
                indexValid++;
            }else{
                break;
            }
        }
        //sort by objective function value
        List<Districting> districtings = (List<Districting>) this.getListDistrictings();
        districtings.sort(areaComparator);
        List<Districting> result = new ArrayList<Districting>();
        //the first element and the last element have very different area
        result.addAll(districtings.subList(0, 5));
        result.addAll(districtings.subList(indexValid - 6, indexValid - 1));
        return result;
    }

//    /**
//     * This method sorts the constrained job by the population of a specific district to find the pair of the districting
//     * with very different population
//     *
//     * @return a pair of districting with very different population by a specific district
//     */
//    public Collection<Districting> findVeryDifferentPopulationPairDeviations() {
//        //this should return it in pairs?
//        //pick a district, then sort the list by the population of that list
//        //return the min and max of that list(very different population in terms of the district selected)
//        Integer districtToCompareArea = 1;
//        Comparator<Districting> populationComparator = new Comparator<Districting>() {
//            @Override
//            public int compare(Districting d1, Districting d2) {
//                float population1 = d1.getDistrictsMap().get(districtToCompareArea).getCensusInfo().getPopulationData()
//                        .get(CensusValues.TOTAL_POPULATION);
//                float population2 = d2.getDistrictsMap().get(districtToCompareArea).getCensusInfo().getPopulationData()
//                        .get(CensusValues.TOTAL_POPULATION);
//                return -1*Float.compare(population1, population2);
//            }
//        };
//
//        //sort by objective function value
//        List<Districting> districtings = (List<Districting>) this.getListDistrictings();
//        districtings.sort(populationComparator);
//        List<Districting> result = new ArrayList<Districting>();
//        //the first element and the last element have very different population
//        result.addAll(districtings.subList(0, 5));
//        result.addAll(districtings.subList(districtings.size() - 6, districtings.size() - 1));
//        return result;
//    }

//    public Collection<Districting> getInterestingDistictions(){
//        this.getTopDistrictingsObjectFunction();
//        this.getTopDistrictingsByDeviationFromEnactedGeo();
//        this.getTopDistrictingsByDeviationFromEnactedPop();
//        this.getVeryDifferentAreaPairDeviations();
//        this.getVeryDifferentPopulationPairDeviations();
//        return null;
//    }

    public Collection<Boolean> findDistrictingsByMajorMinorityRange() {
        //shouldn't this be all districtings? since it is being constrainted by this?
        Minorities minoritySelected = this.getCurrentConstraints().getMinoritySelected();
        Collection<Districting> listDistrictings = this.findTopDistrictingsObjectFunction();
        Collection<Boolean> closeToAvgDistricting = new ArrayList<>();

        for(Districting districting: listDistrictings){
            boolean isClose = true;
            for(Integer districtID: districting.getDistrictsMap().keySet()){
                District avgDist = this.getcurrentAverageDistricting().getDistrictsMap().get(districtID);
                District curDist = districting.getDistrictsMap().get(districtID);
                if(minoritySelected == Minorities.HISPANIC){
                    float ans = percentError(avgDist.getHISPANIC().intValue(), curDist.getHISPANIC().intValue());
                    if(ans > 0.05){
                        isClose = false;
                        break;
                    }
                }else if(minoritySelected == Minorities.ASIAN){
                    float ans = percentError(avgDist.getASIAN().intValue(), curDist.getASIAN().intValue());
                    if(ans > 0.05){
                        isClose = false;
                        break;
                    }
                }else if(minoritySelected == Minorities.BLACK){
                    float ans = percentError(avgDist.getBLACK().intValue(), curDist.getBLACK().intValue());
                    if(ans > 0.05){
                        isClose = false;
                        break;
                    }
                }
            }
            closeToAvgDistricting.add(isClose);
        }
        //list of booleans if districtings are close to the BAW averages
        //for the top 10 districting
        return closeToAvgDistricting;
    }

    public float calMajMinDevFromAvg(Map<Integer, Float> map) {
        //what is this???
        return 0;
    }

    public Districting findDistrictingByID(String id, EntityManager em) {
        // Implement method for use case 15
        String queryString = "SELECT * FROM Diamondbacks.Districtings WHERE recomb_file = " + id;
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
                ", currentAverageDistricting=" + currentAverageDistricting +
                ", id='" + id + '\'' +
                ", analysis=" + analysis +
                ", currentDistricting=" + currentDistricting +
                ", state=" + state +
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

    public Districting getcurrentAverageDistricting() {
        return currentAverageDistricting;
    }

    public void setcurrentAverageDistricting(Districting currentAverageDistricting) {
        this.currentAverageDistricting = currentAverageDistricting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public BoxAndWhisker getBoxAndWhisker() {
        return boxAndWhisker;
    }

    public void setBoxAndWhisker(BoxAndWhisker boxAndWhisker) {
        this.boxAndWhisker = boxAndWhisker;
    }
}
