package models;
import java.sql.Timestamp;
public class Reimbursement {

    int reimbID;
    double reimbAmt;
    Timestamp submitted;
    Timestamp resolved;
    String description;
    int reimbAuthor;
    int reimbResolver;
    String reimbStatus;
    String reimbType;
    String reimbAuthorString;
    String reimbResolverString;
    int reimbStatusInt;
    int reimbTypeInt;

    //Constructor with all ints to send values to database
    public Reimbursement(int reimbID, double reimbAmt,
                         Timestamp submitted, Timestamp resolved,
                         String description, int reimbAuthor,
                         int reimbResolver, int reimbStatusInt,
                         int reimbTypeInt) {
        this.reimbID = reimbID;
        this.reimbAmt = reimbAmt;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatusInt = reimbStatusInt;
        this.reimbTypeInt = reimbTypeInt;
    }

    public Reimbursement() {
    }
    //Constructor with int user values and string status/type to get values from database
    public Reimbursement(int reimbID, double reimbAmt,
                         Timestamp submitted, Timestamp resolved,
                         String description, int reimbAuthor,
                         int reimbResolver, String reimbStatus,
                         String reimbType) {
        this.reimbID = reimbID;
        this.reimbAmt = reimbAmt;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
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

    public int getReimbAuthor() {
        return reimbAuthor;
    }

    public void setReimbAuthor(int reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public int getReimbResolver() {
        return reimbResolver;
    }

    public void setReimbResolver(int reimbResolver) {
        this.reimbResolver = reimbResolver;
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

    public int getReimbStatusInt() {
        return reimbStatusInt;
    }

    public void setReimbStatusInt(int reimbStatusInt) {
        this.reimbStatusInt = reimbStatusInt;
    }

    public int getReimbTypeInt() {
        return reimbTypeInt;
    }

    public void setReimbTypeInt(int reimbTypeInt) {
        this.reimbTypeInt = reimbTypeInt;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbID=" + reimbID +
                ", reimbAmt=" + reimbAmt +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", reimbAuthor=" + reimbAuthor +
                ", reimbResolver=" + reimbResolver +
                ", reimbStatus=" + reimbStatus +
                ", reimbType=" + reimbType +
                '}' + "\n";
    }
}
