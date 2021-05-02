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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    private Job constraintedJob;

    @Transient
    private BoxAndWhisker currentBoxAndWhisker; //box and whisker data for plotting

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public void loadPrecinctGeometries(){
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
        try{
            String file = "/Users/garyjiang/IdeaProjects/Diamondbacks-Backend/az_precincts.json";
            String json = readFileAsString(file);
            GeoJSONReader reader = new GeoJSONReader();
            FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(json);
            System.out.println(featureCollection.getFeatures().length);
            Map<String,Object> properties = featureCollection.getFeatures()[0].getProperties();
            String precinctCode = (String) properties.get("CODE");
            System.out.println(precinctCode);
            System.out.println(properties.toString());
//            for(Feature feature: featureCollection.getFeatures()){
//                Geometry geometry = feature.getGeometry();
//                Map<String,Object> properties = feature.getProperties();
//
//            }
        }
        catch (Exception e){
            System.out.println("Unable to read file");
        }

        File inFile = new File("");

    }



    /**
     * This method makes a clone of the current job and filters it down by the constraints
     * set by the user
     *
     * This method is not called until the constraints are set
     */
    public void makeConstraintedJob(){
        Constraints userConstraints = this.getCurrentJob().getCurrentConstraints();
        Job filteredJob = this.getConstraintedJob();
        Collection<Districting> filteredDistricting = new ArrayList<>();
        for(Districting districting: this.getCurrentJob().getListDistrictings()){
            if(districting.satisfyConstraints(userConstraints)){
                //adds the districting to the constrained list if it satisfies the constraints
                filteredDistricting.add(districting);
            }
        }
        //cloning the original(unfiltered) job
        filteredJob.setCoolingPeriod(this.getCurrentJob().getCoolingPeriod());
        filteredJob.setRounds(this.getCurrentJob().getRounds());
        filteredJob.setCurrentConstraints(this.getCurrentJob().getCurrentConstraints());
        filteredJob.setId(this.getCurrentJob().getId());
        //sets the constrained list of districtings
        filteredJob.setListDistrictings(filteredDistricting);

        //the job has been constrained get the box and whisker
        this.calcBoxAndWhisker();
    }

    private void updatePrecinctMap(){

    }

    /**
     * This method calculates the data necessary for the box and whisker object
     * based on the constrained job
     */
    public void calcBoxAndWhisker(){
        Constraints userConstraints = this.getConstraintedJob().getCurrentConstraints();
        Minorities minorityToPlot = userConstraints.getMinoritySelected();

        Map<Integer, Collection<Integer>> dataToPlot = new HashMap<Integer, Collection<Integer>>();
        //Iterate through the constrained job
        for(Districting districting: this.getConstraintedJob().getListDistrictings()){
            //for each district in the district add the data
            for(Integer districtID: districting.getSortedMinorityData().keySet()){
                Integer countMinority = districting.getSortedMinorityData().get(districtID).get(minorityToPlot);
                if(!dataToPlot.containsKey(districtID)){
                    //if the key does not exist, intitize it to an empty array list
                    dataToPlot.put(districtID, new ArrayList<Integer>());
                }
                dataToPlot.get(districtID).add(countMinority);
            }
        }
        //get the enacted and current disticting data for the box and whisker data
        Map<Integer, Integer> currentDistrictingData = findCurrentDistictingData(minorityToPlot);
        // Map<Integer, Integer> enactedDistrictingData = findEnactedDistictingData(minorityToPlot);
        //update the current BoxAndWhisker object with new data.
        BoxAndWhisker currentBAW = this.getCurrentBoxAndWhisker();
        currentBAW.setMinorityData(dataToPlot);
        currentBAW.setCurrentDistrictingData(currentDistrictingData);
        currentBAW.calculateBoxAndWhiskerData();
        //after setting the BAW object calculate the average districting for the constrained job
        this.getConstraintedJob().calAverageDistricting(currentBAW);
    }

    /**
     * This method gathers the recombination districting plan data for box and whisker
     * @param minorityToPlot the minority that the user selects
     * @return hashmap of minorities in each district for the recombination plan
     */
    public Map<Integer, Integer> findCurrentDistictingData(Minorities minorityToPlot ){
        Map<Integer, Integer> currentDistrictingData = new HashMap<Integer, Integer>();
        for(Integer districtID: this.currentDistricting.getSortedMinorityData().keySet()){
            currentDistrictingData.put(districtID, this.getCurrentDistricting().getSortedMinorityData().
                    get(districtID).get(minorityToPlot));
        }
        return currentDistrictingData;
    }

    /**
     * This method gathers the enacted districting plan data for box and whisker
     * @param minorityToPlot the minority that the user selects
     * @return hashmap of minorities in each district for the enacted plan
     */
    public Map<Integer, Integer> findEnactedDistictingData(Minorities minorityToPlot ){
        Map<Integer, Integer> enactedDistrictingData = new HashMap<Integer, Integer>();
        for(Integer districtID: this.currentDistricting.getSortedMinorityData().keySet()){
            enactedDistrictingData.put(districtID, this.getCurrentDistricting().getSortedMinorityData().
                    get(districtID).get(minorityToPlot));
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
                ", constraintedJob=" + constraintedJob +
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

    public Job getConstraintedJob() {
        return constraintedJob;
    }

    public void setConstraintedJob(Job constraintedJob) {
        this.constraintedJob = constraintedJob;
    }

    public BoxAndWhisker getCurrentBoxAndWhisker() {
        return currentBoxAndWhisker;
    }

    public void setCurrentBoxAndWhisker(BoxAndWhisker currentBoxAndWhisker) {
        this.currentBoxAndWhisker = currentBoxAndWhisker;
    }
}
