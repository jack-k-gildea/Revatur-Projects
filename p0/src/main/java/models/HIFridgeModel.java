package models;

public class HIFridgeModel implements Comparable<HIFridgeModel>{


    private String HIUsername;
    private String rest_user;
    private String access_given;

    public HIFridgeModel(String HIUsername, String rest_user, String access_given) {
        this.HIUsername = HIUsername;
        this.rest_user = rest_user;
        this.access_given = access_given;
    }



    public String getHIUsername() {
        return HIUsername;
    }

    public void setHIUsername(String HIUsername) {
        this.HIUsername = HIUsername;
    }

    public String getRest_user() {
        return rest_user;
    }

    public void setRest_user(String rest_user) {
        this.rest_user = rest_user;
    }

    public String getAccess_given() {
        return access_given;
    }

    public void setAccess_given(String access_given) {
        this.access_given = access_given;
    }

    @Override
    public String toString() {
        return "Fridges with access {" +
                "rest_user='" + rest_user + '\'' +
                ", access_given='" + access_given + '\'' +
                '}';
    }

    @Override
    public int compareTo(HIFridgeModel o) {
        if(this.access_given.charAt(0) < o.access_given.charAt(0)) {
            return -1;
        }
        else{
            return 1;
        }
    }
}
