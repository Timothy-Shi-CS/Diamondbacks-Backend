package com.example.Diamondbacks;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;


public class JobHandler {
    @Context
    ServletContext servletContext;

    public String getPrelimAnalysis(Job job) {
        return null;
    }

    public Map<String, Double> callTopDistrictingsObjectiveFunction(Job currentJob) {
        return null;
    }

    public Map<String, Double> callTopDistrictingsByDeviationFromEnacted(Job currentJob) {
        return null;
    }

    public Map<String, Double> callDistrictingsByMajorMinorityRange(Job currentJob) {
        return null;
    }

    public Map<String, Double> callVeryDifferentAreaPairDeviations(Job currentJob) {
        return null;
    }

    public Job getJobByID(String jobID, EntityManager em, EntityManagerFactory emf) {
//        JSONParser parser = new JSONParser();
        EntityTransaction et = null;
        JsonFactory f = new MappingJsonFactory();
        JsonToken current;
        String queryString = "from Job j where j.id=:jobID";
        Query jobQ = em.createQuery(queryString).setParameter("jobID", jobID);
        Job curJob = (Job) jobQ.getResultList().get(0);
        String curJobID = curJob.getId();
        Query districtingsQ = this.loadJob(jobID, em);
        List<Districting> districtings = districtingsQ.getResultList();
        districtings=districtings.subList(0,70000);
        int files = 1;
        for (int i = 0; i < districtings.size(); i++) {
            System.out.println(files);
            try {
                JsonParser jp = f.createParser(new File("C:\\Users\\jimmy_lin\\Desktop\\new_az\\" + curJobID + "\\" + districtings.get(i).getRecomb_file().substring(1, districtings.get(i).getRecomb_file().length() - 1)));
                jp.nextToken();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = jp.getCurrentName();

                    if (fieldName.equals("cal_pop_diff_percent")) {
                        jp.nextToken();
                        districtings.get(i).setCal_pop_diff_percent(jp.getDoubleValue());
                    } else if (fieldName.equals("list_incumbent_protected_origin")) {
                        current = jp.nextToken();
                        if (current == JsonToken.START_ARRAY) {
                            Collection<String> incumbents = new ArrayList<>();

                            while (jp.nextToken() != JsonToken.END_ARRAY) {
                                incumbents.add(jp.getText());
                            }
                            districtings.get(i).setList_incumbent_protected_origin(incumbents);
                        }
                    } else if (fieldName.equals("tot_pop_equality")) {
                        jp.nextToken();
                        districtings.get(i).setTot_pop_equality(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("vot_pop_equality")) {
                        jp.nextToken();
                        districtings.get(i).setVot_pop_equality(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("distribution_minority")) {
                        current = jp.nextToken();
                        if (current == JsonToken.START_OBJECT) {
                            int districtCounter = 1;
                            HashMap<Integer, List<Double>> districts = new HashMap<>();
                            while (jp.nextToken() != JsonToken.END_OBJECT) {
                                current = jp.getCurrentToken();
                                if (current == JsonToken.FIELD_NAME) {
//                                    District newDistrict = new District();

                                    ArrayList<Double> minorities = new ArrayList<>();
//                                    newDistrict.setDistricting(districtings.get(i));
//                                    newDistrict.setDistrictNumber(districtCounter);
//                                    newDistrict.setDistrictID(districtings.get(i).getId() + "-" + jp.getText());
                                    //////
//                                    CensusInfo censusInfo = new CensusInfo();
//                                    Map<Minorities, Double> minorities = new HashMap<>();
                                    jp.nextToken();
                                    while (jp.nextToken() != JsonToken.END_OBJECT) {
                                        if (jp.getCurrentName().equals("ASIAN")) {
                                            jp.nextToken();
//                                            newDistrict.setASIAN(jp.getDoubleValue());
                                            minorities.add(jp.getDoubleValue());
                                        } else if (jp.getCurrentName().equals("BLACK")) {
                                            jp.nextToken();
//                                            newDistrict.setBLACK(jp.getDoubleValue());
                                            minorities.add(jp.getDoubleValue());
                                        } else if (jp.getCurrentName().equals("HISPANIC")) {
                                            jp.nextToken();
//                                            newDistrict.setHISPANIC(jp.getDoubleValue());
                                            minorities.add(jp.getDoubleValue());
                                        }
//                                        JsonNode node = jp.readValueAsTree();
//                                        minorities.put(Minorities.ASIAN, node.get("ASIAN").asDouble());
//                                        minorities.put(Minorities.BLACK, node.get("BLACK").asDouble());
//                                        minorities.put(Minorities.HISPANIC, node.get("HISPANIC").asDouble());
//                                        break;
                                    }
                                    districts.put(districtCounter, minorities);
//                                    censusInfo.setMinorities(minorities);
//                                    newDistrict.setCensusInfo(censusInfo);
//                                    districtsMap.put(districtCounter,newDistrict);
                                    //persist
//                                    try {
//                                        et = em.getTransaction();
//                                        et.begin();
//                                        em.persist(newDistrict);
//                                        et.commit();
//                                    } catch (Exception jx) {
//                                        if (et != null) {
//                                            et.rollback();
//                                        }
//
//                                        jx.printStackTrace();
//                                    }
//                                    em.close();
//                                    em=emf.createEntityManager();
                                }


                                districtCounter++;
                            }
                            districtings.get(i).setDistrictsMinority(districts);
                        }
//                        servletContext.setAttribute("em",em);
                    } else if (fieldName.equals("box_and_whisker_data")) {
                        current = jp.nextToken();
                        Map<Integer, BAWData> bawMap = new HashMap<>();
                        if (current == JsonToken.START_OBJECT) {
                            while (jp.nextToken() != JsonToken.END_OBJECT) {
                                current = jp.getCurrentToken();
                                if (current == JsonToken.FIELD_NAME) {
                                    int num = Integer.parseInt(jp.getText());
                                    BAWData bawData = new BAWData();
                                    jp.nextToken();
                                    while (jp.nextToken() != JsonToken.END_OBJECT) {
                                        if (jp.getCurrentName().equals("tot_asian_pop")) {
                                            jp.nextToken();
                                            bawData.setTotAsianPop(jp.getValueAsInt());
                                        } else if (jp.getCurrentName().equals("tot_black_pop")) {
                                            jp.nextToken();
                                            bawData.setTotBlackPop(jp.getValueAsInt());
                                        } else if (jp.getCurrentName().equals("tot_hispanic_pop")) {
                                            jp.nextToken();
                                            bawData.setTotHispanicPop(jp.getValueAsInt());
                                        }
//                                        JsonNode node = jp.readValueAsTree();
//                                        bawData.setTotAsianPop(node.get("tot_asian_pop").asInt());
//                                        bawData.setTotBlackPop(node.get("tot_black_pop").asInt());
//                                        bawData.setTotHispanicPop(node.get("tot_hispanic_pop").asInt());
//                                        break;
                                    }
                                    bawMap.put(num, bawData);
                                }
                            }
                        }
                        districtings.get(i).setBawData(bawMap);
                    } else if (fieldName.equals("dev_avg_geometric")) {
                        jp.nextToken();
                        districtings.get(i).setDev_avg_geometric(jp.getText());
                    } else if (fieldName.equals("dev_avg_population")) {
                        jp.nextToken();
                        districtings.get(i).setDev_avg_population(jp.getText());
                    } else if (fieldName.equals("dev_enacted_geometric")) {
                        jp.nextToken();
                        districtings.get(i).setDev_enacted_geometric(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("dev_enacted_population")) {
                        jp.nextToken();
                        districtings.get(i).setDev_enacted_population(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("geographic_comp")) {
                        jp.nextToken();
                        districtings.get(i).setGeographic_comp(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("graph_comp")) {
                        jp.nextToken();
                        districtings.get(i).setGraph_comp(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("population_fatness")) {
                        jp.nextToken();
                        districtings.get(i).setPopulation_fatness(jp.readValueAs(Double.TYPE));
                    } else if (fieldName.equals("precincts_by_district")) {
                        break;

                    } else {
                        jp.nextToken();
                    }
                }
                ObjectiveValue objv = new ObjectiveValue();
                HashMap<MeasureType, Measure> measures = new HashMap<>();
                measures.put(MeasureType.TOT_POP_EQU, new Measure(MeasureType.TOT_POP_EQU, -1, districtings.get(i).getTot_pop_equality()));
                measures.put(MeasureType.VOT_POP_EQU, new Measure(MeasureType.VOT_POP_EQU, -1, districtings.get(i).getVot_pop_equality()));
                measures.put(MeasureType.GEOMETRIC_COMPACTNESS, new Measure(MeasureType.GEOMETRIC_COMPACTNESS, -1, districtings.get(i).getGeographic_comp()));
                measures.put(MeasureType.GRAPH_COMPACTNESS, new Measure(MeasureType.GRAPH_COMPACTNESS, -1, districtings.get(i).getGraph_comp()));
                measures.put(MeasureType.POPULATION_FATNESS, new Measure(MeasureType.POPULATION_FATNESS, -1, districtings.get(i).getPopulation_fatness()));

                objv.setMeasures(measures);
                districtings.get(i).setDistrictingMeasures(objv);
//                districtings.get(i).setDistrictsMap(districtsMap);
                files++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        curJob.setListDistrictings(districtings);
//        int counter = 1;
//        for (Districting districting : districtings) {
//            System.out.println(counter);
//            System.out.println(districting.getDistrictingID());
//            System.out.println(districting.getList_incumbent_protected_origin());
//            System.out.println(districting.getBawData());
//
//            System.out.println(districting.getCal_pop_diff_percent());
//            System.out.println(districting.getDistrictsMinority());
//            System.out.println(districting.getDev_enacted_geometric());
//            System.out.println(districting.getDev_enacted_population());
//            System.out.println(districting.getDev_avg_geometric());
//            System.out.println(districting.getDev_avg_population());
//            System.out.println(districting.getGraph_comp());
//            System.out.println(districting.getPopulation_fatness());
//            System.out.println(districting.getGeographic_comp());
//            System.out.println(districting.getTot_pop_equality());
//            System.out.println(districting.getVot_pop_equality());
//            System.out.println(districting.getDistrictsMinority());
//            counter++;
//        }

        //get the incumbents for this state
        return curJob;
    }

    public Query loadJob(String jobID, EntityManager em) {
        String queryString = "from Districting d where d.job.id=:jobID";
        return em.createQuery(queryString).setParameter("jobID", jobID);
    }

    public Query getIncumbents(State state, EntityManager em) {
        String queryString = "from Incumbent i where i.state.stateName=" + state.getStateName().getStateNumber();
        return em.createQuery(queryString);
    }


    public void setDistrictsAndPrecincts(List<Districting> districtings) {

    }


    public void getRemainingDistrictings(Job job) {

    }

    private Collection<String> convertIncumbentsArray(String incumbentIDs) {
        String[] incumbents = incumbentIDs.substring(1, incumbentIDs.length() - 1).split(",");
        Collection<String> incumbentsProtected = new ArrayList<>();
        for (String incumbent : incumbents) {
            incumbentsProtected.add(incumbent);
        }
        return incumbentsProtected;
    }
}
