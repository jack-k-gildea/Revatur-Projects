package models;

import java.sql.Timestamp;

public class ReimbursementS {

    int reimbID;
    double reimbAmt;
    Timestamp submitted;
    Timestamp resolved;
    String description;
    String reimbAuthorString;
    String reimbResolverString;
    String reimbStatus;
    String reimbType;

    public ReimbursementS() {
    }
        //Constructor with all String values to send to frontend (wouldn't let me use in Reimbursement model, try to fix)
    public ReimbursementS(int reimbID, double reimbAmt, Timestamp submitted,
                          Timestamp resolved, String description, String reimbAuthorString,
                          String reimbResolverString, String reimbStatus, String reimbType) {
        this.reimbID = reimbID;
        this.reimbAmt = reimbAmt;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.reimbAuthorString = reimbAuthorString;
        this.reimbResolverString = reimbResolverString;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
    }

    public int getReimbID() {
        return reimbID;
    }

    public void setReimbID(int reimbID) {
        this.reimbID = reimbID;
    }

    public double getReimbAmt() {
        return reimbAmt;
    }

    public void setReimbAmt(double reimbAmt) {
        this.reimbAmt = reimbAmt;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReimbAuthorString() {
        return reimbAuthorString;
    }

    public void setReimbAuthorString(String reimbAuthorString) {
        this.reimbAuthorString = reimbAuthorString;
    }

    public String getReimbResolverString() {
        return reimbResolverString;
    }

    public void setReimbResolverString(String reimbResolverString) {
        this.reimbResolverString = reimbResolverString;
    }

    public String getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public String getReimbType() {
        return reimbType;
    }

    public void setReimbType(String reimbType) {
        this.reimbType = reimbType;
    }

    @Override
    public String toString() {
        return "ReimbursementS{" +
                "reimbID=" + reimbID +
                ", reimbAmt=" + reimbAmt +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", reimbAuthorString='" + reimbAuthorString + '\'' +
                ", reimbResolverString='" + reimbResolverString + '\'' +
                ", reimbStatus='" + reimbStatus + '\'' +
                ", reimbType='" + reimbType + '\'' +
                '}' + "\n";
    }
}
