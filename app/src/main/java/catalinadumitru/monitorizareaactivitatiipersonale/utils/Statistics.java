package catalinadumitru.monitorizareaactivitatiipersonale.utils;

public class Statistics {

    String activities;
    int rating;

    public Statistics(String a, int r){
        this.activities = a;
        this.rating = r;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
