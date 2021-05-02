package com.example.Diamondbacks;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Incumbents")
public class Incumbent {
    //    private String candidateName; // name of the candidate
//    private PoliticalParty candidateParty; //the political party of the candidate
//    private int districtFrom; // the district which the candidate is from based on enacted plan


    @ManyToOne
    private Districting districting;

    @Id
    private String incumbentID; //unique candidate ID for each candidate

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

    public String getCandidateID() {
        return incumbentID;
    }

    public void setCandidateID(String candidateID) {
        String [] dbID=candidateID.split("-");
        this.incumbentID = dbID[dbID.length-1];
    }
}
