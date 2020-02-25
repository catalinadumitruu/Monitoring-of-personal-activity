package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;
import catalinadumitru.monitorizareaactivitatiipersonale.database.User;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.Person;

public class Settings extends AppCompatActivity  {   // In acest model de settings valorile sunt preluate din baza de date
                                                     // IN SETTINGS2 este folosit un custom Adapter
    List<String> countries;
    TextView spinner;
    Person person;

    TextView nameText;
    TextView usernameText;
    TextView passwordText;
    TextView birthdayText;
    TextView emailText;

    Button changePic;
    ImageView profilePic;
    Uri profilePic_uri;
    private static final int PICK_IMAGE = 100;

    String currentUser;
    User crr_user;
    private App_DML dml_op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dml_op = new App_DML(this.getApplication());
        crr_user = new User();

        spinner = findViewById(R.id.location_settings1);
        nameText = findViewById(R.id.nameTXT_settings1);
        usernameText =  findViewById(R.id.usernameTXT_settings1);
        passwordText = findViewById(R.id.changePassword_settings1);
        birthdayText = findViewById(R.id.birtdayPicker_settings1);
        emailText = findViewById(R.id.email_setrtings1);
        profilePic = findViewById(R.id.profilePic_settings1);
        changePic = findViewById(R.id.changePicBTN_settings1);

        countries = new ArrayList<>();
        countries.add(getResources().getString(R.string.Romania));
        countries.add(getResources().getString(R.string.Moldova));
        countries.add(getResources().getString(R.string.Germania));
        countries.add(getResources().getString(R.string.Anglia));
        countries.add(getResources().getString(R.string.Elevetia));
        countries.add(getResources().getString(R.string.Olanda));
        countries.add(getResources().getString(R.string.Suedia));
        countries.add(getResources().getString(R.string.Norvegia));
        countries.add(getResources().getString(R.string.Irlanda));
        countries.add(getResources().getString(R.string.Italia));
        countries.add(getResources().getString(R.string.Polonia));
        countries.add(getResources().getString(R.string.USA));

//        ArrayAdapter adapter = new ArrayAdapter(Settings.this, android.R.layout.simple_spinner_item, countries);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        person = new Person();

        readData_fromDB();

        //Log.i("READ FROM DB", "" + crr_user);

        nameText.setText(crr_user.getName());
        usernameText.setText(crr_user.getUsername());
        birthdayText.setText(crr_user.getBirthday());
        emailText.setText(crr_user.getEmail());
        passwordText.setText(crr_user.getPassword());
        spinner.setText(crr_user.getCountry());

    }

    public void changePicture(View view){

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            profilePic_uri = data.getData();
            profilePic.setImageURI(profilePic_uri);
        }
    }

    public void readData_fromDB(){

        //get the current user (shared preferences)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentUser = prefs.getString("current_user", "NaN");

        Log.i("Current username " , currentUser);

        //call method to select
        try{
            crr_user = dml_op.getUser(currentUser);

//            Log.i("READ FROM DB", "" + crr_user);

            //get all the fields and put the result in current textboxes


        }catch(Exception e) {
            Log.i("SettingsError", e.getMessage() + "");
        }
    }

    public void update(View view){
        String username = usernameText.getText().toString();
        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();
        String email = emailText.getText().toString();
        String birthday = birthdayText.getText().toString();
        String country = spinner.getText().toString();

        User updatedUser = new User();
        updatedUser.setName(name);
        updatedUser.setBirthday(birthday);
        updatedUser.setCountry(country);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
        updatedUser.setUsername(username);

        dml_op.updateUser(username, name, password, email, birthday, country);

        Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();

        nameText.setText(updatedUser.getName());
        birthdayText.setText(updatedUser.getBirthday());
        emailText.setText(updatedUser.getEmail());
        passwordText.setText(updatedUser.getPassword());
        spinner.setText(updatedUser.getCountry());
    }

    public void delete(View view){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentUser = prefs.getString("current_user", "NaN");

        try{
            dml_op.deleteUser(currentUser);
            Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.this, Welcome.class);
            startActivity(intent);
        }catch(Exception e) {
            Log.i("SettingsError", e.getMessage() + "");
        }
    }
}
