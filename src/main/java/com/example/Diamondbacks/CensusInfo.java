package com.example.Diamondbacks;

import java.util.Map;

public class CensusInfo {
    private CensusValues censusType;
    private float censusNum;
    private Map<Minorities, Float> minorities;

    public CensusInfo(CensusValues censusType, float censusNum, Map<Minorities, Float> minorities) {
        this.censusType = censusType;
        this.censusNum = censusNum;
        this.minorities = minorities;
    }

    @Override
    public String toString() {
        return "CensusInfo{" +
                "censusType=" + censusType +
                ", censusNum=" + censusNum +
                ", minorities=" + minorities +
                '}';
    }

    public CensusValues getCensusType() {
        return censusType;
    }

    public void setCensusType(CensusValues censusType) {
        this.censusType = censusType;
    }

    public float getCensusNum() {
        return censusNum;
    }

    public void setCensusNum(float censusNum) {
        this.censusNum = censusNum;
    }

    public Map<Minorities, Float> getMinorities() {
        return minorities;
    }

    public void setMinorities(Map<Minorities, Float> minorities) {
        this.minorities = minorities;
    }
}
