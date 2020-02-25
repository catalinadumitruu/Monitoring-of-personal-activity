package catalinadumitru.monitorizareaactivitatiipersonale.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    private int userID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @ColumnInfo(name = "country")
    private String country;

//    public User(int userID, String name, String username, String password, String email, String birthday, String country) {
//        this.userID = userID;
//        this.name = name;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.birthday = birthday;
//        this.country = country;
//    }

    public User() {}

    public User Copy_User(User u){
        User us = new User();
        us.userID = u.userID;
        us.name = u.name;
        us.username = u.username;
        us.password = u.password;
        us.email = u.email;
        us.birthday = u.birthday;
        us.country = u.country;

        return us;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(@NonNull String birthday) {
        this.birthday = birthday;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String contry) {
        this.country = contry;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", country='" + country + '\'' +
                '}';
    }
}
