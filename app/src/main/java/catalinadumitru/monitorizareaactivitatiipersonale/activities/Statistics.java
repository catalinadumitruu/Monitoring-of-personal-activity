package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.icu.util.Calendar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import catalinadumitru.monitorizareaactivitatiipersonale.Firebase.Statistics_firebase;
import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;

public class Statistics extends AppCompatActivity {

    DatabaseReference databaseStatistics;

    DatePickerDialog.OnDateSetListener datePicker;
    TextView selector;
//    String intentDate;
//    ArrayList<String> intentList;
//    float intentRate;
//    Intent intent;

    TextView showActiv;
    String activities = "";
    List<String> listOfActivities;
    App_DML dml_op;
    int currentUserID;
    String currentUser;
    SharedPreferences pref;

    TextView date_text;
    TextView RATING_TEXT;
    TextView number_total;
    TextView completed_number;
    String id_firebase;
    String __currentUser;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String FILE_NAME = "file_from_AndroidStudio.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        databaseStatistics = FirebaseDatabase.getInstance().getReference("statistics");

        selector = findViewById(R.id.daySelector);
        showActiv = findViewById(R.id.showActivities_statistics);

        date_text = findViewById(R.id.date_stat);
        RATING_TEXT = findViewById(R.id.ratingOfTheday_stat);
        number_total = findViewById(R.id.numberOFActiv_stat);
        completed_number = findViewById(R.id.completed_number_stat);

        listOfActivities = new ArrayList<>();
        dml_op = new App_DML(this.getApplication());

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        currentUser = pref.getString("current_user", "NaN");
        currentUserID = dml_op.getUserID(currentUser);

        Log.i("user_in_stat", currentUser);
        __currentUser = currentUser;

        selector.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
                public void onClick(View view){
                Date date = new Date();
                Calendar cal =  Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                Log.i("YEAR", year + "");
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Statistics.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        datePicker, year, month, day);
                try{
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }catch(NullPointerException e){
                    Log.i("Null ptr exception", e.getMessage());
                }
            }
        });

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String day = dayOfMonth + "", monthof = month + "";
                if(dayOfMonth <= 9){day = "0"+dayOfMonth;}
//                if(month < 9){monthof= "" + month;}
                String date = day + "/" + (monthof + 1) + "/" + year;
                selector.setText(date);

                Log.i("crr date", "" + selector.getText().toString());
                //populate the textbox with activities for user
                listOfActivities = dml_op.getAllActivities(currentUserID, selector.getText().toString());

               // Log.i("here1", "" + listOfActivities.size());

                for(String activity : listOfActivities){
                    Log.i("here2", "hei");
                    activities += "-> " + activity + "\n";
                    Log.i("aaaa", activity + "");
                }

                showActiv.setText(activities);
            }
        };

//        try{
//            intentDate = intent.getStringExtra("dateFORactivities");
//            intentRate = Float.parseFloat(intent.getStringExtra("rate"));
//            intentList = (ArrayList<String>)intent.getSerializableExtra("listOfActivities");
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
    }

    public void showStatistics(View view){

        //in faza 1 am folosit shared pref

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String date = prefs.getString("date", "No date");
//        int numberOfActivities = prefs.getInt("numberOfAciv", -1);
//        float rate = prefs.getFloat("rate_of", 0.0f);
//
//        String ratingString = prefs.getString("ratingString", "No rating");
//        String numberString = prefs.getString("numberString", "no activ");
//
//        String s1 = R.string.show_stat_for_the_day +  date;
//        String s2 = R.string.rating_of_the_day + ratingString;
//        String s3 = R.string.no_of_activities_p1 + numberString + R.string.no_of_activities_p2;
//
//        date_text.setText(s1);
//        RATING_TEXT.setText(s2);
//        number_total.setText(s3);


        // Acum, in faza 3 citim datele din FIREBASE

        int no_activ;
        int compl_activ;
        float rating;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // get the total number of activ of the current user
        no_activ = dml_op.getCountActivities(currentUserID);

        //get the number of completed activ of the current user
        compl_activ = dml_op.getCountDONEActivities(currentUserID, true);


        //get the rating
        rating = prefs.getFloat("rate_of", 0.0f);


        //set the textbox to its values:
        date_text.setText("Statistics for the user " + currentUser);
        number_total.setText("The total number of activities is  " + no_activ);
        completed_number.setText("Out of which " + compl_activ + " is/are completed");
        RATING_TEXT.setText("The rating is  " + rating);
    }

    public void saveToText(View view){

        String text = showActiv.getText().toString();
        FileOutputStream writer = null;

        try {
            writer = openFileOutput(FILE_NAME, MODE_PRIVATE);

            writer.write(text.getBytes());
            showActiv.setText("These text was saved");

            Toast.makeText(this, "Text was saved " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveToFirebase(View view){
        addData();
    }

    public void addData(){

        int no_activ;
        int compl_activ;
        float ratingg;

        // get the total number of activ of the current user
        no_activ = dml_op.getCountActivities(currentUserID);

        //get the number of completed activ of the current user
        compl_activ = dml_op.getCountDONEActivities(currentUserID, true);
        editor.putInt("completed", compl_activ);

        //get the rating
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ratingg = prefs.getFloat("rate_of", 0.0f);

         id_firebase = databaseStatistics.push().getKey();

        Statistics_firebase stat = new Statistics_firebase();
        stat.setUserID(currentUserID);
        stat.setRating(ratingg);
        stat.setDone_activities(compl_activ);
        stat.setTotal_activities(no_activ);
        stat.setStatisticsID(id_firebase);

        //add to Firebase Database
        databaseStatistics.child(id_firebase).setValue(stat);

        Toast.makeText(this, "Statistics added in Firebase", Toast.LENGTH_SHORT).show();
    }

    public void openGraphic(View view){
        Intent intent = new Intent(Statistics.this, Grafic.class);

        //we also send the current id of the insert in firebase -> for select
        intent.putExtra("id_firebase", id_firebase);
        intent.putExtra("currentUsername_ofuser", __currentUser);
        startActivity(intent);
    }
}
