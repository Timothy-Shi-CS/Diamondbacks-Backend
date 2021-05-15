package com.example.Diamondbacks;

import org.locationtech.jts.*;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.geojson.Geometry;
import org.wololo.jts2geojson.GeoJSONReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;
import java.io.*;

@Entity
@Table(name = "State")
public class State {

    @Transient
    private Districting enactedDistricting;

    @Transient
    private Districting currentDistricting;

    @Id
    private StateName stateName;

    @Transient
    private Map<String, Precinct> precinctMap = new HashMap<>();

    @Transient
    private Map<Integer, Collection<Float>> jobSummaries;

    @Transient
    private Job currentJob;

    @Transient
    private BoxAndWhisker currentBoxAndWhisker; //box and whisker data for plotting

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public void loadPrecinctGeometries() {
        System.out.println("loading precinct geometries");
        String filepath;
        // change location of filepath
//        switch (stateName){
//            case UTAH:
//                filepath="~/Utah.json";
//            case VIRGINIA:
//                filepath="~/Virginia.json";
//            case ARIZONA:
//                filepath="~/Arizona.json";
//        }
        try {
            String file = "/Users/garyjiang/IdeaProjects/Diamondbacks-Backend/az_precincts.json";
            String json = readFileAsString(file);
            GeoJSONReader reader = new GeoJSONReader();
            FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(json);
            System.out.println(featureCollection.getFeatures().length);
            Map<String, Object> properties = featureCollection.getFeatures()[0].getProperties();
            String precinctCode = (String) properties.get("CODE");
            System.out.println(precinctCode);
            System.out.println(properties.toString());
//            for(Feature feature: featureCollection.getFeatures()){
//                Geometry geometry = feature.getGeometry();
//                Map<String,Object> properties = feature.getProperties();
//
//            }
        } catch (Exception e) {
            System.out.println("Unable to read file");
        }

        File inFile = new File("");

    }

    private void updatePrecinctMap() {

    }

    public Map<Integer, Float> calEnactedBoxAndWhiskerPercent() {
        Constraints userConstraints = this.getCurrentJob().getCurrentConstraints();
        Minorities minorityToPlot = userConstraints.getMinoritySelected();
        Map<Integer, Float> dataToPlot = new HashMap<Integer, Float>();

        Districting districting = this.getEnactedDistricting();
        for (Integer districtID : districting.getBawData().keySet()) {
            int countTotPop = (int) ((ArrayList) districting.getSortedTotPop()).get(districtID);
            int countMinority = districting.getBawData().get(districtID).getTotAsianPop();
            if (minorityToPlot == Minorities.BLACK) {
                countMinority = districting.getBawData().get(districtID).getTotBlackPop();
            } else if (minorityToPlot == Minorities.HISPANIC) {
                countMinority = districting.getBawData().get(districtID).getTotHispanicPop();
            }
            float minorityPercent = (float) countMinority / countTotPop;
            dataToPlot.put(districtID, minorityPercent);
        }
        return dataToPlot;
    }

    public Map<Integer, Float> calCurrentBoxAndWhiskerPercent() {
        Constraints userConstraints = this.getCurrentJob().getCurrentConstraints();
        Minorities minorityToPlot = userConstraints.getMinoritySelected();
        Map<Integer, Float> dataToPlot = new HashMap<Integer, Float>();

        Districting districting = this.getCurrentDistricting();
        for (Integer districtID : districting.getBawData().keySet()) {
            int countTotPop = (int) ((ArrayList) districting.getSortedTotPop()).get(districtID);
            int countMinority = districting.getBawData().get(districtID).getTotAsianPop();
            if (minorityToPlot == Minorities.BLACK) {
                countMinority = districting.getBawData().get(districtID).getTotBlackPop();
            } else if (minorityToPlot == Minorities.HISPANIC) {
                countMinority = districting.getBawData().get(districtID).getTotHispanicPop();
            }
            float minorityPercent = (float) countMinority / countTotPop;
            dataToPlot.put(districtID, minorityPercent);
        }
        return dataToPlot;
    }

