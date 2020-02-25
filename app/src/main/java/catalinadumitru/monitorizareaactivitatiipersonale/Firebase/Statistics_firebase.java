package catalinadumitru.monitorizareaactivitatiipersonale.Firebase;

public class Statistics_firebase {

    private int userID;
    private String statisticsID;
    private int total_activities;
    private int done_activities;
    private float rating;

    public Statistics_firebase(){}

    public String getStatisticsID() {
        return statisticsID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setStatisticsID(String statisticsID) {
        this.statisticsID = statisticsID;
    }

    public int getTotal_activities() {
        return total_activities;
    }

    public void setTotal_activities(int total_activities) {
        this.total_activities = total_activities;
    }

    public int getDone_activities() {
        return done_activities;
    }

    public void setDone_activities(int done_activities) {
        this.done_activities = done_activities;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Statistics_firebase{" +
                "userID= " + userID +
                "statisticsID='" + statisticsID + '\'' +
                ", total_activities=" + total_activities +
                ", done_activities=" + done_activities +
                ", rating=" + rating +
                '}';
    }
}
