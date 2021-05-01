package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DistrictingHandler {
    private Districting currentDistricting;
    public String getObjectiveFunctionScore(Job currentJob, int districtingID){
        Districting currentDistricting = currentJob.findDistrictingByID(districtingID);
        ObjectiveValue districtingMeasures = currentDistricting.getDistrictingMeasures();
        return districtingMeasures.toString();
    }
    public String getDistrictingBoundary(int districtingID){
        return null;
    }
    public String getObjectiveFunctionDetail(Job currentJob, String districtID, EntityManager em){
        Districting currentDistricting = currentJob.getCurrentDistricting();
        currentDistricting.findDistrictByID(districtID, em);
        return null;
    }
    public Collection<Float> calculateDeviationFromEnacted(Job job, int districtingID){
        return null;
    }

    @Override
    public String toString() {
        return "DistrictingHandler{" +
                "currentDistricting=" + currentDistricting +
                '}';
    }

    private Map<Integer, Geometry> getGeometries(Map<Integer,District> districts){
        Map<Integer,Geometry> districtGeometries = new HashMap<>();
        for(Map.Entry<Integer,District> entry : districts.entrySet()){
            Geometry districtGeometry = entry.getValue().getDistrictGeometry();
            districtGeometries.put(entry.getKey(),districtGeometry);
        }
        return districtGeometries;
    }

    public Map<Integer,Geometry> getDistrictingGeometry(int districtingID, Job currentJob){
        this.currentDistricting = currentJob.getDistrictingByID(districtingID);
        currentJob.setCurrentDistricting(this.currentDistricting);
        Map<Integer,District> districts = this.currentDistricting.getDistrictsMap();
        return getGeometries(districts);
    }

    public Districting getCurrentDistricting() {
        return currentDistricting;
    }

    public void setCurrentDistricting(Districting currentDistricting) {
        this.currentDistricting = currentDistricting;
    }
}