    public Map<Integer, Collection<Float>> calAllBoxAndWhiskerPercent() {
        Constraints userConstraints = this.getCurrentJob().getCurrentConstraints();
        Minorities minorityToPlot = userConstraints.getMinoritySelected();
        Map<Integer, Collection<Float>> dataToPlot = new HashMap<Integer, Collection<Float>>();

        for (Districting districting : this.getCurrentJob().getListDistrictings()) {
            if (districting.getSatisfiesConstraints()) {
                for (Integer districtID : districting.getBawData().keySet()) {
                    int countTotPop = (int) ((ArrayList) districting.getSortedTotPop()).get(districtID);
                    int countMinority = districting.getBawData().get(districtID).getTotAsianPop();
                    if (minorityToPlot == Minorities.BLACK) {
                        countMinority = districting.getBawData().get(districtID).getTotBlackPop();
                    } else if (minorityToPlot == Minorities.HISPANIC) {
                        countMinority = districting.getBawData().get(districtID).getTotHispanicPop();
                    }
                    if (!dataToPlot.containsKey(districtID)) {
                        //if the key does not exist, intitize it to an empty array list
                        dataToPlot.put(districtID, new ArrayList<Float>());
                    }
                    float minorityPercent = (float) countMinority / countTotPop;
                    dataToPlot.get(districtID).add(minorityPercent);
                }
            }
        }
        return dataToPlot;
    }

    /**
     * This method calculates the data necessary for the box and whisker object
     * based on the constrained job
     */
    public void calcBoxAndWhisker(Double popEqual, Double devAvgDistGeo, Double devAvgDistPop, Double devEnDistGeo, Double devEnDistPop, Double geo, Double graph, Double popFat) {
        Constraints userConstraints = this.getCurrentJob().getCurrentConstraints();
        Minorities minorityToPlot = userConstraints.getMinoritySelected();
        Map<Integer, Collection<Integer>> dataToPlot = new HashMap<Integer, Collection<Integer>>();
        //Iterate through the job for districtings that satisfy constraints
        for (Districting districting : this.getCurrentJob().getListDistrictings()) {
            //for each district in the district add the data
            // if the constraint is satified

            if (districting.getSatisfiesConstraints()) {
                Collection<Integer> totalPop = new ArrayList<>();
                ObjectiveValue objv = districting.getDistrictingMeasures();
                HashMap<MeasureType, Measure> measures = (HashMap<MeasureType, Measure>) objv.getMeasures();
                measures.get(MeasureType.GEOMETRIC_COMPACTNESS).setMeasureWeight(geo.floatValue());
                measures.get(MeasureType.GRAPH_COMPACTNESS).setMeasureWeight(graph.floatValue());
                measures.get(MeasureType.POPULATION_FATNESS).setMeasureWeight(popFat.floatValue());
                ///
                measures.put(MeasureType.DEV_ENACTED_GEO, new Measure(MeasureType.DEV_ENACTED_GEO, devEnDistGeo, districting.getDev_enacted_geometric()));
                measures.put(MeasureType.DEV_ENACTED_POP, new Measure(MeasureType.DEV_ENACTED_POP, devEnDistPop, districting.getDev_enacted_population()));
                measures.put(MeasureType.DEV_AVERAGE_GEO, new Measure(MeasureType.DEV_AVERAGE_GEO, devAvgDistGeo, -1));
                measures.put(MeasureType.DEV_AVERAGE_POP, new Measure(MeasureType.DEV_AVERAGE_POP, devAvgDistPop, -1));
                ///
                measures.get(MeasureType.TOT_POP_EQU).setMeasureWeight(popEqual.floatValue());
                measures.get(MeasureType.VOT_POP_EQU).setMeasureWeight(popEqual.floatValue());

                objv.setMeasures(measures);
                districting.setDistrictingMeasures(objv);
                for (Integer districtID : districting.getBawData().keySet()) {
                    int countMinority = districting.getBawData().get(districtID).getTotAsianPop();
                    if (minorityToPlot == Minorities.BLACK) {
                        countMinority = districting.getBawData().get(districtID).getTotBlackPop();
                    } else if (minorityToPlot == Minorities.HISPANIC) {
                        countMinority = districting.getBawData().get(districtID).getTotHispanicPop();
                    }
                    if (!dataToPlot.containsKey(districtID)) {
                        //if the key does not exist, intitize it to an empty array list
                        dataToPlot.put(districtID, new ArrayList<Integer>());
                    }
                    dataToPlot.get(districtID).add(countMinority);
                    totalPop.add(Integer.parseInt(districting.getDistrictData().get(districtID+1).get(1)));

                }
                Collections.sort((List) totalPop);
                districting.setSortedTotPop(totalPop);
            }

        }
        //get the enacted and current disticting data for the box and whisker data
        //Map<Integer, Integer> currentDistrictingData = findCurrentDistictingData(minorityToPlot);
        // Map<Integer, Integer> enactedDistrictingData = findEnactedDistictingData(minorityToPlot);
        //update the current BoxAndWhisker object with new data.
        if (this.getCurrentBoxAndWhisker() == null) {
            this.setCurrentBoxAndWhisker(new BoxAndWhisker());
        }
        BoxAndWhisker currentBAW = this.getCurrentBoxAndWhisker();
        currentBAW.setMinorityData(dataToPlot);
        currentBAW.setEnactedDistrictingData(this.findEnactedDistictingData(minorityToPlot));
        //currentBAW.setCurrentDistrictingData(currentDistrictingData);
        currentBAW.calculateBoxAndWhiskerData();
        Districting enacted=this.getEnactedDistricting();

        Collection<Integer> totalPop = new ArrayList<>();
        for (Integer districtID : enacted.getBawData().keySet()) {
            totalPop.add(Integer.parseInt(enacted.getDistrictData().get(districtID+1).get(1)));
        }

        Collections.sort((List) totalPop);
        enacted.setSortedTotPop(totalPop);

        //after setting the BAW object calculate the average districting for the constrained job
        this.getCurrentJob().calAverageDistricting(currentBAW);
    }

