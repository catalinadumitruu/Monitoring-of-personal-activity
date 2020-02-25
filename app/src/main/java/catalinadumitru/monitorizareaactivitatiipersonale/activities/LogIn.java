package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import catalinadumitru.monitorizareaactivitatiipersonale.MainActivity;
import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;
import catalinadumitru.monitorizareaactivitatiipersonale.database.User;

public class LogIn extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private App_DML dml_op;
    User currentUser;
    String current_username= "";
    String password_for_user = "";
    TextView username;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.usename_login);
        password = findViewById(R.id.password_logIn);
        currentUser = new User();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        dml_op = new App_DML(this.getApplication());
    }

    public void openMain(View view){
        try{
            current_username = username.getText().toString();
            password_for_user = password.getText().toString();

            //search username in database
            boolean found = findInDB(current_username);

            //check if exists
            if(current_username.length() > 0 && password_for_user.length() > 0){
                if(found){
                        Log.i("HEHE", "" + currentUser.toString());
                    write_in_SharedPref(current_username);
                    startActivity(new Intent(this, MainActivity.class));
                }else{
                    Log.i("HEHEhe", "Nu exista in baza");
                    Toast.makeText(this, "USER DOESN'T EXIST. PLEASE REGISTER", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LogIn.this, Register.class);
                    startActivity(intent);
                }
            }else{
                Toast.makeText(this, "ALL FIELDS MUST BE COMPLETED", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.i("Login exception","" + e.getMessage());
        }

    }

    public boolean findInDB(String username) {
        boolean found = false;
        try{
            currentUser = dml_op.getUser(username);
            if(currentUser != null){
                found = true;
            }
        }catch(Exception e){
            Log.i("This exception", e.getMessage());
        }
        return found;
    }

    public void write_in_SharedPref(String username){
        editor.putString("current_user", username);
        editor.apply();
    }
}
