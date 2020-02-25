package catalinadumitru.monitorizareaactivitatiipersonale.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDAO {

    @Query("SELECT * FROM users WHERE username = :username")
    User getUser(String username);

    @Insert
    void insertUser(User user);

    @Query("UPDATE users SET name = :name, password = :password, email = :email, birthday = :birthday, country = :country WHERE username = :username")
    void updateUser(String username,String name, String password, String email, String birthday, String country);

    @Query("DELETE FROM users WHERE username = :username")
    void deleteUser(String username);

    @Query("SELECT userID FROM users WHERE username = :username")
    int getUserID(String username);

    @Insert
    void insertActivity(Activity activity);

    @Query("SELECT description FROM activities WHERE userID = :userID AND date = :date")
    List<String> getAllActivities(int userID, String date);

    @Query("SELECT count(activity_id) FROM activities WHERE userID = :userID")
    int getCountActivities(int userID);

    @Query("SELECT count(activity_id) FROM activities WHERE userID = :userID AND status = :bool")
    int getCountDONEActivities(int userID, boolean bool);
}
