package com.example.Diamondbacks;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.util.*;

public class StateHandler {
    public List getJobs(String stateName, EntityManager em) {
        /*
         * mySQL query to get jobs
         * */
        // convert stateName to int
        System.out.println(StateName.valueOf(stateName).getStateNumber());
        int stateValue = StateName.valueOf(stateName).getStateNumber();
        String queryString = "from Job j where j.state.stateName=" + stateValue;
        Query q = em.createQuery(queryString);
//        List<Job> jobList = q.getResultList();
//        for (Job j : jobList) {
//            System.out.println(j.toString());
//        }

        return q.getResultList();
    }

    public void loadPrecinctGeometries(State state) {
        state.loadPrecinctGeometries();
    }

    public void calculateBoxAndWhiskerData(String state) {

    }

    public State setState(String stateName, State state) {
        StateName name = StateName.valueOf(stateName);
        state.setStateName(name);
        return state;
    }

    public State resetDistricts(State state) {
        state.setCurrentBoxAndWhisker(null);
        List<Districting> districtings = (List<Districting>) state.getCurrentJob().getListDistrictings();
        for(Districting districting:districtings){
            districting.setDistrictData(null);
            districting.getDistrictingMeasures().getMeasures().remove(MeasureType.DEV_ENACTED_GEO);
            districting.getDistrictingMeasures().getMeasures().remove(MeasureType.DEV_ENACTED_POP);
            districting.getDistrictingMeasures().getMeasures().remove(MeasureType.DEV_AVERAGE_GEO);
            districting.getDistrictingMeasures().getMeasures().remove(MeasureType.DEV_AVERAGE_POP);
        }
        return state;
    }

