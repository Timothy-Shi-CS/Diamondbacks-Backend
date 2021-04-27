package com.example.Diamondbacks;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="Sample")
public class Sample implements Serializable{
    private int id;
    private List<String> protectedIncumbents;
    private float totalPop;
    private float votPop;
    private int numMajMin;
    private List<String> listMajMin;
    private String geoComp;
    private float graphComp;
    private float popFatness;
    private Map<String, String> precinctsByDistrict;

    @Id
    @Column(name="sampleID", unique = true)
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    @ElementCollection
    @Column(name="protected_incumb", nullable = false)
    public List<String> getProtectedIncumbents() {
        return protectedIncumbents;
    }

    public void setProtectedIncumbents(List<String> protectedIncumbents) {
        this.protectedIncumbents = protectedIncumbents;
    }

    @Column(name="tot_pop_equality", nullable = false)
    public float getTotalPop() {
        return totalPop;
    }

    public void setTotalPop(float totalPop) {
        this.totalPop = totalPop;
    }

    @Column(name="vot_pop_equality", nullable = false)
    public float getVotPop() {
        return votPop;
    }

    public void setVotPop(float votPop) {
        this.votPop = votPop;
    }

    @Column(name="num_maj_min", nullable = false)
    public int getNumMajMin() {
        return numMajMin;
    }

    public void setNumMajMin(int numMajMin) {
        this.numMajMin = numMajMin;
    }

    @ElementCollection
    @Column(name="maj_min_list", nullable = false)
    public List<String> getListMajMin() {
        return listMajMin;
    }

    public void setListMajMin(List<String> listMajMin) {
        this.listMajMin = listMajMin;
    }

    @Column(name="geo_comp", nullable = false)
    public String getGeoComp() {
        return geoComp;
    }

    public void setGeoComp(String geoComp) {
        this.geoComp = geoComp;
    }

    @Column(name="graph_comp", nullable = false)
    public float getGraphComp() {
        return graphComp;
    }

    public void setGraphComp(float graphComp) {
        this.graphComp = graphComp;
    }

    @Column(name="pop_fat", nullable = false)
    public float getPopFatness() {
        return popFatness;
    }

    public void setPopFatness(float popFatness) {
        this.popFatness = popFatness;
    }

    @ElementCollection
    @Column(name="precint_by_districts", nullable = false)
    public Map<String, String> getPrecinctsByDistrict() {
        return precinctsByDistrict;
    }

    public void setPrecinctsByDistrict(Map<String, String> precinctsByDistrict) {
        this.precinctsByDistrict = precinctsByDistrict;
    }
}
