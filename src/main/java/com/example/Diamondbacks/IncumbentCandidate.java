package com.example.Diamondbacks;

public class IncumbentCandidate {
    private String candidateName;
    private PoliticalParty candidateParty;
    private int districtFrom;
    private int candidateID;

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
