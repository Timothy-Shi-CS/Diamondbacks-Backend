package com.example.Diamondbacks;

/**
 * The State names which the user can choose from
 */
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
