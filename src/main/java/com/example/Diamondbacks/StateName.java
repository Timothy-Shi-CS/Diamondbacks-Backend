package com.example.Diamondbacks;

public enum StateName {
    UTAH(0),
    VIRGINIA(1),
    ARIZONA(2);

    private Integer stateNumber;

    private StateName(final Integer stateNumber){
        this.stateNumber = stateNumber;
    }

    public Integer getStateNumber(){
        return this.stateNumber;
    }
}
