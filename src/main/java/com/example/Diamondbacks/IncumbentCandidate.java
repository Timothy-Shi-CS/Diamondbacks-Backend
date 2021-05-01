package com.example.Diamondbacks;

public class IncumbentCandidate {
    private String candidateName; // name of the candidate
    private PoliticalParty candidateParty; //the political party of the candidate
    private int districtFrom; // the district which the candidate is from based on enacted plan
    private int candidateID; //unique candidate ID for each candidate

    @Override
    public String toString() {
        return "IncumbentCandidate{" +
                "candidateName='" + candidateName + '\'' +
                ", candidateParty=" + candidateParty +
                ", districtFrom=" + districtFrom +
                ", candidateID=" + candidateID +
                '}';
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public PoliticalParty getCandidateParty() {
        return candidateParty;
    }

    public void setCandidateParty(PoliticalParty candidateParty) {
        this.candidateParty = candidateParty;
    }

    public int getDistrictFrom() {
        return districtFrom;
    }

    public void setDistrictFrom(int districtFrom) {
        this.districtFrom = districtFrom;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }
}
