package catalinadumitru.monitorizareaactivitatiipersonale.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "activities", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userID", childColumns = "userID", onDelete = CASCADE),
        indices = @Index(value = "userID"))
public class Activity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activity_id")
    private int activityID;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "status")
    private boolean status; //este un flag de tipul true/false -> true  = activitatea completata, false = activitate necompletata

    @ColumnInfo(name = "userID")
    private int userID;

//    public Activity(int activityID, String date, String description, char status, int userID) {
//        this.activityID = activityID;
//        this.date = date;
//        this.description = description;
//        this.status = status;
//        this.userID = userID;
//    }

    public Activity() {}

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityID=" + activityID +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", userID=" + userID +
                '}';
    }
}