    public State setDistrictsForDistrictings(State state) {
        JsonFactory f = new MappingJsonFactory();
        JsonToken current;
        String curJobID = state.getCurrentJob().getId();
        List<Districting> districtings = (List<Districting>) state.getCurrentJob().getListDistrictings();
        int num = 1;
        for (int i = 0; i < districtings.size(); i++) {
            if (districtings.get(i).getSatisfiesConstraints()) {
                System.out.println(num);
                try {
                    JsonParser jp = f.createParser(new File("C:\\Users\\jimmy_lin\\Desktop\\new_az\\" + curJobID + "\\" + districtings.get(i).getRecomb_file().substring(1, districtings.get(i).getRecomb_file().length() - 1)));
                    jp.nextToken();
                    while (jp.nextToken() != JsonToken.END_OBJECT) {
                        String fieldName = jp.getCurrentName();

                        if (fieldName.equals("cal_pop_diff_percent")) {
                            jp.nextToken();
                        } else if (fieldName.equals("list_incumbent_protected_origin")) {
                            current = jp.nextToken();
                            if (current == JsonToken.START_ARRAY) {
                                while (jp.nextToken() != JsonToken.END_ARRAY) {
                                }
                            }
                        } else if (fieldName.equals("tot_pop_equality")) {
                            jp.nextToken();
                        } else if (fieldName.equals("vot_pop_equality")) {
                            jp.nextToken();
                        } else if (fieldName.equals("distribution_minority")) {
                            current = jp.nextToken();
                            if (current == JsonToken.START_OBJECT) {
                                while (jp.nextToken() != JsonToken.END_OBJECT) {
                                    current = jp.getCurrentToken();
                                    if (current == JsonToken.FIELD_NAME) {
                                        jp.nextToken();
                                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                                        }
                                    }
                                }
                            }
                        } else if (fieldName.equals("box_and_whisker_data")) {
                            current = jp.nextToken();
                            if (current == JsonToken.START_OBJECT) {
                                while (jp.nextToken() != JsonToken.END_OBJECT) {
                                    current = jp.getCurrentToken();
                                    if (current == JsonToken.FIELD_NAME) {
                                        jp.nextToken();
                                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                                        }
                                    }
                                }
                            }
                        } else if (fieldName.equals("dev_avg_geometric")) {
                            jp.nextToken();
                        } else if (fieldName.equals("dev_avg_population")) {
                            jp.nextToken();
                        } else if (fieldName.equals("dev_enacted_geometric")) {
                            jp.nextToken();
                        } else if (fieldName.equals("dev_enacted_population")) {
                            jp.nextToken();
                        } else if (fieldName.equals("geographic_comp")) {
                            jp.nextToken();
                        } else if (fieldName.equals("graph_comp")) {
                            jp.nextToken();
                        } else if (fieldName.equals("population_fatness")) {
                            jp.nextToken();
                        } else if (fieldName.equals("precincts_by_district")) {
                            current = jp.nextToken();
                            HashMap<Integer, List<String>> districtData = new HashMap<>();
                            int districtCounter = 1;
                            if (current == JsonToken.START_OBJECT) {
                                while (jp.nextToken() != JsonToken.END_OBJECT) {
                                    current = jp.getCurrentToken();
                                    if (current == JsonToken.FIELD_NAME) {
                                        jp.nextToken();

                                        ArrayList<String> districtDataValues = new ArrayList<>();
                                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                                            if (jp.getCurrentName().equals("district_id")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("population")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("area")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("perimeter")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_tot_pop_equality")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_vot_pop_equality")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_dev_avg_geometric")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_dev_avg_population")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_dev_enacted_geometric")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_dev_enacted_population")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_geographic_comp")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_graph_comp")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else if (jp.getCurrentName().equals("district_population_fatness")) {
                                                jp.nextToken();
                                                districtDataValues.add(jp.getText());
                                            } else {
                                                current = jp.nextToken();
                                                if (current == JsonToken.START_ARRAY) {
                                                    while (jp.nextToken() != JsonToken.END_ARRAY) {
                                                    }
                                                }
                                            }
                                        }
                                        districtData.put(districtCounter, districtDataValues);
                                        districtCounter++;
                                    }
                                }
                            }
                            districtings.get(i).setDistrictData(districtData);

                        } else {
                            jp.nextToken();
                        }
                    }
                    num++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return state;
    }

    public State setObjectiveValueAndCalcBAW(State state, Double popEqual, Double devAvgDistGeo, Double devAvgDistPop, Double devEnDistGeo,
                                             Double devEnDistPop, Double geo, Double graph, Double popFat) {
        state.calcBoxAndWhisker(popEqual, devAvgDistGeo, devAvgDistPop, devEnDistGeo, devEnDistPop, geo, graph, popFat);
        return state;
    }

    public void setEnactedDistricting(State state, String stateName) {
        JsonFactory f = new MappingJsonFactory();
        JsonToken current;
        File file;
        if (stateName.equals("ARIZONA")) {
            file = new File("C:\\Users\\jimmy_lin\\Desktop\\enacted\\AZ_ENACTED.json");
        } else if (stateName.equals("UTAH")) {
            file = new File("C:\\Users\\jimmy_lin\\Desktop\\enacted\\UT_ENACTED.json");
        } else {
            file = new File("C:\\Users\\jimmy_lin\\Desktop\\enacted\\VA_ENACTED.json");
        }

        Districting enactedDistricting = new Districting();
        try {
            JsonParser jp = f.createParser(file);
            jp.nextToken();
            while (jp.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jp.getCurrentName();

                if (fieldName.equals("cal_pop_diff_percent")) {
                    jp.nextToken();
                    enactedDistricting.setCal_pop_diff_percent(jp.getDoubleValue());
                } else if (fieldName.equals("list_incumbent_protected_origin")) {
                    current = jp.nextToken();
                    if (current == JsonToken.START_ARRAY) {
                        Collection<String> incumbents = new ArrayList<>();

                        while (jp.nextToken() != JsonToken.END_ARRAY) {
                            incumbents.add(jp.getText());
                        }
                        enactedDistricting.setList_incumbent_protected_origin(incumbents);
                    }
                } else if (fieldName.equals("tot_pop_equality")) {
                    jp.nextToken();
                    enactedDistricting.setTot_pop_equality(jp.readValueAs(Double.TYPE));
                } else if (fieldName.equals("vot_pop_equality")) {
                    jp.nextToken();
                    enactedDistricting.setVot_pop_equality(jp.readValueAs(Double.TYPE));
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
                                }
                                districts.put(districtCounter, minorities);
                            }


                            districtCounter++;
                        }
                        enactedDistricting.setDistrictsMinority(districts);
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
                                }
                                bawMap.put(num, bawData);
                            }
                        }
                    }
                    enactedDistricting.setBawData(bawMap);
                } else if (fieldName.equals("dev_avg_geometric")) {
                    jp.nextToken();
                    enactedDistricting.setDev_avg_geometric(jp.getText());
                } else if (fieldName.equals("dev_avg_population")) {
                    jp.nextToken();
                    enactedDistricting.setDev_avg_population(jp.getText());
                } else if (fieldName.equals("dev_enacted_geometric")) {
                    jp.nextToken();
                    enactedDistricting.setDev_enacted_geometric(jp.readValueAs(Double.TYPE));
                } else if (fieldName.equals("dev_enacted_population")) {
                    jp.nextToken();
                    enactedDistricting.setDev_enacted_population(jp.readValueAs(Double.TYPE));
                } else if (fieldName.equals("geographic_comp")) {
                    jp.nextToken();
                    enactedDistricting.setGeographic_comp(jp.readValueAs(Double.TYPE));
                } else if (fieldName.equals("graph_comp")) {
                    jp.nextToken();
                    enactedDistricting.setGraph_comp(jp.readValueAs(Double.TYPE));
                } else if (fieldName.equals("population_fatness")) {
                    jp.nextToken();
                    enactedDistricting.setPopulation_fatness(jp.readValueAs(Double.TYPE));
                } else if (fieldName.equals("precincts_by_district")) {
                    current = jp.nextToken();
                    HashMap<Integer, List<String>> districtData = new HashMap<>();
                    int districtCounter = 1;
                    if (current == JsonToken.START_OBJECT) {
                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                            current = jp.getCurrentToken();
                            if (current == JsonToken.FIELD_NAME) {
                                jp.nextToken();

                                ArrayList<String> districtDataValues = new ArrayList<>();
                                while (jp.nextToken() != JsonToken.END_OBJECT) {
                                    if (jp.getCurrentName().equals("district_id")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("population")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("area")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("perimeter")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_tot_pop_equality")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_vot_pop_equality")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_dev_avg_geometric")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_dev_avg_population")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_dev_enacted_geometric")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_dev_enacted_population")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_geographic_comp")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_graph_comp")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else if (jp.getCurrentName().equals("district_population_fatness")) {
                                        jp.nextToken();
                                        districtDataValues.add(jp.getText());
                                    } else {
                                        current = jp.nextToken();
                                        if (current == JsonToken.START_ARRAY) {
                                            while (jp.nextToken() != JsonToken.END_ARRAY) {
                                            }
                                        }
                                    }
                                }
                                districtData.put(districtCounter, districtDataValues);
                                districtCounter++;
                            }
                        }
                    }
                    enactedDistricting.setDistrictData(districtData);
                } else {
                    jp.nextToken();
                }
            }
            ObjectiveValue objv = new ObjectiveValue();
            HashMap<MeasureType, Measure> measures = new HashMap<>();
            measures.put(MeasureType.TOT_POP_EQU, new Measure(MeasureType.TOT_POP_EQU, -1, enactedDistricting.getTot_pop_equality()));
            measures.put(MeasureType.VOT_POP_EQU, new Measure(MeasureType.VOT_POP_EQU, -1, enactedDistricting.getVot_pop_equality()));
            measures.put(MeasureType.GEOMETRIC_COMPACTNESS, new Measure(MeasureType.GEOMETRIC_COMPACTNESS, -1, enactedDistricting.getGeographic_comp()));
            measures.put(MeasureType.GRAPH_COMPACTNESS, new Measure(MeasureType.GRAPH_COMPACTNESS, -1, enactedDistricting.getGraph_comp()));
            measures.put(MeasureType.POPULATION_FATNESS, new Measure(MeasureType.POPULATION_FATNESS, -1, enactedDistricting.getPopulation_fatness()));
            measures.put(MeasureType.DEV_ENACTED_GEO, new Measure(MeasureType.DEV_ENACTED_GEO, -1, enactedDistricting.getDev_enacted_geometric()));
            measures.put(MeasureType.DEV_ENACTED_POP, new Measure(MeasureType.DEV_ENACTED_POP, -1, enactedDistricting.getDev_enacted_population()));
            measures.put(MeasureType.DEV_AVERAGE_GEO, new Measure(MeasureType.DEV_AVERAGE_GEO, -1, -1));
            measures.put(MeasureType.DEV_AVERAGE_POP, new Measure(MeasureType.DEV_AVERAGE_POP, -1, -1));

            objv.setMeasures(measures);
            enactedDistricting.setDistrictingMeasures(objv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        state.setEnactedDistricting(enactedDistricting);
    }
}
