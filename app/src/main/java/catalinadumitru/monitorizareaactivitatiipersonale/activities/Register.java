package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import catalinadumitru.monitorizareaactivitatiipersonale.MainActivity;
import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;
import catalinadumitru.monitorizareaactivitatiipersonale.database.User;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.Person;

public class Register extends AppCompatActivity {

    List<String> countries;
    Spinner countryTextSpinner;
    TextView nameText;
    TextView usernameText;
    TextView passwordText;
    TextView birthdayText;
    TextView emailText;

//    public static final int REQUEST_CODE = 1;
    Person person;
    User user;

    App_DML dml_op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dml_op = new App_DML(this.getApplication());

        nameText = findViewById(R.id.nameTxt_register);
        usernameText = findViewById(R.id.usernameTxt_register);
        passwordText = findViewById(R.id.password_Register);
        birthdayText = findViewById(R.id.birthdayTxt_register);
        countryTextSpinner = findViewById(R.id.countrySpinner_reg);
        emailText = findViewById(R.id.email_register);

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

        ArrayAdapter adapter = new ArrayAdapter(Register.this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryTextSpinner.setAdapter(adapter);
    }

    public void __signup(View view) {  //and set the Settings

        //for settings2

//        person = new Person();
//        person.setName(nameText.getText().toString());
//        person.setUsername(usernameText.getText().toString());
//        person.setPassword(passwordText.getText().toString());
//        person.setBirthday(birthdayText.getText().toString());
//        person.setCountry(countryTextSpinner.getSelectedItem().toString());
//        person.setEmail(emailText.getText().toString());

//        Intent intent = new Intent(Register.this, Settings.class);
//        Bundle extras = new Bundle();
//        extras.putString("name", user.getName());
//        extras.putString("username", person.getUsername());
//        extras.putString("password", person.getPassword());
//        extras.putString("email", person.getEmail());
//        extras.putString("birthday", person.getBirthday());
//        intent.putExtras(extras);
//        startActivity(intent);

        //Log.i("PersonGenerated" , person.toString());

//        writeInFile(person);

        //for settings1

        user = new User();
        user.setName(nameText.getText().toString());
        user.setUsername(usernameText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.setBirthday(birthdayText.getText().toString());
        user.setCountry(countryTextSpinner.getSelectedItem().toString());
        user.setEmail(emailText.getText().toString());

//        Log.i("Userdata_sending", user.toString());

        Gson gson = new Gson();
        String userTOBEsent = gson.toJson(user);
        Intent resultIntent = new Intent(this, MainActivity.class);

        User searched = new User();
        try {
            searched = dml_op.getUser(user.getUsername());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (user.getName().length() > 0 &&
                user.getEmail().length() > 0 &&
                user.getCountry().length() > 0 &&
                user.getBirthday().length() > 0 &&
                user.getPassword().length() > 0 &&
                user.getUsername().length() > 0) {

            if(searched == null) {
                resultIntent.putExtra("user_sent", userTOBEsent);
                startActivity(resultIntent);
            }else{
                Toast.makeText(this, "Username already taken", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "All fields must be completed", Toast.LENGTH_LONG).show();

        }
    }

    public void writeInFile(Person pers) {
        String filename = "testFilemost.srl";
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+File.separator+filename));
            out.writeObject(pers);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
