package com.example.Diamondbacks;


import javax.persistence.*;

@Entity
@Table(name = "Incumbents")
public class Incumbent {
    //    private String candidateName; // name of the candidate
//    private PoliticalParty candidateParty; //the political party of the candidate
//    private int districtFrom; // the district which the candidate is from based on enacted plan

    @Id
    private String incumbentID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "party", nullable = false)
    private String party;

    @Column(name = "districtNum", nullable = false)
    private Long districtNum;

    @ManyToOne
    private State state;

    public Incumbent() {

    }

    public Incumbent(String incumbentID, Long districtNum, String name, String party, State state) {
        this.incumbentID = incumbentID;
        this.districtNum = districtNum;
        this.name = name;
        this.party = party;
        this.state = state;
    }

//    @Override
//    public String toString() {
//        return "IncumbentCandidate{" +
//                "candidateName='" + candidateName + '\'' +
//                ", candidateParty=" + candidateParty +
//                ", districtFrom=" + districtFrom +
//                ", candidateID=" + candidateID +
//                '}';
//    }

//    public String getCandidateName() {
//        return candidateName;
//    }
//
//    public void setCandidateName(String candidateName) {
//        this.candidateName = candidateName;
//    }
//
//    public PoliticalParty getCandidateParty() {
//        return candidateParty;
//    }
//
//    public void setCandidateParty(PoliticalParty candidateParty) {
//        this.candidateParty = candidateParty;
//    }
//
//    public int getDistrictFrom() {
//        return districtFrom;
//    }
//
//    public void setDistrictFrom(int districtFrom) {
//        this.districtFrom = districtFrom;
//    }

    public void setIncumbentID(String incumbentID) {
        this.incumbentID = incumbentID;
    }

    public String getIncumbentID() {
        return incumbentID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getDistrictNum() {
        return districtNum;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public void setDistrictNum(Long districtNum) {
        this.districtNum = districtNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