    /**
     * This method gathers the recombination districting plan data for box and whisker
     *
     * @param minorityToPlot the minority that the user selects
     * @return hashmap of minorities in each district for the recombination plan
     */
    public Map<Integer, Integer> findCurrentDistictingData(Minorities minorityToPlot) {
        Map<Integer, Integer> currentDistrictingData = new HashMap<Integer, Integer>();
        for (Integer districtID : this.currentDistricting.getSortedMinorityData().keySet()) {
            currentDistrictingData.put(districtID, this.getCurrentDistricting().getSortedMinorityData().
                    get(districtID).get(minorityToPlot));
        }
        return currentDistrictingData;
    }

    /**
     * This method gathers the enacted districting plan data for box and whisker
     *
     * @param minorityToPlot the minority that the user selects
     * @return hashmap of minorities in each district for the enacted plan
     */
    public Map<Integer, Integer> findEnactedDistictingData(Minorities minorityToPlot) {
        Map<Integer, Integer> enactedDistrictingData = new HashMap<Integer, Integer>();
        for (Integer districtID : this.enactedDistricting.getBawData().keySet()) {
            int countMinority = this.enactedDistricting.getBawData().get(districtID).getTotAsianPop();
            if (minorityToPlot == Minorities.BLACK) {
                countMinority = this.enactedDistricting.getBawData().get(districtID).getTotBlackPop();
            } else if (minorityToPlot == Minorities.HISPANIC) {
                countMinority = this.enactedDistricting.getBawData().get(districtID).getTotHispanicPop();
            }
            enactedDistrictingData.put(districtID, countMinority);
        }
        return enactedDistrictingData;
    }

    @Override
    public String toString() {
        return "State{" +
                "enactedDistricting=" + enactedDistricting +
                ", currentDistricting=" + currentDistricting +
                ", stateName=" + stateName +
                ", jobSummaries=" + jobSummaries +
                ", currentJob=" + currentJob +
                ", currentBoxAndWhisker=" + currentBoxAndWhisker +
                '}';
    }

    public Districting getEnactedDistricting() {
        return enactedDistricting;
    }

    public void setEnactedDistricting(Districting enactedDistricting) {
        this.enactedDistricting = enactedDistricting;
    }

    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }

    public StateName getStateName() {
        return stateName;
    }

    public void setStateName(StateName stateName) {
        this.stateName = stateName;
    }

    public Map<Integer, Collection<Float>> getJobSummaries() {
        return jobSummaries;
    }

    public void setJobSummaries(Map<Integer, Collection<Float>> jobSummaries) {
        this.jobSummaries = jobSummaries;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public BoxAndWhisker getCurrentBoxAndWhisker() {
        return currentBoxAndWhisker;
    }

    public void setCurrentBoxAndWhisker(BoxAndWhisker currentBoxAndWhisker) {
        this.currentBoxAndWhisker = currentBoxAndWhisker;
    }
}
