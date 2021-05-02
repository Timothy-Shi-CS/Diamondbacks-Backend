package com.example.Diamondbacks;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobHandler {
    public String getPrelimAnalysis(Job job) {
        return null;
    }

    public Map<String, Float> callTopDistrictingsObjectiveFunction(Job currentJob) {
        return null;
    }

    public Map<String, Float> callTopDistrictingsByDeviationFromEnacted(Job currentJob) {
        return null;
    }

    public Map<String, Float> callDistrictingsByMajorMinorityRange(Job currentJob) {
        return null;
    }

    public Map<String, Float> callVeryDifferentAreaPairDeviations(Job currentJob) {
        return null;
    }

    public Job getJobByID(String jobID, EntityManager em) {

//        TOT_POP_EQU, VOT_POP_EQU, CITZEN_POP_EQU,
//                GEOMETRIC_COMPACTNESS, GRAPH_COMPACTNESS, POPULATION_FATNESS,
//                DEV_AVERAGE_GEO, DEV_AVERAGE_POP,
//                DEV_ENACTED_GEO, DEV_ENACTED_POP,
        String queryString = "from Job j where j.id=:jobID";
        Query jobQ = em.createQuery(queryString).setParameter("jobID", jobID);
        Job curJob = (Job) jobQ.getResultList().get(0);
        Query districtingsQ = this.loadJob(jobID, em);
        List<Districting> districtings = districtingsQ.getResultList();
        for (Districting districting : districtings) {
            ObjectiveValue objv = new ObjectiveValue();
            HashMap<MeasureType, Measure> measures = new HashMap<>();
            measures.put(MeasureType.TOT_POP_EQU, new Measure(MeasureType.TOT_POP_EQU, -1, districting.getTot_pop_equality()));
            measures.put(MeasureType.VOT_POP_EQU, new Measure(MeasureType.VOT_POP_EQU, -1, districting.getVot_pop_equality()));
            measures.put(MeasureType.GEOMETRIC_COMPACTNESS, new Measure(MeasureType.GEOMETRIC_COMPACTNESS, -1, districting.getGeographic_comp()));
            measures.put(MeasureType.GRAPH_COMPACTNESS, new Measure(MeasureType.GRAPH_COMPACTNESS, -1, districting.getGraph_comp()));
            measures.put(MeasureType.POPULATION_FATNESS, new Measure(MeasureType.POPULATION_FATNESS, -1, districting.getPopulation_fatness()));

            objv.setMeasures(measures);
            List<Incumbent> incumbents = this.getIncumbents(districting.getRecomb_file(), em).getResultList();
            districting.setProtectedIncumbentCandidateList(incumbents);
            districting.setDistrictingMeasures(objv);
        }
        curJob.setListDistrictings(districtingsQ.getResultList());
        return (Job) jobQ.getResultList().get(0);
    }

    public Query loadJob(String jobID, EntityManager em) {
        String queryString = "from Districting d where d.job.id=:jobID";
        return em.createQuery(queryString).setParameter("jobID", jobID);
    }

    public Query getIncumbents(String districtingID, EntityManager em) {
        String queryString = "from Incumbent i where i.districting.recomb_file=:districtingID";
        return em.createQuery(queryString).setParameter("districtingID", districtingID);
    }

    public void getRemainingDistrictings(Job job) {

    }
}
