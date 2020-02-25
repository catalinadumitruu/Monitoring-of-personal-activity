package catalinadumitru.monitorizareaactivitatiipersonale;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import catalinadumitru.monitorizareaactivitatiipersonale.activities.Add;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.Calendar;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.More;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.Settings;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.Settings2;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.Statistics;
import catalinadumitru.monitorizareaactivitatiipersonale.database.User;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;

public class MainActivity extends AppCompatActivity {

    private App_DML dml_op;
    String currentUser = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if((grantResults.length > 0 )&& (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 , 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        //insert new user in database
        dml_op =  new App_DML(this.getApplication());
        Gson gson = new Gson();
        String userTobeREAD = getIntent().getStringExtra("user_sent");
        User u;
        User user = new User();

        try {
            u = gson.fromJson(userTobeREAD, User.class);
            user = user.Copy_User(u);

            //Log.i("heey", u.toString());

            Log.i("MYUSER", "Before");
            Log.i("MYUSER", u.toString());
            Log.i("MYUSER", "After");

            dml_op.insertUser(user);
            Toast.makeText(this, "User inserted", Toast.LENGTH_LONG).show();
            Log.i("inserted", "inserted");

            currentUser = user.getUsername();
            Log.i("before sending", user.getUsername() + "");
            write_in_SharedPref(currentUser);

        }catch(Exception e){
            Log.i("Er", e.getMessage());
            Toast.makeText(this, "User NOT inserted", Toast.LENGTH_LONG).show();

        }


        //LOCATION OF USER
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // when the phone moves
                Log.i("Location", location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // when location is service is enabled or disabled
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //ask for permission

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //daca nu avem permisiunea -> o cerem

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 , 0, locationListener);
            //we have permission
        }
    }

    public void openSettings(View view){
//        startActivity(new Intent(this, Settings2.class));
        startActivity(new Intent(this, Settings.class));
    }

    public void openCalendar(View view){
        startActivity(new Intent(this, Calendar.class));
    }

    public void openAdd(View view){

        Intent intent = new Intent(MainActivity.this , Add.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        intent.putExtra("currentDate", currentDate);
        startActivity(intent);
    }

    public void openStaticstics(View view){ startActivity(new Intent(this, Statistics.class)); }

    public void openMore(View view) { startActivity(new Intent(this, More.class));}

    public void write_in_SharedPref(String username){

        editor.putString("current_user", username);
        editor.apply();
    }
}

