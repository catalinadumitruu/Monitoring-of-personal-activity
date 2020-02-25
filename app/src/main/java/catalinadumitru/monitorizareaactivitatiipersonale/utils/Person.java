package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Person implements Serializable {

    String name;
    String email;
    String username;
    String password;
    String birthday;
    String country;

    public Person(){

    }

    public Person(String name, String username, String password, String birthday, String email){
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
